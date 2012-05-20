package com.sharifpro.eurb.builder.dao;

import com.sharifpro.eurb.builder.exception.ReportChartAxisDaoException;
import com.sharifpro.eurb.builder.model.ReportChart;
import com.sharifpro.eurb.builder.model.ReportChartAxis;
import com.sharifpro.eurb.builder.model.ReportChartAxisPk;

import java.util.List;

public interface ReportChartAxisDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportChartAxisPk
	 */
	public ReportChartAxisPk insert(ReportChartAxis dto) throws ReportChartAxisDaoException;

	/** 
	 * Updates a single row in the report_chart_axis table.
	 */
	public void update(ReportChartAxisPk pk, ReportChartAxis dto) throws ReportChartAxisDaoException;

	/** 
	 * Deletes a single row in the report_chart_axis table.
	 */
	public void delete(ReportChartAxisPk pk) throws ReportChartAxisDaoException;
	
	/**
	 * Deletes all given rows from the report_chart_axis table.
	 */
	public void deleteAll(List<ReportChartAxisPk> pkList) throws ReportChartAxisDaoException;

	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria 'id = :id '.
	 */
	public ReportChartAxis findByPrimaryKey(Long id) throws ReportChartAxisDaoException;

	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria ''.
	 */
	public List<ReportChartAxis> findAll() throws ReportChartAxisDaoException;
	
	/** 
	 * Returns all rows from the report_chart_axis table for given report chart.
	 */
	public List<ReportChartAxis> findAll(ReportChart reportChart) throws ReportChartAxisDaoException;
	
	
	/** 
	 * Counts all rows from the report_chart_axis table that match the criteria ''.
	 */
	public int countAll() throws ReportChartAxisDaoException;
	
	/**
	 *	Counts all rows from the report_chart_axis table for given report chart.
	 */
	public int countAll(ReportChart reportChart) throws ReportChartAxisDaoException;
	

	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria 'id = :id'.
	 */
	public List<ReportChartAxis> findByPersistableObject(Long id) throws ReportChartAxisDaoException;

	
	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria 'chart_id = :chartId '.
	 */
	public List<ReportChartAxis> findByReportChart(Long chartId) throws ReportChartAxisDaoException;
	
	
	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria 'id = :id'.
	 */
	public List<ReportChartAxis> findWhereIdEquals(Long id) throws ReportChartAxisDaoException;

	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria 'chart_id = :chartId'.
	 */
	public List<ReportChartAxis> findWhereChartIdEquals(Long chartId) throws ReportChartAxisDaoException;

	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria 'title = :title'.
	 */
	public List<ReportChartAxis> findWhereTitleEquals(String title) throws ReportChartAxisDaoException;

	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria 'axis_type = :type'.
	 */
	public List<ReportChartAxis> findWhereTypeEquals(String type) throws ReportChartAxisDaoException;

	/** 
	 * Returns the rows from the report_chart_axis table that matches the specified primary-key value.
	 */
	public ReportChartAxis findByPrimaryKey(ReportChartAxisPk pk) throws ReportChartAxisDaoException;

}
