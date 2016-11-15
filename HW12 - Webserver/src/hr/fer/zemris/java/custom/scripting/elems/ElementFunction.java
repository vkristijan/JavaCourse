package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * A representation of an element that can be added inside a {@link Node} that
 * is a function that has it's name that can e stored in the element. The stored
 * value can not be changed.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 * @see Element
 */
public class ElementFunction extends Element {
	/**
	 * The stored name of the function.
	 */
	private final String name;

	/**
	 * Creates a new Element with the name equal to the one given as the
	 * argument.
	 * 
	 * @param name
	 *            the value to be stored in the element.
	 */
	public ElementFunction(String name) {
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

	/**
	 * {@inheritDoc} <br>
	 * A function is defined to begin with a '@' sign.
	 */
	@Override
	public String asText() {
		return "@" + name;
	}
}
