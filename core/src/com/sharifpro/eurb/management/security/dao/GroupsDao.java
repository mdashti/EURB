package com.sharifpro.eurb.management.security.dao;

import com.sharifpro.eurb.management.security.dao.GroupsDao;
import com.sharifpro.eurb.management.security.exception.GroupsDaoException;
import com.sharifpro.eurb.management.security.model.Groups;
import com.sharifpro.eurb.management.security.model.GroupsPk;

import java.util.List;

public interface GroupsDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return GroupsPk
	 */
	public GroupsPk insert(Groups dto);

	/** 
	 * Updates a single row in the groups table.
	 */
	public void update(GroupsPk pk, Groups dto) throws GroupsDaoException;

	/** 
	 * Deletes a single row in the groups table.
	 */
	public void delete(GroupsPk pk) throws GroupsDaoException;

	/** 
	 * Returns all rows from the groups table that match the criteria 'id = :id'.
	 */
	public Groups findByPrimaryKey(Long id) throws GroupsDaoException;

	/** 
	 * Returns all rows from the groups table that match the criteria ''.
	 */
	public List<Groups> findAll() throws GroupsDaoException;

	/** 
	 * Returns all rows from the groups table that match the criteria 'id = :id'.
	 */
	public List<Groups> findByPersistableObject(Long id) throws GroupsDaoException;

	/** 
	 * Returns all rows from the groups table that match the criteria 'id = :id'.
	 */
	public List<Groups> findWhereIdEquals(Long id) throws GroupsDaoException;

	/** 
	 * Returns all rows from the groups table that match the criteria 'group_name = :groupName'.
	 */
	public List<Groups> findWhereGroupNameEquals(String groupName) throws GroupsDaoException;

	/** 
	 * Returns the rows from the groups table that matches the specified primary-key value.
	 */
	public Groups findByPrimaryKey(GroupsPk pk) throws GroupsDaoException;

	/**
	 * Finds current users belonging to this group
	 * @param userId
	 * @return list of user groups
	 */
	public List<Groups> findCurrentGroupsForUser(String userId) throws GroupsDaoException;

	/**
	 * Finds current users not belonging to this group
	 * @param userId
	 * @return list of selectable groups for this user
	 */
	public List<Groups> findSelectableGroupsForUser(String userId) throws GroupsDaoException;

	/**
	 * Find All Groups
	 * 
	 * @param startBy
	 * @param limitBy
	 * @param sort
	 * @param dir
	 * @return
	 * @throws GroupsDaoException
	 */
	public List<Groups> findAll(Integer start, Integer limit, String sortBy,
			String sortDir) throws GroupsDaoException;

	/**
	 * Count All Groups
	 * 
	 * @return
	 * @throws GroupsDaoException
	 */
	public int countAll() throws GroupsDaoException;

	/**
	 * Find All Groups According to Query
	 * 
	 * @param query
	 * @param onFields
	 * @param startBy
	 * @param limitBy
	 * @param sort
	 * @param dir
	 * @return
	 * @throws GroupsDaoException
	 */
	public List<Groups> findAll(String query, List<String> onFields,
			Integer start, Integer limit, String sortBy, String sortDir) throws GroupsDaoException;

	/**
	 * Count All Groups According to Query
	 * 
	 * @param query
	 * @param onFields
	 * @return
	 * @throws GroupsDaoException
	 */
	public int countAll(String query, List<String> onFields) throws GroupsDaoException;

	/**
	 * Delete All Specified Groups
	 * 
	 * @param pkList
	 * @throws GroupsDaoException
	 */
	public void deleteAll(List<GroupsPk> pkList) throws GroupsDaoException;

}
