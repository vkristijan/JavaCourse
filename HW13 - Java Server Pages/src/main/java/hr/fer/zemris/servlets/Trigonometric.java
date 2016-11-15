package hr.fer.zemris.servlets;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web application that is used to calculate the values of sin and cos functions
 * of a range of integers. The result is displayed as a table on a new page.
 * <br>
 * The expected arguments are numbers a and b, the range in which the values
 * should be calculated. If nothing is given, a is assumed to be 0, and b is
 * assumed to be 360.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet(name="trigonometric", urlPatterns={"/trigonometric"})
public class Trigonometric extends HttpServlet{
	
	/**
	 * The serial version ID number.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Object arg1 = req.getParameter("a");
		int a = arg1 == null ? 0 : Integer.parseInt(arg1.toString());
		
		Object arg2 = req.getParameter("b");
		int b = arg2 == null ? 360 : Integer.parseInt(arg2.toString());
		
		if (a > b){
			int tmp = a;
			a = b;
			b = tmp;
		}
		
		if (b > a + 720){
			b = a + 720;
		}
		
		List<TrigonometricValues> trigValues = new LinkedList<>();
		for (int i = a; i <= b; ++i){
			trigValues.add(new TrigonometricValues(i));
		}
		
		req.setAttribute("trigValues", trigValues);
		req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
	
	/**
	 * Calculates the values of sin and cos functions for a given integer
	 * number. The results are accessible through getter methods.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 *
	 */
	public static class TrigonometricValues {
		/**
		 * The number used as the argument for sin and cos functions.
		 */
		private final int x;
		/**
		 * The trigonometric sine of angle x.
		 */
		private final double sin;
		/**
		 * The trigonometric cosine of angle x.
		 */
		private final double cos;
		
		/**
		 * Decimal format used to return the value of sine and cosine. Set to
		 * display the number rounded on 3 decimal places.
		 */
		private static final DecimalFormat format = new DecimalFormat("#.###");

		/**
		 * Creates new {@link TrigonometricValues} for the given angle X and
		 * calculates the values for sine and cosine.
		 * 
		 * @param x
		 *            the angle for the calculation.
		 */
		public TrigonometricValues(int x) {
			this.x = x;
			this.sin = Math.sin(x);
			this.cos = Math.cos(x);
		}

		/**
		 * Returns the value of angle used for the calculation.
		 * 
		 * @return the value of angle used for the calculation.
		 */
		public String getX() {
			return String.valueOf(x);
		}

		/**
		 * Returns the value of sine function of the given angle. The result
		 * is formated as specified by the {@link #format}.
		 * 
		 * @return the value of sine function of the given angle.
		 */
		public String getSin() {
			return format.format(sin);
		}

		/**
		 * Returns the value of cosine function of the given angle. The result
		 * is formated as specified by the {@link #format}.
		 * 
		 * @return the value of cosine function of the given angle.
		 */
		public String getCos() {
			return format.format(cos);
		}
	}
}
