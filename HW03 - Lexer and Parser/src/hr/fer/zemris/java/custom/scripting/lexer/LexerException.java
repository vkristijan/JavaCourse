package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Thrown to indicate that there is an error with the lexer.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class LexerException extends RuntimeException {

	/**
	 * The default serial version id.
	 */
	private static final long serialVersionUID = 2656502528046862864L;

	/**
	 * The default exception constructor.
	 */
	public LexerException() {
	}

	/**
	 * Exception constructor which handles the given exception message.
	 * 
	 * @param message
	 *            the exception message.
	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * Exception constructor which handles the given exception cause.
	 * 
	 * @param cause
	 *            the cause of exception.
	 */
	public LexerException(Throwable cause) {
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
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}
}
