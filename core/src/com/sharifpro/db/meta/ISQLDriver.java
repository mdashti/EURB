package com.sharifpro.db.meta;

import com.sharifpro.db.exception.ValidationException;
import com.sharifpro.db.id.IHasIdentifier;
import com.sharifpro.db.id.IIdentifier;
import com.sharifpro.db.util.beanwrapper.StringWrapper;

public interface ISQLDriver extends IHasIdentifier, Comparable<ISQLDriver>
{
	/**
	 * JavaBean property names for this class.
	 */
	public interface IPropertyNames
	{
		String DRIVER_CLASS = "driverClassName";
		String ID = "identifier";
		String JARFILE_NAME = "jarFileName";
		String JARFILE_NAMES = "jarFileNames";
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

	IIdentifier getIdentifier();

	String getDriverClassName();

	void setDriverClassName(String driverClassName)
		throws ValidationException;

	/**
	 * @deprecated	Replaced by getJarFileURLs().
	 */
	String getJarFileName();

	void setJarFileName(String value) throws ValidationException;

	StringWrapper[] getJarFileNameWrappers();

	StringWrapper getJarFileNameWrapper(int idx) throws ArrayIndexOutOfBoundsException;


	void setJarFileNameWrappers(StringWrapper[] value);

	void setJarFileNameWrapper(int idx, StringWrapper value) throws ArrayIndexOutOfBoundsException;

	String[] getJarFileNames();
	void setJarFileNames(String[] values);

	String getUrl();

	void setUrl(String url) throws ValidationException;

	String getName();

	void setName(String name) throws ValidationException;

	boolean isJDBCDriverClassLoaded();
	void setJDBCDriverClassLoaded(boolean cl);

    String getWebSiteUrl();
    
    void setWebSiteUrl(String url) throws ValidationException;
}
