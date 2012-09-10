package com.sharifpro.eurb.dashboard;

import java.util.List;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.dashboard.dao.DashboardDao;
import com.sharifpro.eurb.dashboard.model.Dashboard;

public class DashboardDaoExample
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
		// findByDashboard(0);
		// findWhereIdEquals(0);
		// findWhereIsDefaultEquals(0);
		// findWhereTitleEquals("");
		// findWhereParentDashboardEquals(0);
		// findWhereUsernameEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		DashboardDao dao = DaoFactory.createDashboardDao();
		List<Dashboard> _result = dao.findAll();
		for (Dashboard dto : _result) {
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
		DashboardDao dao = DaoFactory.createDashboardDao();
		List<Dashboard> _result = dao.findByPersistableObject(id);
		for (Dashboard dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByDashboard'
	 * 
	 * @param parentDashboard
	 * @throws Exception
	 */
	public static void findByDashboard(long parentDashboard) throws Exception
	{
		DashboardDao dao = DaoFactory.createDashboardDao();
		List<Dashboard> _result = dao.findByDashboard(parentDashboard);
		for (Dashboard dto : _result) {
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
		DashboardDao dao = DaoFactory.createDashboardDao();
		List<Dashboard> _result = dao.findWhereIdEquals(id);
		for (Dashboard dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIsDefaultEquals'
	 * 
	 * @param isDefault
	 * @throws Exception
	 */
	public static void findWhereIsDefaultEquals(short isDefault) throws Exception
	{
		DashboardDao dao = DaoFactory.createDashboardDao();
		List<Dashboard> _result = dao.findWhereIsDefaultEquals(isDefault);
		for (Dashboard dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereTitleEquals'
	 * 
	 * @param title
	 * @throws Exception
	 */
	public static void findWhereTitleEquals(String title) throws Exception
	{
		DashboardDao dao = DaoFactory.createDashboardDao();
		List<Dashboard> _result = dao.findWhereTitleEquals(title);
		for (Dashboard dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereParentDashboardEquals'
	 * 
	 * @param parentDashboard
	 * @throws Exception
	 */
	public static void findWhereParentDashboardEquals(long parentDashboard) throws Exception
	{
		DashboardDao dao = DaoFactory.createDashboardDao();
		List<Dashboard> _result = dao.findWhereParentDashboardEquals(parentDashboard);
		for (Dashboard dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereUsernameEquals'
	 * 
	 * @param username
	 * @throws Exception
	 */
	public static void findWhereUsernameEquals(String username) throws Exception
	{
		DashboardDao dao = DaoFactory.createDashboardDao();
		List<Dashboard> _result = dao.findWhereUsernameEquals(username);
		for (Dashboard dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(Dashboard dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getIsDefault() );
		buf.append( ", " );
		buf.append( dto.getTitle() );
		buf.append( ", " );
		buf.append( dto.getParentDashboard() );
		buf.append( ", " );
		buf.append( dto.getUsername() );
		System.out.println( buf.toString() );
	}

}
