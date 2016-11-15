package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Defines the position of a component in {@link CalcLayout}. Every position
 * defines row and column number. Both numbers are indexed from 1.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class RCPosition {
	/**
	 * The row of the component in the {@link CalcLayout}.
	 */
	private final int row;
	/**
	 * The column of the component in the {@link CalcLayout}.
	 */
	private final int column;

	/**
	 * Creates a new {@link RCPosition} with the given row and column.
	 * 
	 * @param row
	 *            the row of the component.
	 * @param column
	 *            the column of the component.
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns the row of the component in the layout.
	 * 
	 * @return the row of the component in the layout.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column of the component in the layout.
	 * 
	 * @return the column of the component in the layout.
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Converts the given string position into a {@link RCPosition}. The
	 * expected format of the string is "row,column", where the values are
	 * separated with a ',' sign.
	 * 
	 * @param position
	 *            the {@link String} representation of the {@link RCPosition}.
	 * @return {@link RCPosition} created from the given {@link String}.
	 * 
	 * @throws IllegalArgumentException
	 *             if the given {@link String} is empty, or if it could not be
	 *             parsed into a valid {@link RCPosition}.
	 */
	public static RCPosition fromString(String position) {
		Objects.requireNonNull(position, "The given constrains String is not allowed to be null!");

		position = position.replaceAll(" ", "");
		if (position.isEmpty()) {
			throw new IllegalArgumentException("The given constrains String is empty!");
		}

		int index = position.indexOf(',');
		if (index < 0) {
			throw new IllegalArgumentException("The constrains String should have two numbers separated by a comma.");
		}

		int row = 0;
		int column = 0;

		try {
			row = Integer.parseInt(position.substring(0, index));
			column = Integer.parseInt(position.substring(index + 1));
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(
				"The constrains String should only contain two comma separated numbers, but the given value could "
						+ "not be parsed. You have given: " + position);
		}

		return new RCPosition(row, column);
	}
}
