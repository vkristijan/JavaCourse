package hr.fer.zemris.java.gui.charts;

import java.util.Objects;

/**
 * A class that stores two values. It has a factory method to parse a string.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class XYValue {
	/**
	 * The X value.
	 */
	private final int x;
	/**
	 * The Y value.
	 */
	private final int y;

	/**
	 * Creates a new {@link XYValue} with the given x and y values.
	 * 
	 * @param x
	 *            the x value.
	 * @param y
	 *            the y value.
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x value.
	 * 
	 * @return the x value.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y value.
	 * 
	 * @return the y value.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Creates a new {@link XYValue} from the string given in the argument. The
	 * string should contain two integer values separated by a ',' char.
	 * 
	 * @param line
	 *            the string containing the information to create a new
	 *            {@link XYValue}.
	 * @return the new created {@link XYValue} value.
	 * 
	 * @throws NullPointerException
	 *             if the given line is null.
	 * @throws IllegalArgumentException
	 *             if the given line contains more than 2 comma separated
	 *             values, or if a value can not be parsed into an integer.
	 */
	public static XYValue fromString(String line) {
		Objects.requireNonNull(line, "The given line was null");

		String[] data = line.split(",");
		if (data.length != 2) {
			throw new IllegalArgumentException(
				"The given line is in wrong format. It is expected that the line contains 2 integer"
						+ " values separated by ',', but the line was: " + line);
		}

		int x = 0;
		int y = 0;

		try {
			x = Integer.parseInt(data[0]);
			y = Integer.parseInt(data[1]);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("The given line could not be parsed into integer values.", ex);
		}

		return new XYValue(x, y);
	}
}
