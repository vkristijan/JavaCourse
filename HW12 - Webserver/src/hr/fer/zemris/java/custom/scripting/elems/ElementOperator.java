package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * A representation of an element that can be added inside a {@link Node} that
 * is used to store an operation with the adequate symbol. The stored value can
 * not be changed.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 * @see Element
 */
public class ElementOperator extends Element {
	/**
	 * The stored symbol of the operation.
	 */
	private final String symbol;

	/**
	 * Creates a new Element with the symbol equal to the one given as the
	 * argument.
	 * 
	 * @param symbol
	 *            the value to be stored in the element.
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}

	/**
	 * Returns the symbol of the operation.
	 * 
	 * @return the symbol of the operation.
	 */
	public String getSymbol() {
		return symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}
}
