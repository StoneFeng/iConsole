package com.singland.console.test;

import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pExecRequest;
import org.jolokia.client.request.J4pExecResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.commons.lang3.StringEscapeUtils;

public class MyMbeanTest {
	
	@SuppressWarnings("unchecked")
	@Test
	public void printHi() throws MalformedObjectNameException, J4pException {
		J4pClient client = new J4pClient("http://localhost:8080/jolokia");
		J4pExecRequest req = new J4pExecRequest("iesb.mbean:type=TomcatLog", 
				"listTomcatLogFiles", new Object[] {"apache-tomcat-8.0.30"});
		J4pExecResponse resp = client.execute(req);
		System.out.println(resp.asJSONObject());
		JSONArray jsonArray = resp.getValue();
		Iterator<JSONObject> iter = jsonArray.iterator();
		while (iter.hasNext()) {
			JSONObject jsonLog = iter.next();
			System.out.println(jsonLog.get("logName"));
			System.out.println(jsonLog.get("absolutePath"));
		}
	}
	
	@Test
	public void getDir() {
		String dir = "/opt/esb/server/tomcat1/logs/localhost.2017-01-16.log";
		assertEquals(dir.substring(0, dir.lastIndexOf("/")), "/opt/esb/server/tomcat1/logs");
	}
	
	@Test
	public void getPort() throws MalformedURLException {
		URL url = new URL("http://10.41.190.65:8081/hawtio/jolokia");
		assertEquals(url.getPort(), 8081);
	}
	
	@Test
	public void  doubleCompareTo() {
		Double maxCpu = 80.00;
		Double realCpu = 80.01;
		System.out.println(maxCpu.compareTo(realCpu));
	}
	
	@Test
	public void dived() {
		Long used = 10634280960L;
		Long total = 33018748928L;
		BigDecimal usedB = new BigDecimal(used);
		BigDecimal totalB = new BigDecimal(total);
		
		System.out.println(usedB.divide(totalB,2, BigDecimal.ROUND_HALF_EVEN));
	}
	
	@Test
	public void getSysUserName() {
		Properties prop = System.getProperties();
		assertEquals(prop.getProperty("user.name"), "fWX425430");
	}
	
	@Test
	public void getLogFileName() {
		String path = "/opt/esb/server/tomcat1/logs/catalina.out";
		File f = new File(path);
		assertEquals(f.getName(), "catalina.out");
	}
	
	@Test
	public void escape() {
		System.out.println(StringEscapeUtils.escapeHtml4("[serviceId<S0000000000020280"));
	}
	
	@Test
	public void ganymedTest() {
		String hostname = "10.41.178.163";
		String username = "root";
		String password = "F6Nru3Q3uUWa$$";

		try
		{
			/* Create a connection instance */

			Connection conn = new Connection(hostname);

			/* Now connect */

			conn.connect();

			/* Authenticate.
			 * If you get an IOException saying something like
			 * "Authentication method password not supported by the server at this stage."
			 * then please check the FAQ.
			 */

			boolean isAuthenticated = conn.authenticateWithPassword(username, password);

			if (isAuthenticated == false)
				throw new IOException("Authentication failed.");

			/* Create a session */

			Session sess = conn.openSession();

			sess.execCommand("uname -a && date && uptime && who");

			System.out.println("Here is some information about the remote host:");

			/* 
			 * This basic example does not handle stderr, which is sometimes dangerous
			 * (please read the FAQ).
			 */

			InputStream stdout = new StreamGobbler(sess.getStdout());

			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

			while (true)
			{
				String line = br.readLine();
				if (line == null)
					break;
				System.out.println(line);
			}

			/* Show exit status, if available (otherwise "null") */

			System.out.println("ExitCode: " + sess.getExitStatus());

			/* Close this session */

			sess.close();

			/* Close the connection */

			conn.close();

		}
		catch (IOException e)
		{
			e.printStackTrace(System.err);
			System.exit(2);
		}
	}

}
