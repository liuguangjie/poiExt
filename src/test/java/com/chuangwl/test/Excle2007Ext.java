package com.chuangwl.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.chuangwl.xml.parsers.model.XmlModelParser;

public class Excle2007Ext {
	private XmlModelParser modelParser = null;

	public Excle2007Ext(XmlModelParser modelParser) {
		this.modelParser = modelParser;
	}

	// 生成建表语句 和 生成insert语句 到一个default.sql中
	public void GenerateSqlFile(File excleFile) throws Exception {
		Map<String, String> tableAttrs = modelParser.getTableAttrs();
		Map<String, String> fieldAttrs = modelParser.getFieldAttrs();
		String tableName = tableAttrs.get("name");
		String fileName = "default.sql";
		//用来拼接 字段  列如:name1,name2,name3,name4,name5,name6,name7
		//为下面 insert into 作准备
		String clumns="";
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("CREATE TABLE `"+tableName+"` (  \n");
		stringBuilder.append("`ID` int(11) NOT NULL AUTO_INCREMENT, \n");
		Set<String> keySet=fieldAttrs.keySet();
		int keyCount=keySet.size();
		for (String key : keySet){
			keyCount--;
			stringBuilder.append("`"+key+"` varchar(255) COLLATE utf8_bin DEFAULT NULL, \n");
			if(keyCount==0){
				stringBuilder.append(" PRIMARY KEY (`ID`), UNIQUE KEY `ID` (`ID`) \n");
				clumns=clumns+key;
			}else{
				clumns=clumns+key+",";
			}
			
		}
//		System.out.println(clumns);
		stringBuilder.append(" ) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC; \n");
		stringBuilder.append("-- ---------------------------- \n");
		stringBuilder.append("-- Records of course \n");
		stringBuilder.append("-- ---------------------------- \n");
		System.out.println(stringBuilder.toString());
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(excleFile));
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		// 用来装一行的内容
		Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		int counter = 0;
		// 获取 一共有多少行
		int rows = sheet.getPhysicalNumberOfRows();
		//System.out.println("["+rows+"]");
		// 获取第一行
		int startRow = sheet.getFirstRowNum();
		int j;
		keyCount=keySet.size();
		for (int i = startRow; counter < rows; i++) {
			row = sheet.getRow(i);
			if (null == row)
				continue;
			counter++;
			// 一行的列是开始角标
			int firstCell = row.getFirstCellNum();
			// 一行的列的最多的个数
			int laseCell = row.getLastCellNum();
			//这里的值用 我们在map中的key个数而key值正好是数据库的字段名称
			j=0;
			//拼接sql语句
			stringBuilder.append("INSERT INTO "+tableName+" ("+clumns+") ");
			stringBuilder.append("values (");
			for(String key : keySet){
				cell = row.getCell(j);
				j++;
				if(null==cell) continue;
				// 判断列的内容类型
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					System.out.print(value+"  ");
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					value = cell.getRawValue();
					System.out.print(value+"  ");
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue();
					System.out.print(value+"  ");
					break;
				// 这一列为空
				case XSSFCell.CELL_TYPE_BLANK:
					value = "";
					System.out.print(value+"  ");
					break;
				default:
					value = cell.toString();
					System.out.print(value+"  ");
				}
				
				if(j>=keyCount){
					stringBuilder.append("'"+value+"'");
				}else{
					stringBuilder.append("'"+value+"',");
				}
				
				if (value == null || "".equals(value)) {
					continue;
				}
			}
			stringBuilder.append(" ); \n");
			System.out.println(stringBuilder.toString());
			/*for (int j = firstCell; j < laseCell; j++) {
				cell = row.getCell(j);
				if(null==cell) continue;
				// 判断列的内容类型
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					System.out.print(value+"  ");
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					value = cell.getRawValue();
					System.out.print(value+"  ");
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue();
					System.out.print(value+"  ");
					break;
				// 这一列为空
				case XSSFCell.CELL_TYPE_BLANK:
					value = "";
					System.out.print(value+"  ");
					break;
				default:
					value = cell.toString();
					System.out.print(value+"  ");
				}
				if (value == null || "".equals(value)) {
					continue;
				}
			}*/
			System.out.println();
		}
		//把StringBuilder写到磁盘上
		writeDisk(stringBuilder, fileName);
		
		
	}
	public void writeDisk(StringBuilder stringBuilder,String fileName){
		String path="/home/reet/"+fileName;
		try {
			FileOutputStream out =new FileOutputStream(new File(path));
			out.write(stringBuilder.toString().getBytes());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	public static void main(String[] args) throws Exception {
		new Excle2007Ext(new XmlModelParser(new File("src/main/resources/generate-sql.xml")).generteModel())
				.GenerateSqlFile(new File("/home/reet/Desktop/library.xlsx"));
		;

	}

}
