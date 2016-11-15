package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw16.jvdraw.color.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;

/**
 * Status label used to display the RGB value of the foreground and background
 * colors.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class StatusLabel extends JLabel implements ColorChangeListener {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The foreground color provider. */
	private IColorProvider foreground;
	
	/** The background color provider. */
	private IColorProvider background;

	/**
	 * Instantiates a new status label.
	 *
	 * @param foreground
	 *            the foreground
	 * @param background
	 *            the background
	 */
	public StatusLabel(IColorProvider foreground, IColorProvider background) {
		this.foreground = foreground;
		this.background = background;

		foreground.addColorChangeListener(this);
		background.addColorChangeListener(this);

		updateText();
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		updateText();
	}

	/**
	 * Updates the text.
	 */
	private void updateText() {
		StringBuilder sb = new StringBuilder();
		sb.append("Foreground color: ");
		sb.append(colorToString(foreground.getCurrentColor()));
		sb.append(", background color: ");
		sb.append(colorToString(background.getCurrentColor()));
		sb.append(".");

		setText(sb.toString());
	}

	/**
	 * Returns a string representation of the given color. The string will
	 * consist of the integer values for the red, green and blue value.
	 *
	 * @param color
	 *            the color
	 * @return the string
	 */
	private String colorToString(Color color) {
		StringBuilder sb = new StringBuilder();

		sb.append("(");
		sb.append(color.getRed()).append(", ");
		sb.append(color.getGreen()).append(", ");
		sb.append(color.getBlue());
		sb.append(")");

		return sb.toString();
	}
}
