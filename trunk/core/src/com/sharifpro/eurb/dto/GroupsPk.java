package com.sharifpro.eurb.dto;

import java.io.Serializable;

/** 
 * This class represents the primary key of the groups table.
 */
public class GroupsPk implements Serializable
{
	private static final long serialVersionUID = 444358832111560554L;
	protected Long id;

	/** 
	 * Sets the value of id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/** 
	 * Gets the value of id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Method 'GroupsPk'
	 * 
	 */
	public GroupsPk()
	{
	}

	/**
	 * Method 'GroupsPk'
	 * 
	 * @param id
	 */
	public GroupsPk(final Long id)
	{
		this.id = id;
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
		
		if (!(_other instanceof GroupsPk)) {
			return false;
		}
		
		final GroupsPk _cast = (GroupsPk) _other;
		if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
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
		
		return _hashCode;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.sharifpro.eurb.dto.GroupsPk: " );
		ret.append( "id=" + id );
		return ret.toString();
	}

}
