package com.sharifpro.eurb.builder.dao;

import java.util.List;

import com.sharifpro.eurb.builder.exception.ReportDesignDaoException;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.model.ReportDesignPk;

public interface ReportDesignDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportDesignPk
	 */
	public ReportDesignPk insert(ReportDesign dto) throws ReportDesignDaoException;

	/** 
	 * Updates a single row in the report_design table.
	 */
	public void update(ReportDesignPk pk, ReportDesign dto) throws ReportDesignDaoException;
	
	/** 
	 * Updates a single row in the report_design table but does not make a new version.
	 */
	public void updateCurrentVersion(ReportDesignPk pk, ReportDesign dto) throws ReportDesignDaoException;


	/** 
	 * Deletes a single row in the report_design table.
	 */
	public void delete(ReportDesignPk pk) throws ReportDesignDaoException;
	
	/**
	 * Deletes multiple given rows form the report_design table.
	 */
	public void deleteAll(List<ReportDesignPk> pkList) throws ReportDesignDaoException;
	
	/**
	 * Activates a single row in database
	 */
	public void activate(ReportDesignPk pk) throws ReportDesignDaoException;
	
	/**
	 * Activates multiple given rows in the report_design table.
	 */
	public void activateAll(List<ReportDesignPk> pkList) throws ReportDesignDaoException;
	
	/**
	 * Deactivates a single row in database
	 */
	public void deactivate(ReportDesignPk pk) throws ReportDesignDaoException;
	
	/**
	 * Deactivates multiple given rows in the report_design table.
	 */
	public void deactivateAll(List<ReportDesignPk> pkList) throws ReportDesignDaoException;
	
	
	

	/** 
	 * Returns current row from the report_design table that match the criteria 'id = :id'.
	 */
	public ReportDesign findByPrimaryKey(Long id) throws ReportDesignDaoException;

	
	/** 
	 * Returns all rows from the report_design table that match the criteria 'id = :id, version_id = versionId'.
	 */
	public ReportDesign findByPrimaryKey(Long id, Long versionId) throws ReportDesignDaoException;

	/** 
	 * Returns all rows from the report_design table that match the criteria ''.
	 */
	public List<ReportDesign> findAll() throws ReportDesignDaoException;
	
	/** 
	 * Counts all rows from the report_design table that match the criteria ''.
	 */
	public int countAll() throws ReportDesignDaoException;
	
	/** 
	 * Returns all rows from the report_category table that match the criteria '' limited by start and limit.
	 */
	public List<ReportDesign> findAll(Integer start, Integer limit, String sort, String dir) throws ReportDesignDaoException;

	/** 
	 * Returns all rows from the report_category table that match the like query in onFields fields limited by start and limit.
	 */
	public List<ReportDesign> findAll(String query, List<String> onFields, Integer start, Integer limit, String sort, String dir) throws ReportDesignDaoException;

	
	/** 
	 * Counts all rows from the report_category table that match the like query in onFields fields.
	 */
	public int countAll(String query, List<String> onFields) throws ReportDesignDaoException;
	

	/** 
	 * Returns all active rows from the report_design table that match the criteria ''.
	 */
	public List<ReportDesign> findAllActive() throws ReportDesignDaoException;

	/** 
	 * Returns all rows from the report_design table that match the criteria 'id = :id'.
	 */
	public List<ReportDesign> findByPersistableObject(Long id) throws ReportDesignDaoException;

	/** 
	 * Returns all rows from the report_design table that match the criteria 'id = :id and version_id = :versionId'.
	 */
	public List<ReportDesign> findByPersistableObject(Long id, Long versionId) throws ReportDesignDaoException;

	/** 
	 * Returns all rows from the report_design table that match the criteria 'category_id = :categoryId'.
	 */
	public List<ReportDesign> findByReportCategory(Long categoryId) throws ReportDesignDaoException;

	/** 
	 * Returns all rows from the report_design table that match the criteria 'id = :id'.
	 */
	public List<ReportDesign> findWhereIdEquals(Long id) throws ReportDesignDaoException;

	/** 
	 * Returns all rows from the report_design table that match the criteria 'version_id = :versionId'.
	 */
	public List<ReportDesign> findWhereIdAndVersionIdEquals(Long id, Long versionId) throws ReportDesignDaoException;

	/** 
	 * Returns all rows from the report_design table that match the criteria 'name = :name'.
	 */
	public List<ReportDesign> findWhereNameEquals(String name) throws ReportDesignDaoException;

	/** 
	 * Returns all rows from the report_design table that match the criteria 'description = :description'.
	 */
	public List<ReportDesign> findWhereDescriptionEquals(String description) throws ReportDesignDaoException;

	/** 
	 * Returns all rows from the report_design table that match the criteria 'category_id = :categoryId'.
	 */
	public List<ReportDesign> findWhereCategoryIdEquals(Long categoryId) throws ReportDesignDaoException;

	/** 
	 * Returns all rows from the report_design table that match the criteria 'query_text = :queryText'.
	 */
	public List<ReportDesign> findWhereQueryTextEquals(String queryText) throws ReportDesignDaoException;

	/** 
	 * Returns all rows from the report_design table that match the criteria 'select_part = :selectPart'.
	 */
	public List<ReportDesign> findWhereSelectPartEquals(String selectPart) throws ReportDesignDaoException;

	/** 
	 * Returns all rows from the report_design table that match the criteria 'result_data = :resultData'.
	 */
	public List<ReportDesign> findWhereResultDataEquals(String resultData) throws ReportDesignDaoException;

	/** 
	 * Returns all rows from the report_design table that match the criteria 'format_file = :formatFile'.
	 */
	public List<ReportDesign> findWhereFormatFileEquals(String formatFile) throws ReportDesignDaoException;

	
	/** 
	 * Returns all rows from the report_design table that match the criteria 'record_status = :recordStatus'.
	 */
	public List<ReportDesign> findWhereRecordStatusEquals(String recordStatus) throws ReportDesignDaoException;

	/** 
	 * Returns the rows from the report_design table that matches the specified primary-key value.
	 */
	public ReportDesign findByPrimaryKey(ReportDesignPk pk) throws ReportDesignDaoException;

}
