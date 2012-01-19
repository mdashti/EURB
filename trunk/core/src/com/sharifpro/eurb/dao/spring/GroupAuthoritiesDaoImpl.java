package com.sharifpro.eurb.dao.spring;

import com.sharifpro.eurb.dao.GroupAuthoritiesDao;
import com.sharifpro.eurb.dto.GroupAuthorities;
import com.sharifpro.eurb.exceptions.GroupAuthoritiesDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class GroupAuthoritiesDaoImpl extends AbstractDAO implements ParameterizedRowMapper<GroupAuthorities>, GroupAuthoritiesDao
{
	protected JdbcTemplate jdbcTemplate;

	protected DataSource dataSource;

	/**
	 * Method 'setDataSource'
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 */
	@Transactional
	public void insert(GroupAuthorities dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( group_id, authority ) VALUES ( ?, ? )",dto.getGroupId(),dto.getAuthority());
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
	public String getTableName()
	{
		return "group_authorities";
	}

	/** 
	 * Returns all rows from the group_authorities table that match the criteria ''.
	 */
	@Transactional
	public List<GroupAuthorities> findAll() throws GroupAuthoritiesDaoException
	{
		try {
			return jdbcTemplate.query("SELECT group_id, authority FROM " + getTableName() + "", this);
		}
		catch (Exception e) {
			throw new GroupAuthoritiesDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_authorities table that match the criteria 'group_id = :groupId'.
	 */
	@Transactional
	public List<GroupAuthorities> findByGroups(Long groupId) throws GroupAuthoritiesDaoException
	{
		try {
			return jdbcTemplate.query("SELECT group_id, authority FROM " + getTableName() + " WHERE group_id = ?", this,groupId);
		}
		catch (Exception e) {
			throw new GroupAuthoritiesDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_authorities table that match the criteria 'group_id = :groupId'.
	 */
	@Transactional
	public List<GroupAuthorities> findWhereGroupIdEquals(Long groupId) throws GroupAuthoritiesDaoException
	{
		try {
			return jdbcTemplate.query("SELECT group_id, authority FROM " + getTableName() + " WHERE group_id = ? ORDER BY group_id", this,groupId);
		}
		catch (Exception e) {
			throw new GroupAuthoritiesDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_authorities table that match the criteria 'authority = :authority'.
	 */
	@Transactional
	public List<GroupAuthorities> findWhereAuthorityEquals(String authority) throws GroupAuthoritiesDaoException
	{
		try {
			return jdbcTemplate.query("SELECT group_id, authority FROM " + getTableName() + " WHERE authority = ? ORDER BY authority", this,authority);
		}
		catch (Exception e) {
			throw new GroupAuthoritiesDaoException("Query failed", e);
		}
		
	}

}
