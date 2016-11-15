package hr.fer.zemris.servlets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.voting.VotingResult;

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
public class GlasanjeGlasajServlet extends HttpServlet {
	/**
	 * The serial version ID number.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int votedId = Integer.parseInt(req.getParameter("id").toString());
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt"); 
		Map<Integer, VotingResult> results;
		
		Path path = Paths.get(fileName);
		results = Utility.readResults(path, req, resp);
		results.forEach((x, y) -> {
			if (y.getId() == votedId){
				y.addVote();
			}
		});
		
		BufferedWriter writer = Files.newBufferedWriter(path);
		for (Entry<Integer, VotingResult> result : results.entrySet()){
			writer.write(result.getValue().toString() + "\n");
		}
		writer.flush();
		writer.close();
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati"); 
	}

	
}
