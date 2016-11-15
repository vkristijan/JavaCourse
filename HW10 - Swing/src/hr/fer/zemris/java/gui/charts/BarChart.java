package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * This class is a representation of a bar chart. The chart has a list of values
 * that should be displayed.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class BarChart {
	/**
	 * A list of all the values in this chart stored as {@link XYValue}.
	 */
	private List<XYValue> values;
	/**
	 * The description on the x axis.
	 */
	private String xDescription;
	/**
	 * The description of the y axis.
	 */
	private String yDescription;
	/**
	 * The minimum value of the Y axis.
	 */
	private int minY;
	/**
	 * The maximum value of the Y axis.
	 */
	private int maxY;
	/**
	 * The distance between two values on the Y axis.
	 */
	private int yDistance;

	/**
	 * The padding from the left site of the {@link BarChart}.
	 */
	public static final int LEFT_PADDING = 10;
	/**
	 * The padding from the right site of the {@link BarChart}.
	 */
	public static final int RIGHT_PADDING = 10;
	/**
	 * The padding from the top of the {@link BarChart}.
	 */
	public static final int TOP_PADDING = 10;
	/**
	 * The padding from the bottom of the {@link BarChart}.
	 */
	public static final int BOTTOM_PADDING = 10;
	/**
	 * The padding between two components of the {@link BarChart}.
	 */
	public static final int COMPONENT_PADDING = 10;

	/**
	 * Creates a new {@link BarChart} with the values given in the arguments.
	 * 
	 * @param values
	 *            A list of all the values in this chart stored as
	 *            {@link XYValue}.
	 * @param xDescription
	 *            the description of the x axis.
	 * @param yDescription
	 *            the description of the y axis.
	 * @param minY
	 *            the minimal value of the y axis.
	 * @param maxY
	 *            the maximal value of the y axis.
	 * @param yDistance
	 *            the distance between two values on the y axis.
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription, int minY, int maxY, int yDistance) {
		maxY += ((maxY - minY) % yDistance);

		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minY = minY;
		this.maxY = maxY;
		this.yDistance = yDistance;
	}

	/**
	 * Returns the list of {@link XYValue}s in this chart.
	 * 
	 * @return the list of {@link XYValue}s in this chart.
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Returns the description of the x axis.
	 * 
	 * @return the description of the x axis.
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Returns the description of the y axis.
	 * 
	 * @return the description of the y axis.
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Returns the minimum value on the y axis.
	 * 
	 * @return the minimum value on the y axis.
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Returns the maximum value on the y axis.
	 * 
	 * @return the maximum value on the y axis.
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Returns the distance between two values on the y axis.
	 * 
	 * @return the distance between two values on the y axis.
	 */
	public int getyDistance() {
		return yDistance;
	}

}
