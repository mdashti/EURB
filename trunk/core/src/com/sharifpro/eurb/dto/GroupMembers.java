package com.sharifpro.eurb.dto;

import com.sharifpro.eurb.dao.*;
import com.sharifpro.eurb.factory.*;
import com.sharifpro.eurb.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class GroupMembers implements Serializable
{
	/** 
	 * This attribute maps to the column id in the group_members table.
	 */
	protected Long id;

	/** 
	 * This attribute maps to the column username in the group_members table.
	 */
	protected String username;

	/** 
	 * This attribute maps to the column group_id in the group_members table.
	 */
	protected Long groupId;

	/**
	 * Method 'GroupMembers'
	 * 
	 */
	public GroupMembers()
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
	 * Method 'getUsername'
	 * 
	 * @return String
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Method 'setUsername'
	 * 
	 * @param username
	 */
	public void setUsername(String username)
	{
		this.username = username;
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
		
		if (!(_other instanceof GroupMembers)) {
			return false;
		}
		
		final GroupMembers _cast = (GroupMembers) _other;
		if (id == null ? _cast.id != id : !id.equals( _cast.id )) {
			return false;
		}
		
		if (username == null ? _cast.username != username : !username.equals( _cast.username )) {
			return false;
		}
		
		if (groupId == null ? _cast.groupId != groupId : !groupId.equals( _cast.groupId )) {
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
		
		if (username != null) {
			_hashCode = 29 * _hashCode + username.hashCode();
		}
		
		if (groupId != null) {
			_hashCode = 29 * _hashCode + groupId.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return GroupMembersPk
	 */
	public GroupMembersPk createPk()
	{
		return new GroupMembersPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "com.sharifpro.eurb.dto.GroupMembers: " );
		ret.append( "id=" + id );
		ret.append( ", username=" + username );
		ret.append( ", groupId=" + groupId );
		return ret.toString();
	}

}
