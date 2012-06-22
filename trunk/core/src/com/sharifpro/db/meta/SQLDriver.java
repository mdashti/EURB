package com.sharifpro.db.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.sharifpro.db.exception.ValidationException;
import com.sharifpro.db.id.IIdentifier;
import com.sharifpro.db.util.beanwrapper.StringWrapper;
import com.sharifpro.util.PropertyProvider;

/**
 * This represents a JDBC driver.
 * This class is a <CODE>JavaBean</CODE>.
 *
 * @author <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 */
@JsonAutoDetect
public class SQLDriver implements ISQLDriver, Cloneable, Serializable
{
    static final long serialVersionUID = 8506401259069527981L;

	private interface IStrings
	{
		String ERR_BLANK_NAME = PropertyProvider.get("SQLDriver.error.blankname");
		String ERR_BLANK_DRIVER = PropertyProvider.get("SQLDriver.error.blankdriver");
		String ERR_BLANK_URL = PropertyProvider.get("SQLDriver.error.blankurl");
	}

	/** The <CODE>IIdentifier</CODE> that uniquely identifies this object. */
	private IIdentifier _id;

	/** The name of this driver. */
	private String _name;

	/**
	 * File name associated with <CODE>_jarFileURL</CODE>.
	 */
	private String _jarFileName = null;

	/** Names for driver jar files. */
	private List<String> _jarFileNamesList = new ArrayList<String>();

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
	public SQLDriver(IIdentifier id)
	{
		_id = id;
		_name = "";
		_jarFileName = null;
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
		setJarFileNames(rhs.getJarFileNames());
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
			rc = ((ISQLDriver) rhs).getIdentifier().equals(getIdentifier());
		}
		return rc;
	}

	/**
	 * Returns a hash code value for this object.
	 */
	public synchronized int hashCode()
	{
		return getIdentifier().hashCode();
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

	@JsonIgnore
	public IIdentifier getIdentifier()
	{
		return _id;
	}

	public void setIdentifier(IIdentifier id)
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
			final String oldValue = _driverClassName;
			_driverClassName = data;
		}
	}

	/**
	 * @deprecated	Replaced by getJarFileNames().
	 */
	@JsonIgnore
	public String getJarFileName()
	{
		return _jarFileName;
	}

	public void setJarFileName(String value)
	{
		if (value == null)
		{
			value = "";
		}
		if (_jarFileName == null || !_jarFileName.equals(value))
		{
			final String oldValue = _jarFileName;
			_jarFileName = value;
		}
	}

	@JsonIgnore
	public synchronized String[] getJarFileNames()
	{
		return _jarFileNamesList.toArray(new String[_jarFileNamesList.size()]);
	}

	public synchronized void setJarFileNames(String[] values)
	{
		String[] oldValue =
            _jarFileNamesList.toArray(new String[_jarFileNamesList.size()]);
		_jarFileNamesList.clear();

		if (values == null)
		{
			values = new String[0];
		}

		for (int i = 0; i < values.length; ++i)
		{
			_jarFileNamesList.add(values[i]);
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
			final String oldValue = _url;
			_url = data;
		}
	}

	public String getName()
	{
		return _name;
	}

	public String getFullName()
	{
		return _name + " - " + getDriverClassName();
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
			final String oldValue = _name;
			_name = data;
		}
	}

	@JsonIgnore
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

	@JsonIgnore
	public synchronized StringWrapper[] getJarFileNameWrappers()
	{
		StringWrapper[] wrappers = new StringWrapper[_jarFileNamesList.size()];
		for (int i = 0; i < wrappers.length; ++i)
		{
			wrappers[i] = new StringWrapper(_jarFileNamesList.get(i));
		}
		return wrappers;
	}

	public StringWrapper getJarFileNameWrapper(int idx)
		throws ArrayIndexOutOfBoundsException
	{
		return new StringWrapper(_jarFileNamesList.get(idx));
	}

	public void setJarFileNameWrappers(StringWrapper[] value)
	{
		_jarFileNamesList.clear();
		if (value != null)
		{
			for (int i = 0; i < value.length; ++i)
			{
				_jarFileNamesList.add(value[i].getString());
			}
		}
	}

	public void setJarFileNameWrapper(int idx, StringWrapper value)
		throws ArrayIndexOutOfBoundsException
	{
		_jarFileNamesList.set(idx, value.getString());
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
            final String oldValue = _websiteUrl;
            _websiteUrl = data;
        }
    }
    
    
}
