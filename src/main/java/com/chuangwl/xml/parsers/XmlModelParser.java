package com.chuangwl.xml.parsers;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.chuangwl.xml.parsers.utils.GenerateMapping;

public class XmlModelParser {

	private GenerateMapping mapping = null;
	private GenerateParser parser = null;
	private InputStream inputStream = null;
	private File file = null;
	private LinkedHashMap<String, String> tableAttrs = null;
	private LinkedHashMap<String, String> fieldAttrs=null;

	public XmlModelParser() {
		mapping = new GenerateMapping();
		parser = new GenerateParser();
	}

	public XmlModelParser(File file) {
		this();
		this.file = file;
	}

	public XmlModelParser(InputStream inputStream) {
		this();
		this.inputStream = inputStream;
	}

	public XmlModelParser generteModel() {
		try {
			Document document = parser.xmlParse(file);
			NodeList nodeLists = document.getElementsByTagName("table");
			Node tableNode = nodeLists.item(0);
			tableAttrs = new LinkedHashMap<String, String>(1);
			NamedNodeMap namedNodeMap = tableNode.getAttributes();
			int attrCount = namedNodeMap.getLength();
			if (tableNode.hasAttributes() && namedNodeMap != null) {
				for (int i = 0; i < attrCount; i++) {
					Node attrNode = namedNodeMap.item(i);
					tableAttrs.put(attrNode.getNodeName(), attrNode.getNodeValue());
				}
			}
			
			Document childDocument=tableNode.getOwnerDocument();
			NodeList childNodeList=childDocument.getElementsByTagName("column");
			int childLength=childNodeList.getLength();
			fieldAttrs=new LinkedHashMap<String, String>(childLength);
			for(int j=0;j<childLength;j++){
				Node childNode=childNodeList.item(j);
				NamedNodeMap childNamedNode=childNode.getAttributes();
				int childAttrCount=childNamedNode.getLength();
				if(childNamedNode!=null&&childNode.hasAttributes()){
					for (int i = 0; i < childAttrCount; i++) {
						Node childAttrNode = childNamedNode.item(i);
						fieldAttrs.put(childAttrNode.getNodeValue(),childAttrNode.getNodeName());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		return this;
	}

	public GenerateMapping getMapping() {
		return mapping;
	}
	
	public LinkedHashMap<String, String> getTableAttrs() {
		return tableAttrs;
	}
	
	public LinkedHashMap<String, String> getFieldAttrs() {
		return fieldAttrs;
	}

}
