package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This program demonstrates the {@link BarChartComponent}. It accepts a single
 * argument from the command line, the path to a file containing the description
 * of a {@link BarChart}. The given file will be parsed and a {@link BarChart}
 * will be created and displayed.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class BarChartDemo extends JFrame {
	/**
	 * The standard serial version ID number.
	 */
	private static final long serialVersionUID = 4254103099433843086L;

	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("One argument was expected, the path to the file with data, but " + args.length
					+ " argumetn was given.");
		}

		try (BufferedReader br = Files.newBufferedReader(Paths.get(args[0]))) {
			String xAxisName = br.readLine().trim();
			String yAxisName = br.readLine().trim();

			String line = br.readLine().trim();
			List<XYValue> values = stringToValueList(line);

			int minY = Integer.parseInt(br.readLine().trim());
			int maxY = Integer.parseInt(br.readLine().trim());
			int yDistance = Integer.parseInt(br.readLine().trim());

			BarChart model = new BarChart(values, xAxisName, yAxisName, minY, maxY, yDistance);
			SwingUtilities.invokeLater(() -> new BarChartDemo(model, args[0]));

		} catch (IOException e) {
			System.err.println("Unable to read from the given file!");
			System.exit(1);
		} catch (NumberFormatException ex) {
			System.err.println("The given number could not be parsed into an integer!");
			System.exit(1);
		}
	}

	/**
	 * Creates a list of {@link XYValue} from a string line. The values in the
	 * line must be separated with a space, and the integers n a value have to
	 * be separated with a ',' char.
	 * 
	 * @param line
	 *            the string line containing the values.
	 * @return the new created list.
	 */
	private static List<XYValue> stringToValueList(String line) {
		List<XYValue> values = new ArrayList<>();

		String[] lineData = line.split("\\s+");
		for (String data : lineData) {
			values.add(XYValue.fromString(data));
		}

		return values;
	}

	/**
	 * Creates a new {@link BarChartDemo} with the {@link BarChart} model given
	 * in the argument, from the given file.
	 * 
	 * @param model
	 *            the {@link BarChart} model used to create the chart.
	 * 
	 * @param fileName
	 *            the file name containing the data.
	 */
	public BarChartDemo(BarChart model, String fileName) {
		setTitle("BarChart");
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 300);
		setLocationRelativeTo(null);

		initGUI(model, fileName);
	}

	/**
	 * Initializes the GUI and creates a new {@link BarChartComponent} that is
	 * displayed over the screen.
	 * 
	 * @param model
	 *            the {@link BarChart} model used to create the chart.
	 * @param fileName
	 *            the file name containing the data.
	 */
	private void initGUI(BarChart model, String fileName) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		JLabel label = new JLabel(fileName);
		label.setHorizontalAlignment(SwingConstants.CENTER);

		cp.add(label, BorderLayout.BEFORE_FIRST_LINE);
		cp.add(new BarChartComponent(model), BorderLayout.CENTER);
	}
}
