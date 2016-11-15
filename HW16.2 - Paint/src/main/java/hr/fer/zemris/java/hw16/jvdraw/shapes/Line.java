package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;

/**
 * A line object that has it's beginning, it's end and it's color.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class Line extends AbstractGeometricalObject {
	
	/** The name prefix. */
	private static String NAME_PREFIX = "Line";
	
	/** The counter. */
	private static int counter;
	
	/** The color. */
	private Color color;
	
	/** The color area. */
	private JColorArea colorArea;
	
	/**
	 * Instantiates a new line.
	 *
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 * @param color
	 *            the color
	 */
	public Line(Point from, Point to, Color color) {
		super(from, to);
		setName(++counter);
		this.color = color;
	}
	
	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public void setName(int id) {
		this.name = NAME_PREFIX + id;
	}

	@Override
	public void draw(Graphics g) {
		Color oldColor = g.getColor();
		g.setColor(color);
		
		g.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
		g.setColor(oldColor);
	}
	
	@Override
	public JPanel getSettingsPanel() {
		JPanel panel = super.getSettingsPanel();
		
		panel.add(new JLabel("Color"));
		colorArea = new JColorArea(color);
		panel.add(colorArea);
		
		return panel;
	}
	
	@Override
	public void updateSettings() {
		super.updateSettings();
		color = colorArea.getCurrentColor();
	}

	@Override
	public String export() {
		//LINE x0 y0 x1 y1 red green blue 
		StringBuilder sb = new StringBuilder();
		sb.append("LINE ");
		sb.append(from.getX()).append(" ");
		sb.append(from.getY()).append(" ");
		
		sb.append(to.getX()).append(" ");
		sb.append(to.getY()).append(" ");
		
		sb.append(color.getRed()).append(" ");
		sb.append(color.getGreen()).append(" ");
		sb.append(color.getBlue());
		
		return sb.toString();
	}

	/**
	 * Creates a new {@link Line} from the given string.
	 *
	 * @param line
	 *            the line
	 * @return the geometrical object
	 */
	public static GeometricalObject fromString(String line) {
		String[] data = line.split(" +");
		
		int x = Integer.parseInt(data[0]);
		int y = Integer.parseInt(data[1]);
		Point from = new Point(x, y);
		
		x = Integer.parseInt(data[2]);
		y = Integer.parseInt(data[3]);
		Point to = new Point(x, y);
		
		int r = Integer.parseInt(data[4]);
		int g = Integer.parseInt(data[5]);
		int b = Integer.parseInt(data[6]);
		Color color = new Color(r, g, b);
		
		return new Line(from, to, color);
	}
	
	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(
			Math.min(from.getX(), to.getX()), 
			Math.min(from.getY(), to.getY()),
			Math.max(from.getX(), to.getX()), 
			Math.max(from.getY(), to.getY())
		);
	}
}
