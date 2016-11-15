package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * Web application used to vote for a band. The application will read all the
 * entries and add one vote to the band with the id defined in the parameter
 * with name id.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet(name="glasanje-glasaj", urlPatterns={"/glasanje-glasaj"})
public class AddVoteServlet extends HttpServlet {
	/**
	 * The serial version ID number.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long votedId = Long.parseLong(req.getParameter("id").toString());
		
		long pollId = DAOProvider.getDao().getPollId(votedId);
		
		DAOProvider.getDao().addVote(votedId);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati?pollID=" + pollId); 
	}

	
}
