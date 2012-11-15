package com.sharifpro.eurb.builder.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.ReportChartAxisDao;
import com.sharifpro.eurb.builder.exception.ReportChartAxisDaoException;
import com.sharifpro.eurb.builder.model.ReportChart;
import com.sharifpro.eurb.builder.model.ReportChartAxis;
import com.sharifpro.eurb.builder.model.ReportChartAxisPk;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.dao.impl.PersistableObjectDaoImpl;
import com.sharifpro.transaction.annotation.TransactionalReadOnly;
import com.sharifpro.transaction.annotation.TransactionalReadWrite;
import com.sharifpro.util.PropertyProvider;

@Repository
public class ReportChartAxisDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportChartAxis>, ReportChartAxisDao
{

	private final static String QUERY_FROM_COLUMNS = " o.chart_id, o.axis_type, o.title, o.column_mapping_id, o.report_dataset_id, o.aggregation, o.has_formula, o.formula	";
	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + " o " + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;
	private final static String COUNT_QUERY = "SELECT count(distinct(o.id)) FROM " + getTableName() + " o ";
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportChartAxisPk
	 */
	@TransactionalReadWrite
	public ReportChartAxisPk insert(ReportChartAxis dto) throws ReportChartAxisDaoException
	{
		try{
			ReportChartAxisPk pk = new ReportChartAxisPk(); 
			DaoFactory.createPersistableObjectDao().insert(dto, pk);
			getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, chart_id, axis_type, title, column_mapping_id, report_dataset_id, aggregation, has_formula, formula) " +
					"VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)",
					pk.getId(),dto.getChartId(),dto.getType(),dto.getTitle(), dto.getColumnMappingId(), dto.getDatasetId(), dto.getAggregation(), dto.hasFormula(), dto.getFormula());
			return pk;
		}
		catch (Exception e) {
			throw new ReportChartAxisDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Updates a single row in the report_chart_axis table.
	 */
	@TransactionalReadWrite
	public void update(ReportChartAxisPk pk, ReportChartAxis dto) throws ReportChartAxisDaoException
	{
		try{
			DaoFactory.createPersistableObjectDao().update(pk);
			getJdbcTemplate().update("UPDATE " + getTableName() + " SET  title = ? , column_mapping_id = ?, report_dataset_id = ?, aggregation = ?, has_formula = ?, formula = ?" +
					" WHERE id = ?",dto.getTitle(), dto.getColumnMappingId(), dto.getDatasetId(), dto.getAggregation(), dto.hasFormula(), dto.getFormula(), pk.getId());
		}
		catch (Exception e) {
			throw new ReportChartAxisDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Deletes a single row in the report_chart_axis table.
	 */
	@TransactionalReadWrite
	public void delete(ReportChartAxisPk pk) throws ReportChartAxisDaoException
	{
		try{
			getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
			DaoFactory.createPersistableObjectDao().delete(pk);
		}
		catch (Exception e) {
			throw new ReportChartAxisDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/**
	 * Deletes all given rows from the report_chart_axis table.
	 */
	@TransactionalReadWrite
	public void deleteAll(List<ReportChartAxisPk> pkList) throws ReportChartAxisDaoException
	{
		for(ReportChartAxisPk pk : pkList){
			delete(pk);
		}
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return ReportChartAxis
	 */
	public ReportChartAxis mapRow(ResultSet rs, int row) throws SQLException
	{
		ReportChartAxis dto = new ReportChartAxis();
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setChartId( new Long( rs.getLong(++i) ) );
		dto.setType( rs.getString(++i) );
		dto.setTitle( rs.getString(++i)  );
		dto.setColumnMappingId( new Long( rs.getLong(++i) ) );
		dto.setDatasetId(new Long ( rs.getLong(++i) ) );
		dto.setAggregation( rs.getString(++i) );
		dto.setHasFormula(rs.getBoolean(++i));
		dto.setFormula(rs.getString(++i));
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "report_chart_axis";
	}

	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public ReportChartAxis findByPrimaryKey(Long id) throws ReportChartAxisDaoException
	{
		try {
			List<ReportChartAxis> list = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ? ", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportChartAxisDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria ''.
	 */
	@TransactionalReadOnly
	public List<ReportChartAxis> findAll() throws ReportChartAxisDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " ORDER BY o.id", this);
		}
		catch (Exception e) {
			throw new ReportChartAxisDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Returns all rows from the report_chart_axis table for given report chart.
	 */

	@TransactionalReadOnly
	public List<ReportChartAxis> findAll(ReportChart reportChart) throws ReportChartAxisDaoException
	{
		return findByReportChart(reportChart.getId());
	}

	/** 
	 * Counts all rows from the report_chart_axis table that match the criteria ''.
	 */

	@TransactionalReadOnly
	public int countAll() throws ReportChartAxisDaoException
	{
		try{
			return getJdbcTemplate().queryForInt(COUNT_QUERY);
		}
		catch (Exception e) {
			throw new ReportChartAxisDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Counts all rows from the report_chart_axis table for given report chart.
	 */
	@TransactionalReadOnly
	public int countAll(ReportChart reportChart) throws ReportChartAxisDaoException
	{
		try{
			return getJdbcTemplate().queryForInt(COUNT_QUERY + " WHERE o.chart_id = ?",reportChart.getId());
		}
		catch (Exception e) {
			throw new ReportChartAxisDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}


	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public List<ReportChartAxis> findByPersistableObject(Long id) throws ReportChartAxisDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ?", this,id);
		}
		catch (Exception e) {
			throw new ReportChartAxisDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	

	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria 'chart_id = :chartId'.
	 */
	@TransactionalReadOnly
	public List<ReportChartAxis> findByReportChart(Long chartId) throws ReportChartAxisDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.chart_id = ? ORDER BY o.id", this, chartId);
		}
		catch (Exception e) {
			throw new ReportChartAxisDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}
	


	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public List<ReportChartAxis> findWhereIdEquals(Long id) throws ReportChartAxisDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ? ORDER BY o.id", this,id);
		}
		catch (Exception e) {
			throw new ReportChartAxisDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria 'chart_id = :chartId'.
	 */
	@TransactionalReadOnly
	public List<ReportChartAxis> findWhereChartIdEquals(Long chartId) throws ReportChartAxisDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.chart_id = ? ORDER BY o.id", this,chartId);
		}
		catch (Exception e) {
			throw new ReportChartAxisDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}


	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria 'title = :title'.
	 */
	@TransactionalReadOnly
	public List<ReportChartAxis> findWhereTitleEquals(String title) throws ReportChartAxisDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.title = ? ORDER BY o.id", this, title);
		}
		catch (Exception e) {
			throw new ReportChartAxisDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_chart_axis table that match the criteria 'axis_type = :type'.
	 */
	@TransactionalReadOnly
	public List<ReportChartAxis> findWhereTypeEquals(String type) throws ReportChartAxisDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.axis_type = ? ORDER BY o.id", this, type);
		}
		catch (Exception e) {
			throw new ReportChartAxisDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns the rows from the report_chart_axis table that matches the specified primary-key value.
	 */
	@TransactionalReadOnly
	public ReportChartAxis findByPrimaryKey(ReportChartAxisPk pk) throws ReportChartAxisDaoException
	{
		return findByPrimaryKey( pk.getId());
	}

}
