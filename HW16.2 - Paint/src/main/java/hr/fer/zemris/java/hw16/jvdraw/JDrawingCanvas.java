package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * A canvas capable of displaying {@link GeometricalObject}s.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The source. */
	private DrawingModel source;
	
	
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		this.source = source;
		
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		this.source = source;

		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		this.source = source;

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if (source == null) return;
		int from = 0;
		int to = source.getSize();
		for (int i = from; i < to; ++i){
			GeometricalObject object = source.getObject(i);
			object.draw(g);
		}
	}
}
