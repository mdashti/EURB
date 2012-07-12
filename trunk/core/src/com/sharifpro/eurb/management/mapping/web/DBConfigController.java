package com.sharifpro.eurb.management.mapping.web;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sharifpro.db.meta.SQLDriver;
import com.sharifpro.eurb.info.AuthorityType;
import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.exception.DbConfigDaoException;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
import com.sharifpro.eurb.management.mapping.model.DbConfigPk;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.util.PropertyProvider;
import com.sharifpro.util.SecurityUtil;
import com.sharifpro.util.json.JsonUtil;
import com.sharifpro.util.xml.XMLObjectCache;

/**
 * Controller - Spring
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Controller
public class DBConfigController {

	private DbConfigDao dbConfigDao;
	
	private JsonUtil jsonUtil;

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_BASE_DB_CONFIG_VIEW_LIST)")
	@RequestMapping(value="/management/mapping/dbconfig.spy")
	public ModelAndView executeDbConfigSpy() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/management/mapping/dbconfig");
		return mv;
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_BASE_DB_CONFIG_VIEW_LIST)")
	@RequestMapping(value="/management/mapping/dbconfig/dbconfigSearch.spy")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(defaultValue="", required=false) String query
			,@RequestParam(defaultValue="[]", required=false) String fields
			,@RequestParam(defaultValue="0", required=false) String start
			,@RequestParam(defaultValue=AbstractDAO.DEFAULT_PAGE_SIZE_STR, required=false) String limit
			,@RequestParam(defaultValue=PersistableObject.IDENTIFIER_FIELD, required=false) String sort
			,@RequestParam(defaultValue=AbstractDAO.ASCENDING_SORT_ORDER, required=false) String dir) throws Exception {
		
		try{
			List<DbConfig> dbConfigs;
			int totalCount;
			Integer startBy = StringUtils.isEmpty(start) ? 0 : Integer.parseInt(start);
			Integer limitBy = StringUtils.isEmpty(limit) ? AbstractDAO.DEFAULT_PAGE_SIZE : Integer.parseInt(limit);
			List<String> onFields = jsonUtil.getListFromRequest(fields, String.class);
			if(StringUtils.isEmpty(query) || onFields == null || onFields.isEmpty()) {
				dbConfigs = dbConfigDao.findAll(startBy, limitBy, sort, dir);
				totalCount = dbConfigDao.countAll();
			} else {
				dbConfigs = dbConfigDao.findAll(query, onFields, startBy, limitBy, sort, dir);
				totalCount = dbConfigDao.countAll(query, onFields);
			}

			return JsonUtil.getSuccessfulMap(dbConfigs, totalCount);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e);
		}
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/management/mapping/dbconfig/driverSearch.spy")
	public @ResponseBody Map<String,? extends Object> driverSearch() throws Exception {
		
		try{
			final XMLObjectCache<SQLDriver> _cache = new XMLObjectCache<SQLDriver>();
			
			URL url = DBConfigController.class.getClassLoader().getResource("default_drivers.xml");
			
			InputStreamReader isr = new InputStreamReader(url.openStream());
			try {
				_cache.load(isr, null, true);
			} finally {
				isr.close();
			}
			
			Iterator<SQLDriver> itr = _cache.getAllForClass(SQLDriver.class);
			List<SQLDriver> sqlDrivers = new LinkedList<SQLDriver>();
			while (itr.hasNext()) {
				SQLDriver driver = itr.next();
				sqlDrivers.add(driver);
			}
			return JsonUtil.getSuccessfulMap(sqlDrivers);
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e);
		}
	}

	@PreAuthorize("hasAnyRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_BASE_DB_CONFIG_EDIT, T(com.sharifpro.eurb.info.AuthorityType).ROLE_BASE_DB_CONFIG_CREATE)")
	@RequestMapping(value="/management/mapping/dbconfig/dbconfigStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data) throws Exception {
		try{

			List<DbConfig> dbConfigs = jsonUtil.getListFromRequest(data, DbConfig.class);
			
			List<Object[]> insertIds = new ArrayList<Object[]>(dbConfigs.size());
			DbConfigPk pk;
			for(DbConfig dbConf : dbConfigs) {
				if(dbConf.isNewRecord()) {
					if(SecurityUtil.hasRole(AuthorityType.ROLE_BASE_DB_CONFIG_CREATE)) {
						pk = dbConfigDao.insert(dbConf);
					} else {
						throw new DbConfigDaoException(PropertyProvider.ERROR_NOT_AUTHORIZED_TO_CREATE);
					}
				} else {
					if(SecurityUtil.hasRole(AuthorityType.ROLE_BASE_DB_CONFIG_EDIT)) {
						pk = dbConf.createPk();
						dbConfigDao.update(pk,dbConf);
					} else {
						throw new DbConfigDaoException(PropertyProvider.ERROR_NOT_AUTHORIZED_TO_EDIT);
					}
				}
				insertIds.add(new Object[] {pk.getId(), dbConf.getTestCon()});
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e);
		}
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_BASE_DB_CONFIG_DELETE)")
	@RequestMapping(value="/management/mapping/dbconfig/dbconfigRemove.spy")
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

			return JsonUtil.getModelMapError(e);
		}
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_BASE_DB_CONFIG_EDIT)")
	@RequestMapping(value="/management/mapping/dbconfig/dbconfigActivate.spy")
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

			return JsonUtil.getModelMapError(e);
		}
	}

	@PreAuthorize("hasRole(T(com.sharifpro.eurb.info.AuthorityType).ROLE_BASE_DB_CONFIG_EDIT)")
	@RequestMapping(value="/management/mapping/dbconfig/dbconfigDeactivate.spy")
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

			return JsonUtil.getModelMapError(e);
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