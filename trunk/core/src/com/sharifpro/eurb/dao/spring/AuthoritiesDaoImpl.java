package com.sharifpro.eurb.dao.spring;

import com.sharifpro.eurb.dao.AuthoritiesDao;
import com.sharifpro.eurb.dto.Authorities;
import com.sharifpro.eurb.dto.AuthoritiesPk;
import com.sharifpro.eurb.exceptions.AuthoritiesDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class AuthoritiesDaoImpl extends AbstractDAO implements ParameterizedRowMapper<Authorities>, AuthoritiesDao
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
	 * @return AuthoritiesPk
	 */
	@Transactional
	public AuthoritiesPk insert(Authorities dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( username, authority ) VALUES ( ?, ? )",dto.getUsername(),dto.getAuthority());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the authorities table.
	 */
	@Transactional
	public void update(AuthoritiesPk pk, Authorities dto) throws AuthoritiesDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET username = ?, authority = ? WHERE username = ? AND authority = ?",dto.getUsername(),dto.getAuthority(),pk.getUsername(),pk.getAuthority());
	}

	/** 
	 * Deletes a single row in the authorities table.
	 */
	@Transactional
	public void delete(AuthoritiesPk pk) throws AuthoritiesDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE username = ? AND authority = ?",pk.getUsername(),pk.getAuthority());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return Authorities
	 */
	public Authorities mapRow(ResultSet rs, int row) throws SQLException
	{
		Authorities dto = new Authorities();
		dto.setUsername( rs.getString( 1 ) );
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
		return "authorities";
	}

	/** 
	 * Returns all rows from the authorities table that match the criteria 'username = :username AND authority = :authority'.
	 */
	@Transactional
	public Authorities findByPrimaryKey(String username, String authority) throws AuthoritiesDaoException
	{
		try {
			List<Authorities> list = jdbcTemplate.query("SELECT username, authority FROM " + getTableName() + " WHERE username = ? AND authority = ?", this,username,authority);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new AuthoritiesDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the authorities table that match the criteria ''.
	 */
	@Transactional
	public List<Authorities> findAll() throws AuthoritiesDaoException
	{
		try {
			return jdbcTemplate.query("SELECT username, authority FROM " + getTableName() + " ORDER BY username, authority", this);
		}
		catch (Exception e) {
			throw new AuthoritiesDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the authorities table that match the criteria 'username = :username'.
	 */
	@Transactional
	public List<Authorities> findByUsers(String username) throws AuthoritiesDaoException
	{
		try {
			return jdbcTemplate.query("SELECT username, authority FROM " + getTableName() + " WHERE username = ?", this,username);
		}
		catch (Exception e) {
			throw new AuthoritiesDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the authorities table that match the criteria 'username = :username'.
	 */
	@Transactional
	public List<Authorities> findWhereUsernameEquals(String username) throws AuthoritiesDaoException
	{
		try {
			return jdbcTemplate.query("SELECT username, authority FROM " + getTableName() + " WHERE username = ? ORDER BY username", this,username);
		}
		catch (Exception e) {
			throw new AuthoritiesDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the authorities table that match the criteria 'authority = :authority'.
	 */
	@Transactional
	public List<Authorities> findWhereAuthorityEquals(String authority) throws AuthoritiesDaoException
	{
		try {
			return jdbcTemplate.query("SELECT username, authority FROM " + getTableName() + " WHERE authority = ? ORDER BY authority", this,authority);
		}
		catch (Exception e) {
			throw new AuthoritiesDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the authorities table that matches the specified primary-key value.
	 */
	public Authorities findByPrimaryKey(AuthoritiesPk pk) throws AuthoritiesDaoException
	{
		return findByPrimaryKey( pk.getUsername(), pk.getAuthority() );
	}

}
