package com.sharifpro.eurb.builder.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class PropertiesDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = 8054234817524982895L;

	/**
	 * Method 'PropertiesDaoException'
	 * 
	 * @param message
	 */
	public PropertiesDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'PropertiesDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public PropertiesDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
