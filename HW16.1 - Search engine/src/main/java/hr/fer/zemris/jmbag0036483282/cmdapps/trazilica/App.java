package hr.fer.zemris.jmbag0036483282.cmdapps.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.environment.Environment;
import hr.fer.zemris.jmbag0036483282.cmdapps.trazilica.environment.EnvironmentImpl;

/**
 * A console application that is used as a simple search engine. The program
 * expects a single argument, the path of the folder containing the text
 * documents that should be searched. After loading the app, it will go through
 * all files and analyze them so that it can later produce search results for
 * queries.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class App {
	
	/** The path to the stop words file. */
	private static String STOP_WORDS_FILE = "hrvatski_stoprijeci.txt";

	/** List of all the stop words. */
	private static List<String> stopWords;

	/**
	 * The dictionary. The key of the map is a word, the value is the number of
	 * documents containing this word.
	 */
	private static Map<String, Integer> dictionary;

	/**
	 * The document vectors. The key is the document name, the value is a
	 * vector.
	 */
	private static Map<String, List<Double>> documentVectors;
	
	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		if (args.length != 1){
			System.err.println("The number of arguments is wrong! Only one argument is expected, "
					+ "the path to the folder containing text documents.");
			System.exit(1);
		}
		Path documentRoot = Paths.get(args[0]);
		documentRoot = documentRoot.normalize();
		
		Path stopWordsDocument = Paths.get(STOP_WORDS_FILE);
		stopWords = getStopWords(stopWordsDocument);
		
		PrepareDataVisitor visitor = new PrepareDataVisitor(stopWords);
		try {
			Files.walkFileTree(documentRoot, visitor);
		} catch (IOException e) {
			System.err.println("Unable to read a document in the given document root folder.");
			System.exit(1);
		}
		dictionary = visitor.getDictionary();
		documentVectors = visitor.getDocumentVectors();
		int documentCount = visitor.getDocumentCount();
		
		System.out.println("Veličina riječnika je " + dictionary.size() + " riječi.");
		
		Environment console = new EnvironmentImpl(
			stopWords, 
			dictionary, 
			documentVectors, 
			documentRoot.toAbsolutePath().toString(),
			documentCount
		);
		console.start();
	}

	/**
	 * Reads the file containing the stop words, creates a list of all the stop
	 * words and returns it.
	 *
	 * @param stopWordsDocument
	 *            the stop words document
	 * @return the stop words
	 */
	private static List<String> getStopWords(Path stopWordsDocument) {
		List<String> stopWords = new ArrayList<>();
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(stopWordsDocument, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("It is not possible to read from the file conaining stop words!");
			System.exit(1);
		}

		for (String line : lines){
			line = Utility.replaceNotWordChars(line);
			line = line.toLowerCase();
			
			String[] words = line.split(" +");
			
			for (String word : words){
				stopWords.add(word);
			}
		}
		
		return stopWords;
	}
}
