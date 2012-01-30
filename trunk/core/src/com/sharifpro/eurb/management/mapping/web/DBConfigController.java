package com.sharifpro.eurb.management.mapping.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
import com.sharifpro.eurb.management.mapping.model.DbConfigPk;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Controller
public class DBConfigController {

	private DbConfigDao dbConfigDao;
	
	private JsonUtil jsonUtil;

	@RequestMapping(value="/management/mapping/dbconfigSearch.spy")
	public @ResponseBody Map<String,? extends Object> search() throws Exception {

		try{
			List<DbConfig> dbConfigs = dbConfigDao.findAll();

			return JsonUtil.getSuccessfulMap(dbConfigs);

		} catch (Exception e) {

			return JsonUtil.getModelMapError("Error retrieving Contacts from database.");
		}
	}

	@RequestMapping(value="/management/mapping/dbconfigStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data) throws Exception {
		try{

			List<DbConfig> dbConfigs = jsonUtil.getListFromRequest(data, DbConfig.class);
			
			List<Long> insertIds = new ArrayList<Long>(dbConfigs.size());
			DbConfigPk pk;
			for(DbConfig dbConf : dbConfigs) {
				if(dbConf.isNewRecord()) {
					pk = dbConfigDao.insert(dbConf);
				} else {
					pk = dbConf.createPk();
					dbConfigDao.update(pk,dbConf);
				}
				insertIds.add(pk.getId());
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError("Error trying to update contact.");
		}
	}

	/*@RequestMapping(value="/sample/delete.action")
	public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {

		try{

			contactService.delete(data);

			Map<String,Object> modelMap = new HashMap<String,Object>(3);
			modelMap.put("success", true);

			return modelMap;

		} catch (Exception e) {

			return getModelMapError("Error trying to delete contact.");
		}
	}*/


	@Autowired
	public void setDbConfigDao(DbConfigDao dbConfigDao) {
		this.dbConfigDao = dbConfigDao;
	}

	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}