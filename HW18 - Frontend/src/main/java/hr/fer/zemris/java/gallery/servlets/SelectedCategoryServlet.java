package hr.fer.zemris.java.gallery.servlets;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.gallery.Utility;
import hr.fer.zemris.java.gallery.model.Picture;

/**
 * Web servlet used to get all the pictures in a category that the user
 * selected.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
@WebServlet(urlPatterns={"/servlets/selectedCategory"})
public class SelectedCategoryServlet extends HttpServlet {
	/**
	 * The default serial version ID number.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String categoryName = req.getParameter("categoryName");
		
		String filePath = req.getServletContext().getRealPath(Utility.DESCRIPTION_FILE); 
		Path file = Paths.get(filePath);
		
		List<Picture> pictures = Utility.getPicturesFromFile(file);
		pictures.removeIf((x) -> {
			for (String category : x.getCategories()){
				if (category.equals(categoryName)){
					return false;
				}
			}
			return true;
		});
		
		Picture[] pictureList = new Picture[pictures.size()];
		pictures.toArray(pictureList);
		
		resp.setContentType("application/json;charset=UTF-8"); 
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(pictureList);
		
		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}
}
