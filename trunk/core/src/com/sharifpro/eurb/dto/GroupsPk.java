package com.sharifpro.eurb.dto;

import java.io.Serializable;

/** 
 * This class represents the primary key of the groups table.
 */
public class GroupsPk extends PersistableObjectPk implements Serializable
{
	private static final long serialVersionUID = 444358832111560554L;

	/**
	 * Method 'GroupsPk'
	 * 
	 */
	public GroupsPk()
	{
		super();
	}

	/**
	 * Method 'GroupsPk'
	 * 
	 * @param id
	 */
	public GroupsPk(final Long id)
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
		
		if (!(_other instanceof GroupsPk)) {
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
		ret.append( "model.GroupsPk: " );
		ret.append( super.toString() );
		return ret.toString();
	}

}
