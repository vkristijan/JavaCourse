package hr.fer.zemris.java.tecaj.hw6.demo5;

/**
 * Stores all the data about a single student. Every student has the following
 * properties:
 * <ul>
 * <li><strong>jmbag</strong> - The student's jmbag number.</li>
 * <li><strong>last name</strong> - The student's last name.</li>
 * <li><strong>first name</strong> - The student's first name.</li>
 * <li><strong>score MI</strong> - The student's score on the first exam.</li>
 * <li><strong>score ZI</strong> - The student's score on the final exam.</li>
 * <li><strong>score Lab</strong> - The student's score on the laboratory
 * exercises.</li>
 * <li><strong>grade</strong> - The student's final grade.</li>
 * </ul>
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class StudentRecord {
	/** The student's jmbag number. */
	private final String jmbag;
	/** The student's last name. */
	private final String lastName;
	/** The student's first name. */
	private final String firstName;
	/** The student's score on the first exam. */
	private double scoreMI;
	/** The student's score on the final exam. */
	private double scoreZI;
	/** The student's score on the laboratory exercises. */
	private double scoreLab;
	/** The student's final grade. */
	private int grade;

	/**
	 * Creates a new {@link StudentRecord} with the values set to the values
	 * given in the arguments.
	 * 
	 * @param jmbag
	 *            The student's jmbag number.
	 * @param lastName
	 *            The student's last name.
	 * @param firstName
	 *            The student's first name.
	 * @param scoreMI
	 *            The student's score on the first exam.
	 * @param scoreZI
	 *            The student's score on the final exam.
	 * @param scoreLab
	 *            The student's score on the laboratory exercises.
	 * @param grade
	 *            The student's final grade.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double scoreMI,
			double scoreZI, double scoreLab, int grade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.scoreMI = scoreMI;
		this.scoreZI = scoreZI;
		this.scoreLab = scoreLab;
		this.grade = grade;
	}

	/**
	 * Returns the student's score on the first exam.
	 * 
	 * @return the student's score on the first exam.
	 */
	public double getScoreMI() {
		return scoreMI;
	}

	/**
	 * Sets the student's score on the first exam to the value given in the
	 * argument.
	 * 
	 * @param scoreMI
	 *            the new score.
	 */
	public void setScoreMI(double scoreMI) {
		this.scoreMI = scoreMI;
	}

	/**
	 * Returns the student's score on the final exam.
	 * 
	 * @return the student's score on the final exam.
	 */
	public double getScoreZI() {
		return scoreZI;
	}

	/**
	 * Sets the student's score on the final exam to the value given in the
	 * argument.
	 * 
	 * @param scoreZI
	 *            the new score.
	 */
	public void setScoreZI(double scoreZI) {
		this.scoreZI = scoreZI;
	}

	/**
	 * Returns the student's score on the laboratory exercises.
	 * 
	 * @return the student's score on the laboratory exercises.
	 */
	public double getScoreLab() {
		return scoreLab;
	}

	/**
	 * Sets the student's score on the laboratory exercises to the value given
	 * in the argument.
	 * 
	 * @param scoreLab
	 *            the new score.
	 */
	public void setScoreLab(double scoreLab) {
		this.scoreLab = scoreLab;
	}

	/**
	 * Returns the final grade of the student.
	 * 
	 * @return the final grade of the student.
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * Sets the student's final grade to the value given in the argument.
	 * 
	 * @param grade
	 *            the new final grade for the student.
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * Returns the jmbag number of the student.
	 * 
	 * @return the jmbag number of the student.
	 */
	public String getJmbag() {
		return jmbag;
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
	 * Returns the first name of the student.
	 * 
	 * @return the first name of the student.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the total score of the student on both exams and laboratory
	 * exercises.
	 * 
	 * @return the total score of the student on both exams and laboratory
	 *         exercises.
	 */
	public double getTotalScore() {
		return scoreMI + scoreZI + scoreLab;
	}

	@Override
	public String toString() {
		return "StudentRecord [jmbag=" + jmbag + ", lastName=" + lastName + ", firstName="
				+ firstName + ", scoreMI=" + scoreMI + ", scoreZI=" + scoreZI + ", scoreLab="
				+ scoreLab + ", grade=" + grade + "]";
	}

}
