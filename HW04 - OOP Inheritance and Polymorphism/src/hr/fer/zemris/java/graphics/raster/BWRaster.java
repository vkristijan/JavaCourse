package hr.fer.zemris.java.graphics.raster;

/**
 * This interface specifies the work of a black and white raster that can draw
 * images in black and white. The raster has it's width and height. The
 * coordinate system used for this raster starts at the point (0, 0) that is
 * located at the upper left corner of the raster and ends in the down right
 * corner on the coordinate (width, height). It is not allowed to address pixels
 * outside that. <br>
 * The raster can work in two different modes; normal mode and flip mode. <br>
 * In normal mode, the raster can turn every pixel on and off. <br>
 * In flip mode, the raster can change the state of a pixel, or turn a pixel
 * off.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public interface BWRaster {
	/**
	 * Returns the width of the raster.
	 * 
	 * @return the width of the raster.
	 */
	int getWidth();

	/**
	 * Returns the height of the raster.
	 * 
	 * @return the height of the raster.
	 */
	int getHeight();

	/**
	 * Clears all the pixels in the raster.
	 */
	void clear();

	/**
	 * Turns the pixel at coordinates (x, y) on. If flip mode is enabled, this
	 * method will change the state of the specified pixel. If it was turned on,
	 * it will be turned of, otherwise it will be turned on.
	 * 
	 * @param x
	 *            the x coordinate of the pixel.
	 * @param y
	 *            the y coordinate of the pixel.
	 * @throws IllegalArgumentException
	 *             if the given coordinate is outside the raster.
	 */
	void turnOn(int x, int y);

	/**
	 * Turns the pixel at coordinates (x, y) off.
	 * 
	 * @param x
	 *            the x coordinate of the pixel.
	 * @param y
	 *            the y coordinate of the pixel.
	 * @throws IllegalArgumentException
	 *             if the given coordinate is outside the raster.
	 */
	void turnOff(int x, int y);

	/**
	 * Enables the flip mode. In flip mode, instead of turning a pixel on, the
	 * method turnOn changes the state of the pixel. If it was turned on, it
	 * will be turned of, otherwise it will be turned on.
	 */
	void enableFlipMode();

	/**
	 * Disables the flip mode. In flip mode, instead of turning a pixel on, the
	 * method turnOn changes the state of the pixel. If it was turned on, it
	 * will be turned of, otherwise it will be turned on.
	 */
	void disableFlipMode();

	/**
	 * Checks if the specified pixel is turned on or not.
	 * 
	 * @param x
	 *            the x coordinate of the pixel.
	 * @param y
	 *            the y coordinate of the pixel.
	 * @return a {@code Boolean} value representing the state of the pixel.
	 *         <strong>True</strong> if the pixel is turned on,
	 *         <strong>false</strong> otherwise.
	 * @throws IllegalArgumentException
	 *             if the given coordinate is outside the raster.
	 */
	boolean isTurnedOn(int x, int y);
}
