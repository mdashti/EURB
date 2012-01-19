package com.sharifpro.eurb.dao;

import com.sharifpro.eurb.dao.GroupAggregationDao;
import com.sharifpro.eurb.dto.GroupAggregation;
import com.sharifpro.eurb.dto.GroupAggregationPk;
import com.sharifpro.eurb.exceptions.GroupAggregationDaoException;
import java.util.List;

public interface GroupAggregationDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return GroupAggregationPk
	 */
	public GroupAggregationPk insert(GroupAggregation dto);

	/** 
	 * Updates a single row in the group_aggregation table.
	 */
	public void update(GroupAggregationPk pk, GroupAggregation dto) throws GroupAggregationDaoException;

	/** 
	 * Deletes a single row in the group_aggregation table.
	 */
	public void delete(GroupAggregationPk pk) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'id = :id'.
	 */
	public GroupAggregation findByPrimaryKey(Long id) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria ''.
	 */
	public List<GroupAggregation> findAll() throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'id = :id'.
	 */
	public List<GroupAggregation> findByPersistableObject(Long id) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'parent_column_id = :parentColumnId AND parent_column_dataset_id = :parentColumnDatasetId AND parent_column_design_id = :parentColumnDesignId AND parent_column_design_version_id = :parentColumnDesignVersionId'.
	 */
	public List<GroupAggregation> findByReportColumn(Long parentColumnId, Long parentColumnDatasetId, Long parentColumnDesignId, Long parentColumnDesignVersionId) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'aggregated_report_column_id = :aggregatedReportColumnId AND aggregated_report_column_dataset_id = :aggregatedReportColumnDatasetId AND aggregated_report_column_design_id = :aggregatedReportColumnDesignId AND aggregated_report_column_design_version_id = :aggregatedReportColumnDesignVersionId'.
	 */
	public List<GroupAggregation> findByReportColumn2(Long aggregatedReportColumnId, Long aggregatedReportColumnDatasetId, Long aggregatedReportColumnDesignId, Long aggregatedReportColumnDesignVersionId) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'id = :id'.
	 */
	public List<GroupAggregation> findWhereIdEquals(Long id) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'parent_column_id = :parentColumnId'.
	 */
	public List<GroupAggregation> findWhereParentColumnIdEquals(Long parentColumnId) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'parent_column_dataset_id = :parentColumnDatasetId'.
	 */
	public List<GroupAggregation> findWhereParentColumnDatasetIdEquals(Long parentColumnDatasetId) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'parent_column_design_id = :parentColumnDesignId'.
	 */
	public List<GroupAggregation> findWhereParentColumnDesignIdEquals(Long parentColumnDesignId) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'parent_column_design_version_id = :parentColumnDesignVersionId'.
	 */
	public List<GroupAggregation> findWhereParentColumnDesignVersionIdEquals(Long parentColumnDesignVersionId) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'aggregated_report_column_id = :aggregatedReportColumnId'.
	 */
	public List<GroupAggregation> findWhereAggregatedReportColumnIdEquals(Long aggregatedReportColumnId) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'aggregated_report_column_dataset_id = :aggregatedReportColumnDatasetId'.
	 */
	public List<GroupAggregation> findWhereAggregatedReportColumnDatasetIdEquals(Long aggregatedReportColumnDatasetId) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'aggregated_report_column_design_id = :aggregatedReportColumnDesignId'.
	 */
	public List<GroupAggregation> findWhereAggregatedReportColumnDesignIdEquals(Long aggregatedReportColumnDesignId) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'aggregated_report_column_design_version_id = :aggregatedReportColumnDesignVersionId'.
	 */
	public List<GroupAggregation> findWhereAggregatedReportColumnDesignVersionIdEquals(Long aggregatedReportColumnDesignVersionId) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'aggregation_function = :aggregationFunction'.
	 */
	public List<GroupAggregation> findWhereAggregationFunctionEquals(String aggregationFunction) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'place = :place'.
	 */
	public List<GroupAggregation> findWherePlaceEquals(Integer place) throws GroupAggregationDaoException;

	/** 
	 * Returns the rows from the group_aggregation table that matches the specified primary-key value.
	 */
	public GroupAggregation findByPrimaryKey(GroupAggregationPk pk) throws GroupAggregationDaoException;

}
