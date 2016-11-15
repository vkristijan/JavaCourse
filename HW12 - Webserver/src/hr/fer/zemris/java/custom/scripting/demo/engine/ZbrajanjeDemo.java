package hr.fer.zemris.java.custom.scripting.demo.engine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Reads the script "zbrajanje.smscr" and executes it. For the script to execute
 * correctly, this program also creates the required cookies and parameters.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class ZbrajanjeDemo {
	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		String documentBody = readFromDisk("webroot/scripts/zbrajanje.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");

		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
			new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

	}

	/**
	 * Reads the data from a file on the given filePath.
	 * 
	 * @param filePath
	 *            the path to the file that should be read.
	 * @return a string with the content of the file.
	 */
	private static String readFromDisk(String filePath) {
		String data = null;
		try {
			data = new String(Files.readAllBytes(Paths.get(filePath)),
				StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Unable to read from the given document!");
		}
		return data;
	}
}
