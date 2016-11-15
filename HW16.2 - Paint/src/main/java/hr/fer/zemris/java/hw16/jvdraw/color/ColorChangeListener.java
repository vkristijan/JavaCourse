package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * Interface that listens to a change of color from the {@link IColorProvider}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public interface ColorChangeListener {
	
	/**
	 * Method called when the color changes.
	 *
	 * @param source
	 *            the source
	 * @param oldColor
	 *            the old color
	 * @param newColor
	 *            the new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor); 
}
