package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Stores a list of elements and calculates the median. 
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 * @param <V>
 *            the value type of the elements. The elements must be
 *            {@code Comparable}.
 */
public class LikeMedian<V extends Comparable<V>> implements Iterable<V> {
	/**
	 * A list of all the elements in this {@code LikeMedian}.
	 */
	private List<V> elements;

	/**
	 * Creates a new {@code LikeMedian}.
	 */
	public LikeMedian() {
		elements = new ArrayList<>();
	}

	/**
	 * Adds the element given in the argument to the elements of this
	 * {@code LikeMedian}.
	 * 
	 * @param element
	 *            the element to be added.
	 */
	public void add(V element) {
		elements.add(element);
	}

	/**
	 * Returns the median of the elements in this {@link LikeMedian}.
	 * 
	 * @return the median of the elements in this {@link LikeMedian}.
	 */
	public Optional<V> get() {
		List<V> tmpElements = new ArrayList<>(elements);
		Collections.sort(tmpElements);
		int size = elements.size();

		if (size == 0) return Optional.empty();

		if (size % 2 == 0) {
			return Optional.of(tmpElements.get(size / 2 - 1));
		} else {
			return Optional.of(tmpElements.get(size / 2));
		}
	}

	@Override
	public Iterator<V> iterator() {
		return elements.iterator();
	}

}
