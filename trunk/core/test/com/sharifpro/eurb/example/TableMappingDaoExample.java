package com.sharifpro.eurb.example;

import java.util.List;
import com.sharifpro.eurb.dao.TableMappingDao;
import com.sharifpro.eurb.dto.TableMapping;
import com.sharifpro.eurb.factory.DaoFactory;

public class TableMappingDaoExample
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
		// findByPersistableObject(null);
		// findByDbConfig(null);
		// findWhereIdEquals(null);
		// findWhereDbConfigIdEquals(null);
		// findWhereTableNameEquals("");
		// findWhereMappedNameEquals("");
		// findWhereTypeEquals(null);
		// findWhereActiveForManagerEquals(null);
		// findWhereActiveForUserEquals(null);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		TableMappingDao dao = DaoFactory.createTableMappingDao();
		List<TableMapping> _result = dao.findAll();
		for (TableMapping dto : _result) {
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
		TableMappingDao dao = DaoFactory.createTableMappingDao();
		List<TableMapping> _result = dao.findByPersistableObject(id);
		for (TableMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByDbConfig'
	 * 
	 * @param dbConfigId
	 * @throws Exception
	 */
	public static void findByDbConfig(Long dbConfigId) throws Exception
	{
		TableMappingDao dao = DaoFactory.createTableMappingDao();
		List<TableMapping> _result = dao.findByDbConfig(dbConfigId);
		for (TableMapping dto : _result) {
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
		TableMappingDao dao = DaoFactory.createTableMappingDao();
		List<TableMapping> _result = dao.findWhereIdEquals(id);
		for (TableMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereDbConfigIdEquals'
	 * 
	 * @param dbConfigId
	 * @throws Exception
	 */
	public static void findWhereDbConfigIdEquals(Long dbConfigId) throws Exception
	{
		TableMappingDao dao = DaoFactory.createTableMappingDao();
		List<TableMapping> _result = dao.findWhereDbConfigIdEquals(dbConfigId);
		for (TableMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereTableNameEquals'
	 * 
	 * @param tableName
	 * @throws Exception
	 */
	public static void findWhereTableNameEquals(String tableName) throws Exception
	{
		TableMappingDao dao = DaoFactory.createTableMappingDao();
		List<TableMapping> _result = dao.findWhereTableNameEquals(tableName);
		for (TableMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereMappedNameEquals'
	 * 
	 * @param mappedName
	 * @throws Exception
	 */
	public static void findWhereMappedNameEquals(String mappedName) throws Exception
	{
		TableMappingDao dao = DaoFactory.createTableMappingDao();
		List<TableMapping> _result = dao.findWhereMappedNameEquals(mappedName);
		for (TableMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereTypeEquals'
	 * 
	 * @param type
	 * @throws Exception
	 */
	public static void findWhereTypeEquals(Integer type) throws Exception
	{
		TableMappingDao dao = DaoFactory.createTableMappingDao();
		List<TableMapping> _result = dao.findWhereMappedTypeEquals(type);
		for (TableMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereActiveForManagerEquals'
	 * 
	 * @param activeForManager
	 * @throws Exception
	 */
	public static void findWhereActiveForManagerEquals(Short activeForManager) throws Exception
	{
		TableMappingDao dao = DaoFactory.createTableMappingDao();
		List<TableMapping> _result = dao.findWhereActiveForManagerEquals(activeForManager);
		for (TableMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereActiveForUserEquals'
	 * 
	 * @param activeForUser
	 * @throws Exception
	 */
	public static void findWhereActiveForUserEquals(Short activeForUser) throws Exception
	{
		TableMappingDao dao = DaoFactory.createTableMappingDao();
		List<TableMapping> _result = dao.findWhereActiveForUserEquals(activeForUser);
		for (TableMapping dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(TableMapping dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getDbConfigId() );
		buf.append( ", " );
		buf.append( dto.getTableName() );
		buf.append( ", " );
		buf.append( dto.getMappedName() );
		buf.append( ", " );
		buf.append( dto.getMappedType() );
		buf.append( ", " );
		buf.append( dto.isActiveForManager() );
		buf.append( ", " );
		buf.append( dto.isActiveForUser() );
		System.out.println( buf.toString() );
	}

}
