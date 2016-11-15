package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * Provides an entity manager when needed.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class JPAEMProvider {
	/**
	 * A {@link ThreadLocal} collection of {@link LocalData}.
	 */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Returns the entity manager.
	 * 
	 * @return the entity manager.
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if(ldata==null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Closes the entity manager and tries to commit the changes.
	 * 
	 * @throws DAOException
	 *             if there is an error while committing changes.
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if(ldata==null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) throw dex;
	}
	
	/**
	 * A class holding static parameters.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 */
	private static class LocalData {
		/**
		 * The entity manager used.
		 */
		EntityManager em;
	}
	
}
