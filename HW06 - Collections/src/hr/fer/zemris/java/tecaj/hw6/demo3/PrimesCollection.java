package hr.fer.zemris.java.tecaj.hw6.demo3;

import java.util.Iterator;

/**
 * A collection that has prime numbers in it. The numbers are not really stored
 * in the memory, but calculated on the go.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class PrimesCollection implements Iterable<Integer> {
	/**
	 * The number of prime numbers to be stored in this collection.
	 */
	private final int count;

	/**
	 * Creates a new {@code PrimesCollection} with the number of prime numbers
	 * to be stored equal to the number given in the argument.
	 * 
	 * @param count
	 *            the number of prime numbers in this collection
	 */
	public PrimesCollection(int count) {
		this.count = count;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimeIterator(count);
	}

	/**
	 * Implementation of an {@link Iterator} for the {@code PrimesCollection}.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 */
	private static class PrimeIterator implements Iterator<Integer> {
		/** The number of prime numbers to be visited. */
		private final int count;
		/** The number of prime numbers already visited. */
		private int visited;
		/** The current prime number. */
		private int current;

		/**
		 * Creates a new {@code PrimeIterator}.
		 * 
		 * @param count
		 *            the number of prime numbers to be visited.
		 */
		public PrimeIterator(int count) {
			this.count = count;
			current = 2;
		}

		@Override
		public boolean hasNext() {
			return visited < count;
		}

		@Override
		public Integer next() {
			int oldValue = current;

			if (oldValue == 2) {
				current++;
			} else {
				do {
					current += 2;
				} while (!isPrime(current));
			}

			visited++;
			return oldValue;
		}

		/**
		 * Checks if the given number is prime or not.
		 * 
		 * @param x
		 *            the number to be checked.
		 * @return a {@code Boolean} value: <strong>true</strong> if the number
		 *         is prime, <strong>false</strong> otherwise.
		 */
		private static boolean isPrime(int x) {
			if (x == 1) return false;
			if (x % 2 == 0) return false;

			int root = (int) Math.sqrt(x);
			for (int i = 3; i <= root; i += 2) {
				if (x % i == 0) return false;
			}

			return true;
		}
	}

}
