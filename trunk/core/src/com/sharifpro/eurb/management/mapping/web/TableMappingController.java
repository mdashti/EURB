package com.sharifpro.eurb.management.mapping.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.comparator.BooleanComparator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.UTF16.StringComparator;
import com.sharifpro.db.meta.ISQLConnection;
import com.sharifpro.db.meta.ITableInfo;
import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.dao.TableMappingDao;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.exception.TableMappingDaoException;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.eurb.management.mapping.model.TableMapping;
import com.sharifpro.eurb.management.mapping.model.TableMappingPk;
import com.sharifpro.util.PropertyProvider;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Controller
public class TableMappingController {

	private DbConfigDao dbConfigDao;
	private TableMappingDao tableMappingDao;
	
	private JsonUtil jsonUtil;

	@RequestMapping(value="/management/mapping/table/tableSearch.spy")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(required=false) Long dbconfig
			,@RequestParam(defaultValue="", required=false) String query
			,@RequestParam(defaultValue="[]", required=false) String fields
			,@RequestParam(defaultValue="tableName", required=false) final String sort
			,@RequestParam(defaultValue=AbstractDAO.ASCENDING_SORT_ORDER, required=false) final String dir) throws Exception {
		
		try{
			DbConfig dbConf = null;
			if(dbconfig == null) {
				List<DbConfig> tmpList = dbConfigDao.findAll(0, 1, PersistableObject.IDENTIFIER_FIELD, AbstractDAO.ASCENDING_SORT_ORDER);
				if(tmpList.size() > 0) {
					dbConf = tmpList.get(0);
				}
			} else {
				dbConf = dbConfigDao.findByPrimaryKey(dbconfig);
			}
			List<TableMapping> tableMappings = new ArrayList<TableMapping>(0);

			if(dbConf != null) {
				List<String> onFields = jsonUtil.getListFromRequest(fields, String.class);
				if(StringUtils.isEmpty(query) || onFields == null || onFields.isEmpty()) {
					tableMappings = tableMappingDao.findAll(dbConf);
					ISQLConnection conn = null;
					try {
						conn = dbConf.getConnection();
						if(conn == null) {
							throw new TableMappingDaoException(PropertyProvider.get("eurb.app.management.table.dbConfigIsInvalid"));
						}
						conn.setReadOnly(true);
						ITableInfo[] tables = dbConf.getTables(conn);
						for(ITableInfo tbl : tables) {
							if(!tableMappings.contains(tbl)) {
								tableMappings.add(new TableMapping(dbConf.getId(), tbl.getCatalogName(), tbl.getSchemaName(), tbl.getSimpleName(), TableMapping.MAPPED_TYPE_TABLE));
							}
						}
						
						ITableInfo[] views = dbConf.getViews(conn);
						for(ITableInfo v : views) {
							if(!tableMappings.contains(v)) {
								tableMappings.add(new TableMapping(dbConf.getId(), v.getCatalogName(), v.getSchemaName(), v.getSimpleName(), TableMapping.MAPPED_TYPE_VIEW));
							}
						}
					} catch (Exception e) {
						throw e;
					} finally {
						if(conn != null) {
							conn.close();
						}
					}
				} else {
					tableMappings = tableMappingDao.findAll(dbConf, query, onFields);
				}
	
				final StringComparator strComp = new StringComparator();
				final BooleanComparator boolComp = new BooleanComparator(true);
				Collections.sort(tableMappings, new Comparator<TableMapping>() {
	
					@Override
					public int compare(TableMapping thiz, TableMapping other) {
						int coef = AbstractDAO.DESCENDING_SORT_ORDER.equals(dir) ? -1 : 1;
						if("tableName".equals(sort)) {
							return coef * strComp.compare(thiz.getTableName(), other.getTableName());
						} else if("catalog".equals(sort)) {
							return coef * strComp.compare(thiz.getCatalog(), other.getCatalog());
						} else if("schema".equals(sort)) {
							return coef * strComp.compare(thiz.getSchema(), other.getSchema());
						} else if("mappedName".equals(sort)) {
							return coef * strComp.compare(thiz.getMappedName(), other.getMappedName());
						} else if("mappedTypeName".equals(sort)) {
							return coef * strComp.compare(thiz.getMappedTypeName(), other.getMappedTypeName());
						} else if("activeForManager".equals(sort)) {
							return coef * boolComp.compare(thiz.isActiveForManager(), other.isActiveForManager());
						} else if("activeForUser".equals(sort)) {
							return coef * boolComp.compare(thiz.isActiveForUser(), other.isActiveForUser());
						}
						return 0;
					}
				});
			}
			return JsonUtil.getSuccessfulMap(tableMappings);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/management/mapping/table/tableStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data) throws Exception {
		try{

			List<TableMapping> tableMappings = jsonUtil.getListFromRequest(data, TableMapping.class);
			
			List<Long> insertIds = new ArrayList<Long>(tableMappings.size());
			TableMappingPk pk;
			for(TableMapping tbl : tableMappings) {
				if(tbl.isNewRecord()) {
					pk = tableMappingDao.insert(tbl);
				} else {
					pk = tbl.createPk();
					tableMappingDao.update(pk,tbl);
				}
				insertIds.add(pk.getId());
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/management/mapping/table/tableRemove.spy")
	public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {

		try{
			List<Integer> deleteIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<TableMappingPk> pkList = new ArrayList<TableMappingPk>(deleteIds.size());
			for(Integer id : deleteIds) {
				pkList.add(new TableMappingPk(new Long(id)));
			}
			tableMappingDao.deleteAll(pkList);
			
			return JsonUtil.getSuccessfulMapAfterStore(deleteIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/management/mapping/table/tableActivate.spy")
	public @ResponseBody Map<String,? extends Object> activate(@RequestParam Object data, @RequestParam(required=true) String target) throws Exception {
		try{

			List<Integer> activateIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<TableMappingPk> pkList = new ArrayList<TableMappingPk>(activateIds.size());
			for(Integer id : activateIds) {
				pkList.add(new TableMappingPk(new Long(id)));
			}
			tableMappingDao.activateAll(pkList, target);
			
			return JsonUtil.getSuccessfulMapAfterStore(activateIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/management/mapping/table/tableDeactivate.spy")
	public @ResponseBody Map<String,? extends Object> deactivate(@RequestParam Object data, @RequestParam(required=true) String target) throws Exception {
		try{

			List<Integer> deactivateIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<TableMappingPk> pkList = new ArrayList<TableMappingPk>(deactivateIds.size());
			for(Integer id : deactivateIds) {
				pkList.add(new TableMappingPk(new Long(id)));
			}
			tableMappingDao.deactivateAll(pkList, target);
			
			return JsonUtil.getSuccessfulMapAfterStore(deactivateIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/management/mapping/db-table.spy")
	public ModelAndView executeTableSpy() throws Exception {
		return executeTableSpy(null);
	}
	
	@RequestMapping(value="/management/mapping/db{dbconfig}-table.spy")
	public ModelAndView executeTableSpy(@PathVariable String dbconfig) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<DbConfig> dbConfList = dbConfigDao.findAllActive();
		Object[][] dbConfArr = new Object[dbConfList.size()][2];
		for(int i = 0; i < dbConfList.size(); i++) {
			dbConfArr[i][0] = dbConfList.get(i).getId();
			dbConfArr[i][1] = dbConfList.get(i).getName();
		}
		mv.addObject("dbConfigComboContent", jsonUtil.getJSONFromObject(dbConfArr));
		Long firstDbConfigId = dbConfArr.length > 0 ? (Long)dbConfArr[0][0] : null;
		mv.addObject("dbconfig", dbconfig == null ? (firstDbConfigId == null ? "" : firstDbConfigId+"") : dbconfig);
		mv.setViewName("/management/mapping/table");
		return mv;
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