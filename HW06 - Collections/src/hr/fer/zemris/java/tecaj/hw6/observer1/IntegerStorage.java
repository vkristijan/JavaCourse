package hr.fer.zemris.java.tecaj.hw6.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides storage for a single integer value. This class can have multiple
 * {@link IntegerStorageObserver} that observe every value change.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class IntegerStorage {
	/**
	 * The value stored in this {@code IntegerStorage}.
	 */
	private int value;
	/**
	 * List of observers for this {@code IntegerStorage}.
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Creates a new {@code IntegerStorage} and sets it's value to the one given
	 * in the argument.
	 * 
	 * @param initialValue
	 *            the initial value for this {@code IntegerStorage}.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Adds a new {@link IntegerStorageObserver} to the list of observers for
	 * this {@code IntegerStorage}.
	 * 
	 * @param observer
	 *            the observer to be added.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observers == null) {
			observers = new ArrayList<>();
		} else {
			observers = new ArrayList<>(observers);
		}

		if (observers.contains(observer)) return;
		observers.add(observer);
	}

	/**
	 * Removes the {@link IntegerStorageObserver} from the list of observers for
	 * this {@code IntegerStorage}.
	 * 
	 * @param observer
	 *            the observer to be removed.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers = new ArrayList<>(observers);
		observers.remove(observer);
	}

	/**
	 * Removes all the observers from this {@link IntegerStorage}.
	 */
	public void clearObservers() {
		if (observers != null) {
			observers = new ArrayList<>(observers);
			observers.clear();
		}
	}

	/**
	 * Returns the value currently stored in this {@code IntegerStorage}.
	 * 
	 * @return the value currently stored in this {@code IntegerStorage}.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value stored in this {@code IntegerStorage} to the one given in
	 * the argument if it is different than the old value. Notifies all the
	 * observers that a change occurred.
	 * 
	 * @param value
	 *            the new value to be set
	 */
	public void setValue(int value) {
		if (this.value != value) {
			this.value = value;
			if (observers != null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(this);
				}
			}
		}
	}
}
