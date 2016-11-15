package hr.fer.zemris.java.tecaj_13.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;


/**
 * Web application that reads all the voting results and produces a pie chart
 * with the data from the votes.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet(name="glasanje-grafika", urlPatterns={"/glasanje-grafika"})
public class VotingGraphicServlet extends HttpServlet{
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
		long pollId = Long.parseLong(req.getParameter("pollId").toString());
		List<PollOption> entries = DAOProvider.getDao().getPollOptions(pollId);
		
		PieDataset dataset = createDataset(entries);
		Poll poll = DAOProvider.getDao().getPoll(pollId);
        JFreeChart chart = createChart(dataset, poll.getTitle());
        
        BufferedImage image = chart.createBufferedImage(IMG_WIDTH, IMG_HEIGHT);
        byte[] pngImage = ChartUtilities.encodeAsPNG(image);
        
        resp.setContentType("image/png");
        resp.getOutputStream().write(pngImage);
	}
	
	/**
	 * Creates the {@link PieDataset} that should be used to create a pie chart.
	 * The data set contains the names of bands, with following number of votes.
	 * 
	 * @param entries
	 *            map of all entries from which the data set should be
	 *            generated.
	 * @return the new created data set.
	 */
	private PieDataset createDataset(List<PollOption> entries){
		DefaultPieDataset dataset = new DefaultPieDataset();

		entries.forEach((x) -> {
			dataset.setValue(x.getOptionTitle(), x.getVotesCount());
		});
		
		return dataset;
	}
	
	/**
	 * Creates a pie chart for the given data set.
	 * 
	 * @param dataset
	 *            the data that is used to create the chart. Describes the
	 *            values and names represented by the chart.
	 * @param title
	 *            the title of the chart.
	 * @return the created pie chart.
	 */
	public static JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setIgnoreZeroValues(true);
        plot.setIgnoreNullValues(true);
        
        return chart;
    }
}
