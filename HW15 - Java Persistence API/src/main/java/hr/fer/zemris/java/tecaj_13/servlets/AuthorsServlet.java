package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.jpa.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Web program used to display {@link BlogEntry} of a specified author. Provides
 * functionality for the author to write new entries or edit old ones.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorsServlet extends HttpServlet{
	/**
	 * The serial version ID number, used for object serialization.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String urlInfo = req.getPathInfo();
		urlInfo = urlInfo.substring(1);
		String[] parameters = urlInfo.split("/");
		
		req.setAttribute("firstName", req.getSession().getAttribute("current.user.fn"));
		req.setAttribute("lastName", req.getSession().getAttribute("current.user.ln"));
		req.setAttribute("nick", req.getSession().getAttribute("current.user.nick"));

		String author = parameters[0];
		req.setAttribute("author", author);
		
		if (parameters.length == 1){
			List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(author);
			req.setAttribute("entries", entries);
			
			req.getRequestDispatcher("/WEB-INF/pages/listEntries.jsp").forward(req, resp);
		
		} else if (parameters.length == 2) {
			req.setAttribute("entryId", Long.parseLong(parameters[1]));
			
			ShowPost servlet = new ShowPost();
			servlet.doGet(req, resp);
			return;
		}
		
		req.setAttribute("error", "The url is invalid!");
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String parameter = req.getParameter("entryId");
		BlogEntry entry;
	
		if (parameter == null){
			long authorId = (long) req.getSession().getAttribute("current.user.id");
			BlogUser author = DAOProvider.getDAO().getUser(authorId);
			
			entry = new BlogEntry();
			entry.setCreatedAt(new Date());
			entry.setLastModifiedAt(entry.getCreatedAt());
			entry.setAuthor(author);
			entry.setTitle("");
			entry.setText("");
			
			DAOProvider.getDAO().addEntry(entry);
			req.setAttribute("entryId", entry.getId());
		} else {
			long entryId = Long.parseLong(parameter);
			entry = DAOProvider.getDAO().getBlogEntry(entryId);
		}
		
		req.setAttribute("blogEntry", entry);
		EditPost servlet = new EditPost();
		servlet.doGet(req, resp);
		return;
	}
}
