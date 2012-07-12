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
			return JsonUtil.getSuccessfulMap(aggregations);
		}
		catch (Exception e) {

			return JsonUtil.getModelMapError(e);
		}
	}
	
	@RequestMapping(value="/builder/report/roupAggregationStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data, @RequestParam(required=true) Long reportColumn) throws Exception {
		try{

			List<GroupAggregation> groupAggregations = jsonUtil.getListFromRequest(data, GroupAggregation.class);
			
			List<Long> insertIds = new ArrayList<Long>(groupAggregations.size());
			GroupAggregationPk pk;
			
			for(GroupAggregation groupAggregation : groupAggregations) {
				groupAggregation.setParentColumnId(reportColumn);
				if(groupAggregation.isNewRecord()) {
					pk = groupAggregationDao.insert(groupAggregation);
				} else {
					pk = groupAggregation.createPk();
					groupAggregationDao.update(pk,groupAggregation);
				}
				insertIds.add(pk.getId());
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e);
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

			return JsonUtil.getModelMapError(e);
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
