package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.Color;

/**
 * A circle that has its outline, but no fill.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class Circle extends AbstractOval {
	
	/** The name prefix. */
	private static String NAME_PREFIX = "Circle";
	
	/** The counter. */
	private static int counter;
	
	/**
	 * Instantiates a new circle.
	 *
	 * @param from the from
	 * @param to the to
	 * @param lineColor the line color
	 */
	public Circle(Point from, Point to, Color lineColor) {
		super(from, to, lineColor);
		setName(++counter);
	}

	@Override
	public void setName(int id) {
		this.name = NAME_PREFIX + id;
	}
	
	@Override
	public String export() {
		//CIRCLE centerx centery radius red green blue 
		StringBuilder sb = new StringBuilder();
		sb.append("CIRCLE ");
		sb.append(center.getX()).append(" ");
		sb.append(center.getY()).append(" ");
		sb.append(radius).append(" ");
		sb.append(lineColor.getRed()).append(" ");
		sb.append(lineColor.getGreen()).append(" ");
		sb.append(lineColor.getBlue());
		return sb.toString();
	}

	/**
	 * Generates a {@link Circle} from the given string line.
	 *
	 * @param line
	 *            the line
	 * @return the geometrical object
	 */
	public static GeometricalObject fromString(String line) {
		//CIRCLE centerx centery radius red green blue 
		String[] data = line.split(" +");
		
		int x = Integer.parseInt(data[0]);
		int y = Integer.parseInt(data[1]);
		
		int radius = Integer.parseInt(data[2]);
		
		Point from = new Point(x + radius, y + radius);
		Point to = new Point(x + radius, y);
		
		int r = Integer.parseInt(data[3]);
		int g = Integer.parseInt(data[4]);
		int b = Integer.parseInt(data[5]);
		Color color = new Color(r, g, b);
		
		return new Circle(from, to, color);
	}
}
