package hr.fer.zemris.java.hw16.jvdraw.model;

/**
 * Interface defining the methods for a listener interested in changes of
 * objects in {@link DrawingModel}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public interface DrawingModelListener {
	
	/**
	 * Objects added action.
	 *
	 * @param source
	 *            the source
	 * @param index0
	 *            the index 0
	 * @param index1
	 *            the index 1
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Objects removed action.
	 *
	 * @param source
	 *            the source
	 * @param index0
	 *            the index 0
	 * @param index1
	 *            the index 1
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Objects changed action.
	 *
	 * @param source
	 *            the source
	 * @param index0
	 *            the index 0
	 * @param index1
	 *            the index 1
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
