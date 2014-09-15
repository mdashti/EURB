package com.sharifpro.eurb.management.security.dao;

import com.sharifpro.eurb.management.security.dao.GroupMembersDao;
import com.sharifpro.eurb.management.security.exception.GroupMembersDaoException;
import com.sharifpro.eurb.management.security.model.GroupMembers;
import com.sharifpro.eurb.management.security.model.GroupMembersPk;

import java.util.List;

public interface GroupMembersDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return GroupMembersPk
	 */
	public GroupMembersPk insert(GroupMembers dto);

	/** 
	 * Deletes a single row in the group_members table using username and group_id.
	 */
	public void delete(GroupMembers dto) throws GroupMembersDaoException;

	/** 
	 * Deletes a single row in the group_members table using group_member.id.
	 */
	public void delete(GroupMembersPk pk) throws GroupMembersDaoException;

	/** 
	 * Returns all rows from the group_members table that match the criteria 'id = :id'.
	 */
	public GroupMembers findByPrimaryKey(Long id) throws GroupMembersDaoException;

	/** 
	 * Returns all rows from the group_members table that match the criteria ''.
	 */
	public List<GroupMembers> findAll() throws GroupMembersDaoException;

	/** 
	 * Returns all rows from the group_members table that match the criteria 'group_id = :groupId'.
	 */
	public List<GroupMembers> findByGroups(Long groupId) throws GroupMembersDaoException;

	/** 
	 * Returns all rows from the group_members table that match the criteria 'id = :id'.
	 */
	public List<GroupMembers> findWhereIdEquals(Long id) throws GroupMembersDaoException;

	/** 
	 * Returns all rows from the group_members table that match the criteria 'username = :username'.
	 */
	public List<GroupMembers> findWhereUsernameEquals(String username) throws GroupMembersDaoException;

	/** 
	 * Returns all rows from the group_members table that match the criteria 'group_id = :groupId'.
	 */
	public List<GroupMembers> findWhereGroupIdEquals(Long groupId) throws GroupMembersDaoException;

	/** 
	 * Returns the rows from the group_members table that matches the specified primary-key value.
	 */
	public GroupMembers findByPrimaryKey(GroupMembersPk pk) throws GroupMembersDaoException;

}
