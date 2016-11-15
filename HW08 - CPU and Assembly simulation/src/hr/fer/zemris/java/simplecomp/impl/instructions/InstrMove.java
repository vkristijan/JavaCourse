package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This {@link Instruction} gets 2 {@link InstructionArgument}s, the indexes of
 * two register. The {@link Instruction} will move the value from the second
 * register to the first one. This {@link Instruction} also supports indirect
 * addressing. In that case, the value will be read from the memory location
 * defined in the given register with the given offset.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class InstrMove implements Instruction {
	/**
	 * The value of the first argument.
	 */
	private InstructionArgument destination;
	/**
	 * The value of the second argument.
	 */
	private InstructionArgument source;

	/**
	 * Creates a new {@link InstrMove} command with the given list of arguments.
	 * 
	 * @param arguments
	 *            a list of {@link InstructionArgument}s used in this
	 *            {@link Instruction}.
	 * @throws IllegalArgumentException
	 *             if the number of arguments in the list is wrong, or if some
	 *             of them are in a wrong type.
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}

		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if (!arguments.get(1).isRegister() && !arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.destination = arguments.get(0);
		this.source = arguments.get(1);
	}

	@Override
	public boolean execute(Computer computer) {
		Object value;
		if (source.isNumber()) {
			value = source.getValue();
		} else {
			if (RegisterUtil.isIndirect((int) source.getValue())) {
				int address = (int) computer.getRegisters()
						.getRegisterValue(RegisterUtil.getRegisterIndex((int) source.getValue()));
				address += RegisterUtil.getRegisterOffset((int) source.getValue());

				value = computer.getMemory().getLocation(address);
			} else {
				value = computer.getRegisters()
						.getRegisterValue(RegisterUtil.getRegisterIndex((int) source.getValue()));
			}
		}

		if (RegisterUtil.isIndirect((int) destination.getValue())) {
			int address = (int) computer.getRegisters()
					.getRegisterValue(RegisterUtil.getRegisterIndex((int) destination.getValue()));
			address += RegisterUtil.getRegisterOffset((int) destination.getValue());

			computer.getMemory().setLocation(address, value);
		} else {
			computer.getRegisters().setRegisterValue(RegisterUtil.getRegisterIndex((int) destination.getValue()),
				value);
		}

		return false;
	}

}
