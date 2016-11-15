package hr.fer.zemris.java.hw16.jvdraw.shapes;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Implementation of {@link GeometricalObject} that has a point for the
 * beginning and end, and a settings panel.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public abstract class AbstractGeometricalObject implements GeometricalObject {
	
	/** The name. */
	protected String name;
	
	/** The beginning point. */
	protected Point from;
	
	/** The ending point. */
	protected Point to;
	
	/** The settings panel. */
	protected JPanel settings;
	
	/** The from X. */
	private JTextField fromX;
	
	/** The from Y. */
	private JTextField fromY;
	
	/** The to X. */
	private JTextField toX;
	
	/** The to Y. */
	private JTextField toY;
	
	/**
	 * Instantiates a new abstract geometrical object.
	 *
	 * @param from the from
	 * @param to the to
	 */
	public AbstractGeometricalObject(Point from, Point to) {
		this.from = from;
		this.to = to;
	}
	

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Point getFrom() {
		return from;
	}

	@Override
	public void setFrom(Point from) {
		this.from = from;
	}

	@Override
	public Point getTo() {
		return to;
	}

	@Override
	public void setTo(Point to) {
		this.to = to;
	}

	/**
	 * Sets the name.
	 *
	 * @param id the new name
	 */
	public abstract void setName(int id);
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public JPanel getSettingsPanel() {
		settings = new JPanel();
		settings.setLayout(new GridLayout(0, 2));
		
		settings.add(new JLabel("From x"));
		fromX = new JTextField(String.valueOf(from.getX()));
		settings.add(fromX);
		
		settings.add(new JLabel("From y"));
		fromY = new JTextField(String.valueOf(from.getY()));
		settings.add(fromY);
		
		settings.add(new JLabel("To x"));
		toX = new JTextField(String.valueOf(to.getX()));
		settings.add(toX);
		
		settings.add(new JLabel("To y"));
		toY = new JTextField(String.valueOf(to.getY()));
		settings.add(toY);
		
		return settings;
	}
	
	public void updateSettings(){
		if (settings == null) return;
		
		from.setX(Integer.parseInt(fromX.getText()));
		from.setY(Integer.parseInt(fromY.getText()));
		to.setX(Integer.parseInt(toX.getText()));
		to.setY(Integer.parseInt(toY.getText()));
	}
}
