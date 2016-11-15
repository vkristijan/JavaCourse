package hr.fer.zemris.java.hw3;

import static hr.fer.zemris.java.custom.scripting.lexer.Utility.TAG_BEGIN;
import static hr.fer.zemris.java.custom.scripting.lexer.Utility.TAG_END;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * A program that demonstrates the {@link SmartScriptParser}. It takes a single
 * argument from the console that has the path to a document containing a Smart
 * Script code. The parser opens the file and parses it. After parsing, every
 * node of the document is written to the standard output. As a result of that
 * the output will represent the same document, in such a way that parsing the
 * document again would give the same nodes.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class SmartScriptTester {
	/**
	 * The program entry point.
	 * 
	 * @param args
	 *            the command line arguments
	 * @throws IOException
	 *             if it's not possible to open the given file.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException("Wrong number of arguments!");
		}
		String filepath = args[0];
		String docBody = new String(Files.readAllBytes(Paths.get(filepath)),
			StandardCharsets.UTF_8);

		SmartScriptParser parser = null;
		parser = new SmartScriptParser(docBody);

		try {
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);
	}

	/**
	 * Creates a string containing the body of the document given as the
	 * argument. The created document should be equal to the original one, but
	 * there could be some smaller difference with spaces. However, it will be
	 * possible to parse the created document, and the result will be equal to
	 * the documentNode given.
	 * 
	 * @param document
	 *            the head {@link Node} of the document.
	 * @return a string containing the body of the document.
	 */
	private static String createOriginalDocumentBody(Node document) {
		StringBuilder documentText = new StringBuilder();
		documentText.append(document.asText());

		int size = document.numberOfChildren();
		for (int i = 0; i < size; ++i) {
			documentText.append(createOriginalDocumentBody(document.getChild(i)));
		}

		if (document instanceof ForLoopNode) {
			documentText.append(TAG_BEGIN);
			documentText.append("END ");
			documentText.append(TAG_END);
		}
		return documentText.toString();
	}

}
