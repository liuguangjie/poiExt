package com.chuangwl.poiext.jdbc.utils;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import com.chuangwl.xml.parsers.ConnectionParser;
import com.chuangwl.xml.parsers.model.ConnectionMapping;

public class ConnectionTool {
	
	private ConnectionMapping connectionMapping=null;
	
	private ConnectionParser connectionParser=null;
	
	private File file=null;
	private InputStream inputStream=null;
	
	public ConnectionTool(File file) {
		connectionParser=new ConnectionParser(file);
		connectionMapping=new ConnectionMapping();
	}
	public ConnectionTool(InputStream inputStream) {
		connectionParser=new ConnectionParser(inputStream);
		connectionMapping=new ConnectionMapping();
	}
	
	//url    String url="jdbc:mysql://localhost:3306/sample_db?user=root&password=your_password";
	// 获取连接
	public Connection getConnection(){
		setConnectionMapping();
		DatabaseTypeHelper typeHelper=new DatabaseTypeHelper();
		String type=connectionMapping.getType();
		String drverName=typeHelper.getDrverName(type);
		//注册 驱动
		register(drverName);
		
		String url=typeHelper.getUrl(type)+connectionMapping.getHost()+
				":"+connectionMapping.getPort()+"/"+connectionMapping.getDatabase();
		try {
			return DriverManager.getConnection(url,connectionMapping.getUser(), connectionMapping.getPasswd() );
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	private void register(String drverName) {
		try {
			Class.forName(drverName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setConnectionMapping(){
		Map<String, String> info=connectionParser.generateConnectionMapping();
		System.out.println(info);
		Field fields[]=connectionMapping.getClass().getDeclaredFields();
		for ( Field field:fields){
			if(!Modifier.isFinal(field.getModifiers())){
				field.setAccessible(true);
				try {
					field.set(connectionMapping, info.get(field.getName()));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public void setConnectionMapping(ConnectionMapping connectionMapping) {
		this.connectionMapping = connectionMapping;
	}
	
	public ConnectionMapping getConnectionMapping() {
		return connectionMapping;
	}
	
	
	public static void main(String[] args) {
		
		InputStream inputStream=ConnectionTool.class.getClassLoader().getResourceAsStream("sql-convert-excle.xml");
		System.out.println(inputStream);
		ConnectionTool connectionTool=new ConnectionTool(inputStream);
		System.out.println(connectionTool.getConnection());
		
		
	}
}
