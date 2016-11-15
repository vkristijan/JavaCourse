package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This {@link Instruction} gets a single {@link InstructionArgument}, a
 * register index. The {@link Instruction} reads the value from the register,
 * decrements it by 1 and stores it back to the register.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class InstrDecrement implements Instruction {
	/**
	 * The value of the first argument.
	 */
	private int registerIndex;

	/**
	 * Creates a new {@link InstrDecrement} command with the given list of
	 * arguments.
	 * 
	 * @param arguments
	 *            a list of {@link InstructionArgument}s used in this
	 *            {@link Instruction}.
	 * @throws IllegalArgumentException
	 *             if the number of arguments in the list is wrong, or if some
	 *             of them are in a wrong type.
	 */
	public InstrDecrement(List<InstructionArgument> arguments) {
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
		Integer value = (Integer) computer.getRegisters().getRegisterValue(registerIndex);
		computer.getRegisters().setRegisterValue(registerIndex, value - 1);
		return false;
	}

}
