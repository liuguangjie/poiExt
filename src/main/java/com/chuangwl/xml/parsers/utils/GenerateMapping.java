package com.chuangwl.xml.parsers.utils;

import java.io.Serializable;

public class GenerateMapping implements Serializable {

	private static final long serialVersionUID = 8105928260991522643L;
	
	private String[] clumns=null;
	
	private String[] tables=null;

	public String[] getClumns() {
		return clumns;
	}

	public void setClumns(String[] clumns) {
		this.clumns = clumns;
	}

	public String[] getTables() {
		return tables;
	}

	public void setTables(String[] tables) {
		this.tables = tables;
	}
	
}
