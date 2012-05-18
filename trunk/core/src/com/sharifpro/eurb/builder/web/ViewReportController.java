package com.sharifpro.eurb.builder.web;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
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

import com.sharifpro.db.DBBean;
import com.sharifpro.db.dialects.HibernateDialect;
import com.sharifpro.db.meta.ISQLConnection;
import com.sharifpro.db.meta.TableColumnInfo;
import com.sharifpro.eurb.builder.dao.ReportCategoryDao;
import com.sharifpro.eurb.builder.dao.ReportColumnDao;
import com.sharifpro.eurb.builder.dao.ReportDatasetDao;
import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.dao.ReportFilterDao;
import com.sharifpro.eurb.builder.exception.ReportExecutionHistoryDaoException;
import com.sharifpro.eurb.builder.intermediate.ExtGridColumn;
import com.sharifpro.eurb.builder.intermediate.ExtStoreField;
import com.sharifpro.eurb.builder.model.ReportCategory;
import com.sharifpro.eurb.builder.model.ReportColumn;
import com.sharifpro.eurb.builder.model.ReportDataset;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.model.ReportFilter;
import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.dao.TableMappingDao;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.exception.TableMappingDaoException;
import com.sharifpro.eurb.management.mapping.model.ColumnMapping;
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
public class ViewReportController {

	private ReportDesignDao reportDesignDao;
	private ReportCategoryDao reportCategoryDao;
	private ReportDatasetDao reportDatasetDao;
	private ReportColumnDao reportColumnDao;
	private TableMappingDao tableMappingDao;
	private DbConfigDao dbConfigDao;
	private ReportFilterDao reportFilterDao;
	private JsonUtil jsonUtil;


	@RequestMapping(value="/builder/report/view-report.spy")
	public ModelAndView executeViewReportList() throws Exception {
		ModelAndView mv = new ModelAndView();

		List<ReportCategory> reportCategories = reportCategoryDao.findAll();
		Object[][] categoriesArr = new Object[reportCategories.size()][2];
		for(int i = 0; i < reportCategories.size(); i++) {
			categoriesArr[i][0] = reportCategories.get(i).getId();
			categoriesArr[i][1] = reportCategories.get(i).getName();
		}

		mv.addObject("categoriesComboContent", jsonUtil.getJSONFromObject(categoriesArr));

		mv.setViewName("/builder/report/view-report");
		return mv;
	}
	@RequestMapping(value="/builder/report/run-report{report}-v{version}.spy")
	public ModelAndView executeRunReport(@PathVariable Long report, @PathVariable Long version) throws Exception {
		//find report design with given id
		ReportDesign reportDesign = reportDesignDao.findByPrimaryKey(report, version);
		List<ReportColumn> columnList = reportColumnDao.findAllSortByColOrder(reportDesign);
		
		//UI
		List<ExtStoreField> storeFields = new ArrayList<ExtStoreField>(columnList.size());
		storeFields.add(new ExtStoreField("id"));
		List<ExtGridColumn> gridColumns = new ArrayList<ExtGridColumn>(columnList.size());
		
		int totalWidth = 0;
		for(ReportColumn col : columnList) {
			String key = col.getColumnKey();
			storeFields.add(new ExtStoreField(key));
			gridColumns.add(new ExtGridColumn(col.getColumnHeader(), key, col.getColumnWidth(), col.getColumnAlign(), "direction:"+col.getColumnDir()+";"));
			totalWidth += col.getColumnWidth();
		}
		gridColumns.add(0,new ExtGridColumn(PropertyProvider.get("eurb.app.builder.runreport.grid.radif"), "id", (int) (0.05 * totalWidth), "center", "direction:ltr;"));
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("report", report);
		mv.addObject("reportVersion", version);
		mv.addObject("reportName", reportDesign.getName());
		mv.addObject("storeFields", jsonUtil.getJSONFromObject(storeFields));
		mv.addObject("gridColumns", jsonUtil.getJSONFromObject(gridColumns));
		
		mv.setViewName("/builder/report/run-report");
		return mv;
	}
	
	@RequestMapping(value="/builder/report/get-reportdata{report}-v{version}.spy")
	public @ResponseBody Map<String,? extends Object> executeRunReportData(@PathVariable Long report, @PathVariable Long version
			,@RequestParam(required=false) String start
			,@RequestParam(required=false) String limit
			,@RequestParam(required=false) String sort
			,@RequestParam(required=false) String dir) throws Exception {
		try{
			//All Datasets must be in the same dbConfig
			Long dbConfigId = null;
			
			//find report design with given id
			ReportDesign reportDesign = reportDesignDao.findByPrimaryKey(report, version);
			List<ReportDataset> datasetList = reportDatasetDao.findAll(reportDesign);
			List<ReportColumn> columnList = reportColumnDao.findAll(reportDesign);
			Map<Long, ReportColumn> columnMap = new HashMap<Long, ReportColumn>();
			List<ReportFilter> reportFilters = reportFilterDao.findAll(reportDesign.getId());
			
			//Build QUERY
			///Build SELECT Part
			StringBuilder querySelectSB = new StringBuilder("Select ");	
			boolean firstCol = true;
			for(ReportColumn col : columnList) {
				//@ TODO key for this map and all its usages must be col.getId()
				columnMap.put(col.getColumnMappingId(), col);
				if(!firstCol) {
					querySelectSB.append(", ");
				}
				querySelectSB.append(col.getDatabaseKey());
				
				if(firstCol) {
					firstCol = false;
				}
			}
			//Build FROM part
			StringBuilder queryFromSB = new StringBuilder(" From ");
			boolean firstDS = true;
			for(ReportDataset ds : datasetList) {
				if(DBBean.isValidIdentifier(ds.getTableMappingId())) { // is directly bound to table
					TableMapping table = tableMappingDao.findByPrimaryKey(ds.getTableMappingId());
					if(!firstDS){
						queryFromSB.append(", ");
					}
					queryFromSB.append(table.getTableFullName()).append(" t").append(ds.getId());
					dbConfigId = table.getDbConfigId();
				} else { //is a report base on report
					//TODO report based on report must be handled
				}
				if(firstDS) {
					firstDS = false;
				}
			}
			//Build Where part
			StringBuilder queryWhereSB = new StringBuilder();
			ReportFilter.ReportFilterOperator oper;
			boolean firstFilter = true;
			for(ReportFilter filter : reportFilters) {
				ReportColumn relatedCol = columnMap.get(filter.getColumnMappingId());
				if(relatedCol != null) {
					oper = filter.getOperatorObj();
					if(firstFilter) {
						queryWhereSB.append(" WHERE ");
						firstFilter = false;
					}
					queryWhereSB.append(" ").append(relatedCol.getDatabaseKey()).append(" ").append(filter.getOperator()).append(" ");
					if(oper.numberOfOperands == 0) {
						queryWhereSB.append(oper.operator);
					} else if(oper.numberOfOperands == 1) {
						queryWhereSB.append("?");
					} else if(oper.numberOfOperands == 2) {
						queryWhereSB.append("? and ?");
					}
				}
			}
			
			//Build ORDER BY part
			StringBuilder querySortSB = new StringBuilder();
			List<ReportColumn> sortCols = new LinkedList<ReportColumn>();
			for(ReportColumn col : columnList) {
				if(col.getSortType() != null && (ReportColumn.SORT_TYPE_ASC.equals(col.getSortType()) || ReportColumn.SORT_TYPE_DESC.equals(col.getSortType()))) {
					sortCols.add(col);
				}
			}
			if(!sortCols.isEmpty()) {
				querySortSB.append(" ORDER BY ");
				Collections.sort(sortCols, new ReportColumn.ReportColumnSortOrderComparator());
				boolean firstSort = true;
				for(ReportColumn col : sortCols) {
					if(!firstSort){
						querySortSB.append(", ");
					}
					querySortSB.append(col.getDatabaseKey()).append(" ").append(col.getSortType() == ReportColumn.SORT_TYPE_DESC ? "DESC" : "ASC");
					if(firstSort) {
						firstSort = false;
					}
				}
			}
			
			String querySelect = querySelectSB.toString();
			String queryFrom = queryFromSB.toString();
			String queryWhere = queryWhereSB.toString();
			String querySort = querySortSB.toString();
			
			
			//Execute QUERY
			DbConfig dbConf = dbConfigDao.findByPrimaryKey(dbConfigId);
			ISQLConnection conn = null;
			List<Map<String, Object>> resultList = new LinkedList<Map<String,Object>>();
			int total = 0;
			try {
				conn = dbConf.getConnection();
				if(conn == null) {
					throw new ReportExecutionHistoryDaoException(PropertyProvider.get("eurb.app.management.table.dbConfigIsInvalid"));
				}
				conn.setReadOnly(true);

				HibernateDialect dialect = dbConf.getDialect();
				
				Map<String,Object> result;
				DBBean db = new DBBean(dbConf.getDataSource());
				String countQuery = dialect.buildCountQuery(querySelect, queryFrom, queryWhere);
				if(queryWhere.isEmpty()) {
					db.executeQuery(countQuery);
				} else {
					int index = 1;
					db.prepareStatement(countQuery);
					for(ReportFilter filter : reportFilters) {
						oper = filter.getOperatorObj();
						if(oper.numberOfOperands == 1) {
							db.pstmt.setObject(index++, filter.getOperand1());
						} else if(oper.numberOfOperands == 2) {
							db.pstmt.setObject(index++, filter.getOperand1());
							db.pstmt.setObject(index++, filter.getOperand2());
						}
					}
					db.executePrepared();
				}
				if(db.result.next()) {
					total = db.result.getInt(1);
				}
				
				String finalQuery;
				int counter = 1;
				if(start == null || limit == null) {
					finalQuery = dialect.buildQuery(querySelect,queryFrom,queryWhere, querySort);
				} else {
					finalQuery = dialect.buildQuery(querySelect,queryFrom,queryWhere, querySort, Integer.parseInt(start), Integer.parseInt(limit));
					counter += Integer.parseInt(start);
				}

				if(queryWhere.isEmpty()) {
					db.executeQuery(finalQuery);
				} else {
					int index = 1;
					db.prepareStatement(finalQuery);
					for(ReportFilter filter : reportFilters) {
						oper = filter.getOperatorObj();
						if(oper.numberOfOperands == 1) {
							db.pstmt.setObject(index++, filter.getOperand1());
						} else if(oper.numberOfOperands == 2) {
							db.pstmt.setObject(index++, filter.getOperand1());
							db.pstmt.setObject(index++, filter.getOperand2());
						}
					}
					db.executePrepared();
				}
				while(db.result.next()) {
					result = new HashMap<String, Object>();
					result.put("id", counter++);
					for(ReportColumn col : columnList) {
						result.put(col.getColumnKey(), db.result.getObject(col.getDatabaseKey()));
					}
					resultList.add(result);
				}
			} catch (Exception e) {
				throw e;
			} finally {
				if(conn != null) {
					conn.close();
				}
			}
			
			return JsonUtil.getSuccessfulMap(resultList, total);

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
	public void setReportDatasetDao(ReportDatasetDao reportDatasetDao) {
		this.reportDatasetDao = reportDatasetDao;
	}

	@Autowired
	public void setReportColumnDao(ReportColumnDao reportColumnDao) {
		this.reportColumnDao = reportColumnDao;
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
	public void setReportFilterDao(ReportFilterDao reportFilterDao) {
		this.reportFilterDao = reportFilterDao;
	}

	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}