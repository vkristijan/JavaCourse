package hr.fer.zemris.java.tecaj.hw5.db.filter;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Defines a filter that can be used to select some specific data from the
 * {@code StudentDatabase}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public interface IFilter {
	/**
	 * Checks the given {@code StudentRecord} and determines if the student
	 * should be accepted or not.
	 * 
	 * @param record
	 *            the student to be checked.
	 * @return a boolean value: <strong>true</strong> if the given record is
	 *         accepted, <strong>false</strong> otherwise.
	 */
	public boolean accepts(StudentRecord record);
}
