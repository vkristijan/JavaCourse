package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * A simple demonstration program for the {@link PrimesCollection}. This program
 * creates a new {@code PrimesCollection} that has 2 numbers in it and prints
 * the <a href="https://en.wikipedia.org/wiki/Cartesian_product">Cartesian
 * product</a>.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class PrimesDemo2 {
	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);

		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
