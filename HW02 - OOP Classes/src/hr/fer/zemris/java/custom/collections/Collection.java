package hr.fer.zemris.java.custom.collections;

/**
 * This class defines a collection that is capable of storing data and methods
 * to use the collection. However, this class does not implement any of the
 * collection's capabilities so that it does not actually have any storage
 * capabilities.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Collection {

	/**
	 * The default constructor. It just calls the base constructor.
	 */
	protected Collection() {
		super();
	}

	/**
	 * Returns true if collection contains no objects and false otherwise.
	 * 
	 * @return A boolean value:
	 *         <ul>
	 *         <li><strong> true </strong> if the collection is empty
	 *         <li><strong> false </strong> if the collection is not empty
	 *         </ul>
	 */
	public boolean isEmpty() {
		if (size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the number of currently stored objects in this collection. This
	 * implementation always returns 0.
	 * 
	 * @return the number of objects stored in this collection.
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given <code>object</code> into this collection. <br>
	 * This implementation does nothing.
	 * 
	 * @param value
	 *            the <code>object</code> that should be added into the
	 *            collection.
	 */
	public void add(Object value) {
		return;
	}

	/**
	 * Returns true only if the collection contains a given value, as determined
	 * by <code>equals</code> method. It is allowed to ask if the collection
	 * contains <code>null</code>. <br>
	 * This implementation always returns <code> false<code>.
	 * 
	 * @param value
	 *            the object that should be checked.
	 * @return A boolean value:
	 *         <ul>
	 *         <li><strong> true </strong> if the collection contains the
	 *         specified object
	 *         <li><strong> false </strong> if the collection doesn't contain
	 *         the specified object
	 *         </ul>
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Returns true only if the collection contains the given value as
	 * determined by <code>equals</code> method and removes <strong>one</strong>
	 * occurrence of it (in this class it is not specified which one). The
	 * collection will stay unchanged if it doesn't contain the given value.
	 * <br>
	 * This implementation always returns <code>false</code>.
	 * 
	 * @param value
	 *            the value that should be removed from the collection
	 * @return A boolean value:
	 *         <ul>
	 *         <li><strong> true </strong> if the collection contains the
	 *         specified object
	 *         <li><strong> false </strong> if the collection doesn't contain
	 *         the specified object
	 *         </ul>
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates a new array with size equal to the size of this collection,
	 * fills it with collection content and returns the array. This method never
	 * returns null. <br>
	 * This implementation throws an <code>UnsupportedOperationException</code>.
	 * 
	 * @return a new array that contains all the elements of this collection.
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException(
			"This method is not implemented in this class.");
	}

	/**
	 * Method calls <code>processor.process()</code> for each element of this
	 * collection. The order in which elements will be sent is undefined in this
	 * class. <br>
	 * This implementation is an empty method.
	 * 
	 * @param processor
	 *            the <code> Processor </code> that should process the elements
	 *            from the collection.
	 */
	public void forEach(Processor processor) {
		return;
	}

	/**
	 * This method adds all the element from the given collection into this
	 * collection. <br>
	 * The other collection remains unchanged.
	 * 
	 * @param other
	 *            the collection that should be added into this collection.
	 */
	public void addAll(Collection other) {
		/**
		 * A local class that processes data by adding it to the collection.
		 * 
		 * @author Kristijan Vulinovic
		 * @version 1.0
		 */
		class localProcessor extends Processor {
			/**
			 * Adds the value into the current collection.
			 */
			@Override
			public void process(Object value) {
				add(value);
			}
		}

		other.forEach(new localProcessor());
	}

	/**
	 * Removes all elements from this collection. This implementation does
	 * nothing.
	 */
	public void clear() {
		return;
	}
}
