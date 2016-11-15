package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * Allows the user to store multiple values for same key. The
 * {@code ObjectMultistack} behaves like an ordinary {@link Map}, but with the
 * capability of storing multiple values with the same key. The values are
 * stored using the last in, first out principle.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class ObjectMultistack {
	/** All the elements in this {@code ObjectMultistack}. */
	private Map<String, MultistackEntry> elements;

	/**
	 * Creates a new {@code ObjectMultistack} with no elements in it.
	 */
	public ObjectMultistack() {
		elements = new HashMap<>();
	}

	/**
	 * Adds the element with the given key to the {@code ObjectMultistack}.
	 * 
	 * @param name
	 *            the key of the element to be added.
	 * @param valueWrapper
	 *            the element to be added.
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		MultistackEntry lastElement = elements.get(name);

		MultistackEntry newElement = new MultistackEntry(valueWrapper, null);
		if (lastElement != null) {
			newElement.next = lastElement;
		}
		elements.put(name, newElement);
	}

	/**
	 * Returns the element on top of the stack with the given key, and deletes
	 * it from the {@code ObjectMultistack}
	 * 
	 * @param name
	 *            the key of the element.
	 * @return the element on top of the stack with the given key.
	 * @throws ObjectMultistackException
	 *             if the stack with the given key is empty.
	 */
	public ValueWrapper pop(String name) {
		checkEmpty(name);

		MultistackEntry returnValue = elements.get(name);

		if (returnValue.next == null) {
			elements.remove(name);
		} else {
			elements.put(name, returnValue.next);
		}

		return returnValue.element;
	}

	/**
	 * Returns the element on top of the stack with the given key, and leaves
	 * the {@code ObjectMultistack} unchanged.
	 * 
	 * @param name
	 *            the key of the element.
	 * @return the element on top of the stack with the given key.
	 * @throws ObjectMultistackException
	 *             if the stack with the given key is empty.
	 */
	public ValueWrapper peek(String name) {
		checkEmpty(name);

		return elements.get(name).element;
	}

	/**
	 * Checks if the {@code ObjectMultistack} with the given key is empty.
	 * 
	 * @param name
	 *            the key of the stack in the {@code ObjectMultistack}.
	 * @throws ObjectMultistackException
	 *             if the stack with the given key is empty.
	 */
	private void checkEmpty(String name) {
		if (isEmpty(name)) {
			throw new ObjectMultistackException("The stack is already empty");
		}
	}

	/**
	 * Checks if the {@code ObjectMultistack} with the given key is empty.
	 * 
	 * @param name
	 *            the key of the stack in the {@code ObjectMultistack}.
	 * @return a {@code Boolean} value, <code>true</code> if the stack in the
	 *         {@code ObjectMultistack} with the given key is empty,
	 *         <code>false</code> otherwise.
	 */
	public boolean isEmpty(String name) {
		return !elements.containsKey(name);
	}

	/**
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 *
	 */
	/**
	 * The entry for the {@link ObjectMultistack}. The entries are organized in
	 * a single linked list so they can be used as a stack. Every element has
	 * its {@link ValueWrapper} and a reference to the next element in the list.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 *
	 */
	private static class MultistackEntry {
		/**
		 * The element stored in this node of the {@link MultistackEntry}.
		 */
		private ValueWrapper element;
		/**
		 * Reference to the next {@link MultistackEntry}.
		 */
		private MultistackEntry next;

		/**
		 * Creates a new {@link MultistackEntry} with the given element and the
		 * reference to the next {@code MultistackEntry}.
		 * 
		 * @param element
		 *            the {@link ValueWrapper} that should be stored in this
		 *            node.
		 * @param next
		 *            the reference to the next element.
		 */
		public MultistackEntry(ValueWrapper element, MultistackEntry next) {
			this.element = element;
			this.next = next;
		}

	}
}
