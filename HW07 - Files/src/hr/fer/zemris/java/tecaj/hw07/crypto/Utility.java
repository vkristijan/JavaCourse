package hr.fer.zemris.java.tecaj.hw07.crypto;

/**
 * A utility class used for some helper methods for the {@link Crypto} program.
 * The defined methods can be used to convert a byte array into a string hex
 * representation and vice versa. There is also a method to check the number of
 * argument from the command line.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class Utility {
	/**
	 * Checks the number of arguments given from the command line.
	 * 
	 * @param args
	 *            the command line arguments
	 * @param expected
	 *            the expected number of arguments
	 */
	public static void checkArguments(String[] args, int expected) {
		if (args.length != expected) {
			System.err.println(
				expected + " arguments expected, but " + args.length + " were provided!");
			System.exit(1);
		}
	}

	/**
	 * Converts a byte array into a {@link String} representation of the
	 * hexadecimal number.
	 * 
	 * @param data
	 *            the byte array
	 * @return the created {@link String} representation of the hexadecimal
	 *         number.
	 */
	public static String bytesToHexString(byte[] data) {
		StringBuilder string = new StringBuilder();

		for (byte b : data) {
			int second = b & 0x0F;
			b >>= 4;
			int first = b & 0x0F;

			string.append(getHexChar(first));
			string.append(getHexChar(second));
		}

		return string.toString();
	}

	/**
	 * Converts the given number into a character hexadecimal representation.
	 * The maximum allowed integer is 15.
	 * 
	 * @param number
	 *            the number to be converted into a hex character.
	 * @return the hex character for this number.
	 */
	private static char getHexChar(int number) {
		if (number < 10) {
			return (char) (number + '0');
		} else {
			return (char) (number - 10 + 'a');
		}
	}

	/**
	 * Converts a {@link String} representation of a hexadecimal number into a
	 * byte array.
	 * 
	 * @param text
	 *            the {@link String} representation of the hexadecimal number
	 * @return a byte array created from the string.
	 */
	public static byte[] hextobyte(String text) {
		if (text.length() % 2 == 1) {
			System.err.println("The given string representation of a byte array is invalid!"
					+ " The string should have an even number of characters!");
			System.exit(1);
		}

		byte[] data = new byte[text.length() / 2];
		char[] chars = text.toLowerCase().toCharArray();

		for (int i = 0; i < chars.length; i += 2) {
			data[i / 2] = getHexInt(chars[i]);
			data[i / 2] <<= 4;
			data[i / 2] += getHexInt(chars[i + 1]);
		}

		return data;
	}

	/**
	 * Converts a character that is a hexadecimal digit into a integer with the
	 * value of that digit.
	 * 
	 * @param ch
	 *            the character to be converted
	 * @return the integer value of the hex digit.
	 */
	private static byte getHexInt(char ch) {
		if (ch <= '9') {
			return (byte) (ch - '0');
		} else {
			return (byte) (10 + ch - 'a');
		}
	}
}
