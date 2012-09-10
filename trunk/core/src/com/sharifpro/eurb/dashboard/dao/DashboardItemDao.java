package com.sharifpro.eurb.dashboard.dao;

import com.sharifpro.eurb.dashboard.dao.DashboardItemDao;
import com.sharifpro.eurb.dashboard.model.DashboardItem;
import com.sharifpro.eurb.dashboard.model.DashboardItemPk;
import com.sharifpro.eurb.dashboard.exceptions.DashboardItemDaoException;
import java.util.List;

public interface DashboardItemDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return DashboardItemPk
	 */
	public DashboardItemPk insert(DashboardItem dto);

	/** 
	 * Updates a single row in the dashboard_item table.
	 */
	public void update(DashboardItemPk pk, DashboardItem dto) throws DashboardItemDaoException;

	/** 
	 * Deletes a single row in the dashboard_item table.
	 */
	public void delete(DashboardItemPk pk) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'id = :id'.
	 */
	public DashboardItem findByPrimaryKey(long id) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria ''.
	 */
	public List<DashboardItem> findAll() throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'id = :id'.
	 */
	public List<DashboardItem> findByPersistableObject(Long id) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'dashboard_col_id = :dashboardColId'.
	 */
	public List<DashboardItem> findByDashboardCol(Long dashboardColId) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'dashboard_id = :dashboardId'.
	 */
	public List<DashboardItem> findByDashboard(Long dashboardId) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'id = :id'.
	 */
	public List<DashboardItem> findWhereIdEquals(Long id) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'dashboard_id = :dashboardId'.
	 */
	public List<DashboardItem> findWhereDashboardIdEquals(Long dashboardId) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'dashboard_col_id = :dashboardColId'.
	 */
	public List<DashboardItem> findWhereDashboardColIdEquals(Long dashboardColId) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'item_order = :itemOrder'.
	 */
	public List<DashboardItem> findWhereItemOrderEquals(Integer itemOrder) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'item_height = :itemHeight'.
	 */
	public List<DashboardItem> findWhereItemHeightEquals(Double itemHeight) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'item_collapsed = :itemCollapsed'.
	 */
	public List<DashboardItem> findWhereItemCollapsedEquals(Short itemCollapsed) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'item_closed = :itemClosed'.
	 */
	public List<DashboardItem> findWhereItemClosedEquals(String itemClosed) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'report_design_id = :reportDesignId'.
	 */
	public List<DashboardItem> findWhereReportDesignIdEquals(Long reportDesignId) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'report_chart_id = :reportChartId'.
	 */
	public List<DashboardItem> findWhereReportChartIdEquals(Long reportChartId) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'is_show_table = :isShowTable'.
	 */
	public List<DashboardItem> findWhereIsShowTableEquals(Short isShowTable) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'item_title = :itemTitle'.
	 */
	public List<DashboardItem> findWhereItemTitleEquals(String itemTitle) throws DashboardItemDaoException;

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'item_content = :itemContent'.
	 */
	public List<DashboardItem> findWhereItemContentEquals(String itemContent) throws DashboardItemDaoException;

	/** 
	 * Returns the rows from the dashboard_item table that matches the specified primary-key value.
	 */
	public DashboardItem findByPrimaryKey(DashboardItemPk pk) throws DashboardItemDaoException;

}
