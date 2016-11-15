package hr.fer.zemris.java.custom.collections.demo;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.Processor;

/**
 * This program tests the ArrayIndexedCollection.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class LinkedListDemo {
	/**
	 * Starts the program.
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		System.out.println(col.size());						//should print 0
		
		col.add("0");
		col.add(15);
		col.add("string");
		System.out.println(col.size());						//should be 3
		
		System.out.println(col.contains(null));				//should be false
		System.out.println(col.contains(15)); 				//should be true
		System.out.println(col.contains(0)); 				//should be false
		
		System.out.println(col.indexOf(null));				//should be -1
		System.out.println(col.indexOf(15));				//should be 1
		
		try {
			col.add(null);									//should be unable to add
			System.out.println("Null was added to the collection!");
		} catch (Exception e) {
			System.out.println("Unable to add null to the collection!");
		}
		
		
		col.insert("new string", 0);
		col.insert("another string", 2);
		col.insert("last string", col.size());
		
		class P extends Processor {
			public void process(Object o) {
				System.out.println(o);
			}
		}
		
		//should be [new string, 0, another string, 15, string, last string]
		System.out.println("The elements in the collection:");
		col.forEach(new P());
		System.out.println(Arrays.toString(col.toArray()));
		
		System.out.println(col.remove(null));				//should be false
		System.out.println(col.remove("another string"));	//should be true
		col.remove(0);
		col.remove(col.size() - 1);
		
		System.out.println(col.size()); 					//should be 3
		System.out.println(Arrays.toString(col.toArray())); //[0, 15, string]
		
		System.out.println(col.get(2));						//should be "string"
		System.out.println(col.get(1));						//should be 15
	}
}
