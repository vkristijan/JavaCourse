package hr.fer.zemris.java.tecaj.hw1;

/**
 * This class calculates i<sub>th</sub> number of <a href =
 * "https://en.wikipedia.org/wiki/Hofstadter_sequence#Hofstadter_Q_sequence">
 * Hofstadter's Q sequence </a>. The number i is read from the console
 * arguments.
 * 
 * @version 1.0
 */
public class HofstadterQ {
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

		long number = Long.parseLong(args[0]);

		if (number <= 0) {
			System.err.println("Wrong number must be positive!");
			return;
		}

		System.out.printf(
			"You requested calculation of %d. number of Hofstadter's Q-sequence. The requested number is %d.%n",
			number, qSequence(number));
	}

	/**
	 * Calculates the i<sub>th</sub> number of the Hofstandter's-Q sequence that
	 * is described with the following formula <img src=
	 * "https://upload.wikimedia.org/math/9/8/a/98a893a4f4457e0f29732de99cb32489.png"
	 * >. <br>
	 * 
	 * @param number
	 *            the number of Hofstadter's Q-sequence to be calculated
	 * @return the number of the Hofstadter's Q-sequence
	 */
	private static long qSequence(long number) {
		if (number <= 2)
			return 1;

		return qSequence(number - qSequence(number - 1))
				+ qSequence(number - qSequence(number - 2));
	}
}
