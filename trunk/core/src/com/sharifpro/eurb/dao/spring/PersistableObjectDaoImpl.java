package com.sharifpro.eurb.dao.spring;

import com.sharifpro.eurb.dao.PersistableObjectDao;
import com.sharifpro.eurb.dto.PersistableObject;
import com.sharifpro.eurb.dto.PersistableObjectPk;
import com.sharifpro.eurb.exceptions.PersistableObjectDaoException;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

public class PersistableObjectDaoImpl extends AbstractDAO implements ParameterizedRowMapper<PersistableObject>, PersistableObjectDao
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
	 * @return PersistableObjectPk
	 */
	@Transactional
	public PersistableObjectPk insert(final PersistableObject dto)
	{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String INSERT_SQL = "INSERT INTO " + getTableName() + " ( obj_obj_type, creator, create_date, modifier, modify_date ) VALUES ( ?, ?, ?, ?, ? )";
		jdbcTemplate.update(
			new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] {"id"});
					Long now = new Date().getTime();
					int i=0;
					ps.setInt(++i, dto.getObjectType());
					ps.setString(++i, dto.getCreator());
					ps.setTimestamp(++i, new java.sql.Timestamp(now));
					ps.setString(++i, dto.getCreator());
					ps.setTimestamp(++i, new java.sql.Timestamp(now));
					return ps;
				}
			}, keyHolder);
		PersistableObjectPk pk = new PersistableObjectPk();
		pk.setId( keyHolder.getKey().longValue() );
		return pk;
	}

	/** 
	 * Updates a single row in the persistable_object table.
	 */
	@Transactional
	public void update(PersistableObjectPk pk, PersistableObject dto) throws PersistableObjectDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET modifier = ?, modify_date = ? WHERE id = ?",dto.getModifier(),new Timestamp(new Date().getTime()),pk.getId());
	}

	/** 
	 * Deletes a single row in the persistable_object table.
	 */
	@Transactional
	public void delete(PersistableObjectPk pk) throws PersistableObjectDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return PersistableObject
	 */
	public PersistableObject mapRow(ResultSet rs, int row) throws SQLException
	{
		PersistableObject dto = new PersistableObject();
		dto.setId( new Long( rs.getLong(1) ) );
		dto.setObjectType( new Integer( rs.getInt(2) ) );
		dto.setCreator( rs.getString( 3 ) );
		dto.setCreateDate( rs.getTimestamp(4 ) );
		dto.setModifier( rs.getString( 5 ) );
		dto.setModifyDate( rs.getTimestamp(6 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "persistable_object";
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'id = :id'.
	 */
	@Transactional
	public PersistableObject findByPrimaryKey(Long id) throws PersistableObjectDaoException
	{
		try {
			List<PersistableObject> list = jdbcTemplate.query("SELECT id, obj_type, creator, create_date, modifier, modify_date FROM " + getTableName() + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria ''.
	 */
	@Transactional
	public List<PersistableObject> findAll() throws PersistableObjectDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, obj_type, creator, create_date, modifier, modify_date FROM " + getTableName() + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'creator = :creator'.
	 */
	@Transactional
	public List<PersistableObject> findByUsers(String creator) throws PersistableObjectDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, obj_type, creator, create_date, modifier, modify_date FROM " + getTableName() + " WHERE creator = ?", this,creator);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'modifier = :modifier'.
	 */
	@Transactional
	public List<PersistableObject> findByUsers2(String modifier) throws PersistableObjectDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, obj_type, creator, create_date, modifier, modify_date FROM " + getTableName() + " WHERE modifier = ?", this,modifier);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<PersistableObject> findWhereIdEquals(Long id) throws PersistableObjectDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, obj_type, creator, create_date, modifier, modify_date FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'objType = :objectType'.
	 */
	@Transactional
	public List<PersistableObject> findWhereObjectTypeEquals(Integer objectType) throws PersistableObjectDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, obj_type, creator, create_date, modifier, modify_date FROM " + getTableName() + " WHERE obj_type = ? ORDER BY obj_type", this,objectType);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'creator = :creator'.
	 */
	@Transactional
	public List<PersistableObject> findWhereCreatorEquals(String creator) throws PersistableObjectDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, obj_type, creator, create_date, modifier, modify_date FROM " + getTableName() + " WHERE creator = ? ORDER BY creator", this,creator);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'create_date = :createDate'.
	 */
	@Transactional
	public List<PersistableObject> findWhereCreateDateEquals(Date createDate) throws PersistableObjectDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, obj_type, creator, create_date, modifier, modify_date FROM " + getTableName() + " WHERE create_date = ? ORDER BY create_date", this,createDate);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'modifier = :modifier'.
	 */
	@Transactional
	public List<PersistableObject> findWhereModifierEquals(String modifier) throws PersistableObjectDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, obj_type, creator, create_date, modifier, modify_date FROM " + getTableName() + " WHERE modifier = ? ORDER BY modifier", this,modifier);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'modify_date = :modifyDate'.
	 */
	@Transactional
	public List<PersistableObject> findWhereModifyDateEquals(Date modifyDate) throws PersistableObjectDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, obj_type, creator, create_date, modifier, modify_date FROM " + getTableName() + " WHERE modify_date = ? ORDER BY modify_date", this,modifyDate);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the persistable_object table that matches the specified primary-key value.
	 */
	public PersistableObject findByPrimaryKey(PersistableObjectPk pk) throws PersistableObjectDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

}
