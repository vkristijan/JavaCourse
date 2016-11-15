package hr.fer.zemris.java.tecaj.hw6.demo5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A program that reads the data about students from a file and does some
 * queries on them.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class StudentDemo {
	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"));
		} catch (IOException e) {
			System.err.println("Unable to read the file!");
			System.exit(1);
		}
		List<StudentRecord> records = convert(lines);

		task1(records);
		task2(records);
		task3(records);
		task4(records);
		task5(records);
		task6(records);
		task7(records);
		task8(records);
	}

	/**
	 * Counts the number of students who have the sum of scores on all the exam
	 * higher than 25.
	 * 
	 * @param records
	 *            a list of all the students.
	 */
	private static void task1(List<StudentRecord> records) {
		long broj = records.stream().filter((student) -> student.getTotalScore() > 25).count();

		System.out.println("Broj studenata koji imaju barem 25 bodova: " + broj);
	}

	/**
	 * Counts the number of students who have got the grade 5.
	 * 
	 * @param records
	 *            a list of all the students.
	 */
	private static void task2(List<StudentRecord> records) {
		long broj5 = records.stream().filter((student) -> student.getGrade() == 5).count();

		System.out.println("Broj studenata koji su dobili ocjenu 5: " + broj5);
	}

	/**
	 * Gets a list of all the students who got the grade 5.
	 * 
	 * @param records
	 *            a list of all the students.
	 */
	private static void task3(List<StudentRecord> records) {
		List<StudentRecord> odlikasi = records.stream()
				.filter((student) -> student.getGrade() == 5).collect(Collectors.toList());

		System.out.println("Popis odlikasa:");
		odlikasi.forEach(System.out::println);
	}

	/**
	 * Gets a list of all the students who got the grade 5, sorted in such a way
	 * that the student with the highest score is first in the list, and the
	 * student with the lowest score is last.
	 * 
	 * @param records
	 *            a list of all the students.
	 */
	private static void task4(List<StudentRecord> records) {
		List<StudentRecord> odlikasiSortirano = records.stream()
				.filter((student) -> student.getGrade() == 5)
				.sorted((s1, s2) -> Double.compare(s2.getTotalScore(), s1.getTotalScore()))
				.collect(Collectors.toList());

		System.out.println("Sortiran popis odlikasa:");
		odlikasiSortirano.forEach(System.out::println);
	}

	/**
	 * Gets a list of all the students who failed the class, sorted by their
	 * jmbag number.
	 * 
	 * @param records
	 *            a list of all the students.
	 */
	private static void task5(List<StudentRecord> records) {
		List<String> nepolozeniJMBAGovi = records.stream()
				.filter((student) -> student.getGrade() == 1)
				.sorted((s1, s2) -> s1.getJmbag().compareTo(s2.getJmbag()))
				.map(StudentRecord::getJmbag).collect(Collectors.toList());

		System.out.println("Sortiran popis osoba koje su pale:");
		nepolozeniJMBAGovi.forEach(System.out::println);
	}

	/**
	 * Maps all student by the grade they got. The result is a map with key
	 * {@link Integer} (the grade) and value {@link StudentRecord}.
	 * 
	 * @param records
	 *            a list of all the students.
	 */
	private static void task6(List<StudentRecord> records) {
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = records.stream().collect(
			Collectors.groupingBy((StudentRecord::getGrade), Collectors.toList()));

		System.out.println("Mapa po ocjenama:");
		mapaPoOcjenama.forEach((grade, students) -> {
			System.out.println("Studenti s ocjenom " + grade + ":");
			students.forEach(System.out::println);
		});
	}

	/**
	 * Creates a map, where the key is an {@link Integer} representing the
	 * grade, and value is an {@link Integer} representing the number of
	 * students who got that grade.
	 * 
	 * @param records
	 *            a list of all the students.
	 */
	private static void task7(List<StudentRecord> records) {
		Map<Integer, Integer> mapaPoOcjenama2 = records.stream().collect(Collectors.toMap(
			(StudentRecord::getGrade), ((Student) -> Integer.valueOf(1)), ((s, a) -> s + 1)));

		System.out.println("Mapa po ocjenama 2:");
		mapaPoOcjenama2.forEach((grade, count) -> {
			System.out.println("Ocjenu " + grade + " ima " + count + " studenata.");
		});
	}

	/**
	 * Creates a map where the key is a {@link Boolean} value <code>true</code>
	 * if the student has passed the course, and <code>false</code> otherwise.
	 * The value in the map is a list of all the students according to the
	 * criteria in the key.
	 * 
	 * 
	 * @param records
	 *            a list of all the students.
	 */
	private static void task8(List<StudentRecord> records) {
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = records.stream()
				.collect(Collectors.partitioningBy((student) -> student.getGrade() > 1));

		System.out.println("Studenti koji su prosli/pali: ");
		prolazNeprolaz.forEach((k, v) -> {
			System.out.println(k ? "Studenti koji su prosli:" : "Studenti koji su pali:");
			v.forEach(System.out::println);
		});

	}

	/**
	 * Converts the list of strings containing the data about students to a list
	 * of student records.
	 * 
	 * @param lines
	 *            a list of strings containing data about students
	 * @return a list of student records.
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> list = new ArrayList<>();

		for (String line : lines) {
			line = line.trim();
			if (line.isEmpty()) continue;
			String[] lineArgs = line.split("\t");
			if (lineArgs.length != 7) {
				System.err.println(line);
				throw new IllegalArgumentException(
					"Invalid number of arguments for the given student record!");
			}

			String jmbag = lineArgs[0];
			String lName = lineArgs[1];
			String fName = lineArgs[2];
			double scoreMI = 0;
			double scoreZI = 0;
			double scoreL = 0;
			int grade = 0;
			try {
				scoreMI = Double.parseDouble(lineArgs[3]);
				scoreZI = Double.parseDouble(lineArgs[4]);
				scoreL = Double.parseDouble(lineArgs[5]);
				grade = Integer.parseInt(lineArgs[6]);
			} catch (NumberFormatException e) {
				System.err.println("The given number could not be parsed as a number!");
				System.exit(1);
			}

			StudentRecord student;
			student = new StudentRecord(jmbag, lName, fName, scoreMI, scoreZI, scoreL, grade);
			list.add(student);
		}
		return list;
	}
}
