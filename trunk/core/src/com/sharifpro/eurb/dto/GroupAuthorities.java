package com.sharifpro.eurb.dto;

import com.sharifpro.eurb.dao.*;
import com.sharifpro.eurb.factory.*;
import com.sharifpro.eurb.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class GroupAuthorities implements Serializable
{
	/** 
	 * This attribute maps to the column group_id in the group_authorities table.
	 */
	protected Long groupId;

	/** 
	 * This attribute maps to the column authority in the group_authorities table.
	 */
	protected String authority;

	/**
	 * Method 'GroupAuthorities'
	 * 
	 */
	public GroupAuthorities()
	{
	}

	/**
	 * Method 'getGroupId'
	 * 
	 * @return Long
	 */
	public Long getGroupId()
	{
		return groupId;
	}

	/**
	 * Method 'setGroupId'
	 * 
	 * @param groupId
	 */
	public void setGroupId(Long groupId)
	{
		this.groupId = groupId;
	}

	/**
	 * Method 'getAuthority'
	 * 
	 * @return String
	 */
	public String getAuthority()
	{
		return authority;
	}

	/**
	 * Method 'setAuthority'
	 * 
	 * @param authority
	 */
	public void setAuthority(String authority)
	{
		this.authority = authority;
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
		
		if (!(_other instanceof GroupAuthorities)) {
			return false;
		}
		
		final GroupAuthorities _cast = (GroupAuthorities) _other;
		if (groupId == null ? _cast.groupId != groupId : !groupId.equals( _cast.groupId )) {
			return false;
		}
		
		if (authority == null ? _cast.authority != authority : !authority.equals( _cast.authority )) {
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
		if (groupId != null) {
			_hashCode = 29 * _hashCode + groupId.hashCode();
		}
		
		if (authority != null) {
			_hashCode = 29 * _hashCode + authority.hashCode();
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
		ret.append( "com.sharifpro.eurb.dto.GroupAuthorities: " );
		ret.append( "groupId=" + groupId );
		ret.append( ", authority=" + authority );
		return ret.toString();
	}

}
