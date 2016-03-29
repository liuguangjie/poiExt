package com.chuangwl.test;

import java.io.File;
import java.util.Map.Entry;

import org.junit.Test;

import com.chuangwl.xml.parsers.XmlModelParser;

public class TestDocument {

	@Test
	public void testdoc() {
		XmlModelParser modelParser = new XmlModelParser(new File("src/main/resources/generate-sql.xml")).generteModel();
		System.out.println(modelParser.getTableAttrs());
		System.out.println();

		for (Entry<String, String> entry : modelParser.getFieldAttrs().entrySet()) {
			System.out.print(entry.getKey() + "    ");
			System.out.println(entry.getValue());
		}

	}
}
