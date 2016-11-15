package hr.fer.zemris.java.gallery;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.gallery.model.Picture;

/**
 * Helper methods and constants used in this application.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class Utility {
	/**
	 * File containing data about the pictures in this gallery.
	 */
	public static final String DESCRIPTION_FILE = "WEB-INF/opisnik.txt";
	
	/**
	 * Creates a list of all {@link Picture}s described in the given file. The
	 * list doesn't contain real pictures, just data describing the picture.
	 * 
	 * @param file
	 *            the path to the file containing the picture information.
	 * @return a list of all the pictures.
	 * @throws IOException
	 *             if it is not possible to read from the given file.
	 */
	public static List<Picture> getPicturesFromFile(Path file) throws IOException{
		List<Picture> pictureList = new ArrayList<>();
		
		List<String> lines = Files.readAllLines(file);
		if (lines.size() % 3 != 0){
			throw new RuntimeException("Invalid number of lines in description file.");
		}
		
		int size = lines.size();
		for (int i = 0; i < size; i += 3){
			String filePath = lines.get(i);
			String description = lines.get(i + 1);
			String categories = lines.get(i + 2);
			
			Picture picture = new Picture(filePath, description, categories);
			pictureList.add(picture);
		}
		
		return pictureList;
	}

	/**
	 * Returns a thumbnail of the picture with the given file name. Thumbnails
	 * are cashed locally, so that the image is resized only the first time.
	 * 
	 * @param fileName
	 *            the file name of the picture.
	 * @param dirPath
	 *            the current directory.
	 * @param width
	 *            the width of the picture to be returned.
	 * @param height
	 *            the height of the picture to be returned.
	 * @return a {@link BufferedImage} of the thumbnail.
	 * @throws IOException
	 *             if it is not possible to read from the given file.
	 */
	public static BufferedImage getThumbnail(String fileName, String dirPath, int width, int height) throws IOException {
		Path directory = Paths.get(dirPath + "/thumbnails").toAbsolutePath();
		if (!Files.exists(directory)){
			Files.createDirectory(directory);
		}

		Path thumbnailPath = Paths.get(dirPath + "/thumbnails/" + fileName);
		if (Files.exists(thumbnailPath)){
			return ImageIO.read(thumbnailPath.toFile());
		}
		
		BufferedImage image = getImage(fileName, dirPath, width, height);

		ImageIO.write(image, "jpg", thumbnailPath.toFile());
		return image;
	}
	
	/**
	 * Resizes the given image to the given size.
	 * 
	 * @param original
	 *            the image that should be resized.
	 * @param width
	 *            the width of the new image.
	 * @param height
	 *            the height of the new image.
	 * @return a {@link BufferedImage} of the resized image.
	 */
	private static BufferedImage resize(BufferedImage original, int width, int height){
		int type = original.getType() == 0? BufferedImage.TYPE_INT_ARGB : original.getType();
		
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(original, 0, 0, width, height, null);
		g.dispose();
		
		return resizedImage;
	}

	/**
	 * Reads the image with the defined file name.
	 * 
	 * @param fileName
	 *            the file name of the picture.
	 * @param dirPath
	 *            the current directory.
	 * @param width
	 *            the width of the picture to be returned.
	 * @param height
	 *            the height of the picture to be returned.
	 * @return a {@link BufferedImage} of the picture.
	 * @throws IOException
	 *             if it is not possible to read from the given file.
	 */
	public static BufferedImage getImage(String fileName, String dirPath, int width, int height) throws IOException {
		Path filePath = Paths.get(dirPath + "/slike/" + fileName);
		
		BufferedImage originalImage = ImageIO.read(filePath.toFile());
		BufferedImage resizedImage = resize(originalImage, width, height);
		
		return resizedImage;
	}
}
