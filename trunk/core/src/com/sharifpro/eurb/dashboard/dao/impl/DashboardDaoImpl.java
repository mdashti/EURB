package com.sharifpro.eurb.dashboard.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.dashboard.dao.DashboardDao;
import com.sharifpro.eurb.dashboard.exceptions.DashboardDaoException;
import com.sharifpro.eurb.dashboard.model.Dashboard;
import com.sharifpro.eurb.dashboard.model.DashboardPk;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DashboardDaoImpl extends AbstractDAO implements ParameterizedRowMapper<Dashboard>, DashboardDao
{

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return DashboardPk
	 */
	@Transactional
	public DashboardPk insert(Dashboard dto)
	{
		DashboardPk pk = new DashboardPk();
		DaoFactory.createPersistableObjectDao().insert(dto, pk);
		getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, is_default, title, parent_dashboard, username ) VALUES ( ?, ?, ?, ?, ? )",dto.getId(),dto.getIsDefault(),dto.getTitle(),dto.getParentDashboard(),dto.getUsername());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the dashboard table.
	 */
	@Transactional
	public void update(DashboardPk pk, Dashboard dto) throws DashboardDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		getJdbcTemplate().update("UPDATE " + getTableName() + " SET id = ?, is_default = ?, title = ?, parent_dashboard = ?, username = ? WHERE id = ?",dto.getId(),dto.getIsDefault(),dto.getTitle(),dto.getParentDashboard(),dto.getUsername(),pk.getId());
	}

	/** 
	 * Deletes a single row in the dashboard table.
	 */
	@Transactional
	public void delete(DashboardPk pk) throws DashboardDaoException
	{
		getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
		DaoFactory.createPersistableObjectDao().delete(pk);
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return Dashboard
	 */
	public Dashboard mapRow(ResultSet rs, int row) throws SQLException
	{
		Dashboard dto = new Dashboard();
		dto.setId( rs.getLong( 1 ) );
		dto.setIsDefault( rs.getBoolean( 2 ) );
		dto.setTitle( rs.getString( 3 ) );
		dto.setParentDashboard( rs.getLong( 4 ) );
		if(rs.wasNull()) {
			dto.setParentDashboard(null);
		}
		dto.setUsername( rs.getString( 5 ) );
		if(rs.wasNull()) {
			dto.setUsername(null);
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
		return "eurb.dashboard";
	}

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'id = :id'.
	 */
	@Transactional
	public Dashboard findByPrimaryKey(long id) throws DashboardDaoException
	{
		try {
			List<Dashboard> list = getJdbcTemplate().query("SELECT id, is_default, title, parent_dashboard, username FROM " + getTableName() + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new DashboardDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard table that match the criteria ''.
	 */
	@Transactional
	public List<Dashboard> findAll() throws DashboardDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, is_default, title, parent_dashboard, username FROM " + getTableName() + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new DashboardDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<Dashboard> findByPersistableObject(long id) throws DashboardDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, is_default, title, parent_dashboard, username FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new DashboardDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'parent_dashboard = :parentDashboard'.
	 */
	@Transactional
	public List<Dashboard> findByDashboard(long parentDashboard) throws DashboardDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, is_default, title, parent_dashboard, username FROM " + getTableName() + " WHERE parent_dashboard = ?", this,parentDashboard);
		}
		catch (Exception e) {
			throw new DashboardDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<Dashboard> findWhereIdEquals(long id) throws DashboardDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, is_default, title, parent_dashboard, username FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new DashboardDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'is_default = :isDefault'.
	 */
	@Transactional
	public List<Dashboard> findWhereIsDefaultEquals(short isDefault) throws DashboardDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, is_default, title, parent_dashboard, username FROM " + getTableName() + " WHERE is_default = ? ORDER BY is_default", this,isDefault);
		}
		catch (Exception e) {
			throw new DashboardDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'title = :title'.
	 */
	@Transactional
	public List<Dashboard> findWhereTitleEquals(String title) throws DashboardDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, is_default, title, parent_dashboard, username FROM " + getTableName() + " WHERE title = ? ORDER BY title", this,title);
		}
		catch (Exception e) {
			throw new DashboardDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'parent_dashboard = :parentDashboard'.
	 */
	@Transactional
	public List<Dashboard> findWhereParentDashboardEquals(long parentDashboard) throws DashboardDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, is_default, title, parent_dashboard, username FROM " + getTableName() + " WHERE parent_dashboard = ? ORDER BY parent_dashboard", this,parentDashboard);
		}
		catch (Exception e) {
			throw new DashboardDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'username = :username'.
	 */
	@Transactional
	public List<Dashboard> findWhereUsernameEquals(String username) throws DashboardDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, is_default, title, parent_dashboard, username FROM " + getTableName() + " WHERE username = ? ORDER BY username", this,username);
		}
		catch (Exception e) {
			throw new DashboardDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the dashboard table that matches the specified primary-key value.
	 */
	public Dashboard findByPrimaryKey(DashboardPk pk) throws DashboardDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

}
