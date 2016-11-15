package hr.fer.zemris.java.custom.collections;

/**
 * This class implements array that can automatically change it's size when new
 * elements are added. Duplicate elements are allowed, but storage of null
 * references is not allowed.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {
	/**
	 * The default initial capacity of the collection if nothing else is given.
	 */
	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * Current size of collection (number of elements actually stored).
	 */
	private int size;

	/**
	 * Current maximum capacity of allocated array of object references.
	 */
	private int capacity;

	/**
	 * An array of object references which length is determined by capacity
	 * variable.
	 */
	private Object[] elements;

	/**
	 * This constructor creates a new empty
	 * <code> ArrayIndexedCollection </code> with the initial capacity equal to
	 * 16.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * This constructor creates a new <code> ArrayIndexedCollection </code> with
	 * the initial capacity equal to the number given in the argument. The
	 * provided initial capacity has to be greater or equal to 1.
	 * 
	 * @param initialCapacity
	 *            the desired initial capacity for the collection
	 * @throws IllegalArgumentException
	 *             if the initial capacity is smaller than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		super();
		if (initialCapacity < 1) {
			throw new IllegalArgumentException(
				"The initial capacity should be greater or equal to 1.");
		}
		size = 0;
		capacity = initialCapacity;
		elements = new Object[initialCapacity];
	}

	/**
	 * This constructor creates a new <code> ArrayIndexedCollection </code> with
	 * the initial capacity equal to . It also copies all the elements of
	 * another collection and adds them into the new created one. All the
	 * elements in the other collection have to follow the rules for the
	 * elements of this collection, otherwise a IllegalArgumentException will
	 * occur.
	 * 
	 * @param other
	 *            the collection which elements are copied into this newly
	 *            constructed collection
	 * 
	 * @throws IllegalArgumentException
	 *             if there is an invalid element in the other collection
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}

	/**
	 * This constructor creates a new <code> ArrayIndexedCollection </code> with
	 * the initial capacity equal to the number given in the argument. The
	 * provided initial capacity has to be greater or equal to 1. It also copies
	 * all the elements of another collection and adds them into the new created
	 * one. All the elements in the other collection have to follow the rules
	 * for the elements of this collection, otherwise a IllegalArgumentException
	 * will occur.
	 * 
	 * @param other
	 *            the collection which elements are copied into this newly
	 *            constructed collection
	 * @param initialCapacity
	 *            the desired initial capacity for the collection
	 * 
	 * @throws IllegalArgumentException
	 *             if there is an invalid element in the other collection or if
	 *             the given capacity is smaller than 1
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this(initialCapacity);
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
	 * Returns true only if the collection contains the given value as
	 * determined by equals method and removes the first occurrence of it. The
	 * collection will stay unchanged if it doesn't contain the given value.
	 * <br>
	 * This implementation has a linear time complexity.
	 */
	@Override
	public boolean remove(Object value) {
		int position = indexOf(value);
		if (position == -1) {
			return false;
		}

		for (int i = position + 1; i < size; ++i) {
			elements[i - 1] = elements[i];
		}
		// remove the last element from the collection and change the size
		size--;
		elements[size] = null;
		return true;
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
		remove(get(index));
	}

	/**
	 * Method calls processor.process() for each element of this collection.
	 * <br>
	 * This implementation goes from the element with the smallest index o the
	 * element with the greatest index.
	 * 
	 * @throws NullPointerException
	 *             if the <code>Processor</code> reference is null.
	 */
	@Override
	public void forEach(Processor processor) {
		if (processor == null) {
			throw new NullPointerException("The processor refference must not be null!");
		}
		for (int i = 0; i < size; ++i) {
			processor.process(elements[i]);
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

		for (int i = 0; i < size; ++i) {
			tmpArray[i] = elements[i];
		}
		return tmpArray;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of
	 * the given value or -1 if the value is not found. <br>
	 * The time complexity of this method is linear on the number of elements in
	 * the collection.
	 * 
	 * @param value
	 *            the value to be found in the collection
	 * @return the index of the object if it exists in the collection, -1
	 *         otherwise.
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < size; ++i) {
			if (elements[i].equals(value)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Returns the object that is stored in backing array at position index.
	 * Valid indexes are 0 to size-1.
	 * 
	 * @param index
	 *            the position index of the element
	 * @return the object at the specified position in the array
	 * @throws IndexOutOfBoundsException
	 *             if the index is invalid
	 */
	public Object get(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException("Index should be a positive number!");
		}
		if (index >= size) {
			throw new IndexOutOfBoundsException(
				"Index should be a smaller than " + size + "!");
		}
		return elements[index];
	}

	/**
	 * Adds the given object into this collection (reference is added into first
	 * empty place in the collection; if the collection is full, it will be
	 * reallocated by doubling its size). It's not allowed to add null values
	 * into the collection, otherwise an exception will occur. <br>
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
	 * Removes all elements from this collection, by changing their values to
	 * null.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; ++i) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in
	 * array, elements at position and at greater positions are shifted one
	 * place toward the end, so that an empty place is created at position. The
	 * legal positions are 0 to size. If position is invalid, an
	 * <code>IndexOutOfboundsException</code> is thrown. If the collection
	 * already has the maximum number of elements it's capacity will be doubled.
	 * <br>
	 * The time complexity of this operation is linear on the number of elements
	 * in the list.
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

		// resize the array if necessary
		if (size == capacity) {
			capacity *= 2;
			Object[] tmpElements = new Object[capacity];
			// copy the first few old elements
			for (int i = 0; i < position; ++i) {
				tmpElements[i] = elements[i];
			}
			// insert the new element
			tmpElements[position] = value;
			// copy the rest of the elements
			for (int i = position; i < size; ++i) {
				tmpElements[i + 1] = elements[i];
			}
			elements = tmpElements;
		} else {
			// move the last elements
			for (int i = size; i > position; --i) {
				elements[i] = elements[i - 1];
			}
			// insert the new element
			elements[position] = value;
		}
		size++;
	}
}
