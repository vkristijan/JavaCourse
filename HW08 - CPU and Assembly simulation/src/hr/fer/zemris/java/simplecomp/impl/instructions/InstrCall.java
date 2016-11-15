package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This {@link Instruction} gets 1 {@link InstructionArgument}, the address of
 * the function that is called. The {@link Instruction} will store the current
 * program counter onto the stack and jump to the position given in the
 * argument.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class InstrCall implements Instruction {
	/**
	 * The value of the first argument.
	 */
	private int address;

	/**
	 * Creates a new {@link InstrCall} command with the given list of arguments.
	 * 
	 * @param arguments
	 *            a list of {@link InstructionArgument}s used in this
	 *            {@link Instruction}.
	 * @throws IllegalArgumentException
	 *             if the number of arguments in the list is wrong, or if some
	 *             of them are in a wrong type.
	 */
	public InstrCall(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		this.address = (int) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		StackUtil.push(computer, computer.getRegisters().getProgramCounter());

		computer.getRegisters().setProgramCounter(address);
		return false;
	}
}
