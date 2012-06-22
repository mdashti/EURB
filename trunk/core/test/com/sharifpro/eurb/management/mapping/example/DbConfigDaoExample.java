package com.sharifpro.eurb.management.mapping.example;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.sharifpro.db.meta.ISQLConnection;
import com.sharifpro.db.meta.ITableInfo;
import com.sharifpro.db.meta.TableColumnInfo;
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
		TableColumnInfo[] colsInfo;
		ISQLConnection conn = null;
		for (DbConfig dto : _result) {
			System.out.println("===================================================");
			if(dto.isActive() && dto.isValidTestCon()) {
				try {
					conn = dto.getConnection();
					System.out.println("Catalogs = " + Arrays.toString(DbConfig.getCatalogs(conn)));
					ITableInfo[] tables = DbConfig.getTables(conn);
					System.out.println("Tables = " + Arrays.toString(tables));
					for(ITableInfo tbl : tables) {
						System.out.println("---------------------------------");
						System.out.println("Catalog: " + tbl.getCatalogName());
						System.out.println("Remarks: " + tbl.getRemarks());
						System.out.println("Schema: " + tbl.getSchemaName());
						System.out.println("Simple Name: " + tbl.getSimpleName());
						System.out.println("Type: " + tbl.getType());
						System.out.println("Child Tables: " + tbl.getChildTables());
						System.out.println("Database Object Type: " + tbl.getDatabaseObjectType());
						colsInfo = DbConfig.getColumns(conn,tbl);
						System.out.println("Cols = " + Arrays.toString(colsInfo));
						for(TableColumnInfo col : colsInfo) {
							System.out.println("++++++++++++++++++++++++++++++");
							System.out.println("\tCatalog: " + col.getCatalogName());
							System.out.println("\tColumn Name: " + col.getColumnName());
							System.out.println("\tColumn Size: " + col.getColumnSize());
							System.out.println("\tData Type: " + col.getDataType());
							System.out.println("\tDecimal Digits: " + col.getDecimalDigits());
							System.out.println("\tDefault Value: " + col.getDefaultValue());
							System.out.println("\tOctet Length: " + col.getOctetLength());
							System.out.println("\tOrdinal Position: " + col.getOrdinalPosition());
							System.out.println("\tRadix: " + col.getRadix());
							System.out.println("\tRemarks: " + col.getRemarks());
							System.out.println("\tSchema Name: " + col.getSchemaName());
							System.out.println("\tSimple Name: " + col.getSimpleName());
							System.out.println("\tTable Name: " + col.getTableName());
							System.out.println("\tType Name: " + col.getTypeName());
							System.out.println("\tDatabase Object Type: " + col.getDatabaseObjectType());
							//System.out.println("++++++++++++++++++++++++++++++");
						}
						System.out.println("---------------------------------");
					}
					/*ITableInfo[] views = dto.getViews(conn);
					System.out.println("Views = " + Arrays.toString(views));
					for(ITableInfo tbl : views) {
						System.out.println("---------------------------------");
						System.out.println("Catalog: " + tbl.getCatalogName());
						System.out.println("Remarks: " + tbl.getRemarks());
						System.out.println("Schema: " + tbl.getSchemaName());
						System.out.println("Simple Name: " + tbl.getSimpleName());
						System.out.println("Type: " + tbl.getType());
						System.out.println("Child Tables: " + tbl.getChildTables());
						System.out.println("Database Object Type: " + tbl.getDatabaseObjectType());
						System.out.println("---------------------------------");
					}
					
					DatabaseMetaData md = dto.getDataSource().getConnection().getMetaData();
					ResultSet tabResult = md.getTables(null, null, null, new String[]{"TABLE"});
					while (tabResult != null && tabResult.next())
					{
						List<String> tblList =
							Arrays.asList(tabResult.getString(1), tabResult.getString(2), tabResult.getString(3),
								tabResult.getString(4), tabResult.getString(5));
						System.out.println(tblList);
						
					}*/
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if(conn != null) {
							conn.close();
						}
					} catch (SQLException e) {
					}
				}
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
