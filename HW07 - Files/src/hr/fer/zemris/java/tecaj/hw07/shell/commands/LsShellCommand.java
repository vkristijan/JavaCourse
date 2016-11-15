package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Prints all the content of the directory given in the argument. The output is
 * formated in such a way that every file is printed in one line, with the data
 * about the file. The output consists of 4 columns. First column indicates if
 * current object is directory (d), readable (r), writable (w) and executable
 * (x). Second column contains object size in bytes that is right aligned and
 * occupies 10 characters. Follows file creation date/time and finally file
 * name. <br>
 * <br>
 * 
 * For example, the following line: <br>
 * <code>-rwx       4096 2011-09-19 12:59:31 text.txt</code> <br>
 * would stand for a file named "text.txt" that is readable, writable and
 * executable. The file has 4096 bytes and was creates on 2011-09-19 12:59:31.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class LsShellCommand extends AbstractCommand {
	/**
	 * Creates a new {@link LsShellCommand} and sets its name and description.
	 */
	public LsShellCommand() {
		super();

		name = "ls";
		description.add("Prints all the content of the directory given in the argument.");
		description.add(
			"The output if formated in such a way that every file is printed in one line,");
		description.add(
			"with the data about the file. The output consists of 4 columns. First column");
		description.add(
			"indicates if current object is directory (d), readable (r), writable (w) and");
		description.add(
			"executable (x). Second column contains object size in bytes that is right aligned");
		description.add(
			"and occupies 10 characters. Follows file creation date/time and finally file name.");
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

			Files.walkFileTree(path, new LsVisitor(env));
		} catch (IOException e) {
			System.err.println("Unable to write to the given environment.");
			System.exit(1);
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * A {@link FileVisitor} that visits the files in the given folder. All the
	 * sub folders are skipped. The visitor print detailed information about
	 * every file visited.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 *
	 */
	private class LsVisitor extends SimpleFileVisitor<Path> {
		/**
		 * The {@link Environment} used to print the results of the file
		 * visitor.
		 */
		private Environment env;
		/**
		 * A flag checking if the initial directory was checked or not.
		 */
		private boolean firstDir;

		/**
		 * Creates a new {@link LsVisitor} using the given {@link Environment}.
		 * 
		 * @param env
		 *            the {@link Environment} to be used by this
		 *            {@link LsVisitor}.
		 */
		public LsVisitor(Environment env) {
			this.env = env;
			firstDir = true;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
				throws IOException {
			if (firstDir) {
				firstDir = false;
				return FileVisitResult.CONTINUE;
			}

			printFile(dir);
			return FileVisitResult.SKIP_SUBTREE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			printFile(file);
			return FileVisitResult.CONTINUE;
		}

		/**
		 * The output is formated in such a way that every file is printed in
		 * one line, with the data about the file. The output consists of 4
		 * columns. First column indicates if current object is directory (d),
		 * readable (r), writable (w) and executable (x). Second column contains
		 * object size in bytes that is right aligned and occupies 10
		 * characters. Follows file creation date/time and finally file name.
		 * 
		 * @param file
		 *            the file which information should be displayed.
		 */
		private void printFile(Path file) {
			try {
				env.write(Files.isDirectory(file) ? "d" : "-");
				env.write(Files.isReadable(file) ? "r" : "-");
				env.write(Files.isWritable(file) ? "w" : "-");
				env.write(Files.isExecutable(file) ? "e" : "-");
				env.write(" ");

				String size = String.valueOf(Files.size(file));
				int len = size.length();

				for (int i = len; i < 10; ++i) {
					env.write(" ");
				}
				env.write(size + " ");

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				BasicFileAttributeView faView = Files.getFileAttributeView(file,
					BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				env.write(formattedDateTime + " ");

				env.writeln(file.getFileName().toString());

			} catch (IOException e) {
				System.err.println("Unable to write to the given environment.");
				System.exit(1);
			}
		}
	}
}
