package com.sharifpro.util;

import java.util.Iterator;

import com.sharifpro.db.exception.DuplicateObjectException;
import com.sharifpro.db.id.IHasIdentifier;
import com.sharifpro.db.id.IIdentifier;

/**
 * This interface defines a the behaviour of an object cache.
 *
 * @author  <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 */
public interface IObjectCache<E extends IHasIdentifier>
{
	/**
	 * Retrieve a stored object.
	 *
	 * @param   objClass	The class of the object to be retrieved.
	 * @param   id			The <CODE>IIdentifier</CODE> that identifies
	 *						the object to be retrieved.
	 *
	 * @return  The <CODE>IHasIdentifier</CODE> retrieved or <CODE>null</CODE>
	 *			if no object exists for <CODE>id</CODE>.
	 */
	IHasIdentifier get(Class<E> objClass, IIdentifier id);

	/**
	 * Store an object.
	 *
	 * @param   obj	 Object to be stored.
	 *
	 * @exception   DuplicateObjectException
	 *				Thrown if an object of the same class as <CODE>obj</CODE>
	 *				and with the same identifier is already in the cache.
	 */
	void add(E obj) throws DuplicateObjectException;

	/**
	 * Remove an object.
	 *
	 * @param   objClass	Class of object to be removed.
	 * @param   id			Identifier for object to be removed.
	 */
	void remove(Class<E> objClass, IIdentifier id);

	/**
	 * Return an array of <CODE>Class</CODE objects that represent all the
	 * different types of objects stored.
	 *
	 * @return  Class[] of all classes stored.
	 */
	Class<E>[] getAllClasses();

	/**
	 * Return an <CODE>Iterator</CODE> of all objects stored for the
	 * passed class.
	 *
	 * @param   objClass	Class to return objects for.
	 *
	 * @return  <CODE>Iterator</CODE> over all objects.
	 */
	Iterator<E> getAllForClass(Class<E> objClass);

	/**
	 * Adds a listener for changes to the cache entry for the passed class.
	 *
	 * @param   lis			a IObjectCacheChangeListener that will be notified
	 *						when objects are added or removed from this cache
	 *						entry.
	 * @param   objClass	The class of objects whose cache we want to listen
	 *						to.
	 */
	void addChangesListener(IObjectCacheChangeListener lis, Class<E> objClass);

	/**
	 * Removes a listener for changes to the cache entry for the passed class.
	 *
	 * @param   lis			a IObjectCacheChangeListener that will be notified
	 *						when objects are added or removed from this cache
	 *						entry.
	 * @param   objClass	The class of objects whose cache we want to listen
	 *						to.
	 */
	void removeChangesListener(IObjectCacheChangeListener lis, Class<E> objClass);
}
