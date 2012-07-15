package com.sharifpro.eurb.builder.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharifpro.eurb.builder.dao.GroupAggregationDao;
import com.sharifpro.eurb.builder.dao.ReportColumnDao;
import com.sharifpro.eurb.builder.model.GroupAggregation;
import com.sharifpro.eurb.builder.model.GroupAggregationPk;
import com.sharifpro.eurb.builder.model.ReportColumn;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Alireza Sadeghipour
 */
@Controller
public class GroupAggregationController {

	private ReportColumnDao reportColumnDao;
	private GroupAggregationDao groupAggregationDao;

	private JsonUtil jsonUtil;

	@RequestMapping(value="/builder/report/groupAggregationSearch.spy")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(required=true) Long reportColumn) throws Exception {
		try{
			ReportColumn column = reportColumnDao.findByPrimaryKey(reportColumn);
			if(column == null){
				throw new NullPointerException("Wrong reportColumn");
			}
			List<GroupAggregation> aggregations = groupAggregationDao.findAll(column);
			if(aggregations.size() == 0){
				//add default aggregation
				GroupAggregation newAggregation = new GroupAggregation();
				newAggregation.setParentColumnId(reportColumn);
				newAggregation.setAggregationFunction("sum");
				newAggregation.setPlace(0);
				aggregations.add(newAggregation);
			}
			return JsonUtil.getSuccessfulMap(aggregations);
		}
		catch (Exception e) {

			return JsonUtil.getModelMapError(e);
		}
	}

	@RequestMapping(value="/builder/report/groupAggregationStore.spy")
	public @ResponseBody Map<String,? extends Object> store( @RequestParam(required=false) Long id, @RequestParam(required=true) Long parentColumnId,
			@RequestParam(required=true) Long aggregatedColumnMappingId, @RequestParam(required=true) Long aggregatedColumnDatasetId,
			@RequestParam(required=true) String aggregationFunction, @RequestParam(required=true) int place) throws Exception {

		try{

			List<Long> insertIds = new ArrayList<Long>(1);
			GroupAggregation groupAggregation;
			GroupAggregationPk pk;

			if(id == null || id == 0){
				groupAggregation = new GroupAggregation();
			}
			else{
				groupAggregation = groupAggregationDao.findByPrimaryKey(id);
			}
			groupAggregation.setParentColumnId(parentColumnId);
			groupAggregation.setAggregatedColumnDatasetId(aggregatedColumnDatasetId);
			groupAggregation.setAggregatedColumnMappingId(aggregatedColumnMappingId);
			groupAggregation.setAggregationFunction(aggregationFunction);
			groupAggregation.setPlace(place);
			if(id == null || id == 0) {
				pk = groupAggregationDao.insert(groupAggregation);
			} else {
				pk = groupAggregation.createPk();
				groupAggregationDao.update(pk,groupAggregation);
			}
			insertIds.add(pk.getId());

		return JsonUtil.getSuccessfulMapAfterStore(insertIds);

	} catch (Exception e) {
		e.printStackTrace();
		return JsonUtil.getModelMapError(e.getMessage());
	}
}

@RequestMapping(value="/builder/report/GroupAggregationRemove.spy")
public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {

	try{
		List<Integer> deleteIds = jsonUtil.getListFromRequest(data, Integer.class);
		List<GroupAggregationPk> pkList = new ArrayList<GroupAggregationPk>(deleteIds.size());
		for(Integer id : deleteIds) {
			pkList.add(new GroupAggregationPk(new Long(id)));
		}
		groupAggregationDao.deleteAll(pkList);

		return JsonUtil.getSuccessfulMapAfterStore(deleteIds);

	} catch (Exception e) {

		return JsonUtil.getModelMapError(e.getMessage());
	}
}

@Autowired
public void setGroupAggregationDao(GroupAggregationDao groupAggregationDao){
	this.groupAggregationDao = groupAggregationDao;
}
@Autowired
public void setReportColumnDao(ReportColumnDao reportColumnDao){
	this.reportColumnDao = reportColumnDao;
}
@Autowired
public void setJsonUtil(JsonUtil jsonUtil){
	this.jsonUtil = jsonUtil;
}
}
