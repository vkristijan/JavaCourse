package hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.command;

import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.environment.Environment;

/**
 * Interface defining the functionality of an command used by this shell.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public interface Command {

	/**
	 * Returns the command name.
	 *
	 * @return the name
	 */
	String getName();
	
	/**
	 * Executes the command.
	 *
	 * @param args
	 *            the arguments
	 * @param env
	 *            the {@link Environment} in which this command is executed.
	 * @return true, if the shell should continue to execute after this command,
	 *         false othervise.
	 */
	boolean execute(String args, Environment env);
}
