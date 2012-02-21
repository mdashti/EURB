package com.sharifpro.eurb.management.mapping.dao;

import com.sharifpro.eurb.management.mapping.dao.TableMappingDao;
import com.sharifpro.eurb.management.mapping.exception.TableMappingDaoException;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
import com.sharifpro.eurb.management.mapping.model.TableMapping;
import com.sharifpro.eurb.management.mapping.model.TableMappingPk;

import java.util.List;

public interface TableMappingDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return TableMappingPk
	 */
	public TableMappingPk insert(TableMapping dto);

	/** 
	 * Updates a single row in the table_mapping table.
	 */
	public void update(TableMappingPk pk, TableMapping dto) throws TableMappingDaoException;

	/** 
	 * Deletes a single row in the table_mapping table.
	 */
	public void delete(TableMappingPk pk) throws TableMappingDaoException;

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'id = :id'.
	 */
	public TableMapping findByPrimaryKey(Long id) throws TableMappingDaoException;

	/** 
	 * Returns all rows from the table_mapping table that match the criteria ''.
	 */
	public List<TableMapping> findAll() throws TableMappingDaoException;
	
	/** 
	 * Counts all rows from the table_mapping table that match the criteria ''.
	 */
	public int countAll() throws TableMappingDaoException;
	
	/** 
	 * Returns all rows from the table_mapping table that match the like query in onFields fields.
	 */
	public List<TableMapping> findAll(String query, List<String> onFields) throws TableMappingDaoException;

	/** 
	 * Counts all rows from the table_mapping table that match the like query in onFields fields.
	 */
	public int countAll(String query, List<String> onFields) throws TableMappingDaoException;


	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'id = :id'.
	 */
	public List<TableMapping> findByPersistableObject(Long id) throws TableMappingDaoException;

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'db_config_id = :dbConfigId'.
	 */
	public List<TableMapping> findByDbConfig(Long dbConfigId) throws TableMappingDaoException;

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'id = :id'.
	 */
	public List<TableMapping> findWhereIdEquals(Long id) throws TableMappingDaoException;

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'db_config_id = :dbConfigId'.
	 */
	public List<TableMapping> findWhereDbConfigIdEquals(Long dbConfigId) throws TableMappingDaoException;

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'table_name = :tableName'.
	 */
	public List<TableMapping> findWhereTableNameEquals(String tableName) throws TableMappingDaoException;

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'mapped_name = :mappedName'.
	 */
	public List<TableMapping> findWhereMappedNameEquals(String mappedName) throws TableMappingDaoException;

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'mapped_type = :mappedType'.
	 */
	public List<TableMapping> findWhereMappedTypeEquals(Integer mappedType) throws TableMappingDaoException;

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'active_for_manager = :activeForManager'.
	 */
	public List<TableMapping> findWhereActiveForManagerEquals(Short activeForManager) throws TableMappingDaoException;

	/** 
	 * Returns all rows from the table_mapping table that match the criteria 'active_for_user = :activeForUser'.
	 */
	public List<TableMapping> findWhereActiveForUserEquals(Short activeForUser) throws TableMappingDaoException;

	/** 
	 * Returns the rows from the table_mapping table that matches the specified primary-key value.
	 */
	public TableMapping findByPrimaryKey(TableMappingPk pk) throws TableMappingDaoException;

	public void deleteAll(List<TableMappingPk> pkList) throws TableMappingDaoException;

	public void activateAll(List<TableMappingPk> pkList, String target) throws TableMappingDaoException;

	public void deactivateAll(List<TableMappingPk> pkList, String target) throws TableMappingDaoException;

	public List<TableMapping> findAll(DbConfig dbConf, String query, List<String> onFields) throws TableMappingDaoException;

	public List<TableMapping> findAll(DbConfig dbConf) throws TableMappingDaoException;

}
