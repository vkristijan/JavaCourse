package hr.fer.zemris.java.custom.scripting.exec.multistack;

import java.util.function.BiFunction;

/**
 * Stores a single value. The stored value can be either an {@link Integer} or
 * an {@link Double}. The class also has methods to modify the stored value by
 * adding, subtracting, multiplying or dividing the stored number with another
 * number.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class ValueWrapper {
	/** The value stored in this {@code ValueWrapper} */
	Object value;

	/**
	 * Creates a new {@code ValueWrapper} with the value given in the argument.
	 * 
	 * @param value
	 *            the value to be stored in this {@code ValueWrapper}.
	 */
	public ValueWrapper(Object value) {
		super();
		setValue(value);
	}

	/**
	 * Returns the value stored in this {@code ValueWrapper}.
	 * 
	 * @return the value stored in this {@code ValueWrapper}.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value of this {@code ValueWrapper} to the one given in the
	 * argument.
	 * 
	 * @param value
	 *            the new value for the {@code ValueWrapper}.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Calculates the result of the operation given as the function argument.
	 * This method will use the intFunction if both of the arguments are
	 * {@code Integer} objects, the doubleFunction will be used otherwise.
	 * 
	 * @param arg
	 *            the other number that is used in this operation.
	 * @param intFunction
	 *            the function to calculate the result of two integers.
	 * @param doubleFunction
	 *            the function to calculate the result of two doubles.
	 * @throws ValueWrapperException
	 *             if the given number argument is invalid
	 */
	private void operation(
		Object arg, BiFunction<Integer, Integer, Integer> intFunction,
		BiFunction<Double, Double, Double> doubleFunction) {

		arg = checkArgument(arg);
		Object value = checkArgument(this.value);

		if (value instanceof Integer && arg instanceof Integer) {
			this.value = intFunction.apply((Integer) value, (Integer) arg);
		} else {
			if (value instanceof Integer) {
				value = Double.valueOf((Integer) value);
			}
			if (arg instanceof Integer) {
				arg = Double.valueOf((Integer) arg);
			}

			this.value = doubleFunction.apply((Double) value, (Double) arg);
		}

	}

	/**
	 * Checks if the given argument is a valid operand for the
	 * {@code ValueWrapper}. Null values are converted to integer values 0.
	 * String values are parsed to {@code Integer} or {@code Double} objects.
	 * 
	 * @param arg
	 *            the argument to be checked and converted.
	 * @return the argument after it is converted to a value acceptable for
	 *         operations
	 * @throws ValueWrapperException
	 *             if the value in the argument is not acceptable for operations
	 */
	private Object checkArgument(Object arg) {
		if (arg instanceof ValueWrapper){
			arg = ((ValueWrapper)arg).value;
		}
		
		if (arg == null) {
			arg = Integer.valueOf(0);

		} else if (arg instanceof String) {
			arg = convertString((String) arg);

		} else if (!(arg instanceof Integer || arg instanceof Double)) {
			throw new ValueWrapperException(
				"The given type is not supported in operations. Only Integer and Double values are supported.");
		}

		return arg;
	}

	/**
	 * Parses the string given in the argument to extract the number written in
	 * it. If the string doesn't contain a number, an
	 * {@link ValueWrapperException} will be thrown.
	 * 
	 * @param arg
	 *            the {@code String} containing the number.
	 * @return an {@code Object} that is the instance of Integer or Double,
	 *         depending on the number stored in the string.
	 */
	private Object convertString(String arg) {
		Object value;
		try {
			value = Integer.parseInt((String) arg);
		} catch (NumberFormatException e) {
			try {
				value = Double.parseDouble((String) arg);
			} catch (NumberFormatException e1) {
				throw new ValueWrapperException(
					"The given String can not be parsed into an integer or double.", e1);
			}
		}
		return value;
	}

	/**
	 * Calculates the sum of the current value and the value given in the
	 * argument. <code>null</code> values are interpreted as zero.
	 * 
	 * @param incValue
	 *            the second value in the operation.
	 */
	public void increment(Object incValue) {
		operation(incValue, (x, y) -> x + y, (x, y) -> x + y);
	}

	/**
	 * Calculates the difference of the current value and the value given in the
	 * argument. <code>null</code> values are interpreted as zero.
	 * 
	 * @param decValue
	 *            the second value in the operation.
	 */
	public void decrement(Object decValue) {
		operation(decValue, (x, y) -> x - y, (x, y) -> x - y);
	}

	/**
	 * Calculates the multiplication of the current value and the value given in
	 * the argument. <code>null</code> values are interpreted as zero.
	 * 
	 * @param mulValue
	 *            the second value in the operation.
	 */
	public void multiply(Object mulValue) {
		operation(mulValue, (x, y) -> x * y, (x, y) -> x * y);
	}

	/**
	 * Calculates the division of the current value and the value given in the
	 * argument. <code>null</code> values are interpreted as zero. Dividing by
	 * zero will result with a {@code Double} value of
	 * {@code Double.POSITIVE_INFINITY} or {@code Double.NEGATIVE_INFINITY}
	 * 
	 * @param divValue
	 *            the second value in the operation.
	 */
	public void divide(Object divValue) {
		divValue = checkArgument(divValue);
		if (divValue.equals(Integer.valueOf(0))) {
			divValue = Double.valueOf(0);
		}
		operation(divValue, (x, y) -> x / y, (x, y) -> x / y);
	}

	/**
	 * Compares the value stored in this {@code ValueWrapper} with the value
	 * given in the argument.
	 * 
	 * @param withValue
	 *            the other value to be compared
	 * @return the value 0 if the value in this {@code ValueWrapper} is equal to
	 *         the argument value; a value less than 0 if the value in this
	 *         {@code ValueWrapper} is numerically less than the argument value;
	 *         and a value greater than 0 if the value in this
	 *         {@code ValueWrapper} is numerically greater than the argument
	 *         value (signed comparison).
	 */
	public int numCompare(Object withValue) {
		withValue = checkArgument(withValue);
		Object value = checkArgument(this.value);

		if (value instanceof Integer && withValue instanceof Integer) {
			return ((Integer) value).compareTo((Integer) withValue);
		} else {
			if (value instanceof Integer) {
				value = Double.valueOf((Integer) value);
			}
			if (withValue instanceof Integer) {
				withValue = Double.valueOf((Integer) withValue);
			}

			return ((Double) value).compareTo((Double) withValue);
		}
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
