package com.sharifpro.eurb.builder.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sharifpro.db.DBBean;
import com.sharifpro.db.meta.ISQLConnection;
import com.sharifpro.db.meta.TableColumnInfo;
import com.sharifpro.eurb.builder.dao.ReportCategoryDao;
import com.sharifpro.eurb.builder.dao.ReportColumnDao;
import com.sharifpro.eurb.builder.dao.ReportDatasetDao;
import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.exception.ReportExecutionHistoryDaoException;
import com.sharifpro.eurb.builder.intermediate.ExtGridColumn;
import com.sharifpro.eurb.builder.intermediate.ExtStoreField;
import com.sharifpro.eurb.builder.model.ReportCategory;
import com.sharifpro.eurb.builder.model.ReportColumn;
import com.sharifpro.eurb.builder.model.ReportDataset;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.dao.TableMappingDao;
import com.sharifpro.eurb.management.mapping.exception.TableMappingDaoException;
import com.sharifpro.eurb.management.mapping.model.ColumnMapping;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
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
		//All Datasets must be in the same dbConfig
		Long dbConfigId = null;
		
		//find report design with given id
		ReportDesign reportDesign = reportDesignDao.findByPrimaryKey(report, version);
		
		List<ReportDataset> datasetList = reportDatasetDao.findAll(reportDesign);
		List<ReportColumn> columnList = reportColumnDao.findAll(reportDesign);
		
		
		//UI
		List<ExtStoreField> storeFields = new ArrayList<ExtStoreField>(columnList.size());
		storeFields.add(new ExtStoreField("id"));
		List<ExtGridColumn> gridColumns = new ArrayList<ExtGridColumn>(columnList.size());
		
		int totalWidth = 0;
		for(ReportColumn col : columnList) {
			String key = "t"+col.getDatasetId()+"_"+col.getColumnMapping().getColumnName() + col.getColumnMappingId();
			storeFields.add(new ExtStoreField(key));
			gridColumns.add(new ExtGridColumn(col.getColumnHeader(), key, col.getColumnWidth()));
			totalWidth += col.getColumnWidth();
		}
		gridColumns.add(0,new ExtGridColumn("radif", "id", (int) (0.05 * totalWidth)));
		
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
	public @ResponseBody Map<String,? extends Object> executeRunReportData(@PathVariable Long report, @PathVariable Long version) throws Exception {
		try{
			//All Datasets must be in the same dbConfig
			Long dbConfigId = null;
			
			//find report design with given id
			ReportDesign reportDesign = reportDesignDao.findByPrimaryKey(report, version);
			
			List<ReportDataset> datasetList = reportDatasetDao.findAll(reportDesign);
			List<ReportColumn> columnList = reportColumnDao.findAll(reportDesign);
			
			//Build QUERY
			StringBuilder query = new StringBuilder();
			StringBuilder querySelect = new StringBuilder("Select ");
			StringBuilder queryFrom = new StringBuilder(" From ");
			StringBuilder queryWhere = new StringBuilder(" WHERE 1=1 ");
	
			boolean firstDS = true;
			for(ReportDataset ds : datasetList) {
				if(DBBean.isValidIdentifier(ds.getTableMappingId())) { // is directly bound to table
					TableMapping table = tableMappingDao.findByPrimaryKey(ds.getTableMappingId());
					if(!firstDS){
						queryFrom.append(", ");
					}
					if(!StringUtils.isEmpty(table.getCatalog())) {
						queryFrom.append(table.getCatalog()).append(".");
					}
					queryFrom.append(table.getTableName()).append(" t").append(ds.getId());
					dbConfigId = table.getDbConfigId();
				} else { //is a report base on report
					//TODO report based on report must be handled
				}
				if(firstDS) {
					firstDS = false;
				}
			}
			
			boolean firstCol = true;
			for(ReportColumn col : columnList) {
				if(!firstCol) {
					querySelect.append(", ");
				}
				querySelect.append("t").append(col.getDatasetId()).append(".").append(col.getColumnMapping().getColumnName());
				
				if(firstCol) {
					firstCol = false;
				}
			}
			
			query.append(querySelect).append(queryFrom).append(queryWhere);
			
			
			//Execute QUERY
			DbConfig dbConf = dbConfigDao.findByPrimaryKey(dbConfigId);
			ISQLConnection conn = null;
			List<Map<String, Object>> resultList = new LinkedList<Map<String,Object>>();
			try {
				conn = dbConf.getConnection();
				if(conn == null) {
					throw new ReportExecutionHistoryDaoException(PropertyProvider.get("eurb.app.management.table.dbConfigIsInvalid"));
				}
				conn.setReadOnly(true);
				Map<String,Object> result;
				DBBean db = new DBBean(dbConf.getDataSource());
				
				db.executeQuery(query.toString());
				
				int counter = 1;
				while(db.result.next()) {
					result = new HashMap<String, Object>();
					result.put("id", counter++);
					for(ReportColumn col : columnList) {
						String key = "t"+col.getDatasetId()+"_"+col.getColumnMapping().getColumnName() + col.getColumnMappingId();
						result.put(key, db.result.getObject("t"+col.getDatasetId()+"."+col.getColumnMapping().getColumnName()));
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
			
			return JsonUtil.getSuccessfulMap(resultList);

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
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}