package com.sharifpro.eurb.management.mapping.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
import com.sharifpro.eurb.management.mapping.model.DbConfigPk;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Controller
@RequestMapping(value="/management/mapping/*")
public class DBConfigController {

	private DbConfigDao dbConfigDao;
	
	private JsonUtil jsonUtil;

	@RequestMapping(value="/dbconfigSearch.spy")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(defaultValue="", required=false) String query
			,@RequestParam(defaultValue="[]", required=false) String fields
			,@RequestParam(defaultValue="0", required=false) String start
			,@RequestParam(defaultValue=AbstractDAO.DEFAULT_PAGE_SIZE_STR, required=false) String limit) throws Exception {
		
		try{
			List<DbConfig> dbConfigs;
			int totalCount;
			Integer startBy = StringUtils.isEmpty(start) ? 0 : Integer.parseInt(start);
			Integer limitBy = StringUtils.isEmpty(limit) ? AbstractDAO.DEFAULT_PAGE_SIZE : Integer.parseInt(limit);
			List<String> onFields = jsonUtil.getListFromRequest(fields, String.class);
			if(StringUtils.isEmpty(query) || onFields == null || onFields.isEmpty()) {
				dbConfigs = dbConfigDao.findAll(startBy, limitBy);
				totalCount = dbConfigDao.countAll();
			} else {
				dbConfigs = dbConfigDao.findAll(query, onFields, startBy, limitBy);
				totalCount = dbConfigDao.countAll(query, onFields);
			}

			return JsonUtil.getSuccessfulMap(dbConfigs, totalCount);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/dbconfigStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data) throws Exception {
		try{

			List<DbConfig> dbConfigs = jsonUtil.getListFromRequest(data, DbConfig.class);
			
			List<Object[]> insertIds = new ArrayList<Object[]>(dbConfigs.size());
			DbConfigPk pk;
			for(DbConfig dbConf : dbConfigs) {
				if(dbConf.isNewRecord()) {
					pk = dbConfigDao.insert(dbConf);
				} else {
					pk = dbConf.createPk();
					dbConfigDao.update(pk,dbConf);
				}
				insertIds.add(new Object[] {pk.getId(), dbConf.getTestCon()});
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/dbconfigRemove.spy")
	public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {

		try{
			List<Integer> deleteIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<DbConfigPk> pkList = new ArrayList<DbConfigPk>(deleteIds.size());
			for(Integer id : deleteIds) {
				pkList.add(new DbConfigPk(new Long(id)));
			}
			dbConfigDao.deleteAll(pkList);
			
			return JsonUtil.getSuccessfulMapAfterStore(deleteIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/dbconfigActivate.spy")
	public @ResponseBody Map<String,? extends Object> activate(@RequestParam Object data) throws Exception {
		try{

			List<Integer> activateIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<DbConfigPk> pkList = new ArrayList<DbConfigPk>(activateIds.size());
			for(Integer id : activateIds) {
				pkList.add(new DbConfigPk(new Long(id)));
			}
			dbConfigDao.activateAll(pkList);
			
			return JsonUtil.getSuccessfulMapAfterStore(activateIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/dbconfigDeactivate.spy")
	public @ResponseBody Map<String,? extends Object> deactivate(@RequestParam Object data) throws Exception {
		try{

			List<Integer> deactivateIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<DbConfigPk> pkList = new ArrayList<DbConfigPk>(deactivateIds.size());
			for(Integer id : deactivateIds) {
				pkList.add(new DbConfigPk(new Long(id)));
			}
			dbConfigDao.deactivateAll(pkList);
			
			return JsonUtil.getSuccessfulMapAfterStore(deactivateIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}


	@Autowired
	public void setDbConfigDao(DbConfigDao dbConfigDao) {
		this.dbConfigDao = dbConfigDao;
	}

	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}