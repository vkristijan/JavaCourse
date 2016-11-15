package hr.fer.zemris.java.tecaj_13.dao.sql;

import java.sql.Connection;

/**
 * Stores the connection to a database in a {@link ThreadLocal} object.
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class SQLConnectionProvider {

	/**
	 * Database connection for the current thread.
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Set the connection for the current thread, or remove the existing one if
	 * the argument is <code>null</code>.
	 * 
	 * @param con
	 *            the database connection.
	 */
	public static void setConnection(Connection con) {
		if(con==null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * Get the current connection.
	 * 
	 * @return the current database connection.
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}