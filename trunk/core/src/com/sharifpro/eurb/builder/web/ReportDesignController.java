package com.sharifpro.eurb.builder.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.management.mapping.dao.ColumnMappingDao;
import com.sharifpro.eurb.management.mapping.dao.TableMappingDao;
import com.sharifpro.eurb.management.mapping.model.ColumnMapping;
import com.sharifpro.eurb.management.mapping.model.TableMapping;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Alireza Sadeghipour
 */
@Controller
public class ReportDesignController {


	private JsonUtil jsonUtil;

	private ReportDesignDao reportDesignDao;
	private TableMappingDao tableMappingDao;
	private ColumnMappingDao columnMappingDao;


	@RequestMapping(value="/builder/report/report-design.spy")
	public ModelAndView executeReportDesignSpy() throws Exception {
		return executeReportDesignSpy(null);
	}

	@RequestMapping(value="/builder/report/report{report}-design.spy")
	public ModelAndView executeReportDesignSpy(@PathVariable String report) throws Exception {
		//find report design with given id
		ReportDesign reportDesign = reportDesignDao.findByPrimaryKey(Long.valueOf(report));
		ModelAndView mv = new ModelAndView();
		mv.addObject("report", report);
		//passing report desing's version with request
		// TODO :  actually we should make a new version
		mv.addObject("version", reportDesign.getVersionId().toString());
		
		List<TableMapping> tableMappings = tableMappingDao.findAllMapped();
		Object[][] tableMappingArr = new Object[tableMappings.size()][2];
		for(int i = 0; i < tableMappings.size(); i++) {
			tableMappingArr[i][0] = tableMappings.get(i).getId();
			tableMappingArr[i][1] = tableMappings.get(i).getMappedName();
		}

		mv.addObject("tableMappingComboContent", jsonUtil.getJSONFromObject(tableMappingArr));
		
		
		List<ColumnMapping> columnMappings = columnMappingDao.findAllMapped(reportDesign);
		Object[][] columnMappingArr = new Object[columnMappings.size()][2];
		for(int i = 0; i < columnMappings.size(); i++){
			columnMappingArr[i][0] = columnMappings.get(i).getId();
			columnMappingArr[i][1] = columnMappings.get(i).getMappedName();
		}
		
		mv.addObject("columnMappingComboContent", jsonUtil.getJSONFromObject(columnMappingArr));
		
		mv.setViewName("/builder/report/report-design");
		return mv;
	}


	@Autowired
	public void setColumnMappingDao(ColumnMappingDao columnMappingDao) {
		this.columnMappingDao = columnMappingDao;
	}
	
	@Autowired
	public void setTableMappingDao(TableMappingDao tableMappingDao) {
		this.tableMappingDao = tableMappingDao;
	}

	@Autowired
	public void setReportDesignDao(ReportDesignDao reportDesignDao) {
		this.reportDesignDao = reportDesignDao;
	}



	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}