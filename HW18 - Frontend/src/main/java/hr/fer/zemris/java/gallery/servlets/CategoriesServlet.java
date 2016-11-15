package hr.fer.zemris.java.gallery.servlets;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.gallery.Utility;
import hr.fer.zemris.java.gallery.model.Picture;

/**
 * Web servlet used to get all the categories in this picture gallery.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet(urlPatterns={"/servlets/categories"})
public class CategoriesServlet extends HttpServlet {
	/**
	 * The default serial version ID number.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String filePath = req.getServletContext().getRealPath(Utility.DESCRIPTION_FILE); 
		Path file = Paths.get(filePath);
		
		List<Picture> pictures = Utility.getPicturesFromFile(file);
		
		Set<String> categories = new HashSet<>();
		for (Picture picture : pictures){
			String[] pictureCategories = picture.getCategories();
			for (String category : pictureCategories){
				categories.add(category);
			}
		}

		String[] array = new String[categories.size()];
		categories.toArray(array);
		
		resp.setContentType("application/json;charset=UTF-8"); 
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(array);
		
		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}
}
