package hr.fer.zemris.java.tecaj.hw6.observer2.observers;

import hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorageChange;
import hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorageObserver;

/**
 * An observer that prints the square of the value that is currently in the
 * {@link IntegerStorageChange} to the standard system output.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int value = istorage.getNewValue();
		System.out.print("Provided new value: " + value);
		System.out.println(" square is " + value * value);
	}

}
