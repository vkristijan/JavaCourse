package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Thrown to indicate that there is an error with the smart script parser.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class SmartScriptParserException extends RuntimeException {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = -7143512009280949584L;

	/**
	 * The default exception constructor.
	 */
	public SmartScriptParserException() {
	}

	/**
	 * Exception constructor which handles the given exception message.
	 * 
	 * @param message
	 *            the exception message.
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

	/**
	 * Exception constructor which handles the given exception cause.
	 * 
	 * @param cause
	 *            the cause of exception.
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}

	/**
	 * Exception constructor which handles the given exception message and cause
	 * of exception.
	 * 
	 * @param message
	 *            the exception message.
	 * @param cause
	 *            the cause of exception.
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
