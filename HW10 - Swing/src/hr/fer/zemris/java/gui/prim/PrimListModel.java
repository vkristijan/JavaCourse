package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * A {@link ListModel} that displays prime numbers. The model can generate new
 * prime numbers.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class PrimListModel implements ListModel<Integer> {
	/**
	 * A list of all the elements in this model.
	 */
	private List<Integer> elements;
	/**
	 * A list of {@link ListDataListener}s in this model.
	 */
	private List<ListDataListener> listeners;

	/**
	 * The last prime number that was added to the model.
	 */
	private int lastNumber;

	/**
	 * Creates a new {@link PrimListModel} that starts from 1.
	 */
	public PrimListModel() {
		lastNumber = 1;
		listeners = new ArrayList<>();
		elements = new ArrayList<>();
		elements.add(lastNumber);
	}

	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public Integer getElementAt(int index) {
		if (index < 0 || index >= elements.size()) {
			throw new IndexOutOfBoundsException("The given index is invalid, there are " + elements.size());
		}

		return elements.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	/**
	 * Calculates the next prime number and adds it to the model.
	 */
	public void next() {
		int index = elements.size();

		lastNumber++;
		while (!isPrime(lastNumber)) {
			lastNumber++;
		}
		;
		elements.add(lastNumber);

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index);
		for (ListDataListener listener : listeners) {
			listener.intervalAdded(event);
		}
	}

	/**
	 * Checks if the given number is prime or not.
	 * 
	 * @param x
	 *            the number that should be checked.
	 * @return a {@link Boolean} value <code>true</code> if x is prime,
	 *         <code>false</code> otherwise.
	 */
	private boolean isPrime(int x) {
		int root = (int) Math.sqrt(x);

		for (int i = 2; i <= root; ++i) {
			if (x % i == 0) {
				return false;
			}
		}

		return true;
	}
}
