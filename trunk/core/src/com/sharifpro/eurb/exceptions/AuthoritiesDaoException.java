package com.sharifpro.eurb.exceptions;

public class AuthoritiesDaoException extends DaoException
{
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
