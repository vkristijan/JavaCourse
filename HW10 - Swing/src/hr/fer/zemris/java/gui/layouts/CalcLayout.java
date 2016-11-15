package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A layout 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class CalcLayout implements LayoutManager2 {
	/**
	 * The number of rows in this layout manager.
	 */
	private static final int ROWS = 5;
	/**
	 * The number of columns in this layout manager.
	 */
	private static final int COLUMNS = 7;

	/**
	 * The padding between the components in two rows or two columns.
	 */
	private int padding;
	/**
	 * The components that should be showed in this layout manager.
	 */
	private Component[][] components;

	/**
	 * Creates a new {@link CalcLayout} with the padding equal to the one given
	 * in the argument.
	 * 
	 * @param padding
	 *            the padding between two components.
	 */
	public CalcLayout(int padding) {
		super();

		if (padding < 0) {
			throw new IllegalArgumentException(
				"The given padding is invalid. A value greater or equal than 0 was expected, but you have provided: "
						+ padding);
		}
		this.padding = padding;
		components = new Component[ROWS][COLUMNS];
	}

	/**
	 * Creates a new {@link CalcLayout} with the default padding of zero.
	 */
	public CalcLayout() {
		this(0);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		addLayoutComponent(comp, name);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLUMNS; ++j) {
				if (components[i][j] == null) continue;

				if (components[i][j].equals(comp)) {
					components[i][j] = null;
				}
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return calculateLayoutSize(parent, Component::getPreferredSize, Math::max);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return calculateLayoutSize(parent, Component::getMinimumSize, Math::max);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return calculateLayoutSize(target, Component::getMaximumSize, Math::min);
	}

	/**
	 * Calculates the dimension for the layout. The dimension is calculated
	 * using the given function and comparator.
	 * 
	 * @param parent
	 *            the parent container.
	 * @param function
	 *            the function used to get the layout size of the child
	 *            component.
	 * @param comparator
	 *            the comparator used to compare two values.
	 * @return the dimension for the layout.
	 */
	private Dimension calculateLayoutSize(
		Container parent, Function<Component, Dimension> function, BiFunction<Integer, Integer, Integer> comparator) {
		int width = 0;
		int height = 0;

		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLUMNS; ++j) {
				if (components[i][j] == null) continue;

				Dimension dim = function.apply(components[i][j]);
				if (dim == null) continue;

				width = comparator.apply(width, dim.width);
				height = comparator.apply(height, dim.height);

				if (i == 0 && j == 0) { // spans through 5 columns.
					width /= 5;
				}
			}
		}

		width *= COLUMNS;
		width += (COLUMNS * padding);

		height *= ROWS;
		height += (ROWS * padding);

		Insets insets = parent.getInsets();
		width += insets.left + insets.right;
		height += insets.top + insets.bottom;

		return new Dimension(width, height);
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints instanceof String) {
			constraints = RCPosition.fromString((String) constraints);
		}

		if (!(constraints instanceof RCPosition)) {
			throw new IllegalArgumentException(
				"The given constrains are invalid. Supported format are RCPosition or String arguments!");
		}

		RCPosition position = (RCPosition) constraints;
		int row = position.getRow() - 1;
		int column = position.getColumn() - 1;

		checkBounds(row, 0, ROWS, "row");
		checkBounds(column, 0, COLUMNS, "column");
		checkAllowed(row, column);

		components[row][column] = comp;
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets in = parent.getInsets();
		int width = parent.getWidth() - in.left - in.right;
		int height = parent.getHeight() - in.bottom - in.top;

		int cellWidth = width / COLUMNS;
		int cellHeight = height / ROWS;

		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLUMNS; ++j) {
				if (components[i][j] == null) continue;

				Component comp = components[i][j];
				int x = cellWidth * j + padding / 2;
				int y = cellHeight * i + padding / 2;
				comp.setLocation(x, y);

				if (i == 0 && j == 0) {
					comp.setSize(cellWidth * 5 - padding, cellHeight - padding);
				} else {
					comp.setSize(cellWidth - padding, cellHeight - padding);
				}
				comp.setVisible(true);

			}
		}
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	/**
	 * Checks if the given value is inside the given bounds.
	 * 
	 * @param value
	 *            the value that should be checked.
	 * @param from
	 *            the minimal value that is allowed.
	 * @param to
	 *            the maximal value that is allowed.
	 * @param name
	 *            the name of the value that is checked.
	 * @throws IndexOutOfBoundsException
	 *             if the given index is not inside the defined bounds.
	 */
	private void checkBounds(int value, int from, int to, String name) {
		if (value < from || value >= to) {
			throw new IndexOutOfBoundsException(
				"The " + name + " is expected to be between " + from + " and " + to + " but " + value + " was given!");
		}
	}

	/**
	 * Checks if the given row and column index are allowed in this layout
	 * manager.
	 * 
	 * @param row
	 *            the index of the row where the component should be displayed.
	 * @param column
	 *            the index of the column where the component should be
	 *            displayed.
	 * @throws IllegalArgumentException
	 *             if the specified cell already has an component in it, or if
	 *             the cell does not exist.
	 */
	private void checkAllowed(int row, int column) {
		if (components[row][column] != null) {
			throw new IllegalArgumentException("There is already an element in the specified field!");
		}

		if (row == 0 && column >= 1 && column <= 4) {
			throw new IllegalArgumentException(
				"The given constraint is not allowed. You have provided row 1 and column " + (column + 1)
						+ ", but the given cell does not exist.");
		}
	}
}
