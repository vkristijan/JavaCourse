package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * A representation of an element that can be added inside a {@link Node} that
 * is a variable that has it's name that can e stored in the element. The stored
 * value can not be changed.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 * @see Element
 */
public class ElementVariable extends Element {
	/**
	 * The name of the variable.
	 */
	private final String name;

	/**
	 * Creates a new Element with the name equal to the one given as the
	 * argument.
	 * 
	 * @param name
	 *            the value to be stored in the element.
	 */
	public ElementVariable(String name) {
		super();
		this.name = name;
	}

	/**
	 * Returns the name of the function.
	 * 
	 * @return the name of the function.
	 */
	public String getName() {
		return name;
	}

	@Override
	public String asText() {
		return name;
	}
}
