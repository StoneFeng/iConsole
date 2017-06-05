package com.singland.console.quartz;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ScanResAvailabilityJobTest {
	
	private ScanResAvailabilityJob job = null;
	
	@BeforeTest
	public void setup() {
		job = new ScanResAvailabilityJob();
	}
	
	@AfterTest
	public void tearDown() {
		job = null;
	}
	
	@Test
	public void keepRecords() throws Exception {
		job.keepRecords();
	}

}
