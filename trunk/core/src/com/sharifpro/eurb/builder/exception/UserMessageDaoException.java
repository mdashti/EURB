package com.sharifpro.eurb.builder.exception;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class UserMessageDaoException extends PersistableObjectDaoException
{
	private static final long serialVersionUID = 6120547439863822456L;

	/**
	 * Method 'UserMessageDaoException'
	 * 
	 * @param message
	 */
	public UserMessageDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'UserMessageDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public UserMessageDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
