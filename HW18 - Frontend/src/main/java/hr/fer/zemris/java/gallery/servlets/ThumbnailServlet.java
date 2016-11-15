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
 * Web servlet that is used to display thumbnails of images. Thumbnails are
 * cashed on the server.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebServlet("servlets/thumbnail")
public class ThumbnailServlet extends HttpServlet{
	/**
	 * The default serial version ID number.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The image width.
	 */
	private static int IMG_WIDTH = 150;
	/**
	 * The image height.
	 */
	private static int IMG_HEIGHT = 150;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getParameter("name");
		
		String dirPath = req.getServletContext().getRealPath("WEB-INF");
        BufferedImage jpgImage = Utility.getThumbnail(fileName, dirPath, IMG_WIDTH, IMG_HEIGHT);
        
        resp.setContentType("image/jpeg");
        ImageIO.write(jpgImage, "jpg", resp.getOutputStream());
	}
}
