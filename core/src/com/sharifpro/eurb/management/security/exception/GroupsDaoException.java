package com.sharifpro.eurb.management.security.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class GroupsDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = 5662180415769138355L;

	/**
	 * Method 'GroupsDaoException'
	 * 
	 * @param message
	 */
	public GroupsDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'GroupsDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public GroupsDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
