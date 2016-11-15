package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * A single command that can be run inside an {@link Environment}. Every command
 * has its name and description.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public interface ShellCommand {
	/**
	 * Executes the command in the given {@link Environment} using the specified
	 * arguments.
	 * 
	 * @param env
	 *            the {@link Environment} in which the command should run.
	 * @param arguments
	 *            the arguments for this command.
	 * @return a {@link ShellStatus} defining what should happen to the
	 *         {@link Environment} after executing this command.
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns the name of the command.
	 * 
	 * @return the name of the command.
	 */
	String getCommandName();

	/**
	 * Returns a list of {@link String}s. Every string in the list represents
	 * one line of the description for the command.
	 * 
	 * @return a list of {@link String}s. Every string in the list represents
	 *         one line of the description for the command.
	 */
	List<String> getCommandDescription();
}
