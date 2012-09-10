package com.sharifpro.eurb.dashboard;

import java.util.List;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.dashboard.dao.DashboardColDao;
import com.sharifpro.eurb.dashboard.model.DashboardCol;

public class DashboardColDaoExample
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
		// findByDashboard(0);
		// findByPersistableObject(0);
		// findWhereIdEquals(0);
		// findWhereDashboardIdEquals(0);
		// findWhereColOrderEquals(0);
		// findWhereColWidthEquals(0);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		DashboardColDao dao = DaoFactory.createDashboardColDao();
		List<DashboardCol> _result = dao.findAll();
		for (DashboardCol dto : _result) {
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
		DashboardColDao dao = DaoFactory.createDashboardColDao();
		List<DashboardCol> _result = dao.findByDashboard(dashboardId);
		for (DashboardCol dto : _result) {
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
		DashboardColDao dao = DaoFactory.createDashboardColDao();
		List<DashboardCol> _result = dao.findByPersistableObject(id);
		for (DashboardCol dto : _result) {
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
		DashboardColDao dao = DaoFactory.createDashboardColDao();
		List<DashboardCol> _result = dao.findWhereIdEquals(id);
		for (DashboardCol dto : _result) {
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
		DashboardColDao dao = DaoFactory.createDashboardColDao();
		List<DashboardCol> _result = dao.findWhereDashboardIdEquals(dashboardId);
		for (DashboardCol dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereColOrderEquals'
	 * 
	 * @param colOrder
	 * @throws Exception
	 */
	public static void findWhereColOrderEquals(int colOrder) throws Exception
	{
		DashboardColDao dao = DaoFactory.createDashboardColDao();
		List<DashboardCol> _result = dao.findWhereColOrderEquals(colOrder);
		for (DashboardCol dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereColWidthEquals'
	 * 
	 * @param colWidth
	 * @throws Exception
	 */
	public static void findWhereColWidthEquals(double colWidth) throws Exception
	{
		DashboardColDao dao = DaoFactory.createDashboardColDao();
		List<DashboardCol> _result = dao.findWhereColWidthEquals(colWidth);
		for (DashboardCol dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(DashboardCol dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getDashboardId() );
		buf.append( ", " );
		buf.append( dto.getColOrder() );
		buf.append( ", " );
		buf.append( dto.getColWidth() );
		System.out.println( buf.toString() );
	}

}
