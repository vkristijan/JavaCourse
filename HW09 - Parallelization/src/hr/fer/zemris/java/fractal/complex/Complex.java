package hr.fer.zemris.java.fractal.complex;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * The Complex class encapsulates a complex number and does calculations on it.
 * The numbers are immutable, so that every operation creates a new complex
 * number. The supported operations are
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Complex {
	/**
	 * A constant defining a {@link Complex} number that is equal to: <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;<code>0 + 0i</code>
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * A constant defining a {@link Complex} number that is equal to: <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;<code>1 + 0i</code>
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * A constant defining a {@link Complex} number that is equal to: <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;<code>-1 + 0i</code>
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * A constant defining a {@link Complex} number that is equal to: <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;<code>0 + 1i</code>
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * A constant defining a {@link Complex} number that is equal to: <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;<code>0 - 1i</code>
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * The real part of the complex number.
	 */
	private final double real;
	/**
	 * The imaginary part of the complex number.
	 */
	private final double imaginary;

	/**
	 * Creates a new {@link Complex} number with the real part equal and the
	 * imaginary part equal to zero.
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Creates a new {@link Complex} number with the real part equal to the
	 * first number in the argument, and the imaginary part equal to the second
	 * number in the argument.
	 * 
	 * @param re
	 *            the real part of the complex number
	 * @param im
	 *            the imaginary part of the complex number
	 */
	public Complex(double re, double im) {
		this.real = re;
		this.imaginary = im;
	}

	/**
	 * Factory method used to create a new {@link Complex} number from a
	 * <a href="https://en.wikipedia.org/wiki/Complex_number#Polar_form">polar
	 * form</a>.
	 * 
	 * @param module
	 *            the module of the {@link Complex} number.
	 * @param angle
	 *            the angle of the {@link Complex} number.
	 * @return a new {@link Complex} number created from the module and angle.
	 */
	private static Complex complexFromPolar(double module, double angle) {
		double real = module * Math.cos(angle);
		double imaginary = module * Math.sin(angle);

		return new Complex(real, imaginary);
	}

	/**
	 * Returns the real art of this {@link Complex} number.
	 * 
	 * @return the real art of this {@link Complex} number.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns the imaginary art of this {@link Complex} number.
	 * 
	 * @return the imaginary art of this {@link Complex} number.
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns the <a href=
	 * "https://en.wikipedia.org/wiki/Complex_number#Absolute_value_and_argument">
	 * module</a> or absolute value of the complex number.
	 * 
	 * @return the module of this complex number.
	 */
	public double module() {
		return Math.hypot(real, imaginary);
	}

	/**
	 * Calculates the multiplication of this {@link Complex} number with the
	 * other one given in the argument. The result is a new {@link Complex} that
	 * is returned.
	 * 
	 * @param c
	 *            the other {@link Complex} number for the operation.
	 * @return a new {@link Complex} number equal to the multiplication of this
	 *         number and the one given as the argument.
	 * @throws NullPointerException
	 *             if the argument was null
	 */
	public Complex multiply(Complex c) {
		Objects.requireNonNull(c);

		double real = this.real * c.real - this.imaginary * c.imaginary;
		double imaginary = this.real * c.imaginary + this.imaginary * c.real;

		return new Complex(real, imaginary);
	}

	/**
	 * Calculates the quotient of this {@link Complex} number with the other one
	 * given in the argument. The result is a new {@link Complex} that is
	 * returned.
	 * 
	 * @param c
	 *            the other {@link Complex} number for the operation.
	 * @return a new {@link Complex} number equal to the quotient of this number
	 *         and the one given as the argument.
	 * @throws NullPointerException
	 *             if the argument was null
	 */
	public Complex divide(Complex c) {
		Objects.requireNonNull(c);

		double divisor = c.real * c.real + c.imaginary * c.imaginary;
		double real = this.real * c.real + this.imaginary * c.imaginary;
		real /= divisor;

		double imaginary = this.imaginary * c.real - this.real * c.imaginary;
		imaginary /= divisor;

		return new Complex(real, imaginary);
	}

	/**
	 * Calculates the sum of this {@link Complex} number with the other one
	 * given in the argument. The result is a new {@link Complex} that is
	 * returned.
	 * 
	 * @param c
	 *            the other {@link Complex} number for the operation.
	 * @return a new {@link Complex} number equal to the sum of this number and
	 *         the one given as the argument.
	 * @throws NullPointerException
	 *             if the argument was null
	 */
	public Complex add(Complex c) {
		Objects.requireNonNull(c);

		double real = this.real + c.real;
		double imaginary = this.imaginary + c.imaginary;

		return new Complex(real, imaginary);
	}

	/**
	 * Calculates the difference of this {@link Complex} number with the other
	 * one given in the argument. The result is a new {@link Complex} that is
	 * returned.
	 * 
	 * @param c
	 *            the other {@link Complex} number for the operation.
	 * @return a new {@link Complex} number equal to the difference of this
	 *         number and the one given as the argument.
	 * @throws NullPointerException
	 *             if the argument was null
	 */
	public Complex sub(Complex c) {
		Objects.requireNonNull(c);

		double real = this.real - c.real;
		double imaginary = this.imaginary - c.imaginary;

		return new Complex(real, imaginary);
	}

	/**
	 * Creates a new {@link Complex} number that is the negative of this
	 * {@link Complex} number.
	 * 
	 * @return a new {@link Complex} number that is the negative of this
	 *         {@link Complex} number.
	 */
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}

	/**
	 * Calculates the angle of this {@link Complex} number in
	 * <a href="https://en.wikipedia.org/wiki/Complex_number#Polar_form">polar
	 * form.</a>
	 * 
	 * @return the angle of this {@link Complex} number in <a href=
	 *         "https://en.wikipedia.org/wiki/Complex_number#Polar_form">polar
	 *         form.</a>
	 */
	private double angle() {
		return Math.atan2(imaginary, real);
	}

	/**
	 * Calculates the n<sup>th</sup> power of this {@link Complex} number.
	 * 
	 * @param n
	 *            the exponent of this complex number.
	 * @return the power of the calculation.
	 */
	public Complex power(int n) {
		double angle = angle();
		double module = module();

		module = Math.pow(module, n);
		angle = angle * n;

		return complexFromPolar(module, angle);
	}

	/**
	 * Calculates all the n<sup>th</sup> roots of this {@link Complex} number.
	 * The result is a {@link List} of {@link Complex} numbers.
	 * 
	 * @param n
	 *            the power of the root.
	 * @return a {@link List} of {@link Complex} number that are the n
	 *         <sup>th</sup> root of this number.
	 */
	public List<Complex> root(int n) {
		double angle = angle();
		double module = module();

		module = Math.pow(module, 1.0 / n);
		List<Complex> roots = new LinkedList<>();

		for (int i = 0; i < n; ++i) {
			double newAngle = angle + 2.0 * Math.PI * i;
			newAngle /= n;

			roots.add(complexFromPolar(module, newAngle));
		}
		return roots;
	}

	@Override
	public String toString() {
		if (real == 0 && imaginary != 0) {
			return String.format("%.3fi", imaginary);
		}
		if (imaginary == 0) {
			return String.format("%.3f", real);
		}
		return String.format("%.3f %+.3fi", real, imaginary);
	}

	/**
	 * Creates a new {@link Complex} number from the given string argument. The
	 * supported number format is "a + ib". It is allowed to provide just the
	 * real part or just the imaginary one. IT is also allowed to write just "i"
	 * without the b number, where it is assumed that b is 1.
	 * 
	 * @param line
	 *            the string containing the {@link Complex} number.
	 * @return the new created {@link Complex} number.
	 * @throws NullPointerException
	 *             if the given line is null
	 * @throws NumberFormatException
	 *             if the given line could not be parsed into a {@link Complex}
	 *             number.
	 */
	public static Complex fromString(String line) {
		Objects.requireNonNull(line);

		line = line.trim();
		line = line.replaceAll(" ", "");
		int index = line.indexOf("i");

		double real = 0;
		double imaginary = 0;
		try {

			if (index == -1) {
				real = Double.parseDouble(line);
				return new Complex(real, imaginary);
			}

			if (index > 1) {
				real = Double.parseDouble(line.substring(0, index - 1));
			}

			line = line.replace("i", "");
			if (index > 0) {
				line = line.substring(index - 1);
			}

			if (line.isEmpty() || line.equals("+")) {
				imaginary = 1;
			} else if (line.equals("-")) {
				imaginary = -1;
			} else {
				imaginary = Double.parseDouble(line);
			}
		} catch (NumberFormatException ex) {
			throw new NumberFormatException(
				"The given string is in an unsupported form. The supported form is 'a + ib', but " + line
						+ " was given.");
		}
		return new Complex(real, imaginary);
	}
}