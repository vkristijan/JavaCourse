package hr.fer.zemris.java.custom.scripting.nodes;

import static hr.fer.zemris.java.custom.scripting.lexer.Utility.TAG_BEGIN;
import static hr.fer.zemris.java.custom.scripting.lexer.Utility.TAG_END;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * This is a representation of a node in the document hierarchy that can store
 * the arguments of a for loop. <br>
 * This class inherits {@link Node}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class ForLoopNode extends Node {
	/**
	 * The variable that is used to iterate through the loop.
	 */
	private final ElementVariable variable;
	/**
	 * The start expression.
	 */
	private final Element startExpression;
	/**
	 * The end expression.
	 */
	private final Element endExpression;
	/**
	 * The step for every iteration.
	 */
	private final Element stepExpression;

	
	/**
	 * Creates a new node and sets the node elements to the one given as
	 * arguments.
	 * 
	 * @param variable the variable for the for loop.
	 * @param startExpression the starting expression.
	 * @param endExpression the end expression.
	 * @param stepExpression the step for every iteration.
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression,
			Element endExpression, Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Creates a new node and sets the node elements to the one given as
	 * arguments.
	 * 
	 * @param variable the variable for the for loop.
	 * @param startExpression the starting expression.
	 * @param endExpression the end expression.
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression,
			Element endExpression) {
		this(variable, startExpression, endExpression, null);
	}

	/**
	 * Returns the variable of iteration.
	 * @return the iteration variable.
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Returns the start expression.
	 * @return the start expression.
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Returns the end expression.
	 * @return the end expression.
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Returns the iteration step.
	 * @return the iteration step.
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public String asText() {
		StringBuilder text = new StringBuilder();
		text.append(TAG_BEGIN);
		text.append("FOR ");

		text.append(variable.asText()).append(" ");
		text.append(startExpression.asText()).append(" ");
		text.append(endExpression.asText()).append(" ");

		if (stepExpression != null) {
			text.append(stepExpression.asText());
		}

		text.append(TAG_END);
		return text.toString();
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
