package com.sharifpro.eurb.management.mapping.exception;

public class PersistableObjectDaoException extends DaoException
{
	private static final long serialVersionUID = 6654299621451218876L;

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
