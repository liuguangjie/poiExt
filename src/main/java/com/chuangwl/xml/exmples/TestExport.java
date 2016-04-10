package com.chuangwl.xml.exmples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.chuangwl.poiext.export.ExportExcle;
import com.chuangwl.poiext.jdbc.utils.ConnectionTool;

public class TestExport {
	public static void main(String[] args) throws Exception {
		InputStream inputStream = ConnectionTool.class.getClassLoader().getResourceAsStream("sql-convert-excle.xml");
		ConnectionTool connectionTool = new ConnectionTool(inputStream);
		List<Object[]> dataSet = connectionTool.excutionSql();
		ExportExcle exportExcle = new ExportExcle();
		OutputStream out = new FileOutputStream(new File("D:/test3.xlsx"));
		exportExcle.export(dataSet, out);
	}
}
