package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Utility class for stack operations.<br>
 * Supports push and pop operations.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class StackUtil {
	/**
	 * Inserts the given value to the end of the stack. Decrements the register
	 * holding the stack pointer.
	 * 
	 * @param computer
	 *            the computer on which the operation is done.
	 * @param value
	 *            the value to be pushed onto the stack.
	 */
	public static void push(Computer computer, Object value) {
		int address = (int) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);

		computer.getMemory().setLocation(address, value);
		address--;

		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, address);
	}

	/**
	 * Returns the value from the top of the stack. Increments the register
	 * holding the stack pointer.
	 * 
	 * @param computer
	 *            the computer on which the operation is done.
	 * @return the value on top of the stack.
	 */
	public static Object pop(Computer computer) {
		int address = (int) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
		address++;

		Object value = computer.getMemory().getLocation(address);
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, address);

		return value;
	}
}
