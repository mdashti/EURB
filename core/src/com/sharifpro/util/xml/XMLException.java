package com.sharifpro.util.xml;

import com.sharifpro.db.exception.BaseException;

/**
 * This exception indicates that a problem has occured in XML processing.
 *
 * @author <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 */
public class XMLException extends BaseException
{
	/*
	 * Ctor.
	 *
	 * @param	msg	 Message describing the error.
	 */
	public XMLException(String msg)
	{
		super(msg);
	}

	/*
	 * Ctor. Wraps this exception around another.
	 *
	 * @param	wrapee  The exception that this one is wrapped around.
	 */
	public XMLException(Exception wrapee)
	{
		super(wrapee);
	}
}
