package hr.fer.zemris.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * Web application that creates a pie chart describing the market shares of
 * desktop operating systems. The result is created as a png image.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet(name="reportImage", urlPatterns={"/reportImage"})
public class ReportImage extends HttpServlet {
	/**
	 * The serial version ID number.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Width of the image in pixels.
	 */
	private static int IMG_WIDTH = 400;
	/**
	 * Height of the image in pixels.
	 */
	private static int IMG_HEIGHT = 300;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PieDataset dataset = createDataset();
        JFreeChart chart = Utility.createChart(dataset, "OS usage");
        
        BufferedImage image = chart.createBufferedImage(IMG_WIDTH, IMG_HEIGHT);
        byte[] pngImage = ChartUtilities.encodeAsPNG(image);
        
        resp.setContentType("image/png");
        resp.getOutputStream().write(pngImage);
	}
	
	/**
	 * Creates a new dataset for a pie chart. The dataset describes the market
	 * share for the three main desktop operating systems.
	 * 
	 * @return a new dateset for a pie chart.
	 */
	private PieDataset createDataset(){
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		dataset.setValue("Windows", 89.69);
		dataset.setValue("Mac", 8.52);
		dataset.setValue("Linux", 1.79);
		
		return dataset;
	}

}
