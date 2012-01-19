package com.sharifpro.eurb.exceptions;

public class DbConfigDaoException extends DaoException
{
	/**
	 * Method 'DbConfigDaoException'
	 * 
	 * @param message
	 */
	public DbConfigDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'DbConfigDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public DbConfigDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
