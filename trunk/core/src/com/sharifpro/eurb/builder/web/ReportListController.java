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
import org.springframework.web.servlet.ModelAndView;

import com.sharifpro.eurb.builder.dao.ReportCategoryDao;
import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.model.ReportCategory;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.model.ReportDesignPk;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Alireza Sadeghipour
 */
@Controller
public class ReportListController {

	private ReportDesignDao reportDesignDao;
	private ReportCategoryDao reportCategoryDao;

	private JsonUtil jsonUtil;


	@RequestMapping(value="/builder/report/report-list.spy")
	public ModelAndView executeReportListSpy() throws Exception {
		ModelAndView mv = new ModelAndView();

		List<ReportCategory> reportCategories = reportCategoryDao.findAll();
		Object[][] categoriesArr = new Object[reportCategories.size()][2];
		for(int i = 0; i < reportCategories.size(); i++) {
			categoriesArr[i][0] = reportCategories.get(i).getId();
			categoriesArr[i][1] = reportCategories.get(i).getName();
		}

		mv.addObject("categoriesComboContent", jsonUtil.getJSONFromObject(categoriesArr));

		mv.setViewName("/builder/report/report-list");
		return mv;
	}

	@RequestMapping(value="/builder/report/reportSearch.spy")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(defaultValue="", required=false) String query
			,@RequestParam(defaultValue="[]", required=false) String fields
			,@RequestParam(defaultValue="0", required=false) String start
			,@RequestParam(defaultValue=AbstractDAO.DEFAULT_PAGE_SIZE_STR, required=false) String limit
			,@RequestParam(defaultValue=PersistableObject.IDENTIFIER_FIELD, required=false) String sort
			,@RequestParam(defaultValue=AbstractDAO.ASCENDING_SORT_ORDER, required=false) String dir) throws Exception {

		try{
			List<ReportDesign> reportDesigns;
			int totalCount;
			Integer startBy = StringUtils.isEmpty(start) ? 0 : Integer.parseInt(start);
			Integer limitBy = StringUtils.isEmpty(limit) ? AbstractDAO.DEFAULT_PAGE_SIZE : Integer.parseInt(limit);
			List<String> onFields = jsonUtil.getListFromRequest(fields, String.class);
			if(StringUtils.isEmpty(query) || onFields == null || onFields.isEmpty()) {
				reportDesigns = reportDesignDao.findAll(startBy, limitBy, sort, dir);
				totalCount = reportDesignDao.countAll();
			} else {
				reportDesigns = reportDesignDao.findAll(query, onFields, startBy, limitBy, sort, dir);
				totalCount = reportDesignDao.countAll(query, onFields);
			}

			return JsonUtil.getSuccessfulMap(reportDesigns, totalCount);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/builder/report/reportStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data) throws Exception {
		try{

			List<ReportDesign> reportDesigns = jsonUtil.getListFromRequest(data, ReportDesign.class);

			List<Object[]> insertIds = new ArrayList<Object[]>(reportDesigns.size());
			ReportDesignPk pk;
			for(ReportDesign reportDesign : reportDesigns) {
				if(reportDesign.isNewRecord()) {
					pk = reportDesignDao.insert(reportDesign);
				} else {
					pk = reportDesign.createPk();
					reportDesignDao.update(pk,reportDesign);
				}
				insertIds.add(new Object[] {pk.getId()});
			}

			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/builder/report/reportRemove.spy")
	public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {

		try{
			List<Integer> deleteIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<ReportDesignPk> pkList = new ArrayList<ReportDesignPk>(deleteIds.size());
			for(Integer id : deleteIds) {
				pkList.add(new ReportDesignPk(new Long(id)));
			}
			reportDesignDao.deleteAll(pkList);

			return JsonUtil.getSuccessfulMapAfterStore(deleteIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/builder/report/reportActivate.spy")
	public @ResponseBody Map<String,? extends Object> activate(@RequestParam Object data) throws Exception {
		try{

			List<Integer> activateIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<ReportDesignPk> pkList = new ArrayList<ReportDesignPk>(activateIds.size());
			for(Integer id : activateIds) {
				pkList.add(new ReportDesignPk(new Long(id)));
			}
			reportDesignDao.activateAll(pkList);

			return JsonUtil.getSuccessfulMapAfterStore(activateIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/builder/report/reportDeactivate.spy")
	public @ResponseBody Map<String,? extends Object> deactivate(@RequestParam Object data) throws Exception {
		try{

			List<Integer> deactivateIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<ReportDesignPk> pkList = new ArrayList<ReportDesignPk>(deactivateIds.size());
			for(Integer id : deactivateIds) {
				pkList.add(new ReportDesignPk(new Long(id)));
			}
			reportDesignDao.deactivateAll(pkList);

			return JsonUtil.getSuccessfulMapAfterStore(deactivateIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}


	@Autowired
	public void setReportCategoryDao(ReportCategoryDao reportCategoryDao){
		this.reportCategoryDao = reportCategoryDao;
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