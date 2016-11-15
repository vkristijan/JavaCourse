package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * Web application that reads the votes and bands, and generates a leaderboard
 * table on a new web page.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet(name="glasanje-rezultati", urlPatterns={"/glasanje-rezultati"})
public class VotingResultsServlet extends HttpServlet{
	/**
	 * The serial version ID number.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollId = Integer.parseInt(req.getParameter("pollID").toString());
		
		List<PollOption> results = DAOProvider.getDao().getPollOptions(pollId);
		
		long bestVotes = results.get(0).getVotesCount();
		List<PollOption> winners = 
				results.stream()
					.filter((x) -> x.getVotesCount() == bestVotes)
					.collect(Collectors.toList());
		
		req.setAttribute("votingResults", results);
		req.setAttribute("pollId", pollId);
		req.setAttribute("winners", winners);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp); 
	}
}
