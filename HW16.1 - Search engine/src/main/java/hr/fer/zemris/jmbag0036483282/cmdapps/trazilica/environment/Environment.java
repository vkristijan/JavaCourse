package hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.environment;

import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.Pair;

/**
 * Interface defining the work of a simple shell. The environment of a shell has
 * it's own list of stop words and a dictionary map. It is capable of writing to
 * the defined output stream.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public interface Environment {
	
	/**
	 * Gets the stop words.
	 *
	 * @return the stop words
	 */
	List<String> getStopWords();

	/**
	 * Gets the dictionary.
	 *
	 * @return the dictionary
	 */
	Map<String, Integer> getDictionary();

	/**
	 * Gets the document count.
	 *
	 * @return the document count
	 */
	int getDocumentCount();

	/**
	 * Gets the path to the document folder.
	 *
	 * @return the path
	 */
	String getPath();

	/**
	 * Gets the search results.
	 *
	 * @return the results
	 */
	Set<Pair<Double, String>> getResults();

	/**
	 * Sets the search results.
	 *
	 * @param results
	 *            the results
	 */
	void setResults(Set<Pair<Double, String>> results);

	/**
	 * Gets the document vector.
	 *
	 * @return the document vector
	 */
	Map<String, List<Double>> getDocumentVector();

	/**
	 * Starts the execution of the shell.
	 */
	void start();

	/**
	 * Write the given text to the defined output stream.
	 *
	 * @param text
	 *            the text to be written
	 */
	void write(String text);
	
	/**
	 * Writes a line to the defined output stream.
	 *
	 * @param text
	 *            the text to be written
	 */
	void writeln(String text);
}
