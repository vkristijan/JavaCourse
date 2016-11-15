package hr.fer.zemris.java.tecaj.hw07.shell.commands;

/**
 * A simple lexer that can be used to parse a string argument into smaller
 * strings. The lexer has two states. In the normal state the lexer will
 * generate a string every time a blank space occurs, and then skip all the
 * spaces until a next character. If the lexer reaches a '"' char at the
 * beginning of a string it will generate a new string when it reaches the next
 * '"' char. In this mode, the lexer can generate strings containing spaces.
 * Inside of '
 * "' chars, the lexer also supports escaping. For example \" will be treated as a '"
 * ' char without ending the string, while \\ will be treated as a single \. All
 * other escape sequences are ignored.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class Lexer {
	/**
	 * The current index in the data array.
	 */
	private int currentIndex;
	/**
	 * The data for the lexer.
	 */
	private char[] data;

	/**
	 * The last generated {@link String}.
	 */
	private String token;

	/**
	 * Creates a new {@link Lexer} with the data given in the argument.
	 * 
	 * @param data
	 *            the data to be used in this lexer.
	 */
	public Lexer(String data) {
		this.data = data.toCharArray();
	}

	/**
	 * The next string in the data array, or null if there are no more strings.
	 * 
	 * @return The next string in the data array, or null if there are no more
	 *         strings.
	 */
	public String next() {
		skipBlank();
		if (currentIndex >= data.length) {
			return null;
		}

		if (data[currentIndex] == '"') {
			token = pathString();
			return token;
		}

		StringBuilder string = new StringBuilder();
		while (currentIndex < data.length) {
			if (!Character.isSpaceChar(data[currentIndex])) {
				string.append(data[currentIndex]);
				currentIndex++;
			} else {
				break;
			}
		}
		token = string.toString();
		return token;
	}

	/**
	 * Returns the last generated {@link String} token.
	 * 
	 * @return the last generated {@link String} token.
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Calculates the next string inside " signs. This method can have escape
	 * characters.
	 * 
	 * @return the next generated string.
	 */
	private String pathString() {
		currentIndex++; // skip the " sign
		StringBuilder string = new StringBuilder();
		while (currentIndex < data.length) {
			if (data[currentIndex] == '\\' && currentIndex < data.length - 1) {
				if (data[currentIndex + 1] == '\\') {
					string.append(data[currentIndex + 1]);
					currentIndex += 2;
				} else if (data[currentIndex + 1] == '"') {
					string.append(data[currentIndex + 1]);
					currentIndex += 2;
				} else {
					string.append(data[currentIndex]);
					currentIndex++;
				}
			} else if (data[currentIndex] != '"') {
				string.append(data[currentIndex]);
				currentIndex++;
			} else {
				currentIndex++; // skip the last " sign
				break;
			}
		}
		return string.toString();
	}

	/**
	 * Skip all the blank spaces currently in the data.
	 */
	private void skipBlank() {
		while (currentIndex < data.length) {
			if (Character.isSpaceChar(data[currentIndex])) {
				currentIndex++;
			} else {
				break;
			}
		}
	}
}