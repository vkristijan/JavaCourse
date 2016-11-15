package hr.fer.zemris.java.tecaj_13.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * Web application that reads the voting results and generates an Microsoft
 * Office Excel document containing all the results. The generated document will
 * be automaticly downloaded.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet(name="glasanje-xls", urlPatterns={"/glasanje-xls"})
public class VotingXlsServlet extends HttpServlet{
	/**
	 * The serial version ID number.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollId = Long.parseLong(req.getParameter("pollId").toString());
		
		List<PollOption> entries = DAOProvider.getDao().getPollOptions(pollId);
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Voting results");
		
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("Place");
		header.createCell(1).setCellValue("Name");
		header.createCell(2).setCellValue("Number of votes");
		header.createCell(3).setCellValue("Details link");
		
		int n = entries.size();
		for (int i = 1; i <= n; ++i){
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(i);
			row.createCell(1).setCellValue(entries.get(i - 1).getOptionTitle());
			row.createCell(2).setCellValue(entries.get(i - 1).getVotesCount());
			row.createCell(3).setCellValue(entries.get(i - 1).getOptionLink());
		}
		
		resp.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=\"Voting results.xls\"";
        resp.setHeader(headerKey, headerValue);
		
        workbook.write(resp.getOutputStream());
        workbook.close();
	}
}
