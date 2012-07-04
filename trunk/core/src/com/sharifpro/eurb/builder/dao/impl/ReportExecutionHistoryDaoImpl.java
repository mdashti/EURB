package com.sharifpro.eurb.builder.dao.impl;

import com.sharifpro.eurb.builder.dao.ReportExecutionHistoryDao;
import com.sharifpro.eurb.builder.exception.ReportExecutionHistoryDaoException;
import com.sharifpro.eurb.builder.model.ReportExecutionHistory;
import com.sharifpro.eurb.builder.model.ReportExecutionHistoryPk;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ReportExecutionHistoryDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportExecutionHistory>, ReportExecutionHistoryDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportExecutionHistoryPk
	 */
	@Transactional
	public ReportExecutionHistoryPk insert(ReportExecutionHistory dto)
	{
		getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, version_id, execution_result, is_current, record_status, report_design_id, report_design_version_id ) VALUES ( ?, ?, ?, ?, ?, ?, ? )",dto.getId(),dto.getVersionId(),dto.getExecutionResult(),dto.isCurrent(),dto.getRecordStatus(),dto.getReportDesignId(),dto.getReportDesignVersionId());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the report_execution_history table.
	 */
	@Transactional
	public void update(ReportExecutionHistoryPk pk, ReportExecutionHistory dto) throws ReportExecutionHistoryDaoException
	{
		getJdbcTemplate().update("UPDATE " + getTableName() + " SET id = ?, version_id = ?, execution_result = ?, is_current = ?, record_status = ?, report_design_id = ?, report_design_version_id = ? WHERE id = ? AND version_id = ?",dto.getId(),dto.getVersionId(),dto.getExecutionResult(),dto.isCurrent(),dto.getRecordStatus(),dto.getReportDesignId(),dto.getReportDesignVersionId(),pk.getId(),pk.getVersionId());
	}

	/** 
	 * Deletes a single row in the report_execution_history table.
	 */
	@Transactional
	public void delete(ReportExecutionHistoryPk pk) throws ReportExecutionHistoryDaoException
	{
		getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE id = ? AND version_id = ?",pk.getId(),pk.getVersionId());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return ReportExecutionHistory
	 */
	public ReportExecutionHistory mapRow(ResultSet rs, int row) throws SQLException
	{
		ReportExecutionHistory dto = new ReportExecutionHistory();
		dto.setId( new Long( rs.getLong(1) ) );
		dto.setVersionId( new Long( rs.getLong(2) ) );
		dto.setExecutionResult( rs.getString( 3 ) );
		dto.setCurrent( rs.getBoolean( 4 ) );
		dto.setRecordStatus( rs.getString( 5 ) );
		dto.setReportDesignId( new Long( rs.getLong(6) ) );
		dto.setReportDesignVersionId( new Long( rs.getLong(7) ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "report_execution_history";
	}

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'id = :id AND version_id = :versionId'.
	 */
	@Transactional
	public ReportExecutionHistory findByPrimaryKey(Long id, Long versionId) throws ReportExecutionHistoryDaoException
	{
		try {
			List<ReportExecutionHistory> list = getJdbcTemplate().query("SELECT id, version_id, execution_result, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE id = ? AND version_id = ?", this,id,versionId);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportExecutionHistoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria ''.
	 */
	@Transactional
	public List<ReportExecutionHistory> findAll() throws ReportExecutionHistoryDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, execution_result, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " ORDER BY id, version_id", this);
		}
		catch (Exception e) {
			throw new ReportExecutionHistoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'report_design_id = :reportDesignId AND report_design_version_id = :reportDesignVersionId'.
	 */
	@Transactional
	public List<ReportExecutionHistory> findByReportDesign(Long reportDesignId, Long reportDesignVersionId) throws ReportExecutionHistoryDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, execution_result, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE report_design_id = ? AND report_design_version_id = ?", this,reportDesignId,reportDesignVersionId);
		}
		catch (Exception e) {
			throw new ReportExecutionHistoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportExecutionHistory> findByPersistableObject(Long id) throws ReportExecutionHistoryDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, execution_result, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new ReportExecutionHistoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportExecutionHistory> findWhereIdEquals(Long id) throws ReportExecutionHistoryDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, execution_result, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new ReportExecutionHistoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'version_id = :versionId'.
	 */
	@Transactional
	public List<ReportExecutionHistory> findWhereVersionIdEquals(Long versionId) throws ReportExecutionHistoryDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, execution_result, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE version_id = ? ORDER BY version_id", this,versionId);
		}
		catch (Exception e) {
			throw new ReportExecutionHistoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'execution_result = :executionResult'.
	 */
	@Transactional
	public List<ReportExecutionHistory> findWhereExecutionResultEquals(String executionResult) throws ReportExecutionHistoryDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, execution_result, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE execution_result = ? ORDER BY execution_result", this,executionResult);
		}
		catch (Exception e) {
			throw new ReportExecutionHistoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'is_current = :isCurrent'.
	 */
	@Transactional
	public List<ReportExecutionHistory> findWhereIsCurrentEquals(Short isCurrent) throws ReportExecutionHistoryDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, execution_result, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE is_current = ? ORDER BY is_current", this,isCurrent);
		}
		catch (Exception e) {
			throw new ReportExecutionHistoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'record_status = :recordStatus'.
	 */
	@Transactional
	public List<ReportExecutionHistory> findWhereRecordStatusEquals(String recordStatus) throws ReportExecutionHistoryDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, execution_result, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE record_status = ? ORDER BY record_status", this,recordStatus);
		}
		catch (Exception e) {
			throw new ReportExecutionHistoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'report_design_id = :reportDesignId'.
	 */
	@Transactional
	public List<ReportExecutionHistory> findWhereReportDesignIdEquals(Long reportDesignId) throws ReportExecutionHistoryDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, execution_result, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE report_design_id = ? ORDER BY report_design_id", this,reportDesignId);
		}
		catch (Exception e) {
			throw new ReportExecutionHistoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_execution_history table that match the criteria 'report_design_version_id = :reportDesignVersionId'.
	 */
	@Transactional
	public List<ReportExecutionHistory> findWhereReportDesignVersionIdEquals(Long reportDesignVersionId) throws ReportExecutionHistoryDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, execution_result, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE report_design_version_id = ? ORDER BY report_design_version_id", this,reportDesignVersionId);
		}
		catch (Exception e) {
			throw new ReportExecutionHistoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the report_execution_history table that matches the specified primary-key value.
	 */
	public ReportExecutionHistory findByPrimaryKey(ReportExecutionHistoryPk pk) throws ReportExecutionHistoryDaoException
	{
		return findByPrimaryKey( pk.getId(), pk.getVersionId() );
	}

}
