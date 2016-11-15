package hr.fer.zemris.java.fractal.complex;

import java.util.Objects;

/**
 * This class is a complex
 * <a href="https://en.wikipedia.org/wiki/Polynomial">polynomial</a> represented
 * as a <a href="https://en.wikipedia.org/wiki/Polynomial#Power_series">power
 * series</a>. The class supports mathematics operations on the polynomial, such
 * as applying a value, deriving the polynomial and multiplying it.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class ComplexPolynomial {
	/**
	 * The {@link Complex} factors of this polynomial. The factor with the
	 * smallest index is the one for the highest exponent.
	 */
	private Complex[] factors;

	/**
	 * Creates a new {@link ComplexPolynomial} with the given factors. The order
	 * of the created polynomial will be equal to the number of factors -1. If
	 * no factors are given, the polynomial will be zero.
	 * 
	 * @param factors
	 *            the factors in this polynomial.
	 */
	public ComplexPolynomial(Complex... factors) {
		if (factors.length == 0) {
			this.factors = new Complex[1];
			this.factors[0] = new Complex();
			return;
		}

		this.factors = new Complex[factors.length];
		System.arraycopy(factors, 0, this.factors, 0, factors.length);
	}

	/**
	 * Returns the order of this polynomial.
	 * 
	 * <pre>
	 * for example: (7+2i)z<sup>3</sup>+2z<sup>2</sup>+5z+1 returns 3
	 * </pre>
	 * 
	 * @return the order of this polynomial.
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Returns the factors of this {@link ComplexPolynomial}. The returned
	 * factor are a copy of the original values, and changing a factor will not
	 * change the polynomial.
	 * 
	 * @return the factors of this {@link ComplexPolynomial}.
	 */
	public Complex[] getFactors() {
		Complex[] tmpFactors = new Complex[factors.length];
		System.arraycopy(factors, 0, tmpFactors, 0, factors.length);

		return tmpFactors;
	}

	/**
	 * Computes the multiplication of this polynomial with the one given in the
	 * argument.
	 * 
	 * @param p
	 *            the other {@link ComplexPolynomial} used for the calculation.
	 * @return a new {@link ComplexPolynomial} that is equal to the
	 *         multiplication of this {@link ComplexPolynomial} with the one
	 *         from the argument.
	 * @throws NullPointerException
	 *             if the given argument is null
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Objects.requireNonNull(p);

		int thisOrder = order();
		int otherOrder = p.order();

		int order = thisOrder + otherOrder;
		Complex[] newFactors = new Complex[order + 1];

		for (int i = 0; i < factors.length; ++i) {
			for (int j = 0; j < p.factors.length; ++j) {
				if (newFactors[i + j] == null) {
					newFactors[i + j] = new Complex();
				}

				Complex tmpFactor = factors[i].multiply(p.factors[j]);
				newFactors[i + j] = newFactors[i + j].add(tmpFactor);
			}
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Computes the first derivative of this {@link ComplexPolynomial}.
	 * 
	 * <pre>
	 * for example: (7+2i)z<sup>3</sup>+2z<sup>2</sup>+5z+1 returns (21+6i)z<sup>2</sup>+4z+5
	 * </pre>
	 * 
	 * @return the derivative of this {@link ComplexPolynomial}.
	 */
	public ComplexPolynomial derive() {
		int order = order();
		Complex[] newFactor = new Complex[order];

		for (int i = 0; i < order; ++i) {
			newFactor[i] = factors[i].multiply(new Complex(order - i, 0));
		}

		return new ComplexPolynomial(newFactor);
	}

	/**
	 * Computes the value of this {@link ComplexPolynomial} at the given point.
	 * 
	 * @param z
	 *            the point in which the value should be computed.
	 * @return the value of this {@link ComplexPolynomial} at the given point.
	 * @throws NullPointerException
	 *             if the given argument is null
	 * 
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z);

		int order = order();
		Complex result = new Complex();

		for (Complex c : factors) {
			Complex tmp = z.power(order);
			order--;

			tmp = tmp.multiply(c);
			result = result.add(tmp);
		}

		return result;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		int order = order();

		for (Complex c : factors) {
			string.append("(" + c.toString() + ")");

			if (order > 0) {
				string.append(" * Z^" + order + " + ");
				order--;
			}
		}

		return string.toString();
	}
}