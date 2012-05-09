package com.sharifpro.eurb.management.security.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObjectPk;

/** 
 * This class represents the primary key of the groups table.
 */
public class UserPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = 444358832111560554L;

	/**
	 * Method 'UserPk'
	 * 
	 */
	public UserPk()
	{
		super();
	}

	/**
	 * Method 'UserPk'
	 * 
	 * @param id
	 */
	public UserPk(final Long id)
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
		
		if (!(_other instanceof UserPk)) {
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
		ret.append( "model.UserPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
