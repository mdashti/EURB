package com.sharifpro.eurb.management.security.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class GroupAuthoritiesDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = -5289944845800604809L;

	/**
	 * Method 'GroupAuthoritiesDaoException'
	 * 
	 * @param message
	 */
	public GroupAuthoritiesDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'GroupAuthoritiesDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public GroupAuthoritiesDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
