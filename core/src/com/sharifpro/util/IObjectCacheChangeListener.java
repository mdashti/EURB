package com.sharifpro.util;

import java.util.EventListener;
/**
 * This interface defines a listener to changes in <CODE>ObjectCache</CODE>.
 *
 * @author  <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 */
public interface IObjectCacheChangeListener extends EventListener
{
	/**
	 * An object has been added to the cache.
	 *
	 * @param   evt	 Describes the event.
	 */
	void objectAdded(ObjectCacheChangeEvent evt);

	/**
	 * An object has been removed from the cache.
	 *
	 * @param   evt	 Describes the event.
	 */
	void objectRemoved(ObjectCacheChangeEvent evt);
}
