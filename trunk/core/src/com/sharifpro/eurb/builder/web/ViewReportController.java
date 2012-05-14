package com.sharifpro.eurb.builder.web;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sharifpro.eurb.builder.dao.ReportCategoryDao;
import com.sharifpro.eurb.builder.dao.ReportColumnDao;
import com.sharifpro.eurb.builder.dao.ReportDatasetDao;
import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.intermediate.ExtGridColumn;
import com.sharifpro.eurb.builder.intermediate.ExtStoreField;
import com.sharifpro.eurb.builder.model.ReportCategory;
import com.sharifpro.eurb.builder.model.ReportColumn;
import com.sharifpro.eurb.builder.model.ReportDataset;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Controller
public class ViewReportController {

	private ReportDesignDao reportDesignDao;
	private ReportCategoryDao reportCategoryDao;
	private ReportDatasetDao reportDatasetDao;
	private ReportColumnDao reportColumnDao;
	private JsonUtil jsonUtil;


	@RequestMapping(value="/builder/report/view-report.spy")
	public ModelAndView executeViewReportList() throws Exception {
		ModelAndView mv = new ModelAndView();

		List<ReportCategory> reportCategories = reportCategoryDao.findAll();
		Object[][] categoriesArr = new Object[reportCategories.size()][2];
		for(int i = 0; i < reportCategories.size(); i++) {
			categoriesArr[i][0] = reportCategories.get(i).getId();
			categoriesArr[i][1] = reportCategories.get(i).getName();
		}

		mv.addObject("categoriesComboContent", jsonUtil.getJSONFromObject(categoriesArr));

		mv.setViewName("/builder/report/view-report");
		return mv;
	}
	
	@RequestMapping(value="/builder/report/run-report{report}-v{version}.spy")
	public ModelAndView executeRunReport(@PathVariable Long report, @PathVariable Long version) throws Exception {
		//find report design with given id
		ReportDesign reportDesign = reportDesignDao.findByPrimaryKey(report, version);
		
		List<ReportDataset> datasetList = reportDatasetDao.findAll(reportDesign);
		List<ReportColumn> columnList = reportColumnDao.findAll(reportDesign);
		
		List<ExtStoreField> storeFields = new ArrayList<ExtStoreField>(columnList.size());
		List<ExtGridColumn> gridColumns = new ArrayList<ExtGridColumn>(columnList.size());
		for(ReportColumn col : columnList) {
			storeFields.add(new ExtStoreField(col.getColumnMapping().getColumnName() + col.getColumnMappingId()));
			gridColumns.add(new ExtGridColumn(col.getColumnHeader(), col.getColumnMapping().getColumnName() + col.getColumnMappingId(), col.getColumnWidth()));
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("report", report);
		mv.addObject("reportVersion", version);
		mv.addObject("reportName", reportDesign.getName());
		mv.addObject("storeFields", jsonUtil.getJSONFromObject(storeFields));
		mv.addObject("gridColumns", jsonUtil.getJSONFromObject(gridColumns));
		
		mv.setViewName("/builder/report/run-report");
		return mv;
	}


	@Autowired
	public void setReportCategoryDao(ReportCategoryDao reportCategoryDao){
		this.reportCategoryDao = reportCategoryDao;
	}

	@Autowired
	public void setReportDesignDao(ReportDesignDao reportDesignDao) {
		this.reportDesignDao = reportDesignDao;
	}

	@Autowired
	public void setReportDatasetDao(ReportDatasetDao reportDatasetDao) {
		this.reportDatasetDao = reportDatasetDao;
	}

	@Autowired
	public void setReportColumnDao(ReportColumnDao reportColumnDao) {
		this.reportColumnDao = reportColumnDao;
	}

	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}