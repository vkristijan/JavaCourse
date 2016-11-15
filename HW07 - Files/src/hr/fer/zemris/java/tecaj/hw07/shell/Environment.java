package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;

/**
 * An environment used in a shell. Every environment defines its own set of
 * commands that can be executed in it. The environment also defines 3 special
 * characters:
 * <ul>
 * <li><i>PROMPT</i> The symbol is used to be shown on the beginning of every
 * line where the user should input something.</li>
 * <li><i>MORE_LINES</i> The symbol is used to split a single command into
 * multiple lines.</li>
 * <li><i>MULTILINES</i> The symbol is used to mark he beginning of a line when
 * a single command is split into multiple lines. The first line of the command
 * will not contain this symbol.</li>
 * </ul>
 * <br>
 * 
 * Every environment defines its methods to read and write text. The commands
 * should use the given methods to communicate with the user.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public interface Environment {
	/**
	 * Reads a line of text from the input defined in the {@link Environment}.
	 * 
	 * @return a {@link String} with the text the was read from the input.
	 * @throws IOException
	 *             if there is a problem while reading from the
	 *             {@link Environment} input.
	 */
	String readLine() throws IOException;

	/**
	 * Writes a line of text to the output defined in the {@link Environment}.
	 * 
	 * @param text
	 *            the text to be written.
	 * @throws IOException
	 *             if there is a problem while writing to the
	 *             {@link Environment} output.
	 */
	void write(String text) throws IOException;

	/**
	 * Writes a line of text to the output defined in the {@link Environment}.
	 * The written line ends with a new line character.
	 * 
	 * @param text
	 *            the text to be written.
	 * @throws IOException
	 *             if there is a problem while writing to the
	 *             {@link Environment} output.
	 */
	void writeln(String text) throws IOException;

	/**
	 * Returns a {@link Iterable} object containing all the {@link ShellCommand}
	 * s supported by this {@link Environment}.
	 * 
	 * @return a {@link Iterable} object containing all the {@link ShellCommand}
	 *         s supported by this {@link Environment}.
	 */
	Iterable<ShellCommand> commands();

	/**
	 * Returns the multiline symbol that is used in this {@link Environment}.
	 * The symbol is used to mark he beginning of a line when a single command
	 * is split into multiple lines. The first line of the command will not
	 * contain this symbol.
	 * 
	 * @return the new symbol to be used.
	 */
	Character getMultilineSymbol();

	/**
	 * Sets the multiline symbol to the new value given in the argument. The
	 * symbol is used to mark he beginning of a line when a single command is
	 * split into multiple lines. The first line of the command will not contain
	 * this symbol.
	 * 
	 * @param symbol
	 *            the new symbol to be used.
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Returns the prompt symbol that is used in this {@link Environment}. The
	 * symbol is used to be shown on the beginning of every line where the user
	 * should input something.
	 * 
	 * @return the new symbol to be used.
	 */
	Character getPromptSymbol();

	/**
	 * Sets the prompt symbol to the new value given in the argument. The symbol
	 * is used to be shown on the beginning of every line where the user should
	 * input something.
	 * 
	 * @param symbol
	 *            the new symbol to be used.
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Returns the more lines symbol that is used in this {@link Environment}.
	 * The symbol is used to split a single command into multiple lines.
	 * 
	 * @return the new symbol to be used.
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets the more lines symbol to the new value given in the argument. The
	 * symbol is used to split a single command into multiple lines.
	 * 
	 * @param symbol
	 *            the new symbol to be used.
	 */
	void setMorelinesSymbol(Character symbol);
}
