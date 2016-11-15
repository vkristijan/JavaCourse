package hr.fer.zemris.java.gallery.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.gallery.Utility;

/**
 * A web servlet that is used to display an picture with the given file name.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet("/servlets/getImage")
public class GetImageServlet extends HttpServlet {
	/**
	 * The default serial version ID number.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The width of the image.
	 */
	private static int IMG_WIDTH = 1080;
	/**
	 * The height of the image.
	 */
	private static int IMG_HEIGHT = 720;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getParameter("imgName");
		
		String dirPath = req.getServletContext().getRealPath("WEB-INF");
        BufferedImage jpgImage = Utility.getImage(fileName, dirPath, IMG_WIDTH, IMG_HEIGHT);
        
        resp.setContentType("image/jpeg");
        ImageIO.write(jpgImage, "jpg", resp.getOutputStream());
	}
}
