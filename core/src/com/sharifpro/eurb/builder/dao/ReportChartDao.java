package com.sharifpro.eurb.builder.dao;

import com.sharifpro.eurb.builder.dao.ReportChartDao;
import com.sharifpro.eurb.builder.exception.ReportChartDaoException;
import com.sharifpro.eurb.builder.model.ReportChart;
import com.sharifpro.eurb.builder.model.ReportChartPk;
import com.sharifpro.eurb.builder.model.ReportDesign;

import java.util.List;

public interface ReportChartDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportChartPk
	 */
	public ReportChartPk insert(ReportChart dto) throws ReportChartDaoException;

	/** 
	 * Updates a single row in the report_chart table.
	 */
	public void update(ReportChartPk pk, ReportChart dto) throws ReportChartDaoException;

	/** 
	 * Deletes a single row in the report_chart table.
	 */
	public void delete(ReportChartPk pk) throws ReportChartDaoException;
	
	/**
	 * Deletes all given rows from the report_chart table.
	 */
	public void deleteAll(List<ReportChartPk> pkList) throws ReportChartDaoException;

	/** 
	 * Returns all rows from the report_chart table that match the criteria 'id = :id'.
	 */
	public ReportChart findByPrimaryKey(Long id) throws ReportChartDaoException;

	/** 
	 * Returns all rows from the report_chart table that match the criteria ''.
	 */
	public List<ReportChart> findAll() throws ReportChartDaoException;
	
	/** 
	 * Returns all rows from the report_chart table for given report design.
	 */
	public List<ReportChart> findAll(ReportDesign reportDesign) throws ReportChartDaoException;
	
	
	/** 
	 * Counts all rows from the report_chart table that match the criteria ''.
	 */
	public int countAll() throws ReportChartDaoException;
	
	/**
	 *	Counts all rows from the report_chart table for given report design.
	 */
	public int countAll(ReportDesign reportDesign) throws ReportChartDaoException;
	

	/** 
	 * Returns all rows from the report_chart table that match the criteria 'id = :id'.
	 */
	public List<ReportChart> findByPersistableObject(Long id) throws ReportChartDaoException;

	
	/** 
	 * Returns all rows from the report_chart table that match the criteria 'design_id = :designId AND design_version_id = :designVersionId'.
	 */
	public List<ReportChart> findByReportDesign(Long designId, Long designVersionId) throws ReportChartDaoException;
	
	
	/** 
	 * Returns all rows from the report_chart table that match the criteria 'id = :id'.
	 */
	public List<ReportChart> findWhereIdEquals(Long id) throws ReportChartDaoException;

	/** 
	 * Returns all rows from the report_chart table that match the criteria 'design_id = :designId'.
	 */
	public List<ReportChart> findWhereDesignIdEquals(Long designId) throws ReportChartDaoException;

	/** 
	 * Returns all rows from the report_chart table that match the criteria 'design_version_id = :designVersionId'.
	 */
	public List<ReportChart> findWhereDesignVersionIdEquals(Long designVersionId) throws ReportChartDaoException;

	/** 
	 * Returns all rows from the report_chart table that match the criteria 'name = :name'.
	 */
	public List<ReportChart> findWhereNameEquals(String name) throws ReportChartDaoException;

	/** 
	 * Returns all rows from the report_chart table that match the criteria 'chart_type = :type'.
	 */
	public List<ReportChart> findWhereTypeEquals(String type) throws ReportChartDaoException;

	/** 
	 * Returns the rows from the report_chart table that matches the specified primary-key value.
	 */
	public ReportChart findByPrimaryKey(ReportChartPk pk) throws ReportChartDaoException;

}
