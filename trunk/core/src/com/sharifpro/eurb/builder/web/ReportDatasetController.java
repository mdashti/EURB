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
import com.sharifpro.eurb.builder.model.ReportDataset;
import com.sharifpro.eurb.builder.model.ReportDatasetPk;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Alireza Sadeghipour
 */
@Controller
public class ReportDatasetController {

	private ReportDesignDao reportDesignDao;
	private ReportDatasetDao reportDatasetDao;
	
	private JsonUtil jsonUtil;

	@RequestMapping(value="/builder/report/reportDatasetSearch.spy")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(required=true) Long reportDesign) throws Exception {
		
		try{
			ReportDesign design = null;
			if(reportDesign != null){
				design = reportDesignDao.findByPrimaryKey(reportDesign);
			}
			else{
				throw new NullPointerException("Report Design is null");
			}
			List<ReportDataset> reportDatasets = new ArrayList<ReportDataset>(0);
			int totalCount = 0;
			
			if(design != null){
				reportDatasets = reportDatasetDao.findAll(design);
				totalCount = reportDatasetDao.countAll(design);
			}

			return JsonUtil.getSuccessfulMap(reportDatasets, totalCount);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/builder/report/reportDatasetStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data, @RequestParam(required=true) Long reportDesign
			, @RequestParam(required=true) Long reportVersion) throws Exception {
		try{

			List<ReportDataset> reportDatasets = jsonUtil.getListFromRequest(data, ReportDataset.class);
			
			List<Long> insertIds = new ArrayList<Long>(reportDatasets.size());
			ReportDatasetPk pk;
			for(ReportDataset reportDataset : reportDatasets) {
				reportDataset.setDesignId(reportDesign);
				reportDataset.setDesignVersionId(reportVersion);
				if(reportDataset.isNewRecord()) {
					pk = reportDatasetDao.insert(reportDataset);
				} else {
					pk = reportDataset.createPk();
					reportDatasetDao.update(pk,reportDataset);
				}
				insertIds.add(pk.getId());
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/builder/report/reportDatasetRemove.spy")
	public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {

		try{
			List<Integer> deleteIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<ReportDatasetPk> pkList = new ArrayList<ReportDatasetPk>(deleteIds.size());
			for(Integer id : deleteIds) {
				pkList.add(new ReportDatasetPk(new Long(id)));
			}
			reportDatasetDao.deleteAll(pkList);
			
			return JsonUtil.getSuccessfulMapAfterStore(deleteIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	
	

	@Autowired
	public void setReportDatasetDao(ReportDatasetDao reportDatasetDao) {
		this.reportDatasetDao = reportDatasetDao;
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