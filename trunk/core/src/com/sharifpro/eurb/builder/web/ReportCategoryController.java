package com.sharifpro.eurb.builder.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sharifpro.eurb.builder.dao.ReportCategoryDao;
import com.sharifpro.eurb.builder.model.ReportCategory;
import com.sharifpro.eurb.builder.model.ReportCategoryPk;
import com.sharifpro.eurb.info.AuthorityType;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.exception.DbConfigDaoException;
import com.sharifpro.util.PropertyProvider;
import com.sharifpro.util.SecurityUtil;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Alireza Sadeghipour
 */
@Controller
public class ReportCategoryController {

	private ReportCategoryDao reportCategoryDao;
	
	private JsonUtil jsonUtil;

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_BASE_CATEGORY_MANAGEMENT_VIEW_LIST)")
	@RequestMapping(value="/builder/category/report-category.spy")
	public ModelAndView executeReportCategorySpy() throws Exception {
		return executeReportCategorySpy(null);
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_BASE_CATEGORY_MANAGEMENT_VIEW_LIST)")
	@RequestMapping(value="/builder/category/report{category}-category.spy")
	public ModelAndView executeReportCategorySpy(@PathVariable String category) throws Exception {
		
		List<ReportCategory> currentCategories;
		if(category != null && !category.equals("")){
			currentCategories = reportCategoryDao.findAll(Long.parseLong(category));
		}
		else{
			currentCategories = reportCategoryDao.findAll();
		}
		
		Object[][] categories = new Object[currentCategories.size()][3];
		
		ReportCategory cat;
		for(int i = 0; i < currentCategories.size(); i++){
			cat = currentCategories.get(i);
			categories[i][0] = cat.getId();
			categories[i][1] = cat.getName();
			categories[i][2] = cat.getParentCategory();
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("parent", category);
		mv.addObject("categoriesJson", jsonUtil.getJSONFromObject(categories));
		
		mv.setViewName("/builder/category/report-category");
		return mv;
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_BASE_CATEGORY_MANAGEMENT_VIEW_LIST)")
	@RequestMapping(value="/builder/category/reportCategorySearch.spy")
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

			return JsonUtil.getModelMapError(e);
		}
	}

	@PreAuthorize("hasAnyRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_BASE_CATEGORY_MANAGEMENT_EDIT, T(com.sharifpro.eurb.info.AuthorityType).ROLE_BASE_CATEGORY_MANAGEMENT_CREATE)")
	@RequestMapping(value="/builder/category/reportCategoryStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data) throws Exception {
		try{

			List<ReportCategory> reportCategories = jsonUtil.getListFromRequest(data, ReportCategory.class);
			
			List<Long> insertIds = new ArrayList<Long>(reportCategories.size());
			ReportCategoryPk pk;
			for(ReportCategory reportCategory : reportCategories) {
				if(reportCategory.isNewRecord()) {
					if(SecurityUtil.hasRole(AuthorityType.ROLE_BASE_CATEGORY_MANAGEMENT_CREATE)) {
						pk = reportCategoryDao.insert(reportCategory);
					} else {
						throw new DbConfigDaoException(PropertyProvider.ERROR_NOT_AUTHORIZED_TO_CREATE);
					}
				} else {
					if(SecurityUtil.hasRole(AuthorityType.ROLE_BASE_CATEGORY_MANAGEMENT_EDIT)) {
						pk = reportCategory.createPk();
						reportCategoryDao.update(pk,reportCategory);
					} else {
						throw new DbConfigDaoException(PropertyProvider.ERROR_NOT_AUTHORIZED_TO_EDIT);
					}
				}
				insertIds.add(pk.getId());
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e);
		}
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_BASE_CATEGORY_MANAGEMENT_DELETE)")
	@RequestMapping(value="/builder/category/reportCategoryRemove.spy")
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

			return JsonUtil.getModelMapError(e);
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