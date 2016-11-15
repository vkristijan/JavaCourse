package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * A simple demonstration program for the {@link LikeMedian} class. This program
 * creates an empty new {@code LikeMedian} and adds three {@code Integer}
 * elements to it, after that the median is calculated and returned.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class MedianDemo1 {
	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		LikeMedian<Integer> likeMedian = new LikeMedian<Integer>();

		likeMedian.add(new Integer(10));
		likeMedian.add(new Integer(5));
		likeMedian.add(new Integer(3));

		Optional<Integer> result = likeMedian.get();
		System.out.println(result.get());

		for (Integer elem : likeMedian) {
			System.out.println(elem);
		}
	}
}
