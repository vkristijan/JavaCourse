package hr.fer.zemris.jmbag0036483282.cmdapps.trazilica;

/**
 * A class that can store two values. The class has a defined comparator that
 * compares the first values and then the second values.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 * @param <K>
 *            the type of the first value.
 * @param <V>
 *            the type of the second value.
 */
public class Pair<K extends Comparable<K>, V extends Comparable<V>> implements Comparable<Pair<K, V>>{
	
	/** The first value. */
	private K first;

	/** The second value. */
	private V second;

	/**
	 * Instantiates a new pair.
	 *
	 * @param first
	 *            the first value
	 * @param second
	 *            the second value
	 */
	public Pair(K first, V second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Gets the first value.
	 *
	 * @return the first value
	 */
	public K getFirst() {
		return first;
	}

	/**
	 * Sets the first value.
	 *
	 * @param first
	 *            the new first value
	 */
	public void setFirst(K first) {
		this.first = first;
	}

	/**
	 * Gets the second value.
	 *
	 * @return the second value
	 */
	public V getSecond() {
		return second;
	}

	/**
	 * Sets the second value.
	 *
	 * @param second
	 *            the new second value
	 */
	public void setSecond(V second) {
		this.second = second;
	}

	@Override
	public int compareTo(Pair<K, V> o) {
		if (first.compareTo(o.first) != 0){
			return -first.compareTo(o.first);
		}
		
		return -second.compareTo(o.second);
	}
}
