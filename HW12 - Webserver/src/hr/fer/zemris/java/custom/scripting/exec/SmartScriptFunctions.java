package hr.fer.zemris.java.custom.scripting.exec;

import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A helper class to execute a function. The function reads values from the
 * stack, and stores results on the same stack. The supported methods are:
 * <ul>
 * <li>sin</li>
 * <li>decfmt</li>
 * <li>dup</li>
 * <li>swap</li>
 * <li>setMimeType</li>
 * <li>paramGet</li>
 * <li>pparamGet</li>
 * <li>pparamSet</li>
 * <li>pparamDel</li>
 * <li>tparamGet</li>
 * <li>tparamSet</li>
 * <li>tparamDel</li>
 * </ul>
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class SmartScriptFunctions {
	/**
	 * The temporary stack used by the command that called this function class.
	 */
	private static Stack<Object> tmpStack;
	/**
	 * The request context of the server that called the command.
	 */
	private static RequestContext requestContext;
	
	/**
	 * Executes the function with the name given as the first argument. The
	 * function execution will use the stack given in the argument to read and
	 * store values. The last argument is the {@link RequestContext} used in the
	 * function caller.
	 * 
	 * @param functionName
	 *            the name of the function that should be executed.
	 * @param tmpStack
	 *            the stack used to read the arguments and store the results.
	 * @param requestContext
	 *            the {@link RequestContext} used in the function caller.
	 * @throws SmartScriptEngineException
	 *             if a function with the given function name doesn't exist.
	 */
	public static void apply(String functionName, Stack<Object> tmpStack, RequestContext requestContext) {
		SmartScriptFunctions.tmpStack = tmpStack;
		SmartScriptFunctions.requestContext = requestContext;
		
		switch (functionName) {
			case "sin":
				calculateSin();
				break;
			case "decfmt":
				calculateDecfmt();
				break;
			case "dup":
				calculateDup();
				break;
			case "swap":
				calculateSwap();
				break;
			case "setMimeType":
				calculateSetMimeType();
				break;
			case "paramGet":
				calculateParamGet();
				break;
			case "pparamGet":
				calculatePParamGet();
				break;
			case "pparamSet":
				calculatePParamSet();
				break;
			case "pparamDel":
				calculatePParamDel();
				break;
			case "tparamGet":
				calculateTParamGet();
				break;
			case "tparamSet":
				calculateTParamSet();
				break;
			case "tparamDel":
				calculateTParamDel();
				break;

			default:
				String message = "The operation " + functionName + " is not supported!";
				throw new SmartScriptEngineException(message);		}
	}

	/**
	 * Executes the TParamDel command. The command takes the last value from the
	 * stack and deletes the persistent parameter with the name equal to the
	 * value from the stack.
	 */
	private static void calculateTParamDel() {
		String name = tmpStack.pop().toString();
		
		requestContext.removePersistentParameter(name);		
	}

	/**
	 * Executes the TParamSet command. The command takes two values from the
	 * stack. The temporary parameter name and parameter value, and creates a
	 * new temporary parameter with the given name and value.
	 */
	private static void calculateTParamSet() {
		String name = tmpStack.pop().toString();
		String value = tmpStack.pop().toString();
		
		requestContext.setTemporaryParameter(name, value);		
	}

	/**
	 * Executes the TParamGet command. The command reads two values from the
	 * stack, the temporary parameter name and default value. After that, the
	 * command tries to read the value for the given parameter name, and if
	 * nothing was found the default value is pushed onto the stack. If the
	 * parameter with the specified name is found, it's value is pushed to the
	 * stack.
	 */
	private static void calculateTParamGet() {
		Object defaultValue = tmpStack.pop();
		String name = tmpStack.pop().toString();
		
		Object value = requestContext.getTemporaryParameter(name);
		if (value == null){
			value = defaultValue;
		}
		
		tmpStack.push(value);			
	}

	/**
	 * Executes the PParamSet command. The command takes two values from the
	 * stack. The persistent parameter name and parameter value, and creates a
	 * new temporary parameter with the given name and value.
	 */
	private static void calculatePParamDel() {
		String name = tmpStack.pop().toString();
		
		requestContext.removePersistentParameter(name);
	}

	/**
	 * Executes the PParamSet command. The command takes two values from the
	 * stack. The persistent parameter name and parameter value, and creates a
	 * new temporary parameter with the given name and value.
	 */
	private static void calculatePParamSet() {
		String name = tmpStack.pop().toString();
		String value = tmpStack.pop().toString();
		
		requestContext.setPersistentParameter(name, value);
	}

	/**
	 * Executes the PParamGet command. The command reads two values from the
	 * stack, the persistent parameter name and default value. After that, the
	 * command tries to read the value for the given parameter name, and if
	 * nothing was found the default value is pushed onto the stack. If the
	 * parameter with the specified name is found, it's value is pushed to the
	 * stack.
	 */
	private static void calculatePParamGet() {
		Object defaultValue = tmpStack.pop();
		String name = tmpStack.pop().toString();
		
		Object value = requestContext.getPersistentParameter(name);
		if (value == null){
			value = defaultValue;
		}
		
		tmpStack.push(value);		
	}

	/**
	 * Executes the ParamGet command. The command reads two values from the
	 * stack, the parameter name and default value. After that, the command
	 * tries to read the value for the given parameter name, and if nothing was
	 * found the default value is pushed onto the stack. If the parameter with
	 * the specified name is found, it's value is pushed to the stack.
	 */
	private static void calculateParamGet() {
		Object defaultValue = tmpStack.pop();
		String name = tmpStack.pop().toString();
		
		Object value = requestContext.getParameter(name);
		if (value == null){
			value = defaultValue;
		}
		
		tmpStack.push(value);
	}

	/**
	 * Executes the setMimeType command. The command takes a single argument
	 * from the stack. The argument is used to define the mime type used by the
	 * server.
	 */
	private static void calculateSetMimeType() {
		String mimeType = tmpStack.pop().toString();
		requestContext.setMimeType(mimeType);
	}

	/**
	 * Executes the swap command. The command takes two values from the stack,
	 * and return them to the stack in reversed order.
	 */
	private static void calculateSwap() {
		Object value1 = tmpStack.pop();
		Object value2 = tmpStack.pop();
		
		tmpStack.push(value2);
		tmpStack.push(value1);
	}

	/**
	 * Executes the dup command. The command takes a single value from the stack
	 * and add's it back to the stack, resulting in the same value being on the
	 * stack twice.
	 */
	private static void calculateDup() {
		Object value = tmpStack.peek();
		tmpStack.push(value);
	}

	/**
	 * Executes the decfmt command. The command takes two values from the stack.
	 * The first value is used as a pattern defining how to format the double
	 * value. The second value is a double number that should be formated into a
	 * string using the given formatting pattern. After executing the command, a
	 * string with the formated double number will be pushed onto the stack.
	 */
	private static void calculateDecfmt() {
		String pattern = tmpStack.pop().toString();
		Object value = tmpStack.pop();
		
		DecimalFormat format = new DecimalFormat(pattern);
		String result = format.format(value);
		
		tmpStack.push(result);
	}

	/**
	 * Executes the sin command. The command takes a single value from the
	 * stack, the number that should be used as the argument of sinus function.
	 * The argument is interpreted as angle in degrees, and the result is
	 * calculated as the sin of the angle. The resoult value is pushed onto the
	 * stack. This command works with integer and double values.
	 */
	private static void calculateSin() {
		Object arg = tmpStack.pop();
		double toRadian = 2.0 * Math.PI / 360.0;
		if (arg instanceof Integer){
			arg = Math.sin(toRadian * (Integer)arg);
		} else if (arg instanceof Double){
			arg = Math.sin(toRadian * (Double)arg);
		} else {
			throw new SmartScriptEngineException("Wrong argument type.");
		}
		
		tmpStack.push(arg);
	}

	
}
