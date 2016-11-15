package hr.fer.zemris.java.tecaj.hw5.db.valueGetters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Describes the interface for getting a {@code String} value from a student
 * record. It is only allowed to get a value from the record that is originally
 * represented as a {@code String}, meaning that it is not allowed to get the
 * final grade using this interface.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public interface IFieldValueGetter {
	/**
	 * Returns the value of a specified field from the {@code StudentRecord}
	 * given in the argument.
	 * 
	 * @param record
	 *            the {@code StudentRecord} from which the value should be
	 *            returned.
	 * @return the value of the specified field.
	 */
	public String get(StudentRecord record);
}
