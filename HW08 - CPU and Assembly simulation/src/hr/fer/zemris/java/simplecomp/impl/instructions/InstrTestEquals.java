package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This {@link Instruction} gets 2 {@link InstructionArgument}s, the indexes of
 * two register . The {@link Instruction} will set the flag in the registers to
 * true if the two given registers hold an equal value. The flag will be set to
 * false otherwise.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class InstrTestEquals implements Instruction {
	/**
	 * The value of the first argument.
	 */
	private int registerIndex1;
	/**
	 * The value of the second argument.
	 */
	private int registerIndex2;

	/**
	 * Creates a new {@link InstrTestEquals} command with the given list of
	 * arguments.
	 * 
	 * @param arguments
	 *            a list of {@link InstructionArgument}s used in this
	 *            {@link Instruction}.
	 * @throws IllegalArgumentException
	 *             if the number of arguments in the list is wrong, or if some
	 *             of them are in a wrong type.
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		for (int i = 0; i < 2; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException("Type mismatch for argument " + i + "!");
			}
		}
		this.registerIndex1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.registerIndex2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(registerIndex1);
		Object value2 = computer.getRegisters().getRegisterValue(registerIndex2);
		boolean equal;
		if (value1 == null) {
			equal = value1 == value2;
		} else {
			equal = value1.equals(value2);
		}
		computer.getRegisters().setFlag(equal);
		return false;
	}
}
