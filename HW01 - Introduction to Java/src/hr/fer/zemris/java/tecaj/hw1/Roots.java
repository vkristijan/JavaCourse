package hr.fer.zemris.java.tecaj.hw1;

import java.lang.Math;

/**
 * This class calculates all the roots of a complex number. <br>
 * The program accepts three command-line arguments: real part of complex
 * number, imaginary part of complex number, and required root to calculate
 * (natural number greater than 1). The program computes and prints all
 * requested roots of given complex number (also in form: real part plus
 * imaginary part).
 * 
 * @version 1.0
 */
public class Roots {

	/**
	 * This method starts the program.
	 * @param args an array of arguments from the console
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Wrong argument count!");
			return;
		}

		// complex number in form: a + b*i
		double a = Double.parseDouble(args[0]);
		double b = Double.parseDouble(args[1]);

		int n = Integer.parseInt(args[2]);

		//formula for calculation:
		//https://en.wikipedia.org/wiki/Nth_root#nth_roots
		double r = Math.sqrt(a * a + b * b);
		double theta = Math.atan2(b, a);

		r = Math.pow(r, 1.0 / n);
		System.out.printf("You requested calculation of %d. roots. Solutions are: %n", n);
		for (int i = 1; i <= n; ++i) {
			double tmpTheta = theta + 2 * Math.PI * i;
			tmpTheta /= n;
			a = Math.cos(tmpTheta) * r;
			b = Math.sin(tmpTheta) * r;

			if (b < 0) {
				System.out.printf("%d) %.2f - %.2fi%n", i, a, -b);
			} else {
				System.out.printf("%d) %.2f + %.2fi%n", i, a, b);
			}
		}
	}

}
