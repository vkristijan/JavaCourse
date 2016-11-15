package hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.Pair;
import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.Utility;
import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.environment.Environment;

/**
 * A command that is used to find documents similar to the given query. The
 * command will compare the words given in the query with all documents and
 * return the 10 most similar document.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class QueryCommand extends AbstractCommand {

	/**
	 * Instantiates a new query command.
	 *
	 * @param name the name
	 */
	public QueryCommand(String name) {
		super(name);
	}

	@Override
	public boolean execute(String args, Environment env) {
		args = Utility.replaceNotWordChars(args);
		args = args.toLowerCase();
		args = args.trim();
		if (args.isEmpty()){
			env.writeln("No argument given!");
			return true;
		}
		
		List<String> stopWords = env.getStopWords();
		Map<String, Integer> dictionary = env.getDictionary();
		
		Map<String, Integer> wordFrequency = new HashMap<>();
		
		env.write("Query is: [");
		boolean first = true;
		String[] words = args.split(" +");
		for (String word : words){
			if (!first){
				env.write(", ");
			}
			first = false;
			env.write(word);
			if (!dictionary.containsKey(word)) {
				continue;
			}
			if (stopWords.contains(word)){
				continue;
			}
			
			Utility.increment(wordFrequency, word);
		}
		env.writeln("]");
		
		int documentCount = env.getDocumentCount();
		List<Double> inputVector = Utility.calculateDocumentVector(dictionary, wordFrequency, documentCount);
		Map<String, List<Double>> documentVector = env.getDocumentVector();
		
		Set<Pair<Double, String>> results = new TreeSet<>();
		
		double inputNorm = Utility.vectorNorm(inputVector);
		for (Entry<String, List<Double>> document : documentVector.entrySet()){
			List<Double> vector = document.getValue();
			double documentNorm = Utility.vectorNorm(vector);
			double score = Utility.scalarProduct(inputVector, vector);
			score /= (inputNorm * documentNorm);
			
			if (score > 0){
				results.add(new Pair<Double, String>(score, document.getKey()));
			}
		}
		
		results.stream()
			   .limit(10)
			   .collect(Collectors.toSet());
		env.setResults(results);
		
		env.writeln("Najboljih " + results.size() + " rezultata:");
		Command cmd = new ResultsCommand("result");
		cmd.execute("", env);
		return true;
	}

	

}
