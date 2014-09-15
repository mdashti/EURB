package com.sharifpro.db.meta;

import com.sharifpro.db.exception.ValidationException;

/**
 * This represents a Database alias which is a description of the means
 * required to connect to a JDBC complient database.
 */
public interface ISQLAlias
{
	/**
	 * JavaBean property names for this class.
	 */
	public interface IPropertyNames
	{
		String AUTO_LOGON = "autoLogon";
		String CONNECT_AT_STARTUP = "connectAtStartup";
		String DRIVER = "driverIdentifier";
		String DRIVER_PROPERTIES = "driverProperties";
		String ID = "identifier";
		String NAME = "name";
		String PASSWORD = "password";
		String URL = "url";
		String USE_DRIVER_PROPERTIES = "useDriverProperties";
		String USER_NAME = "userName";
		String SCHEMA_PROPERTIES = "schemaProperties";
		String COLOR_PROPERTIES = "colorProperties";
		String CONNECTION_PROPERTIES = "connectionProperties";
	}

	/**
	 * Compare this <TT>ISQLAlias</TT> to another object. If the passed object
	 * is a <TT>ISQLAlias</TT>, then the <TT>getName()</TT> functions of the two
	 * <TT>ISQLAlias</TT> objects are used to compare them. Otherwise, it throws
	 * a ClassCastException (as <TT>ISQLAlias</TT> objects are comparable only
	 * to other <TT>ISQLAlias</TT> objects).
	 */
	int compareTo(Object rhs);
	
	long getIdentifier();

	String getName();
	void setName(String name) throws ValidationException;

	long getDriverIdentifier();
	void setDriverIdentifier(long data) throws ValidationException;

	String getUrl();
	void setUrl(String url) throws ValidationException;

	String getUserName();
	void setUserName(String userName) throws ValidationException;

	/**
	 * Retrieve the saved password.
	 *
	 * @return	The saved password.
	 */
	String getPassword();

	/**
	 * Set the password for this alias.
	 *
	 * @param	password	The new password.
	 *
	 * @throws	ValidationException
	 * 			TODO: What conditions?
	 */
	void setPassword(String password) throws ValidationException;

	/**
	 * Should this alias be logged on automatically.
	 *
	 * @return	<TT>true</TT> if this alias should be logged on automatically
	 * 			else <TT>false</TT>.
	 */
	boolean isAutoLogon();

	/**
	 * Set whether this alias should be logged on automatically.
	 *
	 * @param	value	<TT>true</TT> if alias should be autologged on
	 * 					else <TT>false</TT>.
	 */
	void setAutoLogon(boolean value);

	/**
	 * Should this alias be connected when the application is started up.
	 *
	 * @return	<TT>true</TT> if this alias should be connected when the
	 *			application is started up.
	 */
	boolean isConnectAtStartup();

	/**
	 * Set whether alias should be connected when the application is started up.
	 *
	 * @param	value	<TT>true</TT> if alias should be connected when the
	 *					application is started up.
	 */
	void setConnectAtStartup(boolean value);

	boolean getUseDriverProperties();
	void setUseDriverProperties(boolean value);

	SQLDriverPropertyCollection getDriverPropertiesClone();
	void setDriverProperties(SQLDriverPropertyCollection value);
}

