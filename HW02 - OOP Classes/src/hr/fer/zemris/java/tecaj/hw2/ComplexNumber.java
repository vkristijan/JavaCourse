package hr.fer.zemris.java.tecaj.hw2;

/**
 * The ComplexNumber class encapsulates a complex number and does calculations
 * on it. The numbers are immutable, so that every operation creates a new
 * complex number.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class ComplexNumber {
	/**
	 * The real part of the complex number.
	 */
	private double real;
	/**
	 * The imaginary part of the complex number.
	 */
	private double imaginary;

	/**
	 * Constructs a new complex number with the real and imaginary parts equal
	 * to the real and imaginary arguments.
	 * 
	 * @param real
	 *            the real part of the complex number
	 * @param imaginary
	 *            the imaginary part of the complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		super();
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Creates a new complex number with the real part equal to the argument and
	 * without an imaginary part.
	 * 
	 * @param real
	 *            the real part of the complex number.
	 * @return the created complex number.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Creates a new complex number without the real part, and the imaginary
	 * part equal to the argument.
	 * 
	 * @param imaginary
	 *            the imaginary part of the complex number.
	 * @return the created complex number.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Creates a new complex number from the given magnitude and angle.
	 * 
	 * @param magnitude
	 *            the magnitude of the complex number.
	 * @param angle
	 *            the angle of the complex number.
	 * @return the created complex number.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double real = magnitude * Math.cos(angle);
		double imaginary = magnitude * Math.sin(angle);

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Creates a new complex number from the given string. The string should
	 * have a format of "a + bi". Whitespace characters are being ignored.
	 * 
	 * @param s
	 *            the string that represents the complex number.
	 * @return the new created complex number.
	 * @throws NumberFormatException
	 *             if the string does not contain a parsable complex number.
	 * @throws NullPointerException
	 *             if the given string is null
	 */
	public static ComplexNumber parse(String s) {
		if (s == null) {
			throw new NullPointerException("The given string is null!");
		}
		// remove all the blank spaces from s
		s = s.toLowerCase().replaceAll(" ", "");

		if (s.equals("i") || s.equals("+i")){
			return new ComplexNumber(0, 1);
		}
		if (s.equals("-i")){
			return new ComplexNumber(0, -1);
		}
		
		
		int endingPosition = 1;
		int size = s.length();

		while (endingPosition < size) {
			char ch = s.charAt(endingPosition);
			if (ch >= '0' && ch <= '9' || ch == '.') {
				endingPosition++;
			} else {
				break;
			}
		}

		double real;
		try {
			real = Double.parseDouble(s.substring(0, endingPosition));
		} catch (NumberFormatException e) {
			throw new NumberFormatException(
				"The given number could not be parsed to a Complex Number.");
		}

		if (endingPosition == size) {
			// there is no imaginary part of the complex number
			return ComplexNumber.fromReal(real);
		} else if (endingPosition == size - 1) {
			// there is no real part of the complex number
			return ComplexNumber.fromImaginary(real);
		}

		if (!s.endsWith("i")) {
			throw new NumberFormatException(
				"The given number could not be parsed to a Complex Number.");
		}

		s = s.substring(endingPosition, size - 1);
		double imaginary;

		if (s.equals("-")) {
			imaginary = -1;
		} else if (s.equals("+")) {
			imaginary = 1;
		} else {
			try {
				imaginary = Double.parseDouble(s);
			} catch (NumberFormatException e) {
				throw new NumberFormatException(
					"The given number could not be parsed to a Complex Number.");
			}
		}

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Returns the real part of the complex number.
	 * 
	 * @return the real part of the complex number.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns the imaginary part of the complex number.
	 * 
	 * @return the imaginary part of the complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns the angle of the complex number.
	 * 
	 * @return the angle of the complex number.
	 */
	public double getAngle() {
		return Math.atan2(imaginary, real);
	}

	/**
	 * Returns the magnitude of the complex number.
	 * 
	 * @return the magnitude of the complex number.
	 */
	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Sums the complex number with the complex number given in the argument.
	 * The result is stored in a new created complex number that is returned.
	 * 
	 * @param c
	 *            the complex number that is the second operand for the
	 *            operation.
	 * @return a new complex number that is the result of the operation.
	 * @throws NullPointerException
	 *             if the given argument is null.
	 */
	public ComplexNumber add(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException("The given complex number is null!");
		}
		double real = getReal() + c.getReal();
		double imaginary = getImaginary() + c.getImaginary();

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Subtracts the complex number with the complex number given in the
	 * argument. The result is stored in a new created complex number that is
	 * returned.
	 * 
	 * @param c
	 *            the complex number that is the second operand for the
	 *            operation.
	 * @return a new complex number that is the result of the operation.
	 * @throws NullPointerException
	 *             if the given argument is null.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException("The given complex number is null!");
		}
		double real = getReal() - c.getReal();
		double imaginary = getImaginary() - c.getImaginary();

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Multiplies the complex number with the complex number given in the
	 * argument. The result is stored in a new created complex number that is
	 * returned.
	 * 
	 * @param c
	 *            the complex number that is the second operand for the
	 *            operation.
	 * @return a new complex number that is the result of the operation.
	 * @throws NullPointerException
	 *             if the given argument is null.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double real = getReal() * c.getReal();
		real -= getImaginary() * c.getImaginary();

		double imaginary = getReal() * c.getImaginary();
		imaginary += c.getReal() * getImaginary();

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Divides the complex number with the complex number given in the argument.
	 * The result is stored in a new created complex number that is returned.
	 * 
	 * @param c
	 *            the complex number that is the second operand for the
	 *            operation.
	 * @return a new complex number that is the result of the operation.
	 * @throws NullPointerException
	 *             if the given argument is null.
	 */
	public ComplexNumber div(ComplexNumber c) {
		double divisor = c.getReal() * c.getReal();
		divisor += c.getImaginary() * c.getImaginary();

		double real = c.getReal() / divisor;
		double imaginary = -c.getImaginary() / divisor;
		ComplexNumber c2 = new ComplexNumber(real, imaginary);

		return mul(c2);
	}

	/**
	 * Calculates the n-th root of the complex number. The result is stored in
	 * an array of new created complex numbers that is returned.
	 * 
	 * @param n
	 *            the number of the root to be calculated.
	 * @return an array of new complex numbers that are the result of the
	 *         operation.
	 * @throws IllegalArgumentException
	 *             if the given argument is less or equal to 0.
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("The exponent must be greater than zero!");
		}
		double angle = getAngle();
		double magnitude = getMagnitude();
		magnitude = Math.pow(magnitude, 1.0 / n);

		ComplexNumber[] numbers = new ComplexNumber[n];
		for (int i = 0; i < n; ++i) {
			double tmpAngle = angle + 2 * Math.PI * i;
			tmpAngle /= n;

			numbers[i] = fromMagnitudeAndAngle(magnitude, tmpAngle);
		}

		return numbers;
	}

	/**
	 * Calculates the n-th power of the complex number. The result is stored in
	 * a new created complex number that is returned.
	 * 
	 * @param n
	 *            the power to be calculated.
	 * @return a new complex number that is the result of the operation.
	 * @throws IllegalArgumentException
	 *             if the given argument is less than 0.
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("The exponent must be a positive number.");
		}
		double magnitude = getMagnitude();
		magnitude = Math.pow(magnitude, n);

		double angle = getAngle();
		angle *= n;
		angle %= (2 * Math.PI);

		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Returns the string representation of the complex number in the form of
	 * "a + bi". The numbers are rounded on 3 decimal places.
	 */
	@Override
	public String toString() {
		if (imaginary == 0) {
			return String.format("%.3f", real);
		}
		if (real == 0) {
			return String.format("%.3fi", imaginary);
		}

		if (imaginary < 0) {
			return String.format("%.3f - %.3fi", real, -imaginary);
		} else {
			return String.format("%.3f + %.3fi", real, imaginary);
		}
	}
}
