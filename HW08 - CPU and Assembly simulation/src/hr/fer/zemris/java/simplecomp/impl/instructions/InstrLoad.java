package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This {@link Instruction} gets 2 {@link InstructionArgument}s, the index of a
 * register and a number. The {@link Instruction} will store the given number in
 * the register.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class InstrLoad implements Instruction {
	/**
	 * The value of the first argument.
	 */
	private int registerIndex;
	/**
	 * The value of the second argument.
	 */
	private int memoryAddress;

	/**
	 * Creates a new {@link InstrLoad} command with the given list of arguments.
	 * 
	 * @param arguments
	 *            a list of {@link InstructionArgument}s used in this
	 *            {@link Instruction}.
	 * @throws IllegalArgumentException
	 *             if the number of arguments in the list is wrong, or if some
	 *             of them are in a wrong type.
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}

		if (!arguments.get(0).isRegister() || RegisterUtil.isIndirect((Integer) arguments.get(0).getValue())) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if (!arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.registerIndex = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.memoryAddress = (Integer) arguments.get(1).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getMemory().getLocation(memoryAddress);
		computer.getRegisters().setRegisterValue(registerIndex, value);
		return false;
	}

}
