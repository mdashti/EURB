package com.sharifpro.eurb.management.security.dao.impl;

import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.security.dao.GroupAuthoritiesDao;
import com.sharifpro.eurb.management.security.exception.GroupAuthoritiesDaoException;
import com.sharifpro.eurb.management.security.model.GroupAuthorities;
import com.sharifpro.transaction.annotation.TransactionalReadOnly;
import com.sharifpro.transaction.annotation.TransactionalReadWrite;
import com.sharifpro.util.PropertyProvider;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class GroupAuthoritiesDaoImpl extends AbstractDAO implements ParameterizedRowMapper<GroupAuthorities>, GroupAuthoritiesDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	@TransactionalReadWrite
	public void insert(GroupAuthorities dto)
	{
		getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( group_id, authority ) VALUES ( ?, ? )",dto.getGroupId(),dto.getAuthority());
	}

	@TransactionalReadWrite
	public void clearGroupAuthorities(Long groupId) throws GroupAuthoritiesDaoException {
		getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE group_id = ?",groupId);
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return GroupAuthorities
	 */
	public GroupAuthorities mapRow(ResultSet rs, int row) throws SQLException
	{
		GroupAuthorities dto = new GroupAuthorities();
		dto.setGroupId( new Long( rs.getLong(1) ) );
		dto.setAuthority( rs.getString( 2 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "group_authorities";
	}

	/** 
	 * Returns all rows from the group_authorities table that match the criteria ''.
	 */
	@TransactionalReadOnly
	public List<GroupAuthorities> findAll() throws GroupAuthoritiesDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT group_id, authority FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new GroupAuthoritiesDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the group_authorities table that match the criteria 'group_id = :groupId'.
	 */
	@TransactionalReadOnly
	public List<GroupAuthorities> findByGroups(Long groupId) throws GroupAuthoritiesDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT group_id, authority FROM " + getTableName() + " WHERE group_id = ?", this,groupId);
		}
		catch (Exception e) {
			throw new GroupAuthoritiesDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the group_authorities table that match the criteria 'group_id = :groupId'.
	 */
	@TransactionalReadOnly
	public List<GroupAuthorities> findWhereGroupIdEquals(Long groupId) throws GroupAuthoritiesDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT group_id, authority FROM " + getTableName() + " WHERE group_id = ? ORDER BY group_id", this,groupId);
		}
		catch (Exception e) {
			throw new GroupAuthoritiesDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the group_authorities table that match the criteria 'authority = :authority'.
	 */
	@TransactionalReadOnly
	public List<GroupAuthorities> findWhereAuthorityEquals(String authority) throws GroupAuthoritiesDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT group_id, authority FROM " + getTableName() + " WHERE authority = ? ORDER BY authority", this,authority);
		}
		catch (Exception e) {
			throw new GroupAuthoritiesDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

}
