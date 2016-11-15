package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.Utility;
import hr.fer.zemris.java.tecaj_13.dao.jpa.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Web page used to handle user registration. Checks if some of the data is
 * already given (for example if the user tried to register, but has chosen an
 * username that is already in use. If everything is entered correctly, the new
 * user will be added to the database and redirected to the main page.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet("/servleti/register")
public class RegistrationPage extends HttpServlet{
	
	/**
	 * The default serial  version ID number.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUser user = new BlogUser();
		user.setFirstName(stringFromAttribute(req, "firstName"));
		user.setLastName(stringFromAttribute(req, "lastName"));
		user.setEmail(stringFromAttribute(req, "email"));
		user.setNick(stringFromAttribute(req, "nick"));

		String password = stringFromAttribute(req, "password");
		user.setPasswordHash(Utility.hexEncode(password));
		
		Map<String, String> errors = user.isValid();
		if (errors.isEmpty()){
			DAOProvider.getDAO().addUser(user);
			
			req.getSession().setAttribute("current.user.id", user.getId());
			req.getSession().setAttribute("current.user.fn", user.getFirstName());
			req.getSession().setAttribute("current.user.ln", user.getLastName());
			req.getSession().setAttribute("current.user.nick", user.getNick());
			req.getSession().setAttribute("current.user.email", user.getEmail());
			
			resp.sendRedirect("./main");
		} else {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
		}
	}
	
	/**
	 * Reads the given request for the parameter with the specified name. If the
	 * parameter doesn't exist an empty string is returned.
	 * 
	 * @param req
	 *            the {@link HttpServletRequest} from where the parameter should
	 *            be read.
	 * @param attribute
	 *            the name of the parameter.
	 * @return the string value of the parameter, or an empty string if nothing
	 *         was given.
	 */
	private String stringFromAttribute(HttpServletRequest req, String attribute){
		Object result = req.getParameter(attribute);
		
		
		return result == null ? "" : (String)result;
	}
}
