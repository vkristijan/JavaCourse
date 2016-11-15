package hr.fer.zemris.java.tecaj.hw6.observer1.observers;

import hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorageObserver;

/**
 * Tracks the number of times the {@link IntegerStorage} has changed it's value
 * and prints the value to the standard system output every time a change
 * occurs.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/**
	 * Counts the number of times the {@link IntegerStorage} was changed.
	 */
	private int count;

	@Override
	public void valueChanged(IntegerStorage istorage) {
		count++;
		System.out.println("Number of value changes since tracking: " + count);
	}

}
