package com.sharifpro.util.xml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import com.sharifpro.db.exception.DuplicateObjectException;
import com.sharifpro.db.id.IHasIdentifier;
import com.sharifpro.db.id.IIdentifier;
import com.sharifpro.util.IObjectCache;
import com.sharifpro.util.IObjectCacheChangeListener;
import com.sharifpro.util.ObjectCache;
import com.sharifpro.util.PropertyProvider;

/**
 * This class is a cache of objects that can be read from/written to an XML
 * document. All objects stored must implement <CODE>IHasIdentifier</CODE>.<P>
 *
 * It is implemented using <CODE>ObjectCache</CODE>.
 *
 * @author <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 */
public class XMLObjectCache<E extends IHasIdentifier> implements IObjectCache<E>
{
	/** Cache of stored objects. */
	private ObjectCache<E> _cache = new ObjectCache<E>();

	/**
	 * Default ctor.
	 */
	public XMLObjectCache()
	{
		super();
	}

	/**
	 * Retrieve a stored object.
	 *
	 * @param	objClass	The class of the object to be retrieved.
	 * @param	id			The <CODE>IIdentifier</CODE> that identifies
	 *						the object to be retrieved.
	 *
	 * @return	The <CODE>IHasIdentifier</CODE> retrieved or <CODE>null</CODE>
	 *			if no object exists for <CODE>id</CODE>.
	 */
	public IHasIdentifier get(Class<E> objClass, IIdentifier id)
	{
		return _cache.get(objClass, id);
	}

	/**
	 * Store an object.
	 *
	 * @param	obj	Object to be stored.
	 *
	 * @exception	DuplicateObjectException
	 *				Thrown if an object of the same class as <CODE>obj</CODE>
	 *				and with the same identifier is already in the cache.
	 */
	public void add(E obj) throws DuplicateObjectException
	{
		_cache.add(obj);
	}

	/**
	 * Remove an object.
	 *
	 * @param	objClass	Class of object to be removed.
	 * @param	id			Identifier for object to be removed.
	 */
	public void remove(Class<E> objClass, IIdentifier id)
	{
		_cache.remove(objClass, id);
	}

	/**
	 * Return an array of <CODE>Class</CODE objects that represent all the
	 * different types of objects stored.
	 *
	 * @return	Class[] of all classes stored.
	 */
	public Class<E>[] getAllClasses()
	{
		return _cache.getAllClasses();
	}

	/**
	 * Return an <CODE>Iterator</CODE> of all objects stored for the
	 * passed class.
	 *
	 * @param	objClass	Class to return objects for.
	 *
	 * @return	<CODE>Iterator</CODE> over all objects.
	 */
	public Iterator<E> getAllForClass(Class<E> objClass)
	{
		return _cache.getAllForClass(objClass);
	}

	/**
	 * Adds a listener for changes to the cache entry for the passed class.
	 *
	 * @param	lis			an IObjectCacheChangeListener that will be notified
	 *						when objects are added and removed from this cache
	 *						entry.
	 * @param	objClass	The class of objects whose cache we want to listen
	 *						to.
	 */
	public void addChangesListener(IObjectCacheChangeListener lis, Class<E> objClass)
	{
		_cache.addChangesListener(lis, objClass);
	}

	/**
	 * Removes a listener for changes to the cache entry for the passed class.
	 *
	 * @param	lis			an IObjectCacheChangeListener that will be notified
	 *						when objects are added and removed from this cache
	 *						entry.
	 * @param	objClass	The class of objects whose cache we want to listen
	 *						to.
	 */
	public void removeChangesListener(IObjectCacheChangeListener lis,
										Class<E> objClass)
	{
		_cache.removeChangesListener(lis, objClass);
	}

	/**
	 * Load from an XML document.
	 *
	 * @param	xmlFileName	Name of XML file to load from.
	 *
	 * @exception	FileNotFoundException
	 *				Thrown if file not found.
	 *
	 * @exception	XMLException
	 *				Thrown if an XML error occurs.
	 *
	 * @exception	DuplicateObjectException
	 *				Thrown if two objects of the same class
	 *				and with the same identifier are added to the cache.
	 */
	public void load(String xmlFileName)
		throws FileNotFoundException, XMLException, DuplicateObjectException
	{
		load(xmlFileName, null);
	}

	/**
	 * Load from an XML document but don't ignore duplicate objects.
	 *
	 * @param	xmlFileName	Name of XML file to load from.
	 * @param	cl			Class loader to use for object creation.
	 *
	 * @exception	FileNotFoundException
	 *				Thrown if file not found.
	 *
	 * @exception	XMLException
	 *				Thrown if an XML error occurs.
	 *
	 * @exception	DuplicateObjectException
	 *				Thrown if two objects of the same class
	 *				and with the same identifier are added to the cache.
	 */
	public void load(String xmlFileName, ClassLoader cl)
		throws FileNotFoundException, XMLException, DuplicateObjectException
	{
		XMLBeanReader rdr = new XMLBeanReader();
		rdr.load(xmlFileName, cl);
		for (Iterator<Object> it = rdr.iterator(); it.hasNext();)
		{
			final Object obj = it.next();
			if (!(obj instanceof IHasIdentifier))
			{
				throw new XMLException(PropertyProvider.get("XMLObjectCache.error.notimplemented"));
			}
			add((E)obj);
		}
	}

	/**
	 * Load from a reader over an XML document. Use the system classloader and
	 * don't ignore duplicate objects.
	 *
	 * @param	rdr	Reader over the XML document.
	 *
	 * @exception	XMLException
	 *				Thrown if an XML error occurs.
	 *
	 * @exception	DuplicateObjectException
	 *				Thrown if two objects of the same class
	 *				and with the same identifier are added to the cache.
	 */
	public void load(Reader rdr) throws XMLException, DuplicateObjectException
	{
		load(rdr, null, false);
	}

	/**
	 * Load from a reader over an XML document.
	 *
	 * @param	rdr					Reader over the XML document.
	 * @param	cl					Class loader to use for object creation. Pass
	 * 								<TT>null</TT> to use the system classloader.
	 * @param	ignoreDuplicates	If <tt>true</TT> don't throw a
	 * 								<TT>DuplicateObjectException</TT> but rather
	 * 								ignore the attempt to add a duplicate, in
	 *								this case there will be only one object added to
	 *								the cache.
	 *
	 * @exception	XMLException
	 *				Thrown if an XML error occurs.
	 *
	 * @exception	DuplicateObjectException
	 *				Thrown if two objects of the same class
	 *				and with the same identifier are added to the cache.
	 */
	public void load(Reader rdr, ClassLoader cl, boolean ignoreDuplicates)
		throws XMLException, DuplicateObjectException
	{
		XMLBeanReader xmlRdr = new XMLBeanReader();
		xmlRdr.load(rdr, cl);
		for (Iterator<?> it = xmlRdr.iterator(); it.hasNext();)
		{
			final Object obj = it.next();
			if (!(obj instanceof IHasIdentifier))
			{
				throw new XMLException(obj + "\n" + PropertyProvider.get("XMLObjectCache.error.notimplemented"));
			}
			try
			{
				add((E) obj);
			}
			catch (DuplicateObjectException ex)
			{
				if (!ignoreDuplicates)
				{
					throw ex;
				}
			}
		}
	}

	/**
	 * Save all objects in this cache to an XML document.
	 *
	 * @param	xmlFileName	 Name of XML file to save to.
	 *
	 * @exception	IOException
	 *				Thrown if an IO error occurs.
	 *
	 * @exception	XMLException
	 *				Thrown if an XML error occurs.
	 */
	public synchronized void save(String xmlFilename)
		throws IOException, XMLException
	{
		XMLBeanWriter wtr = new XMLBeanWriter();
		Class<E>[] classes = _cache.getAllClasses();
		for (int i = 0; i < classes.length; ++i)
		{
			for (Iterator<E> it = _cache.getAllForClass(classes[i]);
					it.hasNext();)
			{
				wtr.addToRoot(it.next());
			}
		}
		wtr.save(xmlFilename);
	}

	/**
	 * Save all objects of type <CODE>objClass</CODE> to an XML document.
	 *
	 * @param	xmlFileName	 Name of XML file to save to.
	 * @param	forClass		Class of objects to be saved.
	 *
	 * @exception	IOException
	 *				Thrown if an IO error occurs.
	 *
	 * @exception	XMLException
	 *				Thrown if an XML error occurs.
	 */
	public synchronized void saveAllForClass(String xmlFilename,
	                                         Class<E> forClass)
		throws IOException, XMLException
	{
		XMLBeanWriter wtr = new XMLBeanWriter();
		for (Iterator<E> it = _cache.getAllForClass(forClass); it.hasNext();)
		{
			wtr.addToRoot(it.next());
		}
		wtr.save(xmlFilename);
	}
}
