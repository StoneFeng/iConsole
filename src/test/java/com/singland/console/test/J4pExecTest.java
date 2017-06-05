package com.singland.console.test;

import java.util.List;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pExecRequest;
import org.jolokia.client.request.J4pExecResponse;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.jolokia.client.request.J4pSearchResponse;
import org.testng.annotations.Test;

public class J4pExecTest extends J4pParentTest {
	
	@Test
	public void getWebModule() throws MalformedObjectNameException, J4pException {
		J4pSearchResponse resp = getJ4pSearchResponse("*:j2eeType=WebModule,*");
		List<String> list = resp.getMBeanNames();
		for (String name : list) {
			getLogger().error(name);
			
			J4pReadResponse response = getJ4pClient().execute(new J4pReadRequest(name.toString(), new String [] {"path","stateName","displayName","startTime"}));
			getLogger().error("WebModule state: " + response.getValue("stateName"));//1.State
			
			if (name.contains("docs")) {
				J4pExecRequest execReq = new J4pExecRequest(name, "stop");
				J4pExecResponse execResp = getJ4pClient().execute(execReq);
				System.out.println(execResp.asJSONObject());
			}
//			getLogger().error("WebModule path: " + response.getValue("path"));//2.Context-Path
//			getLogger().error("WebModule displayName: " + response.getValue("displayName"));//3.Display Name
//			getLogger().error("WebModule startTime: " + response.getValue("startTime"));//5.Start Time
			
		}
	}	

}
