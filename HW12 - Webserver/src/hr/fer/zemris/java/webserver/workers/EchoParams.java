package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.SmartHttpServerException;

/**
 * {@link IWebWorker} implementation that reads the given request, extracts the
 * given parameters and prints them in a html table.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		Set<String> paramNames = context.getParameterNames();

		try {
			context.write("<html> <head> <title>Parameters</title></head> <body> <table border='1'>");
		} catch (IOException e1) {
			String message = "Unable to write the parameters!";
			throw new SmartHttpServerException(message, e1);
		}
		
		paramNames.forEach((x) -> {
			try {
				context.write("<tr> <td>").write(x).write("</td>");
				context.write("<td>").write(context.getParameter(x)).write("</td></tr>");
				
			} catch (IOException e){
				String message = "Unable to write the parameters!";
				throw new SmartHttpServerException(message, e);
			}
		});
		
		try {
			context.write("</table> </body> </html>");
		} catch (IOException e1) {
			String message = "Unable to write the parameters!";
			throw new SmartHttpServerException(message, e1);
		}
	}

}
