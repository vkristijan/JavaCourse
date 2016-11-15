package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import static org.junit.Assert.*;

@SuppressWarnings("javadoc")
public class RequestContextTests {
	
	@Test
	public void test1(){
		String encoding = "ISO-8859-2";
		ByteArrayOutputStream os;
		os = new ByteArrayOutputStream();
		try {
			
			RequestContext rc = new RequestContext(
				os, 
				new HashMap<String, String>(), 
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>()
			);
			
			rc.setEncoding(encoding);
			rc.setMimeType("text/plain");
			rc.setStatusCode(205);
			rc.setStatusText("Idemo dalje");
			
			rc.write("Čevapčići i Šiščevapčići.");
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String expected = "HTTP/1.1 205 Idemo dalje\r\n" +
						"Server: SmartHttpServer\r\n" +
						"Content-Type: text/plain; charset=ISO-8859-2\r\n" +
						"Connection: close\r\n" + 
						"\r\n" +
						"Čevapčići i Šiščevapčići.";
		String actual = new String(os.toByteArray(), Charset.forName(encoding));
		
		assertEquals(expected, actual);
	}

	@Test
	public void test2(){
		String encoding = "UTF-8";
		ByteArrayOutputStream os;
		os = new ByteArrayOutputStream();
		try {
			RequestContext rc = new RequestContext(
				os,
				new HashMap<String, String>(), 
				new HashMap<String, String>(), 
				new ArrayList<RequestContext.RCCookie>()
			);
			
			rc.setEncoding(encoding);
			rc.setMimeType("text/plain");
			rc.setStatusCode(205);
			rc.setStatusText("Idemo dalje");
			
			rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/"));
			rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/"));
			
			rc.write("Čevapčići i Šiščevapčići.");
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String expected = "HTTP/1.1 205 Idemo dalje\r\n" +
						"Server: SmartHttpServer\r\n" +
						"Content-Type: text/plain; charset=UTF-8\r\n" + 
						"Set-Cookie: korisnik=\"perica\"; Domain=127.0.0.1; Path=/; Max-Age=3600\r\n" +
						"Set-Cookie: zgrada=\"B4\"; Path=/\r\n" +
						"Connection: close\r\n" + 
						"\r\n" +
						"Čevapčići i Šiščevapčići.";
		String actual = new String(os.toByteArray(), Charset.forName(encoding));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test3(){
		String encoding = "UTF-8";
		ByteArrayOutputStream os;
		os = new ByteArrayOutputStream();
		try {
			
			RequestContext rc = new RequestContext(
				os, 
				new HashMap<String, String>(), 
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>()
			);
			
			rc.setEncoding(encoding);
			rc.setMimeType("text/plain");
			rc.setStatusCode(205);
			rc.setStatusText("Idemo dalje");
			
			rc.write("Čevapčići i Šiščevapčići.");
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String expected = "HTTP/1.1 205 Idemo dalje\r\n" +
						"Server: SmartHttpServer\r\n" +
						"Content-Type: text/plain; charset=UTF-8\r\n" +
						"Connection: close\r\n" + 
						"\r\n" +
						"Čevapčići i Šiščevapčići.";
		String actual = new String(os.toByteArray(), Charset.forName(encoding));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test4(){
		Map<String, String> parameters = new HashMap<>();
		Map<String, String> persistentParameters = new HashMap<>();
		List<RCCookie> outputCookies = new ArrayList<>();
		
		RequestContext rc = new RequestContext(
			new ByteArrayOutputStream(), 
			parameters, 
			persistentParameters, 
			outputCookies
		);
		
		assertEquals(null, rc.getParameter("unexisting"));
		assertEquals(null, rc.getTemporaryParameter("unexisting"));

		RCCookie cookie = new RCCookie("cookie", "value", null, null, "/");
		rc.addRCCookie(cookie);
		
		rc.setPersistentParameter("param", "paramVal");
		rc.setTemporaryParameter("tParam", "tParamVal");
		
		assertEquals("paramVal", rc.getPersistentParameter("param"));
		assertEquals("tParamVal", rc.getTemporaryParameter("tParam"));
	}
	
	@Test
	public void test5(){
		Map<String, String> parameters = null;
		Map<String, String> persistentParameters = null;
		List<RCCookie> outputCookies = null;
		
		RequestContext rc = new RequestContext(
			new ByteArrayOutputStream(), 
			parameters, 
			persistentParameters, 
			outputCookies
		);
		
		assertNotNull(rc.getPersistentParameterNames());
		assertNotNull(rc.getParameterNames());
		assertNotNull(rc.getTemporaryParameterNames());
	}
}

