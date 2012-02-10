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

import com.sharifpro.db.meta.ISQLConnection;
import com.sharifpro.db.meta.ITableInfo;
import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.dao.TableMappingDao;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
import com.sharifpro.eurb.management.mapping.model.TableMapping;
import com.sharifpro.eurb.management.mapping.model.TableMappingPk;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Controller
@RequestMapping(value="/management/mapping/table/*")
public class TableMappingController {

	private DbConfigDao dbConfigDao;
	private TableMappingDao tableMappingDao;
	
	private JsonUtil jsonUtil;

	@RequestMapping(value="/tableSearch.spa")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(required=false) Long dbconfig,@RequestParam(defaultValue="", required=false) String query
			,@RequestParam(defaultValue="[]", required=false) String fields) throws Exception {
		
		try{
			DbConfig dbConf;
			if(dbconfig == null) {
				dbConf = dbConfigDao.findAll(0, 1).get(0);
			} else {
				dbConf = dbConfigDao.findByPrimaryKey(dbconfig);
			}
			List<TableMapping> tableMappings = new ArrayList<TableMapping>(0);
			List<String> onFields = jsonUtil.getListFromRequest(fields, String.class);
			if(StringUtils.isEmpty(query) || onFields == null || onFields.isEmpty()) {
				tableMappings = tableMappingDao.findAll();
				ISQLConnection conn = null;
				try {
					conn = dbConf.getConnection();
					conn.setReadOnly(true);
					ITableInfo[] tables = dbConf.getTables(conn);
					for(ITableInfo tbl : tables) {
						tableMappings.add(new TableMapping(dbConf.getId(), tbl.getCatalogName(), tbl.getSchemaName(), tbl.getSimpleName(), TableMapping.MAPPED_TYPE_TABLE));
					}
					
					ITableInfo[] views = dbConf.getViews(conn);
					for(ITableInfo v : views) {
						tableMappings.add(new TableMapping(dbConf.getId(), v.getCatalogName(), v.getSchemaName(), v.getSimpleName(), TableMapping.MAPPED_TYPE_VIEW));
					}
				} catch (Exception e) {
					throw e;
				} finally {
					if(conn != null) {
						conn.close();
					}
				}
			} else {
				//tableMappings = tableMappingDao.findAll(query, onFields);
			}

			return JsonUtil.getSuccessfulMap(tableMappings);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/tableStore.spa")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data) throws Exception {
		try{

			List<TableMapping> tableMappings = jsonUtil.getListFromRequest(data, TableMapping.class);
			
			List<Long> insertIds = new ArrayList<Long>(tableMappings.size());
			TableMappingPk pk;
			for(TableMapping dbConf : tableMappings) {
				if(dbConf.isNewRecord()) {
					pk = tableMappingDao.insert(dbConf);
				} else {
					pk = dbConf.createPk();
					tableMappingDao.update(pk,dbConf);
				}
				insertIds.add(pk.getId());
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}


	@Autowired
	public void setTableMappingDao(TableMappingDao tableMappingDao) {
		this.tableMappingDao = tableMappingDao;
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