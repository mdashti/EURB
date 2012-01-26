package com.sharifpro.eurb.management.mapping.dao;

import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.exception.DbConfigDaoException;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
import com.sharifpro.eurb.management.mapping.model.DbConfigPk;

import java.util.List;

public interface DbConfigDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return DbConfigPk
	 */
	public DbConfigPk insert(DbConfig dto);

	/** 
	 * Updates a single row in the db_config table.
	 */
	public void update(DbConfigPk pk, DbConfig dto) throws DbConfigDaoException;

	/** 
	 * Deletes a single row in the db_config table.
	 */
	public void delete(DbConfigPk pk) throws DbConfigDaoException;

	/** 
	 * Returns all rows from the db_config table that match the criteria 'id = :id'.
	 */
	public DbConfig findByPrimaryKey(Long id) throws DbConfigDaoException;

	/** 
	 * Returns all rows from the db_config table that match the criteria ''.
	 */
	public List<DbConfig> findAll() throws DbConfigDaoException;

	/** 
	 * Returns all rows from the db_config table that match the criteria 'id = :id'.
	 */
	public List<DbConfig> findByPersistableObject(Long id) throws DbConfigDaoException;

	/** 
	 * Returns all rows from the db_config table that match the criteria 'id = :id'.
	 */
	public List<DbConfig> findWhereIdEquals(Long id) throws DbConfigDaoException;

	/** 
	 * Returns all rows from the db_config table that match the criteria 'name = :name'.
	 */
	public List<DbConfig> findWhereNameEquals(String name) throws DbConfigDaoException;

	/** 
	 * Returns all rows from the db_config table that match the criteria 'driver_class = :driverClass'.
	 */
	public List<DbConfig> findWhereDriverClassEquals(String driverClass) throws DbConfigDaoException;

	/** 
	 * Returns all rows from the db_config table that match the criteria 'driver_url = :driverUrl'.
	 */
	public List<DbConfig> findWhereDriverUrlEquals(String driverUrl) throws DbConfigDaoException;

	/** 
	 * Returns all rows from the db_config table that match the criteria 'username = :username'.
	 */
	public List<DbConfig> findWhereUsernameEquals(String username) throws DbConfigDaoException;

	/** 
	 * Returns all rows from the db_config table that match the criteria 'password = :password'.
	 */
	public List<DbConfig> findWherePasswordEquals(String password) throws DbConfigDaoException;

	/** 
	 * Returns all rows from the db_config table that match the criteria 'test_query = :testQuery'.
	 */
	public List<DbConfig> findWhereTestQueryEquals(String testQuery) throws DbConfigDaoException;

	/** 
	 * Returns the rows from the db_config table that matches the specified primary-key value.
	 */
	public DbConfig findByPrimaryKey(DbConfigPk pk) throws DbConfigDaoException;

}
