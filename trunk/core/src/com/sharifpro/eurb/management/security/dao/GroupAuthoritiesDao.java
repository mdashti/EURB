package com.sharifpro.eurb.management.security.dao;

import com.sharifpro.eurb.management.security.dao.GroupAuthoritiesDao;
import com.sharifpro.eurb.management.security.exception.GroupAuthoritiesDaoException;
import com.sharifpro.eurb.management.security.model.GroupAuthorities;

import java.util.List;

public interface GroupAuthoritiesDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	public void insert(GroupAuthorities dto);

	/** 
	 * Returns all rows from the group_authorities table that match the criteria ''.
	 */
	public List<GroupAuthorities> findAll() throws GroupAuthoritiesDaoException;

	/** 
	 * Returns all rows from the group_authorities table that match the criteria 'group_id = :groupId'.
	 */
	public List<GroupAuthorities> findByGroups(Long groupId) throws GroupAuthoritiesDaoException;

	/** 
	 * Returns all rows from the group_authorities table that match the criteria 'group_id = :groupId'.
	 */
	public List<GroupAuthorities> findWhereGroupIdEquals(Long groupId) throws GroupAuthoritiesDaoException;

	/** 
	 * Returns all rows from the group_authorities table that match the criteria 'authority = :authority'.
	 */
	public List<GroupAuthorities> findWhereAuthorityEquals(String authority) throws GroupAuthoritiesDaoException;

}
