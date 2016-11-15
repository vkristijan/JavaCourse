package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This {@link Instruction} gets a single {@link InstructionArgument}, a
 * register. The {@link Instruction} reads the value from the register, and
 * prints it to the standard system output.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class InstrEcho implements Instruction {
	/**
	 * The value of the first argument.
	 */
	private int registerDescriptor;

	/**
	 * Creates a new {@link InstrEcho} command with the given list of arguments.
	 * 
	 * @param arguments
	 *            a list of {@link InstructionArgument}s used in this
	 *            {@link Instruction}.
	 * @throws IllegalArgumentException
	 *             if the number of arguments in the list is wrong, or if some
	 *             of them are in a wrong type.
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}

		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}

		this.registerDescriptor = (Integer) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		Object value;

		if (RegisterUtil.isIndirect(registerDescriptor)) {
			int address = (int) computer.getRegisters()
					.getRegisterValue(RegisterUtil.getRegisterIndex(registerDescriptor));
			address += RegisterUtil.getRegisterOffset(registerDescriptor);

			value = computer.getMemory().getLocation(address);
		} else {
			value = computer.getRegisters().getRegisterValue(RegisterUtil.getRegisterIndex(registerDescriptor));
		}
		System.out.print(value);
		return false;
	}

}
