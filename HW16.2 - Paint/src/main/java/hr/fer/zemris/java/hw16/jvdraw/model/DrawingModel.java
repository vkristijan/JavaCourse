package hr.fer.zemris.java.hw16.jvdraw.model;

import java.awt.Rectangle;
import java.nio.file.Path;

import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;

/**
 * A model that contains a list of {@link GeometricalObject}s that should be
 * displayed in this model. Enables methods to add or remove objects, draw them,
 * add listeners if a new object is added, etc.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public interface DrawingModel {
	
	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize();
	
	/**
	 * Gets the object with the given id.
	 *
	 * @param index the index
	 * @return the object
	 */
	public GeometricalObject getObject(int index);
	
	/**
	 * Adds the {@link GeometricalObject}.
	 *
	 * @param object the object
	 */
	public void add(GeometricalObject object);
	
	/**
	 * Removes the {@link GeometricalObject}.
	 *
	 * @param object the object
	 */
	public void remove(GeometricalObject object);
	
	/**
	 * Changes the {@link GeometricalObject}.
	 *
	 * @param object the object
	 */
	public void change(GeometricalObject object);

	/**
	 * Adds the drawing model listener.
	 *
	 * @param l the l
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Removes the drawing model listener.
	 *
	 * @param l the l
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Checks if the document was changed.
	 *
	 * @return true, if the document was changed without saving, false
	 *         otherwise.
	 */
	public boolean hasChanged();
	
	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public Path getPath();
	
	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(Path path);
	
	/**
	 * Saves the image.
	 */
	public void save();
	
	/**
	 * Clears the objects list.
	 */
	public void clear();
	
	/**
	 * Sets the changed flag.
	 *
	 * @param b the new changed
	 */
	public void setChanged(boolean b);
	
	/**
	 * Gets the bounding box.
	 *
	 * @return the bounding box
	 */
	public Rectangle getBoundingBox();
}
