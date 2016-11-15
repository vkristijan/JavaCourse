package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This is a representation of a node in the document hierarchy that can store
 * some text. <br>
 * This class inherits {@link Node}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class TextNode extends Node {
	/**
	 * The text stored in this node.
	 */
	private final String text;

	/**
	 * Creates a new node and sets the node elements to the one given as
	 * arguments.
	 * 
	 * @param text
	 *            the text to be stored in this node.
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}

	/**
	 * Returns the text stored in this node.
	 * 
	 * @return the text stored in this node.
	 */
	public String getText() {
		return text;
	}

	@Override
	public String asText() {
		return text;
	}
}
