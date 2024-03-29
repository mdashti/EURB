package com.sharifpro.db.exception;

import com.sharifpro.db.id.IHasIdentifier;
import com.sharifpro.util.PropertyProvider;

/**
 * This exception is thrown if an attempt is made to add an object
 * to a <CODE>IObjectCache</CODE> and an object for the same class and with the
 * same ID is already in the cache.
 *
 * @author <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 */
public class DuplicateObjectException extends BaseException
{
	/** Object that couldn't be added to the cache. */
	private IHasIdentifier _obj;

	/**
	 * Ctor.
	 *
	 * @param	obj	 The object that we tried to add into the cache.
	 */
	public DuplicateObjectException(IHasIdentifier obj)
	{
		super(generateMessage(obj));
	}

	/**
	 * Return the object that couldn't be added to the cache.
	 */
	public IHasIdentifier getObject()
	{
		return _obj;
	}

	/**
	 * Generate error message. Help function for ctor.
	 */
	private static String generateMessage(IHasIdentifier obj)
	{
		final Object[] args =
		{
			obj.getClass().getName(), obj.getIdentifier().toString()
		};
		return PropertyProvider.get("DuplicateObjectException.msg", args);
	}
}
