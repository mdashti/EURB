package com.sharifpro.db.meta;

import java.io.Serializable;

import com.sharifpro.db.exception.ValidationException;
import com.sharifpro.util.PropertyProvider;

/**
 * This represents a Database alias which is a description of the means required
 * to connect to a JDBC complient database.
 * <P>
 * This class is a <CODE>JavaBean</CODE>.
 */
public class SQLAlias implements Cloneable, Serializable, Comparable<Object> {
	private static final long serialVersionUID = 3732046070358851197L;

	private interface IStrings {
		String ERR_BLANK_NAME = PropertyProvider
				.get("SQLAlias.error.blankname");
		String ERR_BLANK_URL = PropertyProvider.get("SQLAlias.error.blankurl");
	}

	/** The <CODE>IIdentifier</CODE> that uniquely identifies this object. */
	private long _id;

	/** The name of this alias. */
	private String _name;

	/**
	 * The <CODE>IIdentifier</CODE> that identifies the <CODE>ISQLDriver</CODE>
	 * that this <CODE>ISQLAlias</CODE> uses.
	 */
	private long _driverId;

	/** The URL required to access the database. */
	private String _url;

	/** Name of user for connection. */
	private String _userName;

	/** Password of user for connection. */
	private String _password;

	/** <TT>true</TT> if this alias should be logged on automatically. */
	private boolean _autoLogon;

	/** Should this alias be connected when the application is started up. */
	private boolean _connectAtStartup;

	/** If <TT>true</TT> then use drver properties. */
	private boolean _useDriverProperties = false;

	/** Collection of <TT>SQLDriverProperty</TT> objects for this alias. */
	private SQLDriverPropertyCollection _driverProps = new SQLDriverPropertyCollection();

	/**
	 * Default ctor.
	 */
	public SQLAlias() {
	}

	/**
	 * Ctor specifying the identifier.
	 * 
	 * @param id
	 *            Uniquely identifies this object.
	 */
	public SQLAlias(long id) {
		_id = id;
		_name = "";
		_driverId = -1L;
		_url = "";
		_userName = "";
		_password = "";
	}

	/**
	 * Assign data from the passed <CODE>ISQLAlias</CODE> to this one.
	 * 
	 * This Alias becomes a clone of rhs.
	 * 
	 * @param rhs
	 *            <CODE>ISQLAlias</CODE> to copy data from.
	 * 
	 * @param b
	 * @exception ValidationException
	 *                Thrown if an error occurs assigning data from
	 *                <CODE>rhs</CODE>.
	 */
	public synchronized void assignFrom(SQLAlias rhs, boolean withIdentifier)
			throws ValidationException {
		if (withIdentifier) {
			setIdentifier(rhs.getIdentifier());
		}

		setName(rhs.getName());
		setDriverIdentifier(rhs.getDriverIdentifier());
		setUrl(rhs.getUrl());
		setUserName(rhs.getUserName());
		setPassword(rhs.getPassword());
		setAutoLogon(rhs.isAutoLogon());
		setUseDriverProperties(rhs.getUseDriverProperties());
		setDriverProperties(rhs.getDriverPropertiesClone());
	}

	/**
	 * Returns <TT>true</TT> if this objects is equal to the passed one. Two
	 * <TT>ISQLAlias</TT> objects are considered equal if they have the same
	 * identifier.
	 */
	public boolean equals(Object rhs) {
		boolean rc = false;
		if (rhs != null && rhs.getClass().equals(getClass())) {
			rc = ((ISQLAlias) rhs).getIdentifier() == getIdentifier();
		}
		return rc;
	}

	/**
	 * Returns a hash code value for this object.
	 */
	public synchronized int hashCode() {
		return new Long(getIdentifier()).hashCode();
	}

	/**
	 * Returns the name of this <TT>ISQLAlias</TT>.
	 */
	public String toString() {
		return getName();
	}

	/**
	 * Compare this <TT>SQLAlias</TT> to another object. If the passed object is
	 * a <TT>SQLAlias</TT>, then the <TT>getName()</TT> functions of the two
	 * <TT>SQLAlias</TT> objects are used to compare them. Otherwise, it throws
	 * a ClassCastException (as <TT>SQLAlias</TT> objects are comparable only to
	 * other <TT>SQLAlias</TT> objects).
	 */
	public int compareTo(Object rhs) {
		return _name.compareTo(((ISQLAlias) rhs).getName());
	}

	/**
	 * Returns <CODE>true</CODE> if this object is valid.
	 * <P>
	 * Implementation for <CODE>IPersistable</CODE>.
	 */
	public synchronized boolean isValid() {
		return _name != null && _name.length() > 0 && _driverId > 0
				&& _url != null && _url.length() > 0;
	}

	public long getIdentifier() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public long getDriverIdentifier() {
		return _driverId;
	}

	public String getUrl() {
		return _url;
	}

	public String getUserName() {
		return _userName;
	}

	/**
	 * Retrieve the saved password.
	 * 
	 * @return The saved password.
	 */
	public String getPassword() {
		return _password;
	}

	/**
	 * Set the password for this alias.
	 * 
	 * @param password
	 *            The new password.
	 */
	public void setPassword(String password) {
		_password = password;
	}

	/**
	 * Should this alias be logged on automatically.
	 * 
	 * @return <TT>true</TT> is this alias should be logged on automatically
	 *         else <TT>false</TT>.
	 */
	public boolean isAutoLogon() {
		return _autoLogon;
	}

	/**
	 * Set whether this alias should be logged on automatically.
	 * 
	 * @param value
	 *            <TT>true</TT> if alias should be autologged on else
	 *            <TT>false</TT.
	 */
	public void setAutoLogon(boolean value) {
		_autoLogon = value;
	}

	/**
	 * Should this alias be connected when the application is started up.
	 * 
	 * @return <TT>true</TT> if this alias should be connected when the
	 *         application is started up.
	 */
	public boolean isConnectAtStartup() {
		return _connectAtStartup;
	}

	/**
	 * Set whether alias should be connected when the application is started up.
	 * 
	 * @param value
	 *            <TT>true</TT> if alias should be connected when the
	 *            application is started up.
	 */
	public void setConnectAtStartup(boolean value) {
		_connectAtStartup = value;
	}

	/**
	 * Returns whether this alias uses driver properties.
	 */
	public boolean getUseDriverProperties() {
		return _useDriverProperties;
	}

	public void setIdentifier(long id) {
		_id = id;
	}

	public void setName(String name) throws ValidationException {
		if (name.length() == 0) {
			throw new ValidationException(IStrings.ERR_BLANK_NAME);
		}
		_name = name;
	}

	public void setDriverIdentifier(long data) throws ValidationException {
		_driverId = data;
	}

	public void setUrl(String url) throws ValidationException {
		if (url.length() == 0) {
			throw new ValidationException(IStrings.ERR_BLANK_URL);
		}
		_url = url;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public void setUseDriverProperties(boolean value) {
		_useDriverProperties = value;
	}

	/**
	 * Retrieve a copy of the SQL driver properties.
	 * 
	 * @return the SQL driver properties.
	 */
	public synchronized SQLDriverPropertyCollection getDriverPropertiesClone() {
		final int count = _driverProps.size();
		SQLDriverProperty[] newar = new SQLDriverProperty[count];
		for (int i = 0; i < count; ++i) {
			newar[i] = (SQLDriverProperty) _driverProps.getDriverProperty(i)
					.clone();
		}
		SQLDriverPropertyCollection coll = new SQLDriverPropertyCollection();
		coll.setDriverProperties(newar);
		return coll;
	}

	public synchronized void setDriverProperties(
			SQLDriverPropertyCollection value) {
		_driverProps.clear();
		if (value != null) {
			synchronized (value) {
				final int count = value.size();
				SQLDriverProperty[] newar = new SQLDriverProperty[count];
				for (int i = 0; i < count; ++i) {
					newar[i] = (SQLDriverProperty) value.getDriverProperty(i)
							.clone();

				}
				_driverProps.setDriverProperties(newar);
			}
		}
	}

}
