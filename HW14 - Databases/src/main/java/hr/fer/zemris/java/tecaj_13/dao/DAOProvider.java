package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.sql.SQLDAO;

/**
 * Singleton class that is used to get an instance of a {@link DAO} object that
 * can be used.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class DAOProvider {
	/**
	 * The instance of the {@link DAO} object.
	 */
	private static DAO dao = new SQLDAO();

	/**
	 * Gets the instance of the {@link DAO} object.
	 * 
	 * @return the instance of the {@link DAO} object.
	 */
	public static DAO getDao() {
		return dao;
	}
}