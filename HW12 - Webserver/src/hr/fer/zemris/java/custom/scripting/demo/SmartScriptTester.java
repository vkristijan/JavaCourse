package hr.fer.zemris.java.custom.scripting.demo;

import static hr.fer.zemris.java.custom.scripting.lexer.Utility.TAG_BEGIN;
import static hr.fer.zemris.java.custom.scripting.lexer.Utility.TAG_END;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

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

		SmartScriptParser parser = new SmartScriptParser(docBody);

		WriterVisitor visitor = new WriterVisitor();
		parser.getDocumentNode().accept(visitor); 
	}


	/**
	 * Prints the body of the document on which the visitor was called to the
	 * standard system output.. The created document should be equal to the
	 * original one, but there could be some smaller difference with spaces.
	 * However, it will be possible to parse the created document, and the
	 * result will be equal to the starting given.
	 */
	private static class WriterVisitor implements INodeVisitor{
		@Override
		public void visitDocumentNode(DocumentNode node) {
			printChildNodes(node);	
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			printChildNodes(node);	
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			printChildNodes(node);	
			
			System.out.print(TAG_BEGIN);
			System.out.print("END ");
			System.out.print(TAG_END);
		}

		@Override
		public void visitTextNode(TextNode node) {
			printChildNodes(node);
		}
		
		/**
		 * Goes through all the child nodes and calls the visitor on them.
		 * 
		 * @param node
		 *            the current node.
		 */
		private void printChildNodes(Node node){
			System.out.print(node.asText());
			
			int size = node.numberOfChildren();
			for (int i = 0; i < size; ++i) {
				node.getChild(i).accept(WriterVisitor.this);;
			}
		}
		
	}
}
