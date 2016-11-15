package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * A {@code RasterView} that can transform a raster into a form that can be
 * displayed.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public abstract class AbstractRasterView implements RasterView {
	/**
	 * The character that represents a pixel that is turned on.
	 */
	private final char onCharacter;
	/**
	 * The character that represents a pixel that is turned off.
	 */
	private final char offCharacter;

	/**
	 * Creates a new instance of the {@code AbstractRasterView} and sets the
	 * value for the onCharacter and offCharacter chars.
	 * 
	 * @param onCharacter
	 *            The character that represents a pixel that is turned on.
	 * @param offCharacter
	 *            The character that represents a pixel that is turned off.
	 */
	protected AbstractRasterView(char onCharacter, char offCharacter) {
		super();
		this.onCharacter = onCharacter;
		this.offCharacter = offCharacter;
	}

	/**
	 * Converts the given raster into a string representation. Every pixel in
	 * the raster that is turned on is represented with a character as defined
	 * in the onCharacter given in constructor. Every pixel in the raster that
	 * is turned off is represented with a character as defined in the
	 * offCharacter given in constructor.
	 * 
	 * @param raster
	 *            the raster to be presented as a string
	 * @return the string representation of the raster
	 */
	String rasterToString(BWRaster raster) {
		StringBuilder string = new StringBuilder();

		int height = raster.getHeight();
		int width = raster.getWidth();

		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				string.append(raster.isTurnedOn(x, y) ? onCharacter : offCharacter);
			}
			if (y < height - 1) string.append('\n');
		}

		return string.toString();
	}
}
