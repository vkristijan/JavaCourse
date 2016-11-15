package hr.fer.zemris.java.tecaj.hw5.db.conditionalExpressions;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * A simple conditional expression that is used to merge more
 * {@code IConditionalExpression}s into a bigger expression.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class CompositeExpression implements IConditionalExpression {
	/**
	 * A list of all the {@code IConditionalExpression}s that are used in order
	 * to check if a {@code StudentRecord} satisfies the expression or not.
	 */
	private List<IConditionalExpression> expressions;

	/**
	 * Creates a new {@code ConditionalExpression} without any expression in it.
	 */
	public CompositeExpression() {
		super();
		expressions = new LinkedList<>();
	}

	@Override
	public boolean satisfies(StudentRecord record) {
		for (IConditionalExpression expression : expressions) {
			if (!expression.satisfies(record)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Adds a new {@code IConditionalExpression} that should be used in order to
	 * determine if a {@code StudentRecord} satisfies this expression.
	 * 
	 * @param expression
	 *            the new {@code IConditionalExpression} that should be added.
	 */
	public void addExpression(IConditionalExpression expression) {
		if (expression == null) throw new IllegalArgumentException("Not able to add null!");
		if (expression == this) {
			throw new IllegalArgumentException(
				"It is not allowed to make a recursive call for the conditional expressions!");
		}
		expressions.add(expression);
	}

}
