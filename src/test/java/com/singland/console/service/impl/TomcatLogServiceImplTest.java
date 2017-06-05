package com.singland.console.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.exception.J4pException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.singland.console.service.ITomcatLogService;
import com.singland.console.vo.TomcatLogFileVo;
import com.singland.console.vo.TomcatLogVo;
import static org.testng.AssertJUnit.*;

public class TomcatLogServiceImplTest {

	private static final String JOLOKIA_URL = "http://localhost:8080/jolokia";

	private ITomcatLogService service;

	@BeforeTest
	public void setup() {
		service = new TomcatLogServiceImpl();
	}

	@Test
	public void getTomcatLogs() throws MalformedObjectNameException, IOException, J4pException, InterruptedException {
		List<TomcatLogVo> list = service.getTomcatLogs(JOLOKIA_URL);
		assertNotNull(list);
	}

	@Test
	public void getTomcatLogFiles() throws MalformedObjectNameException, J4pException, MalformedURLException {
		List<TomcatLogFileVo> list = service.getTomcatLogFiles(JOLOKIA_URL);
		assertNotNull(list);
	}

	@Test
	public void printSingleLogFile() throws MalformedObjectNameException, J4pException {
		String s = service.printSingleLogFile("http://localhost:8080/jolokia",
				"D:/apache-tomcat-8.0.30/logs2/catalina.2016-11-30.log");
		System.out.println(s);
		assertNotNull(s);
	}

}
