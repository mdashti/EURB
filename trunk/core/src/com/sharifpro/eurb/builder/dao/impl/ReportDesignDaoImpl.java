package com.sharifpro.eurb.builder.dao.impl;

import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.exception.ReportDesignDaoException;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.model.ReportDesignPk;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;

import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class ReportDesignDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportDesign>, ReportDesignDao
{
	protected JdbcTemplate jdbcTemplate;

	protected DataSource dataSource;

	/**
	 * Method 'setDataSource'
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportDesignPk
	 */
	@Transactional
	public ReportDesignPk insert(ReportDesign dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getId(),dto.getVersionId(),dto.getName(),dto.getDescription(),dto.getCategoryId(),dto.getQueryText(),dto.getSelectPart(),dto.getResultData(),dto.getFormatFile(),dto.isIsCurrent(),dto.getRecordStatus());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the report_design table.
	 */
	@Transactional
	public void update(ReportDesignPk pk, ReportDesign dto) throws ReportDesignDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET id = ?, version_id = ?, name = ?, description = ?, category_id = ?, query_text = ?, select_part = ?, result_data = ?, format_file = ?, is_current = ?, record_status = ? WHERE id = ? AND version_id = ?",dto.getId(),dto.getVersionId(),dto.getName(),dto.getDescription(),dto.getCategoryId(),dto.getQueryText(),dto.getSelectPart(),dto.getResultData(),dto.getFormatFile(),dto.isIsCurrent(),dto.getRecordStatus(),pk.getId(),pk.getVersionId());
	}

	/** 
	 * Deletes a single row in the report_design table.
	 */
	@Transactional
	public void delete(ReportDesignPk pk) throws ReportDesignDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ? AND version_id = ?",pk.getId(),pk.getVersionId());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return ReportDesign
	 */
	public ReportDesign mapRow(ResultSet rs, int row) throws SQLException
	{
		ReportDesign dto = new ReportDesign();
		dto.setId( new Long( rs.getLong(1) ) );
		dto.setVersionId( new Long( rs.getLong(2) ) );
		dto.setName( rs.getString( 3 ) );
		dto.setDescription( rs.getString( 4 ) );
		dto.setCategoryId( new Long( rs.getLong(5) ) );
		if (rs.wasNull()) {
			dto.setCategoryId( null );
		}
		
		dto.setQueryText( rs.getString( 6 ) );
		dto.setSelectPart( rs.getString( 7 ) );
		dto.setResultData( rs.getString( 8 ) );
		dto.setFormatFile( rs.getString( 9 ) );
		dto.setIsCurrent( rs.getBoolean( 10 ) );
		dto.setRecordStatus( rs.getString( 11 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "report_design";
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'id = :id AND version_id = :versionId'.
	 */
	@Transactional
	public ReportDesign findByPrimaryKey(Long id, Long versionId) throws ReportDesignDaoException
	{
		try {
			List<ReportDesign> list = jdbcTemplate.query("SELECT id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status FROM " + getTableName() + " WHERE id = ? AND version_id = ?", this,id,versionId);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria ''.
	 */
	@Transactional
	public List<ReportDesign> findAll() throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status FROM " + getTableName() + " ORDER BY id, version_id", this);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportDesign> findByPersistableObject(Long id) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'category_id = :categoryId'.
	 */
	@Transactional
	public List<ReportDesign> findByReportCategory(Long categoryId) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status FROM " + getTableName() + " WHERE category_id = ?", this,categoryId);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportDesign> findWhereIdEquals(Long id) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'version_id = :versionId'.
	 */
	@Transactional
	public List<ReportDesign> findWhereVersionIdEquals(Long versionId) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status FROM " + getTableName() + " WHERE version_id = ? ORDER BY version_id", this,versionId);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'name = :name'.
	 */
	@Transactional
	public List<ReportDesign> findWhereNameEquals(String name) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status FROM " + getTableName() + " WHERE name = ? ORDER BY name", this,name);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'description = :description'.
	 */
	@Transactional
	public List<ReportDesign> findWhereDescriptionEquals(String description) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status FROM " + getTableName() + " WHERE description = ? ORDER BY description", this,description);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'category_id = :categoryId'.
	 */
	@Transactional
	public List<ReportDesign> findWhereCategoryIdEquals(Long categoryId) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status FROM " + getTableName() + " WHERE category_id = ? ORDER BY category_id", this,categoryId);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'query_text = :queryText'.
	 */
	@Transactional
	public List<ReportDesign> findWhereQueryTextEquals(String queryText) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status FROM " + getTableName() + " WHERE query_text = ? ORDER BY query_text", this,queryText);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'select_part = :selectPart'.
	 */
	@Transactional
	public List<ReportDesign> findWhereSelectPartEquals(String selectPart) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status FROM " + getTableName() + " WHERE select_part = ? ORDER BY select_part", this,selectPart);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'result_data = :resultData'.
	 */
	@Transactional
	public List<ReportDesign> findWhereResultDataEquals(String resultData) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status FROM " + getTableName() + " WHERE result_data = ? ORDER BY result_data", this,resultData);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'format_file = :formatFile'.
	 */
	@Transactional
	public List<ReportDesign> findWhereFormatFileEquals(String formatFile) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status FROM " + getTableName() + " WHERE format_file = ? ORDER BY format_file", this,formatFile);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'is_current = :isCurrent'.
	 */
	@Transactional
	public List<ReportDesign> findWhereIsCurrentEquals(Short isCurrent) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status FROM " + getTableName() + " WHERE is_current = ? ORDER BY is_current", this,isCurrent);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'record_status = :recordStatus'.
	 */
	@Transactional
	public List<ReportDesign> findWhereRecordStatusEquals(String recordStatus) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status FROM " + getTableName() + " WHERE record_status = ? ORDER BY record_status", this,recordStatus);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the report_design table that matches the specified primary-key value.
	 */
	public ReportDesign findByPrimaryKey(ReportDesignPk pk) throws ReportDesignDaoException
	{
		return findByPrimaryKey( pk.getId(), pk.getVersionId() );
	}

}
