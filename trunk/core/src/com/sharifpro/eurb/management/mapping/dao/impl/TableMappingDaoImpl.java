package com.sharifpro.eurb.management.mapping.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.mapping.dao.TableMappingDao;
import com.sharifpro.eurb.management.mapping.exception.TableMappingDaoException;
import com.sharifpro.eurb.management.mapping.model.TableMapping;
import com.sharifpro.eurb.management.mapping.model.TableMappingPk;
import com.sharifpro.util.PropertyProvider;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TableMappingDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TableMapping>, TableMappingDao
{
	private final static String QUERY_FROM_COLUMNS = "o.db_config_id, o.table_name, o.mapped_name, o.mapped_type, o.active_for_manager, o.active_for_user";

	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TableMappingPk
	 */
	@Transactional
	public TableMappingPk insert(TableMapping dto)
	{
		TableMappingPk pk = new TableMappingPk();
		DaoFactory.createPersistableObjectDao().insert(dto, pk);
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, db_config_id, table_name, mapped_name, mapped_type, active_for_manager, active_for_user ) VALUES ( ?, ?, ?, ?, ?, ?, ? )",dto.getId(),dto.getDbConfigId(),dto.getTableName(),dto.getMappedName(),dto.getMappedType(),dto.isActiveForManager(),dto.isActiveForUser());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the table_mapping table.
	 */
	@Transactional
	public void update(TableMappingPk pk, TableMapping dto) throws TableMappingDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk, dto);
		jdbcTemplate.update("UPDATE " + getTableName() + " SET db_config_id = ?, table_name = ?, mapped_name = ?, mapped_type = ?, active_for_manager = ?, active_for_user = ? WHERE id = ?",dto.getDbConfigId(),dto.getTableName(),dto.getMappedName(),dto.getMappedType(),dto.isActiveForManager(),dto.isActiveForUser(),pk.getId());
	}

	/** 
	 * Deletes a single row in the table_mapping table.
	 */
	@Transactional
	public void delete(TableMappingPk pk) throws TableMappingDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
		DaoFactory.createPersistableObjectDao().delete(pk);
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return TableMapping
	 */
	public TableMapping mapRow(ResultSet rs, int row) throws SQLException
	{
		TableMapping dto = new TableMapping();
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setDbConfigId( new Long( rs.getLong(++i) ) );
		dto.setTableName( rs.getString( ++i ) );
		dto.setMappedName( rs.getString( ++i ) );
		dto.setMappedType( new Integer( rs.getInt(++i) ) );
		dto.setActiveForManager( rs.getBoolean( ++i ) );
		dto.setActiveForUser( rs.getBoolean( ++i ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "table_mapping";
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'id = :id'.
	 */
	@Transactional
	public TableMapping findByPrimaryKey(Long id) throws TableMappingDaoException
	{
		try {
			List<TableMapping> list = jdbcTemplate.query(QUERY_SELECT_PART + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TableMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria ''.
	 */
	@Transactional
	public List<TableMapping> findAll() throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new TableMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<TableMapping> findByPersistableObject(Long id) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new TableMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'db_config_id = :dbConfigId'.
	 */
	@Transactional
	public List<TableMapping> findByDbConfig(Long dbConfigId) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE db_config_id = ?", this,dbConfigId);
		}
		catch (Exception e) {
			throw new TableMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<TableMapping> findWhereIdEquals(Long id) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new TableMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'db_config_id = :dbConfigId'.
	 */
	@Transactional
	public List<TableMapping> findWhereDbConfigIdEquals(Long dbConfigId) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE db_config_id = ? ORDER BY db_config_id", this,dbConfigId);
		}
		catch (Exception e) {
			throw new TableMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'table_name = :tableName'.
	 */
	@Transactional
	public List<TableMapping> findWhereTableNameEquals(String tableName) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE table_name = ? ORDER BY table_name", this,tableName);
		}
		catch (Exception e) {
			throw new TableMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'mapped_name = :mappedName'.
	 */
	@Transactional
	public List<TableMapping> findWhereMappedNameEquals(String mappedName) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE mapped_name = ? ORDER BY mapped_name", this,mappedName);
		}
		catch (Exception e) {
			throw new TableMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'mapped_type = :mappedType'.
	 */
	@Transactional
	public List<TableMapping> findWhereMappedTypeEquals(Integer mappedType) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE mapped_type = ? ORDER BY mapped_type", this,mappedType);
		}
		catch (Exception e) {
			throw new TableMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'active_for_manager = :activeForManager'.
	 */
	@Transactional
	public List<TableMapping> findWhereActiveForManagerEquals(Short activeForManager) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE active_for_manager = ? ORDER BY active_for_manager", this,activeForManager);
		}
		catch (Exception e) {
			throw new TableMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'active_for_user = :activeForUser'.
	 */
	@Transactional
	public List<TableMapping> findWhereActiveForUserEquals(Short activeForUser) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE active_for_user = ? ORDER BY active_for_user", this,activeForUser);
		}
		catch (Exception e) {
			throw new TableMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns the rows from the table_mapping table that matches the specified primary-key value.
	 */
	public TableMapping findByPrimaryKey(TableMappingPk pk) throws TableMappingDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

}
