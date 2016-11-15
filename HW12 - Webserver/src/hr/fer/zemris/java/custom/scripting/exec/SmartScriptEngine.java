package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.exec.multistack.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.multistack.ValueWrapper;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * An engine capable of executing smart script language. The engine uses a
 * document node that has the whole node tree in it and executes the commands.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class SmartScriptEngine {
	/**
	 * The root document node from the parsed document. This node contains the
	 * data of the whole document.
	 */
	private DocumentNode documentNode;
	/**
	 * {@link RequestContext} used in this engine.
	 */
	private RequestContext requestContext;
	/**
	 * Multistack used to store variable values and names.
	 */
	private ObjectMultistack multistack;
	
	/**
	 * The visitor used to execute the smarts script from the parsed document.
	 */
	private INodeVisitor visitor = new INodeVisitor() {
		@Override
		public void visitDocumentNode(DocumentNode node) {
			int size = node.numberOfChildren();
			
			for (int i = 0; i < size; ++i){
				node.getChild(i).accept(this);
			}
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> echoStack = new Stack<>();
			
			for (Element elem : node.getElements()){
				if (elem instanceof ElementOperator){
					calculateOperator((ElementOperator) elem, echoStack);
				} else if (elem instanceof ElementFunction){
					calculateFunction((ElementFunction) elem, echoStack);
				} else if (elem instanceof ElementVariable){
					calculateVariable((ElementVariable) elem, echoStack);
				} else {
					try {
						echoStack.push(elem.getConstant());
					} catch (RuntimeException e) {
						String message = "The given token type is not supported!";
						throw new SmartScriptEngineException(message);
					}
				}
			}
			
			Stack<Object> tmpStack = new Stack<>();
			while (!echoStack.isEmpty()){
				tmpStack.push(echoStack.pop());
			}
			
			while (!tmpStack.isEmpty()) {
				try {
					requestContext.write(tmpStack.pop().toString());
				} catch (IOException e) {
					String message = "Error while writing to the requested stream.";
					throw new SmartScriptEngineException(message, e);
				}
			}
		}

		/**
		 * Finds the most current variable with that name on object stack (not
		 * this temporary stack!),peeks that variable and pushes its value to
		 * the temporary stack.
		 * 
		 * @param elem
		 *            the {@link ElementOperator} defining the operation to be
		 *            done
		 * @param tempStack
		 *            the temporary stack used for the operations
		 */
		private void calculateVariable(ElementVariable elem, Stack<Object> tempStack) {
			String varName = elem.getName();
			
			Object value = multistack.peek(varName);
			if (value == null){
				value = 0;
			}
			
			tempStack.push(value);
		}

		private void calculateFunction(ElementFunction elem, Stack<Object> echoStack) {
			String funcName = elem.getName();
			
			SmartScriptFunctions.apply(funcName, echoStack, requestContext);
		}

		/**
		 * Pops two arguments from temporary stack, does the required operation
		 * and pushes the result back onto the temporary stack. Currently
		 * supported operators are + (for addition), - (for subtraction), * (for
		 * multiplication) and / (for division).
		 * 
		 * @param elem
		 *            the {@link ElementOperator} defining the operation to be
		 *            done
		 * @param tempStack
		 *            the temporary stack used for the operations
		 * @throws SmartScriptEngineException
		 *             if the given {@link ElementOperator} is not supported
		 */
		private void calculateOperator(ElementOperator elem, Stack<Object> tempStack) {
			ValueWrapper firstArg = new ValueWrapper(tempStack.pop());
			Object secondArg = new ValueWrapper(tempStack.pop()).getValue();
			
			String symbol = elem.getSymbol();
			switch (symbol) {
				case "+":
					firstArg.increment(secondArg);
					break;
				case "-":
					firstArg.decrement(secondArg);
					break;
				case "*":
					firstArg.multiply(secondArg);
					break;
				case "/":
					firstArg.divide(secondArg);
					break;
				default:
					String message = "The operator " + symbol + " is not supported. Only +, -, * and / are supported.";
					throw new SmartScriptEngineException(message);
			}
			
			tempStack.push(firstArg.getValue());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ElementVariable variable = node.getVariable();
			Element start = node.getStartExpression();
			
			String varName = variable.getName();
			multistack.push(varName, new ValueWrapper(start.getConstant()));
			
			Element step = node.getStepExpression();
			Element end = node.getEndExpression();	
			
			ValueWrapper stepWrapper = new ValueWrapper(step.getConstant());
			ValueWrapper endWrapper = new ValueWrapper(end.getConstant());
			
			int size = node.numberOfChildren();
			while (multistack.peek(varName).numCompare(endWrapper.getValue()) <= 0){
				for (int i = 0; i < size; ++i){
					node.getChild(i).accept(this);
				}
				
				multistack.peek(varName).increment(stepWrapper.getValue());
			}
			multistack.pop(varName);
		}

		@Override
		public void visitTextNode(TextNode node) {
			String text = node.getText();
			
			try {
				requestContext.write(text);
			} catch (IOException e) {
				String message = "Error during writing text to the output stream.";
				throw new SmartScriptEngineException(message, e);
			}
		}

	};

	/**
	 * Creates a new {@link SmartScriptEngine} for the given document node and
	 * request context.
	 * 
	 * @param documentNode
	 *            the document node of the parsed smart script.
	 * @param requestContext
	 *            the {@link RequestContext} used by the engine.
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
		
		multistack = new ObjectMultistack();
	}

	/**
	 * Starts the smart script engine and executes the given smart script file.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
