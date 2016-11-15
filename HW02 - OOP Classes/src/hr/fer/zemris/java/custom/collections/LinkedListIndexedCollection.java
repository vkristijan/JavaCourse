package hr.fer.zemris.java.custom.collections;

/**
 * This class implements a doubly linked list. Duplicate elements are allowed,
 * but storage of null references is not allowed.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class LinkedListIndexedCollection extends Collection {
	/**
	 * This class describes a single node in a doubly linked list.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 */
	private static class ListNode {
		/**
		 * Reference to the previous element in the linked list.
		 */
		private ListNode previous;
		/**
		 * Reference to the next element in the linked list.
		 */
		private ListNode next;
		/**
		 * The data stored in the list.
		 */
		private Object data;

		/**
		 * This constructor creates a new ListNode and sets it's data to the
		 * data given as the argument.
		 * 
		 * @param data
		 *            the data to be inserted into the new ListNode
		 */
		public ListNode(Object data) {
			super();
			this.data = data;
		}
	}

	/**
	 * The size of the linked list.
	 */
	private int size;
	/**
	 * The reference to the first element in the linked list.
	 */
	private ListNode first;
	/**
	 * The reference to the last element in the linked list.
	 */
	private ListNode last;

	/**
	 * This constructor creates an empty linked list.
	 */
	public LinkedListIndexedCollection() {
		super();
		size = 0;
		first = null;
		last = null;
	}

	/**
	 * This constructor creates a new linked list that contains all the elements
	 * of the given collection.
	 * 
	 * @param other
	 *            the collection which elements should be copied into the linked
	 *            list.
	 */
	public LinkedListIndexedCollection(Collection other) {
		this();
		addAll(other);
	}

	/**
	 * Returns the number of currently stored objects in this collection.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns true only if the collection contains a given value, as determined
	 * by equals method. It is allowed to ask if the collection contains null.
	 * <br>
	 * This implementation has a linear time complexity.
	 */
	@Override
	public boolean contains(Object value) {
		int index = indexOf(value);
		if (index != -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Allocates a new array with size equal to the size of this collection,
	 * fills it with collection content and returns the array. This method never
	 * returns null.
	 */
	@Override
	public Object[] toArray() {
		Object[] tmpArray = new Object[size];

		ListNode node = first;
		for (int i = 0; i < size; ++i) {
			tmpArray[i] = node.data;
			node = node.next;
		}
		return tmpArray;
	}

	/**
	 * Method calls processor.process() for each element of this collection.
	 * <br>
	 * This implementation goes from the first element to the last one in a
	 * continuous order.
	 * 
	 * @throws NullPointerException
	 *             if the <code>Processor</code> reference is null.
	 */
	@Override
	public void forEach(Processor processor) {
		if (processor == null) {
			throw new NullPointerException("The processor refference must not be null!");
		}
		ListNode node = first;
		while (node != null) {
			processor.process(node.data);
			node = node.next;
		}
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in
	 * array, elements at position and at greater positions are shifted one
	 * place toward the end, so that an empty place is created at position. The
	 * legal positions are 0 to size. If position is invalid, an
	 * <code>IndexOutOfboundsException</code> is thrown. <br>
	 * The time complexity of this method is never greater than
	 * <strong><sup>n</sup>/<sub>2</sub> +1 </strong>, where n is the number of
	 * elements.
	 * 
	 * @param value
	 *            the value that should be inserted into the collection
	 * @param position
	 *            the position in the collection where the value should be
	 *            inserted
	 */
	public void insert(Object value, int position) {
		if (value == null) {
			throw new IllegalArgumentException("The value is not allowed to be null!");
		}
		if (position < 0) {
			throw new IndexOutOfBoundsException("The position should be greater than 0!");
		}
		if (position > size) {
			throw new IndexOutOfBoundsException(
				"The position should be less or equal to " + size + "!");
		}

		if (position == size) {
			// insert to the end;
			ListNode newNode = new ListNode(value);
			newNode.previous = last;
			if (last != null) {
				last.next = newNode;
			}
			last = newNode;
			if (first == null) {
				first = newNode;
			}
			size++;
			return;
		}

		ListNode node = getNode(position);

		// add the new node
		ListNode newNode = new ListNode(value);
		newNode.previous = node.previous;
		newNode.next = node;
		if (newNode.previous != null) {
			newNode.previous.next = newNode;
		} else {
			first = newNode;
		}
		if (newNode.next != null) {
			newNode.next.previous = newNode;
		} else {
			last = newNode;
		}
		size++;
	}

	/**
	 * Adds the given object to the end of the linked list. It's not allowed to
	 * add null values into the collection, otherwise an exception will occur.
	 * <br>
	 * The average time complexity of this method is constant.
	 * 
	 * @throws IllegalArgumentException
	 *             if the given value is null
	 */
	@Override
	public void add(Object value) {
		insert(value, size);
	}

	/**
	 * Returns the object that is stored in linked list at position index. Valid
	 * indexes are 0 to size-1. If index is invalid an
	 * <code>IndexOutOfBoundsException</code> is thrown. <br>
	 * The time complexity of this method is never greater than
	 * <strong><sup>n</sup>/<sub>2</sub> +1 </strong>, where n is the number of
	 * elements.
	 * 
	 * @param index
	 *            the position of the list node.
	 * @return the value of the desired node.
	 * @throws IndexOutOfBoundsException
	 *             if the index argument is invalid.
	 */
	public Object get(int index) {
		ListNode node = getNode(index);
		return node.data;
	}

	/**
	 * Removes all elements from the collection.
	 */
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of
	 * the given value or -1 if the value is not found. The equality is
	 * determined using the equals method. <br>
	 * The time complexity of this method is linear on the number of elements.
	 * 
	 * @param value
	 *            the value to be found in the collection
	 * @return the index of the object if it exists in the collection, -1
	 *         otherwise.
	 */
	public int indexOf(Object value) {
		if (first == null) {
			return -1;
		}

		ListNode node = first;
		for (int i = 0; i < size; ++i) {
			if (node.data.equals(value)) {
				return i;
			}
			node = node.next;
		}
		return -1;
	}

	/**
	 * Returns true only if the collection contains the given value as
	 * determined by equals method and removes the first occurrence of it. The
	 * collection will stay unchanged if it doesn't contain the given value.
	 * <br>
	 * The time complexity of this method is linear on the number of elements.
	 */
	@Override
	public boolean remove(Object value) {
		ListNode node = first;
		while (node != null) {
			if (node.data.equals(value)) {
				removeNode(node);
				return true;
			}
			node = node.next;
		}

		return false;
	}

	/**
	 * Removes element at specified index from collection. Element that was
	 * previously at location index+1 after this operation is on location index,
	 * etc. Legal indexes are 0 to size-1. In case of invalid index an
	 * <code>IndexOutOfBoundsException</code> is thrown.
	 * 
	 * @param index
	 *            the index of the argument to be removed
	 * @throws IndexOutOfBoundsException
	 *             if the index is invalid
	 */
	public void remove(int index) {
		ListNode node = getNode(index);
		removeNode(node);
	}

	/**
	 * Removes the given <code>ListNode</code> from the linked list.
	 * 
	 * @param node
	 *            the node to be removed.
	 */
	private void removeNode(ListNode node) {
		if (node == null) {
			throw new IllegalArgumentException("The node doesn't exist!");
		}

		if (node.previous != null) {
			node.previous.next = node.next;
		} else {
			first = node.next;
		}
		if (node.next != null) {
			node.next.previous = node.previous;
		} else {
			last = node.previous;
		}

		size--;
	}

	/**
	 * Returns the list node at the given index. <br>
	 * The time complexity of this method is never greater than
	 * <strong><sup>n</sup>/<sub>2</sub> +1 </strong>, where n is the number of
	 * elements.
	 * 
	 * @param index
	 *            the position of the list node.
	 * @return the desired list node
	 * @throws IndexOutOfBoundsException
	 *             if the index argument is invalid.
	 */
	private ListNode getNode(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("The index is incorrect!");
		}
		ListNode node;
		if (index < size / 2) {
			// skip the first nodes
			node = first;
			for (int i = 0; i < index; ++i) {
				node = node.next;
			}
		} else {
			// skip the last nodes
			node = last;
			for (int i = 0; i < size - index - 1; ++i) {
				node = node.previous;
			}
		}
		return node;
	}
}
