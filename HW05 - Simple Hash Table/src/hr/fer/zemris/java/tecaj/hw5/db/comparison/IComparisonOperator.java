package hr.fer.zemris.java.tecaj.hw5.db.comparison;

/**
 * Describes the interface for a comparison operator. The interface specifies a
 * single method that checks if two string values satisfy the given comparison
 * operator.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public interface IComparisonOperator {
	/**
	 * Checks if the two string values given in the arguments satisfy the
	 * specified comparison operator.
	 * 
	 * @param value1
	 *            the first value to be compared
	 * @param value2
	 *            the second value to be compared
	 * @return a boolean value as the result of the comparison.
	 */
	public boolean satisfied(String value1, String value2);
}
