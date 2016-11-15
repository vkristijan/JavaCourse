package hr.fer.zemris.java.fractal;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.fractal.complex.Complex;
import hr.fer.zemris.java.fractal.complex.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;

/**
 * This program is used to calculate and draw fractals. The supported fractal is
 * <a href="http://en.wikipedia.org/wiki/Newton_fractal">Newton fractal</a>.<br>
 * At the beginning, the user has to input the complex roots of a polynomial
 * used to calculate the fractal. The roots are inputed using standard system
 * input stream. To mark the end of input, type "done".
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class Newton {
	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		Scanner sc = new Scanner(System.in);
		List<Complex> roots = new LinkedList<>();
		int counter = 1;
		while (true) {
			System.out.print("Root " + counter++ + "> ");
			String line = sc.nextLine();
			if (line == null) break;

			line = line.trim();
			if (line.isEmpty()) continue;
			if (line.equals("done")) break;

			try {
				roots.add(Complex.fromString(line));
			} catch (NumberFormatException exc) {
				System.err.println("The given input could not be recognised as a complex number. The supported"
						+ "number format is 'a+ib', but you have provided " + line);
				System.err.println("The givn input will be ignored, please try again!");
				counter--;
			}
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		sc.close();

		Complex[] data = new Complex[roots.size()];
		roots.toArray(data);
		FractalViewer.show(new NewtonFractalProduer(new ComplexRootedPolynomial(data)));
	}

}
