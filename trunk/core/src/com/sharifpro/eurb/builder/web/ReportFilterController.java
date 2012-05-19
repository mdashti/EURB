package com.sharifpro.eurb.builder.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharifpro.eurb.builder.dao.ReportDatasetDao;
import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.dao.ReportFilterDao;
import com.sharifpro.eurb.builder.model.ReportDataset;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.model.ReportFilter;
import com.sharifpro.eurb.builder.model.ReportFilterPk;
import com.sharifpro.eurb.management.mapping.dao.ColumnMappingDao;
import com.sharifpro.eurb.management.mapping.model.ColumnMapping;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Alireza Sadeghipour
 */
@Controller
public class ReportFilterController {

	private ReportDesignDao reportDesignDao;
	private ReportFilterDao reportFilterDao;
	private ColumnMappingDao columnMappingDao;
	private ReportDatasetDao reportDatasetDao;

	private JsonUtil jsonUtil;

	@RequestMapping(value="/builder/report/reportFilterSearch.spy")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(required=true) Long reportDesign) throws Exception {
		
		try{
			ReportDesign design = null;
			if(reportDesign != null){
				design = reportDesignDao.findByPrimaryKey(reportDesign);
			}
			else{
				throw new NullPointerException("Report Design is null");
			}
			List<ReportFilter> ReportFilters = new ArrayList<ReportFilter>(0);
			int totalCount = 0;
			
			if(design != null){
				ReportFilters = reportFilterDao.findAll(design.getId());
				totalCount = reportFilterDao.countAll(design.getId());
			}

			return JsonUtil.getSuccessfulMap(ReportFilters, totalCount);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	

	@RequestMapping(value="/builder/report/reportFilterStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data, @RequestParam(required=true) Long reportDesign
			, @RequestParam(required=true) Long reportVersion) throws Exception {
		try{

			List<ReportFilter> reportFilters = jsonUtil.getListFromRequest(data, ReportFilter.class);
			
			List<Long> insertIds = new ArrayList<Long>(reportFilters.size());
			ReportFilterPk pk;
			for(ReportFilter reportFilter : reportFilters) {
				if(reportFilter.getOperand1ColumnMappingId() != null){
					reportFilter.setFilterType(1);
				}
				else{
					reportFilter.setFilterType(0);
				}
				reportFilter.setReportDesignId(reportDesign);
				reportFilter.setReportDesignVersionId(reportVersion);
				if(reportFilter.isNewRecord()) {
					ColumnMapping columnMapping = columnMappingDao.findByPrimaryKey(reportFilter.getColumnMappingId());
					List<ReportDataset> reportDataset = reportDatasetDao.findByReportDesignAndTableMapping(reportDesign, reportVersion, columnMapping.getTableMappingId());
					reportFilter.setReportDatasetId(reportDataset.get(0).getId());
					//@ TODO : set col  type correctly
					pk = reportFilterDao.insert(reportFilter);
				} else {
					pk = reportFilter.createPk();
					reportFilterDao.update(pk,reportFilter);
				}
				insertIds.add(pk.getId());
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/builder/report/reportFilterRemove.spy")
	public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {

		try{
			List<Integer> deleteIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<ReportFilterPk> pkList = new ArrayList<ReportFilterPk>(deleteIds.size());
			for(Integer id : deleteIds) {
				pkList.add(new ReportFilterPk(new Long(id)));
			}
			reportFilterDao.deleteAll(pkList);
			
			return JsonUtil.getSuccessfulMapAfterStore(deleteIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	
	@Autowired
	public void setColumnMappingDao(ColumnMappingDao columnMappingDao) {
		this.columnMappingDao = columnMappingDao;
	}
	
	@Autowired
	public void setReportDatasetDao(ReportDatasetDao reportDatasetDao) {
		this.reportDatasetDao = reportDatasetDao;
	}

	@Autowired
	public void setReportFilterDao(ReportFilterDao reportFilterDao) {
		this.reportFilterDao = reportFilterDao;
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