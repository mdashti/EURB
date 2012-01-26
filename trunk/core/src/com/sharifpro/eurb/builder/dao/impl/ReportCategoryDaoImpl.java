package com.sharifpro.eurb.builder.dao.impl;

import com.sharifpro.eurb.builder.dao.ReportCategoryDao;
import com.sharifpro.eurb.builder.exception.ReportCategoryDaoException;
import com.sharifpro.eurb.builder.model.ReportCategory;
import com.sharifpro.eurb.builder.model.ReportCategoryPk;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class ReportCategoryDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportCategory>, ReportCategoryDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportCategoryPk
	 */
	@Transactional
	public ReportCategoryPk insert(ReportCategory dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, name, description ) VALUES ( ?, ?, ? )",dto.getId(),dto.getName(),dto.getDescription());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the report_category table.
	 */
	@Transactional
	public void update(ReportCategoryPk pk, ReportCategory dto) throws ReportCategoryDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET id = ?, name = ?, description = ? WHERE id = ?",dto.getId(),dto.getName(),dto.getDescription(),pk.getId());
	}

	/** 
	 * Deletes a single row in the report_category table.
	 */
	@Transactional
	public void delete(ReportCategoryPk pk) throws ReportCategoryDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
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
		dto.setId( new Long( rs.getLong(1) ) );
		dto.setName( rs.getString( 2 ) );
		dto.setDescription( rs.getString( 3 ) );
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
	@Transactional
	public ReportCategory findByPrimaryKey(Long id) throws ReportCategoryDaoException
	{
		try {
			List<ReportCategory> list = jdbcTemplate.query("SELECT id, name, description FROM " + getTableName() + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_category table that match the criteria ''.
	 */
	@Transactional
	public List<ReportCategory> findAll() throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, name, description FROM " + getTableName() + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_category table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportCategory> findByPersistableObject(Long id) throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, name, description FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_category table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportCategory> findWhereIdEquals(Long id) throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, name, description FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_category table that match the criteria 'name = :name'.
	 */
	@Transactional
	public List<ReportCategory> findWhereNameEquals(String name) throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, name, description FROM " + getTableName() + " WHERE name = ? ORDER BY name", this,name);
		}
		catch (Exception e) {
			throw new ReportCategoryDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_category table that match the criteria 'description = :description'.
	 */
	@Transactional
	public List<ReportCategory> findWhereDescriptionEquals(String description) throws ReportCategoryDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, name, description FROM " + getTableName() + " WHERE description = ? ORDER BY description", this,description);
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
