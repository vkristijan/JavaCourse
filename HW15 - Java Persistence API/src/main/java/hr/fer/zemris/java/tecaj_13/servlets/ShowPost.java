package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.jpa.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * A web page used to display a single {@link BlogEntry}.
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class ShowPost extends HttpServlet {
	/**
	 * The serial version ID number, used for object serialization.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long entryId = (long) req.getAttribute("entryId");
		String author = (String) req.getAttribute("author");
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryId);
		if (!entry.getAuthor().getNick().equals(author)){
			req.setAttribute("error", "There is no blog entry for the given author with the pecified ID.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
		
		req.setAttribute("title", entry.getTitle());
		req.setAttribute("text", entry.getText());
		req.setAttribute("date", entry.getLastModifiedAt());
		req.setAttribute("comments", entry.getComments());
		req.setAttribute("email", req.getSession().getAttribute("current.user.email"));
		
		req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
	}
}
