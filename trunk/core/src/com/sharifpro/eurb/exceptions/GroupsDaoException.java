package com.sharifpro.eurb.exceptions;

public class GroupsDaoException extends DaoException
{
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
