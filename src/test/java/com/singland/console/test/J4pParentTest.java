package com.singland.console.test;

import javax.management.MalformedObjectNameException;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pSearchRequest;
import org.jolokia.client.request.J4pSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class J4pParentTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(J4pParentTest.class);

	private static final String TEST_URL = "http://localhost:8080/jolokia";
	
	protected static Logger getLogger() {
		return LOGGER;
	}
	
	protected J4pClient getJ4pClient() {
		return new J4pClient(TEST_URL, getHttpClient());
	}
	
	protected J4pSearchResponse getJ4pSearchResponse(String reqStr) throws MalformedObjectNameException, J4pException {
		J4pSearchRequest req = new J4pSearchRequest(reqStr);
		return getJ4pClient().execute(req);
	}

	private HttpClient getHttpClient() {
		J4pClient client = J4pClient.url("http://localhost:8080/jolokia").connectionTimeout(100).expectContinue(false)
				.tcpNoDelay(true).contentCharset("utf-8").maxConnectionPoolTimeout(3000).maxTotalConnections(500)
				.pooledConnections().socketBufferSize(8192).socketTimeout(5000).cookieStore(new BasicCookieStore())
				.build();
		return client.getHttpClient();
	}

}
