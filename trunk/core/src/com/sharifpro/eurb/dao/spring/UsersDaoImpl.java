package com.sharifpro.eurb.dao.spring;

import com.sharifpro.eurb.dao.UsersDao;
import com.sharifpro.eurb.dto.Users;
import com.sharifpro.eurb.dto.UsersPk;
import com.sharifpro.eurb.exceptions.UsersDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class UsersDaoImpl extends AbstractDAO implements ParameterizedRowMapper<Users>, UsersDao
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
	 * @return UsersPk
	 */
	@Transactional
	public UsersPk insert(Users dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( username, password, enabled ) VALUES ( ?, ?, ? )",dto.getUsername(),dto.getPassword(),dto.isEnabled());
		UsersPk pk = new UsersPk();
		pk.setUsername( jdbcTemplate.queryForObject("select last_insert_id()", String.class) );
		return pk;
	}

	/** 
	 * Updates a single row in the users table.
	 */
	@Transactional
	public void update(UsersPk pk, Users dto) throws UsersDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET id = ?, username = ?, password = ?, enabled = ? WHERE username = ?",dto.getId(),dto.getUsername(),dto.getPassword(),dto.isEnabled(),pk.getUsername());
	}

	/** 
	 * Deletes a single row in the users table.
	 */
	@Transactional
	public void delete(UsersPk pk) throws UsersDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE username = ?",pk.getUsername());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return Users
	 */
	public Users mapRow(ResultSet rs, int row) throws SQLException
	{
		Users dto = new Users();
		dto.setId( new Long( rs.getLong(1) ) );
		dto.setUsername( rs.getString( 2 ) );
		dto.setPassword( rs.getString( 3 ) );
		dto.setEnabled( rs.getBoolean( 4 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "users";
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'username = :username'.
	 */
	@Transactional
	public Users findByPrimaryKey(String username) throws UsersDaoException
	{
		try {
			List<Users> list = jdbcTemplate.query("SELECT id, username, password, enabled FROM " + getTableName() + " WHERE username = ?", this,username);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new UsersDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria ''.
	 */
	@Transactional
	public List<Users> findAll() throws UsersDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, username, password, enabled FROM " + getTableName() + " ORDER BY username", this);
		}
		catch (Exception e) {
			throw new UsersDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<Users> findByPersistableObject(Long id) throws UsersDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, username, password, enabled FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new UsersDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<Users> findWhereIdEquals(Long id) throws UsersDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, username, password, enabled FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new UsersDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'username = :username'.
	 */
	@Transactional
	public List<Users> findWhereUsernameEquals(String username) throws UsersDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, username, password, enabled FROM " + getTableName() + " WHERE username = ? ORDER BY username", this,username);
		}
		catch (Exception e) {
			throw new UsersDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'password = :password'.
	 */
	@Transactional
	public List<Users> findWherePasswordEquals(String password) throws UsersDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, username, password, enabled FROM " + getTableName() + " WHERE password = ? ORDER BY password", this,password);
		}
		catch (Exception e) {
			throw new UsersDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the users table that match the criteria 'enabled = :enabled'.
	 */
	@Transactional
	public List<Users> findWhereEnabledEquals(Short enabled) throws UsersDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, username, password, enabled FROM " + getTableName() + " WHERE enabled = ? ORDER BY enabled", this,enabled);
		}
		catch (Exception e) {
			throw new UsersDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the users table that matches the specified primary-key value.
	 */
	public Users findByPrimaryKey(UsersPk pk) throws UsersDaoException
	{
		return findByPrimaryKey( pk.getUsername() );
	}

}
