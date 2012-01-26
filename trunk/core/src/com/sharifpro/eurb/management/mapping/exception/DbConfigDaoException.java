package com.sharifpro.eurb.management.mapping.exception;

public class DbConfigDaoException extends DaoException
{
	private static final long serialVersionUID = -4211413329613215119L;

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
