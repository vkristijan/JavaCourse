package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This {@link Instruction} gets 1 {@link InstructionArgument}s, the index of
 * the register that should be used to store the value from the stack. The
 * {@link Instruction} will store the value from the memory location of the
 * current stack top, and increment the pointer to the stack top.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class InstrPop implements Instruction {
	/**
	 * The value of the first argument.
	 */
	private int registerIndex;

	/**
	 * Creates a new {@link InstrPop} command with the given list of arguments.
	 * 
	 * @param arguments
	 *            a list of {@link InstructionArgument}s used in this
	 *            {@link Instruction}.
	 * @throws IllegalArgumentException
	 *             if the number of arguments in the list is wrong, or if some
	 *             of them are in a wrong type.
	 */
	public InstrPop(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if (!arguments.get(0).isRegister() || RegisterUtil.isIndirect((Integer) arguments.get(0).getValue())) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		this.registerIndex = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		int index = RegisterUtil.getRegisterIndex(registerIndex);
		Object value = StackUtil.pop(computer);

		computer.getRegisters().setRegisterValue(index, value);
		return false;
	}
}
