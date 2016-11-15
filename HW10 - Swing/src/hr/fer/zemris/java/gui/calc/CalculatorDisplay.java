package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * A simple {@link JLabel} component that is used as a calculator display. The
 * {@link Component} provides methods to add a digit to a number or add a while
 * number.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class CalculatorDisplay extends JLabel {
	/**
	 * The default serial version ID number.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The text displayed on the display.
	 */
	private String text;

	/**
	 * Creates a new {@link CalculatorDisplay} that has no text on it.
	 */
	public CalculatorDisplay() {
		text = "";
		setOpaque(true);
		setBackground(new Color(195, 168, 168));
		setHorizontalAlignment(SwingConstants.RIGHT);
		setVerticalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

		setFont(new Font("Serif", Font.BOLD, 18));
		setText(text);
	}

	/**
	 * Adds text to the display.
	 * 
	 * @param text
	 *            the text to be added.
	 */
	public void addText(String text) {
		this.text = this.text + text;

		setText(this.text);
	}

	/**
	 * Sets the text of the display to the number given in the argument.
	 * 
	 * @param number
	 *            the number to be displayed.
	 */
	public void setText(Double number) {
		double value = number;
		int intValue = number.intValue();

		if (value == intValue) {
			setText(String.valueOf(intValue));
		} else {
			setText(String.valueOf(value));
		}
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		this.text = text;
	}
}
