package com.chuangwl.poiext.jdbc.utils;

public class DatabaseTypeHelper {
	
	private PropertiesType propertiesType=null;
	
	public DatabaseTypeHelper() {
		propertiesType=new PropertiesType();
	}
	
	public String getValue(String type){
		return propertiesType.getDatabaseTypes().get(type);
	}
	
	public String getDrverName(String type){
		String value=getValue(type);
		return value.split("&")[1];
	}
	
	public String getUrl(String type){
		String value=getValue(type);
		return value.split("&")[0];
	}
	
	public static void main(String[] args) {
		DatabaseTypeHelper databaseTypeHelper=new DatabaseTypeHelper();
		System.out.println(databaseTypeHelper.getDrverName("mysql"));
		System.out.println(databaseTypeHelper.getUrl("mysql"));
	}
}
