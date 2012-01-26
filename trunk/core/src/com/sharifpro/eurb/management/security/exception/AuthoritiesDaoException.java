package com.sharifpro.eurb.management.security.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class AuthoritiesDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = -3077776886629406123L;

	/**
	 * Method 'AuthoritiesDaoException'
	 * 
	 * @param message
	 */
	public AuthoritiesDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'AuthoritiesDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public AuthoritiesDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
