package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * A {@link ShellCommand} that prints all the files and folders and sub folders
 * of a given directory into a tree like hierarchy.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class TreeShellCommand extends AbstractCommand {
	/**
	 * Creates a new {@link TreeShellCommand} and sets its name and description.
	 */
	public TreeShellCommand() {
		super();

		name = "tree";
		description.add(
			"This command takes a single argument from the command line, a path to a directory.");
		description.add(
			"The content of the directory is printed to the environment, with all its files, folders");
		description.add("and subfolders in a tree like hierarchy.");
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
			if (!Files.isDirectory(path)) {
				env.writeln("The specified path is not a valid directory!");
				return ShellStatus.CONTINUE;
			}

			Files.walkFileTree(path, new TreeVisitor(env));
		} catch (IOException e) {
			System.err.println("Unable to write to the given environment.");
			System.exit(1);
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * A {@link FileVisitor} that visits the files in the given folder, and all
	 * the sub folders. The visitor print the structure of the files and folders
	 * in a tree like hierarchy.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 *
	 */
	private class TreeVisitor extends SimpleFileVisitor<Path> {
		/**
		 * The current level in the tree hierarchy.
		 */
		private int level;
		/**
		 * The {@link Environment} used for printing the results.
		 */
		private Environment env;

		/**
		 * Creates a new {@link TreeVisitor} using the given {@link Environment}
		 * .
		 * 
		 * @param env
		 *            the {@link Environment} to be used by this
		 *            {@link TreeVisitor}.
		 */
		public TreeVisitor(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
				throws IOException {

			printName(dir);
			level++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			printName(file);
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Prints the name of the given file, indented for the number of spaces
		 * as specified by the level.
		 * 
		 * @param file
		 *            the file which name should be printed.
		 */
		private void printName(Path file) {
			try {
				for (int i = 0; i < level * 2; ++i) {
					env.write(" ");
				}
				env.writeln(file.getFileName().toString());

			} catch (IOException e) {
				System.err.println("Unable to write!");
				System.exit(1);
			}
		}
	}
}
