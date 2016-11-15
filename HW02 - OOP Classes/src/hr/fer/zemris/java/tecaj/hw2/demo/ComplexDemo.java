package hr.fer.zemris.java.tecaj.hw2.demo;

import hr.fer.zemris.java.tecaj.hw2.ComplexNumber;

/**
 * This program is a simple demonstration for the complex number class.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class ComplexDemo {
	/**
	 * Starts the program.
	 * 
	 * @param args
	 *            the command line arguments.
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2)
				.power(3).root(2)[1];
		System.out.println(c3);
	}
}
