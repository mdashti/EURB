package com.sharifpro.eurb.builder.dao;

import java.util.List;

import com.sharifpro.eurb.builder.exception.ReportCategoryDaoException;
import com.sharifpro.eurb.builder.model.ReportCategory;
import com.sharifpro.eurb.builder.model.ReportCategoryPk;

public interface ReportCategoryDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportCategoryPk
	 */
	public ReportCategoryPk insert(ReportCategory dto) throws ReportCategoryDaoException;

	/** 
	 * Updates a single row in the report_category table.
	 */
	public void update(ReportCategoryPk pk, ReportCategory dto) throws ReportCategoryDaoException;

	/** 
	 * Deletes a single row in the report_category table.
	 */
	public void delete(ReportCategoryPk pk) throws ReportCategoryDaoException;
	
	/** 
	 * Deletes all given rows in the report_category table.
	 */
	public void deleteAll(List<ReportCategoryPk> pkList) throws ReportCategoryDaoException;


	/** 
	 * Returns all rows from the report_category table that match the criteria 'id = :id'.
	 */
	public ReportCategory findByPrimaryKey(Long id) throws ReportCategoryDaoException;

	/** 
	 * Returns all rows from the report_category table that match the criteria ''.
	 */
	public List<ReportCategory> findAll() throws ReportCategoryDaoException;
	
	/** 
	 * Counts all rows from the report_category table that match the criteria ''.
	 */
	public int countAll() throws ReportCategoryDaoException;
	
	/** 
	 * Returns all rows from the report_category table that match the criteria '' limited by start and limit.
	 */
	public List<ReportCategory> findAll(Integer start, Integer limit) throws ReportCategoryDaoException;
	
	/** 
	 * Returns all rows from the report_category table that match the like query in onFields fields limited by start and limit.
	 */
	public List<ReportCategory> findAll(String query, List<String> onFields, Integer start, Integer limit) throws ReportCategoryDaoException;

	
	/** 
	 * Counts all rows from the report_category table that match the like query in onFields fields.
	 */
	public int countAll(String query, List<String> onFields) throws ReportCategoryDaoException;



	/** 
	 * Returns all rows from the report_category table that match the criteria 'id = :id'.
	 */
	public List<ReportCategory> findByPersistableObject(Long id) throws ReportCategoryDaoException;

	/** 
	 * Returns all rows from the report_category table that match the criteria 'id = :id'.
	 */
	public List<ReportCategory> findWhereIdEquals(Long id) throws ReportCategoryDaoException;

	/** 
	 * Returns all rows from the report_category table that match the criteria 'name = :name'.
	 */
	public List<ReportCategory> findWhereNameEquals(String name) throws ReportCategoryDaoException;

	
	/** 
	 * Returns the rows from the report_category table that matches the specified primary-key value.
	 */
	public ReportCategory findByPrimaryKey(ReportCategoryPk pk) throws ReportCategoryDaoException;

}
