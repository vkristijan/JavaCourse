package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.SmartHttpServerException;

/**
 * {@link IWebWorker} implementation that creates a png image with 200 pixels
 * width and 200 pixels height. The picture will contain a single filled circle
 * in a random color, of random size on a random position.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class CircleWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics2D g2d = bim.createGraphics();

		Random rnd = new Random();
		g2d.setColor(new Color(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
		g2d.fillRect(0, 0, bim.getWidth(), bim.getHeight());
		
		g2d.setColor(new Color(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
		
		int radius = rnd.nextInt(90) + 10;
		g2d.fillOval(rnd.nextInt(200 - radius), rnd.nextInt(200 - radius), radius, radius);
		
		g2d.dispose();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			context.setMimeType("image/png");
			context.write(bos.toByteArray());
		} catch (IOException e) {
			String message = "Unable to create the picture!";
			throw new SmartHttpServerException(message, e);
		}
	}

}
