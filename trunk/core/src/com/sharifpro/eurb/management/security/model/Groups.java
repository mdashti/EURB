package com.sharifpro.eurb.management.security.model;

import java.io.Serializable;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;

public class Groups extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = -8576846036369618269L;

	/** 
	 * This attribute maps to the column group_name in the groups table.
	 */
	protected String groupName;

	/**
	 * Method 'Groups'
	 * 
	 */
	public Groups()
	{
		super();
	}

	/**
	 * Method 'getGroupName'
	 * 
	 * @return String
	 */
	public String getGroupName()
	{
		return groupName;
	}

	/**
	 * Method 'setGroupName'
	 * 
	 * @param groupName
	 */
	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
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
		
		if (!(_other instanceof Groups)) {
			return false;
		}
		
		/*final Groups _cast = (Groups) _other;
		
		if (groupName == null ? _cast.groupName != groupName : !groupName.equals( _cast.groupName )) {
			return false;
		}*/
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = super.hashCode();
		
		/*if (groupName != null) {
			_hashCode = 29 * _hashCode + groupName.hashCode();
		}*/
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return GroupsPk
	 */
	public GroupsPk createPk()
	{
		return new GroupsPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.Groups: " );
		ret.append( super.toString() );
		ret.append( ", groupName=" + groupName );
		return ret.toString();
	}

}
