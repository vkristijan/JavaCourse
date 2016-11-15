package hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.environment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.Pair;
import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.command.Command;
import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.command.ExitCommand;
import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.command.QueryCommand;
import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.command.ResultsCommand;
import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.command.TypeCommand;

/**
 * An implementation of the {@link Environment} interface. Uses standard system
 * output as the output stream. Allowed commands are {@link ExitCommand},
 * {@link QueryCommand}, {@link ResultsCommand} and {@link TypeCommand}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class EnvironmentImpl implements Environment {
	
	/** The stop words. */
	private List<String> stopWords;
	
	/** The dictionary. */
	private Map<String, Integer> dictionary;
	
	/** The document vector. */
	private Map<String, List<Double>> documentVector;
	
	/** The results. */
	private Set<Pair<Double, String>> results;
	
	/** The document count. */
	private int documentCount;
	
	/** The path. */
	private String path;

	/**
	 * A map containing all the commands supported in this shell. The key of the
	 * map is the command name that is used to call the command.
	 */
	private Map<String, Command> commands;
	
	/**
	 * Instantiates a new environment.
	 *
	 * @param stopWords
	 *            the stop words
	 * @param dictionary
	 *            the dictionary
	 * @param documentVector
	 *            the document vector
	 * @param path
	 *            the path
	 * @param documentCount
	 *            the document count
	 */
	public EnvironmentImpl(
		List<String> stopWords,
		Map<String, Integer> dictionary, 
		Map<String, List<Double>> documentVector,
		String path,
		int documentCount) {
		
		this.stopWords = stopWords;
		this.dictionary = dictionary;
		this.documentVector = documentVector;
		this.path = path;
		this.documentCount = documentCount;
		
		this.commands = new HashMap<>();
		commands.put("exit", new ExitCommand("exit"));
		commands.put("query", new QueryCommand("query"));
		commands.put("type", new TypeCommand("type"));
		commands.put("results", new ResultsCommand("results"));
	}

	@Override
	public int getDocumentCount() {
		return documentCount;
	}
	
	@Override
	public String getPath() {
		return path;
	}
	
	@Override
	public List<String> getStopWords() {
		return stopWords;
	}

	@Override
	public Map<String, Integer> getDictionary() {
		return dictionary;
	}

	@Override
	public Set<Pair<Double, String>> getResults() {
		return results;
	}

	@Override
	public void setResults(Set<Pair<Double, String>> results) {
		this.results = results;
	}

	@Override
	public Map<String, List<Double>> getDocumentVector() {
		return documentVector;
	}
	
	@Override
	public void write(String text) {
		System.out.print(text);
	}
	
	@Override
	public void writeln(String text) {
		System.out.println(text);
	}
	
	@Override
	public void start() {
		Scanner sc = new Scanner(System.in);
		
		boolean shouldExecute = true;
		while (shouldExecute) {
			String line = sc.nextLine();
			if (line == null) {
				continue;
			}
			
			line = line.trim();
			if (line.isEmpty()) {
				continue;
			}
			
			String commandName = line;
			String args = "";
			int index = line.indexOf(' ');
			if (index > 0){
				commandName = line.substring(0, index);
				args = line.substring(index + 1);
			}
			
			Command command = commands.get(commandName);
			if (command != null){
				 shouldExecute = command.execute(args, this);
			} else {
				writeln("Nepoznata naredba.");
			}
		}
		sc.close();
	}
}
