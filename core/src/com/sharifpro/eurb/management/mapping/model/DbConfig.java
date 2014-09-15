package com.sharifpro.eurb.management.mapping.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.sharifpro.db.dialects.HibernateDialect;
import com.sharifpro.db.dialects.MySQL5DialectExt;
import com.sharifpro.db.dialects.MySQLDialectExt;
import com.sharifpro.db.dialects.PostgreSQLDialectExt;
import com.sharifpro.db.dialects.SQLServerDialectExt;
import com.sharifpro.db.exception.ValidationException;
import com.sharifpro.db.id.UidIdentifierFactory;
import com.sharifpro.db.meta.ISQLConnection;
import com.sharifpro.db.meta.ISQLDatabaseMetaData;
import com.sharifpro.db.meta.ISQLDriver;
import com.sharifpro.db.meta.ITableInfo;
import com.sharifpro.db.meta.PrimaryKeyInfo;
import com.sharifpro.db.meta.SQLConnection;
import com.sharifpro.db.meta.SQLDriver;
import com.sharifpro.db.meta.SQLDriverPropertyCollection;
import com.sharifpro.db.meta.TableColumnInfo;
import com.sharifpro.eurb.info.RecordStatus;
import com.sharifpro.util.db.DataSourceFactory;

@JsonAutoDetect
public class DbConfig extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = 4348520185323565243L;
	
	public static final long ACL_CLASS_IDENTIFIER = 3;

	/** 
	 * This attribute maps to the column name in the db_config table.
	 */
	protected String name;

	/** 
	 * This attribute maps to the column driver_class in the db_config table.
	 */
	protected String driverClass;

	/** 
	 * This attribute maps to the column driver_url in the db_config table.
	 */
	protected String driverUrl;

	/** 
	 * This attribute maps to the column username in the db_config table.
	 */
	protected String username;

	/** 
	 * This attribute maps to the column password in the db_config table.
	 */
	protected String password;

	/** 
	 * This attribute maps to the column test_query in the db_config table.
	 */
	protected String testQuery;

	private boolean accessPreventDel = true;
	private boolean accessPreventEdit = true;
	private boolean accessPreventExecute = true;
	private boolean accessPreventSharing = true;

	/** 
	 * This attribute maps to the column record_status in the db_config table.
	 */
	protected RecordStatus recordStatus = RecordStatus.ACTIVE;
	
	protected DataSource dataSource;
	/**
	 * Method 'DbConfig'
	 * 
	 */
	public DbConfig()
	{
		super();
	}

	/**
	 * Method 'getName'
	 * 
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Method 'setName'
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Method 'getDriverClass'
	 * 
	 * @return String
	 */
	public String getDriverClass()
	{
		return driverClass;
	}

	/**
	 * Method 'setDriverClass'
	 * 
	 * @param driverClass
	 */
	public void setDriverClass(String driverClass)
	{
		this.driverClass = driverClass;
	}

	/**
	 * Method 'getDriverUrl'
	 * 
	 * @return String
	 */
	public String getDriverUrl()
	{
		return driverUrl;
	}

	/**
	 * Method 'setDriverUrl'
	 * 
	 * @param driverUrl
	 */
	public void setDriverUrl(String driverUrl)
	{
		this.driverUrl = driverUrl;
	}

	/**
	 * Method 'getUsername'
	 * 
	 * @return String
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Method 'setUsername'
	 * 
	 * @param username
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * Method 'getPassword'
	 * 
	 * @return String
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Method 'setPassword'
	 * 
	 * @param password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Method 'getTestQuery'
	 * 
	 * @return String
	 */
	public String getTestQuery()
	{
		return testQuery;
	}

	/**
	 * Method 'setTestQuery'
	 * 
	 * @param testQuery
	 */
	public void setTestQuery(String testQuery)
	{
		this.testQuery = testQuery;
	}

	/**
	 * Method 'getRecordStatus'
	 * 
	 * @return RecordStatus
	 */
	@JsonIgnore
	public RecordStatus getRecordStatus() {
		return recordStatus;
	}

	/**
	 * Method 'getRecordStatusString'
	 * 
	 * @return String
	 */
	@JsonIgnore
	public String getRecordStatusString() {
		return recordStatus == null ? "A" : recordStatus.getId();
	}

	/**
	 * Method 'setRecordStatus'
	 * 
	 * @param recordStatus
	 */
	public void setRecordStatus(RecordStatus recordStatus) {
		this.recordStatus = recordStatus;
	}
	
	/**
	 * Method 'setRecordStatus'
	 * 
	 * @param recordStatusString
	 */
	public void setRecordStatus(String recordStatusString) {
		this.recordStatus = RecordStatus.get(recordStatusString);
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (!super.equals(_other)) {
			return false;
		}
		
		if (!(_other instanceof DbConfig)) {
			return false;
		}
		
		/*final DbConfig _cast = (DbConfig) _other;
		
		if (name == null ? _cast.name != name : !name.equals( _cast.name )) {
			return false;
		}
		
		if (driverClass == null ? _cast.driverClass != driverClass : !driverClass.equals( _cast.driverClass )) {
			return false;
		}
		
		if (driverUrl == null ? _cast.driverUrl != driverUrl : !driverUrl.equals( _cast.driverUrl )) {
			return false;
		}
		
		if (username == null ? _cast.username != username : !username.equals( _cast.username )) {
			return false;
		}
		
		if (password == null ? _cast.password != password : !password.equals( _cast.password )) {
			return false;
		}
		
		if (testQuery == null ? _cast.testQuery != testQuery : !testQuery.equals( _cast.testQuery )) {
			return false;
		}*/
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = super.hashCode();
		
		/*if (name != null) {
			_hashCode = 29 * _hashCode + name.hashCode();
		}
		
		if (driverClass != null) {
			_hashCode = 29 * _hashCode + driverClass.hashCode();
		}
		
		if (driverUrl != null) {
			_hashCode = 29 * _hashCode + driverUrl.hashCode();
		}
		
		if (username != null) {
			_hashCode = 29 * _hashCode + username.hashCode();
		}
		
		if (password != null) {
			_hashCode = 29 * _hashCode + password.hashCode();
		}
		
		if (testQuery != null) {
			_hashCode = 29 * _hashCode + testQuery.hashCode();
		}*/
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return DbConfigPk
	 */
	public DbConfigPk createPk()
	{
		return new DbConfigPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.DbConfig: " );
		ret.append( super.toString() );
		ret.append( ", name=" + name );
		ret.append( ", driverClass=" + driverClass );
		ret.append( ", driverUrl=" + driverUrl );
		ret.append( ", username=" + username );
		ret.append( ", password=" + password );
		ret.append( ", testQuery=" + testQuery );
		ret.append( ", recordStatus=" + recordStatus.getId() );
		return ret.toString();
	}

	public String getTestCon() {
		if(!isActive()) {
			return "dbconf-inactive";
		}
		if(StringUtils.isEmpty(driverClass) || StringUtils.isEmpty(driverUrl)) {
			return "dbconf-incompletedata";
		}
		if(isValidTestCon()) {
			return "dbconf-valid";
		} else {
			return "dbconf-invalidcon";
		}
	}
	
	@JsonIgnore
	public boolean isValidTestCon(){
		try {
			ISQLConnection con = getConnection();
			con.setReadOnly(true);
			con.close();
			return true;
		} catch (Exception e) {
			Logger.getLogger(DbConfig.class).error("DbConfig.isValidTestCon: Connection did not established for db_config (id=" + getId() + ")");
			return false;
		}
	}
	
	@JsonIgnore
	public boolean isActive(){
		return RecordStatus.ACTIVE.equals(getRecordStatus());
	}

	public void resetDataSource() {
		this.dataSource = null;
	}
	
	@JsonIgnore
	public DataSource getDataSource() {
		if(dataSource == null) {
			Properties props = new Properties();
			props.put("username", username);
			props.put("password", password);
			props.put("driverClassName", driverClass);
			props.put("url", driverUrl/*.replaceAll("&", "&amp;")*/);

			try {
				dataSource = DataSourceFactory.createDataSource(props);
			} catch (Exception e) {
				Logger.getLogger(DbConfig.class).error("DbConfig.getDataSource: could not get DS", e);
				dataSource = null;
			}
		}
		return dataSource;
	}
	
	@JsonIgnore
	public ISQLDriver getDriver() throws ValidationException {
		SQLDriver driver = new SQLDriver();
		driver.setIdentifier(new UidIdentifierFactory().createIdentifier());
		driver.setDriverClassName(getDriverClass());
		driver.setName(getName());
		driver.setUrl(getDriverUrl());
		return driver;
	}
	
	@JsonIgnore
	public ISQLConnection getConnection() throws SQLException {
		try {
			ISQLDriver driver = getDriver();
			Connection conn =  getDataSource().getConnection();
			SQLDriverPropertyCollection connProps = new SQLDriverPropertyCollection();
			SQLConnection sqlConn = new SQLConnection(conn, connProps, driver);
			
			return sqlConn;
		} catch (Exception e) {
			return null;
		}
	}
	
	@JsonIgnore
	public static ISQLDatabaseMetaData getMetaData(ISQLConnection conn) throws SQLException {
		return conn.getSQLMetaData();
	}
	
	@JsonIgnore
	public static String[] getCatalogs(ISQLConnection conn) throws SQLException{
		ISQLDatabaseMetaData metaData = getMetaData(conn);
		String[] catalogs = metaData.getCatalogs();
		return catalogs;
	}
	
	@JsonIgnore
	public static ITableInfo[] getTables(ISQLConnection conn) throws SQLException{
		ISQLDatabaseMetaData metaData = getMetaData(conn);
		ITableInfo[] tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
		return tables;
	}
	
	@JsonIgnore
	public static ITableInfo[] getViews(ISQLConnection conn) throws SQLException{
		ISQLDatabaseMetaData metaData = getMetaData(conn);
		ITableInfo[] views = metaData.getTables(null, null, null, new String[]{"VIEW"});
		return views;
	}
	
	@JsonIgnore
	public static TableColumnInfo[] getColumns(ISQLConnection conn, ITableInfo tableInfo) throws SQLException{
		ISQLDatabaseMetaData metaData = getMetaData(conn);
		TableColumnInfo[] cols = metaData.getColumnInfo(tableInfo);
		return cols;
	}
	
	@JsonIgnore
	public static TableColumnInfo[] getColumns(ISQLConnection conn, TableMapping tbl) throws SQLException{
		ISQLDatabaseMetaData metaData = getMetaData(conn);
		return metaData.getColumnInfo(tbl.getCatalog(), tbl.getSchema(), tbl.getTableName());
	}

	@JsonIgnore
	public static PrimaryKeyInfo[] getPrimaryKeys(ISQLConnection conn, TableMapping tbl) throws SQLException{
		ISQLDatabaseMetaData metaData = getMetaData(conn);
		return metaData.getPrimaryKey(tbl.getCatalog(), tbl.getSchema(), tbl.getTableName());
	}

	public HibernateDialect getDialect() {
		if(getDriverClass().toLowerCase().contains("mysql")) {
			return new MySQLDialectExt();
		} else if(getDriverClass().toLowerCase().contains("mysql5")) {
			return new MySQL5DialectExt();
		} else if(getDriverClass().toLowerCase().contains("postgresql")) {
			return new PostgreSQLDialectExt();
		} else if(getDriverClass().toLowerCase().contains("sqlserver") || getDriverClass().toLowerCase().contains("jtds")) {
			return new SQLServerDialectExt();
		} else {
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String name = "MSSQLServer";
		String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String driverUrl = "jdbc:sqlserver://10.211.55.6;user=sa;password=ROOT;";
		String username = "sa";
		String password = "ROOT";
		//String testQuery;
		//RecordStatus recordStatus = RecordStatus.ACTIVE;
		DataSource dataSource = null;
		
		if(dataSource == null) {
			Properties props = new Properties();
			props.put("username", username);
			props.put("password", password);
			props.put("driverClassName", driverClass);
			props.put("url", driverUrl/*.replaceAll("&", "&amp;")*/);

			dataSource = DataSourceFactory.createDataSource(props);
		}
		
		SQLDriver driver = new SQLDriver();
		driver.setIdentifier(new UidIdentifierFactory().createIdentifier());
		driver.setDriverClassName(driverClass);
		driver.setName(name);
		driver.setUrl(driverUrl);
		
		Connection conn =  dataSource.getConnection();
		SQLDriverPropertyCollection connProps = new SQLDriverPropertyCollection();
		SQLConnection sqlConn = new SQLConnection(conn, connProps, driver);
		
		sqlConn.setReadOnly(true);
		sqlConn.close();
	}

	public boolean isAccessPreventDel() {
		return accessPreventDel;
	}

	public void setAccessPreventDel(boolean accessPreventDel) {
		this.accessPreventDel = accessPreventDel;
	}

	public boolean isAccessPreventEdit() {
		return accessPreventEdit;
	}

	public void setAccessPreventEdit(boolean accessPreventEdit) {
		this.accessPreventEdit = accessPreventEdit;
	}

	public boolean isAccessPreventExecute() {
		return accessPreventExecute;
	}

	public void setAccessPreventExecute(boolean accessPreventExecute) {
		this.accessPreventExecute = accessPreventExecute;
	}

	public boolean isAccessPreventSharing() {
		return accessPreventSharing;
	}

	public void setAccessPreventSharing(boolean accessPreventSharing) {
		this.accessPreventSharing = accessPreventSharing;
	}
}
