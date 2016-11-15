package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * This {@link ShellCommand} changes the specified symbol in the
 * {@link Environment}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class SymbolShellCommand extends AbstractCommand {
	/**
	 * Creates a new {@link SymbolShellCommand} and sets its name and
	 * description.
	 */
	public SymbolShellCommand() {
		super();

		name = "symbol";
		description.add("Changes the specified symbol in the environment.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = splitArguments(arguments);
			if (args.size() != 1 && args.size() != 2) {
				env.writeln("Invalid number of arguments! 1 or 2 arguments expected, but got: "
						+ args.size());
				return ShellStatus.CONTINUE;
			}

			String symbol = args.get(0).trim().toLowerCase();

			if (args.size() == 1) {
				switch (symbol) {
					case "prompt":
						env.write("Symbol for PROMPT is '");
						env.writeln(env.getPromptSymbol() + "'");
						break;
					case "morelines":
						env.write("Symbol for MORELINES is '");
						env.writeln(env.getMorelinesSymbol() + "'");
						break;
					case "multilines":
						env.write("Symbol for MULTILINES is '");
						env.writeln(env.getMultilineSymbol() + "'");
						break;
					default:
						env.writeln("The given symbol is not supported!");
						break;
				}
				return ShellStatus.CONTINUE;
			}

			String symbolGot = args.get(1).trim();

			if (symbolGot.length() != 1) {
				env.writeln("Only characters are acceptable symbols!");
				return ShellStatus.CONTINUE;
			}

			Character newSymbol = symbolGot.charAt(0);
			switch (symbol) {
				case "prompt":
					env.write("Symbol for PROMPT changed from '");
					env.write(env.getPromptSymbol() + "' to '");
					env.writeln(newSymbol + "'");
					env.setPromptSymbol(newSymbol);
					break;
				case "morelines":
					env.write("Symbol for MORELINES changed from '");
					env.write(env.getMorelinesSymbol() + "' to '");
					env.writeln(newSymbol + "'");
					env.setMorelinesSymbol(newSymbol);
					break;
				case "multilines":
					env.write("Symbol for MULTILINES changed from '");
					env.write(env.getMultilineSymbol() + "' to '");
					env.writeln(newSymbol + "'");
					env.setMultilineSymbol(newSymbol);
					break;
				default:
					env.writeln("The given symbol is not supported!");
					break;
			}

		} catch (IOException e) {
			System.err.println("Unable to write to the given environment.");
			System.exit(1);
		}
		return ShellStatus.CONTINUE;
	}

}
