package com.chuangwl.poiext.jdbc.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PropertiesType {
	
	private Map<String,String> databaseTypes=null;
	
	private Properties properties=null;
	
	/*static {
		properties=new Properties();
		try {
			properties.load(PropertiesType.class.getClassLoader().getResourceAsStream("public_JDBC.properties"));
			databaseTypes=new HashMap<String, String>();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}*/
	public PropertiesType() {
		properties=new Properties();
		try {
			
			properties.load(this.getClass().getClassLoader().getResourceAsStream("public_JDBC.properties"));
			databaseTypes=new HashMap<String, String>(properties.size());
			Enumeration<Object> enumeration=properties.keys();
			String key=null;
			while(enumeration!=null&&enumeration.hasMoreElements()){
				key=(String) enumeration.nextElement();
				databaseTypes.put(key, properties.getProperty(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setDatabaseTypes(Map<String, String> databaseTypes) {
		this.databaseTypes = databaseTypes;
	}
	
	public Map<String, String> getDatabaseTypes() {
		return databaseTypes;
	}
	
	
	public static void main(String[] args) {
		PropertiesType propertiesType=new PropertiesType();
		System.out.println(propertiesType.getDatabaseTypes().get("mysql"));
	}
	
}
