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
import com.sharifpro.db.meta.TableColumnInfo;
import com.sharifpro.eurb.management.mapping.dao.ColumnMappingDao;
import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.dao.TableMappingDao;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.exception.TableMappingDaoException;
import com.sharifpro.eurb.management.mapping.model.ColumnMapping;
import com.sharifpro.eurb.management.mapping.model.ColumnMappingPk;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.eurb.management.mapping.model.TableMapping;
import com.sharifpro.util.PropertyProvider;
import com.sharifpro.util.json.JsonUtil;

/**
 * Controller - Spring
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Controller
public class ColumnMappingController {

	private DbConfigDao dbConfigDao;
	private TableMappingDao tableMappingDao;
	private ColumnMappingDao columnMappingDao;
	
	private JsonUtil jsonUtil;

	@RequestMapping(value="/management/mapping/column/columnSearch.spy")
	public @ResponseBody Map<String,? extends Object> search(@RequestParam(required=false) Long dbconfig
			,@RequestParam(required=false) Long table
			,@RequestParam(defaultValue="", required=false) String query
			,@RequestParam(defaultValue="[]", required=false) String fields
			,@RequestParam(defaultValue="columnName", required=false) final String sort
			,@RequestParam(defaultValue=AbstractDAO.ASCENDING_SORT_ORDER, required=false) final String dir) throws Exception {
		
		try{
			DbConfig dbConf = null;
			TableMapping tbl = null;
			if(dbconfig == null) {
				if(table == null) {
					List<DbConfig> tmpDbList = dbConfigDao.findAll(0, 1, PersistableObject.IDENTIFIER_FIELD, AbstractDAO.ASCENDING_SORT_ORDER);
					if(tmpDbList.size() > 0) {
						dbConf = tmpDbList.get(0);
						List<TableMapping> tmpTblList = tableMappingDao.findAll(dbConf);
						if(tmpTblList.size() > 0) {
							tbl = tmpTblList.get(0);
						}
					}
				} else {
					tbl = tableMappingDao.findByPrimaryKey(table);
					if(tbl == null) {
						List<DbConfig> tmpDbList = dbConfigDao.findAll(0, 1, PersistableObject.IDENTIFIER_FIELD, AbstractDAO.ASCENDING_SORT_ORDER);
						if(tmpDbList.size() > 0) {
							dbConf = tmpDbList.get(0);
							List<TableMapping> tmpTblList = tableMappingDao.findAll(dbConf);
							if(tmpTblList.size() > 0) {
								tbl = tmpTblList.get(0);
							}
						}
					} else {
						dbConf = dbConfigDao.findByPrimaryKey(tbl.getDbConfigId());
					}
				}
			} else {
				dbConf = dbConfigDao.findByPrimaryKey(dbconfig);
				if(table == null) {
					if(tbl == null) {
						List<TableMapping> tmpTblList = tableMappingDao.findAll(dbConf);
						if(tmpTblList.size() > 0) {
							tbl = tmpTblList.get(0);
						}
					}
				} else {
					tbl = tableMappingDao.findByPrimaryKey(table);
					//it is not secure to give first table to user!
					/*if(tbl == null) {
						List<TableMapping> tmpTblList = tableMappingDao.findAll(dbConf);
						if(tmpTblList.size() > 0) {
							tbl = tmpTblList.get(0);
						}
					}*/
				}
			}
			List<ColumnMapping> columnMappings = new ArrayList<ColumnMapping>(0);

			if(dbConf != null && tbl != null) {
				List<String> onFields = jsonUtil.getListFromRequest(fields, String.class);
				if(StringUtils.isEmpty(query) || onFields == null || onFields.isEmpty()) {
					columnMappings = columnMappingDao.findByTableMapping(tbl.getId());
					ISQLConnection conn = null;
					try {
						conn = dbConf.getConnection();
						if(conn == null) {
							throw new TableMappingDaoException(PropertyProvider.get("eurb.app.management.table.dbConfigIsInvalid"));
						}
						conn.setReadOnly(true);
						TableColumnInfo[] columns = dbConf.getColumns(conn, tbl);
						for(TableColumnInfo col : columns) {
							if(!columnMappings.contains(col)) {
								columnMappings.add(new ColumnMapping(dbConf.getId(),tbl.getId(),col.getColumnName(),col.getTypeName(),col.getDataType(),col.getOrdinalPosition(),null));
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
					columnMappings = columnMappingDao.findAll(tbl, query, onFields);
				}
	
				final StringComparator strComp = new StringComparator();
				final BooleanComparator boolComp = new BooleanComparator(true);
				Collections.sort(columnMappings, new Comparator<ColumnMapping>() {
	
					@Override
					public int compare(ColumnMapping thiz, ColumnMapping other) {
						int coef = AbstractDAO.DESCENDING_SORT_ORDER.equals(dir) ? -1 : 1;
						if("colTypeName".equals(sort)) {
							return coef * strComp.compare(thiz.getColTypeName(), other.getColTypeName());
						} else if("columnName".equals(sort)) {
							return coef * strComp.compare(thiz.getColumnName(), other.getColumnName());
						} else if("formatPattern".equals(sort)) {
							return coef * strComp.compare(thiz.getFormatPattern(), other.getFormatPattern());
						} else if("mappedName".equals(sort)) {
							return coef * strComp.compare(thiz.getMappedName(), other.getMappedName());
						} else if("referencedIdCol".equals(sort)) {
							return coef * strComp.compare(thiz.getReferencedIdCol(), other.getReferencedIdCol());
						} else if("referencedTable".equals(sort)) {
							return coef * strComp.compare(thiz.getReferencedTable(), other.getReferencedTable());
						} else if("referencedValueCol".equals(sort)) {
							return coef * strComp.compare(thiz.getReferencedValueCol(), other.getReferencedValueCol());
						} else if("staticMapping".equals(sort)) {
							return coef * strComp.compare(thiz.getStaticMapping(), other.getStaticMapping());
						} else if("colDataType".equals(sort)) {
							return coef * new Integer(thiz.getColDataType()).compareTo(other.getColDataType());
						} else if("colOrder".equals(sort)) {
							return coef * new Integer(thiz.getColOrder()).compareTo(other.getColOrder());
						} else if("activeForManager".equals(sort)) {
							return coef * boolComp.compare(thiz.isActiveForManager(), other.isActiveForManager());
						} else if("activeForUser".equals(sort)) {
							return coef * boolComp.compare(thiz.isActiveForUser(), other.isActiveForUser());
						}
						return 0;
					}
				});
			}
			return JsonUtil.getSuccessfulMap(columnMappings);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/management/mapping/column/columnStore.spy")
	public @ResponseBody Map<String,? extends Object> store(@RequestParam Object data) throws Exception {
		try{

			List<ColumnMapping> columnMappings = jsonUtil.getListFromRequest(data, ColumnMapping.class);
			
			List<Long> insertIds = new ArrayList<Long>(columnMappings.size());
			ColumnMappingPk pk;
			for(ColumnMapping col : columnMappings) {
				if(col.isNewRecord()) {
					pk = columnMappingDao.insert(col);
				} else {
					pk = col.createPk();
					columnMappingDao.update(pk,col);
				}
				insertIds.add(pk.getId());
			}
			
			return JsonUtil.getSuccessfulMapAfterStore(insertIds);

		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/management/mapping/column/columnRemove.spy")
	public @ResponseBody Map<String,? extends Object> delete(@RequestParam Object data) throws Exception {

		try{
			List<Integer> deleteIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<ColumnMappingPk> pkList = new ArrayList<ColumnMappingPk>(deleteIds.size());
			for(Integer id : deleteIds) {
				pkList.add(new ColumnMappingPk(new Long(id)));
			}
			columnMappingDao.deleteAll(pkList);
			
			return JsonUtil.getSuccessfulMapAfterStore(deleteIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/management/mapping/column/columnActivate.spy")
	public @ResponseBody Map<String,? extends Object> activate(@RequestParam Object data, @RequestParam(required=true) String target) throws Exception {
		try{

			List<Integer> activateIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<ColumnMappingPk> pkList = new ArrayList<ColumnMappingPk>(activateIds.size());
			for(Integer id : activateIds) {
				pkList.add(new ColumnMappingPk(new Long(id)));
			}
			columnMappingDao.activateAll(pkList, target);
			
			return JsonUtil.getSuccessfulMapAfterStore(activateIds);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e.getMessage());
		}
	}
	
	@RequestMapping(value="/management/mapping/column/columnDeactivate.spy")
	public @ResponseBody Map<String,? extends Object> deactivate(@RequestParam Object data, @RequestParam(required=true) String target) throws Exception {
		try{

			List<Integer> deactivateIds = jsonUtil.getListFromRequest(data, Integer.class);
			List<ColumnMappingPk> pkList = new ArrayList<ColumnMappingPk>(deactivateIds.size());
			for(Integer id : deactivateIds) {
				pkList.add(new ColumnMappingPk(new Long(id)));
			}
			columnMappingDao.deactivateAll(pkList, target);

			return JsonUtil.getSuccessfulMapAfterStore(deactivateIds);
		} catch (Exception e) {
			return JsonUtil.getModelMapError(e.getMessage());
		}
	}

	@RequestMapping(value="/management/mapping/db-table-column.spy")
	public ModelAndView executeColumnSpy() throws Exception {
		return executeColumnSpy(null,null);
	}

	@RequestMapping(value="/management/mapping/db{dbconfig}-table-column.spy")
	public ModelAndView executeColumnSpy(@PathVariable Long dbconfig) throws Exception {
		return executeColumnSpy(dbconfig, null);
	}

	@RequestMapping(value="/management/mapping/db{dbconfig}-table{table}-column.spy")
	public ModelAndView executeColumnSpy(@PathVariable Long dbconfig, @PathVariable Long table) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<DbConfig> dbConfList = dbConfigDao.findAllActive();
		DbConfig selectedDbConfig = null;
		Object[][] dbConfArr = new Object[dbConfList.size()][2];
		for(int i = 0; i < dbConfList.size(); i++) {
			dbConfArr[i][0] = dbConfList.get(i).getId();
			dbConfArr[i][1] = dbConfList.get(i).getName();
			if(dbConfList.get(i).getId().equals(dbconfig)) {
				selectedDbConfig = dbConfList.get(i);
			}
		}
		if(selectedDbConfig == null) {
			if(dbConfList.size() > 0) {
				selectedDbConfig = dbConfList.get(0);
				mv.addObject("dbconfig", selectedDbConfig.getId()+"");
			} else {
				mv.addObject("dbconfig", "");
			}
		} else {
			mv.addObject("dbconfig", selectedDbConfig.getId()+"");
		}
		mv.addObject("dbConfigComboContent", jsonUtil.getJSONFromObject(dbConfArr));
		
		if(selectedDbConfig != null) {
			List<TableMapping> tableList = tableMappingDao.findAll(selectedDbConfig);
			TableMapping selectedTable = null;
			Object[][] tableArr = new Object[tableList.size()][2];
			for(int i = 0; i < tableList.size(); i++) {
				tableArr[i][0] = tableList.get(i).getId();
				tableArr[i][1] = tableList.get(i).getMappedName();
				if(tableList.get(i).getId().equals(table)) {
					selectedTable = tableList.get(i);
				}
			}
			if(selectedTable == null) {
				if(tableList.size() > 0) {
					selectedTable = tableList.get(0);
					mv.addObject("table", selectedTable.getId()+"");
				} else {
					mv.addObject("table", "");
				}
			} else {
				mv.addObject("table", selectedTable.getId()+"");
			}
			mv.addObject("tableComboContent", jsonUtil.getJSONFromObject(tableArr));
		} else {
			mv.addObject("table", "");
			mv.addObject("tableComboContent", "[]");
		}
		mv.setViewName("/management/mapping/column");
		return mv;
	}

	@Autowired
	public void setColumnMappingDao(ColumnMappingDao columnMappingDao) {
		this.columnMappingDao = columnMappingDao;
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