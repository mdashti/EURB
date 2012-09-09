package com.sharifpro.eurb.builder.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sharifpro.eurb.builder.dao.ReportCategoryDao;
import com.sharifpro.eurb.builder.dao.ReportColumnDao;
import com.sharifpro.eurb.builder.dao.ReportDatasetDao;
import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.exception.ReportColumnDaoException;
import com.sharifpro.eurb.builder.exception.ReportDatasetDaoException;
import com.sharifpro.eurb.builder.exception.ReportDesignDaoException;
import com.sharifpro.eurb.builder.model.ReportCategory;
import com.sharifpro.eurb.builder.model.ReportColumn;
import com.sharifpro.eurb.builder.model.ReportDataset;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.model.ReportDesignPk;
import com.sharifpro.eurb.management.mapping.dao.ColumnMappingDao;
import com.sharifpro.eurb.management.mapping.dao.TableMappingDao;
import com.sharifpro.eurb.management.mapping.exception.ColumnMappingDaoException;
import com.sharifpro.eurb.management.mapping.model.ColumnMapping;
import com.sharifpro.eurb.management.mapping.model.TableMapping;
import com.sharifpro.util.PropertyProvider;
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
	private ReportCategoryDao reportCategoryDao;
	private ReportColumnDao reportColumnDao;



	@RequestMapping(value="/builder/report/report-design.spy")
	public ModelAndView executeReportDesignSpy() throws Exception {

		ReportDesign reportDesign = new ReportDesign();
		reportDesign.setName(PropertyProvider.get("eurb.app.builder.report.NewReport"));
		reportDesign.setIsCurrent(true);
		ReportDesignPk pk;
		pk = reportDesignDao.insert(reportDesign);
		return executeReportDesignSpy(pk.getId() + "");

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
		mv.addObject("name", reportDesign.getName());

		Long id;
		String name;
		//categories combo content
		List<ReportCategory> reportCategories = reportCategoryDao.findAll();
		Object[][] categoriesArr = new Object[reportCategories.size()][2];
		for(int i = 0; i < reportCategories.size(); i++) {
			categoriesArr[i][0] = reportCategories.get(i).getId();
			categoriesArr[i][1] = reportCategories.get(i).getName();
		}

		mv.addObject("categoriesComboContent", jsonUtil.getJSONFromObject(categoriesArr));

		//report design combo content
		List<ReportDesign> reportDesigns = reportDesignDao.findAll();
		Object[][] reportsArray = new Object[reportDesigns.size()][4];
		for(int i = 0; i < reportDesigns.size(); i++){
			reportsArray[i][0] = reportDesigns.get(i).getId();
			reportsArray[i][1] = reportDesigns.get(i).getVersionId();
			reportsArray[i][2] = reportDesigns.get(i).getName();
			reportsArray[i][3] = reportDesigns.get(i).getCategoryId();
		}

		mv.addObject("reportDesignsComboContent", jsonUtil.getJSONFromObject(reportsArray));

		//table mapping combo content
		HashMap<Long, String> tableMappingIdName = new HashMap<Long, String>();
		List<TableMapping> tableMappings = tableMappingDao.findAllMapped(reportDesign);
		Object[][] tableMappingArr = new Object[tableMappings.size()][2];
		for(int i = 0; i < tableMappings.size(); i++) {
			id = tableMappings.get(i).getId();
			name = tableMappings.get(i).getMappedName();
			tableMappingArr[i][0] = id;
			tableMappingArr[i][1] = name;
			tableMappingIdName.put(id, name);
		}

		mv.addObject("tableMappingComboContent", jsonUtil.getJSONFromObject(tableMappingArr));

		//column mapping combo content
		mv.addObject("columnMappingComboContent", jsonUtil.getJSONFromObject(getColumnMappingArray(reportDesign, tableMappingIdName)));

		mv.setViewName("/builder/report/report-design");
		return mv;
	}



	private Object[][] getColumnMappingArray(ReportDesign reportDesign, HashMap<Long, String> tableMappingIdName) throws ReportDatasetDaoException, ColumnMappingDaoException, ReportColumnDaoException, ReportDesignDaoException
	{
		HashMap<String, ArrayList<Object>> columns = new HashMap<String, ArrayList<Object>>();
		ArrayList<Object> info;
		List<ReportDataset> reportDatasets = reportDatasetDao.findAll(reportDesign);
		String datasetName;
		for(ReportDataset rds : reportDatasets){
			if(rds.getTableMappingId() != null && rds.getTableMappingId() != 0){
				List<ColumnMapping> columnMappings = columnMappingDao.findAllMapped(rds);
				for(ColumnMapping cm : columnMappings){
					info = new ArrayList<Object>();
					//if dataset has a name use it
					//else use the table mapping name for this dataset
					datasetName = (rds.getName() == null || rds.getName().equals("")) ? tableMappingIdName.get(rds.getTableMappingId()) : rds.getName(); 
					info.add(datasetName + " - " + cm.getMappedName());
					info.add(rds.getId());
					info.add(cm.getMappedName());
					info.add(tableMappingIdName.get(rds.getTableMappingId()));
					info.add(cm.getColumnName());
					info.add(0);
					info.add(cm.getId());
					columns.put(rds.getId() + "-" + cm.getId(), info);
				}
			}
			else if(rds.getBaseReportId() != null && rds.getBaseReportId() != 0){
				ReportDesign design = reportDesignDao.findByPrimaryKey(rds.getBaseReportId(), rds.getBaseReportVersionId());
				List<ReportColumn> reportColumns = reportColumnDao.findAll(design);
				for(ReportColumn rc : reportColumns){
					info = new ArrayList<Object>();
					info.add(design.getName() + " - " + rc.getColumnHeader());
					info.add(rds.getId());
					info.add(rc.getColumnHeader());
					info.add(design.getName());
					info.add(rc.getColumnMapping().getColumnName());
					info.add(1);
					info.add(rc.getId());
					columns.put(rds.getId() + "-" + rc.getId(), info);
				}
			}
		}
		Set<String> ck = columns.keySet();
		Object[][] columnMappingArr = new Object[ck.size()][8];
		int i = 0;
		for(String key : ck){
			ArrayList<Object> arr = columns.get(key);
			columnMappingArr[i][0] = key;
			columnMappingArr[i][1] = arr.get(0);
			columnMappingArr[i][2] = arr.get(1);
			columnMappingArr[i][3] = arr.get(2);
			columnMappingArr[i][4] = arr.get(3);
			columnMappingArr[i][5] = arr.get(4);
			columnMappingArr[i][6] = arr.get(5);
			columnMappingArr[i][7] = arr.get(6);
			i++;
		}
		return columnMappingArr;
	}

	@RequestMapping(value="/builder/report/reportSearch.spy")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(defaultValue="", required=true) String reportDesign,
			@RequestParam(defaultValue="", required=true) String reportVersion) throws Exception {
		try{
			ReportDesign design = reportDesignDao.findByPrimaryKey(Long.valueOf(reportDesign), Long.valueOf(reportVersion));
			List<ReportDesign> designs = new ArrayList<ReportDesign>();
			designs.add(design);
			return JsonUtil.getSuccessfulMap(designs, 1);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e);
		}
	}

	@RequestMapping(value="/builder/report/reportStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam(required=true) String id, @RequestParam(required=true) String versionId,
			@RequestParam(required=true) String categoryId, @RequestParam(required=true) String name, @RequestParam(required=true) String description) throws Exception {
		try{

			ReportDesign reportDesign = new ReportDesign();
			reportDesign.setId(Long.valueOf(id));
			reportDesign.setVersionId(Long.valueOf(versionId));
			reportDesign.setName(name);
			reportDesign.setCategoryId(Long.valueOf(categoryId));
			reportDesign.setDescription(description);

			List<Object[]> insertIds = new ArrayList<Object[]>(1);
			ReportDesignPk pk;
			pk = reportDesign.createPk();
			reportDesignDao.updateCurrentVersion(pk,reportDesign);

			insertIds.add(new Object[] {pk.getId(), pk.getVersionId()});

			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e);
		}
	}


	@Autowired
	public void setReportCategoryDao(ReportCategoryDao reportCategoryDao){
		this.reportCategoryDao = reportCategoryDao;
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
	public void setReportColumnDao(ReportColumnDao reportColumnDao){
		this.reportColumnDao = reportColumnDao;
	}


	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}