package com.sharifpro.eurb.dashboard.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.dashboard.dao.DashboardColDao;
import com.sharifpro.eurb.dashboard.exceptions.DashboardColDaoException;
import com.sharifpro.eurb.dashboard.model.DashboardCol;
import com.sharifpro.eurb.dashboard.model.DashboardColPk;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DashboardColDaoImpl extends AbstractDAO implements ParameterizedRowMapper<DashboardCol>, DashboardColDao
{

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return DashboardColPk
	 */
	@Transactional
	public DashboardColPk insert(DashboardCol dto)
	{
		DashboardColPk pk = new DashboardColPk();
		DaoFactory.createPersistableObjectDao().insert(dto, pk);
		getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, dashboard_id, col_order, col_width ) VALUES ( ?, ?, ?, ? )",dto.getId(),dto.getDashboardId(),dto.getColOrder(),dto.getColWidth());
		return pk;
	}

	/** 
	 * Updates a single row in the dashboard_col table.
	 */
	@Transactional
	public void update(DashboardColPk pk, DashboardCol dto) throws DashboardColDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		getJdbcTemplate().update("UPDATE " + getTableName() + " SET id = ?, dashboard_id = ?, col_order = ?, col_width = ? WHERE id = ?",dto.getId(),dto.getDashboardId(),dto.getColOrder(),dto.getColWidth(),pk.getId());
	}

	/** 
	 * Deletes a single row in the dashboard_col table.
	 */
	@Transactional
	public void delete(DashboardColPk pk) throws DashboardColDaoException
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
	 * @return DashboardCol
	 */
	public DashboardCol mapRow(ResultSet rs, int row) throws SQLException
	{
		DashboardCol dto = new DashboardCol();
		dto.setId( rs.getLong( 1 ) );
		dto.setDashboardId( rs.getLong( 2 ) );
		dto.setColOrder( rs.getInt( 3 ) );
		dto.setColWidth( rs.getDouble( 4 ) );
		
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "eurb.dashboard_col";
	}

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria 'id = :id'.
	 */
	@Transactional
	public DashboardCol findByPrimaryKey(long id) throws DashboardColDaoException
	{
		try {
			List<DashboardCol> list = getJdbcTemplate().query("SELECT id, dashboard_id, col_order, col_width FROM " + getTableName() + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new DashboardColDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria ''.
	 */
	@Transactional
	public List<DashboardCol> findAll() throws DashboardColDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, col_order, col_width FROM " + getTableName() + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new DashboardColDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria 'dashboard_id = :dashboardId'.
	 */
	@Transactional
	public List<DashboardCol> findByDashboard(long dashboardId) throws DashboardColDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, col_order, col_width FROM " + getTableName() + " WHERE dashboard_id = ? ORDER BY col_order ASC", this,dashboardId);
		}
		catch (Exception e) {
			throw new DashboardColDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<DashboardCol> findByPersistableObject(long id) throws DashboardColDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, col_order, col_width FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new DashboardColDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<DashboardCol> findWhereIdEquals(long id) throws DashboardColDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, col_order, col_width FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new DashboardColDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria 'dashboard_id = :dashboardId'.
	 */
	@Transactional
	public List<DashboardCol> findWhereDashboardIdEquals(long dashboardId) throws DashboardColDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, col_order, col_width FROM " + getTableName() + " WHERE dashboard_id = ? ORDER BY col_order ASC", this,dashboardId);
		}
		catch (Exception e) {
			throw new DashboardColDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria 'col_order = :colOrder'.
	 */
	@Transactional
	public List<DashboardCol> findWhereColOrderEquals(int colOrder) throws DashboardColDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, col_order, col_width FROM " + getTableName() + " WHERE col_order = ? ORDER BY col_order", this,colOrder);
		}
		catch (Exception e) {
			throw new DashboardColDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria 'col_width = :colWidth'.
	 */
	@Transactional
	public List<DashboardCol> findWhereColWidthEquals(double colWidth) throws DashboardColDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, col_order, col_width FROM " + getTableName() + " WHERE col_width = ? ORDER BY col_width", this,colWidth);
		}
		catch (Exception e) {
			throw new DashboardColDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the dashboard_col table that matches the specified primary-key value.
	 */
	public DashboardCol findByPrimaryKey(DashboardColPk pk) throws DashboardColDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

}
