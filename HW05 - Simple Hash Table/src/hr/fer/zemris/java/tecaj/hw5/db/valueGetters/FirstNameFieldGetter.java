package hr.fer.zemris.java.tecaj.hw5.db.valueGetters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Returns value of the first name field of the given record.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class FirstNameFieldGetter implements IFieldValueGetter {

	@Override
	public String get(StudentRecord record) {
		if (record == null) {
			throw new IllegalArgumentException("The given record is null!");
		}
		return record.getFirstName();
	}

}
