package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class ValueWrapperTests {
	@Test
	public void constructorNotNullTest(){
		ValueWrapper value = new ValueWrapper(42);
		
		assertNotNull(value);
	}
	
	@Test
	public void constructorNullTest(){
		ValueWrapper value = new ValueWrapper(null);
		
		assertNotNull(value);
		assertEquals(null, value.getValue());
	}
	
	@Test
	public void constructorIntegerTest(){
		ValueWrapper value = new ValueWrapper(42);
		
		assertNotNull(value);
		assertEquals(42, value.getValue());
	}
	
	@Test
	public void constructorDoubleTest(){
		ValueWrapper value = new ValueWrapper(13.37);
		
		assertNotNull(value);
		assertEquals(13.37, value.getValue());
	}
	
	@Test
	public void constructorIntegerStringTest(){
		ValueWrapper value = new ValueWrapper("12345");
		
		assertNotNull(value);
		assertEquals("12345", value.getValue());
	}
	
	@Test
	public void constructorDoubleStringTest(){
		ValueWrapper value = new ValueWrapper("123.45");
		
		assertNotNull(value);
		assertEquals("123.45", value.getValue());
	}
	
	@Test
	public void constructorInvalidStringTest(){
		ValueWrapper value = new ValueWrapper("123FFF45");
		assertNotNull(value);
		assertEquals("123FFF45", value.getValue());
	}
	
	@Test
	public void constructorBooleanConstructorTest(){
		ValueWrapper value = new ValueWrapper(new Boolean(false));
		assertNotNull(value);
		assertEquals(new Boolean(false), value.getValue());
	}
	
	@Test
	public void setValueTest(){
		ValueWrapper value = new ValueWrapper(null);
		
		assertNotNull(value);
		assertEquals(null, value.getValue());
		
		value.setValue(42);
		assertEquals(42, value.getValue());
		
		value.setValue(-3842);
		assertEquals(-3842, value.getValue());
		
		value.setValue(Math.PI);
		assertEquals(Math.PI, value.getValue());
		
		value.setValue("13.37");
		assertEquals("13.37", value.getValue());
		
		value.setValue("1337");
		assertEquals("1337", value.getValue());
	}
	
	@Test
	public void addOperationTest(){
		ValueWrapper value = new ValueWrapper(null);
		
		value.increment(0);
		assertEquals(0, value.getValue());
		
		value.increment(5);
		assertEquals(5, value.getValue());
		
		value.increment("45");
		assertEquals(50, value.getValue());
		
		value.increment(-32);
		assertEquals(18, value.getValue());
		
		value.increment(4.2);
		assertEquals(22.2, value.getValue());
		
		value.increment(null);
		assertEquals(22.2, value.getValue());
		
		value.increment("3.42");
		assertEquals(25.62, (Double)value.getValue(), 0.001);
		
		value.increment("1.32E2");
		assertEquals(157.62, (Double)value.getValue(), 0.001);
	}
	
	@Test
	public void subOperationTest(){
		ValueWrapper value = new ValueWrapper(null);
		
		value.decrement(0);
		assertEquals(0, value.getValue());
		
		value.decrement(5);
		assertEquals(-5, value.getValue());
		
		value.decrement("45");
		assertEquals(-50, value.getValue());
		
		value.decrement(-32);
		assertEquals(-18, value.getValue());
		
		value.decrement(4.2);
		assertEquals(-22.2, value.getValue());
		
		value.decrement("3.42");
		assertEquals(-25.62, (Double)value.getValue(), 0.001);
		
		value.decrement("1.32E2");
		assertEquals(-157.62, (Double)value.getValue(), 0.001);
		
		value.decrement(4);
		assertEquals(-161.62, (Double)value.getValue(), 0.001);
	}
	
	@Test
	public void mulOperationTest(){
		ValueWrapper value = new ValueWrapper(1);
			
		value.multiply(5);
		assertEquals(5, value.getValue());
		
		value.multiply("45");
		assertEquals(225, value.getValue());
		
		value.multiply(-32);
		assertEquals(-7200, value.getValue());
		
		value.multiply(4.2);
		assertEquals(Double.valueOf(-30240), (Double)value.getValue(), 0.001);
		
		value.multiply("3.42");
		assertEquals(-103420.8, (Double)value.getValue(), 0.001);
		
		value.multiply("1.32E2");
		assertEquals(-13651545.6, (Double)value.getValue(), 0.001);
	}
	
	@Test
	public void divOperationTest(){
		ValueWrapper value = new ValueWrapper(45);
			
		value.divide(5);
		assertEquals(9, value.getValue());
		
		value.divide("2");
		assertEquals(4, value.getValue());
		
		value.setValue(100);
		value.divide(32.2);
		assertEquals(3.10559, (Double)value.getValue(), 0.001);
		
		value.divide(4.2);
		assertEquals(0.7394, (Double)value.getValue(), 0.001);
		
		value.setValue(100);
		value.divide("3.42");
		assertEquals(29.23976, (Double)value.getValue(), 0.001);
		
		value.divide("1.32E2");
		assertEquals(0.22151, (Double)value.getValue(), 0.001);
	}
	
	@Test
	public void divideByZeroTest(){
		ValueWrapper value = new ValueWrapper(42);
		
		value.divide(0);
		assertEquals(Double.POSITIVE_INFINITY, value.getValue());
	}
	
	@Test(expected = ValueWrapperException.class)
	public void invalidValueOperatorTest(){
		ValueWrapper value = new ValueWrapper(45);
		
		value.multiply(new ValueWrapper(0));
	}
	
	@Test(expected = ValueWrapperException.class)
	public void invalidStringValueOperatorTest(){
		ValueWrapper value = new ValueWrapper(45);
		
		value.multiply("nece raditi");
	}
	
	@Test
	public void intNumCompareTest(){
		ValueWrapper value = new ValueWrapper(12);
		
		assertTrue(value.numCompare(1) > 0);
		assertTrue(value.numCompare(12) == 0);
		assertTrue(value.numCompare(122) < 0);
		
		assertTrue(value.numCompare("1") > 0);
		assertTrue(value.numCompare("12") == 0);
		assertTrue(value.numCompare("122") < 0);
	
		assertTrue(value.numCompare(1.2) > 0);
		assertEquals(12, value.getValue());
		assertTrue(value.numCompare(12.0) == 0);
		assertEquals(12, value.getValue());
		assertTrue(value.numCompare(122.3) < 0);
		assertEquals(12, value.getValue());
	}
	
	@Test
	public void doubleNumCompareTest(){
		ValueWrapper value = new ValueWrapper(12.21);
		
		assertTrue(value.numCompare(1) > 0);
		assertTrue(value.numCompare(12.21) == 0);
		assertTrue(value.numCompare(122) < 0);
		
		assertTrue(value.numCompare("1") > 0);
		assertTrue(value.numCompare("12.21") == 0);
		assertTrue(value.numCompare("122") < 0);
	
		assertTrue(value.numCompare(1.2) > 0);
		assertEquals(12.21, value.getValue());
		assertTrue(value.numCompare(12.21) == 0);
		assertEquals(12.21, value.getValue());
		assertTrue(value.numCompare(122.3) < 0);
		assertEquals(12.21, value.getValue());
	}
}
