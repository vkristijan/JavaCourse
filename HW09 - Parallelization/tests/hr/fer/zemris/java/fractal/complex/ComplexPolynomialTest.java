package hr.fer.zemris.java.fractal.complex;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.fractal.complex.Complex;
import hr.fer.zemris.java.fractal.complex.ComplexPolynomial;

@SuppressWarnings("javadoc")
public class ComplexPolynomialTest {
	private static final double PRECISION = 0.00001;
	private ComplexPolynomial p1;

	@Before
	public void initialize() {
		Complex c0 = new Complex(7, 2);
		Complex c1 = new Complex(2, 0);
		Complex c2 = new Complex(5, 0);
		Complex c3 = Complex.ONE;

		p1 = new ComplexPolynomial(c0, c1, c2, c3);
	}

	@Test
	public void testOrder() {
		assertEquals(3, p1.order());
	}

	@Test
	public void testDerivation() {
		p1 = p1.derive();
		assertEquals(2, p1.order());

		// expected: (21+6i)z^2+4z+5
		Complex[] factors = p1.getFactors();
		assertEquals(3, factors.length);

		checkComplex(new Complex(21, 6), factors[0]);
		checkComplex(new Complex(4, 0), factors[1]);
		checkComplex(new Complex(5, 0), factors[2]);
	}

	@Test
	public void testMultipleDerivation() {
		p1 = p1.derive();
		assertEquals(2, p1.order());
		// expected: (21+6i)z^2+4z+5

		p1 = p1.derive();
		assertEquals(1, p1.order());
		// expected: (42+12i)z+4

		Complex[] factors = p1.getFactors();
		assertEquals(2, factors.length);

		checkComplex(new Complex(42, 12), factors[0]);
		checkComplex(new Complex(4, 0), factors[1]);

		p1 = p1.derive();
		assertEquals(0, p1.order());
		// expected: (42+12i)

		factors = p1.getFactors();
		assertEquals(1, factors.length);

		checkComplex(new Complex(42, 12), factors[0]);

		p1 = p1.derive();
		assertEquals(0, p1.order());
		// expected: 0

		factors = p1.getFactors();
		assertEquals(1, factors.length);

		checkComplex(Complex.ZERO, factors[0]);

		p1 = p1.derive();
		assertEquals(0, p1.order());
		// expected: 0

		factors = p1.getFactors();
		assertEquals(1, factors.length);

		checkComplex(Complex.ZERO, factors[0]);
	}

	@Test
	public void testMultiply() {
		Complex c1 = new Complex(3, -1);
		Complex c2 = new Complex(0, 2);
		ComplexPolynomial p2 = new ComplexPolynomial(c1, c2);

		ComplexPolynomial result = p1.multiply(p2);
		assertEquals(4, result.order());

		Complex[] factors = result.getFactors();
		assertEquals(5, factors.length);

		checkComplex(new Complex(23, -1), factors[0]);
		checkComplex(new Complex(2, 12), factors[1]);
		checkComplex(new Complex(15, -1), factors[2]);
		checkComplex(new Complex(3, 9), factors[3]);
		checkComplex(new Complex(0, 2), factors[4]);
	}

	@Test
	public void applyTest() {
		Complex c = p1.apply(new Complex(1, 3));
		checkComplex(new Complex(-156, -151), c);
	}

	@Test
	public void toStringTest() {
		String expected = "(7.000 +2.000i) * Z^3 + (2.000) * Z^2 + (5.000) * Z^1 + (1.000)";
		assertEquals(expected, p1.toString());
	}

	private void checkComplex(Complex expected, Complex actual) {
		assertEquals(expected.getReal(), actual.getReal(), PRECISION);
		assertEquals(expected.getImaginary(), actual.getImaginary(), PRECISION);
	}
}
