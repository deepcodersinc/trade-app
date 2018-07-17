package com.dci.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropertyUtil {
	
	static HashMap<String, String> propCache = new HashMap<String, String>();
	
	public void loadProperties(String filename) {
		try {
			Properties props = new Properties();		
			InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
			props.load(stream);
			propCache.putAll(props.entrySet().stream()
								.collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
		
	public static String getValue(String key) {		
		return propCache.get(key);
	}	
}
