package hr.fer.zemris.java.tecaj.hw3.prob1;

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
		int n = data.length;
		Character ch;

		while (currentIndex < n && "\n\r\t ".indexOf(data[currentIndex]) >= 0) {
			currentIndex++;
		}

		if (currentIndex == n) {
			currentIndex++;
			token = new Token(TokenType.EOF, null);
		} else if (currentIndex > n) {
			throw new LexerException("There is no more data to be processed.");
		} else if (state == LexerState.EXTENDED) {
			if (data[currentIndex] == '#') {
				token = new Token(TokenType.SYMBOL, '#');
				currentIndex++;
				return token;
			}

			StringBuilder word = new StringBuilder();
			while (currentIndex < n && "\n\r\t #".indexOf(data[currentIndex]) < 0) {
				word.append(data[currentIndex]);
				currentIndex++;
			}
			token = new Token(TokenType.WORD, word.toString());
		} else if ((ch = getLetter()) != null) {
			StringBuilder word = new StringBuilder();
			word.append(ch);

			while ((ch = getLetter()) != null) {
				word.append(ch);
			}
			token = new Token(TokenType.WORD, word.toString());
		} else if (Character.isDigit(data[currentIndex])) {
			StringBuilder number = new StringBuilder();

			while (currentIndex < n && Character.isDigit(data[currentIndex])) {
				number.append(data[currentIndex]);
				currentIndex++;
			}

			long value;
			try {
				value = Long.parseLong(number.toString());
			} catch (NumberFormatException e) {
				throw new LexerException("The given number can not be stored as a long!");
			}

			token = new Token(TokenType.NUMBER, value);
		} else {
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
		}

		return token;
	}

	/**
	 * Checks if the current character can be accepted as a letter in a word.
	 * This method also checks if the data contains escape characters. The
	 * current character is returned in case that it can be accepted as a word,
	 * null is returned otherwise.
	 * 
	 * @return the next character in a word if the current character can be
	 *         accepted as a letter in a word, null otherwise
	 * 
	 * @throws LexerException
	 *             if there is a wrong escape sequence
	 */
	private Character getLetter() {
		int n = data.length;
		if (currentIndex >= n) {
			return null;
		}

		if (Character.isLetter(data[currentIndex])) {
			return data[currentIndex++];
		}

		if (data[currentIndex] == '\\') {
			if (currentIndex + 1 >= n) {
				throw new LexerException("Invalid usage of escape character!");
			}

			if (data[currentIndex + 1] == '\\') {
				currentIndex += 2;
				return '\\';
			}

			if (Character.isDigit(data[currentIndex + 1])) {
				currentIndex += 2;
				return data[currentIndex - 1];
			}

			throw new LexerException("Invalid usage of escape character!");
		}

		return null;
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
