package com.sharifpro.eurb.builder.dao;

import com.sharifpro.eurb.builder.dao.ReportColumnDao;
import com.sharifpro.eurb.builder.exception.ReportColumnDaoException;
import com.sharifpro.eurb.builder.model.ReportColumn;
import com.sharifpro.eurb.builder.model.ReportColumnPk;
import com.sharifpro.eurb.builder.model.ReportDesign;

import java.util.List;

public interface ReportColumnDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportColumnPk
	 */
	public ReportColumnPk insert(ReportColumn dto) throws ReportColumnDaoException;

	/** 
	 * Updates a single row in the report_column table.
	 */
	public void update(ReportColumnPk pk, ReportColumn dto) throws ReportColumnDaoException;

	/** 
	 * Deletes a single row in the report_column table.
	 */
	public void delete(ReportColumnPk pk) throws ReportColumnDaoException;
	
	/** 
	 * Deletes all the given rows in the report_column table.
	 */
	public void deleteAll(List<ReportColumnPk> pkList) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'id = :id AND dataset_id = :datasetId AND design_id = :designId AND design_version_id = :designVersionId'.
	 */
	public ReportColumn findByPrimaryKey(Long id, Long datasetId, Long designId, Long designVersionId) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria ''.
	 */
	public List<ReportColumn> findAll() throws ReportColumnDaoException;
	
	/** 
	 * Returns all rows from the report_column table that match the criteria 'design_id = :design.getId()'.
	 */
	public List<ReportColumn> findAll(ReportDesign design) throws ReportColumnDaoException;
	
	/** 
	 * Returns all rows from the report_column table that match the criteria 'design_id = :design.getId()' order by col order.
	 */
	public List<ReportColumn> findAllSortByColOrder(ReportDesign design) throws ReportColumnDaoException;
	
	/** 
	 * Counts all rows from the report_column table that match the criteria 'design_id = :design.getId()'.
	 */
	public int countAll(ReportDesign design) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'id = :id'.
	 */
	public List<ReportColumn> findByPersistableObject(Long id) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_mapping_id = :columnMappingId'.
	 */
	public List<ReportColumn> findByColumnMapping(Long columnMappingId) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'report_column_id = :reportColumnId'.
	 */
	public List<ReportColumn> findByReportColumn(Long reportColumnId) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'dataset_id = :datasetId AND design_id = :designId AND design_version_id = :designVersionId'.
	 */
	public List<ReportColumn> findByReportDataset(Long datasetId, Long designId, Long designVersionId) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'id = :id'.
	 */
	public List<ReportColumn> findWhereIdEquals(Long id) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'dataset_id = :datasetId'.
	 */
	public List<ReportColumn> findWhereDatasetIdEquals(Long datasetId) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'design_id = :designId'.
	 */
	public List<ReportColumn> findWhereDesignIdEquals(Long designId) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'design_version_id = :designVersionId'.
	 */
	public List<ReportColumn> findWhereDesignVersionIdEquals(Long designVersionId) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'col_type = :colTypeName'.
	 */
	public List<ReportColumn> findWhereColTypeEquals(Integer colType) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_mapping_id = :columnMappingId'.
	 */
	public List<ReportColumn> findWhereColumnMappingIdEquals(Long columnMappingId) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'report_column_id = :reportColumnId'.
	 */
	public List<ReportColumn> findWhereReportColumnIdEquals(Long reportColumnId) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'col_order = :colOrder'.
	 */
	public List<ReportColumn> findWhereColOrderEquals(Integer colOrder) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'sort_order = :sortOrder'.
	 */
	public List<ReportColumn> findWhereSortOrderEquals(Integer sortOrder) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'sort_type = :sortType'.
	 */
	public List<ReportColumn> findWhereSortTypeEquals(Short sortType) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'group_level = :groupLevel'.
	 */
	public List<ReportColumn> findWhereGroupLevelEquals(Integer groupLevel) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_width = :columnWidth'.
	 */
	public List<ReportColumn> findWhereColumnWidthEquals(Integer columnWidth) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_align = :columnAlign'.
	 */
	public List<ReportColumn> findWhereColumnAlignEquals(String columnAlign) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_dir = :columnDir'.
	 */
	public List<ReportColumn> findWhereColumnDirEquals(String columnDir) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_header = :columnHeader'.
	 */
	public List<ReportColumn> findWhereColumnHeaderEquals(String columnHeader) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'is_custom = :isCustom'.
	 */
	public List<ReportColumn> findWhereIsCustomEquals(Short isCustom) throws ReportColumnDaoException;

	/** 
	 * Returns all rows from the report_column table that match the criteria 'formula = :formula'.
	 */
	public List<ReportColumn> findWhereFormulaEquals(String formula) throws ReportColumnDaoException;

	/** 
	 * Returns the rows from the report_column table that matches the specified primary-key value.
	 */
	public ReportColumn findByPrimaryKey(ReportColumnPk pk) throws ReportColumnDaoException;

}
