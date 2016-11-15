package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This {@link Instruction} gets 1 {@link InstructionArgument}s, the number that
 * should be stored in the program counter. The {@link Instruction} will set the
 * value in the program counter to the new one, causing the program to execute
 * from the specified line.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class InstrJump implements Instruction {
	/**
	 * The value of the first argument.
	 */
	private int location;

	/**
	 * Creates a new {@link InstrJump} command with the given list of arguments.
	 * 
	 * @param arguments
	 *            a list of {@link InstructionArgument}s used in this
	 *            {@link Instruction}.
	 * @throws IllegalArgumentException
	 *             if the number of arguments in the list is wrong, or if some
	 *             of them are in a wrong type.
	 */
	public InstrJump(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		this.location = (Integer) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(location);
		return false;
	}

}
