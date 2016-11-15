package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * This command expects two arguments: the source file and destination. The
 * command will copy the file from the source to the destination. If the file
 * already exists, it will ask the user if he wants to replace it or not. If the
 * destination is a folder the command will paste the file into the folder,
 * using the name of the original file.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class CopyShellCommand extends AbstractCommand {
	/**
	 * Creates a new {@link ExitShellCommand} and sets its name and description.
	 */
	public CopyShellCommand() {
		super();

		name = "copy";
		description
				.add("This command expects two arguments: the source file and destination.");
		description.add(
			"The command will copy the file from the source to the destination. If the file already");
		description.add(
			"exists, it will ask the user if he wants to replace it or not. If the destination is a folder");
		description.add(
			"the command will paste the file into the folder, using the name of the original file.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> args = splitArguments(arguments);
			if (args.size() != 2) {
				env.writeln("Invalid number of arguments! 2 arguments are expected, but got: "
						+ args.size());
				return ShellStatus.CONTINUE;
			}

			Path path = Paths.get(args.get(0));
			if (!Files.exists(path)) {
				env.writeln("The specified path does not exist!");
				return ShellStatus.CONTINUE;
			}
			if (Files.isDirectory(path)) {
				env.writeln("The command can not copy folders.");
				return ShellStatus.CONTINUE;
			}

			Path destination = Paths.get(args.get(1));

			if (Files.exists(destination)) {
				if (Files.isDirectory(destination)) {
					destination = destination.resolve(path.getFileName());
				}
				if (Files.exists(destination)) {
					env.writeln(
						"The destination file already exists, would you like to replace it? (y/n)");
					env.write(env.getPromptSymbol() + " ");

					String answer = env.readLine().trim().toLowerCase();
					if (!answer.equals("y")) {
						return ShellStatus.CONTINUE;
					}
				}
			} else {
				if (destination.getFileName().toString().indexOf('.') == -1) {
					destination.resolve(path.getFileName());
				}
			}

			try {
				copy(path, destination);
			} catch (IOException e) {
				env.writeln("Unable to open or create the given files.");
				return ShellStatus.CONTINUE;
			}

		} catch (IOException e) {
			System.err.println("Unable to write to the given environment.");
			System.exit(1);
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Copies the file from the path to the destination path. If the destination
	 * file already exists, it will be overwritten.
	 * 
	 * @param path
	 *            the source file
	 * @param destination
	 *            the destination file
	 * @throws IOException
	 *             if it is not possible to access the source or destination
	 *             file
	 */
	private void copy(Path path, Path destination) throws IOException {
		try (BufferedInputStream br = new BufferedInputStream(
			new FileInputStream(path.toFile()));
				BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(destination.toFile()))) {

			int count = 0;
			byte[] buffer = new byte[4096];
			while ((count = br.read(buffer)) != -1) {
				out.write(buffer, 0, count);
			}
		}
	}

}
