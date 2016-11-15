package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * This {@link ShellCommand} prints a hex representation of the file to the
 * given {@link Environment}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class HexdumpShellCommand extends AbstractCommand {
	/**
	 * Creates a new {@link HexdumpShellCommand} and sets its name and
	 * description.
	 */
	public HexdumpShellCommand() {
		super();

		name = "hexdump";
		description.add("Prints the hex-output of the file.");
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
			if (Files.isDirectory(path)) {
				env.writeln("The specified path is not a valid file!");
				return ShellStatus.CONTINUE;
			}

			printFile(env, path);
		} catch (IOException e) {
			System.err.println("Unable to write to the given environment.");
			System.exit(1);
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Prints the given file in a hex representation.
	 * 
	 * @param env
	 *            the {@link Environment} to which the file should be printed.
	 * @param path
	 *            the path of the file
	 * @throws IOException
	 *             if it is not possible to print the file.
	 */
	private void printFile(Environment env, Path path) throws IOException {
		try (BufferedInputStream br = new BufferedInputStream(
			new FileInputStream(path.toFile()))) {

			int count = 0;
			int totalCount = 0;
			byte[] buffer = new byte[16];
			while ((count = br.read(buffer)) != -1) {
				String offset = String.format("%08x", totalCount);
				totalCount += 16;
				env.write(offset + ": ");

				for (int i = 0; i < 16; ++i) {
					if (i < count) {
						env.write(getHex(buffer[i]));
					} else {
						env.write("  ");
					}

					if (i == 7) {
						env.write("|");
					} else {
						env.write(" ");
					}
				}

				env.write("|");
				for (int i = 0; i < count; ++i) {
					char ch = (char) buffer[i];
					if (ch < 32 || ch > 127) {
						ch = '.';
					}
					env.write(String.valueOf(ch));
				}

				env.writeln("");
			}
		}
	}

	/**
	 * Converts the given byte to a hex string representation.
	 * 
	 * @param b
	 *            the byte to be converted.
	 * @return the hex representation of the byte.
	 */
	private String getHex(byte b) {
		StringBuilder string = new StringBuilder();

		int second = b & 0x0F;
		b >>= 4;
		int first = b & 0x0F;

		string.append(getHexChar(first));
		string.append(getHexChar(second));

		return string.toString();
	}

	/**
	 * Converts the given number into a character hexadecimal representation.
	 * The maximum allowed integer is 15.
	 * 
	 * @param number
	 *            the number to be converted into a hex character.
	 * @return the hex character for this number.
	 */
	private static char getHexChar(int number) {
		if (number < 10) {
			return (char) (number + '0');
		} else {
			return (char) (number - 10 + 'a');
		}
	}

}
