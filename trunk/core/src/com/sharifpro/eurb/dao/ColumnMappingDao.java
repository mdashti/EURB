package com.sharifpro.eurb.dao;

import com.sharifpro.eurb.dao.ColumnMappingDao;
import com.sharifpro.eurb.dto.ColumnMapping;
import com.sharifpro.eurb.dto.ColumnMappingPk;
import com.sharifpro.eurb.exceptions.ColumnMappingDaoException;
import java.util.List;

public interface ColumnMappingDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ColumnMappingPk
	 */
	public ColumnMappingPk insert(ColumnMapping dto);

	/** 
	 * Updates a single row in the column_mapping table.
	 */
	public void update(ColumnMappingPk pk, ColumnMapping dto) throws ColumnMappingDaoException;

	/** 
	 * Deletes a single row in the column_mapping table.
	 */
	public void delete(ColumnMappingPk pk) throws ColumnMappingDaoException;

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'id = :id'.
	 */
	public ColumnMapping findByPrimaryKey(Long id) throws ColumnMappingDaoException;

	/** 
	 * Returns all rows from the column_mapping table that match the criteria ''.
	 */
	public List<ColumnMapping> findAll() throws ColumnMappingDaoException;

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'id = :id'.
	 */
	public List<ColumnMapping> findByPersistableObject(Long id) throws ColumnMappingDaoException;

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'table_mapping_id = :tableMappingId'.
	 */
	public List<ColumnMapping> findByTableMapping(Long tableMappingId) throws ColumnMappingDaoException;

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'id = :id'.
	 */
	public List<ColumnMapping> findWhereIdEquals(Long id) throws ColumnMappingDaoException;

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'table_mapping_id = :tableMappingId'.
	 */
	public List<ColumnMapping> findWhereTableMappingIdEquals(Long tableMappingId) throws ColumnMappingDaoException;

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'column_name = :columnName'.
	 */
	public List<ColumnMapping> findWhereColumnNameEquals(String columnName) throws ColumnMappingDaoException;

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'mapped_name = :mappedName'.
	 */
	public List<ColumnMapping> findWhereMappedNameEquals(String mappedName) throws ColumnMappingDaoException;

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'col_type = :colType'.
	 */
	public List<ColumnMapping> findWhereColTypeEquals(String colType) throws ColumnMappingDaoException;

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'col_order = :colOrder'.
	 */
	public List<ColumnMapping> findWhereColOrderEquals(String colOrder) throws ColumnMappingDaoException;

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'format_pattern = :formatPattern'.
	 */
	public List<ColumnMapping> findWhereFormatPatternEquals(String formatPattern) throws ColumnMappingDaoException;

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'static_mapping = :staticMapping'.
	 */
	public List<ColumnMapping> findWhereStaticMappingEquals(String staticMapping) throws ColumnMappingDaoException;

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'referenced_table = :referencedTable'.
	 */
	public List<ColumnMapping> findWhereReferencedTableEquals(String referencedTable) throws ColumnMappingDaoException;

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'referenced_id_col = :referencedIdCol'.
	 */
	public List<ColumnMapping> findWhereReferencedIdColEquals(String referencedIdCol) throws ColumnMappingDaoException;

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'referenced_value_col = :referencedValueCol'.
	 */
	public List<ColumnMapping> findWhereReferencedValueColEquals(String referencedValueCol) throws ColumnMappingDaoException;

	/** 
	 * Returns the rows from the column_mapping table that matches the specified primary-key value.
	 */
	public ColumnMapping findByPrimaryKey(ColumnMappingPk pk) throws ColumnMappingDaoException;

}
