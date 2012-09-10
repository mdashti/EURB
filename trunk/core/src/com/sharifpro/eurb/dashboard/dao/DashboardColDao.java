package com.sharifpro.eurb.dashboard.dao;

import com.sharifpro.eurb.dashboard.dao.DashboardColDao;
import com.sharifpro.eurb.dashboard.exceptions.DashboardColDaoException;
import com.sharifpro.eurb.dashboard.model.DashboardCol;
import com.sharifpro.eurb.dashboard.model.DashboardColPk;

import java.util.List;

public interface DashboardColDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return DashboardColPk
	 */
	public DashboardColPk insert(DashboardCol dto);

	/** 
	 * Updates a single row in the dashboard_col table.
	 */
	public void update(DashboardColPk pk, DashboardCol dto) throws DashboardColDaoException;

	/** 
	 * Deletes a single row in the dashboard_col table.
	 */
	public void delete(DashboardColPk pk) throws DashboardColDaoException;

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria 'id = :id'.
	 */
	public DashboardCol findByPrimaryKey(long id) throws DashboardColDaoException;

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria ''.
	 */
	public List<DashboardCol> findAll() throws DashboardColDaoException;

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria 'dashboard_id = :dashboardId'.
	 */
	public List<DashboardCol> findByDashboard(long dashboardId) throws DashboardColDaoException;

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria 'id = :id'.
	 */
	public List<DashboardCol> findByPersistableObject(long id) throws DashboardColDaoException;

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria 'id = :id'.
	 */
	public List<DashboardCol> findWhereIdEquals(long id) throws DashboardColDaoException;

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria 'dashboard_id = :dashboardId'.
	 */
	public List<DashboardCol> findWhereDashboardIdEquals(long dashboardId) throws DashboardColDaoException;

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria 'col_order = :colOrder'.
	 */
	public List<DashboardCol> findWhereColOrderEquals(int colOrder) throws DashboardColDaoException;

	/** 
	 * Returns all rows from the dashboard_col table that match the criteria 'col_width = :colWidth'.
	 */
	public List<DashboardCol> findWhereColWidthEquals(double colWidth) throws DashboardColDaoException;

	/** 
	 * Returns the rows from the dashboard_col table that matches the specified primary-key value.
	 */
	public DashboardCol findByPrimaryKey(DashboardColPk pk) throws DashboardColDaoException;

}
