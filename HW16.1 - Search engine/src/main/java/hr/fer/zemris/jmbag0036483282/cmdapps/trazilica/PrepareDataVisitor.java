package hr.fer.zemris.jmbag0036483282.cmdapps.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Reads all the files and prepares the dictionary, and document frequency
 * vectors.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class PrepareDataVisitor extends SimpleFileVisitor<Path> {

	/** The stop words. */
	private List<String> stopWords;

	/**
	 * The word dictionary. The key represents a word, the value is the number
	 * of documents where the word occurs.
	 */
	private Map<String, Integer> dictionary;

	/**
	 * The document vectors. The key is the document name, the value is a list
	 * of document vectors.
	 */
	private Map<String, List<Double>> documentVectors;

	/**
	 * The document word frequency. The key is the document name, the value is a
	 * map of word frequencies for the document.
	 */
	private Map<String, Map<String, Integer>> documentWordFrequency;

	/** The document count. */
	private int documentCount;
	
	/**
	 * Instantiates a new prepare data visitor.
	 *
	 * @param stopWords
	 *            the stop words
	 */
	public PrepareDataVisitor(List<String> stopWords) {
		this.stopWords = stopWords;
		
		dictionary = new HashMap<>();
		documentVectors = new HashMap<>();
		documentWordFrequency = new HashMap<>();
	}
	
	
	/**
	 * Gets the dictionary.
	 *
	 * @return the dictionary
	 */
	public Map<String, Integer> getDictionary() {
		return dictionary;
	}

	/**
	 * Gets the document vectors.
	 *
	 * @return the document vectors
	 */
	public Map<String, List<Double>> getDocumentVectors() {
		return documentVectors;
	}

	/**
	 * Gets the document count.
	 *
	 * @return the document count
	 */
	public int getDocumentCount() {
		return documentCount;
	}
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		documentCount++;
		Map<String, Integer> wordFrequency = new HashMap<>();
		
		List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
		
		for (String line : lines){
			line = Utility.replaceNotWordChars(line);
			line = line.toLowerCase();
			String[] words = line.split(" +");
			
			for (String word : words){
				if (stopWords.contains(word)){
					continue;
				}
				
				if (!wordFrequency.containsKey(word)){
					Utility.increment(dictionary, word);
				}
				Utility.increment(wordFrequency, word);
			}
		}
		
		documentWordFrequency.put(file.getFileName().toString(), wordFrequency);
		return FileVisitResult.CONTINUE;
	}
	
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		
		for (Entry<String, Map<String, Integer>> document : documentWordFrequency.entrySet()){
			List<Double> vector = 
					Utility.calculateDocumentVector(dictionary, document.getValue(), documentCount);
			documentVectors.put(document.getKey(), vector);
		}
		
		return FileVisitResult.TERMINATE;
	}
}
