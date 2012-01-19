package com.sharifpro.eurb.dao;

import com.sharifpro.eurb.dao.ReportFormatDao;
import com.sharifpro.eurb.dto.ReportFormat;
import com.sharifpro.eurb.dto.ReportFormatPk;
import com.sharifpro.eurb.exceptions.ReportFormatDaoException;
import java.util.List;

public interface ReportFormatDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportFormatPk
	 */
	public ReportFormatPk insert(ReportFormat dto);

	/** 
	 * Updates a single row in the report_format table.
	 */
	public void update(ReportFormatPk pk, ReportFormat dto) throws ReportFormatDaoException;

	/** 
	 * Deletes a single row in the report_format table.
	 */
	public void delete(ReportFormatPk pk) throws ReportFormatDaoException;

	/** 
	 * Returns all rows from the report_format table that match the criteria 'version_id = :versionId AND id = :id'.
	 */
	public ReportFormat findByPrimaryKey(Long versionId, Long id) throws ReportFormatDaoException;

	/** 
	 * Returns all rows from the report_format table that match the criteria ''.
	 */
	public List<ReportFormat> findAll() throws ReportFormatDaoException;

	/** 
	 * Returns all rows from the report_format table that match the criteria 'report_design_id = :reportDesignId AND report_design_version_id = :reportDesignVersionId'.
	 */
	public List<ReportFormat> findByReportDesign(Long reportDesignId, Long reportDesignVersionId) throws ReportFormatDaoException;

	/** 
	 * Returns all rows from the report_format table that match the criteria 'id = :id'.
	 */
	public List<ReportFormat> findByPersistableObject(Long id) throws ReportFormatDaoException;

	/** 
	 * Returns all rows from the report_format table that match the criteria 'id = :id'.
	 */
	public List<ReportFormat> findWhereIdEquals(Long id) throws ReportFormatDaoException;

	/** 
	 * Returns all rows from the report_format table that match the criteria 'version_id = :versionId'.
	 */
	public List<ReportFormat> findWhereVersionIdEquals(Long versionId) throws ReportFormatDaoException;

	/** 
	 * Returns all rows from the report_format table that match the criteria 'format_file = :formatFile'.
	 */
	public List<ReportFormat> findWhereFormatFileEquals(String formatFile) throws ReportFormatDaoException;

	/** 
	 * Returns all rows from the report_format table that match the criteria 'is_current = :isCurrent'.
	 */
	public List<ReportFormat> findWhereIsCurrentEquals(Short isCurrent) throws ReportFormatDaoException;

	/** 
	 * Returns all rows from the report_format table that match the criteria 'record_status = :recordStatus'.
	 */
	public List<ReportFormat> findWhereRecordStatusEquals(String recordStatus) throws ReportFormatDaoException;

	/** 
	 * Returns all rows from the report_format table that match the criteria 'report_design_id = :reportDesignId'.
	 */
	public List<ReportFormat> findWhereReportDesignIdEquals(Long reportDesignId) throws ReportFormatDaoException;

	/** 
	 * Returns all rows from the report_format table that match the criteria 'report_design_version_id = :reportDesignVersionId'.
	 */
	public List<ReportFormat> findWhereReportDesignVersionIdEquals(Long reportDesignVersionId) throws ReportFormatDaoException;

	/** 
	 * Returns the rows from the report_format table that matches the specified primary-key value.
	 */
	public ReportFormat findByPrimaryKey(ReportFormatPk pk) throws ReportFormatDaoException;

}
