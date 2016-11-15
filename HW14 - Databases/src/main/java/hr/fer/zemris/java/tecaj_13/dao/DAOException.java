package hr.fer.zemris.java.tecaj_13.dao;

/**
 * {@link RuntimeException} that is thrown when there is an error with
 * {@link DAO}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class DAOException extends RuntimeException {
	/**
	 * The serial version ID number.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new empty {@link DAOException}.
	 */
	public DAOException() {
	}

	/**
	 * Constructs a new {@link DAOException} with the specified detail message,
	 * cause, suppression enabled or disabled, and writable stack trace enabled
	 * or disabled.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause. (A null value is permitted, and indicates that the
	 *            cause is nonexistent or unknown.)
	 * @param enableSuppression
	 *            whether or not suppression is enabled or disabled
	 * @param writableStackTrace
	 *            whether or not the stack trace should be writable
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructs a new {@link DAOException} with the specified detail message,
	 * and cause.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause. (A null value is permitted, and indicates that the
	 *            cause is nonexistent or unknown.)
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new {@link DAOException} with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructs a new {@link DAOException} with the specified cause.
	 * 
	 * @param cause
	 *            the cause. (A null value is permitted, and indicates that the
	 *            cause is nonexistent or unknown.)
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}