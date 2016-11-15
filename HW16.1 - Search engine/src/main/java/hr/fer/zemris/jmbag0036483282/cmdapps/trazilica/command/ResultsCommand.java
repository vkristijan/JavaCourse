package hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.command;

import java.util.Set;

import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.Pair;
import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.environment.Environment;

/**
 * A shell command that will print the results of a previous search query, if
 * such one exists. If no previous search query was made, the command will print
 * an appropriate error message.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class ResultsCommand extends AbstractCommand {

	/**
	 * Instantiates a new results command.
	 *
	 * @param name the name
	 */
	public ResultsCommand(String name) {
		super(name);
	}

	@Override
	public boolean execute(String args, Environment env) {
		Set<Pair<Double, String>> results = env.getResults();
		if (results == null){
			env.writeln("No result to show.");
			return true;
		}
		
		int i = 0;
		for (Pair<Double, String> document : results) {
			env.write("[" + i + "] ");
			env.write("(" + document.getFirst() + ") ");
			env.writeln(env.getPath() + "\\" + document.getSecond());
			++i;
		}

		return true;
	}

}
