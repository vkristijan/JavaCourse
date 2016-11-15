package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.tecaj.hw5.db.filter.QueryFilter;

/**
 * A program that reads the information about students from a file and stores
 * them into local database. The data can then be accessed by writing queries to
 * select some of the students in the database.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class StudentDB {
	/** The database that has all the information about the students. */
	private static StudentDatabase database;
	/**
	 * The {@code InputStream} that is used to read the user input for this
	 * program.
	 */
	private static InputStream in = System.in;
	/**
	 * The {@code PrintStream} that is used to print the user output for this
	 * program.
	 */
	private static PrintStream out = System.out;
	/**
	 * The {@code PrintStream} that is used to print the error messages for this
	 * program.
	 */
	private static PrintStream err = System.err;

	/**
	 * Sets the {@code InputStream} that is used in this program to the one
	 * given in the argument.
	 * 
	 * @param in
	 *            the {@code InputStream} that should be used.
	 */
	public static void setInputStream(InputStream in) {
		StudentDB.in = in;
	}

	/**
	 * Sets the {@code PrintStream} that is used in this program to the one
	 * given in the argument.
	 * 
	 * @param out
	 *            the {@code PrintStream} that should be used.
	 */
	public static void setOutputStream(PrintStream out) {
		StudentDB.out = out;
	}

	/**
	 * Sets the {@code PrintStream} that is used in this program to the one
	 * given in the argument.
	 * 
	 * @param err
	 *            the {@code PrintStream} that should be used.
	 */
	public static void setErrorStream(PrintStream err) {
		StudentDB.err = err;
	}

	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		try {
			List<String> lines = Files.readAllLines(Paths.get("./prva.txt"),
				StandardCharsets.UTF_8);

			String[] data = lines.toArray(new String[0]);
			database = new StudentDatabase(data);
		} catch (Exception e) {
			err.println("Unable to read the given file!");
			System.exit(0);
		}

		try {
			handleInput();
		} catch (Exception e) {
			err.println("Something went wronge with the given input!");
		}
	}

	/**
	 * Reads the user input and does the actions specified by each input.
	 */
	private static void handleInput() {
		Scanner sc = new Scanner(in);
		while (true) {
			out.print(" > ");
			String line = sc.nextLine();
			line = line.trim();

			if (line.equals("exit")) {
				out.println("Goodbye!");
				sc.close();
				return;
			}

			int firstSpace = line.indexOf(' ');
			if (firstSpace == -1) {
				err.println("Unsupported commmand!");
				continue;
			}

			String command = line.substring(0, firstSpace);
			String args = line.substring(firstSpace).trim();

			List<StudentRecord> selected;

			try {
				if (command.equals("indexquery")) {
					args = args.replaceAll(" ", "");
					if (!args.startsWith("jmbag=\"") || !args.endsWith("\"")) {
						err.println("Invalid command!");
						continue;
					}

					String jmbag = args.substring(7, args.length() - 1);
					out.println("Using index for record retrieval.");

					selected = new ArrayList<>();
					StudentRecord student = database.forJMBAG(jmbag);
					if (student != null) selected.add(student);

					print(selected);

				} else if (command.equals("query")) {
					QueryFilter filter = new QueryFilter(args);
					selected = database.filter(filter);
					print(selected);

				} else {
					err.println("Unsuported command!");
				}

			} catch (Exception e) {
				err.println("The given command is invalid!");
			}
		}
	}

	/**
	 * Prints the given list of students to the user output that is specified
	 * for this program. <br>
	 * The student data will be arranged into a table so that the width of every
	 * column is equal to the maximum width of the data in that column. For
	 * example: <br>
	 * <font face=Courier new"> +============+===========+========+===+ <br>
	 * | 0000000001 | Akšamović | Marin&nbsp; | 2 | <br>
	 * | 0000000004 | Božić &nbsp;&nbsp;&nbsp;&nbsp;| Marin&nbsp; | 5 | <br>
	 * | 0000000008 | Ćurić &nbsp;&nbsp;&nbsp;&nbsp;| Marko&nbsp; | 5 | <br>
	 * | 0000000013 | Gagić &nbsp;&nbsp;&nbsp; | Mateja | 2 | <br>
	 * | 0000000014 | Gašić &nbsp;&nbsp;&nbsp; | Mirta&nbsp; | 3 | <br>
	 * | 0000000016 | Glumac &nbsp;&nbsp; | Milan&nbsp; | 5 | <br>
	 * | 0000000018 | Gužvinec &nbsp;| Matija | 3 | <br>
	 * | 0000000059 | Štruml &nbsp;&nbsp; | Marin&nbsp; | 4 | <br>
	 * +============+===========+========+===+ <br>
	 * Records selected: 8 </font>
	 * 
	 * @param selected
	 *            the list of students that should be printed.
	 */
	private static void print(List<StudentRecord> selected) {
		if (selected.isEmpty()) {
			out.println("Records selected: 0");
			return;
		}

		int maxJmbag = 0;
		int maxLast = 0;
		int maxFirst = 0;

		for (StudentRecord record : selected) {
			maxJmbag = Math.max(maxJmbag, record.getJmbag().length());
			maxLast = Math.max(maxLast, record.getLastName().length());
			maxFirst = Math.max(maxFirst, record.getFirstName().length());
		}

		String line = createLine(maxJmbag, maxLast, maxFirst);
		out.println(line);

		for (StudentRecord student : selected) {
			printStudent(student, maxJmbag, maxLast, maxFirst);
		}

		out.println(line);
		out.println("Records selected: " + selected.size());
	}

	/**
	 * Prints a single line to the user output that has all the information
	 * about a single student. The line is created depending on the sizes of the
	 * student's names and jmbag numbers.
	 * 
	 * @param student
	 *            the {@code StudentRecord} with the information about the
	 *            student to be printed
	 * @param maxJmbag
	 *            the maximum size of a jmbag number in this table.
	 * @param maxLast
	 *            the maximum size of a last name in this table.
	 * @param maxFirst
	 *            the maximum size of a first name in this table.
	 * 
	 */
	private static void printStudent(
		StudentRecord student, int maxJmbag, int maxLast, int maxFirst) {

		StringBuilder line = new StringBuilder();
		line.append("| ");
		line.append(student.getJmbag());
		for (int i = student.getJmbag().length(); i < maxJmbag; ++i) {
			line.append(' ');
		}
		line.append(" | ");

		line.append(student.getLastName());
		for (int i = student.getLastName().length(); i < maxLast; ++i) {
			line.append(' ');
		}
		line.append(" | ");

		line.append(student.getFirstName());
		for (int i = student.getFirstName().length(); i < maxFirst; ++i) {
			line.append(' ');
		}
		line.append(" | ");
		line.append(student.getFinalGrade());
		line.append(" |");

		out.println(line.toString());

	}

	/**
	 * Creates a line for the top and bottom of the table that is printed to the
	 * user output. The line is created depending on the sizes of the student's
	 * names and jmbag numbers.
	 * 
	 * @param maxJmbag
	 *            the maximum size of a jmbag number in this table.
	 * @param maxLast
	 *            the maximum size of a last name in this table.
	 * @param maxFirst
	 *            the maximum size of a first name in this table.
	 * @return the new created line that has the size to match exactly the table
	 *         with the given parameters.
	 */
	private static String createLine(int maxJmbag, int maxLast, int maxFirst) {
		StringBuilder line = new StringBuilder();
		line.append('+');
		for (int i = 0; i < maxJmbag + 2; ++i) {
			line.append('=');
		}
		line.append('+');
		for (int i = 0; i < maxLast + 2; ++i) {
			line.append('=');
		}
		line.append('+');
		for (int i = 0; i < maxFirst + 2; ++i) {
			line.append('=');
		}
		line.append('+');
		for (int i = 0; i < 3; ++i) {
			line.append('=');
		}
		line.append('+');

		return line.toString();
	}

}
