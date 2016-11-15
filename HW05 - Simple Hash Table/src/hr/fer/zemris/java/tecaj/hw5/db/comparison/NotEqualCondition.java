package hr.fer.zemris.java.tecaj.hw5.db.comparison;

/**
 * A comparison operator that checks if the two given string are not equal.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class NotEqualCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		return !value1.equals(value2);
	}

}
