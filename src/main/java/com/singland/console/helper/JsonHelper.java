package com.singland.console.helper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

public class JsonHelper {

	public static Map<String, Object> parserToMap(String s) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = new JSONObject(s);
		Iterator<String> keys = json.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String value = json.get(key).toString();
			if (value.startsWith("{") && value.endsWith("}")) {
				map.put(key, parserToMap(value));
			} else {
				map.put(key, value);
			}
		}
		return map;
	}
}