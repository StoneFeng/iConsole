package com.singland.console.helper;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

public class UrlHelperTest {
	
	@Test
	public void getUrlPrefix() {
		String url = "http://10.41.188.156:8080/jolokia";
		assertEquals("http://10.41.188.156:8080", UrlHelper.getUrlPrefix(url));
	}

}
