package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * A {@code RasterView} that can transform a raster into a string and print it
 * to the standard output.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class SimpleRasterView extends AbstractRasterView {
	/**
	 * Creates a new instance of the {@code SimpleRasterView} and sets the value
	 * for the onCharacter to '*' and offCharacter to '.'.
	 */
	public SimpleRasterView() {
		this('*', '.');
	}

	/**
	 * Creates a new instance of the {@code SimpleRasterView} and sets the value
	 * for the onCharacter and offCharacter chars.
	 * 
	 * @param onCharacter
	 *            The character that represents a pixel that is turned on.
	 * @param offCharacter
	 *            The character that represents a pixel that is turned off.
	 */
	public SimpleRasterView(char onCharacter, char offCharacter) {
		super(onCharacter, offCharacter);
	}

	@Override
	public Object produce(BWRaster raster) {
		System.out.println(rasterToString(raster));
		return null;
	}

}
