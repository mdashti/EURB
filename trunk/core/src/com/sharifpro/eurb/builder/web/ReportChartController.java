package com.sharifpro.eurb.builder.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharifpro.eurb.builder.dao.ObjectConfigDao;
import com.sharifpro.eurb.builder.dao.ReportChartDao;
import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.model.ObjectConfig;
import com.sharifpro.eurb.builder.model.ReportChart;
import com.sharifpro.eurb.builder.model.ReportChartPk;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Alireza Sadeghipour
 */
@Controller
public class ReportChartController {

	private ReportDesignDao reportDesignDao;
	private ReportChartDao reportChartDao;
	private ObjectConfigDao objectConfigDao;
	
	private JsonUtil jsonUtil;

	@RequestMapping(value="/builder/report/reportChartSearch.spy")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(required=true) Long reportDesign) throws Exception {
		
		try{
			ReportDesign design = null;
			if(reportDesign != null){
				design = reportDesignDao.findByPrimaryKey(reportDesign);
			}
			else{
				throw new NullPointerException("Report Design is null");
			}
			List<ReportChart> reportCharts = new ArrayList<ReportChart>(0);
			int totalCount = 0;
			
			if(design != null){
				reportCharts = reportChartDao.findAll(design);
				totalCount = reportChartDao.countAll(design);
			}
			
			return JsonUtil.getSuccessfulMap(reportCharts, totalCount);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e);
		}
	}

	@RequestMapping(value="/builder/report/reportChartStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data, @RequestParam(required=true) Long reportDesign
			, @RequestParam(required=true) Long reportVersion) throws Exception {
		try{

			List<ReportChart> reportCharts;
			reportCharts = jsonUtil.getListFromRequest(data, ReportChart.class);
			
			List<Long> insertIds = new ArrayList<Long>(reportCharts.size());
			ReportChartPk pk;
			for(ReportChart reportChart : reportCharts) {
				reportChart.setReportDesignId(reportDesign);
				reportChart.setReportDesignVersionId(reportVersion);
				if(reportChart.isNewRecord()) {
					pk = reportChartDao.insert(reportChart);
				} else {
					pk = reportChart.createPk();
					reportChartDao.update(pk,reportChart);
					objectConfigDao.insertAll(pk.getId(), reportChart.getConfig());
				}
				insertIds.add(pk.getId());
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e);
		}
	}

	@RequestMapping(value="/builder/report/reportChartRemove.spy")
	public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {

		try{
			List<Integer> deleteIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<ReportChartPk> pkList = new ArrayList<ReportChartPk>(deleteIds.size());
			for(Integer id : deleteIds) {
				pkList.add(new ReportChartPk(new Long(id)));
			}
			reportChartDao.deleteAll(pkList);
			
			return JsonUtil.getSuccessfulMapAfterStore(deleteIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e);
		}
	}
	
	

	@Autowired
	public void setObjectConfigDao(ObjectConfigDao objectConfigDao){
		this.objectConfigDao = objectConfigDao;
	}
	
	@Autowired
	public void setReportChartDao(ReportChartDao reportChartDao) {
		this.reportChartDao = reportChartDao;
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