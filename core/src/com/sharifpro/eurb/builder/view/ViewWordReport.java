package com.sharifpro.eurb.builder.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import com.sharifpro.eurb.builder.model.ReportColumn;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.util.DateUtil;
import com.sharifpro.util.FarsiUtil;

public class ViewWordReport extends AbstractWordView {

	@Override
	@SuppressWarnings("unchecked")
	protected void buildWordDocument(Map<String, Object> model,
			XWPFDocument document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReportDesign reportDesign = (ReportDesign) model.get("reportDesign");
		List<ReportColumn> columnList = (List<ReportColumn>) model.get("columnList");
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) model.get("data");

//		XWPFHeader header = new XWPFHeader(document, CTHdrFtr.Factory.newInstance());
//		header.
		
		XWPFHeader header = document.getHeaderFooterPolicy().getDefaultHeader();
		List<XWPFTable> table = header.getTables();
		for (XWPFTable xwpfTable : table) {
            xwpfTable.getRow(0).getCell(0).setText(reportDesign.getName());
            xwpfTable.getRow(1).getCell(0).setText(reportDesign.getDescription());
            xwpfTable.getRow(0).getCell(1).setText("");//Here we should put group (if available)
            xwpfTable.getRow(1).getCell(1).setText(FarsiUtil.convertDigits(DateUtil.getCurrentDateTimeString()));
            //document.setTable(0, xwpfTable);
            //header.insertTable(1, xwpfTable);
        }
		/*XWPFParagraph title = document.getParagraphs().get(0);
		title.setAlignment(ParagraphAlignment.CENTER);
		title.setVerticalAlignment(TextAlignment.CENTER);
		//title.setWordWrap(true);
		XWPFRun titleRun = title.createRun();
		titleRun.setText(reportDesign.getName());
		titleRun.setFontSize(14);*/

		XWPFTable s = document.getTables().get(0);
		// declare a row object reference
		XWPFTableRow r = s.getRow(0);
		// declare a cell object reference
		XWPFTableCell c = null;
		// create columnList.size() cells (0-(columnList.size()-1))
		for (int cellnum = 0; cellnum < columnList.size(); cellnum++) {
			if(r.getCell(cellnum) != null) {
				c = r.getCell(cellnum);
			} else {
				c = r.createCell();
			}
			//c.setColor("c9c9c9");
			//c.setVerticalAlignment(XWPFVertAlign.CENTER);
			c.setText(columnList.get(cellnum).getColumnHeader());
		}
		// create a sheet with resultList.size() rows (1-resultList.size())
		for (int rownum = 0; rownum < resultList.size(); rownum++) {
			// create a row
			if(s.getRow(rownum+1) != null) {
				r = s.getRow(rownum+1);
			} else {
				r = s.createRow();
			}

			// create columnList.size() cells (0-(columnList.size()-1))
			for (int cellnum = 0; cellnum < columnList.size(); cellnum++) {
				if(r.getCell(cellnum) != null) {
					c = r.getCell(cellnum);
				} else {
					c = r.createCell();
				}
				Object value = resultList.get(rownum).get(columnList.get(cellnum).getColumnKey());
				if (value != null) {
					c.setText(value.toString());
				} else {
					c.setText("");
				}
			}
		}
	}

}