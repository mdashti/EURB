package com.sharifpro.eurb.builder.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharifpro.eurb.builder.dao.ReportCategoryDao;
import com.sharifpro.eurb.builder.model.ReportCategory;
import com.sharifpro.eurb.builder.model.ReportCategoryPk;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Alireza Sadeghipour
 */
@Controller
@RequestMapping(value="/builder/category/*")
public class ReportCategoryController {

	private ReportCategoryDao reportCategoryDao;
	
	private JsonUtil jsonUtil;

	@RequestMapping(value="/reportCategorySearch.spy")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(defaultValue="", required=false) String query
			,@RequestParam(defaultValue="[]", required=false) String fields
			,@RequestParam(defaultValue="0", required=false) String start
			,@RequestParam(defaultValue=AbstractDAO.DEFAULT_PAGE_SIZE_STR, required=false) String limit) throws Exception {
		
		try{
			List<ReportCategory> reportCategories;
			int totalCount;
			Integer startBy = StringUtils.isEmpty(start) ? 0 : Integer.parseInt(start);
			Integer limitBy = StringUtils.isEmpty(limit) ? AbstractDAO.DEFAULT_PAGE_SIZE : Integer.parseInt(limit);
			List<String> onFields = jsonUtil.getListFromRequest(fields, String.class);
			if(StringUtils.isEmpty(query) || onFields == null || onFields.isEmpty()) {
				reportCategories = reportCategoryDao.findAll(startBy, limitBy);
				totalCount = reportCategoryDao.countAll();
			} else {
				reportCategories = reportCategoryDao.findAll(query, onFields, startBy, limitBy);
				totalCount = reportCategoryDao.countAll(query, onFields);
			}

			return JsonUtil.getSuccessfulMap(reportCategories, totalCount);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/reportCategoryStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data) throws Exception {
		try{

			List<ReportCategory> reportCategories = jsonUtil.getListFromRequest(data, ReportCategory.class);
			
			List<Object[]> insertIds = new ArrayList<Object[]>(reportCategories.size());
			ReportCategoryPk pk;
			for(ReportCategory reportCategory : reportCategories) {
				if(reportCategory.isNewRecord()) {
					pk = reportCategoryDao.insert(reportCategory);
				} else {
					pk = reportCategory.createPk();
					reportCategoryDao.update(pk,reportCategory);
				}
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/reportCategoryRemove.spy")
	public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {

		try{
			List<Integer> deleteIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<ReportCategoryPk> pkList = new ArrayList<ReportCategoryPk>(deleteIds.size());
			for(Integer id : deleteIds) {
				pkList.add(new ReportCategoryPk(new Long(id)));
			}
			reportCategoryDao.deleteAll(pkList);
			
			return JsonUtil.getSuccessfulMapAfterStore(deleteIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	
	

	@Autowired
	public void setReportCategoryDao(ReportCategoryDao reportCategoryDao) {
		this.reportCategoryDao = reportCategoryDao;
	}

	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}