package com.sharifpro.eurb.management.mapping.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.exception.DbConfigDaoException;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
import com.sharifpro.eurb.management.mapping.model.DbConfigPk;
import com.sharifpro.util.PropertyProvider;

import java.util.Arrays;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class DbConfigDaoImpl extends AbstractDAO implements ParameterizedRowMapper<DbConfig>, DbConfigDao
{
	private final static String QUERY_FROM_COLUMNS = "o.name, o.driver_class, o.driver_url, o.username, o.password, o.test_query, o.record_status";

	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;
	
	private final static String COUNT_QUERY = "SELECT count(distinct(o.id)) FROM " + getTableName() + " o WHERE o.record_status IN ('A', 'P')";

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return DbConfigPk
	 */
	@Transactional
	public DbConfigPk insert(DbConfig dto)
	{
		DbConfigPk pk = new DbConfigPk();
		DaoFactory.createPersistableObjectDao().insert(dto, pk);
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, name, driver_class, driver_url, username, password, test_query, record_status ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )",pk.getId(),dto.getName(),dto.getDriverClass(),dto.getDriverUrl(),dto.getUsername(),dto.getPassword(),dto.getTestQuery(), dto.getRecordStatusString());
		return pk;
	}

	/** 
	 * Updates a single row in the db_config table.
	 */
	@Transactional
	public void update(DbConfigPk pk, DbConfig dto) throws DbConfigDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk, dto);
		jdbcTemplate.update("UPDATE " + getTableName() + " SET name = ?, driver_class = ?, driver_url = ?, username = ?, password = ?, test_query = ?, record_status = 'A' WHERE id = ?",dto.getName(),dto.getDriverClass(),dto.getDriverUrl(),dto.getUsername(),dto.getPassword(),dto.getTestQuery(),pk.getId());
	}
	
	/** 
	 * Activates a single row in the db_config table.
	 */
	@Transactional
	public void activate(DbConfigPk pk, DbConfig dto) throws DbConfigDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk, dto);
		jdbcTemplate.update("UPDATE " + getTableName() + " SET record_status = 'A' WHERE id = ?",pk.getId());
	}
	
	/** 
	 * Deactivates a single row in the db_config table.
	 */
	@Transactional
	public void deactivate(DbConfigPk pk, DbConfig dto) throws DbConfigDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk, dto);
		jdbcTemplate.update("UPDATE " + getTableName() + " SET record_status = 'P' WHERE id = ?",pk.getId());
	}

	/** 
	 * Deletes a single row in the db_config table.
	 */
	@Transactional
	public void delete(DbConfigPk pk) throws DbConfigDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
		DaoFactory.createPersistableObjectDao().delete(pk);
	}

	/** 
	 * Deletes a single row in the db_config table.
	 */
	@Transactional
	public void deleteAll(List<DbConfigPk> pkList) throws DbConfigDaoException
	{
		for(DbConfigPk pk : pkList) {
			delete(pk);
		}
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
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setName( rs.getString( ++i ) );
		dto.setDriverClass( rs.getString( ++i ) );
		dto.setDriverUrl( rs.getString( ++i ) );
		dto.setUsername( rs.getString( ++i ) );
		dto.setPassword( rs.getString( ++i ) );
		dto.setTestQuery( rs.getString( ++i ) );
		dto.setRecordStatus( rs.getString( ++i ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
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
			List<DbConfig> list = jdbcTemplate.query(QUERY_SELECT_PART + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria ''.
	 */
	@Transactional
	public List<DbConfig> findAll() throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE record_status IN ('A', 'P') ORDER BY id", this);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}
	
	@Transactional
	public int countAll() throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.queryForInt(COUNT_QUERY);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria '' limited by start and limit.
	 */
	@Transactional
	public List<DbConfig> findAll(Integer start, Integer limit) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.record_status IN ('A', 'P') ORDER BY id limit ?, ?", this, start, limit);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria like query in onFields fields limited by start and limit.
	 */
	@Transactional
	public List<DbConfig> findAll(String query, List<String> onFields, Integer start, Integer limit) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.record_status IN ('A', 'P') AND (" + getMultipleFieldWhereClause(query, onFields) + ") ORDER BY id limit ?, ?", this, start, limit);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}
	
	@Transactional
	public int countAll(String query, List<String> onFields) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.queryForInt(COUNT_QUERY + " AND (" + getMultipleFieldWhereClause(query, onFields) + ")");
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	private static String getMultipleFieldWhereClause(String txt, List<String> fields) {
		StringBuilder query = new StringBuilder();
		String likeQuery = " LIKE '%"+txt+"%' OR ";
		if(fields.contains("name")) {
			query.append("o.name").append(likeQuery);
		}
		if(fields.contains("driverClass")) {
			query.append("o.driver_class").append(likeQuery);
		}
		if(fields.contains("driverUrl")) {
			query.append("o.driver_url").append(likeQuery);
		}
		if(fields.contains("username")) {
			query.append("o.username").append(likeQuery);
		}
		if(fields.contains("testQuery")) {
			query.append("o.test_query").append(likeQuery);
		}
		if(query.length() > 0) {
			return query.substring(0,query.length()-3);
		} else {
			return "";
		}
	}

	/** 
	 * Returns all active rows from the db_config table that match the criteria ''.
	 */
	@Transactional
	public List<DbConfig> findAllActive() throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE is_current='A' ORDER BY id", this);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<DbConfig> findByPersistableObject(Long id) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<DbConfig> findWhereIdEquals(Long id) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'name = :name'.
	 */
	@Transactional
	public List<DbConfig> findWhereNameEquals(String name) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE name = ? ORDER BY name", this,name);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'driver_class = :driverClass'.
	 */
	@Transactional
	public List<DbConfig> findWhereDriverClassEquals(String driverClass) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE driver_class = ? ORDER BY driver_class", this,driverClass);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'driver_url = :driverUrl'.
	 */
	@Transactional
	public List<DbConfig> findWhereDriverUrlEquals(String driverUrl) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE driver_url = ? ORDER BY driver_url", this,driverUrl);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'username = :username'.
	 */
	@Transactional
	public List<DbConfig> findWhereUsernameEquals(String username) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE username = ? ORDER BY username", this,username);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'password = :password'.
	 */
	@Transactional
	public List<DbConfig> findWherePasswordEquals(String password) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE password = ? ORDER BY password", this,password);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the db_config table that match the criteria 'test_query = :testQuery'.
	 */
	@Transactional
	public List<DbConfig> findWhereTestQueryEquals(String testQuery) throws DbConfigDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE test_query = ? ORDER BY test_query", this,testQuery);
		}
		catch (Exception e) {
			throw new DbConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
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
