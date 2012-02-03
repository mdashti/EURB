package com.sharifpro.eurb.management.mapping.example;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.exception.DbConfigDaoException;
import com.sharifpro.eurb.management.mapping.model.DbConfig;

public class DbConfigDaoExample
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
		
		findMetaDataForFirstActive();
		// findAll();
		// findByPersistableObject(null);
		// findWhereIdEquals(null);
		// findWhereNameEquals("");
		// findWhereDriverClassEquals("");
		// findWhereDriverUrlEquals("");
		// findWhereUsernameEquals("");
		// findWherePasswordEquals("");
		// findWhereTestQueryEquals("");
	}
	
	public static void findMetaDataForFirstActive() throws DbConfigDaoException {
		DbConfigDao dao = DaoFactory.createDbConfigDao();
		List<DbConfig> _result = dao.findAllActive();
		for (DbConfig dto : _result) {
			if(dto.isActive() && dto.isValidTestCon()) {
				try {
					System.out.println("Catalogs = " + dto.getCatalogs());
					System.out.println("Tables = " + dto.getTables());
					System.out.println("Views = " + dto.getViews());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		DbConfigDao dao = DaoFactory.createDbConfigDao();
		List<DbConfig> _result = dao.findAll();
		for (DbConfig dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByPersistableObject'
	 * 
	 * @param id
	 * @throws Exception
	 */
	public static void findByPersistableObject(Long id) throws Exception
	{
		DbConfigDao dao = DaoFactory.createDbConfigDao();
		List<DbConfig> _result = dao.findByPersistableObject(id);
		for (DbConfig dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIdEquals'
	 * 
	 * @param id
	 * @throws Exception
	 */
	public static void findWhereIdEquals(Long id) throws Exception
	{
		DbConfigDao dao = DaoFactory.createDbConfigDao();
		List<DbConfig> _result = dao.findWhereIdEquals(id);
		for (DbConfig dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereNameEquals'
	 * 
	 * @param name
	 * @throws Exception
	 */
	public static void findWhereNameEquals(String name) throws Exception
	{
		DbConfigDao dao = DaoFactory.createDbConfigDao();
		List<DbConfig> _result = dao.findWhereNameEquals(name);
		for (DbConfig dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDriverClassEquals'
	 * 
	 * @param driverClass
	 * @throws Exception
	 */
	public static void findWhereDriverClassEquals(String driverClass) throws Exception
	{
		DbConfigDao dao = DaoFactory.createDbConfigDao();
		List<DbConfig> _result = dao.findWhereDriverClassEquals(driverClass);
		for (DbConfig dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDriverUrlEquals'
	 * 
	 * @param driverUrl
	 * @throws Exception
	 */
	public static void findWhereDriverUrlEquals(String driverUrl) throws Exception
	{
		DbConfigDao dao = DaoFactory.createDbConfigDao();
		List<DbConfig> _result = dao.findWhereDriverUrlEquals(driverUrl);
		for (DbConfig dto : _result) {
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
		DbConfigDao dao = DaoFactory.createDbConfigDao();
		List<DbConfig> _result = dao.findWhereUsernameEquals(username);
		for (DbConfig dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWherePasswordEquals'
	 * 
	 * @param password
	 * @throws Exception
	 */
	public static void findWherePasswordEquals(String password) throws Exception
	{
		DbConfigDao dao = DaoFactory.createDbConfigDao();
		List<DbConfig> _result = dao.findWherePasswordEquals(password);
		for (DbConfig dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereTestQueryEquals'
	 * 
	 * @param testQuery
	 * @throws Exception
	 */
	public static void findWhereTestQueryEquals(String testQuery) throws Exception
	{
		DbConfigDao dao = DaoFactory.createDbConfigDao();
		List<DbConfig> _result = dao.findWhereTestQueryEquals(testQuery);
		for (DbConfig dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(DbConfig dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getName() );
		buf.append( ", " );
		buf.append( dto.getDriverClass() );
		buf.append( ", " );
		buf.append( dto.getDriverUrl() );
		buf.append( ", " );
		buf.append( dto.getUsername() );
		buf.append( ", " );
		buf.append( dto.getPassword() );
		buf.append( ", " );
		buf.append( dto.getTestQuery() );
		System.out.println( buf.toString() );
	}

}
