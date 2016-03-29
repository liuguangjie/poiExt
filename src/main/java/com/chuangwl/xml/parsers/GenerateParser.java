package com.chuangwl.xml.parsers;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class GenerateParser {
	private DocumentBuilder builder = null;
	private DocumentBuilderFactory builderFactory;

	public GenerateParser() {
		builderFactory = DocumentBuilderFactory.newInstance();
		try {
			builder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public Document xmlParse(File file) throws Exception {
		return builder.parse(file);
	}

	public Document xmlParse(InputStream inputStream) throws Exception {
		return builder.parse(inputStream);
	}
	

}
