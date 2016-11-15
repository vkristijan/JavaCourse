package hr.fer.zemris.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import hr.fer.zemris.voting.VotingEntry;
import hr.fer.zemris.voting.VotingResult;

/**
 * Class with utility methods used in servlets.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class Utility {
	/**
	 * Empty constructor, just to make sure that it is not possible to create an
	 * instance of the utility class.
	 */
	private Utility() {
	}
	
	/**
	 * Forwards the request to an error page, where the error message will be
	 * displayed.
	 * 
	 * @param message
	 *            the error message that should be displayed.
	 * @param req
	 *            an {@link HttpServletRequest} object that contains the request
	 *            the client has made of the servlet.
	 * @param resp
	 *            an HttpServletResponse object that contains the response the
	 *            servlet sends to the client.
	 * @throws ServletException
	 *             if the request for the GET could not be handled.
	 * @throws IOException
	 *             if an input or output error is detected when the servlet
	 *             handles the GET request.
	 */
	public static void sendError(String message, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setAttribute("error", message);
		req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
	}
	
	/**
	 * Reads the results of voting from the specified file path. If the file
	 * doesn't exist, it will be created and all number of votes will be set to
	 * zero.
	 * 
	 * @param path
	 *            the path of the document containing the data that should be
	 *            used to read the values.
	 * @param req
	 *            an {@link HttpServletRequest} object that contains the request
	 *            the client has made of the servlet.
	 * @param resp
	 *            an HttpServletResponse object that contains the response the
	 *            servlet sends to the client.
	 * @return the new created map with the data from the document.
	 * @throws ServletException
	 *             if the request for the GET could not be handled.
	 * @throws IOException
	 *             if an input or output error is detected when the servlet
	 *             handles the GET request.
	 */
	public static Map<Integer, VotingResult> readResults(Path path, HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		if (!Files.exists(path) || !Files.isReadable(path)){
			return createResults(req, resp);
		}
		
		Map<Integer, VotingResult> results = new TreeMap<>();
		
		List<String> lines = Files.readAllLines(path);
		for (String line : lines){
			String[] lineData = line.split("\t");
			
			if (lineData.length != 2){
				Utility.sendError("Invalid line in the voting data file. Expected 2 tab "
							+ "separated values, but got " + lineData.length, req, resp);
			}
			
			int id = 0;
			int numberOfVotes = 0;
			try {
				id = Integer.parseInt(lineData[0]);
				numberOfVotes = Integer.parseInt(lineData[1]);
			} catch (NumberFormatException e) {
				Utility.sendError("Invalid line in the voting data file. The values should"
						+ " be integer numbers.", req, resp);
			}
			
			results.put(id, new VotingResult(id, numberOfVotes));
		}
		
		return results;
	}
	
	/**
	 * Creates new voting results with number of votes set to zero.
	 * 
	 * @param req
	 *            an {@link HttpServletRequest} object that contains the request
	 *            the client has made of the servlet.
	 * @param resp
	 *            an HttpServletResponse object that contains the response the
	 *            servlet sends to the client.
	 * @return the new created map with the data from the document.
	 * @throws ServletException
	 *             if the request for the GET could not be handled.
	 * @throws IOException
	 *             if an input or output error is detected when the servlet
	 *             handles the GET request.
	 */
	private static Map<Integer, VotingResult> createResults(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		Map<Integer, VotingResult> results = new TreeMap<>();
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt"); 
		
		Path path = Paths.get(fileName);
		if (!Files.exists(path) || !Files.isReadable(path)){
			Utility.sendError("The file glasanje-definicija.txt does not exist!", req, resp);
		}
		
		List<String> lines = Files.readAllLines(path);
		
		for (String line : lines){
			String[] lineData = line.split("\t");
			
			if (lineData.length != 3){
				Utility.sendError("Invalid line in the voting definition file. Expected 3 tab "
							+ "separated values, but got " + lineData.length, req, resp);
			}
			
			int id = 0;
			try {
				id = Integer.parseInt(lineData[0]);
			} catch (NumberFormatException e) {
				Utility.sendError("Invalid line in the voting definition file. The first value should"
						+ " be an integer number.", req, resp);
			}
			
			int numberOfVotes = 0;
			results.put(id, new VotingResult(id, numberOfVotes));
		}
		
		return results;
	}

	/**
	 * Reads the voting entries from the specified path. If the path doesn't
	 * exist, an error message will be displayed.
	 * 
	 * @param path
	 *            the path of the document containing the data that should be
	 *            used to read the values.
	 * @param req
	 *            an {@link HttpServletRequest} object that contains the request
	 *            the client has made of the servlet.
	 * @param resp
	 *            an HttpServletResponse object that contains the response the
	 *            servlet sends to the client.
	 * @return the new created map with the data from the document.
	 * @throws ServletException
	 *             if the request for the GET could not be handled.
	 * @throws IOException
	 *             if an input or output error is detected when the servlet
	 *             handles the GET request.
	 */
	public static Map<Integer, VotingEntry> readEntries(Path path, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (!Files.exists(path) || !Files.isReadable(path)){
			Utility.sendError("The file glasanje-definicija.txt does not exist!", req, resp);
		}
		
		List<String> lines = Files.readAllLines(path);
		Map<Integer, VotingEntry> votingEntries = new TreeMap<>();
		
		for (String line : lines){
			String[] lineData = line.split("\t");
			
			if (lineData.length != 3){
				Utility.sendError("Invalid line in the voting definition file. Expected 3 tab "
							+ "separated values, but got " + lineData.length, req, resp);
			}
			
			int id = 0;
			try {
				id = Integer.parseInt(lineData[0]);
			} catch (NumberFormatException e) {
				Utility.sendError("Invalid line in the voting definition file. The first value should"
						+ " be an integer number.", req, resp);
			}
			
			votingEntries.put(id, new VotingEntry(id, lineData[1], lineData[2]));
		}
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt"); 
		path = Paths.get(fileName);
		
		Map<Integer, VotingResult> results = Utility.readResults(path, req, resp);
		
		votingEntries.forEach((x, y) -> {
			VotingResult result = results.get(x);
			int numberOfVotes = result == null ? 0 : result.getNumberOfVotes();
			
			y.setNumberOfVotes(numberOfVotes);
		});
		
		return votingEntries;
	}
	
	/**
	 * Creates a pie chart for the given data set.
	 * 
	 * @param dataset
	 *            the data that is used to create the chart. Describes the values
	 *            and names represented by the chart.
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
