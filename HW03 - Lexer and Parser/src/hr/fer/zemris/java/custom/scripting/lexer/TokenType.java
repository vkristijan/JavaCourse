package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * All the possible token types.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public enum TokenType {
	/**
	 * This type represents text.
	 */
	TEXT,
	/**
	 * This type is used for a token that is a symbol.
	 */
	SYMBOL,
	/**
	 * This token represents the end of file.
	 */
	EOF,
	/**
	 * This type is used for a token that is an operator in a tag.
	 */
	OPERATOR,
	/**
	 * This type is used for a token that is a function in a tag.
	 */
	FUNCTION,
	/**
	 * This type is used for a token that is a variable in a tag.
	 */
	VARIABLE,
	/**
	 * This type is used for a token that is an integer in a tag.
	 */
	INTEGER,
	/**
	 * This type is used for a token that is a double in a tag.
	 */
	DOUBLE,
	/**
	 * This type is used for a token that is a string in a tag.
	 */
	STRING;
}
