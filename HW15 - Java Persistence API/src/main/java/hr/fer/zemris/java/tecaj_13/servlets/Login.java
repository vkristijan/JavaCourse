package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.Utility;
import hr.fer.zemris.java.tecaj_13.dao.jpa.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Web page used to handle user login. Creates a form for username and password,
 * and if the username was already given before, it will be already set to the
 * last given value. The given password is encrypted using
 * {@link Utility#hexEncode(String)} so that it is not visible in the database.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet("/servleti/login")
public class Login extends HttpServlet{
	/**
	 * The default serial version ID number.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = Utility.hexEncode(req.getParameter("password"));
		
		BlogUser user = DAOProvider.getDAO().getUser(username, password);
		
		if (user != null){
			req.getSession().setAttribute("current.user.id", user.getId());
			req.getSession().setAttribute("current.user.fn", user.getFirstName());
			req.getSession().setAttribute("current.user.ln", user.getLastName());
			req.getSession().setAttribute("current.user.nick", user.getNick());
			req.getSession().setAttribute("current.user.email", user.getEmail());
			resp.sendRedirect("./main");
		} else {
			req.setAttribute("username", username);
			req.setAttribute("error", "The given username and password combination does not exist.");
			
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
		}
	}
}
