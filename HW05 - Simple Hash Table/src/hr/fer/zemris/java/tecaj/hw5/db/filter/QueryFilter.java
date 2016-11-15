package hr.fer.zemris.java.tecaj.hw5.db.filter;

import hr.fer.zemris.java.tecaj.hw5.db.Parser;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.conditionalExpressions.IConditionalExpression;

/**
 * Defines a filter that selects the students from the {@code StudentDatabase}
 * following the selection query given as the string argument. Valid queries
 * consist of a field name, a comparison operator and a string literal in
 * quotes. It is allowed to connect multiple conditions by joining them with the
 * keyword AND.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class QueryFilter implements IFilter {
	/**
	 * The {@code IConditionalExpression} that is used to determine if a student
	 * should be accepted or not.
	 */
	private final IConditionalExpression expression;

	/**
	 * Creates a new {@code QueryFilter} that can accept a student if he/she
	 * fulfills the criteria given in the string argument.
	 * 
	 * @param inputLine
	 *            the selection query used to select students.
	 */
	public QueryFilter(String inputLine) {
		Parser parser = new Parser(inputLine);
		expression = parser.getExpression();
	}

	@Override
	public boolean accepts(StudentRecord record) {
		return expression.satisfies(record);
	}

}
