package com.singland.console.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

import java.util.Iterator;
import java.util.List;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pExecRequest;
import org.jolokia.client.request.J4pExecResponse;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.jolokia.client.request.J4pSearchResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.singland.console.helper.J4pHelper;

public class J4pSearchTest extends J4pParentTest {
	
	@Test
	public void getServerInfo() throws MalformedObjectNameException, J4pException {
		J4pSearchResponse resp = getJ4pSearchResponse("*:type=Server");
		List<String> names = resp.getMBeanNames();
		assertEquals(1, names.size());
		for (String name : names) {
			J4pReadResponse response = getJ4pClient().execute(new J4pReadRequest(name, "serverInfo"));
			assertEquals("Apache Tomcat/8.0.30", response.getValue("serverInfo"));
		}
	}
	
	@Test
	public void getConnector() throws MalformedObjectNameException, J4pException {
		J4pSearchResponse resp = getJ4pSearchResponse("*:type=Connector,*");
		List<String> names = resp.getMBeanNames();
		for (String name : names) {
			J4pReadResponse response = getJ4pClient().execute(new J4pReadRequest(name, new String [] {"port","scheme","protocol"}));
			getLogger().error("Connector port: " + response.getValue("port"));
			getLogger().error("Connector scheme: " + response.getValue("scheme"));
			getLogger().error("Connector protocol: " + response.getValue("protocol"));
		}
	}
	
	@Test
	public void getWebModule() throws MalformedObjectNameException, J4pException {
		J4pSearchResponse resp = getJ4pSearchResponse("*:j2eeType=WebModule,*");
		List<String> list = resp.getMBeanNames();
		for (String name : list) {
			J4pReadResponse response = getJ4pClient().execute(new J4pReadRequest(name, new String [] {"path","stateName","displayName","startTime"}));
			assertNotNull(response.getValue("stateName"));
			assertNotNull(response.getValue("path"));
			assertNotNull(response.getValue("displayName"));
			assertNotNull(response.getValue("startTime"));
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void findLogQueryMBean() throws MalformedObjectNameException, J4pException {
		J4pSearchResponse resp = getJ4pSearchResponse("*:type=LogQuery,*");
		List<String> names = resp.getMBeanNames();
		for (String name : names) {
			J4pExecRequest execReq = new J4pExecRequest(name, "allLogResults");
			J4pExecResponse execResp = J4pHelper.getJ4pClient("http://10.41.188.156:8080/jolokia").execute(execReq);
			JSONObject jsonObject = execResp.getValue();
			System.out.println("ALL: " + jsonObject.toJSONString());
			JSONArray jsonArray = (JSONArray) jsonObject.get("events");
			System.out.println("EVENTS: " + jsonArray.toJSONString());
			Iterator<JSONObject> iter = jsonArray.iterator();
			while (iter.hasNext()) {
				JSONObject jsonObject2 = iter.next();
				System.out.println(jsonObject2.get("message"));
			}
			
		}
	}

}
