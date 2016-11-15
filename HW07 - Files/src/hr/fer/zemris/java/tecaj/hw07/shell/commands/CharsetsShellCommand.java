package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Lists names of supported charsets for your Java platform.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class CharsetsShellCommand extends AbstractCommand {
	/**
	 * Creates a new {@link CharsetsShellCommand} and sets its name and description.
	 */
	public CharsetsShellCommand() {
		super();

		name = "charsets";
		description.add("Lists names of supported charsets for your Java platform.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			if (!arguments.trim().isEmpty()) {
				env.writeln("Invalid arguments for exit command!");
				return ShellStatus.CONTINUE;
			}

			env.writeln("Supported charsets:");
			for (String charset : Charset.availableCharsets().keySet()) {
				env.writeln(charset);
			}
		} catch (IOException e) {
			System.err.println("Unable to write!");
			System.exit(1);
		}
		return ShellStatus.CONTINUE;
	}

}
