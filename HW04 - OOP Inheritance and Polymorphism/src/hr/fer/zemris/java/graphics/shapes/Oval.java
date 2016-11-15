package hr.fer.zemris.java.graphics.shapes;

/**
 * A simple oval {@code GeometricShape} that has the center on the coordinate
 * (x, y) and a horizontal and vertical radius. Depending on the implementation,
 * changing the horizontal radius may or may not change the vertical radius. The
 * same applies for the vertical radius too. <br>
 * It is not allowed to create a {@code Oval} with vertical or horizontal radius
 * less than 1.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Oval extends GeometricShape {
	/** The x coordinate of the center of the {@code Oval}. **/
	private int x;
	/** The y coordinate of the center of the {@code Oval}. **/
	private int y;
	/** The length of the horizontal radius of the {@code Oval}. **/
	private int horizontalRadius;
	/** The length of the horizontal radius of the {@code Oval}. **/
	private int verticalRadius;

	/**
	 * Constructs a new {@code Oval} with the center in the x, y coordinate as
	 * specified in the arguments, with the given horizontal and vertical
	 * radius.
	 * 
	 * @param x
	 *            The x coordinate of the center of the {@code Oval}.
	 * @param y
	 *            The y coordinate of the center of the {@code Oval}.
	 * @param horizontalRadius
	 *            The length of the horizontal radius of the {@code Oval}.
	 * @param verticalRadius
	 *            The length of the vertical radius of the {@code Oval}.
	 */
	protected Oval(int x, int y, int horizontalRadius, int verticalRadius) {
		super();
		this.x = x;
		this.y = y;
		if (horizontalRadius < 1 || verticalRadius < 1) {
			throw new IllegalArgumentException("The radius must be greater than 1");
		}
		this.horizontalRadius = horizontalRadius;
		this.verticalRadius = verticalRadius;
	}

	/**
	 * Returns the x coordinate of the center of the {@code Oval}.
	 * 
	 * @return the x coordinate of the center of the {@code Oval}.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x coordinate of the center of the {@code Oval} to the new value.
	 * 
	 * @param x
	 *            the new value for the x coordinate of the {@code Oval}.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the y coordinate of the center of the {@code Oval}.
	 * 
	 * @return the y coordinate of the center of the {@code Oval}.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y coordinate of the center of the {@code Oval} to the new value.
	 * 
	 * @param y
	 *            the new value for the x coordinate of the {@code Oval}.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns the length of the horizontal radius of the {@code Oval}.
	 * 
	 * @return the length of the horizontal radius of the {@code Oval}.
	 */
	public int getHorizontalRadius() {
		return horizontalRadius;
	}

	/**
	 * Sets the length of the horizontal radius of the {@code Oval} to a new
	 * value. IT's not allowed to make the radius less than 1.
	 * 
	 * @param horizontalRadius
	 *            the new length of the horizontal radius of the {@code Oval}.
	 * @throws IllegalArgumentException
	 *             if the provided value is less than 1.
	 */
	public void setHorizontalRadius(int horizontalRadius) {
		if (horizontalRadius < 1) {
			throw new IllegalArgumentException("The radius must be greater than 1");
		}
		this.horizontalRadius = horizontalRadius;
	}

	/**
	 * Returns the length of the vertical radius of the {@code Oval}.
	 * 
	 * @return the length of the vertical radius of the {@code Oval}.
	 */
	public int getVerticalRadius() {
		return verticalRadius;
	}

	/**
	 * Sets the length of the vertical radius of the {@code Oval} to a new
	 * value. IT's not allowed to make the radius less than 1.
	 * 
	 * @param verticalRadius
	 *            the new length of the vertical radius of the {@code Oval}.
	 * @throws IllegalArgumentException
	 *             if the provided value is less than 1.
	 */
	public void setVerticalRadius(int verticalRadius) {
		if (verticalRadius < 1) {
			throw new IllegalArgumentException("The radius must be greater than 1");
		}
		this.verticalRadius = verticalRadius;
	}

	@Override
	public boolean containsPoint(int x, int y) {
		/*
		 * Using the following formula: https://upload.wikimedia.org/math/a/9/c/
		 * a9ce1392a08f8795bd12ecfccd68c66b.png
		 */
		double xPart = (x - this.x) / ((double) horizontalRadius);
		xPart = xPart * xPart;

		double yPart = (y - this.y) / ((double) verticalRadius);
		yPart = yPart * yPart;

		return xPart + yPart < 1;
	}

}
