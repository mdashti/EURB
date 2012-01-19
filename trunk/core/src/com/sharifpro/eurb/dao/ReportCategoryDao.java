package com.sharifpro.eurb.dao;

import com.sharifpro.eurb.dao.ReportCategoryDao;
import com.sharifpro.eurb.dto.ReportCategory;
import com.sharifpro.eurb.dto.ReportCategoryPk;
import com.sharifpro.eurb.exceptions.ReportCategoryDaoException;
import java.util.List;

public interface ReportCategoryDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportCategoryPk
	 */
	public ReportCategoryPk insert(ReportCategory dto);

	/** 
	 * Updates a single row in the report_category table.
	 */
	public void update(ReportCategoryPk pk, ReportCategory dto) throws ReportCategoryDaoException;

	/** 
	 * Deletes a single row in the report_category table.
	 */
	public void delete(ReportCategoryPk pk) throws ReportCategoryDaoException;

	/** 
	 * Returns all rows from the report_category table that match the criteria 'id = :id'.
	 */
	public ReportCategory findByPrimaryKey(Long id) throws ReportCategoryDaoException;

	/** 
	 * Returns all rows from the report_category table that match the criteria ''.
	 */
	public List<ReportCategory> findAll() throws ReportCategoryDaoException;

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
	 * Returns all rows from the report_category table that match the criteria 'description = :description'.
	 */
	public List<ReportCategory> findWhereDescriptionEquals(String description) throws ReportCategoryDaoException;

	/** 
	 * Returns the rows from the report_category table that matches the specified primary-key value.
	 */
	public ReportCategory findByPrimaryKey(ReportCategoryPk pk) throws ReportCategoryDaoException;

}
