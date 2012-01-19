package com.sharifpro.eurb.dao.spring;

import com.sharifpro.eurb.dao.ColumnMappingDao;
import com.sharifpro.eurb.dto.ColumnMapping;
import com.sharifpro.eurb.dto.ColumnMappingPk;
import com.sharifpro.eurb.exceptions.ColumnMappingDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class ColumnMappingDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ColumnMapping>, ColumnMappingDao
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
	 * @return ColumnMappingPk
	 */
	@Transactional
	public ColumnMappingPk insert(ColumnMapping dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, table_mapping_id, column_name, mapped_name, type, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getId(),dto.getTableMappingId(),dto.getColumnName(),dto.getMappedName(),dto.getType(),dto.getFormatPattern(),dto.getStaticMapping(),dto.getReferencedTable(),dto.getReferencedIdCol(),dto.getReferencedValueCol());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the column_mapping table.
	 */
	@Transactional
	public void update(ColumnMappingPk pk, ColumnMapping dto) throws ColumnMappingDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET id = ?, table_mapping_id = ?, column_name = ?, mapped_name = ?, type = ?, format_pattern = ?, static_mapping = ?, referenced_table = ?, referenced_id_col = ?, referenced_value_col = ? WHERE id = ?",dto.getId(),dto.getTableMappingId(),dto.getColumnName(),dto.getMappedName(),dto.getType(),dto.getFormatPattern(),dto.getStaticMapping(),dto.getReferencedTable(),dto.getReferencedIdCol(),dto.getReferencedValueCol(),pk.getId());
	}

	/** 
	 * Deletes a single row in the column_mapping table.
	 */
	@Transactional
	public void delete(ColumnMappingPk pk) throws ColumnMappingDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return ColumnMapping
	 */
	public ColumnMapping mapRow(ResultSet rs, int row) throws SQLException
	{
		ColumnMapping dto = new ColumnMapping();
		dto.setId( new Long( rs.getLong(1) ) );
		dto.setTableMappingId( new Long( rs.getLong(2) ) );
		dto.setColumnName( rs.getString( 3 ) );
		dto.setMappedName( rs.getString( 4 ) );
		dto.setType( rs.getString( 5 ) );
		dto.setFormatPattern( rs.getString( 6 ) );
		dto.setStaticMapping( rs.getString( 7 ) );
		dto.setReferencedTable( rs.getString( 8 ) );
		dto.setReferencedIdCol( rs.getString( 9 ) );
		dto.setReferencedValueCol( rs.getString( 10 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "column_mapping";
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'id = :id'.
	 */
	@Transactional
	public ColumnMapping findByPrimaryKey(Long id) throws ColumnMappingDaoException
	{
		try {
			List<ColumnMapping> list = jdbcTemplate.query("SELECT id, table_mapping_id, column_name, mapped_name, type, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col FROM " + getTableName() + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria ''.
	 */
	@Transactional
	public List<ColumnMapping> findAll() throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, table_mapping_id, column_name, mapped_name, type, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col FROM " + getTableName() + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ColumnMapping> findByPersistableObject(Long id) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, table_mapping_id, column_name, mapped_name, type, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'table_mapping_id = :tableMappingId'.
	 */
	@Transactional
	public List<ColumnMapping> findByTableMapping(Long tableMappingId) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, table_mapping_id, column_name, mapped_name, type, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col FROM " + getTableName() + " WHERE table_mapping_id = ?", this,tableMappingId);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereIdEquals(Long id) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, table_mapping_id, column_name, mapped_name, type, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'table_mapping_id = :tableMappingId'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereTableMappingIdEquals(Long tableMappingId) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, table_mapping_id, column_name, mapped_name, type, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col FROM " + getTableName() + " WHERE table_mapping_id = ? ORDER BY table_mapping_id", this,tableMappingId);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'column_name = :columnName'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereColumnNameEquals(String columnName) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, table_mapping_id, column_name, mapped_name, type, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col FROM " + getTableName() + " WHERE column_name = ? ORDER BY column_name", this,columnName);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'mapped_name = :mappedName'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereMappedNameEquals(String mappedName) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, table_mapping_id, column_name, mapped_name, type, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col FROM " + getTableName() + " WHERE mapped_name = ? ORDER BY mapped_name", this,mappedName);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'type = :type'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereTypeEquals(String type) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, table_mapping_id, column_name, mapped_name, type, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col FROM " + getTableName() + " WHERE type = ? ORDER BY type", this,type);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'format_pattern = :formatPattern'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereFormatPatternEquals(String formatPattern) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, table_mapping_id, column_name, mapped_name, type, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col FROM " + getTableName() + " WHERE format_pattern = ? ORDER BY format_pattern", this,formatPattern);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'static_mapping = :staticMapping'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereStaticMappingEquals(String staticMapping) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, table_mapping_id, column_name, mapped_name, type, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col FROM " + getTableName() + " WHERE static_mapping = ? ORDER BY static_mapping", this,staticMapping);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'referenced_table = :referencedTable'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereReferencedTableEquals(String referencedTable) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, table_mapping_id, column_name, mapped_name, type, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col FROM " + getTableName() + " WHERE referenced_table = ? ORDER BY referenced_table", this,referencedTable);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'referenced_id_col = :referencedIdCol'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereReferencedIdColEquals(String referencedIdCol) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, table_mapping_id, column_name, mapped_name, type, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col FROM " + getTableName() + " WHERE referenced_id_col = ? ORDER BY referenced_id_col", this,referencedIdCol);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'referenced_value_col = :referencedValueCol'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereReferencedValueColEquals(String referencedValueCol) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, table_mapping_id, column_name, mapped_name, type, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col FROM " + getTableName() + " WHERE referenced_value_col = ? ORDER BY referenced_value_col", this,referencedValueCol);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the column_mapping table that matches the specified primary-key value.
	 */
	public ColumnMapping findByPrimaryKey(ColumnMappingPk pk) throws ColumnMappingDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

}
