package com.sharifpro.eurb.dashboard;

import java.util.List;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.dashboard.dao.DashboardItemDao;
import com.sharifpro.eurb.dashboard.model.DashboardItem;

public class DashboardItemDaoExample
{
	/**
	 * Method 'main'
	 * 
	 * @param arg
	 * @throws Exception
	 */
	public static void main(String[] arg) throws Exception
	{
		// Uncomment one of the lines below to test the generated code
		
		// findAll();
		// findByPersistableObject(0);
		// findByDashboardCol(0);
		// findByDashboard(0);
		// findWhereIdEquals(0);
		// findWhereDashboardIdEquals(0);
		// findWhereDashboardColIdEquals(0);
		// findWhereItemOrderEquals(0);
		// findWhereItemHeightEquals(0);
		// findWhereItemCollapsedEquals(0);
		// findWhereItemClosedEquals("");
		// findWhereReportDesignIdEquals(0);
		// findWhereReportChartIdEquals(0);
		// findWhereIsShowTableEquals(0);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		DashboardItemDao dao = DaoFactory.createDashboardItemDao();
		List<DashboardItem> _result = dao.findAll();
		for (DashboardItem dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByPersistableObject'
	 * 
	 * @param id
	 * @throws Exception
	 */
	public static void findByPersistableObject(long id) throws Exception
	{
		DashboardItemDao dao = DaoFactory.createDashboardItemDao();
		List<DashboardItem> _result = dao.findByPersistableObject(id);
		for (DashboardItem dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByDashboardCol'
	 * 
	 * @param dashboardColId
	 * @throws Exception
	 */
	public static void findByDashboardCol(long dashboardColId) throws Exception
	{
		DashboardItemDao dao = DaoFactory.createDashboardItemDao();
		List<DashboardItem> _result = dao.findByDashboardCol(dashboardColId);
		for (DashboardItem dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByDashboard'
	 * 
	 * @param dashboardId
	 * @throws Exception
	 */
	public static void findByDashboard(long dashboardId) throws Exception
	{
		DashboardItemDao dao = DaoFactory.createDashboardItemDao();
		List<DashboardItem> _result = dao.findByDashboard(dashboardId);
		for (DashboardItem dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIdEquals'
	 * 
	 * @param id
	 * @throws Exception
	 */
	public static void findWhereIdEquals(long id) throws Exception
	{
		DashboardItemDao dao = DaoFactory.createDashboardItemDao();
		List<DashboardItem> _result = dao.findWhereIdEquals(id);
		for (DashboardItem dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDashboardIdEquals'
	 * 
	 * @param dashboardId
	 * @throws Exception
	 */
	public static void findWhereDashboardIdEquals(long dashboardId) throws Exception
	{
		DashboardItemDao dao = DaoFactory.createDashboardItemDao();
		List<DashboardItem> _result = dao.findWhereDashboardIdEquals(dashboardId);
		for (DashboardItem dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDashboardColIdEquals'
	 * 
	 * @param dashboardColId
	 * @throws Exception
	 */
	public static void findWhereDashboardColIdEquals(long dashboardColId) throws Exception
	{
		DashboardItemDao dao = DaoFactory.createDashboardItemDao();
		List<DashboardItem> _result = dao.findWhereDashboardColIdEquals(dashboardColId);
		for (DashboardItem dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereItemOrderEquals'
	 * 
	 * @param itemOrder
	 * @throws Exception
	 */
	public static void findWhereItemOrderEquals(int itemOrder) throws Exception
	{
		DashboardItemDao dao = DaoFactory.createDashboardItemDao();
		List<DashboardItem> _result = dao.findWhereItemOrderEquals(itemOrder);
		for (DashboardItem dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereItemHeightEquals'
	 * 
	 * @param itemHeight
	 * @throws Exception
	 */
	public static void findWhereItemHeightEquals(double itemHeight) throws Exception
	{
		DashboardItemDao dao = DaoFactory.createDashboardItemDao();
		List<DashboardItem> _result = dao.findWhereItemHeightEquals(itemHeight);
		for (DashboardItem dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereItemCollapsedEquals'
	 * 
	 * @param itemCollapsed
	 * @throws Exception
	 */
	public static void findWhereItemCollapsedEquals(short itemCollapsed) throws Exception
	{
		DashboardItemDao dao = DaoFactory.createDashboardItemDao();
		List<DashboardItem> _result = dao.findWhereItemCollapsedEquals(itemCollapsed);
		for (DashboardItem dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereItemClosedEquals'
	 * 
	 * @param itemClosed
	 * @throws Exception
	 */
	public static void findWhereItemClosedEquals(String itemClosed) throws Exception
	{
		DashboardItemDao dao = DaoFactory.createDashboardItemDao();
		List<DashboardItem> _result = dao.findWhereItemClosedEquals(itemClosed);
		for (DashboardItem dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereReportDesignIdEquals'
	 * 
	 * @param reportDesignId
	 * @throws Exception
	 */
	public static void findWhereReportDesignIdEquals(long reportDesignId) throws Exception
	{
		DashboardItemDao dao = DaoFactory.createDashboardItemDao();
		List<DashboardItem> _result = dao.findWhereReportDesignIdEquals(reportDesignId);
		for (DashboardItem dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereReportChartIdEquals'
	 * 
	 * @param reportChartId
	 * @throws Exception
	 */
	public static void findWhereReportChartIdEquals(long reportChartId) throws Exception
	{
		DashboardItemDao dao = DaoFactory.createDashboardItemDao();
		List<DashboardItem> _result = dao.findWhereReportChartIdEquals(reportChartId);
		for (DashboardItem dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIsShowTableEquals'
	 * 
	 * @param isShowTable
	 * @throws Exception
	 */
	public static void findWhereIsShowTableEquals(short isShowTable) throws Exception
	{
		DashboardItemDao dao = DaoFactory.createDashboardItemDao();
		List<DashboardItem> _result = dao.findWhereIsShowTableEquals(isShowTable);
		for (DashboardItem dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(DashboardItem dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getDashboardId() );
		buf.append( ", " );
		buf.append( dto.getDashboardColId() );
		buf.append( ", " );
		buf.append( dto.getItemOrder() );
		buf.append( ", " );
		buf.append( dto.getItemHeight() );
		buf.append( ", " );
		buf.append( dto.getItemCollapsed() );
		buf.append( ", " );
		buf.append( dto.getItemClosed() );
		buf.append( ", " );
		buf.append( dto.getReportDesignId() );
		buf.append( ", " );
		buf.append( dto.getReportChartId() );
		buf.append( ", " );
		buf.append( dto.getIsShowTable() );
		System.out.println( buf.toString() );
	}

}
