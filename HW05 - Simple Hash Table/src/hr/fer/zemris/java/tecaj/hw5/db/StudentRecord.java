package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * A record containing all the data about a single student. Every student has
 * his/her jmbag number which is unique for every student. The student also has
 * a first name, last name and a final grade.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class StudentRecord {
	/** The student's jmbag. */
	private final String jmbag;
	/** The student's last name. */
	private String lastName;
	/** The student's first name. */
	private String firstName;
	/** The student's final grade. */
	private int finalGrade;

	/**
	 * Creates a new {@code StudentRecord} with the values given in the
	 * arguments.
	 * 
	 * @param jmbag
	 *            the jmbag of the student.
	 * @param lastName
	 *            the student's last name.
	 * @param firstName
	 *            the student's first name.
	 * @param finalGrade
	 *            the student's final grade.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Returns the last name of the student.
	 * 
	 * @return the last name of the student.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the student to the new value.
	 * 
	 * @param lastName
	 *            the new value for the student's last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the first name of the student.
	 * 
	 * @return the first name of the student.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the student to the new value.
	 * 
	 * @param firstName
	 *            the new value for the student's first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the final grade of the student.
	 * 
	 * @return the final grade of the student.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Sets the final grade of the student to the new value.
	 * 
	 * @param finalGrade
	 *            the new value for the student's final grade.
	 */
	public void setFinalGrade(int finalGrade) {
		this.finalGrade = finalGrade;
	}

	/**
	 * Returns the jmbag of the student.
	 * 
	 * @return the jmbag of the student.
	 */
	public String getJmbag() {
		return jmbag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null) return false;
		} else if (!jmbag.equals(other.jmbag)) return false;
		return true;
	}
}
