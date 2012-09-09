package com.sharifpro.eurb.builder.dao;

import com.sharifpro.eurb.builder.dao.ReportFilterDao;
import com.sharifpro.eurb.builder.exception.ReportFilterDaoException;
import com.sharifpro.eurb.builder.model.ReportDataset;
import com.sharifpro.eurb.builder.model.ReportFilter;
import com.sharifpro.eurb.builder.model.ReportFilterPk;
import java.util.List;

public interface ReportFilterDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportFilterPk
	 * @throws ReportFilterDaoException 
	 */
	public ReportFilterPk insert(ReportFilter dto) throws ReportFilterDaoException;

	/** 
	 * Updates a single row in the report_filter table.
	 */
	public void update(ReportFilterPk pk, ReportFilter dto) throws ReportFilterDaoException;

	/** 
	 * Deletes a single row in the report_filter table.
	 */
	public void delete(ReportFilterPk pk) throws ReportFilterDaoException;
	
	/** 
	 * Deletes all given rows in the report_filter table.
	 */
	public void deleteAll(List<ReportFilterPk> pkList) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'id = :id'.
	 */
	public ReportFilter findByPrimaryKey(Long id) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria ''.
	 */
	public List<ReportFilter> findAll() throws ReportFilterDaoException;
	
	/** 
	 * Returns all rows from the report_filter table that match the criteria 'report_design_id := reportDesignId'.
	 */
	public List<ReportFilter> findAll(Long reportDesignId, Long reportVersionId) throws ReportFilterDaoException;
	
	/** 
	 * Counts all rows from the report_filter table that match the criteria 'report_design_id := reportDesignId'.
	 */
	public int countAll(Long reportDesignId, Long reportVersionId) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'id = :id'.
	 */
	public List<ReportFilter> findByPersistableObject(Long id) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand1_column_id = :operand1ColumnId AND operand1_column_dataset_id = :operand1ColumnDatasetId AND operand1_column_design_id = :operand1ColumnDesignId'.
	 */
	public List<ReportFilter> findByReportColumn(Long operand1ColumnId, Long operand1ColumnDatasetId, Long operand1ColumnDesignId) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'report_column_id = :reportColumnId AND report_column_dataset_id = :reportColumnDatasetId AND report_column_design_id = :reportColumnDesignId AND report_column_design_version_id = :reportColumnDesignVersionId'.
	 */
	public List<ReportFilter> findByReportColumn2(Long reportColumnId, Long reportColumnDatasetId, Long reportColumnDesignId, Long reportColumnDesignVersionId) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'id = :id'.
	 */
	public List<ReportFilter> findWhereIdEquals(Long id) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'report_column_id = :reportColumnId'.
	 */
	public List<ReportFilter> findWhereReportColumnIdEquals(Long reportColumnId) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'report_column_dataset_id = :reportColumnDatasetId'.
	 */
	public List<ReportFilter> findWhereReportColumnDatasetIdEquals(Long reportColumnDatasetId) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'report_column_design_id = :reportColumnDesignId'.
	 */
	public List<ReportFilter> findWhereReportColumnDesignIdEquals(Long reportColumnDesignId) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'report_column_design_version_id = :reportColumnDesignVersionId'.
	 */
	public List<ReportFilter> findWhereReportColumnDesignVersionIdEquals(Long reportColumnDesignVersionId) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'prefix = :prefix'.
	 */
	public List<ReportFilter> findWherePrefixEquals(String prefix) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operator = :operator'.
	 */
	public List<ReportFilter> findWhereOperatorEquals(String operator) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'suffix = :suffix'.
	 */
	public List<ReportFilter> findWhereSuffixEquals(String suffix) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand1 = :operand1'.
	 */
	public List<ReportFilter> findWhereOperand1Equals(String operand1) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand2 = :operand2'.
	 */
	public List<ReportFilter> findWhereOperand2Equals(String operand2) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'filter_type = :filterType'.
	 */
	public List<ReportFilter> findWhereFilterTypeEquals(Integer filterType) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand1_column_id = :operand1ColumnId'.
	 */
	public List<ReportFilter> findWhereOperand1ColumnIdEquals(Long operand1ColumnId) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand1_column_dataset_id = :operand1ColumnDatasetId'.
	 */
	public List<ReportFilter> findWhereOperand1ColumnDatasetIdEquals(Long operand1ColumnDatasetId) throws ReportFilterDaoException;

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand1_column_design_id = :operand1ColumnDesignId'.
	 */
	public List<ReportFilter> findWhereOperand1ColumnDesignIdEquals(Long operand1ColumnDesignId) throws ReportFilterDaoException;

	/** 
	 * Returns the rows from the report_filter table that matches the specified primary-key value.
	 */
	public ReportFilter findByPrimaryKey(ReportFilterPk pk) throws ReportFilterDaoException;

	/**
	 * Returns all the report filter in the given reportDesign for the given datasets
	 * @param reportDesignId
	 * @param datasetList
	 * @return
	 * @throws ReportFilterDaoException
	 */
	public List<ReportFilter> findAll(Long reportDesignId, Long reportVersionId, List<ReportDataset> datasetList) throws ReportFilterDaoException;

}
