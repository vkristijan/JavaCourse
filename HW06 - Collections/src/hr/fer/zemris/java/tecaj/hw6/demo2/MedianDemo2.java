package hr.fer.zemris.java.tecaj.hw6.demo2;

/**
 * A simple demonstration program for the {@link LikeMedian} class. This program
 * creates an empty new {@code LikeMedian} and adds four {@code String} elements
 * to it, after that the median is calculated and returned.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class MedianDemo2 {
	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		LikeMedian<String> likeMedian = new LikeMedian<String>();

		likeMedian.add("Joe");
		likeMedian.add("Jane");
		likeMedian.add("Adam");
		likeMedian.add("Zed");

		String result = likeMedian.get().get();
		System.out.println(result); // Writes: Jane
	}
}
