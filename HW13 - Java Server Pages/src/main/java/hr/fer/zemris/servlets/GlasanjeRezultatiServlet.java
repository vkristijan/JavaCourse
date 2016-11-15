package hr.fer.zemris.servlets;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.voting.VotingEntry;

/**
 * Web application that reads the votes and bands, and generates a leaderboard
 * table on a new web page.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet(name="glasanje-rezultati", urlPatterns={"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet{
	/**
	 * The serial version ID number.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt"); 
		Path path = Paths.get(fileName);
		
		Map<Integer, VotingEntry> entries = Utility.readEntries(path, req, resp);
		List<VotingEntry> sorted = new ArrayList<>(entries.values());
		
		sorted.sort(new Comparator<VotingEntry>() {
			@Override
			public int compare(VotingEntry o1, VotingEntry o2) {
				return o2.getNumberOfVotes() - o1.getNumberOfVotes();
			}
		});
		
		req.setAttribute("votingResults", sorted);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp); 
	}
}
