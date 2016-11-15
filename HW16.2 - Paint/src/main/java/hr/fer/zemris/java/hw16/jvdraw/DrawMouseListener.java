package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Point;

/**
 * A mouse listener that is used to draw the objects. Tracks mouse click events,
 * and mouse movements, after the first click.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class DrawMouseListener extends MouseAdapter {
	
	/** The first point. */
	private Point firstPoint;
	
	/** The second point. */
	private Point secondPoint;
	
	/** The draw application that uses this listener. */
	private JVDraw draw;
	
	/** A flag storing used to check if an mouse click already happened. */
	private boolean isClicked = false;
	
	/** The {@link GeometricalObject}. */
	private GeometricalObject object;
	
	/**
	 * Instantiates a new draw mouse listener.
	 *
	 * @param draw the draw
	 */
	public DrawMouseListener(JVDraw draw) {
		this.draw = draw;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if (!isClicked){
			isClicked = true;
			firstPoint = new Point(x, y);
			object = null;
		} else {
			isClicked = false;
			secondPoint = new Point(x, y);
			
			object.setTo(secondPoint);
			draw.changedObject(object);
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if (!isClicked) return;
		
		int x = e.getX();
		int y = e.getY();
		secondPoint = new Point(x, y);
		
		if (object != null){
			object.setTo(secondPoint);
			draw.changedObject(object);
		} else {
			object = draw.addObject(firstPoint, secondPoint);
		}
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		if (!isClicked) return;
		isClicked = false;
		
		if (object != null){
			draw.removeObject(object);
			object = null;
		}
	}
}
