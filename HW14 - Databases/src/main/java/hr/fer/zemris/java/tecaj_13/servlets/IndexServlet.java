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

/**
 * Web servlet that reads all the available polls and renders them on the
 * index.jsp page.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet(name="index", urlPatterns={"/index.html", "/"})
public class IndexServlet extends HttpServlet {
	/**
	 * The default serial version ID number.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDao().getPolls();
		req.setAttribute("polls", polls);
		
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);

		
	}
}
