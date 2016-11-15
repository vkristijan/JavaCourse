package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * This is a representation of a node in the document hierarchy. Every node can
 * have zero or more children nodes.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Node {
	/**
	 * The collection used to store the children nodes.
	 */
	private ArrayIndexedCollection children;

	/**
	 * Adds a child to the current node.
	 * 
	 * @param child
	 *            the node to be added.
	 */
	public void addChildNode(Node child) {
		if (children == null) {
			children = new ArrayIndexedCollection();
		}

		children.add(child);
	}

	/**
	 * Returns the number of children nodes.
	 * 
	 * @return the number of children nodes.
	 */
	public int numberOfChildren() {
		if (children == null) {
			return 0;
		}
		return children.size();
	}

	/**
	 * Returns the child node at the specified index.
	 * 
	 * @param index
	 *            the index of the child.
	 * @return the node of the child at the specified index.
	 * @throws IndexOutOfBoundsException
	 *             if the given index is not valid.
	 */
	public Node getChild(int index) {
		try {
			return (Node) children.get(index);
		} catch (Exception e) {
			throw new IndexOutOfBoundsException("The requested element could not be found!");
		}
	}

	/**
	 * Returns the text representation of the body for the current node. The
	 * child nodes are not included in that text.
	 * 
	 * @return text representation of the body for the node.
	 */
	public String asText() {
		return "";
	}
}
