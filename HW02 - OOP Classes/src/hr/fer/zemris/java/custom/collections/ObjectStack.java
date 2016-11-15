package hr.fer.zemris.java.custom.collections;

/**
 * An implementation of a stack collection. Enables to add elements and and get
 * them using a last in first out approach. More detail can be found on the
 * following
 * <a href="https://en.wikipedia.org/wiki/Stack_(abstract_data_type)">page</a>.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class ObjectStack {
	/**
	 * The collection for storing elements in the stack.
	 */
	private ArrayIndexedCollection adaptee;

	/**
	 * Constructs a new empty stack.
	 */
	public ObjectStack() {
		super();
		adaptee = new ArrayIndexedCollection();
	}

	/**
	 * Returns true if the stack contains no objects and false otherwise.
	 * 
	 * @return A boolean value:
	 *         <ul>
	 *         <li><strong> true </strong> if the stack is empty
	 *         <li><strong> false </strong> if the stack is not empty
	 *         </ul>
	 */
	public boolean isEmpty() {
		return adaptee.isEmpty();
	}

	/**
	 * Returns the size of the stack.
	 * 
	 * @return the size of the stack
	 */
	public int size() {
		return adaptee.size();
	}

	/**
	 * Adds the element to the stack. It's not allowed to add <code>null</code>
	 * values.
	 * 
	 * @param value
	 *            the value to be added to the stack.
	 * @throws IllegalArgumentException
	 *             if the given value is null.
	 */
	public void push(Object value) {
		adaptee.add(value);
	}

	/**
	 * Returns the value of the item on top of the stack and removes it from the
	 * stack.
	 * 
	 * @return the value on top of the stack.
	 * @throws EmptyStackException
	 *             if the stack is empty.
	 */
	public Object pop() {
		return getLast(true);
	}

	/**
	 * Returns the value of the item on top of the stack, without removing it.
	 * 
	 * @return the value on top of the stack.
	 * @throws EmptyStackException
	 *             if the stack is empty.
	 */
	public Object peek() {
		return getLast(false);
	}

	/**
	 * Gets the item on the top of the stack. Depending on the argument that
	 * item can be removed or can stay in the stack.
	 * 
	 * @param remove
	 *            determines if the value on the top of the stack should be
	 *            removed or not. If the argument is true the element will be
	 *            removed from the stack.
	 * @return the value of the element on top of the stack
	 */
	private Object getLast(boolean remove) {
		int index = adaptee.size() - 1;
		if (index < 0) {
			throw new EmptyStackException("There are no elements in the stack!");
		}

		Object value = adaptee.get(index);
		if (remove == true) {
			adaptee.remove(index);
		}

		return value;
	}

	/**
	 * Removes all the elements from the stack.
	 */
	public void clear() {
		adaptee.clear();
	}
}
