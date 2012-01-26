package com.sharifpro.eurb.management.security.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class UserDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = -7970981304156363006L;

	/**
	 * Method 'UserDaoException'
	 * 
	 * @param message
	 */
	public UserDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'UserDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public UserDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
