package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This {@link Instruction} terminates the execution of the program on the
 * {@link Computer}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class InstrHalt implements Instruction {
	/**
	 * Creates a new {@link InstrHalt} command with the given list of arguments.
	 * 
	 * @param arguments
	 *            a list of {@link InstructionArgument}s used in this
	 *            {@link Instruction}.
	 * @throws IllegalArgumentException
	 *             if the number of arguments in the list is wrong, or if some
	 *             of them are in a wrong type.
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		if (arguments.size() != 0) {
			throw new IllegalArgumentException("Expected 0 arguments!");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
