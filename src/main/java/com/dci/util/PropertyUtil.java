package com.dci.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public enum PropertyUtil {
	
	INSTANCE;
	
	FileInputStream stream;
	Properties props;
	String propertyFile;
		
	public String getValue(String key) {
		if(props == null) {
			try {
				String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
				String configPath = rootPath + propertyFile;
				stream = new FileInputStream(configPath);
				props = new Properties();
				props.load(stream);
				
			} catch(FileNotFoundException fnfe)	 {
				System.out.println(fnfe.getMessage());
			} catch(IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		}
		 
		return props.getProperty(key);
	}
	
	public void setPropertyFile(String fileName) {
		this.propertyFile = fileName;
	}
}
