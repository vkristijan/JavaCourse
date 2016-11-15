package hr.fer.zemris.java.fractal.complex;

import java.util.Objects;

/**
 * This class is a complex
 * <a href="https://en.wikipedia.org/wiki/Polynomial">polynomial</a> represented
 * as a multiplication of roots. The class supports mathematics operations on
 * the polynomial, such as applying a value, finding the closest root to a given
 * {@link Complex} number and converting the polynomial to
 * {@link ComplexPolynomial} representation.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class ComplexRootedPolynomial {
	/**
	 * An array of the roots of this {@link ComplexRootedPolynomial}.
	 */
	private Complex[] roots;

	/**
	 * Creates a new {@link ComplexRootedPolynomial} with the roots given in the
	 * argument. If the length of the argument array is 0, the polynomial won't
	 * have any roots. If the user converts it to a {@link ComplexPolynomial},
	 * the result will be 0.
	 * 
	 * @param roots
	 *            the roots of this {@link ComplexRootedPolynomial}.
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		this.roots = new Complex[roots.length];
		System.arraycopy(roots, 0, this.roots, 0, roots.length);
	}

	/**
	 * Computes the value of this {@link ComplexRootedPolynomial} at the given
	 * point.
	 * 
	 * @param z
	 *            the point in which the value should be computed.
	 * @return the value of this {@link ComplexRootedPolynomial} at the given
	 *         point.
	 * @throws NullPointerException
	 *             if the given argument is null
	 * 
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z);

		Complex result = null;
		for (Complex c : roots) {
			Complex tmp = z.sub(c);

			if (result == null) {
				result = tmp;
			} else {
				result = result.multiply(tmp);
			}
		}

		if (result == null) {
			return Complex.ZERO;
		}
		return result;
	}

	/**
	 * Converts this representation to {@link ComplexPolynomial} type.
	 * 
	 * @return a new {@link ComplexPolynomial} that is equal to this
	 *         {@link ComplexRootedPolynomial}.
	 */
	public ComplexPolynomial toComplexPolynom() {
		if (roots.length == 0) {
			return new ComplexPolynomial();
		}

		ComplexPolynomial result = null;

		for (Complex c : roots) {
			if (result == null) {
				result = new ComplexPolynomial(Complex.ONE, c.negate());
			} else {
				ComplexPolynomial p = new ComplexPolynomial(Complex.ONE, c.negate());
				result = result.multiply(p);
			}
		}

		return result;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (Complex c : roots) {
			string.append("(Z - (");
			string.append(c.toString());
			string.append("))");
		}
		return string.toString();
	}

	/**
	 * Finds index of closest root for given {@link Complex} number z that is
	 * within threshold.
	 * 
	 * @param z
	 *            the given {@link Complex} number
	 * @param threshold
	 *            the given threshold
	 * @return the index of the closest root for the given {@link Complex}
	 *         number. If no such number is found, -1 will be returned. If there
	 *         are more than one number on the same distance, the one with the
	 *         smallest index will be returned.
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		int index = -1;
		double distance = threshold;

		for (int i = 0; i < roots.length; ++i) {
			double tmpDistance = complexDistance(z, roots[i]);
			if (tmpDistance < distance) {
				distance = tmpDistance;
				index = i;
			}
		}

		return index;
	}

	/**
	 * Calculates the distance between the given two {@link Complex} numbers in
	 * the complex plane.
	 * 
	 * @param c1
	 *            the first {@link Complex} number.
	 * @param c2
	 *            the second {@link Complex} number.
	 * @return the distance between the two given {@link Complex} numbers.
	 */
	private double complexDistance(Complex c1, Complex c2) {
		Complex c = c1.sub(c2);
		return c.module();
	}
}