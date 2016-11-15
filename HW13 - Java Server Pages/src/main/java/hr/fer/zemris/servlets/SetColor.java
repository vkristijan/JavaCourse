package hr.fer.zemris.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web application used to set the background color attribute for the current
 * session to a new value. The new color is defined as a parameter named color.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet(name="setColor", urlPatterns={"/setColor"})
public class SetColor extends HttpServlet{
	/**
	 * The serial version ID number.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = req.getParameter("color");
		
		if (color != null){
			req.getSession().setAttribute("pickedBgCol", color);
		} else {
			color = "null";
		}
		
		resp.sendRedirect("./index.jsp");
	}
}
