package com.sharifpro.eurb.builder.dao;

import com.sharifpro.eurb.builder.dao.ReportExecutionHistoryDao;
import com.sharifpro.eurb.builder.exception.ReportExecutionHistoryDaoException;
import com.sharifpro.eurb.builder.model.ReportExecutionHistory;
import com.sharifpro.eurb.builder.model.ReportExecutionHistoryPk;
import java.util.List;

public interface ReportExecutionHistoryDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportExecutionHistoryPk
	 */
	public ReportExecutionHistoryPk insert(ReportExecutionHistory dto);

	/** 
	 * Updates a single row in the report_execution_history table.
	 */
	public void update(ReportExecutionHistoryPk pk, ReportExecutionHistory dto) throws ReportExecutionHistoryDaoException;

	/** 
	 * Deletes a single row in the report_execution_history table.
	 */
	public void delete(ReportExecutionHistoryPk pk) throws ReportExecutionHistoryDaoException;

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'id = :id AND version_id = :versionId'.
	 */
	public ReportExecutionHistory findByPrimaryKey(Long id, Long versionId) throws ReportExecutionHistoryDaoException;

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria ''.
	 */
	public List<ReportExecutionHistory> findAll() throws ReportExecutionHistoryDaoException;

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'report_design_id = :reportDesignId AND report_design_version_id = :reportDesignVersionId'.
	 */
	public List<ReportExecutionHistory> findByReportDesign(Long reportDesignId, Long reportDesignVersionId) throws ReportExecutionHistoryDaoException;

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'id = :id'.
	 */
	public List<ReportExecutionHistory> findByPersistableObject(Long id) throws ReportExecutionHistoryDaoException;

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'id = :id'.
	 */
	public List<ReportExecutionHistory> findWhereIdEquals(Long id) throws ReportExecutionHistoryDaoException;

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'version_id = :versionId'.
	 */
	public List<ReportExecutionHistory> findWhereVersionIdEquals(Long versionId) throws ReportExecutionHistoryDaoException;

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'execution_result = :executionResult'.
	 */
	public List<ReportExecutionHistory> findWhereExecutionResultEquals(String executionResult) throws ReportExecutionHistoryDaoException;

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'is_current = :isCurrent'.
	 */
	public List<ReportExecutionHistory> findWhereIsCurrentEquals(Short isCurrent) throws ReportExecutionHistoryDaoException;

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'record_status = :recordStatus'.
	 */
	public List<ReportExecutionHistory> findWhereRecordStatusEquals(String recordStatus) throws ReportExecutionHistoryDaoException;

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'report_design_id = :reportDesignId'.
	 */
	public List<ReportExecutionHistory> findWhereReportDesignIdEquals(Long reportDesignId) throws ReportExecutionHistoryDaoException;

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'report_design_version_id = :reportDesignVersionId'.
	 */
	public List<ReportExecutionHistory> findWhereReportDesignVersionIdEquals(Long reportDesignVersionId) throws ReportExecutionHistoryDaoException;

	/** 
	 * Returns the rows from the report_execution_history table that matches the specified primary-key value.
	 */
	public ReportExecutionHistory findByPrimaryKey(ReportExecutionHistoryPk pk) throws ReportExecutionHistoryDaoException;

}
