package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * A {@link ShellCommand} that allows the user to exit the shell. This command
 * expects no arguments.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class ExitShellCommand extends AbstractCommand {

	/**
	 * Creates a new {@link ExitShellCommand} and sets its name and description.
	 */
	public ExitShellCommand() {
		super();

		name = "exit";
		description.add("The exit statement is used to exit from the shell.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.trim().isEmpty()) {
			try {
				env.writeln("Invalid arguments for exit command!");
				return ShellStatus.CONTINUE;
			} catch (IOException e) {
				System.err.println("Unable to write!");
				System.exit(1);
			}
		}

		return ShellStatus.TERMINATE;
	}

}
