package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;

/**
 * Implementation of an oval object, that has two colors, a center and a radius.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public abstract class AbstractOval extends AbstractGeometricalObject {

	/** The default fill color. */
	private static Color DEFAULT_FILL = new Color(0, 0, 0, 0);

	/** The line color. */
	protected Color lineColor;

	/** The fill color. */
	protected Color fillColor;

	/** The color area used for color selection. */
	private JColorArea colorArea;

	/** The radius. */
	protected int radius;

	/** The center. */
	protected Point center;

	/**
	 * Instantiates a new abstract oval.
	 *
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 * @param lineColor
	 *            the line color
	 * @param fillColor
	 *            the fill color
	 */
	protected AbstractOval(Point from, Point to, Color lineColor, Color fillColor) {
		super(from, to);

		int x = from.getX() - to.getX();
		int y = from.getY() - to.getY();
		radius = (int) Math.sqrt(x * x + y * y);

		x = from.getX() - radius;
		y = from.getY() - radius;
		center = new Point(x, y);

		this.lineColor = lineColor;
		this.fillColor = fillColor;
	}

	/**
	 * Instantiates a new abstract oval, sets the fill color to transparent.
	 *
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 * @param lineColor
	 *            the line color
	 */
	protected AbstractOval(Point from, Point to, Color lineColor) {
		this(from, to, lineColor, DEFAULT_FILL);
	}

	@Override
	public void draw(Graphics g) {
		Color oldColor = g.getColor();

		int x = from.getX() - to.getX();
		int y = from.getY() - to.getY();
		radius = (int) Math.sqrt(x * x + y * y);

		x = from.getX() - radius;
		y = from.getY() - radius;
		center = new Point(x, y);

		g.setColor(fillColor);
		g.fillOval(x, y, radius * 2, radius * 2);

		g.setColor(lineColor);
		g.drawOval(x, y, radius * 2, radius * 2);

		g.setColor(oldColor);
	}

	@Override
	public JPanel getSettingsPanel() {
		JPanel panel = super.getSettingsPanel();

		panel.add(new JLabel("Color"));
		colorArea = new JColorArea(lineColor);
		panel.add(colorArea);

		return panel;
	}

	@Override
	public void updateSettings() {
		super.updateSettings();
		lineColor = colorArea.getCurrentColor();
	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(center.getX(), center.getY(), center.getX() + 2 * radius, center.getY() + 2 * radius);
	}
}
