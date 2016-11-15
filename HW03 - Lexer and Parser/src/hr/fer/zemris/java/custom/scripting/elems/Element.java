package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * A representation of an element that can be added inside a {@link Node}. This
 * class defines the method asText that can be used to get the original text
 * format of the element as it should be in a document.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Element {
	/**
	 * Converts the value stored inside the element to a string in such a way it
	 * would be written in a code.
	 * 
	 * @return a string with the value stored in the element.
	 */
	public String asText() {
		return "";
	}
}
