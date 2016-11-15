package hr.fer.zemris.java.tecaj_13.dao;

/**
 * {@link RuntimeException} that can occur if there is an error while accessing
 * data.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class DAOException extends RuntimeException {
	/**
	 * The default serial version ID number.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@link DAOException} with the description message set to
	 * the one from the argument, and the cause of exception from the other
	 * argument.
	 * 
	 * @param message
	 *            The description message.
	 * @param cause
	 *            The cause of throwing the exception.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new {@link DAOException} with the error message set to the
	 * one given in the argument.
	 * 
	 * @param message
	 *            The error description message.
	 */
	public DAOException(String message) {
		super(message);
	}
}