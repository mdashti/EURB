package com.sharifpro.eurb.builder.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObjectPk;

/** 
 * This class represents the primary key of the user_message table.
 */
public class UserMessagePk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = 2879335742727431281L;

	/**
	 * Method 'UserMessagePk'
	 * 
	 */
	public UserMessagePk()
	{
		super();
	}

	/**
	 * Method 'UserMessagePk'
	 * 
	 * @param versionId
	 * @param id
	 */
	public UserMessagePk(final Long id)
	{
		super(id);
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (!super.equals(_other)) {
			return false;
		}
		
		if (!(_other instanceof UserMessagePk)) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.UserMessagePk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
