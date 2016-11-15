package hr.fer.zemris.java.gui.layouts;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * A simple demonstration program that is used to test the {@link CalcLayout}.
 * The program adds a few buttons to the layout and shows them on the screen.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class Demo extends JFrame {
	/**
	 * The default serial version id number.
	 */
	private static final long serialVersionUID = 7970297056571608310L;

	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(Demo::new);
	}

	/**
	 * Creates a new Demo program and initializes the GUI. The location of the
	 * window is set to (20,20).
	 */
	public Demo() {
		super();
		setLocation(20, 20);
		setSize(500, 500);
		setTitle("Prozor1");
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	/**
	 * Creates the GUI components and shows them in the window.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(4));

		cp.add(new JButton("x"), new RCPosition(1, 1));
		cp.add(new JButton("y"), new RCPosition(2, 3));
		cp.add(new JButton("z"), new RCPosition(2, 7));
		cp.add(new JButton("w"), new RCPosition(4, 2));
		cp.add(new JButton("a"), "4,5");
		cp.add(new JButton("b"), "3,7");
	}
}
