package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * A simple implementation of {@link Registers} used in a {@link Computer}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class RegistersImpl implements Registers {
	/**
	 * The registers used in this implementation.
	 */
	private Object[] registers;
	/**
	 * Stores the value of the line of code that is currently executing. For
	 * more information
	 * <a href = "https://en.wikipedia.org/wiki/Program_counter"> read this.
	 * </a>
	 */
	private int programCounter;
	/**
	 * The <a href="https://en.wikipedia.org/wiki/FLAGS_register">flag</a> used in this {@link Registers} implementation.
	 */
	private boolean flag;
	
	/**
	 * Creates the registers with the given size.
	 * 
	 * @param regsLen
	 *            the number of registers
	 */
	public RegistersImpl(int regsLen) {
		if (regsLen < 0) {
			throw new IllegalArgumentException("The number of registers should be greater than 0!");
		}
		registers = new Object[regsLen];
	}

	@Override
	public Object getRegisterValue(int index) {
		checkIndex(index);
		return registers[index];
	}

	@Override
	public void setRegisterValue(int index, Object value) {
		checkIndex(index);
		registers[index] = value;
	}

	/**
	 * Checks if the given register index is valid
	 * 
	 * @param index
	 *            the register index.
	 * @throws IndexOutOfBoundsException
	 *             if the given register index does not exist (less then 0 or
	 *             greater then the maximal index).
	 */
	private void checkIndex(int index) {
		if (index < 0 || index > registers.length) {
			throw new IndexOutOfBoundsException(
				"The given index is invalid! Expected values are greater or equal then 0, and less then "
						+ registers.length + " but " + index + " was given");
		}
	}

	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	@Override
	public void setProgramCounter(int value) {
		programCounter = value;
	}

	@Override
	public void incrementProgramCounter() {
		programCounter++;
	}

	@Override
	public boolean getFlag() {
		return flag;
	}

	@Override
	public void setFlag(boolean value) {
		flag = value;
	}

}
