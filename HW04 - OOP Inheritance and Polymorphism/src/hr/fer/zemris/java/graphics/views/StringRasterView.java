package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * A {@code RasterView} that can transform a raster into a string.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class StringRasterView extends AbstractRasterView {
	/**
	 * Creates a new instance of the {@code StringRasterView} and sets the value
	 * for the onCharacter to '*' and offCharacter to '.'.
	 */
	public StringRasterView(){
		this('*', '.');
	}

	/**
	 * Creates a new instance of the {@code StringRasterView} and sets the value
	 * for the onCharacter and offCharacter chars.
	 * 
	 * @param onCharacter
	 *            The character that represents a pixel that is turned on.
	 * @param offCharacter
	 *            The character that represents a pixel that is turned off.
	 */
	public StringRasterView(char onCharacter, char offCharacter) {
		super(onCharacter, offCharacter);
	}

	@Override
	public Object produce(BWRaster raster) {
		return rasterToString(raster);
	}

}
