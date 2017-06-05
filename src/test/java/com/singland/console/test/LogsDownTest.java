package com.singland.console.test;

import java.io.File;

import org.testng.annotations.Test;

public class LogsDownTest {
	
	@Test
	public void listFiles() {
		File dir = new File("D:\\apache-tomcat-8.0.30\\logs2");
		if (dir.isDirectory()) {
			File [] files = dir.listFiles();
			for (File f : files) {
				if (f.isFile()) {
					System.out.println(f.getAbsolutePath());
				}
			}
		}
	}

}
