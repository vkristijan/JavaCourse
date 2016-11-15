package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Simple demonstration program for {@link PrimListModel}. The program has two
 * lists that use the same model to display prime numbers. On the bottom of the
 * screen is a button that generates a new prime number that is added to the
 * model and displayed in the lists.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class PrimDemo extends JFrame {
	/**
	 * The default serial version ID number.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The model used for the lists.
	 */
	private PrimListModel model;

	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(PrimDemo::new);
	}

	/**
	 * Creates a new {@link PrimDemo} and initializes the GUI components.
	 */
	public PrimDemo() {
		setTitle("Prime number list");
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 300);
		setLocationRelativeTo(null);

		model = new PrimListModel();
		initGUI();
	}

	/**
	 * Initializes the GUI components. Creates two lists and set's their model.
	 * Creates a button to generate the next prime number.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		JButton button = new JButton("Sljedeci");
		button.addActionListener((x) -> {
			model.next();
		});

		JPanel panel = new JPanel(new GridLayout(1, 2));
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);

		JScrollPane scrol1 = new JScrollPane(list1);
		JScrollPane scrol2 = new JScrollPane(list2);

		panel.add(scrol1);
		panel.add(scrol2);

		cp.add(panel, BorderLayout.CENTER);
		cp.add(button, BorderLayout.AFTER_LAST_LINE);
	}
}
