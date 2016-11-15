package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * This {@link ShellCommand} creates the folder structure given in the argument.
 * If the given path already exists, nothing will happen.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class MkdirShellCommand extends AbstractCommand {
	/**
	 * Creates a new {@link MkdirShellCommand} and sets its name and
	 * description.
	 */
	public MkdirShellCommand() {
		super();

		name = "mkdir";
		description.add("Creates the folder structure given in the argument.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = splitArguments(arguments);
			if (args.size() != 1) {
				env.writeln(
					"Invalid number of arguments! Only 1 argument is expected, but got: "
							+ args.size());
				return ShellStatus.CONTINUE;
			}

			Path path = Paths.get(args.get(0));
			if (Files.exists(path)) {
				env.writeln("The specified path already exists!");
				return ShellStatus.CONTINUE;
			}

			Files.createDirectories(path);
		} catch (IOException e) {
			System.err.println("Unable to write to the given environment.");
			System.exit(1);
		}
		return ShellStatus.CONTINUE;
	}

}
