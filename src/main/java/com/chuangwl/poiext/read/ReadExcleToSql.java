package com.chuangwl.poiext.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.chuangwl.xml.parsers.model.XmlModelParser;

public class ReadExcleToSql {
	private XmlModelParser modelParser = null;

	public ReadExcleToSql(XmlModelParser modelParser) {
		this.modelParser = modelParser;
	}

	// 生成建表语句 和 生成insert语句 到一个default.sql中
	public void GenerateSqlFile(File excleFile) throws Exception {
		Map<String, String> tableAttrs = modelParser.getTableAttrs();
		Map<String, String> fieldAttrs = modelParser.getFieldAttrs();
		String tableName = tableAttrs.get("name");
		String fileName = "/home/free/tmp/default.sql";
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
		HSSFWorkbook xwb = new HSSFWorkbook(new FileInputStream(excleFile));
		// 读取第一章表格内容
		HSSFSheet sheet = xwb.getSheetAt(0);
		// 用来装一行的内容
		Object value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
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
			stringBuilder.append("INSERT INTO "+tableName+" ("+clumns+")  \n");
			stringBuilder.append("values (");
			for(String key : keySet){
				cell = row.getCell(j);
                value=cell;
				/*if(null==cell) continue;
				// 判断列的内容类型
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					System.out.print(value+"  ");
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					value = cell.getStringCellValue();
					System.out.print(value+"  ");
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue();
					System.out.print(value+"  ");
					break;
				// 这一列为空
				case HSSFCell.CELL_TYPE_BLANK:
					value = "";
					System.out.print(value+"  ");
					break;
				default:
					value = cell.toString();
					System.out.print(value+"  ");
				}*/
                if (value == null ) {
                    value="";
                }
                j++;
				if(j==keyCount){
					stringBuilder.append("'"+value+"'");
				}else{
					stringBuilder.append("'"+value+"',");
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
	public void writeDisk(StringBuilder stringBuilder,String fileAbsolutePath){
		try {
			FileOutputStream out =new FileOutputStream(new File(fileAbsolutePath));
			out.write(stringBuilder.toString().getBytes());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) throws Exception {
		new ReadExcleToSql(new XmlModelParser(new File("src/main/resources/generate-sql.xml")).generteModel()
				).GenerateSqlFile(new File("/home/free/tmp/excle_test/test3.xlsx"));
	}

}
