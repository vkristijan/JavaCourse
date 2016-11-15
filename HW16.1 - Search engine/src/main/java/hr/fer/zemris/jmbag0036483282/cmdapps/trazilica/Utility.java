package hr.fer.zemris.jmbag0036483282.cmdapps.trazilica;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Utility class used to do calculations.
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class Utility {
	
	/**
	 * Instantiates a new utility.
	 */
	private Utility(){
	}

	/**
	 * Replace not word chars with blank spaces.
	 *
	 * @param word
	 *            the word
	 * @return the string after replacing no word characters with blank spaces.
	 */
	public static String replaceNotWordChars(String word){
		//regex used to replace every character that is not an letter.
		String pattern = "[^a-zA-ZčćžšđČĆŽŠĐ]"; 
		return word.replaceAll(pattern, " ");
	}
	
	/**
	 * Calculate document vector.
	 *
	 * @param dictionary
	 *            the dictionary
	 * @param documentWordFrequency
	 *            the document word frequency
	 * @param documentCount
	 *            the document count
	 * @return the list
	 */
	public static List<Double> calculateDocumentVector(
		Map<String, Integer> dictionary, Map<String, Integer> documentWordFrequency, int documentCount){
		
		List<Double> vector = new ArrayList<>();
		
		for (String word : dictionary.keySet()) {
			double idf = Math.log((double) documentCount / dictionary.get(word));
			double tf = 0;
			
			Integer wordCount = documentWordFrequency.get(word);
			if (wordCount != null){
				tf = wordCount;
			}
			
			vector.add(tf * idf);
		}
		
		return vector;
	}
	
	/**
	 * Increments the count of the given word in the given map. If the word
	 * doesn't exist in the map, it will be added and the value will be set to
	 * 1.
	 *
	 * @param map
	 *            the map
	 * @param word
	 *            the word
	 */
	public static void increment(Map<String, Integer> map, String word){
		int count = 0;
		if (map.containsKey(word)){
			count = map.get(word);
		} 
		
		map.put(word, count + 1);
	}
	
	/**
	 * Calculates the scalar product of two vectors.
	 *
	 * @param v1
	 *            the first vector.
	 * @param v2
	 *            the second vector.
	 * @return the scalar product.
	 */
	public static double scalarProduct(List<Double> v1, List<Double> v2){
		if (v1.size() != v2.size()){
			throw new IllegalArgumentException("The given vectors don't have the same dimension.");
		}
		
		double result = 0;
		
		int size = v1.size();
		for (int i = 0; i < size; ++i){
			result += v1.get(i) * v2.get(i);
		}
		
		return result;
	}
	
	/**
	 * Calculates the norm of a vector.
	 *
	 * @param v
	 *            the vector.
	 * @return the vector norm.
	 */
	public static double vectorNorm(List<Double> v){
		double scalarProduct = scalarProduct(v, v);
		return Math.sqrt(scalarProduct);
	}
}

