package com.sharifpro.eurb.dao;

import com.sharifpro.eurb.dao.GroupMembersDao;
import com.sharifpro.eurb.dto.GroupMembers;
import com.sharifpro.eurb.dto.GroupMembersPk;
import com.sharifpro.eurb.exceptions.GroupMembersDaoException;
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
	 * Updates a single row in the group_members table.
	 */
	public void update(GroupMembersPk pk, GroupMembers dto) throws GroupMembersDaoException;

	/** 
	 * Deletes a single row in the group_members table.
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
