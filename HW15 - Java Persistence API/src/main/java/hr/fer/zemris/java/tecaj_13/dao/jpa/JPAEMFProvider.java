package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Singleton class used to provide a reference to an
 * {@link EntityManagerFactory} when needed.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class JPAEMFProvider {
	/**
	 * The {@link EntityManagerFactory} used by the provider.
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * Returns a reference to the {@link EntityManagerFactory}.
	 * 
	 * @return a reference to the {@link EntityManagerFactory}.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Sets the {@link EntityManagerFactory} to the value given in the argument.
	 * 
	 * @param emf
	 *            the new value for the {@link EntityManagerFactory}.
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}