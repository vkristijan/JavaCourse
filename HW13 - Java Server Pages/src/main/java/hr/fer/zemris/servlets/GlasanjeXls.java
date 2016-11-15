package hr.fer.zemris.servlets;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.voting.VotingEntry;

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
public class GlasanjeXls extends HttpServlet{
	/**
	 * The serial version ID number.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt"); 
		Path path = Paths.get(fileName);
		
		Map<Integer, VotingEntry> entries = Utility.readEntries(path, req, resp);
		List<VotingEntry> sorted = new ArrayList<>(entries.values());
		
		sorted.sort(new Comparator<VotingEntry>() {
			@Override
			public int compare(VotingEntry o1, VotingEntry o2) {
				return o2.getNumberOfVotes() - o1.getNumberOfVotes();
			}
		});
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Voting results");
		
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("Place");
		header.createCell(1).setCellValue("Band name");
		header.createCell(2).setCellValue("Score");
		header.createCell(3).setCellValue("Song");
		
		int n = sorted.size();
		for (int i = 1; i <= n; ++i){
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(i);
			row.createCell(1).setCellValue(sorted.get(i - 1).getName());
			row.createCell(2).setCellValue(sorted.get(i - 1).getNumberOfVotes());
			row.createCell(3).setCellValue(sorted.get(i - 1).getUrl());
		}
		
		resp.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=\"Voting results.xls\"";
        resp.setHeader(headerKey, headerValue);
		
        workbook.write(resp.getOutputStream());
        workbook.close();
	}
}
