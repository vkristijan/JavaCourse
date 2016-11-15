package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * A simple demonstration program for the {@link PrimesCollection}. This program
 * creates a new {@code PrimesCollection} that has 5 numbers in it and prints
 * all of them.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class PrimesDemo1 {
	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5);
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}
}
