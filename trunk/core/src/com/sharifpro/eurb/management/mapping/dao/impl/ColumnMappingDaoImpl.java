package com.sharifpro.eurb.management.mapping.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.mapping.dao.ColumnMappingDao;
import com.sharifpro.eurb.management.mapping.exception.ColumnMappingDaoException;
import com.sharifpro.eurb.management.mapping.model.ColumnMapping;
import com.sharifpro.eurb.management.mapping.model.ColumnMappingPk;
import com.sharifpro.util.PropertyProvider;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class ColumnMappingDaoImpl extends PersistableObjectDaoImpl implements ParameterizedRowMapper<ColumnMapping>, ColumnMappingDao
{
	private final static String QUERY_FROM_COLUMNS = "o.db_config_id, o.table_mapping_id, o.column_name, o.mapped_name, o.col_type_name, o.col_data_type, o.col_order, o.format_pattern, o.static_mapping, o.referenced_table, o.referenced_id_col, o.referenced_value_col";

	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ColumnMappingPk
	 */
	@Transactional
	public ColumnMappingPk insert(ColumnMapping dto)
	{
		ColumnMappingPk pk = new ColumnMappingPk();
		DaoFactory.createPersistableObjectDao().insert(dto, pk);
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, db_config_id, table_mapping_id, column_name, mapped_name, col_type_name, col_data_type, col_order, format_pattern, static_mapping, referenced_table, referenced_id_col, referenced_value_col ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",pk.getId(),dto.getDbConfigId(),dto.getTableMappingId(),dto.getColumnName(),dto.getMappedName(),dto.getColTypeName(),dto.getColDataType(),dto.getColOrder(),dto.getFormatPattern(),dto.getStaticMapping(),dto.getReferencedTable(),dto.getReferencedIdCol(),dto.getReferencedValueCol());
		return pk;
	}

	/** 
	 * Updates a single row in the column_mapping table.
	 */
	@Transactional
	public void update(ColumnMappingPk pk, ColumnMapping dto) throws ColumnMappingDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		jdbcTemplate.update("UPDATE " + getTableName() + " SET db_config_id = ?, table_mapping_id = ?, column_name = ?, mapped_name = ?, col_type_name = ?, col_data_type = ?, col_order = ?, format_pattern = ?, static_mapping = ?, referenced_table = ?, referenced_id_col = ?, referenced_value_col = ? WHERE id = ?",dto.getDbConfigId(),dto.getTableMappingId(),dto.getColumnName(),dto.getMappedName(),dto.getColTypeName(),dto.getColDataType(),dto.getColOrder(),dto.getFormatPattern(),dto.getStaticMapping(),dto.getReferencedTable(),dto.getReferencedIdCol(),dto.getReferencedValueCol(),pk.getId());
	}

	/** 
	 * Deletes a single row in the column_mapping table.
	 */
	@Transactional
	public void delete(ColumnMappingPk pk) throws ColumnMappingDaoException
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
	 * @return ColumnMapping
	 */
	public ColumnMapping mapRow(ResultSet rs, int row) throws SQLException
	{
		ColumnMapping dto = new ColumnMapping();
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setDbConfigId( new Long( rs.getLong(++i) ) );
		dto.setTableMappingId( new Long( rs.getLong(++i) ) );
		dto.setColumnName( rs.getString( ++i ) );
		dto.setMappedName( rs.getString( ++i ) );
		dto.setColTypeName( rs.getString( ++i ) );
		dto.setColDataType( rs.getInt( ++i ) );
		dto.setColOrder( rs.getString( ++i ) );
		dto.setFormatPattern( rs.getString( ++i ) );
		dto.setStaticMapping( rs.getString( ++i ) );
		dto.setReferencedTable( rs.getString( ++i ) );
		dto.setReferencedIdCol( rs.getString( ++i ) );
		dto.setReferencedValueCol( rs.getString( ++i ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
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
			List<ColumnMapping> list = jdbcTemplate.query(QUERY_SELECT_PART + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria ''.
	 */
	@Transactional
	public List<ColumnMapping> findAll() throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ColumnMapping> findByPersistableObject(Long id) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'table_mapping_id = :tableMappingId'.
	 */
	@Transactional
	public List<ColumnMapping> findByTableMapping(Long tableMappingId) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE table_mapping_id = ?", this,tableMappingId);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereIdEquals(Long id) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'table_mapping_id = :tableMappingId'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereTableMappingIdEquals(Long tableMappingId) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE table_mapping_id = ? ORDER BY table_mapping_id", this,tableMappingId);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'column_name = :columnName'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereColumnNameEquals(String columnName) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE column_name = ? ORDER BY column_name", this,columnName);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'mapped_name = :mappedName'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereMappedNameEquals(String mappedName) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE mapped_name = ? ORDER BY mapped_name", this,mappedName);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'col_type_name = :colTypeName'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereColTypeEquals(String colTypeName) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE col_type_name = ? ORDER BY col_type_name", this,colTypeName);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'col_data_type = :colDataType'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereColDataTypeEquals(int colDataType) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE col_data_type = ? ORDER BY col_data_type", this,colDataType);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'col_order = :colOrder'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereColOrderEquals(String colOrder) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE col_order = ? ORDER BY col_order", this,colOrder);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'format_pattern = :formatPattern'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereFormatPatternEquals(String formatPattern) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE format_pattern = ? ORDER BY format_pattern", this,formatPattern);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'static_mapping = :staticMapping'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereStaticMappingEquals(String staticMapping) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE static_mapping = ? ORDER BY static_mapping", this,staticMapping);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'referenced_table = :referencedTable'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereReferencedTableEquals(String referencedTable) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE referenced_table = ? ORDER BY referenced_table", this,referencedTable);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'referenced_id_col = :referencedIdCol'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereReferencedIdColEquals(String referencedIdCol) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE referenced_id_col = ? ORDER BY referenced_id_col", this,referencedIdCol);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'referenced_value_col = :referencedValueCol'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereReferencedValueColEquals(String referencedValueCol) throws ColumnMappingDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE referenced_value_col = ? ORDER BY referenced_value_col", this,referencedValueCol);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
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
