package com.sharifpro.eurb.dto;

import java.io.Serializable;

public class Groups implements Serializable
{
	private static final long serialVersionUID = -8576846036369618269L;

	/** 
	 * This attribute maps to the column id in the groups table.
	 */
	protected Long id;

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
	}

	/**
	 * Method 'getId'
	 * 
	 * @return Long
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Method 'setId'
	 * 
	 * @param id
	 */
	public void setId(Long id)
	{
		this.id = id;
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
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof Groups)) {
			return false;
		}
		
		final Groups _cast = (Groups) _other;
		if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
			return false;
		}
		
		if (groupName == null ? _cast.groupName != groupName : !groupName.equals( _cast.groupName )) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		if (id != null) {
			_hashCode = 29 * _hashCode + id.hashCode();
		}
		
		if (groupName != null) {
			_hashCode = 29 * _hashCode + groupName.hashCode();
		}
		
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
		ret.append( "com.sharifpro.eurb.dto.Groups: " );
		ret.append( "id=" + id );
		ret.append( ", groupName=" + groupName );
		return ret.toString();
	}

}
