package hr.fer.zemris.java.custom.collections;

/**
 * <code>EmptyStackException is an unchecked exception that indicates a problem
 * with the stack.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException {
	/**
	 * The serial version ID.
	 */
	private static final long serialVersionUID = 5019575938076748881L;

	/**
	 * Constructs a new Empty stack exception with the specified detail message.
	 * 
	 * @param message
	 *            the message to be displayed when the exception occurs
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}
