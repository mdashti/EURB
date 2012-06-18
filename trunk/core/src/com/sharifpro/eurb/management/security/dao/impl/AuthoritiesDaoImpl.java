package com.sharifpro.eurb.management.security.dao.impl;

import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.security.dao.AuthoritiesDao;
import com.sharifpro.eurb.management.security.exception.AuthoritiesDaoException;
import com.sharifpro.eurb.management.security.model.Authorities;
import com.sharifpro.eurb.management.security.model.AuthoritiesPk;
import com.sharifpro.util.PropertyProvider;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class AuthoritiesDaoImpl extends AbstractDAO implements ParameterizedRowMapper<Authorities>, AuthoritiesDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return AuthoritiesPk
	 */
	@Transactional
	public AuthoritiesPk insert(Authorities dto)
	{
		getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( username, authority ) VALUES ( ?, ? )",dto.getUsername(),dto.getAuthority());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the authorities table.
	 */
	@Transactional
	public void update(AuthoritiesPk pk, Authorities dto) throws AuthoritiesDaoException
	{
		getJdbcTemplate().update("UPDATE " + getTableName() + " SET username = ?, authority = ? WHERE username = ? AND authority = ?",dto.getUsername(),dto.getAuthority(),pk.getUsername(),pk.getAuthority());
	}

	/** 
	 * Deletes a single row in the authorities table.
	 */
	@Transactional
	public void delete(AuthoritiesPk pk) throws AuthoritiesDaoException
	{
		getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE username = ? AND authority = ?",pk.getUsername(),pk.getAuthority());
	}

	@Transactional
	public void clearUserAuthorities(String username) throws AuthoritiesDaoException {
		getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE username = ?",username);
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
	public static String getTableName()
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
			List<Authorities> list = getJdbcTemplate().query("SELECT username, authority FROM " + getTableName() + " WHERE username = ? AND authority = ?", this,username,authority);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new AuthoritiesDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the authorities table that match the criteria ''.
	 */
	@Transactional
	public List<Authorities> findAll() throws AuthoritiesDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT username, authority FROM " + getTableName() + " ORDER BY username, authority", this);
		}
		catch (Exception e) {
			throw new AuthoritiesDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the authorities table that match the criteria 'username = :username'.
	 */
	@Transactional
	public List<Authorities> findByUsers(String username) throws AuthoritiesDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT username, authority FROM " + getTableName() + " WHERE username = ?", this,username);
		}
		catch (Exception e) {
			throw new AuthoritiesDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the authorities table that match the criteria 'username = :username'.
	 */
	@Transactional
	public List<Authorities> findWhereUsernameEquals(String username) throws AuthoritiesDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT username, authority FROM " + getTableName() + " WHERE username = ? ORDER BY username", this,username);
		}
		catch (Exception e) {
			throw new AuthoritiesDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the authorities table that match the criteria 'authority = :authority'.
	 */
	@Transactional
	public List<Authorities> findWhereAuthorityEquals(String authority) throws AuthoritiesDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT username, authority FROM " + getTableName() + " WHERE authority = ? ORDER BY authority", this,authority);
		}
		catch (Exception e) {
			throw new AuthoritiesDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns the rows from the authorities table that matches the specified primary-key value.
	 */
	@Transactional(readOnly=true)
	public Authorities findByPrimaryKey(AuthoritiesPk pk) throws AuthoritiesDaoException
	{
		return findByPrimaryKey( pk.getUsername(), pk.getAuthority() );
	}

}
