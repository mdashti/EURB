package com.sharifpro.eurb.builder.dao.impl;

import com.sharifpro.eurb.builder.dao.ReportFormatDao;
import com.sharifpro.eurb.builder.exception.ReportFormatDaoException;
import com.sharifpro.eurb.builder.model.ReportFormat;
import com.sharifpro.eurb.builder.model.ReportFormatPk;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.transaction.annotation.TransactionalReadOnly;
import com.sharifpro.transaction.annotation.TransactionalReadWrite;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class ReportFormatDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportFormat>, ReportFormatDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportFormatPk
	 */
	@TransactionalReadWrite
	public ReportFormatPk insert(ReportFormat dto)
	{
		getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, format_file, is_current, record_status, report_design_id, report_design_version_id ) VALUES ( ?, ?, ?, ?, ?, ? )",dto.getId(),dto.getFormatFile(),dto.isIsCurrent(),dto.getRecordStatus(),dto.getReportDesignId(),dto.getReportDesignVersionId());
		ReportFormatPk pk = new ReportFormatPk();
		pk.setVersionId( getJdbcTemplate().queryForLong("select last_insert_id()") );
		return pk;
	}

	/** 
	 * Updates a single row in the report_format table.
	 */
	@TransactionalReadWrite
	public void update(ReportFormatPk pk, ReportFormat dto) throws ReportFormatDaoException
	{
		getJdbcTemplate().update("UPDATE " + getTableName() + " SET id = ?, version_id = ?, format_file = ?, is_current = ?, record_status = ?, report_design_id = ?, report_design_version_id = ? WHERE version_id = ? AND id = ?",dto.getId(),dto.getVersionId(),dto.getFormatFile(),dto.isIsCurrent(),dto.getRecordStatus(),dto.getReportDesignId(),dto.getReportDesignVersionId(),pk.getVersionId(),pk.getId());
	}

	/** 
	 * Deletes a single row in the report_format table.
	 */
	@TransactionalReadWrite
	public void delete(ReportFormatPk pk) throws ReportFormatDaoException
	{
		getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE version_id = ? AND id = ?",pk.getVersionId(),pk.getId());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return ReportFormat
	 */
	public ReportFormat mapRow(ResultSet rs, int row) throws SQLException
	{
		ReportFormat dto = new ReportFormat();
		dto.setId( new Long( rs.getLong(1) ) );
		dto.setVersionId( new Long( rs.getLong(2) ) );
		dto.setFormatFile( rs.getString( 3 ) );
		dto.setIsCurrent( rs.getBoolean( 4 ) );
		dto.setRecordStatus( rs.getString( 5 ) );
		dto.setReportDesignId( new Long( rs.getLong(6) ) );
		if (rs.wasNull()) {
			dto.setReportDesignId( null );
		}
		
		dto.setReportDesignVersionId( new Long( rs.getLong(7) ) );
		if (rs.wasNull()) {
			dto.setReportDesignVersionId( null );
		}
		
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "report_format";
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'version_id = :versionId AND id = :id'.
	 */
	@TransactionalReadOnly
	public ReportFormat findByPrimaryKey(Long versionId, Long id) throws ReportFormatDaoException
	{
		try {
			List<ReportFormat> list = getJdbcTemplate().query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE version_id = ? AND id = ?", this,versionId,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria ''.
	 */
	@TransactionalReadOnly
	public List<ReportFormat> findAll() throws ReportFormatDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " ORDER BY version_id, id", this);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'report_design_id = :reportDesignId AND report_design_version_id = :reportDesignVersionId'.
	 */
	@TransactionalReadOnly
	public List<ReportFormat> findByReportDesign(Long reportDesignId, Long reportDesignVersionId) throws ReportFormatDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE report_design_id = ? AND report_design_version_id = ?", this,reportDesignId,reportDesignVersionId);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public List<ReportFormat> findByPersistableObject(Long id) throws ReportFormatDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public List<ReportFormat> findWhereIdEquals(Long id) throws ReportFormatDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'version_id = :versionId'.
	 */
	@TransactionalReadOnly
	public List<ReportFormat> findWhereVersionIdEquals(Long versionId) throws ReportFormatDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE version_id = ? ORDER BY version_id", this,versionId);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'format_file = :formatFile'.
	 */
	@TransactionalReadOnly
	public List<ReportFormat> findWhereFormatFileEquals(String formatFile) throws ReportFormatDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE format_file = ? ORDER BY format_file", this,formatFile);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'is_current = :isCurrent'.
	 */
	@TransactionalReadOnly
	public List<ReportFormat> findWhereIsCurrentEquals(Short isCurrent) throws ReportFormatDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE is_current = ? ORDER BY is_current", this,isCurrent);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'record_status = :recordStatus'.
	 */
	@TransactionalReadOnly
	public List<ReportFormat> findWhereRecordStatusEquals(String recordStatus) throws ReportFormatDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE record_status = ? ORDER BY record_status", this,recordStatus);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'report_design_id = :reportDesignId'.
	 */
	@TransactionalReadOnly
	public List<ReportFormat> findWhereReportDesignIdEquals(Long reportDesignId) throws ReportFormatDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE report_design_id = ? ORDER BY report_design_id", this,reportDesignId);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'report_design_version_id = :reportDesignVersionId'.
	 */
	@TransactionalReadOnly
	public List<ReportFormat> findWhereReportDesignVersionIdEquals(Long reportDesignVersionId) throws ReportFormatDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE report_design_version_id = ? ORDER BY report_design_version_id", this,reportDesignVersionId);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the report_format table that matches the specified primary-key value.
	 */
	public ReportFormat findByPrimaryKey(ReportFormatPk pk) throws ReportFormatDaoException
	{
		return findByPrimaryKey( pk.getVersionId(), pk.getId() );
	}

}
