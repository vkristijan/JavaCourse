package hr.fer.zemris.java.hw16.jvdraw;

import javax.swing.AbstractListModel;
import javax.swing.JList;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * A list model used to display {@link GeometricalObject}s in a {@link JList}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class DrawingObjectListModel 
	extends AbstractListModel<GeometricalObject> 
	implements DrawingModelListener {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The model. */
	private DrawingModel model;
	
	/**
	 * Instantiates a new drawing object list model.
	 *
	 * @param model the model
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}
}
