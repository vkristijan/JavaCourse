package hr.fer.zemris.java.graphics.shapes;

/**
 * A Ellipse {@code GeometricShape} that has the center on the coordinate
 * (x, y) and a horizontal and vertical radius. <br>
 * It is not allowed to create a {@code Oval} with vertical or horizontal radius
 * less than 1.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Ellipse extends Oval {

	/**
	 * Constructs a new {@code Ellipse} with the center in the x, y coordinate as
	 * specified in the arguments, with the given horizontal and vertical
	 * radius.
	 * 
	 * @param x
	 *            The x coordinate of the center of the {@code Ellipse}.
	 * @param y
	 *            The y coordinate of the center of the {@code Ellipse}.
	 * @param horizontalRadius
	 *            The length of the horizontal radius of the {@code Ellipse}.
	 * @param verticalRadius
	 *            The length of the vertical radius of the {@code Ellipse}.
	 */
	public Ellipse(int x, int y, int horizontalRadius, int verticalRadius) {
		super(x, y, horizontalRadius, verticalRadius);
	}

}
