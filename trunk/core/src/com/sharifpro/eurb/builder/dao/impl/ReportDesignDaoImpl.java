package com.sharifpro.eurb.builder.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.exception.ReportDesignDaoException;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.model.ReportDesignPk;
import com.sharifpro.eurb.info.RecordStatus;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.dao.impl.PersistableObjectDaoImpl;
import com.sharifpro.util.PropertyProvider;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class ReportDesignDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportDesign>, ReportDesignDao
{
	private final static String QUERY_FROM_COLUMNS = "o.version_id, o.name, o.description, o.query_text, o.select_part, o.result_data, o.format_file, o.is_current, o.record_status ";

	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;
	
	private final static String QUERY_ACTIVE_WHERE_PART = " o.is_current = 1 and o.record_status = '" + RecordStatus.ACTIVE + "'";
	
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportDesignPk
	 */
	@Transactional(readOnly = false)
	public ReportDesignPk insert(ReportDesign dto)
	{
		ReportDesignPk pk = new ReportDesignPk(); 
		DaoFactory.createPersistableObjectDao().insert(dto, pk);
		dto.setVersionId(pk.getId());
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status ) " +
				"VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",pk.getId(),dto.getVersionId(),dto.getName(),dto.getDescription(),dto.getCategoryId(),dto.getQueryText(),dto.getSelectPart(),dto.getResultData(),dto.getFormatFile(),dto.isIsCurrent(),dto.getRecordStatusString());
		pk.setVersionId(dto.getVersionId());
		return pk;
	}

	/** 
	 * Updates a single row in the report_design table.
	 */
	@Transactional(readOnly = false)
	public void update(ReportDesignPk pk, ReportDesign dto) throws ReportDesignDaoException
	{
		Long lastVersion = pk.getVersionId();
		jdbcTemplate.update("UPDATE " + getTableName() + " SET is_current = 0 WHERE id = ? AND version_id = ?", pk.getId(), pk.getVersionId());
		DaoFactory.createPersistableObjectDao().update(pk);
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status ) " +
				"VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",pk.getId(),++lastVersion,dto.getName(),dto.getDescription(),dto.getCategoryId(),dto.getQueryText(),dto.getSelectPart(),dto.getResultData(),dto.getFormatFile(),dto.isIsCurrent(),dto.getRecordStatusString());
		pk.setVersionId(lastVersion);

	}
	
	/**
	 * Sets result data for a given report design in database
	 * Is it really necessary???
	 * */
	@Transactional(readOnly = false)
	public void setResultData(ReportDesignPk pk, ReportDesign dto)
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		jdbcTemplate.update("UPDATE " + getTableName() + " SET result_data = ? WHERE id = ? and version_id = ?", dto.getResultData(), pk.getId(), pk.getVersionId());
	}
	
	/**
	 * Activates a single row in database
	 * @param pk
	 * @param dto
	 */
	@Transactional(readOnly = false)
	public void activate(ReportDesignPk pk)
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		jdbcTemplate.update("UPDATE " + getTableName() + " SET record_status = ? WHERE id = ? ", RecordStatus.ACTIVE.getId(), pk.getId());
	}
	
	/**
	 * Dectivates a single row in database
	 * @param pk
	 * @param dto
	 */
	@Transactional(readOnly = false)
	public void deactivate(ReportDesignPk pk)
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		jdbcTemplate.update("UPDATE " + getTableName() + " SET record_status = ? WHERE id = ? ", RecordStatus.PASSIVE.getId(), pk.getId());
	}
	

	/** 
	 * Deletes a single row in the report_design table.
	 */
	@Transactional(readOnly = false)
	public void delete(ReportDesignPk pk) throws ReportDesignDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		jdbcTemplate.update("UPDATE " + getTableName() + " SET record_status = ? WHERE id = ?", RecordStatus.DELETED.getId() ,pk.getId());
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
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setId( new Long( rs.getLong(++i) ) );
		dto.setVersionId( new Long( rs.getLong(++i) ) );
		dto.setName( rs.getString( ++i ) );
		dto.setDescription( rs.getString( ++i ) );
		dto.setCategoryId( new Long( rs.getLong(++i) ) );
		if (rs.wasNull()) {
			dto.setCategoryId( null );
		}
		
		dto.setQueryText( rs.getString( ++i ) );
		dto.setSelectPart( rs.getString( ++i ) );
		dto.setResultData( rs.getString( ++i ) );
		dto.setFormatFile( rs.getString( ++i ) );
		dto.setIsCurrent( rs.getBoolean( ++i ) );
		dto.setRecordStatusString( rs.getString( ++i ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "report_design";
	}

	/** 
	 * Returns current row from the report_design table that match the criteria 'id = :id '.
	 */
	@Transactional(readOnly = true)
	public ReportDesign findByPrimaryKey(Long id) throws ReportDesignDaoException
	{
		try {
			List<ReportDesign> list = jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.id = ? AND o.is_current = 1", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}
	
	@Transactional(readOnly = true)
	public ReportDesign findByPrimaryKey(Long id, Long versionId) throws ReportDesignDaoException
	{
		try {
			List<ReportDesign> list = jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.id = ? AND o.version_id = ?", this,id, versionId);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}
	

	/** 
	 * Returns all rows from the report_design table that match the criteria ''.
	 */
	@Transactional(readOnly = true)
	public List<ReportDesign> findAll() throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.record_status IN ('" + RecordStatus.ACTIVE + "', '" + RecordStatus.PASSIVE + "') ORDER BY o.id, o.version_id", this);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all active rows from the report_design table that match the criteria ''.
	 */
	@Transactional(readOnly = true)
	public List<ReportDesign> findAllActive() throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE " + QUERY_ACTIVE_WHERE_PART + "' ORDER BY o.id, o.version_id", this);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportDesign> findByPersistableObject(Long id) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.id = ? and o.is_current = 1", this,id);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}
	
	/** 
	 * Returns all rows from the report_design table that match the criteria 'id = :id and version_id = :versionId'.
	 */
	@Transactional
	public List<ReportDesign> findByPersistableObject(Long id, Long versionId) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.id = ? and o.version_id = ?", this,id,versionId);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}


	/** 
	 * Returns all rows from the report_design table that match the criteria 'category_id = :categoryId'.
	 */
	@Transactional
	public List<ReportDesign> findByReportCategory(Long categoryId) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.category_id = ? and " + QUERY_ACTIVE_WHERE_PART, this,categoryId);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportDesign> findWhereIdEquals(Long id) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.id = ? and o.is_current = 1 ORDER BY o.id", this,id);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'id = :id, version_id = :versionId'.
	 */
	@Transactional
	public List<ReportDesign> findWhereIdAndVersionIdEquals(Long id, Long versionId) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.id = ? and o.version_id = ? ORDER BY o.id, o.version_id", this,id,versionId);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'name = :name'.
	 */
	@Transactional
	public List<ReportDesign> findWhereNameEquals(String name) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.name = ? " + QUERY_ACTIVE_WHERE_PART + " ORDER BY o.name", this,name);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'description = :description'.
	 */
	@Transactional
	public List<ReportDesign> findWhereDescriptionEquals(String description) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.description = ? " + QUERY_ACTIVE_WHERE_PART + " ORDER BY o.description", this,description);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'category_id = :categoryId'.
	 */
	@Transactional
	public List<ReportDesign> findWhereCategoryIdEquals(Long categoryId) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.category_id = ? " + QUERY_ACTIVE_WHERE_PART + " ORDER BY o.category_id", this,categoryId);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'query_text = :queryText'.
	 */
	@Transactional
	public List<ReportDesign> findWhereQueryTextEquals(String queryText) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.query_text = ? " + QUERY_ACTIVE_WHERE_PART + " ORDER BY o.query_text", this,queryText);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'select_part = :selectPart'.
	 */
	@Transactional
	public List<ReportDesign> findWhereSelectPartEquals(String selectPart) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.select_part = ? " + QUERY_ACTIVE_WHERE_PART + " ORDER BY o.select_part", this,selectPart);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'result_data = :resultData'.
	 */
	@Transactional
	public List<ReportDesign> findWhereResultDataEquals(String resultData) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.result_data = ? " + QUERY_ACTIVE_WHERE_PART + " ORDER BY o.result_data", this,resultData);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'format_file = :formatFile'.
	 */
	@Transactional
	public List<ReportDesign> findWhereFormatFileEquals(String formatFile) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.format_file = ? " + QUERY_ACTIVE_WHERE_PART + " ORDER BY o.format_file", this,formatFile);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria 'record_status = :recordStatus'.
	 */
	@Transactional
	public List<ReportDesign> findWhereRecordStatusEquals(String recordStatus) throws ReportDesignDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.record_status = ? ORDER BY o.record_status", this,recordStatus);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
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
