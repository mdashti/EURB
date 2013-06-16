package com.sharifpro.eurb.builder.web;


import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sharifpro.db.DBBean;
import com.sharifpro.db.dialects.HibernateDialect;
import com.sharifpro.db.meta.ISQLConnection;
import com.sharifpro.db.util.StringUtilities;
import com.sharifpro.eurb.builder.dao.GroupAggregationDao;
import com.sharifpro.eurb.builder.dao.ReportCategoryDao;
import com.sharifpro.eurb.builder.dao.ReportChartAxisDao;
import com.sharifpro.eurb.builder.dao.ReportChartDao;
import com.sharifpro.eurb.builder.dao.ReportColumnDao;
import com.sharifpro.eurb.builder.dao.ReportDatasetDao;
import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.dao.ReportFilterDao;
import com.sharifpro.eurb.builder.exception.ReportChartDaoException;
import com.sharifpro.eurb.builder.exception.ReportExecutionHistoryDaoException;
import com.sharifpro.eurb.builder.intermediate.ExtGridColumn;
import com.sharifpro.eurb.builder.intermediate.ExtStoreField;
import com.sharifpro.eurb.builder.model.GroupAggregation;
import com.sharifpro.eurb.builder.model.ReportCategory;
import com.sharifpro.eurb.builder.model.ReportChart;
import com.sharifpro.eurb.builder.model.ReportChartAxis;
import com.sharifpro.eurb.builder.model.ReportColumn;
import com.sharifpro.eurb.builder.model.ReportDataset;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.model.ReportFilter;
import com.sharifpro.eurb.management.mapping.dao.ColumnMappingDao;
import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.dao.TableMappingDao;
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
	private ColumnMappingDao columnMappingDao;
	private DbConfigDao dbConfigDao;
	private ReportFilterDao reportFilterDao;
	private ReportChartDao reportChartDao;
	private ReportChartAxisDao reportChartAxisDao;
	private GroupAggregationDao groupAggregationDao;
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
		//check to see if report has any charts
		//in the future it has to change to list of charts
		List<ReportChart> reportCharts = reportChartDao.findAll(reportDesign);
		Boolean hasChart = reportCharts != null && reportCharts.size() > 0 ;
		Integer chartCount = reportCharts.size();

		//UI
		List<ExtStoreField> storeFields = new ArrayList<ExtStoreField>(columnList.size());
		storeFields.add(new ExtStoreField("id"));
		Boolean hasGroup = false;
		List<String> groupFields = new ArrayList<String>();
		List<ExtGridColumn> gridColumns = new ArrayList<ExtGridColumn>(columnList.size());

		int totalWidth = 0;
		for(ReportColumn col : columnList) {
			List<GroupAggregation> groups = groupAggregationDao.findAll(col);
			String key = col.getColumnKey();
			storeFields.add(new ExtStoreField(key));
			ExtGridColumn extGridColumn = new ExtGridColumn(col.getColumnHeader(), col.getColumnHeader(), key, col.getColumnWidth(), col.getColumnAlign(), "direction:"+col.getColumnDir()+";");
			if(groups.size() > 0){
				extGridColumn.setSummaryType(groups.get(0).getAggregationFunction());
			}
			gridColumns.add(extGridColumn);
			totalWidth += col.getColumnWidth();
			if(col.getGroupLevel() != null && col.getGroupLevel() > 0){
				hasGroup = true;
				groupFields.add(col.getColumnKey());
			}
		}
		gridColumns.add(0,new ExtGridColumn(PropertyProvider.get("eurb.app.builder.runreport.grid.radif"), PropertyProvider.get("eurb.app.builder.runreport.grid.radif"), "id", (int) (0.05 * totalWidth), "center", "direction:ltr;"));

		ModelAndView mv = new ModelAndView();
		mv.addObject("report", report);
		mv.addObject("reportVersion", version);
		mv.addObject("reportName", reportDesign.getName());
		mv.addObject("storeFields", jsonUtil.getJSONFromObject(storeFields));
		mv.addObject("hasGroup", hasGroup);
		mv.addObject("groupFields", jsonUtil.getJSONFromObject(groupFields));
		mv.addObject("gridColumns", jsonUtil.getJSONFromObject(gridColumns));

		mv.addObject("hasChart", hasChart);
		mv.addObject("chartCount", chartCount);

		mv.setViewName("/builder/report/run-report");
		return mv;
	}

	interface OutputCollector {
		public void collect(DBBean db, int counter, int total, ReportDesign reportDesign, List<ReportDataset> datasetList, List<ReportColumn> columnList, List<ReportFilter> reportFilters) throws Exception;
	}
	
	class MapOutputCollector implements OutputCollector {

		boolean includeMetadata = true;
		Object result;
		
		public void collect(DBBean db, int counter, int total, ReportDesign reportDesign, List<ReportDataset> datasetList, List<ReportColumn> columnList, List<ReportFilter> reportFilters) throws Exception {
			Map<String,Object> resultMap = new HashMap<String, Object>();
			List<Map<String, Object>> rowList = new LinkedList<Map<String,Object>>();
			Map<String,Object> row;
			while(db.result.next()) {
				row = new HashMap<String, Object>();
				row.put("id", counter++);
				for(ReportColumn col : columnList) {
					row.put(col.getColumnKey(), db.result.getObject(col.getColumnKey()));
				}
				rowList.add(row);
			}

			resultMap = JsonUtil.getSuccessfulMap(rowList, total);
			if(includeMetadata) {
				resultMap.put("reportDesign", reportDesign);
				resultMap.put("datasetList", datasetList);
				resultMap.put("columnList", columnList);
				resultMap.put("reportFilters", reportFilters);
			}
			
			this.result = resultMap;
		}
		
	}
	
	@RequestMapping(value="/builder/report/report{report}-v{version}-{year}-{month}-{day}-{min}-{sec}.csv", method = RequestMethod.GET)
	public @ResponseBody void executeRunReportCSV(@PathVariable Long report, @PathVariable Long version, @PathVariable Long year, @PathVariable Long month, @PathVariable Long day, @PathVariable Long min, @PathVariable Long sec, 
	        HttpServletRequest request, 
	        HttpServletResponse response) throws Exception {
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition","attachment; filename=\"" + String.format("report%d-v%d-%04d-%02d-%02d-%02d-%02d.csv", report, version, year, month, day, min, sec) + "\"");
		final BufferedWriter writer = new BufferedWriter(response.getWriter());

		
		OutputCollector collector = new OutputCollector() {
				
			@Override
			public void collect(DBBean db, int counter, int total,
					ReportDesign reportDesign, List<ReportDataset> datasetList,
					List<ReportColumn> columnList, List<ReportFilter> reportFilters)
					throws Exception {

				for (int cellnum = 0; cellnum < columnList.size(); cellnum ++) {
					if(cellnum > 0) {
						writer.write(',');
					}
					StringUtilities.escapeCSV(columnList.get(cellnum).getColumnHeader(), writer); 
				}
				writer.append("\r\n");
				Object value;
				while(db.result.next()) {
					for (int cellnum = 0; cellnum < columnList.size(); cellnum ++) {
						if(cellnum > 0) {
							writer.write(',');
						}
						value = db.result.getObject(columnList.get(cellnum).getColumnKey());
						if(value != null) {
							StringUtilities.escapeCSV(value.toString(), writer);
						} else {
							StringUtilities.escapeCSV("", writer);
						}
					}
					writer.append("\r\n");
				}
			}
		};
		executeRunReportDataInner(report, version, null, null, null, null, collector);
		
		writer.flush();
		writer.close();
	}

	@RequestMapping(value="/builder/report/report{report}-v{version}-{year}-{month}-{day}-{min}-{sec}.xls")
	public ModelAndView executeRunReportExcel(@PathVariable Long report, @PathVariable Long version, @PathVariable Long year, @PathVariable Long month, @PathVariable Long day, @PathVariable Long min, @PathVariable Long sec) throws Exception {
		ModelAndView mv = new ModelAndView();
		try {
			MapOutputCollector collector = new MapOutputCollector();
			executeRunReportDataInner(report, version, null, null, null, null, collector);
			mv.addAllObjects((Map<String, ?>) collector.result);
		} catch (Exception e) {
			e.printStackTrace();
			mv.addAllObjects(JsonUtil.getModelMapError(e));
		}
		mv.setView(new com.sharifpro.eurb.builder.view.ViewExcelReport());
		return mv;
	}

	@RequestMapping(value="/builder/report/report{report}-v{version}-{year}-{month}-{day}-{min}-{sec}.docx")
	public ModelAndView executeRunReportWord(@PathVariable Long report, @PathVariable Long version, @PathVariable Long year, @PathVariable Long month, @PathVariable Long day, @PathVariable Long min, @PathVariable Long sec) throws Exception {
		ModelAndView mv = new ModelAndView();
		try {
			MapOutputCollector collector = new MapOutputCollector();
			executeRunReportDataInner(report, version, null, null, null, null, collector);
			mv.addAllObjects((Map<String, ?>) collector.result);
		} catch (Exception e) {
			e.printStackTrace();
			mv.addAllObjects(JsonUtil.getModelMapError(e));
		}
		mv.setView(new com.sharifpro.eurb.builder.view.ViewWordReport());
		return mv;
	}

	@RequestMapping(value="/builder/report/get-reportdata{report}-v{version}.spy")
	public @ResponseBody Map<String,? extends Object> executeRunReportData(@PathVariable Long report, @PathVariable Long version
			,@RequestParam(required=false) String start
			,@RequestParam(required=false) String limit
			,@RequestParam(required=false) String sort
			,@RequestParam(required=false) String dir) throws Exception {
		try {
			MapOutputCollector collector = new MapOutputCollector();
			collector.includeMetadata = false;
			executeRunReportDataInner(report, version, start, limit, sort, dir, collector);
			return (Map<String, ?>) collector.result;
		} catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.getModelMapError(e);
		}
	}

	private void executeRunReportDataInner(Long report, Long version
			,String start
			,String limit
			,String sort
			,String dir
			,OutputCollector collector) throws Exception {
		//All Datasets must be in the same dbConfig
		Long dbConfigId = null;

		//find report design with given id
		ReportDesign reportDesign = reportDesignDao.findByPrimaryKey(report, version);
		List<ReportDataset> datasetList = reportDatasetDao.findAll(reportDesign);
		List<ReportColumn> columnList = reportColumnDao.findAll(reportDesign);
		List<ReportFilter> reportFilters = reportFilterDao.findAll(reportDesign.getId(), reportDesign.getVersionId());

		//Build QUERY
		///Build SELECT Part
		StringBuilder querySelectSB = new StringBuilder("Select ");
		StringBuilder querySelectOnlyAliasSB = new StringBuilder("Select ");
		boolean firstCol = true;
		for(ReportColumn rcol : columnList) {
			if(!firstCol) {
				querySelectSB.append(", ");
				querySelectOnlyAliasSB.append(", ");
			}
			querySelectSB.append(rcol.getDatabaseKey()).append(" AS ").append(rcol.getColumnKey());
			querySelectOnlyAliasSB.append(rcol.getColumnKey());
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
		List<Integer> reportFilterTypes = new ArrayList<Integer>(reportFilters.size());
		if(reportFilters != null && !reportFilters.isEmpty()) {
			///////////GENERATING A COLUMN MAP FOR BUILDING FILTERS\\\\\\\\\\\\\\\
			Map<Long, ColumnMapping> columnMap = new HashMap<Long, ColumnMapping>();
			List<ReportDataset> reportDatasets = reportDatasetDao.findAll(reportDesign);
			for(ReportDataset rds : reportDatasets){
				List<ColumnMapping> columnMappings = columnMappingDao.findAllMapped(rds);
				for(ColumnMapping cm : columnMappings){
					columnMap.put(cm.getId(), cm);
				}
			}
			/////////////////////////////////////////////////////////////////////////
			for(ReportFilter filter : reportFilters) {
				ColumnMapping relatedRCol = columnMap.get(filter.getColumnMappingId());
				if(relatedRCol != null) {
					reportFilterTypes.add(relatedRCol.getColDataType());
					oper = filter.getOperatorObj();
					if(firstFilter) {
						queryWhereSB.append(" WHERE ");
						firstFilter = false;
					}
					else {
						queryWhereSB.append(" AND ");
					}
					queryWhereSB.append(" ").append(relatedRCol.getDatabaseKey(filter.getReportDatasetId())).append(" ").append(filter.getOperator()).append(" ");

					if(oper.numberOfOperands == 0) {
						queryWhereSB.append(oper.operator);
					} else if(oper.numberOfOperands == 1) {
						if(filter.isJoinFilter()) {
							ColumnMapping oper1 = columnMap.get(filter.getOperand1ColumnMappingId());
							queryWhereSB.append(oper1.getDatabaseKey(filter.getOperand1DatasetId()));
						} else {
							queryWhereSB.append("?");
						}
					} else if(oper.numberOfOperands == 2) {
						queryWhereSB.append("? and ?");
					}
				}
			}
		}

		//Build ORDER BY part
		StringBuilder querySortSB = new StringBuilder();
		StringBuilder querySortOnlyAliasSB = new StringBuilder();
		List<ReportColumn> sortCols = new LinkedList<ReportColumn>();
		for(ReportColumn col : columnList) {
			if((col.getSortType() != null && (ReportColumn.SORT_TYPE_ASC.equals(col.getSortType()) || ReportColumn.SORT_TYPE_DESC.equals(col.getSortType()))) ||
					(col.getGroupLevel() != null && col.getGroupLevel() > 0)){
				sortCols.add(col);
			}
		}
		if(!sortCols.isEmpty()) {
			querySortSB.append(" ORDER BY ");
			querySortOnlyAliasSB.append(" ORDER BY ");
			Collections.sort(sortCols, new ReportColumn.ReportColumnSortOrderComparator());
			boolean firstSort = true;
			for(ReportColumn col : sortCols) {
				if(!firstSort){
					querySortSB.append(", ");
					querySortOnlyAliasSB.append(", ");
				}
				querySortSB.append(col.getDatabaseKey()).append(" ");
				querySortOnlyAliasSB.append(col.getColumnKey()).append(" ");
				if(col.getGroupLevel() == null){
					querySortSB.append(col.getSortType() == ReportColumn.SORT_TYPE_DESC ? "DESC" : "ASC");
					querySortOnlyAliasSB.append(col.getSortType() == ReportColumn.SORT_TYPE_DESC ? "DESC" : "ASC");
				}
				if(firstSort) {
					firstSort = false;
				}
			}
		}

		String querySelect = querySelectSB.toString();
		String querySelectOnlyAlias = querySelectOnlyAliasSB.toString();
		String queryFrom = queryFromSB.toString();
		String queryWhere = queryWhereSB.toString();
		String querySort = querySortSB.toString();
		String querySortOnlyAlias = querySortOnlyAliasSB.toString();


		//Execute QUERY
		DbConfig dbConf = dbConfigDao.findByPrimaryKey(dbConfigId);
		ISQLConnection conn = null;
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
				for(int i = 0; i < reportFilters.size(); i++) {
					ReportFilter filter = reportFilters.get(i);
					int type = reportFilterTypes.get(i);
					if(!filter.isJoinFilter()) {
						oper = filter.getOperatorObj();
						Object operand1 , operand2;
						if(type == 2){
							operand1 = (filter.getOperand1() == null || filter.getOperand1().equals("")) ? null : Integer.parseInt(filter.getOperand1());
							operand2 = (filter.getOperand2() == null || filter.getOperand2().equals("")) ? null : Integer.parseInt(filter.getOperand2());
						}
						else{
							operand1 = filter.getOperand1();
							operand2 = filter.getOperand2();
						}
						if(oper.numberOfOperands == 1) {
							db.pstmt.setObject(index++, operand1);
						} else if(oper.numberOfOperands == 2) {
							db.pstmt.setObject(index++, operand1);
							db.pstmt.setObject(index++, operand2);
						}
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
				finalQuery = dialect.buildQuery(conn, datasetList, querySelectOnlyAlias, querySelect,queryFrom,queryWhere, querySort, querySortOnlyAlias, Integer.parseInt(start), Integer.parseInt(limit));
				counter += Integer.parseInt(start);
			}

			if(queryWhere.isEmpty()) {
				db.executeQuery(finalQuery);
			} else {
				int index = 1;
				db.prepareStatement(finalQuery);
				for(int i = 0; i < reportFilters.size(); i++) {
					ReportFilter filter = reportFilters.get(i);
					int type = reportFilterTypes.get(i);
					if(!filter.isJoinFilter()) {
						oper = filter.getOperatorObj();
						Object operand1 , operand2;
						if(type == 2){
							operand1 = (filter.getOperand1() == null || filter.getOperand1().equals("")) ? null : Integer.parseInt(filter.getOperand1());
							operand2 = (filter.getOperand2() == null || filter.getOperand2().equals("")) ? null : Integer.parseInt(filter.getOperand2());
						}
						else{
							operand1 = filter.getOperand1();
							operand2 = filter.getOperand2();
						}
						if(oper.numberOfOperands == 1) {
							db.pstmt.setObject(index++, operand1);
						} else if(oper.numberOfOperands == 2) {
							db.pstmt.setObject(index++, operand1);
							db.pstmt.setObject(index++, operand2);
						}
					}
				}
				db.executePrepared();
			}
			collector.collect(db, counter, total, reportDesign, datasetList, columnList, reportFilters);
		} catch (Exception e) {
			throw e;
		} finally {
			if(conn != null) {
				conn.close();
			}
		}
	}


	@RequestMapping(value="/builder/report/get-reportchart{report}-c{chart}.spy")
	public @ResponseBody Map<String,? extends Object> executeRunReportChartByChartId(@PathVariable Long report, @PathVariable Long chart) throws Exception {

		try{
			long version;
			try {
				version = reportDesignDao.findWhereIdEquals(report).get(0).getVersionId();
			} catch (Exception e) {
				throw new ReportChartDaoException("Chart not found!");
			}
			//find report design with given id
			ReportDesign reportDesign = reportDesignDao.findByPrimaryKey(report, version);

			//All Datasets must be in the same dbConfig
			Long dbConfigId = reportDesign.getDbConfigId();

			List<ReportChart> reportCharts = reportChartDao.findAll(reportDesign);
			for(ReportChart c : reportCharts){
				if(c.getId().equals(chart)) {
					return JsonUtil.getSuccessfulMap(getResultForChart(c, dbConfigId, reportDesign));
				}
			}

			throw new ReportChartDaoException("Chart not found!");

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e);
		}
	}

	@RequestMapping(value="/builder/report/get-reportchart{report}-i{chartIndex}.spy")
	public @ResponseBody Map<String,? extends Object> executeNewRunReportChart(@PathVariable Long report, @PathVariable Integer chartIndex, @RequestParam String formula) throws Exception {
		try{

			//find report design with given id
			ReportDesign reportDesign = reportDesignDao.findWhereIdEquals(report).get(0);

			//All Datasets must be in the same dbConfig
			Long dbConfigId = reportDesign.getDbConfigId();

			List<Object> res = new ArrayList<Object>();

			List<ReportChart> reportCharts = reportChartDao.findAll(reportDesign);
			ReportChart chart;
			List<ArrayList<Object>> resultList;
			for(int i = 0; i < reportCharts.size(); i++){
				chart = reportCharts.get(i);
				resultList = getResultForChart(chart, dbConfigId, reportDesign);
				res.add(resultList);
				if(i == chartIndex)
				{
					resultList = getResultForChart(chart, dbConfigId, reportDesign, formula);
					res.add(resultList);

				}
			}

			return JsonUtil.getSuccessfulMap(res);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e);
		}
	}

	@RequestMapping(value="/builder/report/get-reportchart{report}-v{version}.spy")
	public @ResponseBody Map<String,? extends Object> executeRunReportChart(@PathVariable Long report, @PathVariable Long version) throws Exception {
		try{


			//find report design with given id
			ReportDesign reportDesign = reportDesignDao.findByPrimaryKey(report, version);

			//All Datasets must be in the same dbConfig
			Long dbConfigId = reportDesign.getDbConfigId();

			List<Object> res = new ArrayList<Object>();

			List<ReportChart> reportCharts = reportChartDao.findAll(reportDesign);
			for(ReportChart chart : reportCharts){
				List<ArrayList<Object>> resultList = getResultForChart(chart, dbConfigId, reportDesign);
				res.add(resultList);
			}

			return JsonUtil.getSuccessfulMap(res);

		} catch (Exception e) {

			return JsonUtil.getModelMapError(e);
		}
	}

	private List<ArrayList<Object>> getResultForChart(ReportChart chart, Long dbConfigId, ReportDesign reportDesign) throws Exception{
		return this.getResultForChart(chart, dbConfigId, reportDesign, null);
	}
	private List<ArrayList<Object>> getResultForChart(ReportChart chart, Long dbConfigId, ReportDesign reportDesign, String formula) throws Exception{

		List<ReportDataset> datasetList = reportDatasetDao.findAll(reportDesign, chart);
		//this dataset list may contain repeated values so we're gonna eliminate them
		datasetList = removeRepeated(datasetList);

		List<ReportFilter> reportFilters = reportFilterDao.findAll(reportDesign.getId(), reportDesign.getVersionId(), datasetList);


		HashMap<Long, HashMap<Long, ColumnMapping>> datasetColumnMappingMap = new HashMap<Long, HashMap<Long, ColumnMapping>>();
		HashMap<Long, ColumnMapping> colMap;
		for(ReportDataset ds : datasetList){
			colMap = new HashMap<Long, ColumnMapping>();
			List<ColumnMapping> columns = columnMappingDao.findAllMapped(ds);
			for(ColumnMapping c : columns){
				colMap.put(c.getId(), c);
			}
			datasetColumnMappingMap.put(ds.getId(), colMap);
		}

		List<ArrayList<Object>> resultList = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> resultConfig = new ArrayList<Object>();

		List<ReportChartAxis> reportChartAxis = reportChartAxisDao.findAll(chart);
		if(reportChartAxis == null || reportChartAxis.size() != 2){
			throw new Exception("There is wrong number of axis for the chart");
		}
		ReportChartAxis xAxis = null, yAxis = null;
		for(ReportChartAxis rca : reportChartAxis){
			if(rca.getType().equals("x")){
				xAxis = rca;
			}
			else if(rca.getType().equals("y")){
				yAxis = rca;
			}
		}
		if(xAxis == null || yAxis == null){
			throw new Exception("There is not an X and Y axis");
		}

		resultConfig.add(chart.getType());
		resultConfig.add(chart.getName());
		resultConfig.add(chart.getConfigMap().get("titleFont"));
		resultConfig.add(chart.getConfigMap().get("titleSize"));
		resultConfig.add(chart.getConfigMap().get("titleColor"));
		resultConfig.add(xAxis.getTitle());
		resultConfig.add(chart.getConfigMap().get("chartColor"));
		resultConfig.add(chart.getConfigMap().get("xAxisFont"));
		resultConfig.add(chart.getConfigMap().get("xAxisSize"));
		resultConfig.add(chart.getConfigMap().get("xAxisColor"));
		resultConfig.add(yAxis.getTitle());
		resultConfig.add(chart.getConfigMap().get("yAxisFont"));
		resultConfig.add(chart.getConfigMap().get("yAxisSize"));
		resultConfig.add(chart.getConfigMap().get("yAxisColor"));
		resultConfig.add(chart.getConfigMap().get("minValue"));
		resultConfig.add(chart.getConfigMap().get("maxValue"));
		resultConfig.add(chart.getConfigMap().get("criticalValue"));
		resultConfig.add(chart.getConfigMap().get("hasAlaram"));
		if(yAxis.hasFormula())
		{
			resultConfig.add(true);
			resultConfig.add(yAxis.getFormula());
		}


		//Build QUERY
		///Build SELECT Part
		StringBuilder querySelectSB = new StringBuilder("Select ");	
		String xAxisDatabaseKey = "t" + xAxis.getDatasetId() + "." + datasetColumnMappingMap.get(xAxis.getDatasetId()).get(xAxis.getColumnMappingId()).getColumnName();
		String xAxisColumnKey = "t"+xAxis.getDatasetId()+"_"+datasetColumnMappingMap.get(xAxis.getDatasetId()).get(xAxis.getColumnMappingId()).getColumnName() + xAxis.getColumnMappingId();
		querySelectSB.append(xAxisDatabaseKey).append(" AS ").append(xAxisColumnKey);
		querySelectSB.append(", ");
		String yAxisDatabaseKey = "";
		String yAxisColumnKey = "";
		if(yAxis.hasFormula()) {
			String yAxisFormula;
			if(formula == null || formula.equals("")){
				yAxisFormula = yAxis.getFormula();
			}
			else {
				yAxisFormula = formula;
			}

			yAxisFormula = yAxisFormula.replaceAll("\\[([^_])*__", "");
			yAxisFormula = yAxisFormula.replaceAll("\\]", "");
			yAxisDatabaseKey = yAxisFormula;
			yAxisColumnKey = "axis" + yAxis.getId();
		}
		else {
			yAxisDatabaseKey = "t" + yAxis.getDatasetId() + "." + datasetColumnMappingMap.get(yAxis.getDatasetId()).get(yAxis.getColumnMappingId()).getColumnName();
			yAxisColumnKey = "t"+yAxis.getDatasetId()+"_"+datasetColumnMappingMap.get(yAxis.getDatasetId()).get(yAxis.getColumnMappingId()).getColumnName() + yAxis.getColumnMappingId();
		}


		if(yAxis.hasAggregation()){
			if(yAxis.getAggregation().equals("distinct-count"))
			{
				querySelectSB.append("count(distinct ").append(yAxisDatabaseKey).append(")").append(" AS ").append(yAxisColumnKey);
			}
			else
			{
				querySelectSB.append(yAxis.getAggregation()).append("(").append(yAxisDatabaseKey).append(")").append(" AS ").append(yAxisColumnKey);
			}
		}
		else{
			querySelectSB.append(yAxisDatabaseKey).append(" AS ").append(yAxisColumnKey);
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
		String databaseKey;
		List<Integer> reportFilterTypes = new ArrayList<Integer>(reportFilters.size());
		boolean firstFilter = true;
		for(ReportFilter filter : reportFilters) {
			oper = filter.getOperatorObj();
			if(firstFilter) {
				queryWhereSB.append(" WHERE ");
				firstFilter = false;
			}
			else {
				queryWhereSB.append(" AND ");
			}
			databaseKey = "t" + filter.getReportDatasetId() + "." + datasetColumnMappingMap.get(filter.getReportDatasetId()).get(filter.getColumnMappingId()).getColumnName();
			queryWhereSB.append(" ").append(databaseKey).append(" ").append(filter.getOperator()).append(" ");

			reportFilterTypes.add(datasetColumnMappingMap.get(filter.getReportDatasetId()).get(filter.getColumnMappingId()).getColDataType());

			if(oper.numberOfOperands == 0) {
				queryWhereSB.append(oper.operator);
			} else if(oper.numberOfOperands == 1) {
				if(filter.isJoinFilter()) {
					ColumnMapping oper1 = datasetColumnMappingMap.get(filter.getOperand1DatasetId()).get(filter.getOperand1ColumnMappingId());
					queryWhereSB.append(oper1.getDatabaseKey(filter.getOperand1DatasetId()));
				} else {
					queryWhereSB.append("?");
				}
			} else if(oper.numberOfOperands == 2) {
				queryWhereSB.append("? and ?");
			}
		}


		StringBuilder queryGroupBySB = new StringBuilder();
		if(yAxis.hasAggregation()){
			queryGroupBySB.append(" GROUP BY ").append(xAxisColumnKey);
		}


		String querySelect = querySelectSB.toString();
		String queryFrom = queryFromSB.toString();
		String queryWhere = queryWhereSB.toString();
		String queryGroupBy = queryGroupBySB.toString(); 


		//Execute QUERY
		DbConfig dbConf = dbConfigDao.findByPrimaryKey(dbConfigId);
		ISQLConnection conn = null;
		ArrayList<Object> xResult = new ArrayList<Object>();
		ArrayList<Object> yResult = new ArrayList<Object>();
		try {
			conn = dbConf.getConnection();
			if(conn == null) {
				throw new ReportExecutionHistoryDaoException(PropertyProvider.get("eurb.app.management.table.dbConfigIsInvalid"));
			}
			conn.setReadOnly(true);

			HibernateDialect dialect = dbConf.getDialect();

			DBBean db = new DBBean(dbConf.getDataSource());


			String finalQuery;
			finalQuery = dialect.buildQuery(querySelect,queryFrom,queryWhere, queryGroupBy);

			if(queryWhere.isEmpty()) {
				db.executeQuery(finalQuery);
			} else {
				int index = 1;
				db.prepareStatement(finalQuery);
				for(int i = 0; i < reportFilters.size(); i++) {
					ReportFilter filter = reportFilters.get(i);
					int type = reportFilterTypes.get(i);
					if(!filter.isJoinFilter()) {
						oper = filter.getOperatorObj();
						Object operand1 , operand2;
						if(type == 2){
							operand1 = (filter.getOperand1() == null || filter.getOperand1().equals("")) ? null : Integer.parseInt(filter.getOperand1());
							operand2 = (filter.getOperand2() == null || filter.getOperand2().equals("")) ? null : Integer.parseInt(filter.getOperand2());
						}
						else{
							operand1 = filter.getOperand1();
							operand2 = filter.getOperand2();
						}
						if(oper.numberOfOperands == 1) {
							db.pstmt.setObject(index++, operand1);
						} else if(oper.numberOfOperands == 2) {
							db.pstmt.setObject(index++, operand1);
							db.pstmt.setObject(index++, operand2);
						}
					}
				}
				db.executePrepared();
			}
			while(db.result.next()) {
				xResult.add(db.result.getObject(xAxisColumnKey));
				yResult.add(db.result.getObject(yAxisColumnKey));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(conn != null) {
				conn.close();
			}
		}

		/*if ("gauge".equals(chart.getType()) && chart.getConfigMap().get("hasAlaram") != null && "true".equals(chart.getConfigMap().get("hasAlaram").toString()) && chart.getConfigMap().get("criticalValue") != null)
		{
			if( (Double.parseDouble(yResult.get(0).toString()) > Double.parseDouble(chart.getConfigMap().get("criticalValue").toString()))){
				System.out.println("Hello!");
			}
			else{
				
			}
		}
*/
		resultList.add(resultConfig);
		resultList.add(xResult);
		resultList.add(yResult);

		return resultList;
	}


	private List<ReportDataset> removeRepeated(List<ReportDataset> datasetList) {
		Map<Long, ReportDataset> datasetIdMap = new HashMap<Long, ReportDataset>(datasetList.size());
		for(ReportDataset ds : datasetList)
		{
			datasetIdMap.put(ds.getId(), ds);
		}
		List<ReportDataset> removed = new ArrayList<ReportDataset>(datasetIdMap.size());
		for(ReportDataset ds: datasetIdMap.values())
		{
			removed.add(ds);
		}
		return removed;
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
	public void setReportChartDao(ReportChartDao reportChartDao){
		this.reportChartDao = reportChartDao;
	}

	@Autowired 
	public void setReportChartAxisDao(ReportChartAxisDao reportChartAxisDao){
		this.reportChartAxisDao = reportChartAxisDao;
	}

	@Autowired
	public void setColumnMappingDao(ColumnMappingDao columnMappingDao){
		this.columnMappingDao = columnMappingDao;
	}

	@Autowired
	public void setGroupAggregationDao(GroupAggregationDao groupAggregationDao){
		this.groupAggregationDao = groupAggregationDao;
	}

	@Autowired
	public void setJsonUtil(JsonUtil jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}