package hr.fer.zemris.java.tecaj.hw5.db.comparison;

/**
 * A comparison operator that checks if the first string is greater than the
 * second one in lexicographic order.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class GreaterThanCondition extends AbstractCollatorCondition {
	@Override
	public boolean satisfied(String value1, String value2) {
		return collator.compare(value1, value2) > 0;
	}
}
