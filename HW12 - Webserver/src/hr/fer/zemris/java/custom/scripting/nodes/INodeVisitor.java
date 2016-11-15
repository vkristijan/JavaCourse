package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * <a href="https://en.wikipedia.org/wiki/Visitor_pattern">Visitor</a> used for
 * actions on {@link Node}s.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public interface INodeVisitor {
	/**
	 * The action that should be done if the current node is a {@link DocumentNode}.
	 * 
	 * @param node
	 *            the {@link DocumentNode} that is currently visited.
	 */
	public void visitDocumentNode(DocumentNode node);
	
	/**
	 * The action that should be done if the current node is a {@link EchoNode}.
	 * 
	 * @param node
	 *            the {@link EchoNode} that is currently visited.
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * The action that should be done if the current node is a {@link ForLoopNode}.
	 * 
	 * @param node
	 *            the {@link ForLoopNode} that is currently visited.
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * The action that should be done if the current node is a {@link TextNode}.
	 * 
	 * @param node
	 *            the {@link TextNode} that is currently visited.
	 */
	public void visitTextNode(TextNode node);
}
