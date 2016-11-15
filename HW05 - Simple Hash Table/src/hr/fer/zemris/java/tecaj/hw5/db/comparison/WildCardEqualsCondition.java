package hr.fer.zemris.java.tecaj.hw5.db.comparison;

/**
 * A comparison operator that checks if the two given string are equal. It is
 * allowed to use a single wildcard operator "*" in the second {@code String} to
 * replace zero or more characters in the string.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class WildCardEqualsCondition implements IComparisonOperator {

	@Override
	public boolean satisfied(String value1, String value2) {
		if (count('*', value2) > 1) {
			throw new IllegalArgumentException("Only 1 wildcard operator is allowed.");
		}

		value2 = value2.replaceAll("\\*", ".*");

		StringBuilder string = new StringBuilder();
		string.append("^");
		string.append(value2);
		string.append("$");

		value2 = string.toString();

		return value1.matches(value2);
	}

	/**
	 * Counts the number of times the {@code Character} ch appears in the
	 * {@code String} from the argument.
	 * 
	 * @param ch
	 *            the char to be counted.
	 * @param string
	 *            the string to be checked.
	 * @return the number of occurrences of the char.
	 */
	int count(char ch, String string) {
		char[] chars = string.toCharArray();

		int count = 0;
		for (char c : chars) {
			if (c == ch) count++;
		}
		return count;
	}
}
