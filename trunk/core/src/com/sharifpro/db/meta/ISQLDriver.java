package com.sharifpro.db.meta;

import com.sharifpro.db.exception.ValidationException;

public interface ISQLDriver extends Comparable<ISQLDriver>
{
	/**
	 * JavaBean property names for this class.
	 */
	public interface IPropertyNames
	{
		String DRIVER_CLASS = "driverClassName";
		String ID = "identifier";
		String NAME = "name";
		String URL = "url";
        String WEBSITE_URL = "websiteUrl";
	}

	/**
	 * Assign data from the passed <CODE>ISQLDriver</CODE> to this one. This
	 * does <B>not</B> copy the identifier.
	 *
	 * @param	rhs	<CODE>ISQLDriver</CODE> to copy data from.
	 *
	 * @exception	ValidationException
	 *				Thrown if an error occurs assigning data from
	 *				<CODE>rhs</CODE>.
	 */
	void assignFrom(ISQLDriver rhs) throws ValidationException;

	/**
	 * Compare this <TT>ISQLDriver</TT> to another object. If the passed object
	 * is a <TT>ISQLDriver</TT>, then the <TT>getName()</TT> functions of the two
	 * <TT>ISQLDriver</TT> objects are used to compare them. Otherwise, it throws a
	 * ClassCastException (as <TT>ISQLDriver</TT> objects are comparable only to
	 * other <TT>ISQLDriver</TT> objects).
	 */
	int compareTo(ISQLDriver rhs);

	long getIdentifier();

	String getDriverClassName();

	void setDriverClassName(String driverClassName)
		throws ValidationException;

	String getUrl();

	void setUrl(String url) throws ValidationException;

	String getName();

	void setName(String name) throws ValidationException;

	boolean isJDBCDriverClassLoaded();
	void setJDBCDriverClassLoaded(boolean cl);
    
    String getWebSiteUrl();
    
    void setWebSiteUrl(String url) throws ValidationException;
}
