package com.sharifpro.eurb.builder.view;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.sharifpro.eurb.builder.model.ReportColumn;
import com.sharifpro.eurb.builder.model.ReportDesign;

public class ViewExcelReport extends AbstractExcelView {

	@Override
	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		ReportDesign reportDesign = (ReportDesign) model.get("reportDesign");
		List<ReportColumn> columnList = (List<ReportColumn>)model.get("columnList");
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) model.get("data");

		// create a new sheet
		Sheet s = wb.createSheet(reportDesign.getName());
		s.setRightToLeft(true);
		// declare a row object reference
		
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setWrapText(true);
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerStyle.setFillForegroundColor((short)0xc9c9c9);
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Row r = s.createRow(0);
		// declare a cell object reference
		Cell c = null;
		// create columnList.size() cells (0-(columnList.size()-1))
		for (int cellnum = 0; cellnum < columnList.size(); cellnum ++) {
			c = r.createCell(cellnum);
			c.setCellValue(columnList.get(cellnum).getColumnHeader());
			c.setCellStyle(headerStyle);
		}
		// create a sheet with resultList.size() rows (1-resultList.size())
		for (int rownum = 0; rownum < resultList.size(); rownum++) {
			// create a row
			r = s.createRow(rownum+1);

			// create columnList.size() cells (0-(columnList.size()-1))
			for (int cellnum = 0; cellnum < columnList.size(); cellnum ++) {
				c = r.createCell(cellnum);
				Object value = resultList.get(rownum).get(columnList.get(cellnum).getColumnKey());
				if (value != null) {
					if (value instanceof String) {
						c.setCellValue((String) value);
					} else if (value instanceof Double) {
						c.setCellValue((Double) value);
					} else if (value instanceof Integer) {
						c.setCellValue(((Integer) value).doubleValue());
					} else if (value instanceof Short) {
						c.setCellValue(((Short) value).doubleValue());
					} else if (value instanceof Float) {
						c.setCellValue(((Float) value).doubleValue());
					} else if (value instanceof Boolean) {
						c.setCellValue((Boolean) value);
					} else if (value instanceof Date) {
						c.setCellValue((Date) value);
					} else if (value instanceof Calendar) {
						c.setCellValue((Calendar) value);
					} else if (value instanceof java.sql.Date) {
						c.setCellValue(new Date(((java.sql.Date) value).getTime()));
					} else {
						c.setCellValue(value.toString());
					}
				} else {
					c.setCellValue("");
				}
			}
		}
	}
}