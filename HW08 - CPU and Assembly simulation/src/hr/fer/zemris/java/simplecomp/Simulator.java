package hr.fer.zemris.java.simplecomp;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * Simulates a computer by reading a source code from a file and executing the
 * program. The path to the program can be given through command line argument
 * or through user input.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class Simulator {
	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		String fileName;
		if (args.length > 1) {
			System.err.println("Maximum 1 argument expected!");
		}
		if (args.length == 1) {
			fileName = args[0];
		} else {
			Scanner sc = new Scanner(new CloseProtectedInputStream(System.in));
			System.out.println("Please provide path to the file with the source code.");
			fileName = sc.nextLine();
			sc.close();
		}
		if (!Files.exists(Paths.get(fileName))) {
			System.err.println("The given file does not exist!");
			return;
		}

		Computer comp = new ComputerImpl(256, 16);
		InstructionCreator creator = new InstructionCreatorImpl("hr.fer.zemris.java.simplecomp.impl.instructions");

		try {
			ProgramParser.parse(fileName, comp, creator);
			ExecutionUnit exec = new ExecutionUnitImpl();
			exec.go(comp);
		} catch (Exception e) {
			System.err.println("Error during execution!");
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
	}
}
