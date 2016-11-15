package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * All the possible token types.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public enum TokenType {
	/**
	 * This type represents the end of file.
	 */
	EOF,
	/**
	 * This type is used for a token that is a word.
	 */
	WORD,
	/**
	 * This type is used for a token that is a number.
	 */
	NUMBER,
	/**
	 * This type is used for a token that is a symbol.
	 */
	SYMBOL;
}
