package hr.fer.zemris.java.webserver;

/**
 * Runtime exception that will occur if there is a problem with
 * {@link RequestContext}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class RequestException extends RuntimeException {
	/**
	 * The serial version ID number.
	 */
	private static final long serialVersionUID = 3919264475777753014L;

	/**
	 * Creates a new {@link RequestException}, without a message.
	 */
	public RequestException() {
	}

	/**
	 * Creates a new {@link RequestException} with the message set to the one
	 * from the argument.
	 * 
	 * @param message
	 *            the message that should be displayed for this exception.
	 */
	public RequestException(String message) {
		super(message);
	}

	/**
	 * Creates a new {@link RequestException} with the cause of throwing the
	 * exception to the one from the argument.
	 * 
	 * @param cause
	 *            the cause of throwing this exception.
	 */
	public RequestException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new {@link RequestException} with the message set to the one
	 * from the argument and the cause of throwing the exception to the one from
	 * the argument.
	 * 
	 * @param message
	 *            the message that should be displayed for this exception.
	 * @param cause
	 *            the cause of throwing this exception.
	 */
	public RequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
