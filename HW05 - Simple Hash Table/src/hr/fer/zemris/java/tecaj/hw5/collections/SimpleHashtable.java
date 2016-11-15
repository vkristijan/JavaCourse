package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

/**
 * A collection that can store elements in a hash table. The hash of an element
 * is calculated based on the key. It is allowed to add null elements in the
 * collection, but the key is not allowed to be null. It is not possible to
 * store elements with duplicate keys into the collection. When an element with
 * a key that already exists in the collection is being added, the old value for
 * the key is just updated.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 * @param <K>
 *            the type of the key in the {@code SimpleHashtable}
 * @param <V>
 *            the type of the value in the {@code SimpleHashtable}
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	/** Number of elements in this collection. */
	private int size;
	/** Capacity of the {link {@link SimpleHashtable#table}. */
	private int capacity;
	/**
	 * Array of entries in this {@code SimpleHashtable}. Every element of this
	 * table is the head of a linked list, so that there can be multiple items
	 * for every index of the list.
	 */
	TableEntry<K, V>[] table;
	/**
	 * The default initial capacity for this collection, if nothing else was
	 * given.
	 */
	private static final int DEFAULT_CAPACITY = 16;
	/**
	 * The maximum allowed load factor of the {@code SimpleHashtable} before the
	 * capacity is doubled and all the elements are rehashed.
	 */
	private static final double REHASH_LOAD_FACTOR = 0.75;
	/** the current modification count for this {@code SimpleHashtable}. */
	private int modCount;

	/**
	 * Creates an empty new {@code SimpleHashtable} with size equal to
	 * {@link SimpleHashtable#DEFAULT_CAPACITY}.
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Creates an empty new {@code SimpleHashtable} with size equal to the first
	 * power of two that is greater than or equal to the capacity given in the
	 * argument.
	 * 
	 * @param capacity
	 *            the wanted capacity.
	 * @throws IllegalArgumentException
	 *             if the given capacity is less or equal to zero.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		checkGreaterThan(capacity, 0, "capacity");

		int firstPowOfTwo = 1;
		while (firstPowOfTwo < capacity) {
			firstPowOfTwo *= 2;
		}
		this.capacity = firstPowOfTwo;

		table = (TableEntry<K, V>[]) new TableEntry[this.capacity];
		modCount = 0;
	}

	/**
	 * Inserts the given key and value into this {@code SimpleHashtable}. If
	 * there already exists an item with the specified key, the old value is
	 * updated. If there is no item with the given key, a new {@code TableEntry}
	 * is created with the given key and value.
	 * 
	 * @param key
	 *            the key of the element to be inserted.
	 * @param value
	 *            the value of the element to be inserted.
	 * @throws IllegalArgumentException
	 *             if the given key is null.
	 */
	public void put(K key, V value) {
		checkArgumentNull(key, "key");

		TableEntry<K, V> entry = new TableEntry<>(key, value, null);
		int currentSize = size;

		put(entry, table);
		if (size > currentSize) modCount++;

		rehash();
	}

	/**
	 * Checks if the given argument is null.
	 * 
	 * @param argument
	 *            the argument that has to be checked.
	 * @param argName
	 *            the name of the argument so it can be printed in an error
	 *            message.
	 * @throws IllegalArgumentException
	 *             if the given argument was null
	 */
	private static void checkArgumentNull(Object argument, String argName) {
		if (argument == null) {
			throw new IllegalArgumentException(
				"The given value for " + argName + " should not be null!");
		}
	}

	/**
	 * Checks if the given argument is greater than the value given in the
	 * argument.
	 * 
	 * @param argument
	 *            the argument that has to be checked.
	 * @param value
	 *            the minimal value for the argument
	 * @param argName
	 *            the name of the argument so it can be printed in an error
	 *            message.
	 * @throws IllegalArgumentException
	 *             if the given argument was not greater than the given value
	 */
	private static void checkGreaterThan(int argument, int value, String argName) {
		if (argument <= value) {
			throw new IllegalArgumentException(argName + " is expected to be greater than "
					+ value + ", but the given walue was" + argument);
		}
	}

	/**
	 * Inserts the given {@code TableEntry} into the table given in the
	 * argument. If there already exists an item with the specified key, the old
	 * value is updated. If there is no item with the given key.
	 * 
	 * @param entry
	 *            the {@code TableEntry} to be inserted.
	 * @param table
	 *            the table in which the {@code TableEntry} should be inserted.
	 * @throws IllegalArgumentException
	 *             if the given key or table are null.
	 */
	private void put(TableEntry<K, V> entry, TableEntry<K, V>[] table) {
		checkArgumentNull(entry, "table entry");
		checkArgumentNull(table, "table entry table");
		entry.next = null;

		int capacity = table.length;
		int hash = getHash(entry.key, capacity);

		if (table[hash] == null) {
			table[hash] = entry;
			size++;
			return;
		}

		TableEntry<K, V> tmpEntry = table[hash];
		while (tmpEntry.next != null) {
			if (tmpEntry.key.equals(entry.key)) {
				tmpEntry.value = entry.value;
				return;
			}
			tmpEntry = tmpEntry.next;
		}

		if (tmpEntry.key.equals(entry.key)) {
			tmpEntry.value = entry.value;
		} else {
			tmpEntry.next = entry;
			size++;
		}
	}

	/**
	 * Returns the value of the element with the given key. If the value is not
	 * found null is returned.
	 * 
	 * @param key
	 *            the key of the wanted value.
	 * @return the value of the element with the given key.
	 * @throws IllegalArgumentException
	 *             if the given key is null
	 */
	public V get(Object key) {
		if (key == null) return null;
		TableEntry<K, V> entry = getEntry(key);

		if (entry == null) return null;
		return entry.value;
	}

	/**
	 * Checks if this collection contains a value with the given key.
	 * 
	 * @param key
	 *            the key to be found.
	 * @return a boolean value: <strong>true</strong> if the key is in this
	 *         collection, <strong>false</strong> otherwise.
	 */
	public boolean containsKey(Object key) {
		if (key == null) return false;
		TableEntry<K, V> entry = getEntry(key);

		if (entry == null) return false;
		return true;
	}

	/**
	 * Returns the {@code TableEntry} that has the given key, or null if there
	 * is no such entry.
	 * 
	 * @param key
	 *            the hey of the entry to be found.
	 * @return the {@code TableEntry} with the given key.
	 * @throws IllegalArgumentException
	 *             if the given key is null
	 */
	private TableEntry<K, V> getEntry(Object key) {
		checkArgumentNull(key, "key");

		int hash = getHash(key, capacity);

		TableEntry<K, V> entry = table[hash];
		while (entry != null) {
			if (entry.key.equals(key)) {
				return entry;
			}
			entry = entry.next;
		}

		return null;
	}

	/**
	 * Returns the number of elements in this collection.
	 * 
	 * @return the number of elements in this collection.
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks if this {@code SimpleHashtable} contains the given value.
	 * 
	 * @param value
	 *            the value to be fond in this {@code SimpleHashtable}
	 * @return a boolean value <strong>true</strong> if the value is found in
	 *         this {@code SimpleHashtable}, <strong>false</strong> otherwise.
	 */
	public boolean containsValue(Object value) {
		for (TableEntry<K, V> entry : this) {
			if (entry.value == null) {
				if (value == null) {
					return true;
				} else {
					continue;
				}
			}
			if (entry.value.equals(value)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Removes the entry with the key equal to the one in the argument. The
	 * collection will stay unchanged if there is no entry with the given key.
	 * 
	 * @param key
	 *            the key of the entry to be deleted
	 */
	public void remove(Object key) {
		if (key == null) return;
		int hash = getHash(key, capacity);

		TableEntry<K, V> entry = table[hash];
		if (entry == null) return;
		if (entry.key.equals(key)) {
			table[hash] = entry.next;
			size--;
			modCount++;
			return;
		}

		while (entry.next != null) {
			if (entry.next.key.equals(key)) {
				entry.next = entry.next.next;
				size--;
				modCount++;
				return;
			}
		}
	}

	/**
	 * Calculates the hash value of the given key. The value calculated by this
	 * function can be used to index the key in the table array.
	 * 
	 * @param key
	 *            the key that we are calculating the hash for
	 * @param capacity
	 *            the capacity of the array where this hash is used. The give
	 *            capacity is used as a modulo for the hash, meaning that the
	 *            calculated hash will always be in a range from 0 to the given
	 *            capacity.
	 * @return the hash value of the key
	 * @throws IllegalArgumentException
	 *             if the given key is null
	 */
	private int getHash(Object key, int capacity) {
		checkArgumentNull(key, "key");
		checkGreaterThan(capacity, 1, "capacity");

		int hash = key.hashCode();
		hash = Math.abs(hash);
		hash %= capacity;

		return hash;
	}

	/**
	 * Checks if this collection is empty.
	 * 
	 * @return a {@code Boolean} value: <strong>true</strong> if the collection
	 *         is empty, <strong>false</strong> otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public String toString() {
		StringJoiner string = new StringJoiner(", ", "[", "]");

		for (TableEntry<K, V> entry : this) {
			string.add(entry.key + "=" + entry.value);
		}
		return string.toString();
	}

	/**
	 * Removes all the elements from this {@code SimpleHashtable}. The capacity
	 * of the collection remains unchanged after this operation.
	 */
	public void clear() {
		for (int i = 0; i < capacity; ++i) {
			table[i] = null;
		}

		size = 0;
	}

	/**
	 * If this {@code SimpleHashtable} has more elements than of its capacity
	 */
	private void rehash() {
		if ((double) size / capacity < REHASH_LOAD_FACTOR) {
			return;
		}

		modCount++;
		size = 0;
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[capacity * 2];
		for (TableEntry<K, V> entry : this) {
			put(entry, newTable);
		}
		table = newTable;
		this.capacity *= 2;
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * An implementation of an {@code Iterator} for the {@code SimpleHashtable}.
	 * Allows the user to iterate through every element of the
	 * {@code SimpleHashtable}, but does not specify the order in which the
	 * elements will be accessed.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 */
	private class IteratorImpl implements Iterator<TableEntry<K, V>> {
		/** The current index in the {@code SimpleHashtable#table} */
		private int index;
		/** The current entry in the {@code SimpleHashtable}. */
		private TableEntry<K, V> currentEntry;
		/** The next entry in the {@code SimpleHashtable}. */
		private TableEntry<K, V> nextEntry;
		/**
		 * The {@link SimpleHashtable#modCount} of the {@code SimpleHashtable}
		 * when the iterator was first created.
		 */
		private int startingModCount;

		/**
		 * Creates a new iterator for the {@code SimpleHashtable} and calculates
		 * the first entry in the collection.
		 */
		protected IteratorImpl() {
			super();

			index = 0;
			while (index < capacity && table[index] == null) {
				index++;
			}
			if (index < capacity) {
				nextEntry = table[index];
			}
			startingModCount = modCount;
		}

		/**
		 * Checks if the current modification counter of the
		 * {@code SimpleHashtable} is equal to the one when this iterator was
		 * first created.
		 */
		private void checkModCount() {
			if (modCount != startingModCount) {
				throw new ConcurrentModificationException(
					"The collection was modified after creating this iterator!");
			}
		}

		@Override
		public boolean hasNext() {
			checkModCount();
			return nextEntry != null;
		}

		@Override
		public TableEntry<K, V> next() {
			checkModCount();
			if (!hasNext()) {
				throw new NoSuchElementException("Ne postoji sljedeci element!");
			}

			currentEntry = nextEntry;
			if (nextEntry.next != null) {
				nextEntry = nextEntry.next;
			} else {
				nextEntry = null;
				index++;
				while (index < capacity && table[index] == null) {
					index++;
				}
				if (index < capacity) {
					nextEntry = table[index];
				}
			}

			return currentEntry;
		}

		@Override
		public void remove() {
			checkModCount();
			if (currentEntry == null) {
				throw new IllegalStateException("The current element was already removed!");
			}

			SimpleHashtable.this.remove(currentEntry.key);
			currentEntry = null;
			startingModCount = modCount;
		}

	}

	/**
	 * A table entry element for the {@link SimpleHashtable} collection. Every
	 * {@code TableEntry} has its key and value. The table entry is implemented
	 * as a linked list, which allows the user to add as many elements as he
	 * needs.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 * 
	 * @param <K>
	 *            The type of the key of the {@code TableEntry}.
	 * @param <V>
	 *            The type of the value in the {@code TableEntry}.
	 */
	public static class TableEntry<K, V> {
		/** The key of this {@code TableEntry}. */
		private K key;
		/** The value of this {@code TableEntry}. */
		private V value;
		/** Pointer to the next {@code TableEntry} in this linked list. */
		private TableEntry<K, V> next;

		/**
		 * Creates a new {@code TableEntry} with the specified key and value.
		 * The created {@code TableEntry} has a pointer to a next
		 * {@code TableEntry} that is also given as an argument.
		 * 
		 * @param key
		 *            key of this {@code TableEntry}
		 * @param value
		 *            value of this {@code TableEntry}
		 * @param next
		 *            pointer to the next {@code TableEntry}
		 * @throws IllegalArgumentException
		 *             if the given key is null
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			super();
			checkArgumentNull(key, "TableEntry key");
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Returns the value of this {@code TableEntry}.
		 * 
		 * @return the value of this {@code TableEntry}.
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets the value of this {@code TableEntry} to the one given as the
		 * argument.
		 * 
		 * @param value
		 *            the new value for this {@code TableEntry}.
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Returns the key of this {@code TableEntry}.
		 * 
		 * @return the key of this {@code TableEntry}.
		 */
		public K getKey() {
			return key;
		}

		@Override
		public String toString() {
			return "TableEntry [key=" + key + ", value=" + value + "]";
		}
	}
}
