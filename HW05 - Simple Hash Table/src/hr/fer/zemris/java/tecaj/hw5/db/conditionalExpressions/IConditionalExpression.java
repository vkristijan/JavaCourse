package hr.fer.zemris.java.tecaj.hw5.db.conditionalExpressions;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Checks if some expression satisfies a criteria that is defined by this
 * {@code IConditionalExpression}.
 * 
 * @author Kristijan Vulinovic
 *
 */
public interface IConditionalExpression {
	/**
	 * Check if the given {@code StudentRecord} satisfies this
	 * {@code IConditionalExpression}.
	 * 
	 * @param record
	 *            the {@code StudentRecord} whose satisfaction should be
	 *            checked.
	 * @return a boolean value, depending on the result of the expression.
	 */
	boolean satisfies(StudentRecord record);
}
