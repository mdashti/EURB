package com.sharifpro.eurb.builder.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.GroupAggregationDao;
import com.sharifpro.eurb.builder.exception.GroupAggregationDaoException;
import com.sharifpro.eurb.builder.model.GroupAggregation;
import com.sharifpro.eurb.builder.model.GroupAggregationPk;
import com.sharifpro.eurb.builder.model.ReportColumn;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.dao.impl.PersistableObjectDaoImpl;
import com.sharifpro.transaction.annotation.TransactionalReadOnly;
import com.sharifpro.transaction.annotation.TransactionalReadWrite;
import com.sharifpro.util.PropertyProvider;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GroupAggregationDaoImpl extends AbstractDAO implements ParameterizedRowMapper<GroupAggregation>, GroupAggregationDao
{
	
	private final static String QUERY_FROM_COLUMNS = "o.parent_column_id, o.aggregated_column_mapping_id, o.aggregated_column_dataset_id, o.aggregated_report_column_id, o.aggregation_function, o.place";
	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + " o " + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;

	
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return GroupAggregationPk
	 */
	@TransactionalReadWrite
	public GroupAggregationPk insert(GroupAggregation dto) throws GroupAggregationDaoException
	{
		try{
			GroupAggregationPk pk = new GroupAggregationPk();
			DaoFactory.createPersistableObjectDao().insert(dto, pk);
			getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, parent_column_id, aggregated_column_mapping_id, aggregated_column_dataset_id, aggregated_report_column_id," +
					" aggregation_function, place) VALUES ( ?, ?, ?, ?, ?, ?, ? )",pk.getId(),dto.getParentColumnId(),dto.getAggregatedColumnMappingId(),dto.getAggregatedColumnDatasetId(),
					dto.getAggregatedReportColumnId(),dto.getAggregationFunction(),dto.getPlace());	
			return pk;
		}
		catch(Exception e){
			throw new GroupAggregationDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Updates a single row in the group_aggregation table.
	 */
	@TransactionalReadWrite
	public void update(GroupAggregationPk pk, GroupAggregation dto) throws GroupAggregationDaoException
	{
		try{
			DaoFactory.createPersistableObjectDao().update(pk);
			getJdbcTemplate().update("UPDATE " + getTableName() + " SET parent_column_id = ?, aggregated_column_mapping_id = ?, aggregated_column_dataset_id = ?, aggregated_report_column_id = ?, " +
					"aggregation_function = ?, place = ? WHERE id = ?",dto.getParentColumnId(),dto.getAggregatedColumnMappingId(),dto.getAggregatedColumnDatasetId(),dto.getAggregatedReportColumnId(),
					dto.getAggregationFunction(),dto.getPlace(),pk.getId());
		}
		catch(Exception e){
			throw new GroupAggregationDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Deletes a single row in the group_aggregation table.
	 */
	@TransactionalReadWrite
	public void delete(GroupAggregationPk pk) throws GroupAggregationDaoException
	{
		try{
			getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}
	
	/** 
	 * Deletes multiple rows in the group_aggregation table.
	 */
	@TransactionalReadWrite
	public void deleteAll(List<GroupAggregationPk> pkList) throws GroupAggregationDaoException
	{
		for(GroupAggregationPk pk : pkList){
			delete(pk);
		}
	}

	
	
	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return GroupAggregation
	 */
	public GroupAggregation mapRow(ResultSet rs, int row) throws SQLException
	{
		GroupAggregation dto = new GroupAggregation();
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setParentColumnId( new Long( rs.getLong(++i) ) );
		dto.setAggregatedColumnMappingId( new Long( rs.getLong(++i) ) );
		dto.setAggregatedColumnDatasetId( new Long( rs.getLong(++i) ) );
		dto.setAggregatedReportColumnId( new Long( rs.getLong(++i) ) );
		dto.setAggregationFunction( rs.getString( ++i ) );
		dto.setPlace( new Integer( rs.getInt(++i) ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "group_aggregation";
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public GroupAggregation findByPrimaryKey(Long id) throws GroupAggregationDaoException
	{
		try {
			List<GroupAggregation> list = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'parent_column_id = :parentColumn.getId()'.
	 */
	@TransactionalReadOnly
	public List<GroupAggregation> findAll(ReportColumn parentColumn) throws GroupAggregationDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.parent_column_id = ? ORDER BY o.id", this, parentColumn.getId());
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public List<GroupAggregation> findByPersistableObject(Long id) throws GroupAggregationDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ?", this,id);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'parent_column_id = :parentColumnId '.
	 */
	@TransactionalReadOnly
	public List<GroupAggregation> findByReportColumn(Long parentColumnId) throws GroupAggregationDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.parent_column_id = ? ", this,parentColumnId);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	
	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public List<GroupAggregation> findWhereIdEquals(Long id) throws GroupAggregationDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ? ORDER BY o.id", this, id);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'parent_column_id = :parentColumnId'.
	 */
	@TransactionalReadOnly
	public List<GroupAggregation> findWhereParentColumnIdEquals(Long parentColumnId) throws GroupAggregationDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.parent_column_id = ? ORDER BY o.id", this,parentColumnId);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	
	/** 
	 * Returns the rows from the group_aggregation table that matches the specified primary-key value.
	 */
	public GroupAggregation findByPrimaryKey(GroupAggregationPk pk) throws GroupAggregationDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

}
