package com.chuangwl.poiext.jdbc.utils;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class PropertiesType {
	
	private List<String> databaseTypes=null;
	
	private Properties properties=null;
	
	public PropertiesType() {
		properties=new Properties();
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream(""));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
