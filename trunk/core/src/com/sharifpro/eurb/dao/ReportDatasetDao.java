package com.sharifpro.eurb.dao;

import com.sharifpro.eurb.dao.ReportDatasetDao;
import com.sharifpro.eurb.dto.ReportDataset;
import com.sharifpro.eurb.dto.ReportDatasetPk;
import com.sharifpro.eurb.exceptions.ReportDatasetDaoException;
import java.util.List;

public interface ReportDatasetDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportDatasetPk
	 */
	public ReportDatasetPk insert(ReportDataset dto);

	/** 
	 * Updates a single row in the report_dataset table.
	 */
	public void update(ReportDatasetPk pk, ReportDataset dto) throws ReportDatasetDaoException;

	/** 
	 * Deletes a single row in the report_dataset table.
	 */
	public void delete(ReportDatasetPk pk) throws ReportDatasetDaoException;

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'id = :id AND design_id = :designId AND design_version_id = :designVersionId'.
	 */
	public ReportDataset findByPrimaryKey(Long id, Long designId, Long designVersionId) throws ReportDatasetDaoException;

	/** 
	 * Returns all rows from the report_dataset table that match the criteria ''.
	 */
	public List<ReportDataset> findAll() throws ReportDatasetDaoException;

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'id = :id'.
	 */
	public List<ReportDataset> findByPersistableObject(Long id) throws ReportDatasetDaoException;

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'base_report_id = :baseReportId AND base_report_version_id = :baseReportVersionId'.
	 */
	public List<ReportDataset> findByReportDesign(Long baseReportId, Long baseReportVersionId) throws ReportDatasetDaoException;

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'design_id = :designId AND design_version_id = :designVersionId'.
	 */
	public List<ReportDataset> findByReportDesign2(Long designId, Long designVersionId) throws ReportDatasetDaoException;

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'table_mapping_id = :tableMappingId'.
	 */
	public List<ReportDataset> findByTableMapping(Long tableMappingId) throws ReportDatasetDaoException;

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'id = :id'.
	 */
	public List<ReportDataset> findWhereIdEquals(Long id) throws ReportDatasetDaoException;

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'design_id = :designId'.
	 */
	public List<ReportDataset> findWhereDesignIdEquals(Long designId) throws ReportDatasetDaoException;

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'design_version_id = :designVersionId'.
	 */
	public List<ReportDataset> findWhereDesignVersionIdEquals(Long designVersionId) throws ReportDatasetDaoException;

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'table_mapping_id = :tableMappingId'.
	 */
	public List<ReportDataset> findWhereTableMappingIdEquals(Long tableMappingId) throws ReportDatasetDaoException;

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'base_report_id = :baseReportId'.
	 */
	public List<ReportDataset> findWhereBaseReportIdEquals(Long baseReportId) throws ReportDatasetDaoException;

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'base_report_version_id = :baseReportVersionId'.
	 */
	public List<ReportDataset> findWhereBaseReportVersionIdEquals(Long baseReportVersionId) throws ReportDatasetDaoException;

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'ds_order = :dsOrder'.
	 */
	public List<ReportDataset> findWhereDsOrderEquals(Integer dsOrder) throws ReportDatasetDaoException;

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'operator = :operator'.
	 */
	public List<ReportDataset> findWhereOperatorEquals(Integer operator) throws ReportDatasetDaoException;

	/** 
	 * Returns the rows from the report_dataset table that matches the specified primary-key value.
	 */
	public ReportDataset findByPrimaryKey(ReportDatasetPk pk) throws ReportDatasetDaoException;

}
