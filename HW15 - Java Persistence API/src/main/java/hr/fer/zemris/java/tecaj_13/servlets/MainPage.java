package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.jpa.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Main web page of the application. Used to check user credentials and redirect
 * the user to the login page if not logged in already. Displays a list of all
 * the authors on this blog.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet("/servleti/main")
public class MainPage extends HttpServlet{
	/**
	 * The serial version ID number.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("userId", req.getSession().getAttribute("current.user.id"));
		req.setAttribute("firstName", req.getSession().getAttribute("current.user.fn"));
		
		List<BlogUser> authors = DAOProvider.getDAO().getUsers();
		req.setAttribute("authors", authors);

		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}
}
