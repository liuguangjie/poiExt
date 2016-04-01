package com.chuangwl.xml.parsers;

import java.io.File;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ConnectionParser {
	private GenerateParser generateParser = null;
	private File file = null;
	private InputStream inputStream = null;

	public ConnectionParser() {
		generateParser = new GenerateParser();
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
	public void generateConnectionMapping() {
		Document document=generateDocument();
		Node parentNode=document.getElementsByTagName("connections").item(0);
		Document childNode=parentNode.getOwnerDocument();
		Node node=childNode.getElementsByTagName("connection").item(0);
		//获取connection节点 属性值
		NamedNodeMap namedNodeMap=node.getAttributes();
		int attrs=namedNodeMap.getLength();
		for (int i = 0; i < attrs; i++) {
			namedNodeMap.item(i);
		}
		
		
	}

	private void getNodeValue(Node node) {
		
		
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
	
	public static void main(String[] args) {
		
		InputStream in=ConnectionParser.class.getClassLoader().getResourceAsStream("sql-convert-excle.xml");
		ConnectionParser connectionParser=new ConnectionParser(in);
		connectionParser.generateConnectionMapping();
		
		
	}

}
