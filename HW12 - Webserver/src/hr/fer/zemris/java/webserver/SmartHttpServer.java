package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * A web server capable of executing Smart Script language. The server s capable
 * of serving multiple users at the same time, and executing the requests on
 * separate threads. Supported operations are viewing a document stored on the
 * server and executing smart script scripts, either by calling them as defined
 * in properties file, or by using the /ext/ prefix to instance the class.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class SmartHttpServer {
	/**
	 * The address of the web server.
	 */
	private String address;
	/**
	 * The port that the server should listen to.
	 */
	private int port;
	/**
	 * The number of worker threads on this server.
	 */
	private int workerThreads;
	/**
	 * Time until a session times out.
	 */
	private int sessionTimeout;
	/**
	 * Map of all supported mime types. The key of the map is the name of the
	 * mime, and the value is the mime type that should be written in the
	 * header.
	 */
	private Map<String, String> mimeTypes = new HashMap<>();
	/**
	 * Map of all workers defined in the properties.
	 */
	private Map<String,IWebWorker> workersMap = new HashMap<>();
	 	
	/**
	 * The thread of the server.
	 */
	private ServerThread serverThread;
	/**
	 * The thread pool used to process client requests.
	 */
	private ExecutorService threadPool;
	/**
	 * Path to root directory from which we serve files.
	 */
	private Path documentRoot;
	
	/**
	 * Map of all current active sessions. The key of map is session ID number.
	 * The map is automatically "garbage collected" from time to time to delete
	 * sessions that have expired.
	 */
	private Map<String, SessionMapEntry> sessions = 
			new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * A random number generator used for generating session ID-s.
	 */
	private Random sessionRandom = new Random();
	
	/**
	 * Thread used to check sessions to delete expired sessions from memory.
	 */
	private SessionGarbageCollector sessionGarbageCollector;

	/**
	 * Creates a new {@link SmartHttpServer} that loads the properties from the
	 * given properties file, and starts the server.
	 * 
	 * @param configFileName
	 *            the file containing the properties for this server.
	 */
	public SmartHttpServer(String configFileName) {
		Properties serverProp = loadProperties(configFileName);
				
		address = serverProp.getProperty("server.addres");
		port = Integer.parseInt(serverProp.getProperty("server.port"));
		workerThreads = Integer.parseInt(serverProp.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(serverProp.getProperty("session.timeout"));
		documentRoot = Paths.get(serverProp.getProperty("server.documentRoot"));
		
		String mimeConfigFile = serverProp.getProperty("server.mimeConfig");
		Properties mimeProp = loadProperties(mimeConfigFile);

		mimeProp.entrySet().forEach(e -> {
				String key = e.getKey().toString();
				String value = e.getValue().toString();
				mimeTypes.put(key, value);
			}
		);
		
		
		
		String workersConfigFile = serverProp.getProperty("server.workers");
		
		Properties workerProp = loadProperties(workersConfigFile);
		workerProp.entrySet().forEach((x) -> {
			try {
				String path = x.getKey().toString();
				String fqcn = x.getValue().toString();
				Class<?> referenceToClass;
				
				referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.newInstance(); 
				IWebWorker iww = (IWebWorker)newObject; 
				workersMap.put(path, iww);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e1) {
				String message = "Error during worker creation!";
				throw new SmartHttpServerException(message, e1);
			} 
		});
		

		serverThread = new ServerThread();
		start();
		
		sessionGarbageCollector = new SessionGarbageCollector();
		sessionGarbageCollector.start();
	}	
	
	/**
	 * A thread that is used as a garbage collector for sessions. The thread
	 * will execute every 5 minutes and check if there is an expired session and
	 * delete it. The thread is daemon.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 */
	private class SessionGarbageCollector extends Thread {
		/**
		 * The time this thread will wait before trying to remove sessions
		 * again.
		 */
		private static final int WAIT_TIME = 5 * 60_000;
		
		/**
		 * Creates a new {@link SessionGarbageCollector} that is set to be
		 * daemon.
		 */
		public SessionGarbageCollector() {
			setDaemon(true);
		}
		
		@Override
		public void run() {
			while (true) {
				synchronized (SmartHttpServer.this) {
					Iterator<Map.Entry<String, SessionMapEntry>> it;
					it = sessions.entrySet().iterator();
					
					long time = System.currentTimeMillis() / 1000;
					while (it.hasNext()){
						Map.Entry<String, SessionMapEntry> entry = it.next();
						
						long validUntil = entry.getValue().validUntil;
						if (validUntil > time){
							it.remove();
						}
					}
				}
				
				try {
					sleep(WAIT_TIME);
				} catch (InterruptedException e){}
			}
		}
	}

	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		if (args.length != 1){
			String message = "Expected one argument, the path to the config file.";
			throw new IllegalArgumentException(message);
		}
			
		new SmartHttpServer(args[0]);
	}
	
	/**
	 * Creates a new instance of {@link Properties} and loads all the properties
	 * given in the file in the argument.
	 * 
	 * @param configFileName
	 *            the path to the file containing the properties to load.
	 * @return a new created {@link Properties} containing all the properties
	 *         from the file.
	 */
	private Properties loadProperties(String configFileName) {
		Properties properties = new Properties();
		
		try (InputStream is = new FileInputStream(configFileName);){
			properties.load(is);
		} catch (FileNotFoundException e) {
			System.err.println("The given file has not been found!");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Unable to read from the given file!");
			System.exit(1);
		}
		
		return properties;
	}

	/**
	 * Creates a new thread pool if it is shut down, and starts the server.
	 */
	protected synchronized void start() {
		if (threadPool == null || threadPool.isShutdown()){
			threadPool = Executors.newFixedThreadPool(workerThreads);
		}
		
		if (!serverThread.isAlive()){
			serverThread.start();
		}
	}
	
	/**
	 * Shuts down the thread pool and gives the server thread a signal to stop
	 * itself.
	 */
	protected synchronized void stop() {
		serverThread.setStop();
		threadPool.shutdown();
	}

	/**
	 * A thread used to execute the server. The thread waits for requests, and
	 * once a request occurs, it will create a new worker to serve the client.
	 * The server will still remain responsive and wait for next requests.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 */
	protected class ServerThread extends Thread {
		/**
		 * The maximum time to wait for a cookie.
		 */
		private final static int TIMEOUT_TIME = 10000;
		/**
		 * A boolean flag that is used to check if the server should be stopped
		 * or not.
		 */
		private boolean toStop;
		
		/**
		 * Signals the thread that it should stop.
		 */
		public void setStop(){
			toStop = true;
		}
		
		@Override
		public void run() {
			try {
				@SuppressWarnings("resource")
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
				serverSocket.setSoTimeout(TIMEOUT_TIME);

				while (!toStop){
					Socket client;
					
					try {
						client = serverSocket.accept();
					} catch (SocketTimeoutException e) {
						System.out.println("Request timed out...");
						continue;
					}

					System.out.println("Recieved a new Request!");
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException e) {
				String message = "Unable to create socket!";
				throw new SmartHttpServerException(message, e);
			}
		}
	}

	/**
	 * A {@link Runnable} worker used to execute client requests. Every worker
	 * will read the {@link Socket} it got and try to read the request header
	 * from the input stream defined in the socket. The worker will then serve
	 * the client, depending on the request. Supported requests are requests for
	 * a file on the server, executing scripts defined in properties and
	 * executing scripts with prefix /ext/. After serving the client, the worker
	 * will die.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 */
	private class ClientWorker implements Runnable {
		/**
		 * The {@link Socket} that this {@link ClientWorker} is using.
		 */
		private Socket csocket;
		/**
		 * The input stream defined in the {@link Socket}, wrapped into a
		 * {@link PushbackInputStream}.
		 */
		private PushbackInputStream istream;
		/**
		 * The output stream defined in the {@link Socket}.
		 */
		private OutputStream ostream;
		/**
		 * The http version used on this request.
		 */
		private String version;
		/**
		 * The method of this request.
		 */
		private String method;
		/**
		 * The parameters given in this request.
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * A list of parameters from the previous session that was stored.
		 */
		private Map<String, String> permPrams = null;
		/**
		 * A list of all the output cookies created by the request.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * The session ID of this client.
		 */
		private String SID;

		/**
		 * Creates a new {@link ClientWorker} with the {@link Socket} defined in
		 * the argument.
		 * 
		 * @param csocket
		 *            the {@link Socket} that this {@link ClientWorker} should
		 *            use.
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				
				List<String> request = readRequest();
				if (request.size() < 1){
					sendError(400, "Invalid header file!");
					return;
				}
			 
				checkSession(request);
				
				String firstLine = request.get(0);

				String[] lineData = firstLine.split(" ");
				if (lineData == null || lineData.length != 3){
					sendError(400, "Bad request");
					return;
				}
				method = lineData[0].toUpperCase();
				String requestedPath = lineData[1];
				version = lineData[2];
				
				if (!(version.equals("HTTP/1.0") || version.equals("HTTP/1.1"))){
					sendError(400, "Version not supported!");
					return;
				}
				if (!method.equals("GET")){
					sendError(400, "Method not supported!");
					return;
				}

				String path; 
				String paramString;
				
				int index = requestedPath.indexOf('?');
				if (index != -1){
					path = requestedPath.substring(0, index);
					paramString = requestedPath.substring(index + 1);
				} else {
					path = requestedPath;
					paramString = null;
				}
				
				parseParameters(paramString);
				
				Path requestPath = Paths.get(documentRoot.toAbsolutePath() + path);
				if (!requestPath.startsWith(documentRoot)){
					sendError(403, "Forbidden");
					System.out.println(documentRoot.toAbsolutePath().toString());
					System.out.println(requestPath.toAbsolutePath().toString());
					System.out.println("Forbidden access");
					return;
				}

					
				RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);
				
				String fileName = requestPath.getFileName().toString();
				index = fileName.lastIndexOf('.');
				String extension = "";
				
				if (index > 1){
					extension = fileName.substring(index + 1);
				}
				
				if (path.startsWith("/ext/")){
					path = path.substring(5);
					
					String packageName = "hr.fer.zemris.java.webserver.workers." + path;
					Class<?> referenceToClass;
					try {
						referenceToClass = this.getClass().getClassLoader().loadClass(packageName);
						Object newObject = referenceToClass.newInstance();
						IWebWorker iww = (IWebWorker) newObject;
						if (iww != null){
							iww.processRequest(rc);
						}
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
						String message = "Error during worker creation!";
						throw new SmartHttpServerException(message, e);
					}
					
					return;
				} else {
					IWebWorker worker = workersMap.get(path);
					if (worker != null){
						worker.processRequest(rc);
						return;
					}
				}
				
				if (!Files.exists(requestPath) || !Files.isRegularFile(requestPath) || !Files.isReadable(requestPath)){
					sendError(404, "File not Found");
					return;
				}
					
				if (extension.equals("smscr")){
					String documentBody = new String(Files.readAllBytes(requestPath), StandardCharsets.UTF_8);
					
					rc.setMimeType("text/plain");
					rc.setStatusCode(200);
					SmartScriptParser scParser = new SmartScriptParser(documentBody);
					
					new SmartScriptEngine(scParser.getDocumentNode(), rc).execute();
					
				} else {
					String mimeType = mimeTypes.get(extension);
					if (mimeType == null) {
						mimeType = "application/octet-stream";
					}
					rc.setMimeType(mimeType);
					rc.setStatusCode(200);
					
					byte[] fileContent = Files.readAllBytes(requestPath);
					rc.write(fileContent);
					ostream.flush();
				}
			} catch (IOException e) {
				String message = "Error during socket evaluation!";
				throw new SmartHttpServerException(message, e);
			} finally {
				System.out.println("Everything done!");
				try {
					csocket.close();
				} catch (IOException e) {}
			}

		}
		
		/**
		 * Checks if the cookie got in the request is a currently stored
		 * session. Creates a new session if necessary, if the old session has
		 * timed out, or never existed.
		 * 
		 * @param request
		 *            the current header requests.
		 */
		private void checkSession(List<String> request) {
			synchronized(SmartHttpServer.this){
				String sidCandidate = null;
				for (String line : request){
					if (line.startsWith("Cookie:")){
						line = line.substring(7).trim();
						
						String[] cookies = line.split(";");
						
						for (String cookie : cookies){
							cookie = cookie.trim();
							if (!cookie.startsWith("sid")){
								continue;
							}
							sidCandidate = cookie.split("=")[1];
							
							sidCandidate = sidCandidate.replaceAll("\"", "");
							System.out.println("Got SID:" + sidCandidate);
						}
					}
				}
				
				if (sidCandidate == null){
					sidCandidate = createSid();
				} else {
					SessionMapEntry session = sessions.get(sidCandidate);
					if (session != null){
						if (session.validUntil > System.currentTimeMillis() / 1000){
							session.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
						} else {
							System.out.println("Cookie too old :(");
							sessions.remove(sidCandidate);
							sidCandidate = createSid();
						}
					} else {
						sidCandidate = createSid();
					}
				}
				
				permPrams = sessions.get(sidCandidate).map;
			}
		}

		/**
		 * Creates a new session ID string. The value will be random generated
		 * 20 upper case Latin letters.
		 * 
		 * @return the generated SID number.
		 */
		private String createSid() {
			StringBuilder sb = new StringBuilder();
			
			for (int i = 0; i < 20; ++i){
				int letter = sessionRandom.nextInt(26);
				sb.append((char)(letter + 'A'));
			}
			
			SessionMapEntry mapEntry = new SessionMapEntry();
			SID = sb.toString();
			mapEntry.sid = SID;
			mapEntry.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
			mapEntry.map = new ConcurrentHashMap<>();
			
			outputCookies.add(new RCCookie("sid", SID, sessionTimeout, address, "/"));
			
			sessions.put(mapEntry.sid, mapEntry);
			return sb.toString();
		}

		/**
		 * Reads the header from a request.
		 * 
		 * @return a list of lines in the request header.
		 * @throws IOException
		 *             if there is an error during reading from the stream
		 */
		private List<String> readRequest() throws IOException {
			List<String> headers = new ArrayList<>();
			
			String requestStr = new String(readRequestFromStream(istream), StandardCharsets.US_ASCII);
			System.out.println(requestStr);
			
			String currentLine = null;
			for (String s : requestStr.split("\n")){
				if (s.isEmpty()) break;
				char c = s.charAt(0);
				if (c == 9 || c == 32){
					currentLine += s;
				} else {
					if (currentLine != null){
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			
			if (!currentLine.isEmpty()){
				headers.add(currentLine);
			}
			return headers;
		}
		
		/**
		 * Reads the request from the input stream from the cookie.
		 * 
		 * @param is
		 *            the input stream used to read the data
		 * 
		 * @return a byte array with the request from the cookie.
		 * @throws IOException
		 *             if it is not possible to read from the given input
		 *             stream.
		 */
		private byte[] readRequestFromStream(InputStream is) throws IOException{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			
l:			while(true){
				int b = is.read();
				if (b == -1) return "\r\n".getBytes(StandardCharsets.US_ASCII);
				if (b != 13){
					bos.write(b);
				}
				switch (state) {
					case 0:
						if (b == 13) {
							state = 1;
						} else if (b == 10){
							state = 4;
						}
						break;
						
					case 1:
						if (b == 10) {
							state = 2;
						} else {
							state = 0;
						}
						break;
					
					case 2:
						if (b == 13) {
							state = 3;
						} else {
							state = 0;
						}
						break;
					
					case 3:
						if (b == 10) {
							break l;
						} else {
							state = 0;
						}
						break;
						
					case 4:
						if (b == 10) {
							break l;
						} else {
							state = 0;
						}
						break;

					default:
						break;
				}
				
			}
			return bos.toByteArray();
		}

		/**
		 * Parses the parameter string and adds all found parameters to the
		 * parameter map. A single parameter consists of a pair 'key' and
		 * 'value', separated with an '=' sign. Multiple parameters are
		 * separated by &.
		 * 
		 * @param paramString
		 *            the string containing all the parameters.
		 */
		private void parseParameters(String paramString) {
			if (paramString == null) return;
			
			paramString = paramString.trim();
			while (!paramString.isEmpty()){
				int index = paramString.indexOf('&');
				
				String currentParam;
				if (index == -1){
					currentParam = paramString;
					paramString = "";
				} else {
					currentParam = paramString.substring(0, index);
					paramString = paramString.substring(index + 1);
				}
				
				parseParameter(currentParam);
			}
		}

		/**
		 * Parses the given parameter string and adds it to the parameters map.
		 * The parameter is in form 'key' and 'value', separated with an '='
		 * sign.
		 * 
		 * @param currentParam
		 *            the string containing a single parameter.
		 * @throws SmartHttpServerException
		 *             if the given parameter string does not represent a valid
		 *             parameter.
		 */
		private void parseParameter(String currentParam) {
			if (currentParam == null) return;
			currentParam = currentParam.trim();
			if (currentParam.isEmpty()) return;
			
			int index = currentParam.indexOf('=');
			if (index == -1){
				throw new SmartHttpServerException("Illegal parameters!");
			}
			
			String name = currentParam.substring(0, index);
			String value = currentParam.substring(index + 1);
			
			params.put(name, value);
		}

		/**
		 * Sends an error with the given status code and status message.
		 * 
		 * @param statusCode
		 *            the status code that should be sent.
		 * @param statusText
		 *            the status text that should be displayed with the status
		 *            code.
		 * @throws IOException
		 *             if it is not possible to write to the output stream.
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			ostream.write((
				"HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
				"Server: SmartHttpServer\r\n" +
				"Content-Type: text/plain;charset=UTF-8\r\n" +
				"Content-Length: 0\r\n" +
				"Connection: close\r\n" + 
				"\r\n"
			).getBytes(StandardCharsets.US_ASCII));
			
			ostream.flush();
		}
	}

	/**
	 * The data of a single session that is stored in the sessions map. A
	 * session is defined by it's session ID (SID number), the time until the
	 * session is valid, and the map of stored parameters.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 */
	private static class SessionMapEntry {
		/**
		 * The session id.
		 */
		String sid;
		/**
		 * The time until the session is valid. The time is written in seconds.
		 */
		long validUntil;
		/**
		 * The map of stored parameters.
		 */
		Map<String, String> map;
	}
	
	
}