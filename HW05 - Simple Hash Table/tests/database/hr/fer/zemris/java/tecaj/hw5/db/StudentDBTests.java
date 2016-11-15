package hr.fer.zemris.java.tecaj.hw5.db;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class StudentDBTests {
	@Test
	public void testGreaterThan() {
		String input = String.format("query firstName > \"V\"%nexit%n");
		String expected = String.format(" > +============+===========+========+===+%n"
				+ "| 0000000026 | Katunarić | Zoran  | 3 |%n"
				+ "| 0000000040 | Mišura    | Zrinka | 5 |%n"
				+ "| 0000000045 | Rahle     | Vedran | 2 |%n"
				+ "| 0000000056 | Šimunović | Veljko | 5 |%n"
				+ "| 0000000058 | Šoić      | Vice   | 3 |%n"
				+ "| 0000000063 | Žabčić    | Željko | 4 |%n"
				+ "+============+===========+========+===+%n" + "Records selected: 6%n"
				+ " > Goodbye!%n");

		assertEquals(expected, executeTest(input));
	}

	@Test
	public void testLessThan() {
		String input = String.format("query firstName < \"C\"%nexit%n");
		String expected = String.format(" > +============+=================+==========+===+%n"
				+ "| 0000000003 | Bosnić          | Andrea   | 4 |%n"
				+ "| 0000000021 | Jakobušić       | Antonija | 2 |%n"
				+ "| 0000000023 | Kalvarešin      | Ana      | 4 |%n"
				+ "| 0000000030 | Kovačević       | Boris    | 3 |%n"
				+ "| 0000000031 | Krušelj Posavec | Bojan    | 4 |%n"
				+ "| 0000000036 | Markić          | Ante     | 5 |%n"
				+ "| 0000000048 | Rezić           | Bruno    | 5 |%n"
				+ "| 0000000049 | Saratlija       | Branimir | 2 |%n"
				+ "| 0000000050 | Sikirica        | Alen     | 3 |%n"
				+ "| 0000000051 | Skočir          | Andro    | 4 |%n"
				+ "+============+=================+==========+===+%n"
				+ "Records selected: 10%n" + " > Goodbye!%n");

		assertEquals(expected, executeTest(input));
	}

	@Test
	public void testGreaterOrEqual() {
		String input = String.format("query firstName >= \"V\"%nexit%n");
		String expected = String.format(" > +============+===========+========+===+%n"
				+ "| 0000000026 | Katunarić | Zoran  | 3 |%n"
				+ "| 0000000040 | Mišura    | Zrinka | 5 |%n"
				+ "| 0000000045 | Rahle     | Vedran | 2 |%n"
				+ "| 0000000056 | Šimunović | Veljko | 5 |%n"
				+ "| 0000000058 | Šoić      | Vice   | 3 |%n"
				+ "| 0000000063 | Žabčić    | Željko | 4 |%n"
				+ "+============+===========+========+===+%n" + "Records selected: 6%n"
				+ " > Goodbye!%n");

		assertEquals(expected, executeTest(input));
	}

	@Test
	public void testLessOrEqual() {
		String input = String.format("query firstName <= \"c\"%nexit%n");
		String expected = String.format(" > +============+=================+==========+===+%n"
				+ "| 0000000003 | Bosnić          | Andrea   | 4 |%n"
				+ "| 0000000021 | Jakobušić       | Antonija | 2 |%n"
				+ "| 0000000023 | Kalvarešin      | Ana      | 4 |%n"
				+ "| 0000000030 | Kovačević       | Boris    | 3 |%n"
				+ "| 0000000031 | Krušelj Posavec | Bojan    | 4 |%n"
				+ "| 0000000036 | Markić          | Ante     | 5 |%n"
				+ "| 0000000048 | Rezić           | Bruno    | 5 |%n"
				+ "| 0000000049 | Saratlija       | Branimir | 2 |%n"
				+ "| 0000000050 | Sikirica        | Alen     | 3 |%n"
				+ "| 0000000051 | Skočir          | Andro    | 4 |%n"
				+ "+============+=================+==========+===+%n"
				+ "Records selected: 10%n" + " > Goodbye!%n");

		assertEquals(expected, executeTest(input));
	}

	@Test
	public void testEqual() {
		String input = String.format("query firstName = \"Marin\"%nexit%n");
		String expected = String.format(" > +============+===========+=======+===+%n"
				+ "| 0000000001 | Akšamović | Marin | 2 |%n"
				+ "| 0000000004 | Božić     | Marin | 5 |%n"
				+ "| 0000000059 | Štruml    | Marin | 4 |%n"
				+ "+============+===========+=======+===+%n" + "Records selected: 3%n"
				+ " > Goodbye!%n");

		assertEquals(expected, executeTest(input));
	}

	@Test
	public void testNotEqual() {
		String input = String.format("query firstName != \"Marin\"%nexit%n");
		String expected = String
				.format(" > +============+==================+===========+===+%n"
						+ "| 0000000002 | Bakamović        | Petra     | 3 |%n"
						+ "| 0000000003 | Bosnić           | Andrea    | 4 |%n"
						+ "| 0000000005 | Brezović         | Jusufadis | 2 |%n"
						+ "| 0000000006 | Cvrlje           | Ivan      | 3 |%n"
						+ "| 0000000007 | Čima             | Sanjin    | 4 |%n"
						+ "| 0000000008 | Ćurić            | Marko     | 5 |%n"
						+ "| 0000000009 | Dean             | Nataša    | 2 |%n"
						+ "| 0000000010 | Dokleja          | Luka      | 3 |%n"
						+ "| 0000000011 | Dvorničić        | Jura      | 4 |%n"
						+ "| 0000000012 | Franković        | Hrvoje    | 5 |%n"
						+ "| 0000000013 | Gagić            | Mateja    | 2 |%n"
						+ "| 0000000014 | Gašić            | Mirta     | 3 |%n"
						+ "| 0000000015 | Glavinić Pecotić | Kristijan | 4 |%n"
						+ "| 0000000016 | Glumac           | Milan     | 5 |%n"
						+ "| 0000000017 | Grđan            | Goran     | 2 |%n"
						+ "| 0000000018 | Gužvinec         | Matija    | 3 |%n"
						+ "| 0000000019 | Gvardijan        | Slaven    | 4 |%n"
						+ "| 0000000020 | Hibner           | Sonja     | 5 |%n"
						+ "| 0000000021 | Jakobušić        | Antonija  | 2 |%n"
						+ "| 0000000022 | Jurina           | Filip     | 3 |%n"
						+ "| 0000000023 | Kalvarešin       | Ana       | 4 |%n"
						+ "| 0000000024 | Karlović         | Đive      | 5 |%n"
						+ "| 0000000025 | Katanić          | Dino      | 2 |%n"
						+ "| 0000000026 | Katunarić        | Zoran     | 3 |%n"
						+ "| 0000000027 | Komunjer         | Luka      | 4 |%n"
						+ "| 0000000028 | Kosanović        | Nenad     | 5 |%n"
						+ "| 0000000029 | Kos-Grabar       | Ivo       | 2 |%n"
						+ "| 0000000030 | Kovačević        | Boris     | 3 |%n"
						+ "| 0000000031 | Krušelj Posavec  | Bojan     | 4 |%n"
						+ "| 0000000032 | Lučev            | Tomislav  | 5 |%n"
						+ "| 0000000033 | Machiedo         | Ivor      | 2 |%n"
						+ "| 0000000034 | Majić            | Diana     | 3 |%n"
						+ "| 0000000035 | Marić            | Ivan      | 4 |%n"
						+ "| 0000000036 | Markić           | Ante      | 5 |%n"
						+ "| 0000000037 | Markoč           | Domagoj   | 2 |%n"
						+ "| 0000000038 | Markotić         | Josip     | 3 |%n"
						+ "| 0000000039 | Martinec         | Jelena    | 4 |%n"
						+ "| 0000000040 | Mišura           | Zrinka    | 5 |%n"
						+ "| 0000000041 | Orešković        | Jakša     | 2 |%n"
						+ "| 0000000042 | Palajić          | Nikola    | 3 |%n"
						+ "| 0000000043 | Perica           | Krešimir  | 4 |%n"
						+ "| 0000000044 | Pilat            | Ivan      | 5 |%n"
						+ "| 0000000045 | Rahle            | Vedran    | 2 |%n"
						+ "| 0000000046 | Rajtar           | Robert    | 3 |%n"
						+ "| 0000000047 | Rakipović        | Ivan      | 4 |%n"
						+ "| 0000000048 | Rezić            | Bruno     | 5 |%n"
						+ "| 0000000049 | Saratlija        | Branimir  | 2 |%n"
						+ "| 0000000050 | Sikirica         | Alen      | 3 |%n"
						+ "| 0000000051 | Skočir           | Andro     | 4 |%n"
						+ "| 0000000052 | Slijepčević      | Josip     | 5 |%n"
						+ "| 0000000053 | Srdarević        | Dario     | 2 |%n"
						+ "| 0000000054 | Šamija           | Pavle     | 3 |%n"
						+ "| 0000000055 | Šimunov          | Ivan      | 4 |%n"
						+ "| 0000000056 | Šimunović        | Veljko    | 5 |%n"
						+ "| 0000000057 | Širanović        | Hrvoje    | 2 |%n"
						+ "| 0000000058 | Šoić             | Vice      | 3 |%n"
						+ "| 0000000060 | Vignjević        | Irena     | 5 |%n"
						+ "| 0000000061 | Vukojević        | Renato    | 2 |%n"
						+ "| 0000000062 | Zadro            | Kristijan | 3 |%n"
						+ "| 0000000063 | Žabčić           | Željko    | 4 |%n"
						+ "+============+==================+===========+===+%n"
						+ "Records selected: 60%n" + " > Goodbye!%n");

		assertEquals(expected, executeTest(input));
	}

	@Test
	public void testLike() {
		String input = String.format("query firstName LIKE \"M*\"%nexit%n");
		String expected = String.format(" > +============+===========+========+===+%n"
				+ "| 0000000001 | Akšamović | Marin  | 2 |%n"
				+ "| 0000000004 | Božić     | Marin  | 5 |%n"
				+ "| 0000000008 | Ćurić     | Marko  | 5 |%n"
				+ "| 0000000013 | Gagić     | Mateja | 2 |%n"
				+ "| 0000000014 | Gašić     | Mirta  | 3 |%n"
				+ "| 0000000016 | Glumac    | Milan  | 5 |%n"
				+ "| 0000000018 | Gužvinec  | Matija | 3 |%n"
				+ "| 0000000059 | Štruml    | Marin  | 4 |%n"
				+ "+============+===========+========+===+%n" + "Records selected: 8%n"
				+ " > Goodbye!%n");

		assertEquals(expected, executeTest(input));
	}

	@Test
	public void testGetByJmbag() {
		String input = String.format("indexquery jmbag = \"0000000015\"%nexit%n");
		String expected = String.format(" > Using index for record retrieval.%n"
				+ "+============+==================+===========+===+%n"
				+ "| 0000000015 | Glavinić Pecotić | Kristijan | 4 |%n"
				+ "+============+==================+===========+===+%n"
				+ "Records selected: 1%n" + " > Goodbye!%n");

		assertEquals(expected, executeTest(input));
	}

	@Test
	public void testMultipleArguments() {
		String input = String.format(
			"query jmbag != \"0000000015\" and firstName LIKE \"M*\" AnD lastName LIKE \"*ć\"%nexit%n");
		String expected = String.format(" > +============+===========+========+===+%n"
				+ "| 0000000001 | Akšamović | Marin  | 2 |%n"
				+ "| 0000000004 | Božić     | Marin  | 5 |%n"
				+ "| 0000000008 | Ćurić     | Marko  | 5 |%n"
				+ "| 0000000013 | Gagić     | Mateja | 2 |%n"
				+ "| 0000000014 | Gašić     | Mirta  | 3 |%n"
				+ "+============+===========+========+===+%n" + "Records selected: 5%n"
				+ " > Goodbye!%n");

		assertEquals(expected, executeTest(input));
	}

	// executes the test and returns the output printed
	private String executeTest(String input) {
		InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		StudentDB.setInputStream(in);

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(bout);
		StudentDB.setOutputStream(out);

		StudentDB.main(new String[0]);
		return bout.toString();
	}
}