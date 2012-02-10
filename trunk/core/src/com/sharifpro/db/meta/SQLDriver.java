package com.sharifpro.db.meta;

import java.io.Serializable;

import com.sharifpro.db.exception.ValidationException;
import com.sharifpro.util.PropertyProvider;

/**
 * This represents a JDBC driver.
 * This class is a <CODE>JavaBean</CODE>.
 */
public class SQLDriver implements ISQLDriver, Cloneable, Serializable
{
    static final long serialVersionUID = 8506401259069527981L;
    
	/** Internationalized strings for this class. */

	private interface IStrings
	{
		String ERR_BLANK_NAME = PropertyProvider.get("SQLDriver.error.blankname");
		String ERR_BLANK_DRIVER = PropertyProvider.get("SQLDriver.error.blankdriver");
		String ERR_BLANK_URL = PropertyProvider.get("SQLDriver.error.blankurl");
	}

	/** The <CODE>IIdentifier</CODE> that uniquely identifies this object. */
	private long _id;

	/** The name of this driver. */
	private String _name;

	/** The class name of the JDBC driver. */
	private String _driverClassName;

	/** Default URL required to access the database. */
	private String _url;

	/** Is the JDBC driver class for this object loaded? */
	private boolean _jdbcDriverClassLoaded;

    /** Default Website URL for more info about the JDBC driver */
    private String _websiteUrl;
    
	/**
	 * Ctor specifying the identifier.
	 *
	 * @param	id	Uniquely identifies this object.
	 */
	public SQLDriver(long id)
	{
		_id = id;
		_name = "";
		_driverClassName = null;
		_url = "";
        _websiteUrl = "";
	}

	/**
	 * Default ctor.
	 */
	public SQLDriver()
	{
	}

	/**
	 * Assign data from the passed <CODE>ISQLDriver</CODE> to this one. This
	 * does <B>not</B> copy the identifier.
	 *
	 * @param	rhs	 <CODE>ISQLDriver</CODE> to copy data from.
	 *
	 * @exception	ValidationException
	 *				Thrown if an error occurs assigning data from
	 *				<CODE>rhs</CODE>.
	 */
	public synchronized void assignFrom(ISQLDriver rhs)
		throws ValidationException
	{
		setName(rhs.getName());
		setDriverClassName(rhs.getDriverClassName());
		setUrl(rhs.getUrl());
		setJDBCDriverClassLoaded(rhs.isJDBCDriverClassLoaded());
        setWebSiteUrl(rhs.getWebSiteUrl());
	}

	/**
	 * Returns <TT>true</TT> if this objects is equal to the passed one. Two
	 * <TT>ISQLDriver</TT> objects are considered equal if they have the same
	 * identifier.
	 */
	public boolean equals(Object rhs)
	{
		boolean rc = false;
		if (rhs != null && rhs.getClass().equals(getClass()))
		{
			rc = ((ISQLDriver) rhs).getIdentifier() == getIdentifier();
		}
		return rc;
	}

	/**
	 * Returns a hash code value for this object.
	 */
	public synchronized int hashCode()
	{
		return (int) getIdentifier();
	}

	/**
	 * Returns the name of this <TT>ISQLDriver</TT>.
	 */
	public String toString()
	{
		return getName();
	}

	/**
	 * Return a clone of this object.
	 */
	public Object clone()
	{
		try
		{
			final SQLDriver driver = (SQLDriver)super.clone();
			return driver;
		}
		catch (CloneNotSupportedException ex)
		{
			throw new InternalError(ex.getMessage()); // Impossible.
		}
	}

	/**
	 * Compare this <TT>ISQLDriver</TT> to another <TT>ISQLDriver</TT>.  The 
	 * <TT>getName()</TT> functions of the two <TT>ISQLDriver</TT> objects are 
	 * used to compare them. 
	 */
	public int compareTo(ISQLDriver rhs)
	{
		return _name.compareTo(rhs.getName());
	}

	public long getIdentifier()
	{
		return _id;
	}

	public void setIdentifier(long id)
	{
		_id = id;
	}

	public String getDriverClassName()
	{
		return _driverClassName;
	}

	public void setDriverClassName(String driverClassName)
		throws ValidationException
	{
		String data = getString(driverClassName);
		if (data.length() == 0)
		{
			throw new ValidationException(IStrings.ERR_BLANK_DRIVER);
		}
        if (!data.equals(_driverClassName))
		{
			_driverClassName = data;
		}
	}

	public String getUrl()
	{
		return _url;
	}

	public void setUrl(String url) throws ValidationException
	{
		String data = getString(url);
		if (data.length() == 0)
		{
			throw new ValidationException(IStrings.ERR_BLANK_URL);
		}
		if (!data.equals(_url))
		{
			_url = data;
		}
	}

	public String getName()
	{
		return _name;
	}

	public void setName(String name) throws ValidationException
	{
		String data = getString(name);
		if (data.length() == 0)
		{
			throw new ValidationException(IStrings.ERR_BLANK_NAME);
		}
        if (!data.equals(_name))
		{
			_name = data;
		}
	}

	public boolean isJDBCDriverClassLoaded()
	{
		return _jdbcDriverClassLoaded;
	}

	public void setJDBCDriverClassLoaded(boolean cl)
	{
		_jdbcDriverClassLoaded = cl;
		//TODO: Decide whether this should be a bound property or not.
		//		getPropertyChangeReporter().firePropertyChange(ISQLDriver.IPropertyNames.NAME, _name, _name);
	}

	private String getString(String data)
	{
		return data != null ? data.trim() : "";
	}

    /* (non-Javadoc)
     * @see net.sourceforge.squirrel_sql.fw.sql.ISQLDriver#getWebSiteUrl()
     */
    public String getWebSiteUrl() {
        return _websiteUrl;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.squirrel_sql.fw.sql.ISQLDriver#setWebSiteUrl(java.lang.String)
     */
    public void setWebSiteUrl(String url) throws ValidationException { 
        String data = getString(url);
        if (!data.equals(_websiteUrl)) {
            _websiteUrl = data;   
        }
    }
    
    
}
