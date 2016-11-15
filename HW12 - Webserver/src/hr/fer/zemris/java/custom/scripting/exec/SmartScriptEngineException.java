package hr.fer.zemris.java.custom.scripting.exec;

/**
 * {@link RuntimeException} that can occur if there is an error during executing
 * Smart Script code in {@link SmartScriptEngine}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class SmartScriptEngineException extends RuntimeException {
	/**
	 * Default serial version ID number.
	 */
	private static final long serialVersionUID = 210144044064794036L;

	/**
	 * Creates a new {@link SmartScriptEngineException}.
	 */
	public SmartScriptEngineException() {
	}

	/**
	 * Creates a new {@link SmartScriptEngineException} with the message set to
	 * the one in the argument.
	 * 
	 * @param message
	 *            the message for the exception
	 */
	public SmartScriptEngineException(String message) {
		super(message);
	}

	/**
	 * Creates a new {@link SmartScriptEngineException} with the cause of
	 * throwing the exception to the one from the argument.
	 * 
	 * @param cause
	 *            the cause of throwing the exception
	 */
	public SmartScriptEngineException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new {@link SmartScriptEngineException} with the message set to
	 * the one in the argument, and the cause of throwing the exception to the
	 * one from the argument.
	 * 
	 * @param message
	 *            the message for the exception
	 * @param cause
	 *            the cause of throwing the exception
	 */
	public SmartScriptEngineException(String message, Throwable cause) {
		super(message, cause);
	}
}
