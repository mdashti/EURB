package com.sharifpro.eurb.builder.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.ReportCategoryDao;
import com.sharifpro.eurb.builder.exception.ReportCategoryDaoException;
import com.sharifpro.eurb.builder.model.ReportCategory;
import com.sharifpro.eurb.builder.model.ReportCategoryPk;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.dao.impl.PersistableObjectDaoImpl;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class ReportCategoryDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportCategory>, ReportCategoryDao
{
	
	private final static String QUERY_FROM_COLUMNS = "o.name, o.description";

	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportCategoryPk
	 */
	@Transactional(readOnly = false)
	public ReportCategoryPk insert(ReportCategory dto)
	{
		ReportCategoryPk pk = new ReportCategoryPk();
		DaoFactory.createPersistableObjectDao().insert(dto, pk);
		
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, name, description ) VALUES ( ?, ?, ? )",pk.getId(),dto.getName(),dto.getDescription());
		return pk;
	}

	/** 
	 * Updates a single row in the report_category table.
	 */
	@Transactional(readOnly = false)
	public void update(ReportCategoryPk pk, ReportCategory dto) throws ReportCategoryDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk, dto);
		jdbcTemplate.update("UPDATE " + getTableName() + " SET name = ?, description = ? WHERE id = ?",dto.getName(),dto.getDescription(),pk.getId());
	}

	/** 
	 * Deletes a single row in the report_category table.
	 */
	@Transactional(readOnly = false)
	public void delete(ReportCategoryPk pk) throws ReportCategoryDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
		DaoFactory.createPersistableObjectDao().delete(pk);
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
			List<ReportCategory> list = jdbcTemplate.query(QUERY_SELECT_PART + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_category table that match the criteria ''.
	 */
	@Transactional(readOnly = true)
	public List<ReportCategory> findAll() throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_category table that match the criteria 'id = :id'.
	 */
	@Transactional(readOnly = true)
	public List<ReportCategory> findByPersistableObject(Long id) throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_category table that match the criteria 'id = :id'.
	 */
	@Transactional(readOnly = true)
	public List<ReportCategory> findWhereIdEquals(Long id) throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_category table that match the criteria 'name = :name'.
	 */
	@Transactional(readOnly = true)
	public List<ReportCategory> findWhereNameEquals(String name) throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE name = ? ORDER BY name", this,name);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException("Query failed", e);
		}
		
	}

	

	/** 
	 * Returns the rows from the report_category table that matches the specified primary-key value.
	 */
	public ReportCategory findByPrimaryKey(ReportCategoryPk pk) throws ReportCategoryDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

}
