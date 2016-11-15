package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * A button that is used in the {@link Calculator}. Every button has defined
 * text to be displayed in normal and in inverted mode.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class CalculatorButton extends JButton {
	/**
	 * The default serial version ID number.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The text to be displayed on the button when it is in normal state.
	 */
	private String normalText;
	/**
	 * The text to be displayed on the button when it is in inverted mode.
	 */
	private String invertedText;

	/**
	 * Creates a new {@link CalculatorButton} with the text given in the
	 * argument.
	 * 
	 * @param text
	 *            the text to be displayed on the button.
	 */
	public CalculatorButton(String text) {
		super(text);

		normalText = text;
		setOpaque(true);
		setBackground(Color.LIGHT_GRAY);
		setHorizontalAlignment(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

		setFont(new Font("Serif", Font.BOLD, 18));
	}

	/**
	 * Sets the text to be displayed in inverted mode to the one in the argument
	 * 
	 * @param text
	 *            the new value for the inverted text.
	 */
	public void setInvertedText(String text) {
		this.invertedText = text;
	}

	/**
	 * Returns the text that should be displayed on the button in normal mode.
	 * 
	 * @return the text that should be displayed on the button in normal mode.
	 */
	public String getNormalText() {
		return normalText;
	}

	/**
	 * Sets the text to be displayed in normal mode to the one in the argument
	 * 
	 * @param normalText
	 *            the new value for the normal text.
	 */
	public void setNormalText(String normalText) {
		this.normalText = normalText;
	}

	/**
	 * Returns the text that should be displayed on the button in inverted mode.
	 * 
	 * @return the text that should be displayed on the button in inverted mode.
	 */
	public String getInvertedText() {
		return invertedText;
	}

}
