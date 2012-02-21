package com.sharifpro.eurb.builder.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.management.mapping.dao.TableMappingDao;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.eurb.management.mapping.model.TableMapping;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Alireza Sadeghipour
 */
@Controller
public class ReportDesignController {

	
	private JsonUtil jsonUtil;

	@RequestMapping(value="/builder/report/report-design.spy")
	public ModelAndView executeReportDesignSpy() throws Exception {
		return executeReportDesignSpy(null);
	}
	
	@RequestMapping(value="/builder/report/report{report}-design.spy")
	public ModelAndView executeReportDesignSpy(@PathVariable String report) throws Exception {
		ModelAndView mv = new ModelAndView();
		//List<DbConfig> dbConfList = dbConfigDao.findAllActive();
		//Object[][] dbConfArr = new Object[dbConfList.size()][2];
		//for(int i = 0; i < dbConfList.size(); i++) {
			//dbConfArr[i][0] = dbConfList.get(i).getId();
			//dbConfArr[i][1] = dbConfList.get(i).getName();
		//}
		//mv.addObject("dbConfigComboContent", jsonUtil.getJSONFromObject(dbConfArr));
		//Long firstDbConfigId = dbConfArr.length > 0 ? (Long)dbConfArr[0][0] : null;
		mv.addObject("report", report);
		mv.setViewName("/builder/report/report-design");
		return mv;
	}
	
	@RequestMapping(value="/builder/report/tablesList.spy")
	public @ResponseBody Map<String,? extends Object> loadTablesList(@RequestParam(defaultValue="", required=false) String query) throws Exception {
		TableMappingDao tableMappingDao = DaoFactory.createTableMappingDao();
		try{
			List<TableMapping> tableMappings;
			int totalCount;
			List<String> onFields = new ArrayList<String>();
			onFields.add("mappedName");
			if(StringUtils.isEmpty(query)) {
				tableMappings = tableMappingDao.findAll();
				totalCount = tableMappingDao.countAll();
			} else {
				tableMappings = tableMappingDao.findAll(query, onFields);
				totalCount = tableMappingDao.countAll(query, onFields);
			}

			return JsonUtil.getSuccessfulMap(tableMappings, totalCount);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}


	

	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}