package com.sharifpro.eurb.builder.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.ReportCategoryDao;
import com.sharifpro.eurb.builder.exception.ReportCategoryDaoException;
import com.sharifpro.eurb.builder.model.ReportCategory;
import com.sharifpro.eurb.builder.model.ReportCategoryPk;
import com.sharifpro.eurb.info.RecordStatus;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.dao.impl.PersistableObjectDaoImpl;
import com.sharifpro.util.PropertyProvider;

public class ReportCategoryDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportCategory>, ReportCategoryDao
{

	private final static String QUERY_FROM_COLUMNS = "o.name, o.description";

	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + " o " + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;

	private final static String COUNT_QUERY = "SELECT count(distinct(o.id)) FROM " + getTableName() + " o ";

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportCategoryPk
	 * @throws ReportCategoryDaoException 
	 */
	@Transactional(readOnly = false)
	public ReportCategoryPk insert(ReportCategory dto) throws ReportCategoryDaoException
	{
		try{
			ReportCategoryPk pk = new ReportCategoryPk();
			DaoFactory.createPersistableObjectDao().insert(dto, pk);

			jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, name, description ) VALUES ( ?, ?, ? )",pk.getId(),dto.getName(),dto.getDescription());
			return pk;
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Updates a single row in the report_category table.
	 */
	@Transactional(readOnly = false)
	public void update(ReportCategoryPk pk, ReportCategory dto) throws ReportCategoryDaoException
	{
		try{
			DaoFactory.createPersistableObjectDao().update(pk);
			jdbcTemplate.update("UPDATE " + getTableName() + " SET name = ?, description = ? WHERE id = ?",dto.getName(),dto.getDescription(),pk.getId());
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Deletes a single row in the report_category table.
	 */
	@Transactional(readOnly = false)
	public void delete(ReportCategoryPk pk) throws ReportCategoryDaoException
	{
		try{
			//first we have to check for any active report for this category
			int countActive = jdbcTemplate.queryForInt("SELECT count(id) FROM " + ReportDesignDaoImpl.getTableName() + " WHERE category_id = ? AND record_status <> ? ",pk.getId(), RecordStatus.DELETED.getId());
			//if there is any active report for the category, user cannot delete it
			if(countActive > 0){
				throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE);
			}
			//else we have to change category_id for not active reports of this category
			jdbcTemplate.update("UPDATE " + ReportDesignDaoImpl.getTableName() + " SET category_id = NULL WHERE category_id = ?", pk.getId());
			//and then delete the category
			jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
			DaoFactory.createPersistableObjectDao().delete(pk);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Deletes multiple rows in the report_category table.
	 */
	@Transactional
	public void deleteAll(List<ReportCategoryPk> pkList) throws ReportCategoryDaoException
	{
		for(ReportCategoryPk pk : pkList) {
			delete(pk);
		}
	}


	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return ReportCategory
	 */
	public ReportCategory mapRow(ResultSet rs, int row) throws SQLException
	{
		ReportCategory dto = new ReportCategory();
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setName( rs.getString(++i) );
		dto.setDescription( rs.getString(++i) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "report_category";
	}

	/** 
	 * Returns all rows from the report_category table that match the criteria 'id = :id'.
	 */
	@Transactional(readOnly = true)
	public ReportCategory findByPrimaryKey(Long id) throws ReportCategoryDaoException
	{
		try {
			List<ReportCategory> list = jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_category table that match the criteria ''.
	 */
	@Transactional(readOnly = true)
	public List<ReportCategory> findAll() throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " ORDER BY o.id", this);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	@Transactional
	public int countAll() throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.queryForInt(COUNT_QUERY);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}


	/** 
	 * Returns all rows from the report_category table that match the criteria '' limited by start and limit.
	 */
	@Transactional
	public List<ReportCategory> findAll(Integer start, Integer limit) throws ReportCategoryDaoException{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " ORDER BY o.id limit ?, ?", this, start, limit);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria like query in onFields fields limited by start and limit.
	 */
	@Transactional
	public List<ReportCategory> findAll(String query, List<String> onFields, Integer start, Integer limit) throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE (" + getMultipleFieldWhereClause(query, onFields) + ") ORDER BY o.id limit ?, ?", this, start, limit);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	@Transactional
	public int countAll(String query, List<String> onFields) throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.queryForInt(COUNT_QUERY + " WHERE (" + getMultipleFieldWhereClause(query, onFields) + ")");
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}



	/** 
	 * Returns all rows from the report_category table that match the criteria 'id = :id'.
	 */
	@Transactional(readOnly = true)
	public List<ReportCategory> findByPersistableObject(Long id) throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.id = ?", this,id);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_category table that match the criteria 'id = :id'.
	 */
	@Transactional(readOnly = true)
	public List<ReportCategory> findWhereIdEquals(Long id) throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_category table that match the criteria 'name = :name'.
	 */
	@Transactional(readOnly = true)
	public List<ReportCategory> findWhereNameEquals(String name) throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.name = ? ORDER BY name", this,name);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}



	/** 
	 * Returns the rows from the report_category table that matches the specified primary-key value.
	 */
	public ReportCategory findByPrimaryKey(ReportCategoryPk pk) throws ReportCategoryDaoException
	{
		return findByPrimaryKey( pk.getId() );
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

}
