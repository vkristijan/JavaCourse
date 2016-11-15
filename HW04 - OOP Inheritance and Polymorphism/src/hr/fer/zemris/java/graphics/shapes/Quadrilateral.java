package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * A simple quadrilateral {@code GeometricShape} that has the upper left corner
 * on the coordinate (x, y) and a height and width. Depending on the
 * implementation, changing the width may or may not change the height. The same
 * applies for the height too. <br>
 * It is not allowed to create a {@code Quadrilateral} with width or height less
 * or equal to 0.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Quadrilateral extends GeometricShape {
	/**
	 * The x coordinate of the upper left corner of the {@code Quadrilateral}.
	 **/
	private int x;
	/**
	 * The y coordinate of the upper left corner of the {@code Quadrilateral}.
	 **/
	private int y;
	/** The width of the {@code Quadrilateral}. **/
	private int width;
	/** The height of the {@code Quadrilateral}. **/
	private int height;

	/**
	 * Constructs a new {@code Quadrilateral}with the upper left corner on the
	 * x, y coordinate as specified in the arguments, with the given width and
	 * given height.
	 * 
	 * @param x
	 *            The x coordinate of the upper left corner of the
	 *            {@code Quadrilateral}.
	 * @param y
	 *            The y coordinate of the upper left corner of the
	 *            {@code Quadrilateral}.
	 * @param width
	 *            The width of the {@code Quadrilateral}.
	 * @param height
	 *            The height of the {@code Quadrilateral}.
	 * @throws IllegalArgumentException
	 *             if the given width or height is less than 1
	 */
	protected Quadrilateral(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;

		if (width <= 0) {
			throw new IllegalArgumentException("Width must be greater than 0!");
		}
		this.width = width;

		if (height <= 0) {
			throw new IllegalArgumentException("Height must be greater than 0!");
		}
		this.height = height;
	}

	/**
	 * Returns the x coordinate of the upper left corner.
	 * 
	 * @return the x coordinate of the upper left corner.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x coordinate of the upper left corner to the value given in the
	 * argument.
	 * 
	 * @param x
	 *            the new value for the upper x corner.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the y coordinate of the upper left corner.
	 * 
	 * @return the y coordinate of the upper left corner.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y coordinate of the upper left corner to the value given in the
	 * argument.
	 * 
	 * @param y
	 *            the new value for the upper x corner.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns the width of this {@code Quadrilateral}
	 * 
	 * @return the width of this {@code Quadrilateral}
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width of this {@code Quadrilateral} to the one given as the
	 * argument.<br>
	 * It is not allowed to set the width to a value less or equal to 0.
	 * 
	 * @param width
	 *            the new value for the width of this {@code Quadrilateral}
	 * 
	 * @throws IllegalArgumentException
	 *             if the given width is less than 1
	 */
	public void setWidth(int width) {
		if (width <= 0) {
			throw new IllegalArgumentException("Width must be greater than 0!");
		}
		this.width = width;
	}

	/**
	 * Returns the height of this {@code Quadrilateral}
	 * 
	 * @return the height of this {@code Quadrilateral}
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of this {@code Quadrilateral} to the one given as the
	 * argument.<br>
	 * It is not allowed to set the height to a value less or equal to 0.
	 * 
	 * @param height
	 *            the new value for the height of this {@code Quadrilateral}
	 * 
	 * @throws IllegalArgumentException
	 *             if the given width is less than 1
	 */
	public void setHeight(int height) {
		if (height <= 0) {
			throw new IllegalArgumentException("Height must be greater than 0!");
		}
		this.height = height;
	}

	@Override
	public boolean containsPoint(int x, int y) {
		if (x < this.x) return false;
		if (x >= this.x + width) return false;
		if (y < this.y) return false;
		if (y >= this.y + height) return false;

		return true;
	}

	@Override
	public void draw(BWRaster r) {
		int startX = Math.max(this.x, 0);
		int endX = Math.min(this.x + width, r.getWidth());

		int startY = Math.max(this.y, 0);
		int endY = Math.min(this.y + height, r.getHeight());

		for (int x = startX; x < endX; ++x) {
			for (int y = startY; y < endY; ++y) {
				r.turnOn(x, y);
			}
		}
	}
}
