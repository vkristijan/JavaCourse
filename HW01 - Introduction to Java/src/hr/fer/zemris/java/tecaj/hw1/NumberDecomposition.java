package hr.fer.zemris.java.tecaj.hw1;

/**
 * The program calculates and prints the decomposition of this number onto prime
 * factors. The program accepts a single command-line argument: a natural number
 * greater than 1.
 * 
 * @version 1.0
 *
 */
public class NumberDecomposition {

	/**
	 * This method starts the program.
	 * 
	 * @param args
	 *            an array of arguments from the console
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Wrong number of arguments!");
			return;
		}

		int n = Integer.parseInt(args[0]);
		if (n <= 1) {
			System.err.println("Wrong argument! The number should be greater than 1!");
			return;
		}

		System.out.printf(
			"You requested decomposition of number %d onto prime factors. Here they are: %n",
			n);

		int prime = 2;
		int count = 0;
		while (n > 1) {
			while (n % prime == 0) {
				count++;
				n /= prime;
				System.out.printf("%d. %d%n", count, prime);
			}
			prime++;
		}

	}

}
