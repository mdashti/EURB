package com.sharifpro.eurb.builder.dao.impl;

import com.sharifpro.eurb.builder.dao.GroupAggregationDao;
import com.sharifpro.eurb.builder.exception.GroupAggregationDaoException;
import com.sharifpro.eurb.builder.model.GroupAggregation;
import com.sharifpro.eurb.builder.model.GroupAggregationPk;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;

import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class GroupAggregationDaoImpl extends AbstractDAO implements ParameterizedRowMapper<GroupAggregation>, GroupAggregationDao
{
	protected JdbcTemplate jdbcTemplate;

	protected DataSource dataSource;

	/**
	 * Method 'setDataSource'
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return GroupAggregationPk
	 */
	@Transactional
	public GroupAggregationPk insert(GroupAggregation dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getId(),dto.getParentColumnId(),dto.getParentColumnDatasetId(),dto.getParentColumnDesignId(),dto.getParentColumnDesignVersionId(),dto.getAggregatedReportColumnId(),dto.getAggregatedReportColumnDatasetId(),dto.getAggregatedReportColumnDesignId(),dto.getAggregatedReportColumnDesignVersionId(),dto.getAggregationFunction(),dto.getPlace());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the group_aggregation table.
	 */
	@Transactional
	public void update(GroupAggregationPk pk, GroupAggregation dto) throws GroupAggregationDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET id = ?, parent_column_id = ?, parent_column_dataset_id = ?, parent_column_design_id = ?, parent_column_design_version_id = ?, aggregated_report_column_id = ?, aggregated_report_column_dataset_id = ?, aggregated_report_column_design_id = ?, aggregated_report_column_design_version_id = ?, aggregation_function = ?, place = ? WHERE id = ?",dto.getId(),dto.getParentColumnId(),dto.getParentColumnDatasetId(),dto.getParentColumnDesignId(),dto.getParentColumnDesignVersionId(),dto.getAggregatedReportColumnId(),dto.getAggregatedReportColumnDatasetId(),dto.getAggregatedReportColumnDesignId(),dto.getAggregatedReportColumnDesignVersionId(),dto.getAggregationFunction(),dto.getPlace(),pk.getId());
	}

	/** 
	 * Deletes a single row in the group_aggregation table.
	 */
	@Transactional
	public void delete(GroupAggregationPk pk) throws GroupAggregationDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
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
		dto.setId( new Long( rs.getLong(1) ) );
		dto.setParentColumnId( new Long( rs.getLong(2) ) );
		dto.setParentColumnDatasetId( new Long( rs.getLong(3) ) );
		dto.setParentColumnDesignId( new Long( rs.getLong(4) ) );
		dto.setParentColumnDesignVersionId( new Long( rs.getLong(5) ) );
		dto.setAggregatedReportColumnId( new Long( rs.getLong(6) ) );
		dto.setAggregatedReportColumnDatasetId( new Long( rs.getLong(7) ) );
		dto.setAggregatedReportColumnDesignId( new Long( rs.getLong(8) ) );
		dto.setAggregatedReportColumnDesignVersionId( new Long( rs.getLong(9) ) );
		dto.setAggregationFunction( rs.getString( 10 ) );
		dto.setPlace( new Integer( rs.getInt(11) ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "group_aggregation";
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'id = :id'.
	 */
	@Transactional
	public GroupAggregation findByPrimaryKey(Long id) throws GroupAggregationDaoException
	{
		try {
			List<GroupAggregation> list = jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria ''.
	 */
	@Transactional
	public List<GroupAggregation> findAll() throws GroupAggregationDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<GroupAggregation> findByPersistableObject(Long id) throws GroupAggregationDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'parent_column_id = :parentColumnId AND parent_column_dataset_id = :parentColumnDatasetId AND parent_column_design_id = :parentColumnDesignId AND parent_column_design_version_id = :parentColumnDesignVersionId'.
	 */
	@Transactional
	public List<GroupAggregation> findByReportColumn(Long parentColumnId, Long parentColumnDatasetId, Long parentColumnDesignId, Long parentColumnDesignVersionId) throws GroupAggregationDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " WHERE parent_column_id = ? AND parent_column_dataset_id = ? AND parent_column_design_id = ? AND parent_column_design_version_id = ?", this,parentColumnId,parentColumnDatasetId,parentColumnDesignId,parentColumnDesignVersionId);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'aggregated_report_column_id = :aggregatedReportColumnId AND aggregated_report_column_dataset_id = :aggregatedReportColumnDatasetId AND aggregated_report_column_design_id = :aggregatedReportColumnDesignId AND aggregated_report_column_design_version_id = :aggregatedReportColumnDesignVersionId'.
	 */
	@Transactional
	public List<GroupAggregation> findByReportColumn2(Long aggregatedReportColumnId, Long aggregatedReportColumnDatasetId, Long aggregatedReportColumnDesignId, Long aggregatedReportColumnDesignVersionId) throws GroupAggregationDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " WHERE aggregated_report_column_id = ? AND aggregated_report_column_dataset_id = ? AND aggregated_report_column_design_id = ? AND aggregated_report_column_design_version_id = ?", this,aggregatedReportColumnId,aggregatedReportColumnDatasetId,aggregatedReportColumnDesignId,aggregatedReportColumnDesignVersionId);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<GroupAggregation> findWhereIdEquals(Long id) throws GroupAggregationDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'parent_column_id = :parentColumnId'.
	 */
	@Transactional
	public List<GroupAggregation> findWhereParentColumnIdEquals(Long parentColumnId) throws GroupAggregationDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " WHERE parent_column_id = ? ORDER BY parent_column_id", this,parentColumnId);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'parent_column_dataset_id = :parentColumnDatasetId'.
	 */
	@Transactional
	public List<GroupAggregation> findWhereParentColumnDatasetIdEquals(Long parentColumnDatasetId) throws GroupAggregationDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " WHERE parent_column_dataset_id = ? ORDER BY parent_column_dataset_id", this,parentColumnDatasetId);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'parent_column_design_id = :parentColumnDesignId'.
	 */
	@Transactional
	public List<GroupAggregation> findWhereParentColumnDesignIdEquals(Long parentColumnDesignId) throws GroupAggregationDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " WHERE parent_column_design_id = ? ORDER BY parent_column_design_id", this,parentColumnDesignId);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'parent_column_design_version_id = :parentColumnDesignVersionId'.
	 */
	@Transactional
	public List<GroupAggregation> findWhereParentColumnDesignVersionIdEquals(Long parentColumnDesignVersionId) throws GroupAggregationDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " WHERE parent_column_design_version_id = ? ORDER BY parent_column_design_version_id", this,parentColumnDesignVersionId);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'aggregated_report_column_id = :aggregatedReportColumnId'.
	 */
	@Transactional
	public List<GroupAggregation> findWhereAggregatedReportColumnIdEquals(Long aggregatedReportColumnId) throws GroupAggregationDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " WHERE aggregated_report_column_id = ? ORDER BY aggregated_report_column_id", this,aggregatedReportColumnId);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'aggregated_report_column_dataset_id = :aggregatedReportColumnDatasetId'.
	 */
	@Transactional
	public List<GroupAggregation> findWhereAggregatedReportColumnDatasetIdEquals(Long aggregatedReportColumnDatasetId) throws GroupAggregationDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " WHERE aggregated_report_column_dataset_id = ? ORDER BY aggregated_report_column_dataset_id", this,aggregatedReportColumnDatasetId);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'aggregated_report_column_design_id = :aggregatedReportColumnDesignId'.
	 */
	@Transactional
	public List<GroupAggregation> findWhereAggregatedReportColumnDesignIdEquals(Long aggregatedReportColumnDesignId) throws GroupAggregationDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " WHERE aggregated_report_column_design_id = ? ORDER BY aggregated_report_column_design_id", this,aggregatedReportColumnDesignId);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'aggregated_report_column_design_version_id = :aggregatedReportColumnDesignVersionId'.
	 */
	@Transactional
	public List<GroupAggregation> findWhereAggregatedReportColumnDesignVersionIdEquals(Long aggregatedReportColumnDesignVersionId) throws GroupAggregationDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " WHERE aggregated_report_column_design_version_id = ? ORDER BY aggregated_report_column_design_version_id", this,aggregatedReportColumnDesignVersionId);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'aggregation_function = :aggregationFunction'.
	 */
	@Transactional
	public List<GroupAggregation> findWhereAggregationFunctionEquals(String aggregationFunction) throws GroupAggregationDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " WHERE aggregation_function = ? ORDER BY aggregation_function", this,aggregationFunction);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the group_aggregation table that match the criteria 'place = :place'.
	 */
	@Transactional
	public List<GroupAggregation> findWherePlaceEquals(Integer place) throws GroupAggregationDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, parent_column_id, parent_column_dataset_id, parent_column_design_id, parent_column_design_version_id, aggregated_report_column_id, aggregated_report_column_dataset_id, aggregated_report_column_design_id, aggregated_report_column_design_version_id, aggregation_function, place FROM " + getTableName() + " WHERE place = ? ORDER BY place", this,place);
		}
		catch (Exception e) {
			throw new GroupAggregationDaoException("Query failed", e);
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
