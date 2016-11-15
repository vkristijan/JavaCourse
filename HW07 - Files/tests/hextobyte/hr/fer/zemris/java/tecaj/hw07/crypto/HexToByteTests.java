package hr.fer.zemris.java.tecaj.hw07.crypto;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

@SuppressWarnings("javadoc")
public class HexToByteTests {
	
	@Test
	public void testEmptyString(){
		String string = "";
		
		byte[] expected = new byte[0];
		byte[] actual = Utility.hextobyte(string);
		
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test1(){
		String string = "01";
		
		byte[] expected = new byte[1];
		expected[0] = 1;
		byte[] actual = Utility.hextobyte(string);
		
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test2(){
		String string = "31";
		
		byte[] expected = new byte[1];
		expected[0] = 49;
		byte[] actual = Utility.hextobyte(string);
		
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test3(){
		String string = "C4";
		
		byte[] expected = new byte[1];
		expected[0] = (byte) 196;
		byte[] actual = Utility.hextobyte(string);
		
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test4(){
		String string = "2135";
		
		byte[] expected = new byte[2];
		expected[0] = 33;
		expected[1] = 53;

		byte[] actual = Utility.hextobyte(string);
		
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test5(){
		String string = "2B35";
		
		byte[] expected = new byte[2];
		expected[0] = 43;
		expected[1] = 53;

		byte[] actual = Utility.hextobyte(string);
		
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test6(){
		String string = "FFFF";
		
		byte[] expected = new byte[2];
		expected[0] = (byte) 255;
		expected[1] = (byte) 255;

		byte[] actual = Utility.hextobyte(string);
		
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test7(){
		String string = "0000";
		
		byte[] expected = new byte[2];
		expected[0] = 0;
		expected[1] = 0;

		byte[] actual = Utility.hextobyte(string);
		
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test8(){
		String string = "9102";
		
		byte[] expected = new byte[2];
		expected[0] = (byte) 145;
		expected[1] = 2;

		byte[] actual = Utility.hextobyte(string);
		
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test9(){
		String string = "CF61";
		
		byte[] expected = new byte[2];
		expected[0] = (byte) 207;
		expected[1] = 97;

		byte[] actual = Utility.hextobyte(string);
		
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void test10(){
		String string = "2135BaF4736c";
		
		byte[] expected = new byte[6];
		expected[0] = 33;
		expected[1] = 53;
		expected[2] = (byte) 186;
		expected[3] = (byte) 244;
		expected[4] = 115;
		expected[5] = 108;

		byte[] actual = Utility.hextobyte(string);
		
		assertArrayEquals(expected, actual);
	}
}
