package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web servlet used to redirect the user to the main page.
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet("/")
public class IndexServlet extends HttpServlet{
	/**
	 * The serial version ID number, used for object serialization.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("./servleti/main");
	}
}
