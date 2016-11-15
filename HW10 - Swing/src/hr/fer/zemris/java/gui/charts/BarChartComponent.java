package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * A {@link JComponent} that displays a {@link BarChart}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class BarChartComponent extends JComponent {
	/**
	 * The default serial version ID number.
	 */
	private static final long serialVersionUID = 5118306037884609866L;
	/**
	 * The {@link BarChart} that should be displayed.
	 */
	private BarChart chart;
	/**
	 * The minimal X value from where it is allowed to continue drawing.
	 */
	private int minX;
	/**
	 * The maximal Y value that is left to draw. The rest of the component is
	 * already drawn.
	 */
	private int maxY;
	/**
	 * The color used to draw the cart.
	 */
	private Color color;

	/**
	 * Creates a new {@link BarChartComponent} that displays the
	 * {@link BarChart} given in the argument.
	 * 
	 * @param chart
	 *            the {@link BarChart} to be displayed.
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
		color = Color.ORANGE;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawAxisNames(g);
		drawAxisNumbers(g);
		drawData(g);
	}

	/**
	 * Draws the data of the {@link BarChart}. The data will be drawn from the
	 * current {@link #minX}, {@link #maxY} position to the rest of the
	 * component.
	 * 
	 * @param g
	 *            the {@link Graphics} used for the drawing.
	 */
	private void drawData(Graphics g) {
		List<XYValue> data = chart.getValues();

		int n = data.size();

		int height = maxY - BarChart.TOP_PADDING;
		int width = getWidth() - minX - BarChart.RIGHT_PADDING;
		int minValue = chart.getMinY();
		int valueRange = chart.getMaxY() - minValue;

		Color oldColor = g.getColor();
		g.setColor(color);
		for (int i = 0; i < n; ++i) {
			int x1 = minX + (int) ((double) width / n * i) + 1;
			int absoluteValue = data.get(i).getY() - minValue;
			int y1 = maxY - (int) Math.ceil((double) height * absoluteValue / valueRange) + 1;

			int x2 = minX + (int) ((double) width / n * (i + 1));
			int y2 = maxY;

			g.fillRect(x1, y1, (x2 - x1), (y2 - y1));
		}
		g.setColor(oldColor);
	}

	/**
	 * Draws the numbers written by the chart axis. The method also draws the
	 * lines of the chart area. The data will be drawn from the current
	 * {@link #minX}, {@link #maxY} position to the rest of the component.
	 * 
	 * @param g
	 *            the {@link Graphics} used for the drawing.
	 */
	private void drawAxisNumbers(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int fontSize = fm.getAscent();
		int maxNumberLength = String.valueOf(chart.getMaxY()).length();
		maxNumberLength = Math.max(maxNumberLength, String.valueOf(chart.getMinY()).length());
		int numberWidth = maxNumberLength * fm.stringWidth("0");

		int height = maxY - fontSize - BarChart.COMPONENT_PADDING - BarChart.TOP_PADDING;
		int number = chart.getMaxY();
		int distance = chart.getyDistance();
		int n = (number - chart.getMinY()) / distance;
		int x = minX;

		minX += numberWidth;
		for (int i = 0; i <= n; ++i) {
			int y = (int) ((double) height / n * i) + fontSize / 2;
			String currentNumber = String.format("%d", number);

			x = minX - fm.stringWidth(currentNumber);
			g.drawString(currentNumber, x, y + BarChart.TOP_PADDING);

			y = (int) ((double) height / n * i) + BarChart.TOP_PADDING;
			g.drawLine(minX, y, getWidth() - BarChart.RIGHT_PADDING, y);

			number -= distance;
		}

		int y = maxY;
		int width = getWidth() - minX - BarChart.RIGHT_PADDING;
		n = chart.getValues().size();
		maxY = y - fontSize;

		List<XYValue> data = chart.getValues();
		for (int i = 0; i < n; ++i) {
			x = (int) ((double) width / n * ((double) i + 0.5));
			g.drawString(String.format("%d", data.get(i).getX()), minX + x, y);

			x = (int) ((double) (width - BarChart.COMPONENT_PADDING) / n * i) + BarChart.COMPONENT_PADDING;
			g.drawLine(minX + x, BarChart.TOP_PADDING, minX + x, maxY);
		}
		x = getWidth() - BarChart.RIGHT_PADDING;
		g.drawLine(x, BarChart.TOP_PADDING, x, maxY);

		minX += BarChart.COMPONENT_PADDING;
		maxY -= BarChart.COMPONENT_PADDING;
	}

	/**
	 * Draws the names of the axis. The data will be drawn from the current
	 * {@link #minX}, {@link #maxY} position to the rest of the component.
	 * 
	 * @param g
	 *            the {@link Graphics} used for the drawing.
	 */
	private void drawAxisNames(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		Graphics2D g2d = (Graphics2D) g;
		int y = this.getHeight();
		y -= BarChart.BOTTOM_PADDING;
		maxY = y - BarChart.COMPONENT_PADDING - fm.getAscent();
		minX = BarChart.LEFT_PADDING + fm.getAscent() + BarChart.COMPONENT_PADDING;
		
		int x = this.getWidth() - minX;
		x -= BarChart.LEFT_PADDING;
		x -= BarChart.RIGHT_PADDING;
		x -= fm.stringWidth(chart.getxDescription());
		x = (x / 2) + BarChart.LEFT_PADDING;

		g2d.drawString(chart.getxDescription(), minX + x, y);

		AffineTransform defaultAt = g2d.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);

		y = minX - BarChart.COMPONENT_PADDING;

		x = this.getHeight();
		x -= BarChart.TOP_PADDING;
		x -= fm.stringWidth(chart.getyDescription());
		x -= (getHeight() - maxY);
		x = (x / 2) + BarChart.BOTTOM_PADDING;
		x = -x - fm.stringWidth(chart.getyDescription());

		g2d.drawString(chart.getyDescription(), x, y);
		g2d.setTransform(defaultAt);
	}

}
