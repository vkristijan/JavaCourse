package hr.fer.zemris.java.fractal.complex;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.fractal.complex.Complex;

@SuppressWarnings("javadoc")
public class ComplexTest {
	private static final double PRECISION = 0.00001;
	private Complex c1;
	private Complex c2;
	private Complex c3;

	@Before
	public void initialize() {
		c1 = new Complex(1, 2);
		c2 = new Complex(5, 2);
		c3 = new Complex(42, -14);
	}

	@Test
	public void testConstructor() {
		Complex c = new Complex(1, 2);
		assertEquals(1, c.getReal(), PRECISION);
		assertEquals(2, c.getImaginary(), PRECISION);
	}

	@Test
	public void testAdd1() {
		Complex c = c1.add(c2);
		assertEquals(6, c.getReal(), PRECISION);
		assertEquals(4, c.getImaginary(), PRECISION);
	}

	@Test
	public void testAdd2() {
		Complex c = c1.add(c3);
		assertEquals(43, c.getReal(), PRECISION);
		assertEquals(-12, c.getImaginary(), PRECISION);
	}

	@Test
	public void testSub1() {
		Complex c = c1.sub(c2);
		assertEquals(-4, c.getReal(), PRECISION);
		assertEquals(0, c.getImaginary(), PRECISION);
	}

	@Test
	public void testSub2() {
		Complex c = c2.sub(c1);
		assertEquals(4, c.getReal(), PRECISION);
		assertEquals(0, c.getImaginary(), PRECISION);
	}

	@Test
	public void testModul1() {
		double expected = 2.236067977;
		assertEquals(expected, c1.module(), PRECISION);
	}

	@Test
	public void testModul2() {
		double expected = 5.38516480713;
		assertEquals(expected, c2.module(), PRECISION);
	}

	@Test
	public void testModul3() {
		double expected = 44.271887242;
		assertEquals(expected, c3.module(), PRECISION);
	}

	@Test
	public void testNegate1() {
		Complex c = c1.negate();

		assertEquals(-1, c.getReal(), PRECISION);
		assertEquals(-2, c.getImaginary(), PRECISION);
	}

	@Test
	public void testNegate2() {
		Complex c = c2.negate();

		assertEquals(-5, c.getReal(), PRECISION);
		assertEquals(-2, c.getImaginary(), PRECISION);
	}

	@Test
	public void testNegate3() {
		Complex c = c3.negate();

		assertEquals(-42, c.getReal(), PRECISION);
		assertEquals(14, c.getImaginary(), PRECISION);
	}

	@Test
	public void testMultiply1() {
		Complex c = c1.multiply(c2);

		assertEquals(1, c.getReal(), PRECISION);
		assertEquals(12, c.getImaginary(), PRECISION);
	}

	@Test
	public void testMultiply2() {
		Complex c = c2.multiply(c2);

		assertEquals(21, c.getReal(), PRECISION);
		assertEquals(20, c.getImaginary(), PRECISION);
	}

	@Test
	public void testMultiply3() {
		Complex c = c3.multiply(c2);

		assertEquals(238, c.getReal(), PRECISION);
		assertEquals(14, c.getImaginary(), PRECISION);
	}

	@Test
	public void testDivide1() {
		Complex c = c2.divide(c3);

		assertEquals(0.0928571, c.getReal(), PRECISION);
		assertEquals(0.0785714, c.getImaginary(), PRECISION);
	}

	@Test
	public void testDivide2() {
		Complex c = c1.divide(c2);

		assertEquals(0.3103448, c.getReal(), PRECISION);
		assertEquals(0.2758620, c.getImaginary(), PRECISION);
	}

	@Test
	public void testDivide3() {
		Complex c = c3.divide(c2);

		assertEquals(6.2758620, c.getReal(), PRECISION);
		assertEquals(-5.3103448, c.getImaginary(), PRECISION);
	}

	@Test
	public void testPower1() {
		Complex c = c1.power(5);

		assertEquals(41, c.getReal(), PRECISION);
		assertEquals(-38, c.getImaginary(), PRECISION);
	}

	@Test
	public void testPower2() {
		Complex c = c1.power(6);

		assertEquals(117, c.getReal(), PRECISION);
		assertEquals(44, c.getImaginary(), PRECISION);
	}

	@Test
	public void testPower3() {
		Complex c = c3.power(3);

		assertEquals(49392, c.getReal(), PRECISION);
		assertEquals(-71344, c.getImaginary(), PRECISION);
	}

	@Test
	public void testRoots1() {
		List<Complex> roots = c1.root(5);

		for (Complex c : roots) {
			Complex tmp = c.power(5);

			assertEquals(tmp.getReal(), c1.getReal(), PRECISION);
			assertEquals(tmp.getImaginary(), c1.getImaginary(), PRECISION);
		}
	}

	@Test
	public void testRoots2() {
		List<Complex> roots = c2.root(5);

		for (Complex c : roots) {
			Complex tmp = c.power(5);

			assertEquals(tmp.getReal(), c2.getReal(), PRECISION);
			assertEquals(tmp.getImaginary(), c2.getImaginary(), PRECISION);
		}
	}

	@Test
	public void testRoots3() {
		List<Complex> roots = c3.root(5);

		for (Complex c : roots) {
			Complex tmp = c.power(5);

			assertEquals(tmp.getReal(), c3.getReal(), PRECISION);
			assertEquals(tmp.getImaginary(), c3.getImaginary(), PRECISION);
		}
	}

	@Test
	public void testRoots4() {
		List<Complex> roots = c3.root(10);

		for (Complex c : roots) {
			Complex tmp = c.power(10);

			assertEquals(tmp.getReal(), c3.getReal(), PRECISION);
			assertEquals(tmp.getImaginary(), c3.getImaginary(), PRECISION);
		}
	}

	@Test
	public void testToString1() {
		String str = c1.toString();
		assertEquals("1.000 +2.000i", str);
	}

	@Test
	public void testToString2() {
		String str = Complex.ONE.toString();
		assertEquals("1.000", str);
	}

	@Test
	public void testToString3() {
		String str = Complex.IM.toString();
		assertEquals("1.000i", str);
	}

	@Test
	public void testFromString1() {
		String input = "1+i2";

		Complex expected = new Complex(1, 2);
		Complex actual = Complex.fromString(input);

		checkComplex(expected, actual);
	}

	@Test
	public void testFromString2() {
		String input = "1 - i2";

		Complex expected = new Complex(1, -2);
		Complex actual = Complex.fromString(input);

		checkComplex(expected, actual);
	}

	@Test
	public void testFromString3() {
		String input = "+i2";

		Complex expected = new Complex(0, 2);
		Complex actual = Complex.fromString(input);

		checkComplex(expected, actual);
	}

	@Test
	public void testFromString4() {
		String input = "i2";

		Complex expected = new Complex(0, 2);
		Complex actual = Complex.fromString(input);

		checkComplex(expected, actual);
	}

	@Test
	public void testFromString5() {
		String input = "i";

		Complex expected = new Complex(0, 1);
		Complex actual = Complex.fromString(input);

		checkComplex(expected, actual);
	}

	@Test
	public void testFromString6() {
		String input = "-i";

		Complex expected = new Complex(0, -1);
		Complex actual = Complex.fromString(input);

		checkComplex(expected, actual);
	}

	@Test
	public void testFromString7() {
		String input = "-1+i2";

		Complex expected = new Complex(-1, 2);
		Complex actual = Complex.fromString(input);

		checkComplex(expected, actual);
	}

	@Test
	public void testFromString8() {
		String input = "-1+i";

		Complex expected = new Complex(-1, 1);
		Complex actual = Complex.fromString(input);

		checkComplex(expected, actual);
	}

	@Test
	public void testFromString9() {
		String input = "-1";

		Complex expected = new Complex(-1, 0);
		Complex actual = Complex.fromString(input);

		checkComplex(expected, actual);
	}

	private void checkComplex(Complex expected, Complex actual) {
		assertEquals(expected.getReal(), actual.getReal(), PRECISION);
		assertEquals(expected.getImaginary(), actual.getImaginary(), PRECISION);
	}
}
