package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * This interface specifies a raster viewer that can transform the given raster
 * into a visible format.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public interface RasterView {
	/**
	 * Produces a visible format of the raster given in the argument.
	 * 
	 * @param raster
	 *            the raster to be visualized
	 * @return an object visualization of the raster
	 * @throws IllegalArgumentException
	 *             if the given raster is null
	 */
	Object produce(BWRaster raster);
}
