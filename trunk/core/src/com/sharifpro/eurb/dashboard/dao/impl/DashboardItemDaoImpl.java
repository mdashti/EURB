package com.sharifpro.eurb.dashboard.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.dashboard.dao.DashboardItemDao;
import com.sharifpro.eurb.dashboard.model.DashboardItem;
import com.sharifpro.eurb.dashboard.model.DashboardItemPk;
import com.sharifpro.eurb.dashboard.exceptions.DashboardItemDaoException;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DashboardItemDaoImpl extends AbstractDAO implements ParameterizedRowMapper<DashboardItem>, DashboardItemDao
{

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return DashboardItemPk
	 */
	@Transactional
	public DashboardItemPk insert(DashboardItem dto)
	{
		DashboardItemPk pk = new DashboardItemPk();
		DaoFactory.createPersistableObjectDao().insert(dto, pk);
		getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getId(),dto.getDashboardId(),dto.getDashboardColId(),dto.getItemOrder(),dto.getItemHeight(),dto.getItemCollapsed(),dto.getItemClosed(),dto.getReportDesignId(),dto.getReportChartId(),dto.getIsShowTable(),dto.getItemTitle(),dto.getItemContent());
		return pk;
	}

	/** 
	 * Updates a single row in the dashboard_item table.
	 */
	@Transactional
	public void update(DashboardItemPk pk, DashboardItem dto) throws DashboardItemDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		getJdbcTemplate().update("UPDATE " + getTableName() + " SET id = ?, dashboard_id = ?, dashboard_col_id = ?, item_order = ?, item_height = ?, item_collapsed = ?, item_closed = ?, report_design_id = ?, report_chart_id = ?, is_show_table = ?, item_title = ?, item_content = ? WHERE id = ?",dto.getId(),dto.getDashboardId(),dto.getDashboardColId(),dto.getItemOrder(),dto.getItemHeight(),dto.getItemCollapsed(),dto.getItemClosed(),dto.getReportDesignId(),dto.getReportChartId(),dto.getIsShowTable(),dto.getItemTitle(),dto.getItemContent(),pk.getId());
	}

	/** 
	 * Deletes a single row in the dashboard_item table.
	 */
	@Transactional
	public void delete(DashboardItemPk pk) throws DashboardItemDaoException
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
	 * @return DashboardItem
	 */
	public DashboardItem mapRow(ResultSet rs, int row) throws SQLException
	{
		DashboardItem dto = new DashboardItem();
		dto.setId( rs.getLong( 1 ) );
		dto.setDashboardId( rs.getLong( 2 ) );
		dto.setDashboardColId( rs.getLong( 3 ) );
		dto.setItemOrder( rs.getInt( 4 ) );
		dto.setItemHeight( rs.getDouble( 5 ) );
		dto.setItemCollapsed( rs.getBoolean( 6 ) );
		dto.setItemClosed( rs.getBoolean( 7 ) );
		dto.setReportDesignId( rs.getLong( 8 ) );
		if(rs.wasNull()) {
			dto.setReportDesignId( null );
		}
		dto.setReportChartId( rs.getLong( 9 ) );
		if(rs.wasNull()) {
			dto.setReportChartId( null );
		}
		dto.setIsShowTable( rs.getShort( 10 ) );
		dto.setItemTitle( rs.getString( 11 ) );
		dto.setItemContent( rs.getString( 12 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "eurb.dashboard_item";
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'id = :id'.
	 */
	@Transactional
	public DashboardItem findByPrimaryKey(long id) throws DashboardItemDaoException
	{
		try {
			List<DashboardItem> list = getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria ''.
	 */
	@Transactional
	public List<DashboardItem> findAll() throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<DashboardItem> findByPersistableObject(Long id) throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'dashboard_col_id = :dashboardColId'.
	 */
	@Transactional
	public List<DashboardItem> findByDashboardCol(Long dashboardColId) throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE dashboard_col_id = ?", this,dashboardColId);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'dashboard_id = :dashboardId'.
	 */
	@Transactional
	public List<DashboardItem> findByDashboard(Long dashboardId) throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE dashboard_id = ?", this,dashboardId);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<DashboardItem> findWhereIdEquals(Long id) throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'dashboard_id = :dashboardId'.
	 */
	@Transactional
	public List<DashboardItem> findWhereDashboardIdEquals(Long dashboardId) throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE dashboard_id = ? ORDER BY dashboard_id", this,dashboardId);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'dashboard_col_id = :dashboardColId'.
	 */
	@Transactional
	public List<DashboardItem> findWhereDashboardColIdEquals(Long dashboardColId) throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE dashboard_col_id = ? ORDER BY item_order ASC", this,dashboardColId);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'item_order = :itemOrder'.
	 */
	@Transactional
	public List<DashboardItem> findWhereItemOrderEquals(Integer itemOrder) throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE item_order = ? ORDER BY item_order", this,itemOrder);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'item_height = :itemHeight'.
	 */
	@Transactional
	public List<DashboardItem> findWhereItemHeightEquals(Double itemHeight) throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE item_height = ? ORDER BY item_height", this,itemHeight);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'item_collapsed = :itemCollapsed'.
	 */
	@Transactional
	public List<DashboardItem> findWhereItemCollapsedEquals(Short itemCollapsed) throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE item_collapsed = ? ORDER BY item_collapsed", this,itemCollapsed);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'item_closed = :itemClosed'.
	 */
	@Transactional
	public List<DashboardItem> findWhereItemClosedEquals(String itemClosed) throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE item_closed = ? ORDER BY item_closed", this,itemClosed);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'report_design_id = :reportDesignId'.
	 */
	@Transactional
	public List<DashboardItem> findWhereReportDesignIdEquals(Long reportDesignId) throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE report_design_id = ? ORDER BY report_design_id", this,reportDesignId);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'report_chart_id = :reportChartId'.
	 */
	@Transactional
	public List<DashboardItem> findWhereReportChartIdEquals(Long reportChartId) throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE report_chart_id = ? ORDER BY report_chart_id", this,reportChartId);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'is_show_table = :isShowTable'.
	 */
	@Transactional
	public List<DashboardItem> findWhereIsShowTableEquals(Short isShowTable) throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE is_show_table = ? ORDER BY is_show_table", this,isShowTable);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'item_title = :itemTitle'.
	 */
	@Transactional
	public List<DashboardItem> findWhereItemTitleEquals(String itemTitle) throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE item_title = ? ORDER BY item_title", this,itemTitle);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the dashboard_item table that match the criteria 'item_content = :itemContent'.
	 */
	@Transactional
	public List<DashboardItem> findWhereItemContentEquals(String itemContent) throws DashboardItemDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT id, dashboard_id, dashboard_col_id, item_order, item_height, item_collapsed, item_closed, report_design_id, report_chart_id, is_show_table, item_title, item_content FROM " + getTableName() + " WHERE item_content = ? ORDER BY item_content", this,itemContent);
		}
		catch (Exception e) {
			throw new DashboardItemDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the dashboard_item table that matches the specified primary-key value.
	 */
	public DashboardItem findByPrimaryKey(DashboardItemPk pk) throws DashboardItemDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

}
