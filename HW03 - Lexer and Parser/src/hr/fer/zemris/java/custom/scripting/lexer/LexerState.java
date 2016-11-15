package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Defines the state in which the lexer works.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public enum LexerState {
	/**
	 * In this state the lexer works normally and generates words and symbols.
	 */
	TEXT_MODE,
	/**
	 * In this state the lexer generates tokens representing the elements inside
	 * a tag.
	 */
	TAG_MODE;
}
