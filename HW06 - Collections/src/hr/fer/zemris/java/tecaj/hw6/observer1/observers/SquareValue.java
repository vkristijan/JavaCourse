package hr.fer.zemris.java.tecaj.hw6.observer1.observers;

import hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw6.observer1.IntegerStorageObserver;

/**
 * An observer that prints the square of the value that is currently in the
 * {@link IntegerStorage} to the standard system output.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.print("Provided new value: " + value);
		System.out.println(" square is " + value * value);
	}

}
