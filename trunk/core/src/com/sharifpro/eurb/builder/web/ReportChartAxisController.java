package com.sharifpro.eurb.builder.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharifpro.util.json.JsonUtil;
import com.sharifpro.eurb.builder.dao.ReportChartAxisDao;
import com.sharifpro.eurb.builder.model.ReportChart;
import com.sharifpro.eurb.builder.model.ReportChartAxis;
import com.sharifpro.eurb.builder.model.ReportChartAxisPk;

/**
 * Controller - Spring
 * 
 * @author Alireza Sadeghipour
 */
@Controller
public class ReportChartAxisController {

	private ReportChartAxisDao reportChartAxisDao;

	private JsonUtil jsonUtil;

	@RequestMapping(value="/builder/report/chartAxisStore.spy")
	public @ResponseBody Map<String,? extends Object> storeChartAxis(@RequestParam(required=true) Long id, 
			@RequestParam Long xAxisId, @RequestParam(required=true) Long xColumnMapping, @RequestParam(required=true) Long xDataset, @RequestParam String xTitle, 
			@RequestParam Long yAxisId, @RequestParam(required=true) Long yColumnMapping, @RequestParam(required=true) Long yDataset, @RequestParam String yTitle, @RequestParam String yAggregation)
					throws Exception {
		List<Long> insertIds = new ArrayList<Long>(2);
		ReportChartAxisPk pk;
		try{
			if(xAxisId == null){
				ReportChartAxis xAxis = new ReportChartAxis();
				xAxis.setChartId(id);
				xAxis.setColumnMappingId(xColumnMapping);
				xAxis.setDatasetId(xDataset);
				xAxis.setType("x");
				xAxis.setTitle(xTitle);
				pk = reportChartAxisDao.insert(xAxis);
			}
			else{
				ReportChartAxis xAxis = reportChartAxisDao.findByPrimaryKey(xAxisId);
				pk = xAxis.createPk();
				xAxis.setColumnMappingId(xColumnMapping);
				xAxis.setDatasetId(xDataset);
				xAxis.setTitle(xTitle);
				reportChartAxisDao.update(pk, xAxis);
			}
			insertIds.add(pk.getId());

			if(yAxisId == null){
				ReportChartAxis yAxis = new ReportChartAxis();
				yAxis.setChartId(id);
				yAxis.setColumnMappingId(yColumnMapping);
				yAxis.setDatasetId(yDataset);
				yAxis.setAggregation(yAggregation);
				yAxis.setType("y");
				yAxis.setTitle(yTitle);
				pk = reportChartAxisDao.insert(yAxis);
			}
			else{
				ReportChartAxis yAxis = reportChartAxisDao.findByPrimaryKey(yAxisId);
				pk = yAxis.createPk();
				yAxis.setColumnMappingId(yColumnMapping);
				yAxis.setDatasetId(yDataset);
				yAxis.setAggregation(yAggregation);
				yAxis.setTitle(yTitle);
				reportChartAxisDao.update(pk, yAxis);
			}
			insertIds.add(pk.getId());


			return JsonUtil.getSuccessfulMapAfterStore(insertIds);
		}
		catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e);
		}
	}

	@RequestMapping(value="/builder/report/chartAxisSearch.spy")
	public @ResponseBody Map<String,? extends Object> searchChartAxis(@RequestParam Object data) throws Exception {
		try{

			List<ReportChart> reportCharts = jsonUtil.getListFromRequest(data, ReportChart.class);

			List<ReportChartAxis> axises = reportChartAxisDao.findAll(reportCharts.get(0));
			ReportChartAxis xAxis = null, yAxis = null;
			for(ReportChartAxis a : axises){
				if(a.getType().equals("x")){
					xAxis = a;
				}
				else{
					yAxis = a;
				}
			}
			List<Object> result = new ArrayList<Object>();
			result.add(reportCharts.get(0).getId());
			if(xAxis != null){
				result.add(xAxis.getId());
				result.add(xAxis.getColumnMappingId());
				result.add(xAxis.getSelectedColumn());
				result.add(xAxis.getDatasetId());
				result.add(xAxis.getTitle());
			}

			if(yAxis != null){
				result.add(yAxis.getId());
				result.add(yAxis.getColumnMappingId());
				result.add(yAxis.getSelectedColumn());
				result.add(yAxis.getDatasetId());
				result.add(yAxis.getTitle());
				result.add(yAxis.getAggregation());
			}

			return JsonUtil.getSuccessfulMap(result);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e);
		}
	}




	@Autowired
	public void setReportChartAxisDao(ReportChartAxisDao reportChartAxisDao){
		this.reportChartAxisDao = reportChartAxisDao;
	}



	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}