package hr.fer.zemris.java.graphics.raster;

/**
 * A black and white raster that can draw images in black and white. The raster
 * has it's width and height. The coordinate system used for this raster starts
 * at the point (0, 0) that is located at the upper left corner of the raster
 * and ends in the down right corner on the coordinate (width, height). It is
 * not allowed to address pixels outside that. <br>
 * The raster can work in two different modes; normal mode and flip mode. <br>
 * In normal mode, the raster can turn every pixel on and off. <br>
 * In flip mode, the raster can change the state of a pixel, or turn a pixel
 * off.
 * @see BWRaster
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class BWRasterMem implements BWRaster {
	/** The width of the raster. **/
	private final int width;
	/** The height of the raster. **/
	private final int height;
	/**
	 * Defines the mode of the raster. If set to true, the raster is in
	 * flipMode, where the turnOn method changes the current state of the pixel.
	 * If this is set to false, the turnOn method will just turn the pixel on.
	 **/
	private boolean flipMode;

	/** Matrix containing the data of every pixel in this raster. **/
	private boolean[][] imageData;

	/**
	 * Creates a new raster with the given width and height.
	 * 
	 * @param width
	 *            the width of the raster.
	 * @param height
	 *            the height of the raster.
	 * @throws IllegalArgumentException
	 *             if the width or height are less than zero.
	 */
	public BWRasterMem(int width, int height) {
		super();
		if (width <= 0) throw new IllegalArgumentException("Width must be posizitve!");
		if (height <= 0) throw new IllegalArgumentException("Height must be posizitve!");

		this.width = width;
		this.height = height;

		imageData = new boolean[width][height];
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void clear() {
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				imageData[i][j] = false;
			}
		}
	}

	@Override
	public void turnOn(int x, int y) {
		checkCoordinate(x, y);
		if (flipMode) {
			imageData[x][y] = !imageData[x][y];
		} else {
			imageData[x][y] = true;
		}
	}

	@Override
	public void turnOff(int x, int y) {
		checkCoordinate(x, y);
		imageData[x][y] = false;
	}

	@Override
	public void enableFlipMode() {
		flipMode = true;
	}

	@Override
	public void disableFlipMode() {
		flipMode = false;
	}

	@Override
	public boolean isTurnedOn(int x, int y) {
		checkCoordinate(x, y);
		return imageData[x][y];
	}

	/**
	 * Checks if the given x and y are a valid coordinate in this raster.
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @throws IllegalArgumentException
	 *             if the x or y coordinate is not inside this raster
	 */
	private void checkCoordinate(int x, int y) {
		if (x < 0) {
			throw new IllegalArgumentException("X coordinate must be greater or equal to 0!");
		}
		if (y < 0) {
			throw new IllegalArgumentException("Y coordinate must be greater or equal to 0!");
		}

		if (x >= width) {
			throw new IllegalArgumentException("X coordinate must be less than width!");
		}
		if (y >= height) {
			throw new IllegalArgumentException("Y coordinate must be less than height!");
		}
	}
}
