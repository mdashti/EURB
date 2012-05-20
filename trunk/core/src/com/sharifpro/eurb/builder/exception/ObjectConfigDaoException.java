package com.sharifpro.eurb.builder.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class ObjectConfigDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = 5405082594019799278L;

	/**
	 * Method 'ObjectConfigDaoException'
	 * 
	 * @param message
	 */
	public ObjectConfigDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'ObjectConfigDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public ObjectConfigDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
