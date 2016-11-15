package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This {@link Instruction} gets no {@link InstructionArgument}s. The
 * {@link Instruction} will read the value written on top of the stack and store
 * it in the program counter.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class InstrRet implements Instruction {
	/**
	 * Creates a new {@link InstrRet} command with the given list of arguments.
	 * 
	 * @param arguments
	 *            a list of {@link InstructionArgument}s used in this
	 *            {@link Instruction}.
	 * @throws IllegalArgumentException
	 *             if the number of arguments in the list is wrong, or if some
	 *             of them are in a wrong type.
	 */
	public InstrRet(List<InstructionArgument> arguments) {
		if (arguments.size() != 0) {
			throw new IllegalArgumentException("Expected 0 arguments!");
		}
	}

	@Override
	public boolean execute(Computer computer) {
		int pc = (Integer) StackUtil.pop(computer);

		computer.getRegisters().setProgramCounter(pc);
		return false;
	}
}
