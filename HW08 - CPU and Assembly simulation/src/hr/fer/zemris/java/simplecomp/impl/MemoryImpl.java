package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * Implementation of the {@link Memory} used in a {@link Computer}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class MemoryImpl implements Memory {
	/**
	 * The memory array used in this {@link Memory}.
	 */
	private Object[] memory;

	/**
	 * Creates the memory with the given size.
	 * 
	 * @param size
	 *            the size of the memory
	 */
	public MemoryImpl(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("The size of the memory should be greater than 0!");
		}
		memory = new Object[size];
	}

	@Override
	public void setLocation(int location, Object value) {
		checkLocation(location);
		memory[location] = value;
	}

	@Override
	public Object getLocation(int location) {
		checkLocation(location);
		return memory[location];
	}

	/**
	 * Checks if the given memory location is in the memory.
	 * 
	 * @param location
	 *            the memory location.
	 * @throws IndexOutOfBoundsException
	 *             if the given memory location does not exist (less then 0 or
	 *             greater then the maximal address).
	 */
	private void checkLocation(int location) {
		if (location < 0 || location > memory.length) {
			throw new IndexOutOfBoundsException(
				"The given location is invalid! Expected values are greater or equal then 0, and less then "
						+ memory.length + " but " + location + " was given");
		}
	}
}
