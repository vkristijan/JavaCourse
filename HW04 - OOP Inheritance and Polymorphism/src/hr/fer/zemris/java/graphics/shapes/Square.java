package hr.fer.zemris.java.graphics.shapes;

/**
 * A Square {@code GeometricShape} that has the upper left corner on the
 * coordinate (x, y) and a height and width. <br>
 * It is not allowed to create a {@code Square} with size less or
 * equal to 0.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Square extends Quadrilateral {

	/**
	 * Creates a new {@code Square} with the upper left corner on the x, y
	 * coordinate as specified in the arguments, with the given width and given
	 * height.
	 * 
	 * @param x
	 *            The x coordinate of the upper left corner of the
	 *            {@code Square}.
	 * @param y
	 *            The y coordinate of the upper left corner of the
	 *            {@code Square}.
	 * @param size
	 *            The width of the {@code Square}.
	 * @throws IllegalArgumentException
	 *             if the given width or height is less than 1
	 */
	public Square(int x, int y, int size) {
		super(x, y, size, size);
	}

	/**
	 * {@inheritDoc} This method will also change the width to the same value!
	 */
	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		super.setWidth(height);
	}

	/**
	 * {@inheritDoc} This method will also change the height to the same value!
	 */
	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		super.setHeight(width);
	}

	/**
	 * Sets the width and height of this {@code Square} to the one given in the
	 * argument.<br>
	 * It is not allowed to set the size to a value less or equal to 0.
	 * 
	 * @param size
	 *            the new value for the width and height of this {@code Square}
	 * 
	 * @throws IllegalArgumentException
	 *             if the given size is less than 1
	 */
	public void setSize(int size) {
		setWidth(size);
	}

	/**
	 * Returns the size of this {@code Square}
	 * 
	 * @return the size of this {@code Square}
	 */
	public int getSize() {
		return getWidth();
	}
}
