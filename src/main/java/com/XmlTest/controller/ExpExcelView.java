package com.XmlTest.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

@SuppressWarnings("unchecked")
public class ExpExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Map<String, Object>> tabLs = (List<Map<String, Object>>) model.get("tabLs");

		if (null == tabLs || tabLs.size() <= 0) {
			return;
		}
		int size = tabLs.size();
		// 获取表头
		String xls_head = (String) model.get("xls_head");
		// 获取列名
		String xls_col = (String) model.get("xls_col");
		// 表头和列名字符串转成数组,转义 \\|
		String heads[] = xls_head.split("\\|");
		String cols[] = xls_col.split("\\|");

		// 直接写表头
		// 设置execl表格字段名称数据--第一行
		HSSFSheet sheet = workbook.createSheet();
		// 工作表名字乱码 解决办法：workBook.setSheetName(sheetCount, sheetName ,
		// HSSFWorkbook.ENCODING_UTF_16);
		int colCount = heads.length;
		// 默认列宽
		sheet.setDefaultColumnWidth(12);
		Map<String, Integer> columnTitleNo = new HashMap<String, Integer>(); // 数据名称对应的列位置

		HSSFCell cell = null;
		// 单元格乱码
		// 解决办法：在写单元格的值的之前，workSheet.getRow(rowIdx).getCell((short)2).setEncoding(HSSFCell.ENCODING_UTF_16);
		for (int i = 0; i < colCount; i++) {
			cell = getCell(sheet, 0, i);
			// sheet.getRow(0).getCell((short)i).setEncoding(HSSFCell.ENCODING_UTF_16);//新版已经没有这个方法
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			setText(cell, heads[i]);
			columnTitleNo.put(cols[i], i);
		}
		// 定义需合计项的变量
		int recordNum = 0;
		double payfee = 0.0;
		double dzfee = 0.0;
		double notdzfee = 0.0;
		double qffee = 0.0;
		double notqffee = 0.0;
		double hzfee = 0.0;
		double nothzfee = 0.0;

		for (int j = 0; j < size; j++) {
			// 65536 分页
			HSSFRow row = sheet.createRow(j + 1);
			Map<String, Object> map = tabLs.get(j);
			Set set = map.entrySet();
			for (Iterator iter = set.iterator(); iter.hasNext();) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				Object value_o = entry.getValue();
				String value = String.valueOf(value_o);

				if ("pay_fee".equals(key)) {
					payfee += Double.valueOf(value);
				}
				if ("dz_fee".equals(key)) {
					dzfee += Double.valueOf(value);
				}
				if ("not_dz_fee".equals(key)) {
					notdzfee += Double.valueOf(value);
				}
				if ("qf_fee".equals(key)) {
					qffee += Double.valueOf(value);
				}
				if ("not_qf_fee".equals(key)) {
					notqffee += Double.valueOf(value);
				}
				if ("hz_fee".equals(key)) {
					hzfee += Double.valueOf(value);
				}
				if ("not_hz_fee".equals(key)) {
					nothzfee += Double.valueOf(value);
				}

				Integer num = columnTitleNo.get(key);
				if (num != null) {
					row.createCell(num).setCellValue(value);
				}
			}
			if (payfee > 0) {
				recordNum += 1;
			}
		}

		if (recordNum > 1) {
			HSSFRow row = sheet.createRow(size + 1);
			row.createCell(0).setCellValue("合计");
			row.createCell(1).setCellValue(String.format("%.2f", payfee));
			row.createCell(2).setCellValue(String.format("%.2f", dzfee));
			row.createCell(3).setCellValue(String.format("%.2f", notdzfee));
			row.createCell(4).setCellValue(String.format("%.2f", qffee));
			row.createCell(5).setCellValue(String.format("%.2f", notqffee));
			row.createCell(6).setCellValue(String.format("%.2f", hzfee));
			row.createCell(7).setCellValue(String.format("%.2f", nothzfee));
		}

		String fileName = (String) model.get("exp_file");
		if ("".equals(fileName) || fileName == null) {
			fileName = "导出Excel.xls";
		}
		//
		String enableFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		// 处理中文文件名
		try {
			String agent = (String) request.getHeader("USER-AGENT").toUpperCase();

			if ((agent.indexOf("COMPATIBLE") > -1 && agent.indexOf("MSIE") > -1) || (agent.indexOf("EDGE") > -1)|| (agent.indexOf("TRIDENT") > -1)) {// ie
				fileName = URLEncoder.encode(fileName, "UTF-8");
				enableFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			} else {// FF
				enableFileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-disposition", "attachment; filename=" + enableFileName);

		OutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}
}
