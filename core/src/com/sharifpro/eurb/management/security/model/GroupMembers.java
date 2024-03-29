package com.sharifpro.eurb.management.security.model;

import java.io.Serializable;

public class GroupMembers implements Serializable
{
	private static final long serialVersionUID = -4745387844493492387L;

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
		super();
	}

	/**
	 * Method 'GroupMembers'
	 * 
	 */
	public GroupMembers(String username, Long groupId)
	{
		super();
		this.username = username;
		this.groupId = groupId;
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
		if (!super.equals(_other)) {
			return false;
		}
		
		if (!(_other instanceof GroupMembers)) {
			return false;
		}
		
		/*final GroupMembers _cast = (GroupMembers) _other;
		
		if (username == null ? _cast.username != username : !username.equals( _cast.username )) {
			return false;
		}
		
		if (groupId == null ? _cast.groupId != groupId : !groupId.equals( _cast.groupId )) {
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
		
		/*if (username != null) {
			_hashCode = 29 * _hashCode + username.hashCode();
		}
		
		if (groupId != null) {
			_hashCode = 29 * _hashCode + groupId.hashCode();
		}*/
		
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
		ret.append( "model.GroupMembers: " );
		ret.append( super.toString() );
		ret.append( ", username=" + username );
		ret.append( ", groupId=" + groupId );
		return ret.toString();
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

}
