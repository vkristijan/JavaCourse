package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * Web application that reads all the current voting entries and generates a web
 * page showing a list of all bands. By clicking on a band in the list, the user
 * will vote for it.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet(name="glasanje", urlPatterns={"/glasanje"})
public class VoteServlet extends HttpServlet {
	/**
	 * The serial version ID number.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollId = Integer.parseInt(req.getParameter("pollID").toString());
		List<PollOption> votingEntries = DAOProvider.getDao().getPollOptions(pollId);
		Poll poll = DAOProvider.getDao().getPoll(pollId);
		
		req.setAttribute("votingEntries", votingEntries);
		req.setAttribute("title", poll.getTitle());
		req.setAttribute("message", poll.getMessage());
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp); 
	}
}
