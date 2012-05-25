package com.sharifpro.eurb.builder.dao;

import com.sharifpro.eurb.builder.dao.GroupAggregationDao;
import com.sharifpro.eurb.builder.exception.GroupAggregationDaoException;
import com.sharifpro.eurb.builder.model.GroupAggregation;
import com.sharifpro.eurb.builder.model.GroupAggregationPk;
import com.sharifpro.eurb.builder.model.ReportColumn;

import java.util.List;

public interface GroupAggregationDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return GroupAggregationPk
	 */
	public GroupAggregationPk insert(GroupAggregation dto) throws GroupAggregationDaoException;

	/** 
	 * Updates a single row in the group_aggregation table.
	 */
	public void update(GroupAggregationPk pk, GroupAggregation dto) throws GroupAggregationDaoException;

	/** 
	 * Deletes a single row in the group_aggregation table.
	 */
	public void delete(GroupAggregationPk pk) throws GroupAggregationDaoException;
	
	/** 
	 * Deletes multiple rows in the group_aggregation table.
	 */
	public void deleteAll(List<GroupAggregationPk> pkList) throws GroupAggregationDaoException;
	
	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'parent_column_id = :parentColumn.getId()'.
	 */
	public List<GroupAggregation> findAll(ReportColumn parentColumn) throws GroupAggregationDaoException;

	

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'id = :id'.
	 */
	public GroupAggregation findByPrimaryKey(Long id) throws GroupAggregationDaoException;

	
	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'id = :id'.
	 */
	public List<GroupAggregation> findByPersistableObject(Long id) throws GroupAggregationDaoException;

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'parent_column_id = :parentColumnId'.
	 */
	public List<GroupAggregation> findByReportColumn(Long parentColumnId) throws GroupAggregationDaoException;

	
	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'id = :id'.
	 */
	public List<GroupAggregation> findWhereIdEquals(Long id) throws GroupAggregationDaoException;

		/** 
	 * Returns the rows from the group_aggregation table that matches the specified primary-key value.
	 */
	public GroupAggregation findByPrimaryKey(GroupAggregationPk pk) throws GroupAggregationDaoException;

}
