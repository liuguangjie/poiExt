package com.chuangwl.poiext.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExportExcle {

	public ExportExcle() {
	}
	public void export2003Excle(List<Object[]> dataSet, OutputStream out){
		//创建一个2003工作薄
        HSSFWorkbook hwb = new HSSFWorkbook();
        // 生成一个表格
        String title="default";
        HSSFSheet sheet=hwb.createSheet(title);
        // 设置表格默认列宽度
        // 设置样式
        // 设置一个字体

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        Iterator<Object[]> it = dataSet.iterator();
        Object[] headers = it.next();
        int length=headers.length;
        for (short i = 0; i < length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i].toString());
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行
        int index = 0;
        HSSFCell cell = null;
        Object value;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            headers = it.next();
            for (int i = 0; i < length; i++) {
                cell = row.createCell(i);
                value = headers[i];
                if (null != value) {
                    HSSFRichTextString richString = new HSSFRichTextString(value.toString());
                    cell.setCellValue(richString);
                } else {
                    cell.setCellValue("");
                }
            }
        }
        try {
            hwb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public void export2007Excle(){

	}

	public boolean excle2007or2003(){

		return true;
	}




    @Deprecated
	public void export(List<Object[]> dataSet, OutputStream out) {
		// 1.声明一个工作薄
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		// 2.生成一个表格 // 在Excel工作簿中建一工作表，其名为test
		String sheetname = "test";
		HSSFSheet hssfSheet = hssfWorkbook.createSheet(sheetname);

		// 生成一个样式
		HSSFCellStyle hssfCellStyle = hssfWorkbook.createCellStyle();
		// 设置这些样式
		hssfCellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		hssfCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		hssfCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		hssfCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		hssfCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		hssfCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = hssfWorkbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		hssfCellStyle.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = hssfWorkbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = hssfWorkbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = hssfSheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");
		// 产生表格标题行
		HSSFRow row = hssfSheet.createRow(0);
		Iterator<Object[]> it = dataSet.iterator();
		Object[] headers = it.next();
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(hssfCellStyle);
			HSSFRichTextString text = new HSSFRichTextString(headers[i].toString());
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行

		int index = 0;
		HSSFCell cell = null;
		Object value;
		while (it.hasNext()) {
			index++;
			row = hssfSheet.createRow(index);
			headers = it.next();
			for (int i = 0; i < headers.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(style2);
				value = headers[i];
				if (null != value) {
					HSSFRichTextString richString = new HSSFRichTextString(value.toString());
					HSSFFont font3 = hssfWorkbook.createFont();
					font3.setColor(HSSFColor.BLUE.index);
					richString.applyFont(font3);
					cell.setCellValue(richString);
				} else {
					cell.setCellValue("");
				}
			}

		}
		try {
			hssfWorkbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
