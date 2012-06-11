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
	private final static String QUERY_FROM_COLUMNS = "o.version_id, o.name, o.description, o.category_id, o.query_text, o.select_part, o.result_data, o.format_file, o.is_current, o.record_status, o.db_config_id ";

	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + " o " + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;

	private final static String QUERY_ACTIVE_WHERE_PART = " o.is_current = 1 and o.record_status = '" + RecordStatus.ACTIVE + "'";

	private final static String QUERY_ACTIVE_AND_PASSIVE_WHERE = " o.record_status IN ('" + RecordStatus.ACTIVE.getId() + "', '" + RecordStatus.PASSIVE.getId() + "') and o.is_current = 1 ";

	private final static String COUNT_QUERY = "SELECT count(distinct(o.id)) FROM " + getTableName() + " o ";


	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportDesignPk
	 * @throws ReportDesignDaoException 
	 */
	@Transactional(readOnly = false)
	public ReportDesignPk insert(ReportDesign dto) throws ReportDesignDaoException
	{
		try{
			ReportDesignPk pk = new ReportDesignPk(); 
			DaoFactory.createPersistableObjectDao().insert(dto, pk);
			Long versionId = DaoFactory.createPersistableObjectDao().makeVersionId();
			pk.setVersionId(versionId);
			getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status, db_config_id ) " +
					"VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",pk.getId(),pk.getVersionId(),dto.getName(),dto.getDescription(),dto.getCategoryId(),dto.getQueryText(),dto.getSelectPart(),dto.getResultData(),
					dto.getFormatFile(),dto.isIsCurrent(),dto.getRecordStatusString(), dto.getDbConfigId());
			return pk;
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Updates a single row in the report_design table.
	 */
	@Transactional(readOnly = false)
	public void update(ReportDesignPk pk, ReportDesign dto) throws ReportDesignDaoException
	{
		try{
			getJdbcTemplate().update("UPDATE " + getTableName() + " SET is_current = 0 WHERE id = ? AND version_id = ?", pk.getId(), pk.getVersionId());
			DaoFactory.createPersistableObjectDao().update(pk);
			Long versionId = DaoFactory.createPersistableObjectDao().makeVersionId();
			pk.setVersionId(versionId);
			getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, version_id, name, description, category_id, query_text, select_part, result_data, format_file, is_current, record_status, db_config_id ) " +
					"VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",pk.getId(),pk.getVersionId(),dto.getName(),dto.getDescription(),dto.getCategoryId(),dto.getQueryText(),dto.getSelectPart(),dto.getResultData(),
					dto.getFormatFile(),dto.isIsCurrent(),dto.getRecordStatusString(), dto.getDbConfigId());
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}
	
	/** 
	 * Updates a single row in the report_design table but doesn't make a new version.
	 */
	@Transactional(readOnly = false)
	public void updateCurrentVersion(ReportDesignPk pk, ReportDesign dto) throws ReportDesignDaoException
	{
		try{
			DaoFactory.createPersistableObjectDao().update(pk);
			getJdbcTemplate().update("UPDATE " + getTableName() + " SET name = ?, description = ?, category_id = ?, query_text = ?, select_part = ?, result_data = ?, format_file = ?, is_current = ?, record_status = ?, db_config_id = ?  " +
					"WHERE id = ? AND version_id = ?",dto.getName(),dto.getDescription(),dto.getCategoryId(),dto.getQueryText(),dto.getSelectPart(),dto.getResultData(),dto.getFormatFile(),dto.isIsCurrent(),
					dto.getRecordStatusString(), dto.getDbConfigId(),pk.getId(),pk.getVersionId());
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/**
	 * Sets result data for a given report design in database
	 * Is it really necessary???
	 * */
	@Transactional(readOnly = false)
	public void setResultData(ReportDesignPk pk, ReportDesign dto)
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		getJdbcTemplate().update("UPDATE " + getTableName() + " SET result_data = ? WHERE id = ? and version_id = ?", dto.getResultData(), pk.getId(), pk.getVersionId());
	}

	/**
	 * Activates a single row in report_design table
	 * @param pk
	 */
	@Transactional(readOnly = false)
	public void activate(ReportDesignPk pk) throws ReportDesignDaoException
	{
		try{
			DaoFactory.createPersistableObjectDao().update(pk);
			getJdbcTemplate().update("UPDATE " + getTableName() + " SET record_status = ? WHERE id = ? ", RecordStatus.ACTIVE.getId(), pk.getId());
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/**
	 * Activates multiple row in report_desing table
	 */
	@Transactional
	public void activateAll(List<ReportDesignPk> pkList) throws ReportDesignDaoException
	{
		for(ReportDesignPk pk : pkList){
			activate(pk);
		}
	}

	/**
	 * Deactivates a single row in database
	 * @param pk
	 */
	@Transactional(readOnly = false)
	public void deactivate(ReportDesignPk pk) throws ReportDesignDaoException
	{
		try{
			DaoFactory.createPersistableObjectDao().update(pk);
			getJdbcTemplate().update("UPDATE " + getTableName() + " SET record_status = ? WHERE id = ? ", RecordStatus.PASSIVE.getId(), pk.getId());
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/**
	 * Deactivates multiple row in report_desing table
	 */
	@Transactional
	public void deactivateAll(List<ReportDesignPk> pkList) throws ReportDesignDaoException
	{
		for(ReportDesignPk pk : pkList){
			deactivate(pk);
		}
	}



	/** 
	 * Deletes a single row in the report_design table.
	 */
	@Transactional(readOnly = false)
	public void delete(ReportDesignPk pk) throws ReportDesignDaoException
	{
		try{
			DaoFactory.createPersistableObjectDao().update(pk);
			getJdbcTemplate().update("UPDATE " + getTableName() + " SET record_status = ? WHERE id = ?", RecordStatus.DELETED.getId() ,pk.getId());
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/**
	 * Deletes multiple given rows from the report_design table.
	 */
	@Transactional
	public void deleteAll(List<ReportDesignPk> pkList) throws ReportDesignDaoException
	{
		for(ReportDesignPk pk : pkList){
			delete(pk);
		}
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
		dto.setDbConfigId( new Long( rs.getLong( ++i ) ) );
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
			List<ReportDesign> list = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ? AND o.is_current = 1", this,id);
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
			List<ReportDesign> list = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ? AND o.version_id = ?", this,id, versionId);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE " + QUERY_ACTIVE_AND_PASSIVE_WHERE + " ORDER BY o.id, o.version_id", this);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	@Transactional
	public int countAll() throws ReportDesignDaoException
	{
		try {
			return getJdbcTemplate().queryForInt(COUNT_QUERY + " WHERE " + QUERY_ACTIVE_AND_PASSIVE_WHERE);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_design table that match the criteria '' limited by start and limit.
	 */
	@Transactional
	public List<ReportDesign> findAll(Integer start, Integer limit, String sortBy, String sortDir) throws ReportDesignDaoException{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE " + QUERY_ACTIVE_AND_PASSIVE_WHERE + " ORDER BY " + getSortClause(sortBy, sortDir) + " limit ?, ?", this, start, limit);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Returns all rows from the report_design table that match the criteria like query in onFields fields limited by start and limit.
	 */
	@Transactional
	public List<ReportDesign> findAll(String query, List<String> onFields, Integer start, Integer limit, String sortBy, String sortDir) throws ReportDesignDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE " + QUERY_ACTIVE_AND_PASSIVE_WHERE + 
					" AND (" + getMultipleFieldWhereClause(query, onFields) + ") ORDER BY " + getSortClause(sortBy, sortDir) + " limit ?, ?", this, start, limit);
		}
		catch (Exception e) {
			throw new ReportDesignDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	@Transactional
	public int countAll(String query, List<String> onFields) throws ReportDesignDaoException
	{
		try {
			return getJdbcTemplate().queryForInt(COUNT_QUERY + " WHERE " + QUERY_ACTIVE_AND_PASSIVE_WHERE + 
					" AND (" + getMultipleFieldWhereClause(query, onFields) + ")");
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE " + QUERY_ACTIVE_WHERE_PART + "' ORDER BY o.id, o.version_id", this);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ? and o.is_current = 1", this,id);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ? and o.version_id = ?", this,id,versionId);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.category_id = ? and " + QUERY_ACTIVE_AND_PASSIVE_WHERE, this,categoryId);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ? and o.is_current = 1 ORDER BY o.id", this,id);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ? AND o.version_id = ? ORDER BY o.id, o.version_id", this,id,versionId);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.name = ? AND " + QUERY_ACTIVE_WHERE_PART + " ORDER BY o.name", this,name);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.description = ? AND " + QUERY_ACTIVE_WHERE_PART + " ORDER BY o.description", this,description);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.category_id = ? AND " + QUERY_ACTIVE_AND_PASSIVE_WHERE + " ORDER BY o.category_id", this,categoryId);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.query_text = ? AND " + QUERY_ACTIVE_WHERE_PART + " ORDER BY o.query_text", this,queryText);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.select_part = ? AND " + QUERY_ACTIVE_WHERE_PART + " ORDER BY o.select_part", this,selectPart);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.result_data = ? AND " + QUERY_ACTIVE_WHERE_PART + " ORDER BY o.result_data", this,resultData);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.format_file = ? AND " + QUERY_ACTIVE_WHERE_PART + " ORDER BY o.format_file", this,formatFile);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.record_status = ? ORDER BY o.record_status", this,recordStatus);
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


	private static String getMultipleFieldWhereClause(String txt, List<String> fields) {
		StringBuilder query = new StringBuilder();
		String likeQuery = " LIKE '%"+txt+"%' OR ";
		if(fields.contains("name")) {
			query.append("o.name").append(likeQuery);
		}
		if(fields.contains("description")) {
			query.append("o.description").append(likeQuery);
		}
		if(query.length() > 0) {
			return query.substring(0,query.length()-3);
		} else {
			return "";
		}
	}

	private static String getSortClause(String sortBy, String sortDir) {
		StringBuilder result = new StringBuilder();
		if("id".equals(sortBy)) {
			result.append("o.id");
		} else if("name".equals(sortBy)) {
			result.append("o.name");
		} else if("description".equals(sortBy)) {
			result.append("o.description");
		} else {
			result.append("o.id");
		}
		result.append(" ").append(AbstractDAO.DESCENDING_SORT_ORDER.equals(sortDir) ? AbstractDAO.DESCENDING_SORT_ORDER : AbstractDAO.ASCENDING_SORT_ORDER);
		return result.toString();
	}

}
