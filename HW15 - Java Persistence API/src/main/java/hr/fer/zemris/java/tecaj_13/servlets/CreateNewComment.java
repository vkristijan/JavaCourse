package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.jpa.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * Web application used to add a new comment to the {@link BlogEntry}. Enables
 * everyone to comment (registered or not).
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet("/servleti/newComment")
public class CreateNewComment extends HttpServlet {
	/**
	 * The serial version ID number, used for object serialization.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long entryId = Long.parseLong(req.getParameter("blogEntry"));
		String email = req.getParameter("email");
		String message = req.getParameter("message");
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryId);
		
		BlogComment comment = new BlogComment();
		comment.setBlogEntry(entry);
		comment.setUsersEMail(email);
		comment.setMessage(message);
		comment.setPostedOn(new Date());
		
		DAOProvider.getDAO().addComment(comment);
		
		resp.sendRedirect("/blog/servleti/author/" + entry.getAuthor().getNick() + "/" + entryId);
	}
}	
