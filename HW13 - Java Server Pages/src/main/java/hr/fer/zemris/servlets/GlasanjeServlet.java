package hr.fer.zemris.servlets;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.voting.VotingEntry;

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
public class GlasanjeServlet extends HttpServlet {
	/**
	 * The serial version ID number.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt"); 
		
		Path path = Paths.get(fileName);
		Map<Integer, VotingEntry> votingEntries = Utility.readEntries(path, req, resp);
		
		req.setAttribute("votingEntries", votingEntries);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp); 
	}
}
