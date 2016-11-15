package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Singleton class used to provide an instance of {@link DAO} that is used in the program.
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class DAOProvider {
	/**
	 * The {@link DAO} instance that is used in this program.
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Returns the instance of a {@link DAO}.
	 * 
	 * @return the {@link DAO} instance that should be used.
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}