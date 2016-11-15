package hr.fer.zemris.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web listener that stores the time when the server was started. Once the
 * server is shut down, the stored time will be deleted.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@WebListener
public class TimeListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		long currentTime = System.currentTimeMillis();
		sce.getServletContext().setAttribute("time", currentTime);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("time", null);
	}

}
