package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class LexerTest {

	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");

		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullInput() {
		// must throw!
		new Lexer(null);
	}

	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");

		assertEquals("Empty input must generate only EOF token.", TokenType.EOF,
			lexer.nextToken().getType());
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must
		// return each time what nextToken returned...
		Lexer lexer = new Lexer("");

		Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token,
			lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token,
			lexer.getToken());
	}

	@Test(expected = LexerException.class)
	public void testRadAfterEOF() {
		Lexer lexer = new Lexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullState() {
		new Lexer("").setState(null);
	}

	@Test
	public void testTextMode() {
		Lexer lexer = new Lexer("ovo je tekst");

		Token correctData[] = {
			new Token(TokenType.TEXT, "ovo je tekst"),
			new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testTextWithTags() {
		Lexer lexer = new Lexer("ovo$} je {$tekst koji {ima znakove {$za tagove");

		Token correctData[] = {
			new Token(TokenType.TEXT, "ovo$} je "),
			new Token(TokenType.SYMBOL, "{$"),
			new Token(TokenType.TEXT, "tekst koji {ima znakove "),
			new Token(TokenType.SYMBOL, "{$"),
			new Token(TokenType.TEXT, "za tagove"),
			new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testEscapeSequences() {
		Lexer lexer = new Lexer(
			"ovo je\\{ \\\"tekst koji \\\\ima\\\\ \\nekoliko escape sekvenci");

		Token correctData[] = {
			new Token(TokenType.TEXT,
				"ovo je{ \"tekst koji \\ima\\ \nekoliko escape sekvenci"),
			new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test(expected = LexerException.class)
	public void testWrongEscapeSequence() {
		Lexer lexer = new Lexer("\\ovo je lose escapean text");

		lexer.nextToken();
	}

	@Test
	public void testEscapeTagBegin() {
		Lexer lexer = new Lexer("ovo je pocetak taga\\{$ a ovo je tag {$i dalje tekst");

		Token correctData[] = {
			new Token(TokenType.TEXT, "ovo je pocetak taga{$ a ovo je tag "),
			new Token(TokenType.SYMBOL, "{$"),
			new Token(TokenType.TEXT, "i dalje tekst"),
			new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testTagOperators() {
		Lexer lexer = new Lexer("+ - * / + ^");
		lexer.setState(LexerState.TAG_MODE);

		Token correctData[] = {
			new Token(TokenType.OPERATOR, "+"),
			new Token(TokenType.OPERATOR, "-"),
			new Token(TokenType.OPERATOR, "*"),
			new Token(TokenType.OPERATOR, "/"),
			new Token(TokenType.OPERATOR, "+"),
			new Token(TokenType.OPERATOR, "^"),
			new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testTagFunction() {
		Lexer lexer = new Lexer("@func1 @func2 @func3");
		lexer.setState(LexerState.TAG_MODE);

		Token correctData[] = {
			new Token(TokenType.FUNCTION, "func1"),
			new Token(TokenType.FUNCTION, "func2"),
			new Token(TokenType.FUNCTION, "func3"),
			new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testTagVariable() {
		Lexer lexer = new Lexer("i mojaVarijabla SuperVarijabla =IstoVarijabla");
		lexer.setState(LexerState.TAG_MODE);

		Token correctData[] = {
			new Token(TokenType.VARIABLE, "i"),
			new Token(TokenType.VARIABLE, "mojaVarijabla"),
			new Token(TokenType.VARIABLE, "SuperVarijabla"),
			new Token(TokenType.VARIABLE, "="),
			new Token(TokenType.VARIABLE, "IstoVarijabla"),
			new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testTagInteger() {
		Lexer lexer = new Lexer("12 1235 7853 -42");
		lexer.setState(LexerState.TAG_MODE);

		Token correctData[] = {
			new Token(TokenType.INTEGER, 12),
			new Token(TokenType.INTEGER, 1235),
			new Token(TokenType.INTEGER, 7853),
			new Token(TokenType.INTEGER, -42),
			new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test(expected=LexerException.class)
	public void testBigInteger() {
		Lexer lexer = new Lexer("1212357855245642");
		lexer.setState(LexerState.TAG_MODE);
		lexer.nextToken();
	}
	
	@Test
	public void testTagDouble() {
		Lexer lexer = new Lexer("1.2 123.5 78.53 -42.12");
		lexer.setState(LexerState.TAG_MODE);

		Token correctData[] = {
			new Token(TokenType.DOUBLE, 1.2),
			new Token(TokenType.DOUBLE, 123.5),
			new Token(TokenType.DOUBLE, 78.53),
			new Token(TokenType.DOUBLE, -42.12),
			new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testTagString(){
		Lexer lexer = new Lexer("\"testString1\" \"anotherString\" \"-/*+#string2551*-/51+3\"");
		lexer.setState(LexerState.TAG_MODE);
		
		Token correctData[] = {
			new Token(TokenType.STRING, "testString1"),
			new Token(TokenType.STRING, "anotherString"),
			new Token(TokenType.STRING, "-/*+#string2551*-/51+3"),
			new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testMultipleTags(){
		Lexer lexer = new Lexer("{$FOR i 0 10.2 2 $}");
		lexer.setState(LexerState.TAG_MODE);
		
		Token correctData[] = {
			new Token(TokenType.SYMBOL, "{$"),
			new Token(TokenType.VARIABLE, "FOR"),
			new Token(TokenType.VARIABLE, "i"),
			new Token(TokenType.INTEGER, 0),
			new Token(TokenType.DOUBLE, 10.2),
			new Token(TokenType.INTEGER, 2),
			new Token(TokenType.SYMBOL, "$}"),
			new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(Lexer lexer, Token[] correctData) {
		int counter = 0;
		for (Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
	}
}
