package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Defines all the data about a web context.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class RequestContext {
	/**
	 * The output stream used for writing data.
	 */
	private OutputStream outputStream;
	/**
	 * {@link Charset} used for writing to the output stream.
	 */
	private Charset charset;
	
	/**
	 * The encoding that is used to create the charset. The default value is
	 * "UTF-8".
	 */
	private String encoding = "UTF-8";
	/**
	 * The status code that is used to create the header data. The default value
	 * is 200.
	 */
	private int statusCode = 200;
	/**
	 * The status text that is used to create the header data. The default value
	 * is "OK".
	 */
	private String statusText = "OK";
	/**
	 * The content type used to create the header data. The default value is
	 * "text/html".
	 */
	private String mimeType = "text/html";
	/**
	 * The document length that should be printed in the header file. If the
	 * value is -1, nothing will be printed.
	 */
	private int documentLength = -1;
	
	/**
	 * Map of all parameters in this {@link RequestContext}.
	 */
	private Map<String,String> parameters;
	/**
	 * Map of temporary parameters in this {@link RequestContext}.
	 */
	private Map<String,String> temporaryParameters;
	/**
	 * Map of persistent parameters in this {@link RequestContext}.
	 */
	private Map<String,String> persistentParameters;
	/**
	 * Map of output cookies in this {@link RequestContext}.
	 */
	private List<RCCookie> outputCookies;
	
	/**
	 * Boolean flag that is used to check if the header data has already been
	 * generated or not. Once the header data is generated, it is not allowed to
	 * change the {@link #encoding}, {@link #statusCode}, {@link #statusText} or
	 * {@link RequestContext#mimeType}.
	 */
	private boolean headerGenerated;
	
	/**
	 * Creates a new {@link RequestContext} with the data given in the
	 * arguments. The output stream is not allowed to be null. If any of the
	 * other values is null, they will be considered as empty.
	 * 
	 * @param outputStream
	 *            the output stream that should be used to write data.
	 * @param parameters
	 *            the parameters for the {@link RequestContext}.
	 * @param persistentParameters
	 *            the persistent parameters for the {@link RequestContext}.
	 * @param outputCookies
	 *            the output cookies of the {@link RequestContext}.
	 * @throws NullPointerException
	 *             if the given output stream is null.
	 */
	public RequestContext( OutputStream outputStream, Map<String,String> parameters,
		Map<String,String> persistentParameters, List<RCCookie> outputCookies){
		
		Objects.requireNonNull(outputStream, "It is not allowed for the output stream to be null!");
		this.outputStream = outputStream;
		
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.parameters = Collections.unmodifiableMap(this.parameters);
		
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
		
		temporaryParameters = new HashMap<>();
	};

	/**
	 * Checks if the header has already been generated or not.
	 * 
	 * @throws RequestException
	 *             if the header has already been generated.
	 */
	private void checkHeader() {
		if (headerGenerated) {
			throw new RequestException("The header has already been generated. "
					+ "It is not possible to change it's content anymore!");
		}
	}
	
	/**
	 * Sets the encoding for this {@link RequestException} to the value given
	 * in the argument. It is not allowed to call this method once the header
	 * has been generated.
	 * 
	 * @param encoding
	 *            the new value for the encoding.
	 * @throws RequestException
	 *             if the header has already been generated.
	 */
	public void setEncoding(String encoding) {
		checkHeader();
		this.encoding = encoding;
	}

	/**
	 * Sets the status code for this {@link RequestException} to the value given
	 * in the argument. It is not allowed to call this method once the header
	 * has been generated.
	 * 
	 * @param statusCode
	 *            the new value for the status code.
	 * @throws RequestException
	 *             if the header has already been generated.
	 */
	public void setStatusCode(int statusCode) {
		checkHeader();
		this.statusCode = statusCode;
	}

	/**
	 * Sets the status text for this {@link RequestException} to the value given
	 * in the argument. It is not allowed to call this method once the header
	 * has been generated.
	 * 
	 * @param statusText
	 *            the new value for the status text.
	 * @throws RequestException
	 *             if the header has already been generated.
	 */
	public void setStatusText(String statusText) {
		checkHeader();
		this.statusText = statusText;
	}

	/**
	 * Sets the mime type for this {@link RequestException} to the value given
	 * in the argument. It is not allowed to call this method once the header
	 * has been generated.
	 * 
	 * @param mimeType
	 *            the new value for the mime type.
	 * @throws RequestException
	 *             if the header has already been generated.
	 */
	public void setMimeType(String mimeType) {
		checkHeader();
		this.mimeType = mimeType;
	}

	/**
	 * Retrieves value from parameters map. If no associated parameter exists,
	 * null is returned.
	 * 
	 * @param name
	 *            the key of the parameter that should be found.
	 * @return the value of the parameter with the given key.
	 * @throws NullPointerException
	 *             if the name argument is null
	 */
	public String getParameter(String name){
		Objects.requireNonNull(name, "The name is not allowed to be null!");
		
		return parameters.get(name);
	}
	
	/**
	 * Returns a read only set of names of all parameters in parameters map.
	 * 
	 * @return a read only set of names of all parameters in parameters map.
	 */
	public Set<String> getParameterNames(){
		return Collections.unmodifiableSet(parameters.keySet());
	}
	
	/**
	 * Retrieves value from persistent parameters map. If no associated
	 * parameter exists, null is returned.
	 * 
	 * @param name
	 *            the key of the parameter that should be found.
	 * @return the value of the parameter with the given key.
	 * @throws NullPointerException
	 *             if the name argument is null
	 */
	public String getPersistentParameter(String name){
		Objects.requireNonNull(name, "The name is not allowed to be null!");
		
		return persistentParameters.get(name);
	}
	
	/**
	 * Returns a read only set of names of all parameters in persistent
	 * parameters map.
	 * 
	 * @return a read only set of names of all parameters in persistent
	 *         parameters map.
	 */
	public Set<String> getPersistentParameterNames(){
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * Adds the given value to the persistent parameters map under the specified
	 * name. The name is not allowed to be null. If the map previously contained
	 * a mapping for given name, the old value is replaced by the given value.
	 * 
	 * @param name
	 *            the name of the value that should be added.
	 * @param value
	 *            the value to be added to the map.
	 * @throws NullPointerException
	 *             if the name is null
	 */
	public void setPersistentParameter(String name, String value){
		Objects.requireNonNull(name,  "The name is not allowed to be null!");
		persistentParameters.put(name, value);
	}
	
	/**
	 * Removes the value from persistent parameters map for the given name. If
	 * there is no value stored for the given name, nothing will happen.
	 * 
	 * @param name
	 *            the name of the value that should be removed.
	 * @throws NullPointerException
	 *             if the name is null.
	 */
	public void removePersistentParameter(String name){
		Objects.requireNonNull(name, "The name is not allowed to be null!");
		persistentParameters.remove(name);
	}

	/**
	 * Retrieves value from temporary parameters map. If no associated
	 * parameter exists, null is returned.
	 * 
	 * @param name
	 *            the key of the parameter that should be found.
	 * @return the value of the parameter with the given key.
	 * @throws NullPointerException
	 *             if the name argument is null
	 */
	public String getTemporaryParameter(String name){
		Objects.requireNonNull(name, "The name is not allowed to be null!");
		
		return temporaryParameters.get(name);
	}
	
	/**
	 * Returns a read only set of names of all parameters in temporary
	 * parameters map.
	 * 
	 * @return a read only set of names of all parameters in temporary
	 *         parameters map.
	 */
	public Set<String> getTemporaryParameterNames(){
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	/**
	 * Adds the given value to the temporary parameters map under the specified
	 * name. The name is not allowed to be null. If the map previously contained
	 * a mapping for given name, the old value is replaced by the given value.
	 * 
	 * @param name
	 *            the name of the value that should be added.
	 * @param value
	 *            the value to be added to the map.
	 * @throws NullPointerException
	 *             if the name is null
	 */
	public void setTemporaryParameter(String name, String value){
		Objects.requireNonNull(name,  "The name is not allowed to be null!");
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Removes the value from temporary parameters map for the given name. If
	 * there is no value stored for the given name, nothing will happen.
	 * 
	 * @param name
	 *            the name of the value that should be removed.
	 * @throws NullPointerException
	 *             if the name is null.
	 */
	public void removeTemporaryParameter(String name){
		Objects.requireNonNull(name, "The name is not allowed to be null!");
		persistentParameters.remove(name);
	}
	
	/**
	 * Adds the given {@link RCCookie} to the list of output cookies.
	 * 
	 * @param rcCookie
	 *            the {@link RCCookie} to be added.
	 * @throws NullPointerException
	 *             if the given {@link RCCookie} is null.
	 */
	public void addRCCookie(RCCookie rcCookie) {
		Objects.requireNonNull(rcCookie, "The cookie is not allowed to be null!");
		outputCookies.add(rcCookie);
	}
	
	/**
	 * Sets the expected document length to the new value. If the value is
	 * different than -1, it will be printed in the header. Once the header is
	 * generated, it is not allowed to change the document length anymore.
	 * 
	 * @param documentLength the expected document length
	 */
	public void setDocumentLength(int documentLength) {
		checkHeader();
		
		this.documentLength = documentLength;
	}

	/**
	 * Writes the given byte array to the {@link #outputStream}. When the method
	 * is called for the first time, it will also write the header of the
	 * document, using {@link #writeHeader()}.
	 * 
	 * @param data
	 *            the byte array that should be written to the output stream.
	 * @return a reference to this {@link RequestContext}.
	 * @throws IOException
	 *             if there is an error with the output stream.
	 */
	public RequestContext write(byte[] data) throws IOException{
		writeHeader();
		
		outputStream.write(data);
		return this;
	}
	
	/**
	 * Writes the given string to the {@link #outputStream}, using the charset
	 * encoding defined in {@link #charset}. When the method is called for the
	 * first time, it will also write the header of the document, using
	 * {@link #writeHeader()}.
	 * 
	 * @param text
	 *            the string that should be written to the output stream.
	 * @return a reference to this {@link RequestContext}.
	 * @throws IOException
	 *             if there is an error with the output stream.
	 */
	public RequestContext write(String text) throws IOException{
		writeHeader();
		outputStream.write(text.getBytes(charset));
		return this;
	}
	
	/**
	 * Writes the header of the HTML document. If the header has already been
	 * written, nothing will happen. The header contains the http version,
	 * status code and status message in the first line, content type in the
	 * second line, and all the cookies in the following lines. The header ends
	 * with a blank line. The header is written using
	 * {@link StandardCharsets#ISO_8859_1} charset.
	 * 
	 * @throws IOException
	 *             if there is an error with the output stream.
	 */
	private void writeHeader() throws IOException{
		if (headerGenerated == true){
			return;
		}
		headerGenerated = true;
		
		charset = StandardCharsets.ISO_8859_1;
		
		write("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		write("Server: SmartHttpServer\r\n");
		
		write("Content-Type: " + mimeType);
		if (mimeType.startsWith("text/")){
			write("; charset=" + encoding);
		}
		write("\r\n");
		
		if (documentLength != -1){
			write("Content-Length: " + documentLength + "\r\n");
		}
		
		for (RCCookie cookie : outputCookies){
			write("Set-Cookie: ");
			write(cookie.toString());
			write("\r\n");
		}
		
		write("Connection: close\r\n");
		write("\r\n");
		
		
		charset = Charset.forName(encoding);
	}

	/**
	 * Defines a web cookie and all it's data.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 *
	 */
	public static class RCCookie {
		/**
		 * The cookie name.
		 */
		private final String name;
		/**
		 * The cookie value.
		 */
		private final String value;
		/**
		 * The domain for the cookie.
		 */
		private final String domain;
		/**
		 * The path of the cookie.
		 */
		private final String path;
		/**
		 * The maximal age of the cookie.
		 */
		private final int maxAge;

		/**
		 * Creates a new {@link RCCookie} with the values from the arguments.
		 * 
		 * @param name
		 *            the name of the cookie.
		 * @param value
		 *            the cookie value.
		 * @param maxAge
		 *            the maximal age of the cookie.
		 * @param domain
		 *            the domain for the cookie.
		 * @param path
		 *            the path of the cookie.
		 * 
		 * @throws NullPointerException
		 *             if the cookie name or value are null.
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			Objects.requireNonNull(name, "The cookie name is not allowed to be null!");
			Objects.requireNonNull(value, "The cookie value is not allowed to be null!");
			
			this.name = name;
			this.value = value;
			this.maxAge = maxAge == null ? -1 : maxAge;
			this.domain = domain;
			this.path = path;
		}

		/**
		 * Returns the name of the cookie.
		 * 
		 * @return the name of the cookie.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns the value of the cookie.
		 * 
		 * @return the value of the cookie.
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Returns the domain of the cookie.
		 * 
		 * @return the domain of the cookie.
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Returns the path of the cookie.
		 * 
		 * @return the path of the cookie.
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Returns the maximal age of the cookie.
		 * 
		 * @return the maximal age of the cookie.
		 */
		public int getMaxAge() {
			return maxAge;
		}
		
		@Override
		public String toString() {
			StringBuilder string = new StringBuilder();
			string.append(name).append("=\"");
			string.append(value).append("\"");
			
			if (domain != null){
				string.append("; Domain=");
				string.append(domain);
			}
			
			if (path != null){
				string.append("; Path=");
				string.append(path);
			}
			
			if (maxAge != -1){
				string.append("; Max-Age=");
				string.append(maxAge);
			}
			
			if (name.equals("sid")){
				string.append("; HttpOnly");
			}
			
			return string.toString();
		}
	}
}
