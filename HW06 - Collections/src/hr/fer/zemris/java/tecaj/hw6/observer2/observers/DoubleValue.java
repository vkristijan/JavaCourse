package hr.fer.zemris.java.tecaj.hw6.observer2.observers;

import hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorageChange;
import hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorageObserver;

/**
 * Prints the value that is stored in the {@link IntegerStorageChange} to the
 * standard system output every time a change occurs. This <code>observer</code>
 * is removed once the predefined amount of changes occurred.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	/**
	 * Counts the number of changes that should still occur in the
	 * {@link IntegerStorage} before this <code>observer</code> is removed.
	 */
	private int remainingChanges;

	/**
	 * Creates a new {@code DoubleValue} observer, and sets remainingChanges to
	 * the value given in the argument.
	 * 
	 * @param remainingChanges
	 *            the number of changes that should still occur in the
	 *            {@link IntegerStorageChange} before this <code>observer</code>
	 *            is removed.
	 */
	public DoubleValue(int remainingChanges) {
		this.remainingChanges = remainingChanges;

	}

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.println("Double value: " + 2 * istorage.getNewValue());
		remainingChanges--;

		if (remainingChanges == 0) {
			istorage.getStorage().removeObserver(this);
		}
	}

}
