package com.sharifpro.eurb.exceptions;

public class TableMappingDaoException extends DaoException
{
	private static final long serialVersionUID = -5291866167453171514L;

	/**
	 * Method 'TableMappingDaoException'
	 * 
	 * @param message
	 */
	public TableMappingDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'TableMappingDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public TableMappingDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
