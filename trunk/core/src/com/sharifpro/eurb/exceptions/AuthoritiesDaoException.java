package com.sharifpro.eurb.exceptions;

public class AuthoritiesDaoException extends DaoException
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
