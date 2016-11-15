package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.CloseProtectedInputStream;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This {@link Instruction} gets a single {@link InstructionArgument}, a memory
 * location. The {@link Instruction} reads a value from the standard system
 * input and stores it to the specified location in the memory. If the value is
 * an integer, the flag in the registers will be set to true, otherwise the flag
 * will be false and nothing will happen.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class InstrIinput implements Instruction {
	/**
	 * The value of the first argument.
	 */
	private int location;

	/**
	 * Creates a new {@link InstrIinput} command with the given list of
	 * arguments.
	 * 
	 * @param arguments
	 *            a list of {@link InstructionArgument}s used in this
	 *            {@link Instruction}.
	 * @throws IllegalArgumentException
	 *             if the number of arguments in the list is wrong, or if some
	 *             of them are in a wrong type.
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		this.location = (int) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		try (Scanner sc = new Scanner(new CloseProtectedInputStream(System.in))) {
			String str = sc.nextLine();
			int value = Integer.parseInt(str);

			computer.getMemory().setLocation(location, value);
			computer.getRegisters().setFlag(true);
		} catch (Exception e) {
			computer.getRegisters().setFlag(false);
		}

		return false;
	}

}
