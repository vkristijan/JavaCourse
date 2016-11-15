package hr.fer.zemris.java.cstr;

/**
 * The <code>CString</code> class represents an immutable character string. The
 * class <code>CString</code> includes methods for examining individual
 * characters of the sequence, comparing strings, searching strings, for
 * extracting substrings, and for creating a copy of a string. <br>
 * The substring operation is made in constant time complexity, while searches
 * and comparisons are made in linear time complexity.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class CString {
	/**
	 * Array storing the characters of the <code>CString</code>. Can be shared
	 * among many different instances.
	 **/
	private final char[] data;

	/** The index of the first character in the data array. **/
	private int offset;

	/** The length of this <code>CString</code> **/
	private int length;

	/** The prime number used to calculate the hash code. **/
	private static final int prime = 3137;

	/**
	 * Allocates a new <code>CString</code> that contains the data given in the
	 * character array argument. The newly created <code>CString</code> copies
	 * the elements from the array so that any further change of the array does
	 * not affect the <code>CString</code>.
	 * 
	 * @param data
	 *            the initial value of the <code>CDsting</code>
	 */
	public CString(char[] data) {
		this(data, 0, data == null ? 0 : data.length);
	}

	/**
	 * Allocates a new <code>CString</code> that contains the data given in the
	 * character array argument. The <code>offset</code> argument is the index
	 * of the first character in the array that will be copied. The
	 * <code>length</code> argument is the number of characters that will be
	 * copied. The newly created <code>CString</code> copies the elements from
	 * the array so that any further change of the array does not affect the
	 * <code>CString</code>.
	 * 
	 * @param data
	 *            a character array containing the initial value of the
	 *            <code>CString</code>
	 * @param offset
	 *            the index of the first character to be copied
	 * @param length
	 *            the number of characters to be copied
	 * @throws ArrayIndexOutOfBoundsException
	 *             if the <code>offset</code> argument is greater than the data
	 *             length, or if there are not enough characters in the data
	 *             array as defined with the <code>length</code> argument.
	 * 
	 */
	public CString(char[] data, int offset, int length) {
		super();
		if (data == null) {
			throw new IllegalArgumentException("The given data array must not be null!");
		}
		if (offset + length > data.length) {
			throw new ArrayIndexOutOfBoundsException(
				"The offset and count index characters out of the data array.");
		}

		this.data = new char[length];
		this.offset = 0;
		this.length = length;

		System.arraycopy(data, offset, this.data, 0, length);
	}

	/**
	 * Allocates a new <code>CString</code> that contains the data given in the
	 * character array argument. The <code>offset</code> argument is the index
	 * of the first character in the array that will be copied. The
	 * <code>length</code> argument is the number of characters that will be
	 * copied. The newly created <code>CString</code> <strong>does not
	 * copy </strong> the elements from the array, allowing this construction to
	 * be made in time complexity O(1). This is used when two
	 * <code>CString</code>s share the same data array.
	 * 
	 * @param data
	 *            a character array containing the initial value of the
	 *            <code>CString</code>
	 * @param offset
	 *            the index of the first character to be copied
	 * @param length
	 *            the number of characters to be copied
	 * @throws ArrayIndexOutOfBoundsException
	 *             if the <code>offset</code> argument is greater than the data
	 *             length, or if there are not enough characters in the data
	 *             array as defined with the <code>length</code> argument.
	 * 
	 */
	private CString(int offset, int length, char[] data) {
		super();
		if (offset + length > data.length) {
			throw new ArrayIndexOutOfBoundsException(
				"The offset and count index characters out of the data array.");
		}
		this.data = data;
		this.offset = offset;
		this.length = length;
	}

	/**
	 * Allocates a new <code>CString</code> that contains the data given in the
	 * original <code>CString</code> argument. If the original
	 * <code>CString</code> uses all of the data array, this method will just
	 * use it's data array, otherwise a new array will be created with the size
	 * necessary for this <code>CString</code>.
	 * 
	 * @param original
	 *            the <code>CString</code> that should be copied.
	 * @throws NullPointerException
	 *             if the original <code>CString</code> argument is null
	 */
	public CString(CString original) {
		if (original == null) {
			throw new IllegalArgumentException("The given CString is null!");
		}

		offset = 0;
		length = original.length;

		if (original.length == original.data.length) {
			data = original.data;
		} else {
			data = original.toCharArray();
		}
	}

	/**
	 * Creates a new <code>CString</code> object which has the same character
	 * data as the given <code>String</code> object.
	 * 
	 * @param s
	 *            the <code>String</code> that should be copied.
	 * @return new <code>CString</code> object which has the same character data
	 *         as given Java's <code>String</code> object.
	 */
	public static CString fromString(String s) {
		return new CString(s.toCharArray());
	}

	/**
	 * Returns the length of the <code>CString</code>.
	 * 
	 * @return the length of the <code>CString</code>.
	 */
	public int length() {
		return length;
	}

	/**
	 * Returns the character at the given position in the <code>CString</code>.
	 * 
	 * @param index
	 *            the position of the character in the <code>CString</code>.
	 * @return the character at the given position in the <code>CString</code>.
	 * @throws IndexOutOfBoundsException
	 *             if the given index if greater than the <code>CString</code>
	 *             length
	 */
	public char charAt(int index) {
		if (index >= length) {
			throw new IndexOutOfBoundsException("The given index is too big.");
		}

		return data[offset + index];
	}

	/**
	 * Converts this string to a new character array. The newly allocated
	 * character array has length equal to the length of this
	 * <code>CString</code> and contains the character sequence represented by
	 * this <code>CString</code>.
	 * 
	 * @return a new created character array containing the character sequence
	 *         represented by this <code>CString</code>.
	 */
	public char[] toCharArray() {
		char[] array = new char[length];
		System.arraycopy(data, offset, array, 0, length);
		return array;
	}

	@Override
	public String toString() {
		return new String(data, offset, length);
	}

	/**
	 * Returns the index of the first occurrence of the character argument c. In
	 * case that there is no character equal to c in this <code>CString</code>
	 * -1 is returned.
	 * 
	 * @param c
	 *            the character to be found in this <code>CString</code>.
	 * @return index of the first occurrence of the character argument c, or -1
	 *         if the character is not found in this <code>CString</code>.
	 */
	public int indexOf(char c) {
		return indexOf(CString.fromString(String.valueOf(c)));
	}

	/**
	 * Returns the index of the first occurrence of the <code>CString</code>
	 * argument s. In case that there is no <code>CString</code> equal to s in
	 * this <code>CString</code> -1 is returned. <br>
	 * The time complexity of this method is linear on the length of this
	 * <code>CString</code>.
	 * 
	 * @param s
	 *            the <code>CString</code> to be found.
	 * @return index of the first occurrence of the <code>CString</code>
	 *         argument s, or -1 if the <code>CString</code> is not found in
	 *         this <code>CString</code>
	 */
	private int indexOf(CString s) {
		if (s.length > length) return -1;
		/*
		 * This method uses the Rabin-Karp algorithm to check if this CString
		 * contains another CString. In order for that to work, the HashCode and
		 * Equals methods have to be defined.
		 * https://en.wikipedia.org/wiki/Rabin%E2%80%93Karp_algorithm
		 */
		int other = s.hashCode();
		int hash = left(s.length).hashCode();

		if (hash == other && left(s.length).equals(s)) {
			return 0;
		}

		int power = pow(prime, s.length - 1);
		for (int i = s.length; i < length; ++i) {
			hash -= power * charAt(i - s.length);
			hash *= prime;
			hash += charAt(i);

			if (hash == other && substring(i - s.length + 1, i + 1).equals(s)) {
				return i - s.length + 1;
			}
		}
		return -1;
	}

	/**
	 * Checks if this <code>CString</code> starts with the <code>CString</code>
	 * s given as the argument.
	 * 
	 * @param s
	 *            the beginning of this <code>CString</code> that should be
	 *            checked.
	 * @return true if this <code>CString</code> starts with the given
	 *         <code>CString</code>, false otherwise.
	 */
	public boolean startsWith(CString s) {
		if (s.length > length) {
			return false;
		}
		return left(s.length).equals(s);
	}

	/**
	 * Checks if this <code>CString</code> ends with the <code>CString</code> s
	 * given as the argument.
	 * 
	 * @param s
	 *            the ending of this <code>CString</code> that should be
	 *            checked.
	 * @return true if this <code>CString</code> ends with the given
	 *         <code>CString</code>, false otherwise.
	 */
	public boolean endsWith(CString s) {
		if (s.length > length) {
			return false;
		}
		return right(s.length).equals(s);
	}

	/**
	 * Creates a new <code>CString</code> that is the substring of this
	 * <code>CString</code>, starting from the startIndex given as the argument,
	 * and ending on the endIndex given as the second argument. The startIndex
	 * is included in the substring, but the endIndex isn't. <br>
	 * This operation runs in O(1).
	 * 
	 * @param startIndex
	 *            the index of the beginning of the substring.
	 * @param endIndex
	 *            the index of the end of the end of the substring (not included
	 *            in the substring).
	 * @return a new <code>CString</code> that is the substring of this
	 *         <code>CString</code>.
	 */
	public CString substring(int startIndex, int endIndex) {
		if (endIndex < startIndex) {
			throw new IllegalArgumentException(
				"The end index must be greater than the start index.");
		}
		if (startIndex < 0) {
			throw new IllegalArgumentException("The start index must be greater than zero!");
		}
		if (endIndex > length) {
			throw new IllegalArgumentException("End index must be smaller than the length!");
		}
		return new CString(startIndex + offset, endIndex - startIndex, data);
	}

	/**
	 * Creates a new <code>CString</code> that is the substring of this
	 * <code>CString</code>, containing the first n characters of this
	 * <code>CString</code>. <br>
	 * This operation runs in O(1).
	 * 
	 * @param n
	 *            the number of letters from the beginning of the
	 *            <code>CString</code> to be copied to the new
	 *            <code>CString</code>
	 * @return a new <code>CString</code> that has the first n characters of
	 *         this <code>CString</code>.
	 */
	public CString left(int n) {
		return substring(0, n);
	}

	/**
	 * Creates a new <code>CString</code> that is the substring of this
	 * <code>CString</code>, containing the last n characters of this
	 * <code>CString</code>. <br>
	 * This operation runs in O(1).
	 * 
	 * @param n
	 *            the number of letters from the ending of the
	 *            <code>CString</code> to be copied to the new
	 *            <code>CString</code>
	 * @return a new <code>CString</code> that has the last n characters of this
	 *         <code>CString</code>.
	 */
	public CString right(int n) {
		return substring(length - n, length);
	}

	/**
	 * Checks if the given <code>CString</code> is a substring of this
	 * <code>CString</code>. <br>
	 * The time complexity of this method is linear on the length of this
	 * <code>CString</code>.
	 * 
	 * @param s
	 *            the <code>CString</code> to be found in this
	 *            <code>CString</code>.
	 * @return true if this string contains given string at any position, false
	 *         otherwise
	 */
	public boolean contains(CString s) {
		if (s == null) {
			throw new IllegalArgumentException("Null was given!");
		}
		if (s.length > length) {
			return false;
		}

		return indexOf(s) > -1;
	}

	/**
	 * Calculates the power of the given number argument. If the result is
	 * greater than 2<sup>31</sup> the result will overflow. <br>
	 * The time complexity of this method is logarithmic on the exponent.
	 * 
	 * @param number
	 *            the base
	 * @param power
	 *            the exponent
	 * @return the value of <strong>base<sup>exponent</sup></strong>
	 */
	private int pow(int number, int power) {
		if (power == 0) return 1;
		if (power == 1) return number;

		if (power % 2 == 0) {
			return pow(number * number, power / 2);
		}
		return number * pow(number, power - 1);
	}

	/**
	 * Creates a new <code>CString</code> which is concatenation of current and
	 * given <code>CString</code>.
	 * 
	 * @param s
	 *            the <code>CString</code> to be added to this
	 *            <code>CString</code>.
	 * @return a new <code>CString</code> that is the concatenation of current
	 *         and given <code>CString</code>.
	 */
	public CString add(CString s) {
		if (s == null) return this;

		int newLength = length + s.length;
		char[] data = new char[newLength];
		System.arraycopy(toCharArray(), 0, data, 0, length);
		System.arraycopy(s.toCharArray(), 0, data, length, s.length);

		return new CString(0, newLength, data);
	}

	/**
	 * Creates a new instance of <code>CString</code> where all the occurrences
	 * of the oldChar <code>char</code> in this <code>CString</code> are
	 * replaced with newChar.
	 * 
	 * @param oldChar
	 *            the <code>char</code> that should be replaced.
	 * @param newChar
	 *            the replacement for the old <code>char</code>.
	 * @return a new <code>CString</code> where every occurrence of oldChar is
	 *         replaced with newChar.
	 */
	public CString replaceAll(char oldChar, char newChar) {
		CString oldStr = CString.fromString(String.valueOf(oldChar));
		CString newStr = CString.fromString(String.valueOf(newChar));

		return replaceAll(oldStr, newStr);
	}

	/**
	 * Creates a new instance of <code>CString</code> where all the occurrences
	 * of the oldStr <code>CString</code> in this <code>CString</code> are
	 * replaced with newStr.
	 * 
	 * @param oldStr
	 *            the <code>CString</code> that should be replaced.
	 * @param newStr
	 *            the replacement for the old <code>CString</code>.
	 * @return a new <code>CString</code> where every occurrence of oldStr is
	 *         replaced with newStr.
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		if (oldStr == null || newStr == null) {
			throw new IllegalArgumentException("The old and new string must not be null!");
		}

		if (oldStr.length == 0) return replaceAll(newStr);
		int occurrences = numberOfOccurrences(oldStr);
		if (occurrences == 0) return this;

		int newLength = length + occurrences * (newStr.length - oldStr.length);
		char[] data = new char[newLength];
		CString thisString = this;
		char[] newData = newStr.toCharArray();
		int currentIndex = 0;

		while (thisString.length > 0) {
			int index = thisString.indexOf(oldStr);

			if (index > -1) {
				System.arraycopy(thisString.toCharArray(), 0, data, currentIndex, index);
				currentIndex += index;

				System.arraycopy(newData, 0, data, currentIndex, newData.length);
				currentIndex += newData.length;

				thisString = thisString.substring(index + oldStr.length, thisString.length);
			} else {
				System.arraycopy(thisString.toCharArray(), 0, data, currentIndex,
					thisString.length);
				break;
			}
		}

		return new CString(0, newLength, data);
	}

	/**
	 * Creates a new instance of <code>CString</code> where newStr is inserted
	 * between every two character of this <code>CString</code>. There is also a
	 * newStr inserted at the beginning and the end.
	 * 
	 * @param newStr
	 *            the <code>CString</code> that should be inserted.
	 * @return a new <code>CString</code> where every occurrence of oldStr is
	 *         replaced with newStr.
	 */
	private CString replaceAll(CString newStr) {
		if (newStr.length == 0) return this;

		int newLength = length * (newStr.length + 1) + 1;
		char[] data = new char[newLength];
		char[] thisData = toCharArray();
		int thisIndex = 0;
		char[] newData = newStr.toCharArray();
		int currentIndex = newData.length;

		System.arraycopy(newData, 0, data, 0, newData.length);
		for (int i = 0; i < length; ++i) {
			data[currentIndex] = thisData[thisIndex];
			thisIndex++;
			currentIndex++;

			System.arraycopy(newData, 0, data, currentIndex, newData.length);
			currentIndex += newData.length;
		}

		return new CString(0, newLength, data);
	}

	/**
	 * Calculates the number of times the <code>CString</code> occurs in this
	 * <code>CString</code>. <br>
	 * The time complexity of this method is linear on the length of this
	 * <code>CString</code>.
	 * 
	 * @param s
	 *            the <code>CString</code> to be found.
	 * @return the number of occurrences of the <code>CString</code> s in this
	 *         <code>CString</code>.
	 * 
	 */
	private int numberOfOccurrences(CString s) {
		int count = -1;
		int index = -s.length;

		CString toCheck = this;
		do {
			count++;
			toCheck = toCheck.substring(index + s.length, toCheck.length);
			index = toCheck.indexOf(s);
		} while (index > -1);

		return count;
	}

	@Override
	public int hashCode() {
		int result = 0;

		for (int i = 0; i < length; ++i) {
			result *= prime;
			result += charAt(i);
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		CString other = (CString) obj;
		if (length != other.length) return false;

		for (int i = 0; i < length; ++i) {
			if (charAt(i) != other.charAt(i)) {
				return false;
			}
		}
		return true;
	}
}
