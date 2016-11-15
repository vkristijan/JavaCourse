package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

// TODO: Auto-generated Javadoc
/**
 * Defines an object that is capable of providing a color. It will notify all
 * {@link ColorChangeListener}s.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public interface IColorProvider {
	
	/**
	 * Gets the current color.
	 *
	 * @return the current color
	 */
	public Color getCurrentColor();
	
	/**
	 * Adds the color change listener.
	 *
	 * @param listener
	 *            the listener
	 */
	public void addColorChangeListener(ColorChangeListener listener);

	/**
	 * Removes the color change listener.
	 *
	 * @param listener
	 *            the listener
	 */
	public void removeColorChangeListener(ColorChangeListener listener);
}
