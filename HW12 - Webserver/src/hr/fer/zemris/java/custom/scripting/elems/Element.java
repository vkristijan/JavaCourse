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
	
	/**
	 * Returns the constant stored in this element, if the element is a constant
	 * type. Otherwise an exception is thrown.
	 * 
	 * @return the constant stored in the element.
	 * @throws RuntimeException
	 *             if this element is not a constant.
	 */
	public Object getConstant(){
		if (this instanceof ElementConstantInteger){
			return ((ElementConstantInteger)this).getValue();
		} else if (this instanceof ElementConstantDouble){
			return ((ElementConstantDouble)this).getValue();
		} else if (this instanceof ElementString){
			return ((ElementString)this).getValue();
		} else {
			throw new RuntimeException("The current element is not a constatnt.");
		}
		
	}
}
