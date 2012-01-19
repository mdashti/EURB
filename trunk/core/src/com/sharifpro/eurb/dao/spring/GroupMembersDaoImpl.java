package com.sharifpro.eurb.dao.spring;

import com.sharifpro.eurb.dao.GroupMembersDao;
import com.sharifpro.eurb.dto.GroupMembers;
import com.sharifpro.eurb.dto.GroupMembersPk;
import com.sharifpro.eurb.exceptions.GroupMembersDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class GroupMembersDaoImpl extends AbstractDAO implements ParameterizedRowMapper<GroupMembers>, GroupMembersDao
{
	protected SimpleJdbcTemplate jdbcTemplate;

	protected DataSource dataSource;

	/**
	 * Method 'setDataSource'
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

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
	 * Updates a single row in the group_members table.
	 */
	@Transactional
	public void update(GroupMembersPk pk, GroupMembers dto) throws GroupMembersDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET id = ?, username = ?, group_id = ? WHERE id = ?",dto.getId(),dto.getUsername(),dto.getGroupId(),pk.getId());
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
	public String getTableName()
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
			throw new GroupMembersDaoException("Query failed", e);
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
			throw new GroupMembersDaoException("Query failed", e);
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
			throw new GroupMembersDaoException("Query failed", e);
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
			throw new GroupMembersDaoException("Query failed", e);
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
			throw new GroupMembersDaoException("Query failed", e);
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
			throw new GroupMembersDaoException("Query failed", e);
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
