package hr.fer.zemris.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.voting.VotingEntry;

/**
 * Web application that reads all the voting results and produces a pie chart
 * with the data from the votes.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet(name="glasanje-grafika", urlPatterns={"/glasanje-grafika"})
public class GlasanjeGrafika extends HttpServlet{
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
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt"); 
		Path path = Paths.get(fileName);
		Map<Integer, VotingEntry> entries = Utility.readEntries(path, req, resp);

		PieDataset dataset = createDataset(entries);
        JFreeChart chart = Utility.createChart(dataset, "Favourite band");
        
        BufferedImage image = chart.createBufferedImage(IMG_WIDTH, IMG_HEIGHT);
        byte[] pngImage = ChartUtilities.encodeAsPNG(image);
        
        resp.setContentType("image/png");
        resp.getOutputStream().write(pngImage);
	}
	
	/**
	 * Creates the {@link PieDataset} that should be used to create a pie chart.
	 * The data set contains the names of bands, with following number of votes.
	 * 
	 * @param entries map of all entries from which the data set should be generated.
	 * @return the new created data set.
	 */
	private PieDataset createDataset(Map<Integer, VotingEntry> entries){
		DefaultPieDataset dataset = new DefaultPieDataset();

		entries.forEach((x, y) -> {
			dataset.setValue(y.getName(), y.getNumberOfVotes());
		});
		
		return dataset;
	}
	
}
