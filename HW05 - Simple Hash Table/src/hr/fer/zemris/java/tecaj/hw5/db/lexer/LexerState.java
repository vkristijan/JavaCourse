package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * Defines the state in which the {@link Lexer} works. There are two supported
 * states:
 * <ul>
 * <li><strong>NORMAL</strong> which defines that the lexer is working in a
 * normal state, generating symbols, operators and commands.</li>
 * <li><strong>STRING</strong> which defines that the lexer is working in a
 * state where it generates only string literals and symbols.</li>
 * </ul>
 * <br>
 * 
 * @author Kristijan Vulinovic
 *
 */
public enum LexerState {
	/**
	 * Defines that the lexer is working in a normal state, generating symbols,
	 * operators and commands.
	 */
	NORMAL,
	/**
	 * Defines that the lexer is working in a state where it generates only
	 * string literals and symbols.
	 */
	STRING;
}
