package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Defines the state in which the lexer works.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public enum LexerState {
	/**
	 * In this state the lexer works normally and accepts words, numbers and
	 * symbols.
	 */
	BASIC,
	/**
	 * In this state the lexer accepts only words.
	 */
	EXTENDED;
}
