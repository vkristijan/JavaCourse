package hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.Pair;
import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.environment.Environment;

/**
 * A shell command that prints the content of a document with the given id in
 * the search results.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class TypeCommand extends AbstractCommand {

	/**
	 * Instantiates a new type command.
	 *
	 * @param name the name
	 */
	public TypeCommand(String name) {
		super(name);
	}

	@Override
	public boolean execute(String args, Environment env) {
		args = args.trim();
		if (args.isEmpty()){
			env.writeln("No argument given!");
			return true;
		}
		
		int id = 0;
		try {
			id = Integer.parseInt(args);
		} catch (NumberFormatException e){
			env.writeln("Not able to parse the given argument into an integer.");
			return true;
		}
		
		Set<Pair<Double, String>> results = env.getResults();
		if (results == null || id >= results.size() || id < 0){
			env.writeln("The search result with the given index doesn't exist!");
			return true;
		}
		
		String document = "";
		Iterator<Pair<Double, String>> it = results.iterator();
		while (id >= 0){
			document = it.next().getSecond();
			id--;
		}
		
		String documentPath = env.getPath() + File.separatorChar + document;
		Path doc = Paths.get(documentPath);
		
		String text = "Dokument: " + documentPath;
		String separator = createLineSeparator(text.length());
		env.writeln(separator);
		env.writeln(text);
		env.writeln(separator);
		
		List<String> lines;
		try {
			lines = Files.readAllLines(doc);
		} catch (IOException e) {
			System.err.println("Unable to read the file!");
			return true;
		}
		
		for (String line : lines){
			env.writeln(line);
		}
		
		env.writeln(separator);
		return true;
	}

	/**
	 * Creates the line separator that is used between different parts of the
	 * result output..
	 *
	 * @param length
	 *            the length of the separator
	 * @return the result separator
	 */
	private String createLineSeparator(int length) {
		StringBuilder string = new StringBuilder();
		
		while (length-- > 0){
			string.append('-');
		}
		
		return string.toString();
	}

}
