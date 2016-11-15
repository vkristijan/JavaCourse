package hr.fer.zemris.java.custom.scripting.lexer;

import static hr.fer.zemris.java.custom.scripting.lexer.Utility.TAG_BEGIN;
import static hr.fer.zemris.java.custom.scripting.lexer.Utility.TAG_END;;

/**
 * Parses the given input string and generates tokens. The tokens are not
 * generated before calling the nextToken method.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Lexer {
	/**
	 * Input text.
	 */
	private final char[] data;
	/**
	 * Current token.
	 */
	private Token token;
	/**
	 * The index of the current character in the data that should be examined.
	 */
	private int currentIndex;
	/**
	 * The current state of the lexer.
	 */
	private LexerState state;

	/**
	 * Constructs a new lexer that parses the given string into tokens.
	 * 
	 * @param text
	 *            the text to be parsed.
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("The given text must not be null!");
		}
		data = text.toCharArray();
		state = LexerState.TEXT_MODE;
	}

	/**
	 * Calculates the next token.
	 * 
	 * @return the next token
	 * 
	 * @throws LexerException
	 *             if there was an error while calculating the new token.
	 */
	public Token nextToken() {
		if (currentIndex > data.length) {
			throw new LexerException("No more tokens in the data!");
		}

		if (state == LexerState.TAG_MODE) {
			ignoreWhiteSpaces();
		}

		if (currentIndex == data.length) {
			currentIndex++;
			token = new Token(TokenType.EOF, null);
			return token;
		}

		if (isBeginningOf(TAG_BEGIN)) {
			token = new Token(TokenType.SYMBOL, TAG_BEGIN);
			currentIndex += 2;
			return token;
		}
		if (isBeginningOf(TAG_END)) {
			token = new Token(TokenType.SYMBOL, TAG_END);
			currentIndex += 2;
			return token;
		}

		switch (state) {
			case TEXT_MODE:
				token = nextTextToken();
				return token;
			case TAG_MODE:
				token = nextTagToken();
				return token;
			default:
				throw new LexerException("Unsuported lexer state!");
		}
	}

	/**
	 * Generates the next token as calculated in the <code>TAG_MODE</code>
	 * state.
	 * 
	 * @return the next token
	 * @throws LexerException
	 *             if there was an error while parsing an integer or double.
	 */
	private Token nextTagToken() {
		String word = getCharacterSet();

		if (word.matches("^[+\\-*/^]$")) {
			token = new Token(TokenType.OPERATOR, word);
		} else if (word.startsWith("@")) {
			token = new Token(TokenType.FUNCTION, word.substring(1));
		} else if (word.matches("^\".+\"$")) {
			token = new Token(TokenType.STRING, word.substring(1, word.length() - 1));
		} else if (word.matches("^-?\\d+$")) {
			try {
				Integer number = Integer.parseInt(word);
				token = new Token(TokenType.INTEGER, number);
			} catch (NumberFormatException e) {
				throw new LexerException("Unable to parse the given integer!");
			}
		} else if (word.matches("^-?\\d+\\.\\d+$")) {
			try {
				Double number = Double.parseDouble(word);
				token = new Token(TokenType.DOUBLE, number);
			} catch (NumberFormatException e) {
				throw new LexerException("Unable to parse the given double!");
			}
		} else {
			token = new Token(TokenType.VARIABLE, word);
		}
		return token;
	}

	/**
	 * Returns a string containing all the characters in the data until a
	 * whitespace or tag beginning or tag ending. Changes the value of the
	 * current index to point to the first character not included in this
	 * string.
	 * 
	 * @return the next string.
	 */
	private String getCharacterSet() {
		if (data[currentIndex] == '=') {
			currentIndex++;
			return "=";
		}

		StringBuilder string = new StringBuilder();
		while (currentIndex < data.length && "\r\n\t ".indexOf(data[currentIndex]) < 0) {
			if (isBeginningOf(TAG_BEGIN) || isBeginningOf(TAG_END)) {
				break;
			}
			string.append(data[currentIndex]);
			currentIndex++;
		}

		return string.toString();
	}

	/**
	 * Changes the current index so that it points to the next character in the
	 * data that is not a whitespace.
	 */
	private void ignoreWhiteSpaces() {
		while (currentIndex < data.length && "\r\n\t ".indexOf(data[currentIndex]) >= 0) {
			currentIndex++;
		}
	}

	/**
	 * Generates the next token as calculated in the <code>TEXT_MODE</code>
	 * state.
	 * 
	 * @return the next token
	 * @throws LexerException
	 *             if there was an error while evaluating the next token.
	 */
	private Token nextTextToken() {
		StringBuilder word = new StringBuilder();
		while (currentIndex < data.length && !isBeginningOf(TAG_BEGIN)) {
			if (data[currentIndex] == '\\') {
				word.append(getEscapeChar());
				continue;
			}

			word.append(data[currentIndex]);
			currentIndex++;
		}

		return new Token(TokenType.TEXT, word.toString());
	}

	/**
	 * Checks if the current position in the data array is the beginning of the
	 * given text.
	 * 
	 * @param text
	 *            the text that should be found at the current position
	 * 
	 * @return a boolean value <br>
	 *         <ul>
	 *         <li><strong> true </strong> if the current position is the
	 *         beginning of the given text</li>
	 *         <li><strong> false </strong> otherwise</li>
	 *         </ul>
	 */
	private boolean isBeginningOf(String text) {
		char[] textData = text.toCharArray();

		if (currentIndex > data.length - textData.length) {
			return false;
		}

		for (int i = 0; i < textData.length; ++i) {
			if (data[currentIndex + i] != textData[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the character at the current position in the data, after
	 * evaluating the escape character. The currentIndex will change after
	 * reading the character.
	 * 
	 * @return the evaluated escape character
	 * @throws LexerException
	 *             if the current position does not contain a valid escape
	 *             sequence
	 */
	private char getEscapeChar() {
		if (currentIndex >= data.length - 1 || data[currentIndex] != '\\') {
			throw new LexerException("Invalid escape sequence!");
		}

		currentIndex += 2;
		switch (data[currentIndex - 1]) {
			case 'r':
				return '\r';
			case 'n':
				return '\n';
			case 't':
				return '\t';
			case '\\':
				return '\\';
			case '{':
				return '{';
			case '"':
				return '"';
			default:
				currentIndex -= 2;
				throw new LexerException("Invalid escape sequence!");
		}
	}

	/**
	 * Returns the last calculated token.
	 * 
	 * @return the last calculated token.
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets the state of the lexer to the given state. It's not allowed to set
	 * the state to null.
	 * 
	 * @param state
	 *            the new state for the lexer.
	 * @throws IllegalArgumentException
	 *             if the given state is null.
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("The state is not allowed to be null!");
		}

		this.state = state;
	}
}
