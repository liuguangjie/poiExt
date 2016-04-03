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
		if(value!=null&&value.split("&").length>=2){
			return value.split("&")[1];
		}
		return "";
	}
	
	public String getUrl(String type){
		String value=getValue(type);
		if(value!=null&&value.split("&").length>=2){
			return value.split("&")[0];
		}
		return "";
	}
	
	public static void main(String[] args) {
		DatabaseTypeHelper databaseTypeHelper=new DatabaseTypeHelper();
		System.out.println(databaseTypeHelper.getDrverName("mysql"));
		System.out.println(databaseTypeHelper.getUrl("mysql"));
	}
}
