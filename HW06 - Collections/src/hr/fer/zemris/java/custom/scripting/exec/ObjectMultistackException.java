package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Indicates a problem that occurred with the {@link ObjectMultistack}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class ObjectMultistackException extends RuntimeException {

	/**
	 * The default serial version ID number.
	 */
	private static final long serialVersionUID = 5779996822949334125L;

	/**
	 * Creates an empty new {@code ObjectMultistackException}.
	 */
	public ObjectMultistackException() {
		super();
	}

	/**
	 * Creates a new {@code ObjectMultistackException}. With the message equal
	 * to the one given in the argument and the cause of exception equal to the
	 * one in the argument.
	 * 
	 * @param message
	 *            the exception message.
	 * @param cause
	 *            the cause of the exception.
	 */
	public ObjectMultistackException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new {@code ObjectMultistackException}. With the message equal
	 * to the one given in the argument.
	 * 
	 * @param message
	 *            the exception message.
	 */
	public ObjectMultistackException(String message) {
		super(message);
	}

	/**
	 * Creates a new {@code ObjectMultistackException}. With the cause of
	 * exception equal to the one in the argument.
	 * 
	 * @param cause
	 *            the cause of the exception.
	 */
	public ObjectMultistackException(Throwable cause) {
		super(cause);
	}

}
