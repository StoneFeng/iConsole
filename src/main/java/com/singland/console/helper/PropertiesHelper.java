package com.singland.console.helper;

import java.io.IOException;
import java.util.Properties;

import com.singland.console.service.impl.OsResourceProviderImpl;

public final class PropertiesHelper {
	
	private static Properties properties;
	
	static {
		properties = new Properties();
		try {
			properties.load(OsResourceProviderImpl.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Properties getProperties() {
		return properties;
	}
	
	public static boolean isAllowedWebModule(String webModule) {
		String filterStr = properties.getProperty("WEB_MODULE_FILTER");
		if (filterStr != null) {
			String [] filterArr = filterStr.split(",");
			if (filterArr != null && filterArr.length > 0) {
				for (String filter : filterArr) {
					if (webModule.contains(filter)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static String getHostDesc(String host) {
		String desc = null;
		desc = getProperties().getProperty(String.format("%s.desc", host));
		if (desc != null) {
			desc = desc.trim();
		} else {
			desc = "";
		}
		return desc;
	}
}
