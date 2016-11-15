package hr.fer.zemris.java.tecaj.hw5.db.conditionalExpressions;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.comparison.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.valueGetters.IFieldValueGetter;

/**
 * A simple conditional expression that can be used to query the entries in the
 * {@code StudentDatabase}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class ConditionalExpression implements IConditionalExpression {
	/**
	 * the {@code IFieldValueGetter} to get the value of the field that should
	 * be checked.
	 */
	private final IFieldValueGetter fieldValue;
	/** the {@code String} literal for the comparison. */
	private final String literal;
	/**
	 * the {@code IComparisonOperator} that should be used to compare the two
	 * strings.
	 */
	private final IComparisonOperator operator;

	/**
	 * Creates a new {@code ConditionalExpression} that can compare two
	 * {@code String}s, a field specified with the {@code IFieldValueGetter},
	 * and a literal given in the argument. The two {@code String}s are compared
	 * using the {@code IComparisonOperator} given in the argument.
	 * 
	 * @param fieldValue
	 *            the {@code IFieldValueGetter} to get the value of the field
	 *            that should be checked.
	 * @param literal
	 *            the {@code String} literal for the comparison.
	 * @param operator
	 *            the {@code IComparisonOperator} that should be used to compare
	 *            the two strings.
	 */
	public ConditionalExpression(IFieldValueGetter fieldValue, String literal,
			IComparisonOperator operator) {
		super();
		this.fieldValue = fieldValue;
		this.literal = literal;
		this.operator = operator;
	}

	/**
	 * Returns the {@code IFieldValueGetter} used in this
	 * {@code ConditionalExpression}.
	 * 
	 * @return the {@code IFieldValueGetter} used in this
	 *         {@code ConditionalExpression}.
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldValue;
	}

	/**
	 * Returns the {@code String} literal used in this
	 * {@code ConditionalExpression}.
	 * 
	 * @return the {@code String} literal used in this
	 *         {@code ConditionalExpression}.
	 */
	public String getStringLiteral() {
		return literal;
	}

	/**
	 * Returns the {@code IComparisonOperator} used in this
	 * {@code ConditionalExpression}.
	 * 
	 * @return the {@code IComparisonOperator} used in this
	 *         {@code ConditionalExpression}.
	 */
	public IComparisonOperator getComparisonOperator() {
		return operator;
	}

	@Override
	public boolean satisfies(StudentRecord record) {
		return operator.satisfied(fieldValue.get(record), literal);
	}

}
