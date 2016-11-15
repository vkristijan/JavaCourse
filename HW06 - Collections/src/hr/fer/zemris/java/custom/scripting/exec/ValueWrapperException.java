package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Indicates a problem that occurred with the {@link ValueWrapper}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class ValueWrapperException extends RuntimeException {

	/**
	 * The default serial version ID number.
	 */
	private static final long serialVersionUID = 5779996822949334125L;

	/**
	 * Creates an empty new {@code ValueWrapperException}.
	 */
	public ValueWrapperException() {
		super();
	}

	/**
	 * Creates a new {@code ValueWrapperException}. With the message equal to
	 * the one given in the argument and the cause of exception equal to the one
	 * in the argument.
	 * 
	 * @param message
	 *            the exception message.
	 * @param cause
	 *            the cause of the exception.
	 */
	public ValueWrapperException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new {@code ValueWrapperException}. With the message equal to
	 * the one given in the argument.
	 * 
	 * @param message
	 *            the exception message.
	 */
	public ValueWrapperException(String message) {
		super(message);
	}

	/**
	 * Creates a new {@code ValueWrapperException}. With the cause of exception
	 * equal to the one in the argument.
	 * 
	 * @param cause
	 *            the cause of the exception.
	 */
	public ValueWrapperException(Throwable cause) {
		super(cause);
	}

}
