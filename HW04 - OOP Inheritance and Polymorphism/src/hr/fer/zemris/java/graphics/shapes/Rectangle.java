package hr.fer.zemris.java.graphics.shapes;

/**
 * A Rectangle {@code GeometricShape} that has the upper left corner on the
 * coordinate (x, y) and a height and width. <br>
 * It is not allowed to create a {@code Rectangle} with width or height less or
 * equal to 0.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Rectangle extends Quadrilateral {

	/**
	 * Creates a new {@code Rectangle} with the upper left corner on the x, y
	 * coordinate as specified in the arguments, with the given width and given
	 * height.
	 * 
	 * @param x
	 *            The x coordinate of the upper left corner of the
	 *            {@code Rectangle}.
	 * @param y
	 *            The y coordinate of the upper left corner of the
	 *            {@code Rectangle}.
	 * @param width
	 *            The width of the {@code Rectangle}.
	 * @param height
	 *            The height of the {@code Rectangle}.
	 * @throws IllegalArgumentException
	 *             if the given width or height is less than 1
	 */
	public Rectangle(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
}
