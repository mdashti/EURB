package com.sharifpro.util;

import java.util.EventObject;

import com.sharifpro.db.id.IHasIdentifier;

/**
 * This class is an event fired whenever an object is added to or removed from
 * an <CODE>ObjectCache</CODE>.
 *
 * @author  <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 */
public class ObjectCacheChangeEvent<E extends IHasIdentifier> extends EventObject
{
	/** The <CODE>ObjectCache</CODE> that object was added to/removed from. */
	private ObjectCache<E> _cache;

	/** The object added/removed. */
	private IHasIdentifier _obj;

	/**
	 * Ctor.
	 *
	 * @param   source	The <CODE>ObjectCache</CODE> that change has happened to.
	 * @param   obj		The object added/removed.
	 */
	ObjectCacheChangeEvent(ObjectCache<E> source, IHasIdentifier obj)
	{
		super(source);
		_cache = source;
		_obj = obj;
	}

	/**
	 * Return the object added/removed.
	 */
	public IHasIdentifier getObject()
	{
		return _obj;
	}

	/**
	 * Return the <CODE>ObjectCache</CODE>.
	 */
	public ObjectCache<E> getObjectCache()
	{
		return _cache;
	}
}