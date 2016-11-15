package hr.fer.zemris.java.webserver;

/**
 * Interface defining a worker that can be used to process a web request and
 * produce a web page as a result.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public interface IWebWorker {
	/**
	 * Processes the given {@link RequestContext} and produces a web page.
	 * 
	 * @param context
	 *            the {@link RequestContext} that was given.
	 */
	public void processRequest(RequestContext context);
}
