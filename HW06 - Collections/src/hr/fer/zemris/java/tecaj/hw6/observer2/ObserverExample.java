package hr.fer.zemris.java.tecaj.hw6.observer2;

import hr.fer.zemris.java.tecaj.hw6.observer2.observers.ChangeCounter;
import hr.fer.zemris.java.tecaj.hw6.observer2.observers.DoubleValue;
import hr.fer.zemris.java.tecaj.hw6.observer2.observers.SquareValue;

/**
 * Demonstration for {@link IntegerStorageObserver} and {@link IntegerStorage}.
 * Changes the value in the {@code IntegerStorage} to some sample values and
 * defines a few {@code IntegerStorageObserver}s.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class ObserverExample {
	/**
	 * The program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);

		IntegerStorageObserver observer = new SquareValue();

		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);

		istorage.removeObserver(observer);

		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));

		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}