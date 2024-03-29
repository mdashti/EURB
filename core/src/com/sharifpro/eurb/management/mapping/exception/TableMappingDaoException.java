package com.sharifpro.eurb.management.mapping.exception;

public class TableMappingDaoException extends PersistableObjectDaoException
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
