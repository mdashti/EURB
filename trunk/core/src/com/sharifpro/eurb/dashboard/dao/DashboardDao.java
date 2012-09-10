package com.sharifpro.eurb.dashboard.dao;

import com.sharifpro.eurb.dashboard.dao.DashboardDao;
import com.sharifpro.eurb.dashboard.exceptions.DashboardDaoException;
import com.sharifpro.eurb.dashboard.model.Dashboard;
import com.sharifpro.eurb.dashboard.model.DashboardPk;

import java.util.List;

public interface DashboardDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return DashboardPk
	 */
	public DashboardPk insert(Dashboard dto);

	/** 
	 * Updates a single row in the dashboard table.
	 */
	public void update(DashboardPk pk, Dashboard dto) throws DashboardDaoException;

	/** 
	 * Deletes a single row in the dashboard table.
	 */
	public void delete(DashboardPk pk) throws DashboardDaoException;

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'id = :id'.
	 */
	public Dashboard findByPrimaryKey(long id) throws DashboardDaoException;

	/** 
	 * Returns all rows from the dashboard table that match the criteria ''.
	 */
	public List<Dashboard> findAll() throws DashboardDaoException;

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'id = :id'.
	 */
	public List<Dashboard> findByPersistableObject(long id) throws DashboardDaoException;

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'parent_dashboard = :parentDashboard'.
	 */
	public List<Dashboard> findByDashboard(long parentDashboard) throws DashboardDaoException;

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'id = :id'.
	 */
	public List<Dashboard> findWhereIdEquals(long id) throws DashboardDaoException;

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'is_default = :isDefault'.
	 */
	public List<Dashboard> findWhereIsDefaultEquals(short isDefault) throws DashboardDaoException;

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'title = :title'.
	 */
	public List<Dashboard> findWhereTitleEquals(String title) throws DashboardDaoException;

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'parent_dashboard = :parentDashboard'.
	 */
	public List<Dashboard> findWhereParentDashboardEquals(long parentDashboard) throws DashboardDaoException;

	/** 
	 * Returns all rows from the dashboard table that match the criteria 'username = :username'.
	 */
	public List<Dashboard> findWhereUsernameEquals(String username) throws DashboardDaoException;

	/** 
	 * Returns the rows from the dashboard table that matches the specified primary-key value.
	 */
	public Dashboard findByPrimaryKey(DashboardPk pk) throws DashboardDaoException;

}
