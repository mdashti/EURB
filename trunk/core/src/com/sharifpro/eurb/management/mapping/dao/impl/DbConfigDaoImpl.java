package com.sharifpro.eurb.management.mapping.dao.impl;

import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.exception.DbConfigDaoException;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
import com.sharifpro.eurb.management.mapping.model.DbConfigPk;

import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class DbConfigDaoImpl extends AbstractDAO implements ParameterizedRowMapper<DbConfig>, DbConfigDao
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
	 * @return DbConfigPk
	 */
	@Transactional
	public DbConfigPk insert(DbConfig dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, name, driver_class, driver_url, username, password, test_query ) VALUES ( ?, ?, ?, ?, ?, ?, ? )",dto.getId(),dto.getName(),dto.getDriverClass(),dto.getDriverUrl(),dto.getUsername(),dto.getPassword(),dto.getTestQuery());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the db_config table.
	 */
	@Transactional
	public void update(DbConfigPk pk, DbConfig dto) throws DbConfigDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET id = ?, name = ?, driver_class = ?, driver_url = ?, username = ?, password = ?, test_query = ? WHERE id = ?",dto.getId(),dto.getName(),dto.getDriverClass(),dto.getDriverUrl(),dto.getUsername(),dto.getPassword(),dto.getTestQuery(),pk.getId());
	}

	/** 
	 * Deletes a single row in the db_config table.
	 */
	@Transactional
	public void delete(DbConfigPk pk) throws DbConfigDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return DbConfig
	 */
	public DbConfig mapRow(ResultSet rs, int row) throws SQLException
	{
		DbConfig dto = new DbConfig();
		dto.setId( new Long( rs.getLong(1) ) );
		dto.setName( rs.getString( 2 ) );
		dto.setDriverClass( rs.getString( 3 ) );
		dto.setDriverUrl( rs.getString( 4 ) );
		dto.setUsername( rs.getString( 5 ) );
		dto.setPassword( rs.getString( 6 ) );
		dto.setTestQuery( rs.getString( 7 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "db_config";
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'id = :id'.
	 */
	@Transactional
	public DbConfig findByPrimaryKey(Long id) throws DbConfigDaoException
	{
		try {
			List<DbConfig> list = jdbcTemplate.query("SELECT id, name, driver_class, driver_url, username, password, test_query FROM " + getTableName() + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new DbConfigDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria ''.
	 */
	@Transactional
	public List<DbConfig> findAll() throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, name, driver_class, driver_url, username, password, test_query FROM " + getTableName() + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new DbConfigDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<DbConfig> findByPersistableObject(Long id) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, name, driver_class, driver_url, username, password, test_query FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new DbConfigDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<DbConfig> findWhereIdEquals(Long id) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, name, driver_class, driver_url, username, password, test_query FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new DbConfigDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'name = :name'.
	 */
	@Transactional
	public List<DbConfig> findWhereNameEquals(String name) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, name, driver_class, driver_url, username, password, test_query FROM " + getTableName() + " WHERE name = ? ORDER BY name", this,name);
		}
		catch (Exception e) {
			throw new DbConfigDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'driver_class = :driverClass'.
	 */
	@Transactional
	public List<DbConfig> findWhereDriverClassEquals(String driverClass) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, name, driver_class, driver_url, username, password, test_query FROM " + getTableName() + " WHERE driver_class = ? ORDER BY driver_class", this,driverClass);
		}
		catch (Exception e) {
			throw new DbConfigDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'driver_url = :driverUrl'.
	 */
	@Transactional
	public List<DbConfig> findWhereDriverUrlEquals(String driverUrl) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, name, driver_class, driver_url, username, password, test_query FROM " + getTableName() + " WHERE driver_url = ? ORDER BY driver_url", this,driverUrl);
		}
		catch (Exception e) {
			throw new DbConfigDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'username = :username'.
	 */
	@Transactional
	public List<DbConfig> findWhereUsernameEquals(String username) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, name, driver_class, driver_url, username, password, test_query FROM " + getTableName() + " WHERE username = ? ORDER BY username", this,username);
		}
		catch (Exception e) {
			throw new DbConfigDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'password = :password'.
	 */
	@Transactional
	public List<DbConfig> findWherePasswordEquals(String password) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, name, driver_class, driver_url, username, password, test_query FROM " + getTableName() + " WHERE password = ? ORDER BY password", this,password);
		}
		catch (Exception e) {
			throw new DbConfigDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'test_query = :testQuery'.
	 */
	@Transactional
	public List<DbConfig> findWhereTestQueryEquals(String testQuery) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, name, driver_class, driver_url, username, password, test_query FROM " + getTableName() + " WHERE test_query = ? ORDER BY test_query", this,testQuery);
		}
		catch (Exception e) {
			throw new DbConfigDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the db_config table that matches the specified primary-key value.
	 */
	public DbConfig findByPrimaryKey(DbConfigPk pk) throws DbConfigDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

}
