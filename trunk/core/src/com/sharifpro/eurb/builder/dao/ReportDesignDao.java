package com.sharifpro.eurb.builder.dao;

import com.sharifpro.eurb.builder.dao.ReportDesignDao;
import com.sharifpro.eurb.builder.exception.ReportDesignDaoException;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.builder.model.ReportDesignPk;
import java.util.List;

public interface ReportDesignDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportDesignPk
	 */
	public ReportDesignPk insert(ReportDesign dto);

	/** 
	 * Updates a single row in the report_design table.
	 */
	public void update(ReportDesignPk pk, ReportDesign dto) throws ReportDesignDaoException;

	/** 
	 * Deletes a single row in the report_design table.
	 */
	public void delete(ReportDesignPk pk, ReportDesign dto) throws ReportDesignDaoException;

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
	 * Returns all rows from the report_design table that match the criteria 'id = :id'.
	 */
	public List<ReportDesign> findByPersistableObject(Long id) throws ReportDesignDaoException;

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
	public List<ReportDesign> findWhereVersionIdEquals(Long versionId) throws ReportDesignDaoException;

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
	 * Returns all rows from the report_design table that match the criteria 'is_current = :isCurrent'.
	 */
	public List<ReportDesign> findWhereIsCurrentEquals(Short isCurrent) throws ReportDesignDaoException;

	/** 
	 * Returns all rows from the report_design table that match the criteria 'record_status = :recordStatus'.
	 */
	public List<ReportDesign> findWhereRecordStatusEquals(String recordStatus) throws ReportDesignDaoException;

	/** 
	 * Returns the rows from the report_design table that matches the specified primary-key value.
	 */
	public ReportDesign findByPrimaryKey(ReportDesignPk pk) throws ReportDesignDaoException;

}
