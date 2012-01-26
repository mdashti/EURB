package com.sharifpro.eurb.management.security.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.dao.impl.PersistableObjectDaoImpl;
import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.eurb.management.security.exception.UserDaoException;
import com.sharifpro.eurb.management.security.model.User;
import com.sharifpro.eurb.management.security.model.UserPk;
import com.sharifpro.util.SecurityUtil;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.OperationNotSupportedException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class UserDaoImpl extends AbstractDAO implements ParameterizedRowMapper<User>, UserDao
{
	private final static String QUERY_FROM_COLUMNS = "o.username, o.password, o.enabled";

	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;
	
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return UsersPk
	 */
	@Transactional(readOnly=false)
	public UserPk insert(User dto)
	{
		UserPk pk = new UserPk();
		DaoFactory.createPersistableObjectDao().insert(dto, pk);
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, username, password, enabled ) VALUES ( ?, ?, ?, ? )", pk.getId(), dto.getUsername(),SecurityUtil.generatePassword(dto.getPassword(), dto.getUsername()),dto.isEnabled());
		pk.setUsername( dto.getUsername() );
		return pk;
	}

	/** 
	 * Updates a single row in the users table.
	 */
	@Transactional(readOnly=false)
	public void update(UserPk pk, User dto) throws UserDaoException {
		throw new RuntimeException(new OperationNotSupportedException("User can not be updated! use setEnabled and setPassword instead"));
	}
	/** 
	 * Sets enabled flag for a single user in the users table.
	 */
	@Transactional(readOnly=false)
	public void setEnabled(UserPk pk, User dto)
	{
		DaoFactory.createPersistableObjectDao().update(pk, dto);
		jdbcTemplate.update("UPDATE " + getTableName() + " SET enabled = ? WHERE username = ?", dto.isEnabled(), pk.getUsername());
	}
	
	/** 
	 * Sets password for a single user in the users table.
	 */
	@Transactional(readOnly=false)
	public void setPassword(UserPk pk, User dto)
	{
		DaoFactory.createPersistableObjectDao().update(pk, dto);
		jdbcTemplate.update("UPDATE " + getTableName() + " SET password = ? WHERE username = ?", SecurityUtil.generatePassword(dto.getPassword(), dto.getUsername()), pk.getUsername());
	}

	/** 
	 * Deletes a single row in the users table.
	 */
	@Transactional(readOnly=false)
	public void delete(UserPk pk)
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE username = ?",pk.getUsername());
		DaoFactory.createPersistableObjectDao().delete(pk);
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return Users
	 */
	public User mapRow(ResultSet rs, int row) throws SQLException
	{
		User dto = new User();
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setUsername( rs.getString( ++i ) );
		dto.setPassword( rs.getString( ++i ) );
		dto.setEnabled( rs.getBoolean( ++i ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "users";
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'username = :username'.
	 */
	@Transactional(readOnly=true)
	public User findByPrimaryKey(String username) throws UserDaoException
	{
		try {
			List<User> list = jdbcTemplate.query(QUERY_SELECT_PART + " WHERE username = ?", this,username);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new UserDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria ''.
	 */
	@Transactional(readOnly=true)
	public List<User> findAll() throws UserDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " ORDER BY username", this);
		}
		catch (Exception e) {
			throw new UserDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'id = :id'.
	 */
	@Transactional(readOnly=true)
	public List<User> findByPersistableObject(Long id) throws UserDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new UserDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'id = :id'.
	 */
	@Transactional(readOnly=true)
	public List<User> findWhereIdEquals(Long id) throws UserDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new UserDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'username = :username'.
	 */
	@Transactional(readOnly=true)
	public List<User> findWhereUsernameEquals(String username) throws UserDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE username = ? ORDER BY username", this,username);
		}
		catch (Exception e) {
			throw new UserDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'password = :password'.
	 */
	@Transactional(readOnly=true)
	public List<User> findWherePasswordEquals(String password) throws UserDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE password = ? ORDER BY password", this,password);
		}
		catch (Exception e) {
			throw new UserDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'enabled = :enabled'.
	 */
	@Transactional(readOnly=true)
	public List<User> findWhereEnabledEquals(Short enabled) throws UserDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE enabled = ? ORDER BY enabled", this,enabled);
		}
		catch (Exception e) {
			throw new UserDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the users table that matches the specified primary-key value.
	 */
	public User findByPrimaryKey(UserPk pk) throws UserDaoException
	{
		return findByPrimaryKey( pk.getUsername() );
	}
}
