package com.chuangwl.xml.parsers.model;

import java.io.Serializable;

public class ConnectionMapping implements Serializable {

	private static final long serialVersionUID = 7459811259221401245L;

	private String type;
	private String host;
	private String port;
	private String user;
	private String passwd;
	private String database;
	
	private String sql;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	@Override
	public String toString() {
		return "ConnectionMapping [type=" + type + ", host=" + host + ", port=" + port + ", user=" + user + ", passwd="
				+ passwd + ", database=" + database + ", sql=" + sql + "]";
	}
	
	
	
}
