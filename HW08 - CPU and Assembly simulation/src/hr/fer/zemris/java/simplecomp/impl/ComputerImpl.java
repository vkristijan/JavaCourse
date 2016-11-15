package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * An implementation of a simple computer.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class ComputerImpl implements Computer {
	/**
	 * The {@link Registers} used in this {@link Computer}.
	 */
	private Registers registers;
	/**
	 * The {@link Memory} used in this {@link Computer}.
	 */
	private Memory memory;

	/**
	 * Creates a new {@link Computer} with {@link Memory} size equal to the one
	 * given in the argument and {@link Registers} count equal to the one given
	 * in the argument.
	 * 
	 * @param size
	 *            the amount of memory for this computer.
	 * @param regsLen
	 *            the number of registers in this computer.
	 */
	public ComputerImpl(int size, int regsLen) {
		memory = new MemoryImpl(size);
		registers = new RegistersImpl(regsLen);
	}

	@Override
	public Registers getRegisters() {
		return registers;
	}

	@Override
	public Memory getMemory() {
		return memory;
	}

}
