package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Prints the file given in the argument to the environment. This command can
 * take either one or two arguments. The first one is the path to the file that
 * should be printed. The second argument is optional and defines the charset
 * that should be used for this command. If nothing is specified, the default
 * system defined charset will be used.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class CatShellCommand extends AbstractCommand {
	/**
	 * Creates a new {@link CatShellCommand} and sets its name and description.
	 */
	public CatShellCommand() {
		super();

		name = "cat";
		description.add(
			"Prints the file given in the argument to the environment. This command can");
		description.add(
			"take either one or two arguments. The first one is the path to the file that");
		description.add(
			"should be printed. The second argument is optional and defines the charset");
		description.add(
			"that should be used for this command. If nothing is specified, the default system");
		description.add("defined charset will be used.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = splitArguments(arguments);
			if (args.size() != 1 && args.size() != 2) {
				env.writeln(
					"Invalid number of arguments! The command expects either 1 or 2 arguments, but got: "
							+ args.size());
				return ShellStatus.CONTINUE;
			}

			Path path = Paths.get(args.get(0));
			if (Files.isDirectory(path)) {
				env.writeln("The specified path is not a valid file!");
				return ShellStatus.CONTINUE;
			}

			Charset charset = Charset.defaultCharset();
			if (args.size() == 2) {
				charset = Charset.forName(args.get(1));
			}

			BufferedReader br = Files.newBufferedReader(path, charset);
			int count = 0;
			char[] buffer = new char[4096];

			while ((count = br.read(buffer)) != -1) {
				env.write(new String(buffer, 0, count));
			}
			env.writeln("");

		} catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
			try {
				env.writeln("The given charset is not supported by the system.");
			} catch (IOException e1) {
				System.err.println("Unable to write to the given environment.");
				System.exit(1);
			}
		} catch (IOException e) {
			System.err.println("Unable to write to the given environment.");
			System.exit(1);
		}
		return ShellStatus.CONTINUE;
	}

}
