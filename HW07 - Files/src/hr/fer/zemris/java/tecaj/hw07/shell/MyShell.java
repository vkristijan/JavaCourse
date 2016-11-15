package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.TreeShellCommand;

/**
 * This shell enables the user to do some basic operations on files. The
 * supported command are:
 * <ul>
 * <li>{@link CatShellCommand}</li>
 * <li>{@link CharsetsShellCommand}</li>
 * <li>{@link CopyShellCommand}</li>
 * <li>{@link ExitShellCommand}</li>
 * <li>{@link HelpShellCommand}</li>
 * <li>{@link HexdumpShellCommand}</li>
 * <li>{@link LsShellCommand}</li>
 * <li>{@link MkdirShellCommand}</li>
 * <li>{@link SymbolShellCommand}</li>
 * <li>{@link TreeShellCommand}</li>
 * </ul>
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class MyShell {
	/**
	 * The default prompt character used by this shell.
	 */
	private static final Character DEFAULT_PROMPT = '>';
	/**
	 * The default more lines character used by this shell.
	 */
	private static final Character DEFAULT_MORE_LINES = '\\';
	/**
	 * The default multiple lines character used by this shell.
	 */
	private static final Character DEFAULT_MULTILINES = '|';

	/**
	 * The prompt character used by this shell.
	 */
	private static Character promptSymbol;
	/**
	 * The more lines character used by this shell.
	 */
	private static Character moreLinesSymbol;
	/**
	 * The multiple lines character used by this shell.
	 */
	private static Character multilinesSymbol;

	/**
	 * The status of the shell.
	 */
	private static ShellStatus status;

	/**
	 * A collection of all the commands supported by this shell.
	 */
	private static Map<String, ShellCommand> commands;
	/**
	 * The scanner used to read information from the user.
	 */
	private static Scanner in;

	/**
	 * The {@link Environment} used by this shell.
	 */
	private static Environment env;

	static {
		in = new Scanner(System.in);
		env = new EnvironmentImpl();

		promptSymbol = DEFAULT_PROMPT;
		moreLinesSymbol = DEFAULT_MORE_LINES;
		multilinesSymbol = DEFAULT_MULTILINES;

		commands = new HashMap<>();
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
	}

	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		try {
			env.writeln("Welcome to MyShell v 1.0");
		} catch (IOException e) {
			System.err.println("Unable to write to the given environment.");
			System.exit(1);
		}

		do {
			String line = readInput().trim();

			String commandName;
			String arguments;
			int firstSpace = line.indexOf(' ');

			if (firstSpace > -1) {
				commandName = line.substring(0, firstSpace);
				arguments = line.substring(firstSpace).trim();
			} else {
				commandName = line;
				arguments = "";
			}

			ShellCommand command;
			command = commands.get(commandName);
			status = command.executeCommand(new EnvironmentImpl(), arguments);
		} while (status != ShellStatus.TERMINATE);
	}

	/**
	 * Reads one line of input. If the line ends with a more lines character,
	 * another line will be read.
	 * 
	 * @return a string that was read from the input.
	 */
	private static String readInput() {
		StringBuilder finalLine = new StringBuilder();

		try {
			env.write(promptSymbol + " ");
			String line = env.readLine().trim();

			while (line.endsWith(moreLinesSymbol.toString())) {
				line = line.substring(0, line.length() - 1);
				finalLine.append(line);
				finalLine.append(" ");

				env.write(multilinesSymbol + " ");
				line = env.readLine().trim();
			}
			finalLine.append(line);

		} catch (IOException e) {
			System.err.println("Error during reading or writing!");
			System.exit(1);
		}

		return finalLine.toString();
	}

	/**
	 * An implementation of the {@link Environment} that is used in this shell.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 *
	 */
	private static class EnvironmentImpl implements Environment {
		@Override
		public String readLine() throws IOException {
			return in.nextLine();
		}

		@Override
		public void write(String text) throws IOException {
			System.out.print(text);
		}

		@Override
		public void writeln(String text) throws IOException {
			System.out.println(text);
		}

		@Override
		public Iterable<ShellCommand> commands() {
			return commands.values();
		}

		@Override
		public Character getMultilineSymbol() {
			return multilinesSymbol;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			multilinesSymbol = symbol;
		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			promptSymbol = symbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return moreLinesSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			moreLinesSymbol = symbol;
		}
	}

}
