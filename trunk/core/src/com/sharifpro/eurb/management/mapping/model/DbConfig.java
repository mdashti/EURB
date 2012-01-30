package com.sharifpro.eurb.management.mapping.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.sharifpro.eurb.info.RecordStatus;

@JsonAutoDetect
public class DbConfig extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = 4348520185323565243L;

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

	/** 
	 * This attribute maps to the column record_status in the db_config table.
	 */
	protected RecordStatus recordStatus = RecordStatus.ACTIVE;
	
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
	public RecordStatus getRecordStatus() {
		return recordStatus;
	}

	/**
	 * Method 'getRecordStatusString'
	 * 
	 * @return String
	 */
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

}
