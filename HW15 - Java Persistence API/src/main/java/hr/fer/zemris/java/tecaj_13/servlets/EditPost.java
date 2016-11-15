package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.jpa.DAOProvider;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * Web application used to edit an existing {@link BlogEntry}.
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet("/servleti/edit")
public class EditPost extends HttpServlet {
	/**
	 * The serial version ID number, used for object serialization.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogEntry entry = (BlogEntry) req.getAttribute("blogEntry");
		
		req.setAttribute("firstName", req.getSession().getAttribute("current.user.fn"));
		req.setAttribute("lastName", req.getSession().getAttribute("current.user.ln"));
		
		req.setAttribute("title", entry.getTitle());
		req.setAttribute("text", entry.getText());
		req.setAttribute("entryId", entry.getId());
		
		req.setAttribute("author", entry.getAuthor().getNick());
		req.setAttribute("nick", req.getSession().getAttribute("current.user.nick"));
		
		req.getRequestDispatcher("/WEB-INF/pages/edit.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long entryId = Long.parseLong(req.getParameter("entryId"));
		
		String title = req.getParameter("title");
		String text = req.getParameter("text");
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryId);
		
		entry.setTitle(title);
		entry.setText(text);
		entry.setLastModifiedAt(new Date());
		JPAEMProvider.getEntityManager().getTransaction().commit();
		
		resp.sendRedirect("/blog/servleti/author/" + entry.getAuthor().getNick());
	}
}
