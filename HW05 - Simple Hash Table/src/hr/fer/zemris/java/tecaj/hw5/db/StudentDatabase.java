package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw5.db.filter.IFilter;

/**
 * A simple database that holds the information about all the students. Every student in this database has
 * a unique jmbag, and it's possible to search for a student by jmbag in constant time.
 * @author Kristijan Vulinovic
 *
 */
public class StudentDatabase {
	/** The collection of students. */
	SimpleHashtable<String, StudentRecord> students;
	/** The collection of students. This collection is used to store the students in the original order. */
	List<StudentRecord> studentsList;

	/**
	 * Creates a new {@code StudentDatabase} from the data given as the
	 * argument. All the data will be parsed and stored in {@code StudentRecord}
	 * s.
	 * 
	 * @param data
	 *            a {@code String} array containing all the data that should be
	 *            stored into this {@code StudentDatabase}. Every {@code String}
	 *            in the data should have the information for one
	 *            {@code StudentRecord}, meaning that it should have the
	 *            student's jmbag, lastName, firstName, finalGrad in this order.
	 */
	protected StudentDatabase(String[] data) {
		super();
		if (data == null) {
			throw new IllegalArgumentException("The given data was null!");
		}

		students = new SimpleHashtable<>(data.length);
		studentsList = new LinkedList<>();
		for (String line : data) {
			StudentRecord student = parseLine(line);
			students.put(student.getJmbag(), student);
			studentsList.add(student);
		}
	}

	/**
	 * Parses the given line and creates a new {@code StudentRecord} with the
	 * data from the line.
	 * 
	 * @param line
	 *            string that contains the data about one student. Every student
	 *            has his/her jmbag number which is unique for every student.
	 *            The student also has a first name, last name and a final
	 *            grade.
	 * @return the new created {@code StudentRecord} with the data from the line.
	 * @throws IllegalArgumentException
	 *             if the line is not valid
	 */
	private StudentRecord parseLine(String line) {
		if (line == null) {
			throw new IllegalArgumentException("The line is not allowed to be null!");
		}

		String[] lineData = line.split("\\t");
		if (lineData.length != 4) {
			throw new IllegalArgumentException(
				"The given line should contain 4 arguments, but the given one has "
						+ lineData.length);
		}

		String jmbag = lineData[0];
		String lastName = lineData[1];
		String firstName = lineData[2];
		int finalGrade = -1;

		try {
			finalGrade = Integer.parseInt(lineData[3]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
				"The given line contains a final grade that could not be parsed as an integer. "
						+ "An integer is expected, but " + lineData[3] + " was given.");
		}

		return new StudentRecord(jmbag, lastName, firstName, finalGrade);
	}

	/**
	 * Returns the {@code StudentRecord} for the student whose jmbag is equal to
	 * the one given as the argument. If no such student is found in the
	 * database, null is returned. <br>
	 * This method has a time complexity of <strong>O(1)</strong>
	 * 
	 * @param jmbag
	 *            the jmbag of the student to be found
	 * @return the {@code StudentRecord} of the student with the given jmbag, or
	 *         null if no such student exists.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return students.get(jmbag);
	}

	/**
	 * Checks all the students in the database using the given filter and
	 * returns a new created list of the ones that are accepted by the filter
	 * criteria.
	 * 
	 * @param filter
	 *            the {@link IFilter} whose
	 *            {@link IFilter#accepts(StudentRecord)} method is used to
	 *            determine if a {@code StudentRecord} should be added into the
	 *            list or not.
	 * @return a {@code List} of all the students who are accepted by the
	 *         {@code IFilter}.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		if (filter == null) {
			throw new IllegalArgumentException("The given filter was null!");
		}

		List<StudentRecord> studentList = new ArrayList<>();

		for (StudentRecord student : studentsList) {
			if (filter.accepts(student)) {
				studentList.add(student);
			}
		}

		return studentList;
	}
}
