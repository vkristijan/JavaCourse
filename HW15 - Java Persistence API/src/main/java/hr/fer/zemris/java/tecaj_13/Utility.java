package hr.fer.zemris.java.tecaj_13;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Helper class containing static helper methods used by the application.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class Utility {
	/**
	 * Private constructor to make sure that the user will not create an
	 * instance of this class.
	 */
	private Utility(){
	}

	/**
	 * Creates an encrypted version of the given string using SHA-1 encryption.
	 * 
	 * @param password
	 *            the string that should be encrypted
	 * @return the encrypted string
	 */
	public static String hexEncode(String password){
		try {
			MessageDigest shaDigest = MessageDigest.getInstance("SHA-1");
			shaDigest.update(password.getBytes());
			
			byte[] data = shaDigest.digest();
			return bytesToHexString(data);
			
		} catch (NoSuchAlgorithmException e) {
			System.err.println("The algorithm 'SHA-1' is not supported on the current machine.");
		}
		return null;
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
	private static String bytesToHexString(byte[] data) {
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
}
