package com.sharifpro.eurb.exceptions;

public class GroupMembersDaoException extends DaoException
{
	/**
	 * Method 'GroupMembersDaoException'
	 * 
	 * @param message
	 */
	public GroupMembersDaoException(String message)
	{
		super(message);
	}

	/**
	 * Method 'GroupMembersDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public GroupMembersDaoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
