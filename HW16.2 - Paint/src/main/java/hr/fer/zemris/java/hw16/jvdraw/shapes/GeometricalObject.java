package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

/**
 * Interface defining a object that can be displayed in the canvas.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public interface GeometricalObject {
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();
	
	/**
	 * Draws the object to the given {@link Graphics}.
	 *
	 * @param g
	 *            the g
	 */
	void draw(Graphics g);

	/**
	 * Gets the from point.
	 *
	 * @return the from
	 */
	Point getFrom();

	/**
	 * Sets the from point.
	 *
	 * @param from
	 *            the new from
	 */
	void setFrom(Point from);

	/**
	 * Sets the to point.
	 *
	 * @param to
	 *            the new to
	 */
	void setTo(Point to);

	/**
	 * Gets the to point.
	 *
	 * @return the to
	 */
	Point getTo();

	/**
	 * Gets the settings panel, used to update settings.
	 *
	 * @return the settings panel
	 */
	JPanel getSettingsPanel();

	/**
	 * Update settings.
	 */
	void updateSettings();

	/**
	 * Creates a string representation of the object.
	 *
	 * @return the string representation
	 */
	String export();

	/**
	 * Gets the bounding box.
	 *
	 * @return the bounding box
	 */
	public Rectangle getBoundingBox();
}
