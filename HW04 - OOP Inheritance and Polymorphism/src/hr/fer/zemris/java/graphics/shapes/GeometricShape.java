package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * A geometric shape that can be drawn on a raster.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public abstract class GeometricShape {
	/**
	 * Draws this {@code GeometricShape} on the {@code BWRaster} given in the
	 * argument.
	 * 
	 * @param r
	 *            the {@code BWRaster} on which this {@code GeometricShape}
	 *            should be drawn.
	 * @throws IllegalArgumentException
	 *             if the provided {@code BWRaster} is null.
	 */
	public void draw(BWRaster r) {
		if (r == null) throw new IllegalArgumentException("Raster is null!");

		int height = r.getHeight();
		int width = r.getWidth();

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				if (containsPoint(x, y)) {
					r.turnOn(x, y);
				}
			}
		}
	}

	/**
	 * Checks if this {@code GeometricShape} contains the point specified in the
	 * arguments.
	 * 
	 * @param x
	 *            the x coordinate of the point
	 * @param y
	 *            the y coordinate of the point
	 * @return a {@code Boolean} value: <strong>true</strong> if the point is
	 *         inside this {@code GeometricShape}, <strong>false</strong>
	 *         otherwise.
	 * @throws IllegalArgumentException
	 *             if the given coordinate is outside the raster.
	 */
	public abstract boolean containsPoint(int x, int y);
}
