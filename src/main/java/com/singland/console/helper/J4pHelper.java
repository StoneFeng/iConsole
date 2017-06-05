package com.singland.console.helper;

import javax.management.MalformedObjectNameException;

import org.apache.http.impl.client.BasicCookieStore;
import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.jolokia.client.request.J4pSearchRequest;
import org.jolokia.client.request.J4pSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class J4pHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(J4pHelper.class);
	
	public static J4pSearchResponse getJ4pSearchResponse(String url, String reqStr) throws MalformedObjectNameException {
		J4pSearchResponse resp = null;
		try {
			resp = getJ4pClient(url).execute(new J4pSearchRequest(reqStr));
		} catch (J4pException e) {
			LOGGER.debug(String.format("We are unable to connect to %s, please start that tomcat", url));
		}
		return resp;
	}
	
	public static J4pReadResponse getJ4pReadResponse(String url, J4pReadRequest req) throws MalformedObjectNameException {
		J4pReadResponse resp = null;
		try {
			resp = getJ4pClient(url).execute(req);
		} catch (J4pException e) {
			LOGGER.debug(String.format("We are unable to connect to %s, please start that tomcat", url));
		}
		return resp;
	}
	
	public static J4pClient getJ4pClient(String url) {
		return J4pClient.url(url).connectionTimeout(100).expectContinue(false).tcpNoDelay(true).contentCharset("utf-8")
				.maxConnectionPoolTimeout(3000).maxTotalConnections(500).pooledConnections().socketBufferSize(8192)
				.socketTimeout(5000).cookieStore(new BasicCookieStore()).build();
	}

}
