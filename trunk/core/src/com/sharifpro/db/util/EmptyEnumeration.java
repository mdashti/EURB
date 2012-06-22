package com.sharifpro.db.util;

import java.util.Enumeration;
import java.util.NoSuchElementException;
/**
 * This represents an enumeration that is over an empty container.
 *
 * @author  <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 */
public class EmptyEnumeration<E> implements Enumeration<E>
{
	/**
	 * Returns <CODE>false</CODE> as container is empty.
	 */
	public boolean hasMoreElements()
	{
		return false;
	}

	/**
	 * Throws <CODE>NoSuchElementException</CODE> as container is empty.
	 */
	public E nextElement()
	{
		throw new NoSuchElementException();
	}
}
