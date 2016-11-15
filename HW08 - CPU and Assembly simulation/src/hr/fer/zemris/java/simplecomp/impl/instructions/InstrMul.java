package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This {@link Instruction} gets 3 {@link InstructionArgument}s. <br>
 * The first one is the destination register, and the other two are the operand
 * registers. The result of the {@link Instruction} is the multiplication of the
 * operands.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class InstrMul implements Instruction {
	/**
	 * The value of the first argument.
	 */
	private int indexRegistra1;
	/**
	 * The value of the first argument.
	 */
	private int indexRegistra2;
	/**
	 * The value of the first argument.
	 */
	private int indexRegistra3;

	/**
	 * Creates a new {@link InstrMul} command with the given list of arguments.
	 * 
	 * @param arguments
	 *            a list of {@link InstructionArgument}s used in this
	 *            {@link Instruction}.
	 * @throws IllegalArgumentException
	 *             if the number of arguments in the list is wrong, or if some
	 *             of them are in a wrong type.
	 */
	public InstrMul(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		for (int i = 0; i < 3; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException("Type mismatch for argument " + i + "!");
			}
		}
		this.indexRegistra1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.indexRegistra2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
		this.indexRegistra3 = RegisterUtil.getRegisterIndex((Integer) arguments.get(2).getValue());
	}

	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexRegistra2);
		Object value2 = computer.getRegisters().getRegisterValue(indexRegistra3);
		computer.getRegisters().setRegisterValue(indexRegistra1, Integer.valueOf((Integer) value1 * (Integer) value2));
		return false;
	}
}