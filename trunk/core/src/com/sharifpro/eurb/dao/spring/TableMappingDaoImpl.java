package com.sharifpro.eurb.dao.spring;

import com.sharifpro.eurb.dao.TableMappingDao;
import com.sharifpro.eurb.dto.TableMapping;
import com.sharifpro.eurb.dto.TableMappingPk;
import com.sharifpro.eurb.exceptions.TableMappingDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TableMappingDaoImpl extends AbstractDAO implements ParameterizedRowMapper<TableMapping>, TableMappingDao
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
	 * @return TableMappingPk
	 */
	@Transactional
	public TableMappingPk insert(TableMapping dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, db_config_id, table_name, mapped_name, mapped_type, active_for_manager, active_for_user ) VALUES ( ?, ?, ?, ?, ?, ?, ? )",dto.getId(),dto.getDbConfigId(),dto.getTableName(),dto.getMappedName(),dto.getMappedType(),dto.isActiveForManager(),dto.isActiveForUser());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the table_mapping table.
	 */
	@Transactional
	public void update(TableMappingPk pk, TableMapping dto) throws TableMappingDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET id = ?, db_config_id = ?, table_name = ?, mapped_name = ?, mapped_type = ?, active_for_manager = ?, active_for_user = ? WHERE id = ?",dto.getId(),dto.getDbConfigId(),dto.getTableName(),dto.getMappedName(),dto.getMappedType(),dto.isActiveForManager(),dto.isActiveForUser(),pk.getId());
	}

	/** 
	 * Deletes a single row in the table_mapping table.
	 */
	@Transactional
	public void delete(TableMappingPk pk) throws TableMappingDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
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
		dto.setId( new Long( rs.getLong(1) ) );
		dto.setDbConfigId( new Long( rs.getLong(2) ) );
		dto.setTableName( rs.getString( 3 ) );
		dto.setMappedName( rs.getString( 4 ) );
		dto.setMappedType( new Integer( rs.getInt(5) ) );
		dto.setActiveForManager( rs.getBoolean( 6 ) );
		dto.setActiveForUser( rs.getBoolean( 7 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
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
			List<TableMapping> list = jdbcTemplate.query("SELECT id, db_config_id, table_name, mapped_name, mapped_type, active_for_manager, active_for_user FROM " + getTableName() + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new TableMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria ''.
	 */
	@Transactional
	public List<TableMapping> findAll() throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, db_config_id, table_name, mapped_name, mapped_type, active_for_manager, active_for_user FROM " + getTableName() + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new TableMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<TableMapping> findByPersistableObject(Long id) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, db_config_id, table_name, mapped_name, mapped_type, active_for_manager, active_for_user FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new TableMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'db_config_id = :dbConfigId'.
	 */
	@Transactional
	public List<TableMapping> findByDbConfig(Long dbConfigId) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, db_config_id, table_name, mapped_name, mapped_type, active_for_manager, active_for_user FROM " + getTableName() + " WHERE db_config_id = ?", this,dbConfigId);
		}
		catch (Exception e) {
			throw new TableMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<TableMapping> findWhereIdEquals(Long id) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, db_config_id, table_name, mapped_name, mapped_type, active_for_manager, active_for_user FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new TableMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'db_config_id = :dbConfigId'.
	 */
	@Transactional
	public List<TableMapping> findWhereDbConfigIdEquals(Long dbConfigId) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, db_config_id, table_name, mapped_name, mapped_type, active_for_manager, active_for_user FROM " + getTableName() + " WHERE db_config_id = ? ORDER BY db_config_id", this,dbConfigId);
		}
		catch (Exception e) {
			throw new TableMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'table_name = :tableName'.
	 */
	@Transactional
	public List<TableMapping> findWhereTableNameEquals(String tableName) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, db_config_id, table_name, mapped_name, mapped_type, active_for_manager, active_for_user FROM " + getTableName() + " WHERE table_name = ? ORDER BY table_name", this,tableName);
		}
		catch (Exception e) {
			throw new TableMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'mapped_name = :mappedName'.
	 */
	@Transactional
	public List<TableMapping> findWhereMappedNameEquals(String mappedName) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, db_config_id, table_name, mapped_name, mapped_type, active_for_manager, active_for_user FROM " + getTableName() + " WHERE mapped_name = ? ORDER BY mapped_name", this,mappedName);
		}
		catch (Exception e) {
			throw new TableMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'mapped_type = :mappedType'.
	 */
	@Transactional
	public List<TableMapping> findWhereMappedTypeEquals(Integer mappedType) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, db_config_id, table_name, mapped_name, mapped_type, active_for_manager, active_for_user FROM " + getTableName() + " WHERE mapped_type = ? ORDER BY mapped_type", this,mappedType);
		}
		catch (Exception e) {
			throw new TableMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'active_for_manager = :activeForManager'.
	 */
	@Transactional
	public List<TableMapping> findWhereActiveForManagerEquals(Short activeForManager) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, db_config_id, table_name, mapped_name, mapped_type, active_for_manager, active_for_user FROM " + getTableName() + " WHERE active_for_manager = ? ORDER BY active_for_manager", this,activeForManager);
		}
		catch (Exception e) {
			throw new TableMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'active_for_user = :activeForUser'.
	 */
	@Transactional
	public List<TableMapping> findWhereActiveForUserEquals(Short activeForUser) throws TableMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, db_config_id, table_name, mapped_name, mapped_type, active_for_manager, active_for_user FROM " + getTableName() + " WHERE active_for_user = ? ORDER BY active_for_user", this,activeForUser);
		}
		catch (Exception e) {
			throw new TableMappingDaoException("Query failed", e);
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
