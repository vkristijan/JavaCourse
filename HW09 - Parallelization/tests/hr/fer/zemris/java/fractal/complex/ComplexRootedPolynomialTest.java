package hr.fer.zemris.java.fractal.complex;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.fractal.complex.Complex;
import hr.fer.zemris.java.fractal.complex.ComplexPolynomial;
import hr.fer.zemris.java.fractal.complex.ComplexRootedPolynomial;

@SuppressWarnings("javadoc")
public class ComplexRootedPolynomialTest {
	private static final double PRECISION = 0.0001;
	private ComplexRootedPolynomial p;

	@Before
	public void init() {
		Complex c1 = new Complex(1, 2);
		Complex c2 = new Complex(3, 1);
		Complex c3 = new Complex(-1, 2);

		p = new ComplexRootedPolynomial(c1, c2, c3);
	}

	@Test
	public void testToString() {
		String expected = "(Z - (1.000 +2.000i))(Z - (3.000 +1.000i))(Z - (-1.000 +2.000i))";
		assertEquals(expected, p.toString());
	}

	@Test
	public void testToCmplexPolynomial() {
		ComplexPolynomial p1 = p.toComplexPolynom();
		assertNotNull(p1);

		assertEquals(3, p1.order());
		Complex[] factors = p1.getFactors();
		checkComplex(Complex.ONE, factors[0]);
		checkComplex(new Complex(-3, -5), factors[1]);
		checkComplex(new Complex(-9, 12), factors[2]);
		checkComplex(new Complex(15, 5), factors[3]);

	}

	@Test
	public void testApply() {
		Complex c = new Complex(1, 2);
		Complex c1 = p.apply(c);

		checkComplex(Complex.ZERO, c1);
	}

	@Test
	public void testIndexOfClosest() {
		int index = p.indexOfClosestRootFor(Complex.ONE, 0);
		assertEquals(-1, index);
	}

	@Test
	public void testIndexOfClosest2() {
		int index = p.indexOfClosestRootFor(Complex.ONE, 2.5);
		assertEquals(0, index);
	}

	private void checkComplex(Complex expected, Complex actual) {
		assertEquals(expected.getReal(), actual.getReal(), PRECISION);
		assertEquals(expected.getImaginary(), actual.getImaginary(), PRECISION);
	}
}
