package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.SmartHttpServerException;

/**
 * {@link IWebWorker} implementation that creates a simple HTML page that
 * displays the current time, and a hello message. The program reads the
 * parameter named "name" and prints the length of the name if it was given.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class HelloWorker implements IWebWorker {
	@Override
	public void processRequest(RequestContext context) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		context.setMimeType("text/html");
		String name = context.getParameter("name");
		try {
			context.write("<html><body>");
			context.write("<h1>Hello!!!</h1>");
			context.write("<p>Now is: " + sdf.format(now) + "</p>");
			if (name == null || name.trim().isEmpty()) {
				context.write("<p>You did not send me your name!</p>");
			} else {
				context.write("<p>Your name has " + name.trim().length() + " letters.</p>");
			}
			context.write("</body></html>");
		} catch (IOException ex) {
			String message = "Error during writing to the output stream!";
			throw new SmartHttpServerException(message, ex);
		}
	}
}
