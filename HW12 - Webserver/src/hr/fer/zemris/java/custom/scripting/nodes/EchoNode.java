package hr.fer.zemris.java.custom.scripting.nodes;

import static hr.fer.zemris.java.custom.scripting.lexer.Utility.TAG_BEGIN;
import static hr.fer.zemris.java.custom.scripting.lexer.Utility.TAG_END;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * This is a representation of a node in the document hierarchy that can store
 * multiple arguments. <br>
 * This class inherits {@link Node}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class EchoNode extends Node {
	/**
	 * The elements stored in this node.
	 */
	private final Element[] elements;

	/**
	 * Creates a new node and sets the node elements to the one given as
	 * arguments.
	 * 
	 * @param elements
	 *            the elements to be stored in this node.
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	/**
	 * Returns the stored elements.
	 * 
	 * @return stored elements.
	 */
	public Element[] getElements() {
		return elements;
	}

	@Override
	public String asText() {
		StringBuilder text = new StringBuilder();
		text.append(TAG_BEGIN);
		text.append("= ");

		for (Element element : elements) {
			text.append(element.asText());
			text.append(" ");
		}

		text.append(TAG_END);
		return text.toString();
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
