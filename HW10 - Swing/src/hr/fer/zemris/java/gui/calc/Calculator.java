package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * This program is a calculator capable of performing simple math operations.
 * The calculator has two working modes, one for normal operations and one for
 * inverted ones. The calculator can calculate trigonometric functions,
 * logarithms, powers and roots. It is possible to store numbers onto a stack
 * and use them later.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Calculator extends JFrame {
	/**
	 * Default serial version ID number.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The {@link CalculatorDisplay} used to display data.
	 */
	private CalculatorDisplay display;
	/**
	 * The last calculated result.
	 */
	private Double result;
	/**
	 * The operator that should be used on two numbers to calculate the result.
	 */
	private BiFunction<Double, Double, Double> operator;
	/**
	 * A list of invertible buttons;
	 */
	private List<CalculatorButton> invertableButtons;

	/**
	 * {@link Stack} used to store results.
	 */
	private Stack<String> stack;

	/**
	 * A flag that is checking if the user should currently enter the first
	 * digit of a number.
	 */
	private boolean firstChar;
	/**
	 * A flag that marks the current state of the operations.
	 */
	private boolean isInverted;
	/**
	 * A flag that is used when an error occurs.
	 */
	private boolean error;

	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(Calculator::new);
	}

	/**
	 * Creates a new {@link Calculator} and initializes all the GUI elements.
	 */
	public Calculator() {
		setTitle("Calculator");
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 300);
		setLocationRelativeTo(null);

		firstChar = true;
		invertableButtons = new LinkedList<>();
		stack = new Stack<>();
		initGUI();
		setMinimumSize(getMinimumSize());
	}

	/**
	 * Initializes all the gui elements and creates all the buttons for the
	 * calculator.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(6));

		display = new CalculatorDisplay();
		cp.add(display, "1,1");

		cp.add(createNumberButtons("1"), "4,3");
		cp.add(createNumberButtons("2"), "4,4");
		cp.add(createNumberButtons("3"), "4,5");
		cp.add(createNumberButtons("4"), "3,3");
		cp.add(createNumberButtons("5"), "3,4");
		cp.add(createNumberButtons("6"), "3,5");
		cp.add(createNumberButtons("7"), "2,3");
		cp.add(createNumberButtons("8"), "2,4");
		cp.add(createNumberButtons("9"), "2,5");
		cp.add(createNumberButtons("0"), "5,3");
		cp.add(createNumberButtons("."), "5,5");
		cp.add(changeSign("+/-"), "5,4");

		cp.add(createSimpleOperator("+", Double::sum), "5,6");
		cp.add(createSimpleOperator("-", (x, y) -> x - y), "4,6");
		cp.add(createSimpleOperator("*", (x, y) -> x * y), "3,6");
		cp.add(createSimpleOperator("/", (x, y) -> x / y), "2,6");
		cp.add(createSimpleOperator("=", null), "1,6");

		cp.add(createInvertibleOperator("sin", "arc sin", Math::sin, Math::asin), "2,2");
		cp.add(createInvertibleOperator("cos", "arc cos", Math::cos, Math::acos), "3,2");
		cp.add(createInvertibleOperator("tan", "arc tan", Math::tan, Math::atan), "4,2");
		cp.add(createInvertibleOperator("ctg", "arc ctg", (x) -> 1.0 / Math.tan(x), (x) -> Math.atan(1.0 / x)), "5,2");

		cp.add(createInvertibleOperator("log", "10^x", Math::log10, (x) -> Math.pow(x, 10)), "3,1");
		cp.add(createInvertibleOperator("ln", "e^x", Math::log, (x) -> Math.pow(x, Math.E)), "4,1");
		cp.add(createInvertibleOperator("1/x", "1/x", (x) -> 1.0 / x, (x) -> 1.0 / x), "2,1");
		cp.add(exponentButton(), "5,1");

		cp.add(invertButton("Inv"), "5,7");
		cp.add(clearButton("clr"), "1,7");
		cp.add(resetButton("res"), "2,7");

		cp.add(pushButton(), "3,7");
		cp.add(popButton(), "4,7");
	}

	/**
	 * Creates a new {@link CalculatorButton} that is used for the stack push
	 * command.
	 * 
	 * @return the new created {@link CalculatorButton}.
	 */
	private Component pushButton() {
		JButton button = new CalculatorButton("push");

		ActionListener action = (x) -> {
			if (error || display.getText().isEmpty()) return;
			stack.push(display.getText());
		};

		button.addActionListener(action);

		return button;
	}

	/**
	 * Creates a new {@link CalculatorButton} that is used for the stack pop
	 * command.
	 * 
	 * @return the new created {@link CalculatorButton}.
	 */
	private Component popButton() {
		JButton button = new CalculatorButton("pop");

		ActionListener action = (x) -> {
			if (error) return;
			if (stack.isEmpty()) {
				display.setText("Stack is empty!");
				error = true;
				return;
			}

			firstChar = false;
			display.setText(stack.pop());
		};

		button.addActionListener(action);
		return button;
	}

	/**
	 * Creates a new {@link CalculatorButton} that is used for the power
	 * operation. The command is inversive, meaning that in case the
	 * {@code #isInverted} flag is true, the command will perform a root
	 * operation. The created button is added to the {@link #invertableButtons}
	 * list.
	 * 
	 * @return the new created {@link CalculatorButton}.
	 */
	private Component exponentButton() {
		CalculatorButton button = new CalculatorButton("x^n");
		button.setInvertedText("n√x");

		BiFunction<Double, Double, Double> operation;

		if (isInverted) {
			operation = (x, y) -> Math.pow(x, 1.0 / y);
		} else {
			operation = Math::pow;
		}

		ActionListener action = (x) -> {
			if (error) return;
			if (firstChar) {
				if (result != null) {
					operator = operation;
				}
				return;
			}

			if (operator != null) {
				double number = Double.parseDouble(display.getText());
				result = operator.apply(result, number);
				display.setText(result);
			} else {
				result = Double.parseDouble(display.getText());
			}

			firstChar = true;
			operator = operation;
		};

		button.addActionListener(action);
		invertableButtons.add(button);
		return button;
	}

	/**
	 * Creates a new {@link CalculatorButton} that is used to clear the
	 * calculator display.
	 * 
	 * @param text
	 *            the text that should be displayed on this button.
	 * 
	 * @return the new created {@link CalculatorButton}.
	 */
	private Component clearButton(String text) {
		JButton button = new CalculatorButton(text);

		ActionListener action = (x) -> {
			if (error) return;
			display.setText("");
			firstChar = true;
		};

		button.addActionListener(action);
		return button;
	}

	/**
	 * Creates a new {@link CalculatorButton} that is used to reset the whole
	 * calculator. This command will clear all the memory of the calculator and
	 * set every flag to their initial state.
	 * 
	 * @param text
	 *            the text that should be displayed on this button.
	 * 
	 * @return the new created {@link CalculatorButton}.
	 */
	private Component resetButton(String text) {
		JButton button = new CalculatorButton(text);

		ActionListener action = (x) -> {
			display.setText("");
			stack.clear();
			firstChar = true;
			error = false;
			operator = null;
			result = null;
		};

		button.addActionListener(action);
		return button;
	}

	/**
	 * Creates a new {@link CalculatorButton} that is used to switch the working
	 * mode from inverted to normal and vice versa.
	 * 
	 * @param text
	 *            the text that should be displayed on this button.
	 * 
	 * @return the new created {@link CalculatorButton}.
	 */
	private Component invertButton(String text) {
		JCheckBox button = new JCheckBox(text);
		button.setBackground(Color.LIGHT_GRAY);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setVerticalAlignment(SwingConstants.CENTER);
		button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		button.setBorderPainted(true);

		button.setFont(new Font("Serif", Font.BOLD, 18));

		ActionListener action = (x) -> {
			isInverted = !isInverted;

			invertableButtons.forEach((tmpButton) -> {
				if (isInverted) {
					tmpButton.setText(tmpButton.getInvertedText());
				} else {
					tmpButton.setText(tmpButton.getNormalText());
				}
			});
		};
		button.addActionListener(action);

		return button;
	}

	/**
	 * Creates a new {@link CalculatorButton} that is used to change the sign of
	 * the number currently displayed on the {@link CalculatorDisplay}.
	 * 
	 * @param text
	 *            the text that should be displayed on this button.
	 * 
	 * @return the new created {@link CalculatorButton}.
	 */
	private Component changeSign(String text) {
		JButton button = new CalculatorButton(text);

		ActionListener action = (x) -> {
			if (error) return;
			if (firstChar || display.getText().isEmpty()) {
				return;
			}

			Double number = Double.parseDouble(display.getText());
			number *= -1;
			display.setText(number);
		};

		button.addActionListener(action);
		return button;
	}

	/**
	 * Creates a new {@link CalculatorButton} that is used for commands that are
	 * invertible. Every command will have two possible operations, one normal
	 * and one inverted. The button will also be added to the
	 * {@link #invertableButtons} list, so that it's text will be changed
	 * depending on the current working mode.
	 * 
	 * @param text
	 *            the text that should be displayed on this button.
	 * @param invertedText
	 *            the text that should be displayed on this button in inverted
	 *            mode.
	 * @param operation
	 *            the primary operation that this button should perform in
	 *            normal mode.
	 * @param inverted
	 *            the operation that this button should perform in inverted
	 *            mode.
	 * 
	 * @return the new created {@link CalculatorButton}.
	 */
	private Component createInvertibleOperator(
		String text, String invertedText, Function<Double, Double> operation, Function<Double, Double> inverted) {

		CalculatorButton button = new CalculatorButton(text);
		button.setInvertedText(invertedText);

		ActionListener action = (x) -> {
			if (error) return;
			if (display.getText().isEmpty()) {
				return;
			}

			result = Double.parseDouble(display.getText());
			if (isInverted) {
				result = inverted.apply(result);
			} else {
				result = operation.apply(result);
			}

			display.setText(result);
			firstChar = true;
			operator = null;
		};

		button.addActionListener(action);
		invertableButtons.add(button);
		return button;
	}

	/**
	 * Creates a new {@link CalculatorButton} that is used to create a simple
	 * operator.
	 * 
	 * @param text
	 *            the text that should be displayed on this button.
	 * @param operation
	 *            the operation that should be performed by this button.
	 * 
	 * @return the new created {@link CalculatorButton}.
	 */
	private Component createSimpleOperator(String text, BiFunction<Double, Double, Double> operation) {
		JButton button = new CalculatorButton(text);

		ActionListener action = (x) -> {
			if (error) return;
			if (firstChar) {
				if (result != null) {
					operator = operation;
				}
				return;
			}

			if (operator != null) {
				double number = Double.parseDouble(display.getText());
				result = operator.apply(result, number);
				display.setText(result);
			} else {
				result = Double.parseDouble(display.getText());
			}

			firstChar = true;
			operator = operation;
		};

		button.addActionListener(action);
		return button;
	}

	/**
	 * Creates a new {@link CalculatorButton} that is used to input numbers in
	 * the calculator.
	 * 
	 * @param text
	 *            the text that should be displayed on this button.
	 * 
	 * @return the new created {@link CalculatorButton}.
	 */
	private Component createNumberButtons(String text) {
		JButton button = new CalculatorButton(text);

		ActionListener action = (x) -> {
			if (error) return;
			if (firstChar) {
				display.setText("");
				firstChar = false;
			}

			if (text.equals(".")) {
				if (display.getText().contains(text)) {
					return;
				}
			}
			display.addText(text);
		};

		button.addActionListener(action);
		return button;
	}
}
