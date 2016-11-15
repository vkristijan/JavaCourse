package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This program evaluates an expression written using
 * <a href = "https://en.wikipedia.org/wiki/Reverse_Polish_notation"> reverse
 * Polish notation </a>. The expression is given as the only argument from the
 * command line. The whole expression should be inside quotation marks. <br>
 * This program demonstrates the usage of the stack collection.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class StackDemo {
	/**
	 * A collection of all the supported operations.
	 */
	private static ArrayIndexedCollection supportedOperations;

	/**
	 * Adds elements to the supported operation collection.
	 */
	static {
		supportedOperations = new ArrayIndexedCollection();
		supportedOperations.add("+");
		supportedOperations.add("-");
		supportedOperations.add("*");
		supportedOperations.add("/");
		supportedOperations.add("%");
	}

	/**
	 * Starts the program.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Wrong number of arguments!");
			return;
		}

		String[] arguments = args[0].trim().split(" +");

		ObjectStack stack = new ObjectStack();
		for (String element : arguments) {
			if (supportedOperations.contains(element)) {
				calculate(element, stack);
			} else {
				try {
					stack.push(Double.parseDouble(element));
				} catch (NumberFormatException e) {
					System.err
							.println("The given number or operations is invalid: " + element);
					return;
				}
			}
		}

		if (stack.size() != 1) {
			System.err.println(
				"The number of arguments was invalid! %n The expression could not be evaluated!");
			return;
		}

		System.out.println("The expression: " + args[0]);
		System.out.println("evaluates to " + stack.pop());
	}

	/**
	 * Calculates the operation defined by the operation argument. The operands
	 * are taken from the stack, and the result is pushed on the stack.
	 * 
	 * @param operation
	 *            the operation to be calculated
	 * @param stack
	 *            the stack with the operands
	 * @throws UnsupportedOperationException
	 *             if the given operation is not supported by this program
	 * @throws EmptyStackException
	 *             if the stack has not enough elements in it to do the
	 *             calculation
	 */
	private static void calculate(String operation, ObjectStack stack) {
		double b = (double) stack.pop();
		double a = (double) stack.pop();

		double result = 0;
		try {
			switch (operation) {
				case "+":
					result = a + b;
					break;
				case "-":
					result = a - b;
					break;
				case "*":
					result = a * b;
					break;
				case "/":
					result = a / b;
					break;
				case "%":
					result = a % b;
					break;
				default:
					throw new UnsupportedOperationException(
						"The given operation is not supported: " + operation);
			}
		} catch (ArithmeticException e) {
			System.err.println("Error! %n Divizion by zero!");
			System.exit(0);
		}

		stack.push(result);
	}
}
