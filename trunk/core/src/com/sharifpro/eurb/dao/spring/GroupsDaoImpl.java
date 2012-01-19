package com.sharifpro.eurb.dao.spring;

import com.sharifpro.eurb.dao.GroupsDao;
import com.sharifpro.eurb.dto.Groups;
import com.sharifpro.eurb.dto.GroupsPk;
import com.sharifpro.eurb.exceptions.GroupsDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class GroupsDaoImpl extends AbstractDAO implements ParameterizedRowMapper<Groups>, GroupsDao
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
	 * @return GroupsPk
	 */
	@Transactional
	public GroupsPk insert(Groups dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( group_name ) VALUES ( ? )",dto.getGroupName());
		GroupsPk pk = new GroupsPk();
		pk.setId( jdbcTemplate.queryForLong("select last_insert_id()") );
		return pk;
	}

	/** 
	 * Updates a single row in the groups table.
	 */
	@Transactional
	public void update(GroupsPk pk, Groups dto) throws GroupsDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET id = ?, group_name = ? WHERE id = ?",dto.getId(),dto.getGroupName(),pk.getId());
	}

	/** 
	 * Deletes a single row in the groups table.
	 */
	@Transactional
	public void delete(GroupsPk pk) throws GroupsDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return Groups
	 */
	public Groups mapRow(ResultSet rs, int row) throws SQLException
	{
		Groups dto = new Groups();
		dto.setId( new Long( rs.getLong(1) ) );
		dto.setGroupName( rs.getString( 2 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "groups";
	}

	/** 
	 * Returns all rows from the groups table that match the criteria 'id = :id'.
	 */
	@Transactional
	public Groups findByPrimaryKey(Long id) throws GroupsDaoException
	{
		try {
			List<Groups> list = jdbcTemplate.query("SELECT id, group_name FROM " + getTableName() + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new GroupsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the groups table that match the criteria ''.
	 */
	@Transactional
	public List<Groups> findAll() throws GroupsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, group_name FROM " + getTableName() + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new GroupsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the groups table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<Groups> findByPersistableObject(Long id) throws GroupsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, group_name FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new GroupsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the groups table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<Groups> findWhereIdEquals(Long id) throws GroupsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, group_name FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new GroupsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the groups table that match the criteria 'group_name = :groupName'.
	 */
	@Transactional
	public List<Groups> findWhereGroupNameEquals(String groupName) throws GroupsDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, group_name FROM " + getTableName() + " WHERE group_name = ? ORDER BY group_name", this,groupName);
		}
		catch (Exception e) {
			throw new GroupsDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the groups table that matches the specified primary-key value.
	 */
	public Groups findByPrimaryKey(GroupsPk pk) throws GroupsDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

}
