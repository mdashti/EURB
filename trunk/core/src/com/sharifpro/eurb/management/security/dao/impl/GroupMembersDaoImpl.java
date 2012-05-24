package com.sharifpro.eurb.management.security.dao.impl;

import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.security.dao.GroupMembersDao;
import com.sharifpro.eurb.management.security.exception.GroupMembersDaoException;
import com.sharifpro.eurb.management.security.model.GroupMembers;
import com.sharifpro.eurb.management.security.model.GroupMembersPk;
import com.sharifpro.util.PropertyProvider;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class GroupMembersDaoImpl extends AbstractDAO implements ParameterizedRowMapper<GroupMembers>, GroupMembersDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return GroupMembersPk
	 */
	@Transactional
	public GroupMembersPk insert(GroupMembers dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( username, group_id ) VALUES ( ?, ? )",dto.getUsername(),dto.getGroupId());
		GroupMembersPk pk = new GroupMembersPk();
		pk.setId( jdbcTemplate.queryForLong("select last_insert_id()") );
		return pk;
	}

	/** 
	 * Deletes a single row in the group_members table.
	 */
	@Transactional
	public void delete(GroupMembers dto) throws GroupMembersDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE username = ? AND group_id = ?",dto.getUsername(), dto.getGroupId());
	}

	/** 
	 * Deletes a single row in the group_members table.
	 */
	@Transactional
	public void delete(GroupMembersPk pk) throws GroupMembersDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return GroupMembers
	 */
	public GroupMembers mapRow(ResultSet rs, int row) throws SQLException
	{
		GroupMembers dto = new GroupMembers();
		dto.setId( new Long( rs.getLong(1) ) );
		dto.setUsername( rs.getString( 2 ) );
		dto.setGroupId( new Long( rs.getLong(3) ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "group_members";
	}

	/** 
	 * Returns all rows from the group_members table that match the criteria 'id = :id'.
	 */
	@Transactional
	public GroupMembers findByPrimaryKey(Long id) throws GroupMembersDaoException
	{
		try {
			List<GroupMembers> list = jdbcTemplate.query("SELECT id, username, group_id FROM " + getTableName() + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new GroupMembersDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the group_members table that match the criteria ''.
	 */
	@Transactional
	public List<GroupMembers> findAll() throws GroupMembersDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, username, group_id FROM " + getTableName() + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new GroupMembersDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the group_members table that match the criteria 'group_id = :groupId'.
	 */
	@Transactional
	public List<GroupMembers> findByGroups(Long groupId) throws GroupMembersDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, username, group_id FROM " + getTableName() + " WHERE group_id = ?", this,groupId);
		}
		catch (Exception e) {
			throw new GroupMembersDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the group_members table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<GroupMembers> findWhereIdEquals(Long id) throws GroupMembersDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, username, group_id FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new GroupMembersDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the group_members table that match the criteria 'username = :username'.
	 */
	@Transactional
	public List<GroupMembers> findWhereUsernameEquals(String username) throws GroupMembersDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, username, group_id FROM " + getTableName() + " WHERE username = ? ORDER BY username", this,username);
		}
		catch (Exception e) {
			throw new GroupMembersDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the group_members table that match the criteria 'group_id = :groupId'.
	 */
	@Transactional
	public List<GroupMembers> findWhereGroupIdEquals(Long groupId) throws GroupMembersDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, username, group_id FROM " + getTableName() + " WHERE group_id = ? ORDER BY group_id", this,groupId);
		}
		catch (Exception e) {
			throw new GroupMembersDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns the rows from the group_members table that matches the specified primary-key value.
	 */
	public GroupMembers findByPrimaryKey(GroupMembersPk pk) throws GroupMembersDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

}
