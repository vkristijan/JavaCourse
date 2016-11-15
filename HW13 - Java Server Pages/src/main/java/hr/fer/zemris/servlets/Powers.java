package hr.fer.zemris.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Web application that is used to calculate the powers of a range of integers.
 * The result is stored into an Microsoft Office Excel document. <br>
 * The expected arguments are numbers a and b, the range in which the values
 * should be calculated. The third argument should be number n, the number of
 * powers that should be calculated.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet(name="powers", urlPatterns={"/powers"})
public class Powers extends HttpServlet {
	/**
	 * The serial version ID number.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a = getParam("a", -100, 100, req, resp);
		int b = getParam("b", -100, 100, req, resp);
		int n = getParam("n", 1, 5, req, resp);
	
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		for (int i = 1; i <= n; ++i){
			HSSFSheet sheet = workbook.createSheet(String.valueOf(i));
			
			HSSFRow header = sheet.createRow(0);
			header.createCell(0).setCellValue("x");
			header.createCell(1).setCellValue("x^" + i);
			
			for (int j = a; j <= b; ++j){
				HSSFRow row = sheet.createRow(1 + j - a);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}
		}
		
		resp.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=\"Powers.xls\"";
        resp.setHeader(headerKey, headerValue);
		
        workbook.write(resp.getOutputStream());
        workbook.close();
	}

	/**
	 * Returns the value of a parameter, whose name is defined in the argument.
	 * If the value is not in the defined range, an error will be generated.
	 * 
	 * @param name
	 *            the name of the parameter that should be read.
	 * @param from
	 *            the lower bound of the value range.
	 * @param to
	 *            the upper bound of the value range.
	 * @param req
	 *            the {@link HttpServletRequest} from where the parameter should
	 *            be read.
	 * @param resp
	 *            the {@link HttpServletResponse} where an error should be sent.
	 * @return the value of the paramater.
	 * @throws ServletException
	 *             if the request for the GET could not be handled.
	 * @throws IOException
	 *             if an input or output error is detected when the servlet
	 *             handles the GET request.
	 */
	private int getParam(String name, int from, int to, HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		int value = 0;
		try {
			value = Integer.parseInt(req.getParameter(name));
		} catch (NumberFormatException e) {
			String message = "The attribute " + name + " was not given, or invalid. An integer value is expected!";
			req.setAttribute("error", message);
			
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
		}
		
		if (value < from || value > to){
			String message = "The attribute " + name + " is not in the expected range! "
					+ "It should be greater then " + from + " and less then " + to + ".";
			
			req.setAttribute("error", message);
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
		}
		
		return value;
	}
}
