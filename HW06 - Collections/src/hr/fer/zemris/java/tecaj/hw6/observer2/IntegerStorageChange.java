package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Stores the last and the current state of the {@link IntegerStorage}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class IntegerStorageChange {
	/**
	 * The {@link IntegerStorage} that was changed.
	 */
	private final IntegerStorage storage;
	/**
	 * The old value of the changed {@code IntegerStorage}.
	 */
	private final int oldValue;
	/**
	 * The new value of the changed {@code IntegerStorage}.
	 */
	private final int newValue;

	/**
	 * Creates a new {@code IntegerStorageChange} with the values given in the
	 * arguments.
	 * 
	 * @param storage
	 *            The {@link IntegerStorage} that was changed.
	 * @param oldValue
	 *            The old value of the changed {@code IntegerStorage}.
	 * @param newValue
	 *            The new value of the changed {@code IntegerStorage}.
	 */
	public IntegerStorageChange(IntegerStorage storage, int oldValue, int newValue) {
		super();
		this.storage = storage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * Creates a new {@code IntegerStorageChange} with the values given in the
	 * arguments.
	 * 
	 * @param storage
	 *            The {@link IntegerStorage} that was changed.
	 * @param oldValue
	 *            The old value of the changed {@code IntegerStorage}.
	 */
	public IntegerStorageChange(IntegerStorage storage, int oldValue) {
		this(storage, oldValue, storage.getValue());
	}

	/**
	 * Returns the {@code IntegerStorage} that was changed.
	 * 
	 * @return the {@code IntegerStorage} that was changed.
	 */
	public IntegerStorage getStorage() {
		return storage;
	}

	/**
	 * Returns the old value of the {@code IntegerStorage}.
	 * 
	 * @return the old value of the {@code IntegerStorage}.
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * Returns the new value of the {@code IntegerStorage}.
	 * 
	 * @return the new value of the {@code IntegerStorage}.
	 */
	public int getNewValue() {
		return newValue;
	}
}
