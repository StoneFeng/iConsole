package com.singland.console.test;

import static org.testng.Assert.assertNotEquals;

import java.io.IOException;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class J4pClientTest extends J4pParentTest {

	public J4pReadRequest TEST_REQUEST, TEST_REQUEST_2;
	private J4pReadRequest PROCESS_CPU_LOAD_REQUEST;
	private J4pReadRequest FREE_PHYSICAL_MEMORY_SIZE_REQUEST;

	@BeforeTest
	public void setup() throws MalformedObjectNameException {
		TEST_REQUEST = new J4pReadRequest("java.lang:type=Memory", "HeapMemoryUsage");
		TEST_REQUEST_2 = new J4pReadRequest("java.lang:type=Memory", "NonHeapMemoryUsage");
		PROCESS_CPU_LOAD_REQUEST = new J4pReadRequest("java.lang:type=OperatingSystem", "ProcessCpuLoad");
		FREE_PHYSICAL_MEMORY_SIZE_REQUEST = new J4pReadRequest("java.lang:type=OperatingSystem",
				"FreePhysicalMemorySize");
	}

	@Test
	public void getProcessCpuLoad() throws MalformedObjectNameException, J4pException, IOException {
		J4pReadResponse resp = getJ4pClient().execute(PROCESS_CPU_LOAD_REQUEST);
		Object cpuLoad = resp.getValue("ProcessCpuLoad");
		getLogger().error("CPU使用率" + cpuLoad);
		assertNotEquals(0, cpuLoad);
	}
	
	@Test
	public void getSystemCpuLoad() throws Exception {
		J4pReadRequest req = new J4pReadRequest("java.lang:type=OperatingSystem", "SystemCpuLoad");
		J4pReadResponse resp = getJ4pClient().execute(req);
		Double cpuLoad = resp.getValue("SystemCpuLoad");
		cpuLoad = cpuLoad * 100;
		getLogger().error("CPU使用率" + cpuLoad + "%");
		assertNotEquals(0, cpuLoad);
	}

	@Test
	public void getFreePhysicalMemorySize() throws MalformedObjectNameException, J4pException, IOException {
		J4pReadResponse resp = getJ4pClient().execute(FREE_PHYSICAL_MEMORY_SIZE_REQUEST);
		Long mem = getGigabyteMemory((Long) resp.getValue("FreePhysicalMemorySize"));
		getLogger().error("可用物理内存" + mem + "G");
		assertNotEquals(0, mem);
	}
	
	@Test
	public void getTotalPhysicalMemorySize() throws Exception {
		J4pReadRequest req = new J4pReadRequest("java.lang:type=OperatingSystem", "TotalPhysicalMemorySize");
		J4pReadResponse resp = getJ4pClient().execute(req);
		Long mem = getGigabyteMemory((Long) resp.getValue("TotalPhysicalMemorySize"));
		getLogger().error("总共物理内存" + mem + "G");
		assertNotEquals(0, mem);
	}

	private Long getGigabyteMemory(Long bytes) {
		return bytes / 1073741824;
	}

}
