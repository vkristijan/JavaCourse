package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * A representation of an element that can be added inside a {@link Node} that
 * can store a double. The stored value can not be changed.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 * @see Element
 */
public class ElementConstantDouble extends Element {
	/**
	 * The value stored in this element.
	 */
	private final double value;

	/**
	 * Creates a new Element with the value equal to the one given as the
	 * argument.
	 * 
	 * @param value
	 *            the value to be stored in the element.
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
	 * Returns the value stored in this element.
	 * 
	 * @return the value stored in this element.
	 */
	public double getValue() {
		return value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
