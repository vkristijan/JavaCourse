package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * An exception that can occurs in case that there is a problem with the
 * {@code Lexer}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class LexerException extends RuntimeException {

	/**
	 * The default serial version ID number that is used for serialization.
	 */
	private static final long serialVersionUID = 3630372071549943354L;

	/**
	 * Creates a new {@code LexerException} without any additional information.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Creates a new {@code LexerException} with a message about the exception
	 * and the cause of the exception.
	 * 
	 * @param message
	 *            the message describing the exception.
	 * @param cause
	 *            the cause of the exception.
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new {@code LexerException} with a message about the exception.
	 * 
	 * @param message
	 *            the message describing the exception.
	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * Creates a new {@code LexerException} with a cause of the exception.
	 * 
	 * @param cause
	 *            the cause of the exception.
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}

}
