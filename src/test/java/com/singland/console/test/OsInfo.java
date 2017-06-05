package com.singland.console.test;

import java.util.Properties;

public class OsInfo {

	public static void main(String[] args) {
		Properties properties = System.getProperties();
		System.out.println("os.name "+properties.get("os.name"));
		System.out.println("os.arch "+properties.get("os.arch"));
		System.out.println("os.version "+properties.get("os.version"));

	}

}
