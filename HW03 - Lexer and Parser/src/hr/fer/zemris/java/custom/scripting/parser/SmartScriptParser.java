package hr.fer.zemris.java.custom.scripting.parser;

import static hr.fer.zemris.java.custom.scripting.lexer.Utility.TAG_BEGIN;
import static hr.fer.zemris.java.custom.scripting.lexer.Utility.TAG_END;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;;

/**
 * A parser that is capable of parsing a SmartScript. After getting a string
 * containing the body of a document to be parsed this class goes through the
 * text and creates {@link Node}s representing the data in the text. This parser
 * uses the following nodes:
 * <ul>
 * <li>{@link DocumentNode} to represent the whole document.</li>
 * <li>{@link TextNode} to represent text inside a document.</li>
 * <li>{@link EchoNode} to represent the body of the echo command.</li>
 * <li>{@link ForLoopNode} to represent the body of a for loop, and all the
 * content inside the loop.</li>
 * </ul>
 * In order to create the nodes, this parser uses {@link Lexer}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class SmartScriptParser {
	/**
	 * The lexer used to get the tokens.
	 */
	private final Lexer lexer;

	/**
	 * A stack with the current node hierarchy. The element on top of the stack
	 * is the node that is currently parsed.
	 */
	private ObjectStack nodes;
	/**
	 * The root node of the document.
	 */
	private DocumentNode document;

	/**
	 * Creates the parser for the given text and starts parsing it.
	 * 
	 * @param documentBody
	 *            the text to be parsed
	 * @throws SmartScriptParserException
	 *             if anything goes wrong
	 */
	public SmartScriptParser(String documentBody) {
		lexer = new Lexer(documentBody);
		document = new DocumentNode();

		parse();
	}

	/**
	 * Returns the root node of the document.
	 * 
	 * @return the root node of the document.
	 */
	public DocumentNode getDocumentNode() {
		return document;
	}

	/**
	 * Parses the document generating nodes as described in the class.
	 * 
	 * @throws SmartScriptParserException
	 *             if anything goes wrong
	 */
	private void parse() {
		try {
			nodes = new ObjectStack();
			nodes.push(document);

			Token token = lexer.nextToken();
			while (token.getType() != TokenType.EOF) {
				if (token.getType() == TokenType.SYMBOL) {
					parseTag();
				} else if (token.getType() == TokenType.TEXT) {
					TextNode node = new TextNode((String) token.getValue());
					((Node) nodes.peek()).addChildNode(node);
				} else {
					throw new SmartScriptParserException("Unexpected token!");
				}

				token = lexer.nextToken();
			}
		} catch (Exception e) {
			throw new SmartScriptParserException("Something went wrong!");
		}
	}

	/**
	 * Checks if the name in the given token is a valid tag name. In order for a
	 * string to be a valid tag name it must begin with a letter or with a '='
	 * character, following with zero or more alphanumeric characters and
	 * underscores.
	 * 
	 * @param name
	 *            the token containing the tag name
	 * @throws SmartScriptParserException
	 *             if the name is not valid
	 */
	private void checkTagName(final Token name) {
		if (name == null || name.getType() != TokenType.VARIABLE) {
			throw new SmartScriptParserException("Tag name invalid!");
		}

		if (!(name.getValue() instanceof String)) {
			throw new SmartScriptParserException("Tag name invalid!");
		}
	}

	/**
	 * Parses the document once a tag is reached.
	 * 
	 * @throws SmartScriptParserException
	 *             if the current token is not a beginning of a tag or if the
	 *             parsed tag is not supported by this implementation
	 */
	private void parseTag() {
		lexer.setState(LexerState.TAG_MODE);
		if (!lexer.getToken().getValue().equals(TAG_BEGIN)) {
			throw new SmartScriptParserException("Unable to parse the given tag!");
		}

		Token name = lexer.nextToken();
		checkTagName(name);
		String nameValue = ((String) name.getValue()).toUpperCase();

		if (nameValue.equals("=")) {
			parseEcho();
		} else if (nameValue.equals("FOR")) {
			parseFor();
		} else if (nameValue.equals("END")) {
			parseEnd();
		} else {
			throw new SmartScriptParserException("Tag not supported!");
		}

		lexer.setState(LexerState.TEXT_MODE);
	}

	/**
	 * After entering a end tag, this method can check if the end of the tag is
	 * valid and handle all the arguments.
	 * 
	 * @throws SmartScriptParserException
	 *             if the number of end tags is invalid or if there are some
	 *             arguments given to the end tag
	 */
	private void parseEnd() {
		if (nodes.size() <= 1) {
			throw new SmartScriptParserException(
				"Invalid number of opening and clossing tags!");
		}

		nodes.pop();

		Token[] tokens = getParameters();
		if (tokens.length > 0) {
			throw new SmartScriptParserException("End tag does not accept parameters!");
		}
	}

	/**
	 * Parses a for loop tag.
	 * 
	 * @throws SmartScriptParserException
	 *             if the argument count is invalid
	 */
	private void parseFor() {
		Token[] tokens = getParameters();

		if (tokens.length < 3 || tokens.length > 4) {
			throw new SmartScriptParserException("Invalid number of parameters!");
		}

		if (tokens[0].getType() != TokenType.VARIABLE) {
			throw new SmartScriptParserException("First argument must be a variable!");
		}
		String variableName = (String) tokens[0].getValue();
		checkVariableName(variableName);

		ElementVariable variable = new ElementVariable(variableName);
		Element startExp = getForParameter(tokens[1]);
		Element endExp = getForParameter(tokens[2]);
		Element stepExp = null;
		if (tokens.length == 4) {
			stepExp = getForParameter(tokens[3]);
		}

		ForLoopNode forNode = new ForLoopNode(variable, startExp, endExp, stepExp);
		((Node) nodes.peek()).addChildNode(forNode);
		nodes.push(forNode);
	}

	/**
	 * Parses an echo tag.
	 */
	private void parseEcho() {
		Token[] tokens = getParameters();
		Element[] elements = new Element[tokens.length];

		for (int i = 0; i < tokens.length; ++i) {
			elements[i] = getParameter(tokens[i]);
		}

		EchoNode echoNode = new EchoNode(elements);
		((Node) nodes.peek()).addChildNode(echoNode);
	}

	/**
	 * Gets and checks the parameters inside a for tag. The allowed parameters
	 * are tokens of the following types: {@link TokenType#INTEGER},
	 * {@link TokenType#DOUBLE}, {@link TokenType#STRING}.
	 * 
	 * @param token
	 *            the token containing the parameter.
	 * @return an element with of the corresponding type for the given
	 *         parameter.
	 * @throws SmartScriptParserException
	 *             if the given token doesn't contain a valid element.
	 */
	private Element getForParameter(Token token) {
		switch (token.getType()) {
			case INTEGER:
			case DOUBLE:
			case STRING:
				return getParameter(token);
			default:
				throw new SmartScriptParserException("Invalid parameter!");
		}
	}

	/**
	 * Extracts a parameter from a token and checks if it is valid.
	 * 
	 * @param token
	 *            the token containing the parameter.
	 * @return an element with of the corresponding type for the given
	 *         parameter.
	 * @throws SmartScriptParserException
	 *             if the given token doesn't contain a valid element.
	 */
	private Element getParameter(Token token) {
		switch (token.getType()) {
			case INTEGER:
				return new ElementConstantInteger((int) token.getValue());
			case DOUBLE:
				return new ElementConstantDouble((double) token.getValue());
			case STRING:
				return new ElementString((String) token.getValue());
			case VARIABLE:
				String name = (String) token.getValue();
				checkVariableName(name);
				return new ElementVariable(name);
			case OPERATOR:
				return new ElementOperator((String) token.getValue());
			case FUNCTION:
				name = (String) token.getValue();
				checkFunctionName(name);
				return new ElementFunction(name);
			default:
				throw new SmartScriptParserException("Invalid parameter!");
		}
	}

	/**
	 * Checks if the name in the given token is a valid function name. In order
	 * for a string to be a valid function name it must begin with a letter,
	 * following with zero or more alphanumeric characters and underscores.
	 * 
	 * @param name
	 *            the token containing the function name
	 * @throws SmartScriptParserException
	 *             if the name is not valid
	 */
	private void checkFunctionName(String name) {
		// Right now a valid function name is the same as a valid variable name.
		checkVariableName(name);
	}

	/**
	 * Checks if the name in the given token is a valid variable name. In order
	 * for a string to be a valid variable name it must begin with a letter,
	 * following with zero or more alphanumeric characters and underscores.
	 * 
	 * @param name
	 *            the token containing the variable name
	 * @throws SmartScriptParserException
	 *             if the name is not valid
	 */
	private void checkVariableName(String name) {
		if (name == null) {
			throw new SmartScriptParserException("Varible name is not allowed to be null!");
		}

		if (!name.matches("^[a-zA-Z]\\w*$")) {
			throw new SmartScriptParserException("Invalid variable name!");
		}
	}

	/**
	 * Returns a list of all the parameters in this tag.
	 * 
	 * @return a list of all the parameters in this tag.
	 * @throws SmartScriptParserException
	 *             if the tag is not valid.
	 */
	private Token[] getParameters() {
		ArrayIndexedCollection parameters = new ArrayIndexedCollection();

		Token token = lexer.nextToken();
		while (token.getType() != TokenType.SYMBOL) {
			if (token.getType() == TokenType.EOF) {
				throw new SmartScriptParserException("Invalid tag!");
			}

			parameters.add(token);
			token = lexer.nextToken();
		}
		if (!token.getValue().equals(TAG_END)) {
			throw new SmartScriptParserException("Invalid tag ending!");
		}

		int size = parameters.size();
		Object[] objTokens = parameters.toArray();
		Token[] tokens = new Token[size];

		for (int i = 0; i < size; ++i) {
			tokens[i] = (Token) objTokens[i];
		}
		return tokens;
	}
}
