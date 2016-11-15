package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * A representation of an element that can be added inside a {@link Node} that
 * can store a string. The stored value can not be changed.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 * @see Element
 */
public class ElementString extends Element {
	/**
	 * The value stored in this element.
	 */
	private final String value;

	/**
	 * Creates a new Element with the value equal to the one given as the
	 * argument.
	 * 
	 * @param value
	 *            the value to be stored in the element.
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}

	/**
	 * Returns the stored value.
	 * 
	 * @return the stored value.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * {@inheritDoc} <br>
	 * A string begins and ends with a '"' sign.
	 */
	@Override
	public String asText() {
		return "\"" + value + "\"";
	}
}
