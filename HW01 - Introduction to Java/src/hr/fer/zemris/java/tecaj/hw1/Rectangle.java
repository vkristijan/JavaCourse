package hr.fer.zemris.java.tecaj.hw1;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * This class reads two numbers that represent the width and height of a
 * rectangle and calculates the area and perimeter. The numbers can be read
 * either from standard input or provided as arguments through the console.
 * 
 * @version 1.0
 *
 */
public class Rectangle {
	/**
	 * This method reads a number from the provided stream. This method also
	 * checks the value of the number, and writes a message if the number is
	 * incorrect. In that case the user will be asked to input the number once
	 * again..
	 * 
	 * @param parameter
	 *            the name of the parameter to be read
	 * @param reader
	 *            the stream that should be used to read the number
	 * @return the number that the user inputed
	 * @throws IOException
	 *             If an I/O error
	 */
	private static double read(String parameter, BufferedReader reader) throws IOException {
		double number;

		while (true) {
			String line;
			System.out.printf("Please provide %s: ", parameter);
			line = reader.readLine();

			if (line == null) {
				System.err.println("Input stream is closed.");
				System.exit(0);
			}
			line = line.trim();
			if (line.isEmpty()) {
				System.err.println("Nothing was given.");
				continue;
			}

			number = Double.parseDouble(line);
			if (number < 0) {
				System.err.println("The " + parameter + " is negative.");
				continue;
			}

			return number;
		}
	}

	/**
	 * This method starts the program.
	 * 
	 * @param args
	 *            an array of arguments from the console
	 * @throws IOException
	 *             If an I/O error
	 */
	public static void main(String[] args) throws IOException {
		double width, height;
		if (args.length > 0) {
			if (args.length != 2) {
				System.err.println("Invalid number of arguments was provided.");
				return;
			}

			width = Double.parseDouble(args[0]);
			height = Double.parseDouble(args[1]);
		} else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			width = read("width", reader);
			height = read("height", reader);
			reader.close();
		}

		double area = height * width;
		double perimeter = 2 * (height + width);
		System.out.printf(
			"You have specified a rectangle with width %.1f and height %.1f. Its area is %.1f and its perimeter is %.1f.%n",
			width, height, area, perimeter);
	}
}
