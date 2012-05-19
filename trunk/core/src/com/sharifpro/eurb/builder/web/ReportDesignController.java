package com.sharifpro.eurb.builder.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sharifpro.eurb.builder.dao.ReportDatasetDao;
import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.model.ReportDataset;
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
	private ReportDatasetDao reportDatasetDao;

	

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
		HashMap<Long, String> tableMappingIdName = new HashMap<Long, String>();
		List<TableMapping> tableMappings = tableMappingDao.findAllMapped(reportDesign);
		Object[][] tableMappingArr = new Object[tableMappings.size()][2];
		for(int i = 0; i < tableMappings.size(); i++) {
			tableMappingArr[i][0] = tableMappings.get(i).getId();
			tableMappingArr[i][1] = tableMappings.get(i).getMappedName();
			tableMappingIdName.put(tableMappings.get(i).getId(), tableMappings.get(i).getMappedName());
		}

		mv.addObject("tableMappingComboContent", jsonUtil.getJSONFromObject(tableMappingArr));
		
		HashMap<Long, ArrayList<Object>> columns = new HashMap<Long, ArrayList<Object>>();
		ArrayList<Object> info;
		List<ReportDataset> reportDatasets = reportDatasetDao.findAll(reportDesign);
		for(ReportDataset rds : reportDatasets){
			List<ColumnMapping> columnMappings = columnMappingDao.findAllMapped(rds);
			for(ColumnMapping cm : columnMappings){
				info = new ArrayList<Object>();
				info.add(tableMappingIdName.get(rds.getTableMappingId()) + " - " + cm.getMappedName());
				info.add(rds.getId());
				info.add(cm.getMappedName());
				info.add(tableMappingIdName.get(rds.getTableMappingId()));
				columns.put(cm.getId(), info);
			}
		}
		Set<Long> ck = columns.keySet();
		Object[][] columnMappingArr = new Object[ck.size()][5];
		int i = 0;
		for(Long key : ck){
			ArrayList<Object> arr = columns.get(key);
			columnMappingArr[i][0] = key;
			columnMappingArr[i][1] = arr.get(0);
			columnMappingArr[i][2] = arr.get(1);
			columnMappingArr[i][3] = arr.get(2);
			columnMappingArr[i][4] = arr.get(3);
			i++;
		}
		
		mv.addObject("columnMappingComboContent", jsonUtil.getJSONFromObject(columnMappingArr));
		
		mv.setViewName("/builder/report/report-design");
		return mv;
	}


	@Autowired
	public void setReportDatasetDao(ReportDatasetDao reportDatasetDao){
		this.reportDatasetDao = reportDatasetDao;
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