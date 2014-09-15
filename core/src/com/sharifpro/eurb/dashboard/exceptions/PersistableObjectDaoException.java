package com.sharifpro.eurb.dashboard.exceptions;

public class PersistableObjectDaoException extends DaoException
{
	/**
	 * Method 'PersistableObjectDaoException'
	 * 
	 * @param message
	 */
	public PersistableObjectDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'PersistableObjectDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public PersistableObjectDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
