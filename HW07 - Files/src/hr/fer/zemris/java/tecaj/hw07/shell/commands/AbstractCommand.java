package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * An abstract implementation of the {@link ShellCommand}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public abstract class AbstractCommand implements ShellCommand {
	/**
	 * The name of the command.
	 */
	protected String name;
	/**
	 * The command description.
	 */
	protected List<String> description;

	/**
	 * Creates a new {@link AbstractCommand} with an empty description.
	 */
	protected AbstractCommand() {
		super();
		description = new LinkedList<>();
	}

	/**
	 * Splits the argument string using {@link Lexer}.
	 * 
	 * @param argument
	 *            the string to be split.
	 * @return a list of strings created by splitting the argument.
	 */
	protected List<String> splitArguments(String argument) {
		List<String> args = new ArrayList<>();

		Lexer lexer = new Lexer(argument);

		while (lexer.next() != null) {
			args.add(lexer.getToken());
		}

		return args;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
