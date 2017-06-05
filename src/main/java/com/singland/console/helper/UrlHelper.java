package com.singland.console.helper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public final class UrlHelper {

	public static List<String> parseRemoteJolokiaUrl(String key) throws IOException {
		String valueList = PropertiesHelper.getProperties().getProperty(key);
		String [] arr = valueList.split(",");
		return Arrays.asList(arr);
	}	
	
	public static String getHost(String url) {
		int start = url.indexOf("http://");
		int end = url.lastIndexOf(":");
		return url.substring(start + 7, end);
	}
	
	public static String getUrlPrefix(String url) {
		return url.substring(0, url.lastIndexOf("/"));
	}
	
}
