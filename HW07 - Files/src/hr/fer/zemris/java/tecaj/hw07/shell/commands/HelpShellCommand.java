package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The help command is used to provide information about the commands in the
 * shell. If the command is called with no arguments given, it will print a list
 * of all the available commands in the current environment. If the command is
 * called with an argument, it will print the name of the command from the
 * argument and its description.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class HelpShellCommand extends AbstractCommand {

	/**
	 * Creates a new {@link HelpShellCommand} and sets its name and description.
	 */
	public HelpShellCommand() {
		super();

		name = "help";
		description.add(
			"The help command is used to provide information about the commands in the shell.");
		description.add(
			"If the command is called with no arguments given, it will print a list of all the");
		description.add("available commands in the current environment.");
		description.add(
			"If the command is called with an argument, it will print the name of the command");
		description.add("from the argument and its description.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.toLowerCase().trim();

		if (arguments.isEmpty()) {
			printCommands(env);
			return ShellStatus.CONTINUE;
		}

		try {
			for (ShellCommand command : env.commands()) {
				if (command.getCommandName().toLowerCase().equals(arguments)) {
					env.writeln(command.getCommandName());

					for (String line : command.getCommandDescription()) {
						env.writeln(line);
					}

					return ShellStatus.CONTINUE;
				}
			}

			env.writeln("The given command does not exist.");
		} catch (IOException e) {
			System.err.println("Unable to access the output stream!");
			System.exit(1);
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Prints all the commands supported in this environment.
	 * 
	 * @param env
	 *            the current environment.
	 */
	private void printCommands(Environment env) {
		try {
			env.writeln("Supported commands:");
			for (ShellCommand command : env.commands()) {
				env.writeln("\t" + command.getCommandName());
			}
		} catch (IOException e) {
			System.err.println("Unable to access the output stream!");
			System.exit(1);
		}
	}

}
