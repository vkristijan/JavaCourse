package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * A representation of an element that can be added inside a {@link Node} that
 * can store an integer. The stored value can not be changed.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 * @see Element
 */
public class ElementConstantInteger extends Element {
	/**
	 * The value stored in this element.
	 */
	private final int value;

	/**
	 * Creates a new Element with the value equal to the one given as the
	 * argument.
	 * 
	 * @param value
	 *            the value to be stored in the element.
	 */
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}

	/**
	 * Returns the value stored in this element.
	 * 
	 * @return the value stored in this element.
	 */
	public int getValue() {
		return value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
