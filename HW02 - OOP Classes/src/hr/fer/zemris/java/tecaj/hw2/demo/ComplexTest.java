package hr.fer.zemris.java.tecaj.hw2.demo;

import hr.fer.zemris.java.tecaj.hw2.ComplexNumber;

/**
 * This program demonstrates the complex number class.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class ComplexTest {
	/**
	 * Starts the program.
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		System.out.println(ComplexNumber.parse("i"));
		System.out.println(ComplexNumber.parse("+i"));
		System.out.println(ComplexNumber.parse("-i"));
		System.out.println(ComplexNumber.parse("2i"));
		System.out.println(ComplexNumber.parse("-0.125i"));
		
		System.out.println(ComplexNumber.parse("-123-0.125i"));
		System.out.println(ComplexNumber.parse("+123-0.125i"));
		
		System.out.println(ComplexNumber.parse("1"));
		System.out.println(ComplexNumber.parse("-1"));
		System.out.println(ComplexNumber.parse("2"));
		System.out.println(ComplexNumber.parse("-0.125"));
	}
}