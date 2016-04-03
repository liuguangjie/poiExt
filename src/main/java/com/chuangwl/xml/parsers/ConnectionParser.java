package com.chuangwl.xml.parsers;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.chuangwl.xml.parsers.model.ConnectionMapping;

public class ConnectionParser {
	private GenerateParser generateParser = null;
	private File file = null;
	private InputStream inputStream = null;
	
	private ConnectionMapping connectionMapping=null;
	
	private Map<String, String> databaseConnectionInfo=null;
	
	private Field fields[]=null;

	public ConnectionParser() {
		generateParser = new GenerateParser();
		connectionMapping=new ConnectionMapping();
		fields=connectionMapping.getClass().getDeclaredFields();
		databaseConnectionInfo=new HashMap<String, String>();
	}

	public ConnectionParser(File file) {
		this();
		this.file = file;
	}

	public ConnectionParser(InputStream inputStream) {
		this();
		this.inputStream = inputStream;
	}

	public Document generateDocument() {
		Document document=null;
		try {
			if(file!=null)
			document=generateParser.xmlParse(file);
			if(null==document&&inputStream!=null)
				document=generateParser.xmlParse(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
	public Map<String,String> generateConnectionMapping() {
		Document document=generateDocument();
		Node parentNode=document.getElementsByTagName("connections").item(0);
		Document childDocument=parentNode.getOwnerDocument();
		Node node=childDocument.getElementsByTagName("connection").item(0);
		//获取connection节点 属性值  解析 <connection type="mysql" host="localhost" port="3306">
		NamedNodeMap namedNodeMap=node.getAttributes();
		int attrs=namedNodeMap.getLength();
		for (int i = 0; i < attrs; i++) {
			Node nodeAttrs=namedNodeMap.item(i);
			databaseConnectionInfo.put(nodeAttrs.getNodeName(), nodeAttrs.getNodeValue().trim());
		}
		//解析导出路径
		String exportPath=childDocument.getElementsByTagName("export-path").item(0).getTextContent().trim();
		databaseConnectionInfo.put("exportPath", exportPath);
		
		//解析sql语句
		String sql=childDocument.getElementsByTagName("sql").item(0).getTextContent().trim();
		databaseConnectionInfo.put("sql", sql);
		
		//这里还要该 不够好 还需要改
		childDocument=node.getOwnerDocument();
		String database=childDocument.getElementsByTagName("database").item(0).getTextContent();
		String user=childDocument.getElementsByTagName("user").item(0).getTextContent();
		String passwd=childDocument.getElementsByTagName("passwd").item(0).getTextContent();
		databaseConnectionInfo.put("database",database.trim());
		databaseConnectionInfo.put("user",user.trim());
		databaseConnectionInfo.put("passwd",passwd.trim());
		
		return databaseConnectionInfo;
	}

	
	
	//没有 用的
	public void setValue(Node nodeAttrs) {
		String nodeName=nodeAttrs.getNodeName();
		for (Field field:fields){
			if(field.getName().equalsIgnoreCase(nodeName)){
				try {
					field.setAccessible(true);
					field.set(connectionMapping, nodeAttrs.getNodeValue());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (DOMException e) {
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
	public void setDatabaseConnectionInfo(Map<String, String> databaseConnectionInfo) {
		this.databaseConnectionInfo = databaseConnectionInfo;
	}
	
	public Map<String, String> getDatabaseConnectionInfo() {
		return databaseConnectionInfo;
	}
	
	public static void main(String[] args) {
		
		InputStream in=ConnectionParser.class.getClassLoader().getResourceAsStream("sql-convert-excle.xml");
		ConnectionParser connectionParser=new ConnectionParser(in);
		connectionParser.generateConnectionMapping();
		System.out.println(connectionParser.databaseConnectionInfo);
	}

}
