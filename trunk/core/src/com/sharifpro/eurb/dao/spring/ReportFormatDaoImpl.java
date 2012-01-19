package com.sharifpro.eurb.dao.spring;

import com.sharifpro.eurb.dao.ReportFormatDao;
import com.sharifpro.eurb.dto.ReportFormat;
import com.sharifpro.eurb.dto.ReportFormatPk;
import com.sharifpro.eurb.exceptions.ReportFormatDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class ReportFormatDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportFormat>, ReportFormatDao
{
	protected SimpleJdbcTemplate jdbcTemplate;

	protected DataSource dataSource;

	/**
	 * Method 'setDataSource'
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportFormatPk
	 */
	@Transactional
	public ReportFormatPk insert(ReportFormat dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, format_file, is_current, record_status, report_design_id, report_design_version_id ) VALUES ( ?, ?, ?, ?, ?, ? )",dto.getId(),dto.getFormatFile(),dto.isIsCurrent(),dto.getRecordStatus(),dto.getReportDesignId(),dto.getReportDesignVersionId());
		ReportFormatPk pk = new ReportFormatPk();
		pk.setVersionId( jdbcTemplate.queryForLong("select last_insert_id()") );
		return pk;
	}

	/** 
	 * Updates a single row in the report_format table.
	 */
	@Transactional
	public void update(ReportFormatPk pk, ReportFormat dto) throws ReportFormatDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET id = ?, version_id = ?, format_file = ?, is_current = ?, record_status = ?, report_design_id = ?, report_design_version_id = ? WHERE version_id = ? AND id = ?",dto.getId(),dto.getVersionId(),dto.getFormatFile(),dto.isIsCurrent(),dto.getRecordStatus(),dto.getReportDesignId(),dto.getReportDesignVersionId(),pk.getVersionId(),pk.getId());
	}

	/** 
	 * Deletes a single row in the report_format table.
	 */
	@Transactional
	public void delete(ReportFormatPk pk) throws ReportFormatDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE version_id = ? AND id = ?",pk.getVersionId(),pk.getId());
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
	public String getTableName()
	{
		return "report_format";
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'version_id = :versionId AND id = :id'.
	 */
	@Transactional
	public ReportFormat findByPrimaryKey(Long versionId, Long id) throws ReportFormatDaoException
	{
		try {
			List<ReportFormat> list = jdbcTemplate.query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE version_id = ? AND id = ?", this,versionId,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria ''.
	 */
	@Transactional
	public List<ReportFormat> findAll() throws ReportFormatDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " ORDER BY version_id, id", this);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'report_design_id = :reportDesignId AND report_design_version_id = :reportDesignVersionId'.
	 */
	@Transactional
	public List<ReportFormat> findByReportDesign(Long reportDesignId, Long reportDesignVersionId) throws ReportFormatDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE report_design_id = ? AND report_design_version_id = ?", this,reportDesignId,reportDesignVersionId);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportFormat> findByPersistableObject(Long id) throws ReportFormatDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportFormat> findWhereIdEquals(Long id) throws ReportFormatDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'version_id = :versionId'.
	 */
	@Transactional
	public List<ReportFormat> findWhereVersionIdEquals(Long versionId) throws ReportFormatDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE version_id = ? ORDER BY version_id", this,versionId);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'format_file = :formatFile'.
	 */
	@Transactional
	public List<ReportFormat> findWhereFormatFileEquals(String formatFile) throws ReportFormatDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE format_file = ? ORDER BY format_file", this,formatFile);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'is_current = :isCurrent'.
	 */
	@Transactional
	public List<ReportFormat> findWhereIsCurrentEquals(Short isCurrent) throws ReportFormatDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE is_current = ? ORDER BY is_current", this,isCurrent);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'record_status = :recordStatus'.
	 */
	@Transactional
	public List<ReportFormat> findWhereRecordStatusEquals(String recordStatus) throws ReportFormatDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE record_status = ? ORDER BY record_status", this,recordStatus);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'report_design_id = :reportDesignId'.
	 */
	@Transactional
	public List<ReportFormat> findWhereReportDesignIdEquals(Long reportDesignId) throws ReportFormatDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE report_design_id = ? ORDER BY report_design_id", this,reportDesignId);
		}
		catch (Exception e) {
			throw new ReportFormatDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_format table that match the criteria 'report_design_version_id = :reportDesignVersionId'.
	 */
	@Transactional
	public List<ReportFormat> findWhereReportDesignVersionIdEquals(Long reportDesignVersionId) throws ReportFormatDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, format_file, is_current, record_status, report_design_id, report_design_version_id FROM " + getTableName() + " WHERE report_design_version_id = ? ORDER BY report_design_version_id", this,reportDesignVersionId);
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
