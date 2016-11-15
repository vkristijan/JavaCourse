package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;

/**
 * A circle with outline and fill.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class FilledCircle extends AbstractOval {
	
	/** The name prefix. */
	private static String NAME_PREFIX = "Filled circle";
	
	/** The counter. */
	private static int counter;

	/** The color area used to chose colors. */
	private JColorArea colorArea;

	/**
	 * Instantiates a new filled circle.
	 *
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 * @param lineColor
	 *            the line color
	 * @param backgroundColor
	 *            the background color
	 */
	public FilledCircle(Point from, Point to, Color lineColor, Color backgroundColor) {
			super(from, to, lineColor, backgroundColor);
			setName(++counter);
		}

	@Override
	public void setName(int id) {
		this.name = NAME_PREFIX + id;
	}
	
	@Override
	public JPanel getSettingsPanel() {
		JPanel panel = super.getSettingsPanel();
		
		panel.add(new JLabel("Fill"));
		colorArea = new JColorArea(fillColor);
		panel.add(colorArea);
		
		return panel;
	}
	
	@Override
	public void updateSettings() {
		super.updateSettings();
		lineColor = colorArea.getCurrentColor();
	}

	@Override
	public String export() {
		//FCIRCLE centerx centery radius red green blue red green blue
		StringBuilder sb = new StringBuilder();
		sb.append("FCIRCLE ");
		sb.append(center.getX()).append(" ");
		sb.append(center.getY()).append(" ");
		sb.append(radius).append(" ");
		
		sb.append(lineColor.getRed()).append(" ");
		sb.append(lineColor.getGreen()).append(" ");
		sb.append(lineColor.getBlue()).append(" ");
		
		sb.append(fillColor.getRed()).append(" ");
		sb.append(fillColor.getGreen()).append(" ");
		sb.append(fillColor.getBlue());
		return sb.toString();
	}

	/**
	 * Generates a new {@link FilledCircle} from the given string.
	 *
	 * @param line
	 *            the line
	 * @return the geometrical object
	 */
	public static GeometricalObject fromString(String line) {
		//FCIRCLE centerx centery radius red green blue red green blue
		String[] data = line.split(" +");
		
		int x = Integer.parseInt(data[0]);
		int y = Integer.parseInt(data[1]);
		
		int radius = Integer.parseInt(data[2]);
		
		Point from = new Point(x + radius, y + radius);
		Point to = new Point(x + radius, y);
		
		int r = Integer.parseInt(data[3]);
		int g = Integer.parseInt(data[4]);
		int b = Integer.parseInt(data[5]);
		Color lineColor = new Color(r, g, b);
		
		r = Integer.parseInt(data[6]);
		g = Integer.parseInt(data[7]);
		b = Integer.parseInt(data[8]);
		Color backgroundColor = new Color(r, g, b);
		
		return new FilledCircle(from, to, lineColor, backgroundColor);
	}
}
