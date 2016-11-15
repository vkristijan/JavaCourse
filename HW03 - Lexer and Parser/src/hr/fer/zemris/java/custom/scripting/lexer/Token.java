package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This class is a token that can have different types and store a value.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Token {
	/**
	 * The type of the token.
	 */
	private final TokenType type;
	/**
	 * The token value.
	 */
	private final Object value;

	/**
	 * Constructs a new token of the given type, with the given value. The type
	 * is not allowed to be null.
	 * 
	 * @param type
	 *            the type of the token
	 * @param value
	 *            the value of the token
	 */
	public Token(TokenType type, Object value) {
		if (type == null) {
			throw new IllegalArgumentException("The type is not allowed to be null!");
		}
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns the value of the token.
	 * 
	 * @return the value of the token
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Returns the type of the token.
	 * 
	 * @return the type of the token
	 */
	public TokenType getType() {
		return type;
	}
}