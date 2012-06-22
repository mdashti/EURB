package com.sharifpro.eurb.builder.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.sharifpro.eurb.builder.model.ReportColumn;
import com.sharifpro.eurb.builder.model.ReportDesign;

public class ViewWordReport extends AbstractWordView {

	@Override
	@SuppressWarnings("unchecked")
	protected void buildWordDocument(Map<String, Object> model,
			XWPFDocument document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReportDesign reportDesign = (ReportDesign) model.get("reportDesign");
		List<ReportColumn> columnList = (List<ReportColumn>) model.get("columnList");
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) model.get("data");

		XWPFTable s = document.createTable(resultList.size(), columnList.size());

		// declare a row object reference
		XWPFTableRow r = null;
		// declare a cell object reference
		XWPFTableCell c = null;

		// create a sheet with resultList.size() rows (0-(resultList.size()-1))
		for (int rownum = 0; rownum < resultList.size(); rownum++) {
			// create a row
			r = s.getRow(rownum);

			// create columnList.size() cells (0-(columnList.size()-1))
			for (int cellnum = 0; cellnum < columnList.size(); cellnum++) {
				c = r.getCell(cellnum);
				Object value = resultList.get(rownum).get(
						columnList.get(cellnum).getColumnKey());
				if (value != null) {
					c.setText(value.toString());
				} else {
					c.setText("");
				}
			}
		}
	}

}