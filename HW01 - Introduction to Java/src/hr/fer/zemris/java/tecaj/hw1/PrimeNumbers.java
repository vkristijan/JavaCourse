package hr.fer.zemris.java.tecaj.hw1;

/**
 * This class calculates the first n prime numbers and prints them to the
 * standard output. The number n is the argument from the command line.
 * 
 * @version 1.0
 */
public class PrimeNumbers {
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
		if (n <= 0) {
			System.err.println("Wrong argument! The number should be greater than 0!");
			return;
		}

		System.out.printf("You requested calculation of %d prime numbers. Here they are: %n",
			n);
		int count = 0;
		int number = 1;
		while (count < n) {
			number++;
			if (isPrime(number)) {
				count++;
				System.out.printf("%d. %d%n", count, number);
			}
		}
	}

	/**
	 * Checks if the given number is prime or not. 
	 * @param x the number that should be checked
	 * @return a boolean value: <br>
	 * <strong> true </strong> if the number is prime <br>
	 * <strong> false </strong> if the number is not prime <br>
	 */
	private static boolean isPrime(int x) {
		int n = (int) Math.sqrt(x);
		for (int i = 2; i <= n; ++i) {
			if (x % i == 0) {
				return false;
			}
		}
		return true;
	}
}
