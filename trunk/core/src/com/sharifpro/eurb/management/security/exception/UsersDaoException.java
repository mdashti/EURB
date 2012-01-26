package com.sharifpro.eurb.management.security.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class UsersDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = -7970981304156363006L;

	/**
	 * Method 'UsersDaoException'
	 * 
	 * @param message
	 */
	public UsersDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'UsersDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public UsersDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
