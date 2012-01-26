package com.sharifpro.eurb.dto;

import java.io.Serializable;

/** 
 * This class represents the primary key of the group_members table.
 */
public class GroupMembersPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = 6617840698172091546L;

	/**
	 * Method 'GroupMembersPk'
	 * 
	 */
	public GroupMembersPk()
	{
		super();
	}

	/**
	 * Method 'GroupMembersPk'
	 * 
	 * @param id
	 */
	public GroupMembersPk(final Long id)
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
		
		if (!(_other instanceof GroupMembersPk)) {
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
		ret.append( "model.GroupMembersPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
