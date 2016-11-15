package hr.fer.zemris.java.graphics.shapes;

/**
 * A Circle {@code GeometricShape} that has the center on the coordinate
 * (x, y) and a horizontal and vertical radius. <br>
 * It is not allowed to create a {@code Oval} with vertical or horizontal radius
 * less than 1.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Circle extends Oval {

	/**
	 * Constructs a new {@code Circle} with the center in the x, y coordinate as
	 * specified in the arguments, with the given horizontal and vertical
	 * radius.
	 * 
	 * @param x
	 *            The x coordinate of the center of the {@code Ellipse}.
	 * @param y
	 *            The y coordinate of the center of the {@code Ellipse}.
	 * @param radius
	 *            The length of the horizontal radius of the {@code Ellipse}.
	 */
	public Circle(int x, int y, int radius) {
		super(x, y, radius, radius);
	}

	/**
	 * {@inheritDoc} This method will also change the vertical radius to the same value!
	 */
	@Override
	public void setHorizontalRadius(int horizontalRadius) {
		super.setHorizontalRadius(horizontalRadius);
		super.setVerticalRadius(horizontalRadius);
	}

	/**
	 * {@inheritDoc} This method will also change the horizontal radius to the same value!
	 */
	@Override
	public void setVerticalRadius(int horizontalRadius) {
		setHorizontalRadius(horizontalRadius);
	}

	/**
	 * Sets the horizontal and vertical radius of this {@code Circle} to the one given in the
	 * argument.<br>
	 * It is not allowed to set the size to a value less or equal to 0.
	 * 
	 * @param radius
	 *            the new value for radius of this {@code Circle}
	 * 
	 * @throws IllegalArgumentException
	 *             if the given radius is less than 1
	 */
	public void setRadius(int radius) {
		setHorizontalRadius(radius);
	}

	/**
	 * Returns the radius of this {@code Circle}
	 * 
	 * @return the radius of this {@code Circle}
	 */
	public int getRadius() {
		return getHorizontalRadius();
	}
}
