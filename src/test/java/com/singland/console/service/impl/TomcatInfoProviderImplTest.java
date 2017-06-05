package com.singland.console.service.impl;

import java.io.IOException;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.exception.J4pException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.singland.console.service.ITomcatService;

public class TomcatInfoProviderImplTest {
	
	private ITomcatService provider;
	
	@BeforeTest
	public void setup() {
		provider = new TomcatServiceImpl();
	}
	
	@Test
	public void getTomcatInfo() throws MalformedObjectNameException, IOException, J4pException, InterruptedException {
		provider.getTomcatInfo();
	}

}
