package hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.command;

import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.environment.Environment;

/**
 * A shell command that is used to stop execution.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class ExitCommand extends AbstractCommand{
	
	/**
	 * Instantiates a new exit command.
	 *
	 * @param name the name
	 */
	public ExitCommand(String name) {
		super(name);
	}

	@Override
	public boolean execute(String args, Environment env) {
		return false;
	}
}
