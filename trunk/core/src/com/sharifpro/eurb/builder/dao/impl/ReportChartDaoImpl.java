package com.sharifpro.eurb.builder.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.ReportChartDao;
import com.sharifpro.eurb.builder.exception.ReportChartDaoException;
import com.sharifpro.eurb.builder.model.ObjectConfig;
import com.sharifpro.eurb.builder.model.ReportChart;
import com.sharifpro.eurb.builder.model.ReportChartPk;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.dao.impl.PersistableObjectDaoImpl;
import com.sharifpro.util.PropertyProvider;

@Repository
public class ReportChartDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportChart>, ReportChartDao
{

	private final static String QUERY_FROM_COLUMNS = " o.design_id, o.design_version_id, o.chart_type, o.name ";
	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + " o " + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;
	private final static String COUNT_QUERY = "SELECT count(distinct(o.id)) FROM " + getTableName() + " o ";
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportChartPk
	 */
	@Transactional
	public ReportChartPk insert(ReportChart dto) throws ReportChartDaoException
	{
		try{
			ReportChartPk pk = new ReportChartPk(); 
			DaoFactory.createPersistableObjectDao().insert(dto, pk);
			getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, design_id, design_version_id, chart_type, name) VALUES ( ?, ?, ?, ?, ?)",
					pk.getId(),dto.getReportDesignId(),dto.getReportDesignVersionId(),dto.getType(), dto.getName());
			return pk;
		}
		catch (Exception e) {
			throw new ReportChartDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Updates a single row in the report_chart table.
	 */
	@Transactional
	public void update(ReportChartPk pk, ReportChart dto) throws ReportChartDaoException
	{
		try{
			DaoFactory.createPersistableObjectDao().update(pk);
			getJdbcTemplate().update("UPDATE " + getTableName() + " SET  chart_type = ? , name = ?" +
					" WHERE id = ?",dto.getType(), dto.getName(), pk.getId());
		}
		catch (Exception e) {
			throw new ReportChartDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Deletes a single row in the report_chart table.
	 */
	@Transactional
	public void delete(ReportChartPk pk) throws ReportChartDaoException
	{
		try{
			//first delete all axis for the given chart
			getJdbcTemplate().update("DELETE FROM " + ReportChartAxisDaoImpl.getTableName() + " WHERE chart_id = ?", pk.getId());
			//then delete all config for the given chart
			getJdbcTemplate().update("DELETE FROM " + ObjectConfigDaoImpl.getTableName() + " WHERE object_id = ? ", pk.getId());
			//then delete the chart
			getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
			DaoFactory.createPersistableObjectDao().delete(pk);
		}
		catch (Exception e) {
			throw new ReportChartDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/**
	 * Deletes all given rows from the report_chart table.
	 */
	@Transactional
	public void deleteAll(List<ReportChartPk> pkList) throws ReportChartDaoException
	{
		for(ReportChartPk pk : pkList){
			delete(pk);
		}
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return ReportChart
	 */
	public ReportChart mapRow(ResultSet rs, int row) throws SQLException
	{
		ReportChart dto = new ReportChart();
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setReportDesignId( new Long( rs.getLong(++i) ) );
		dto.setReportDesignVersionId( new Long( rs.getLong(++i) ) );
		dto.setType( rs.getString(++i)  );
		dto.setName( rs.getString(++i) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "report_chart";
	}

	/** 
	 * Returns all rows from the report_chart table that match the criteria 'id = :id AND design_id = :designId AND design_version_id = :designVersionId'.
	 */
	@Transactional(readOnly = true)
	public ReportChart findByPrimaryKey(Long id) throws ReportChartDaoException
	{
		try {
			List<ReportChart> list = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ? ", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportChartDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_chart table that match the criteria ''.
	 */
	@Transactional(readOnly = true)
	public List<ReportChart> findAll() throws ReportChartDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " ORDER BY o.id", this);
		}
		catch (Exception e) {
			throw new ReportChartDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Returns all rows from the report_chart table for given report design.
	 */

	@Transactional(readOnly = true)
	public List<ReportChart> findAll(ReportDesign reportDesign) throws ReportChartDaoException
	{
		return findByReportDesign(reportDesign.getId(), reportDesign.getVersionId());
	}

	/** 
	 * Counts all rows from the report_chart table that match the criteria ''.
	 */

	@Transactional(readOnly = true)
	public int countAll() throws ReportChartDaoException
	{
		try{
			return getJdbcTemplate().queryForInt(COUNT_QUERY);
		}
		catch (Exception e) {
			throw new ReportChartDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Counts all rows from the report_chart table for given report design.
	 */
	@Transactional(readOnly = true)
	public int countAll(ReportDesign reportDesign) throws ReportChartDaoException
	{
		try{
			return getJdbcTemplate().queryForInt(COUNT_QUERY + " WHERE o.design_id = ? AND o.design_version_id = ?",reportDesign.getId(), reportDesign.getVersionId());
		}
		catch (Exception e) {
			throw new ReportChartDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}


	/** 
	 * Returns all rows from the report_chart table that match the criteria 'id = :id'.
	 */
	@Transactional(readOnly = true)
	public List<ReportChart> findByPersistableObject(Long id) throws ReportChartDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ?", this,id);
		}
		catch (Exception e) {
			throw new ReportChartDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	

	/** 
	 * Returns all rows from the report_chart table that match the criteria 'design_id = :designId AND design_version_id = :designVersionId'.
	 */
	@Transactional(readOnly = true)
	public List<ReportChart> findByReportDesign(Long designId, Long designVersionId) throws ReportChartDaoException
	{
		try {
			List<ReportChart> charts = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.design_id = ? AND o.design_version_id = ? ORDER BY o.id", this,designId,designVersionId);
			for(ReportChart rc : charts){
				List<ObjectConfig> config = getJdbcTemplate().query(ObjectConfigDaoImpl.QUERY_SELECT_PART + " WHERE object_id = ?", new ObjectConfigDaoImpl(), rc.getId());
				rc.setConfig(config);
			}
			return charts;
		}
		catch (Exception e) {
			throw new ReportChartDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}
	


	/** 
	 * Returns all rows from the report_chart table that match the criteria 'id = :id'.
	 */
	@Transactional(readOnly = true)
	public List<ReportChart> findWhereIdEquals(Long id) throws ReportChartDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ? ORDER BY o.id", this,id);
		}
		catch (Exception e) {
			throw new ReportChartDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_chart table that match the criteria 'design_id = :designId'.
	 */
	@Transactional(readOnly = true)
	public List<ReportChart> findWhereDesignIdEquals(Long designId) throws ReportChartDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.design_id = ? ORDER BY o.id", this,designId);
		}
		catch (Exception e) {
			throw new ReportChartDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_chart table that match the criteria 'design_version_id = :designVersionId'.
	 */
	@Transactional(readOnly = true)
	public List<ReportChart> findWhereDesignVersionIdEquals(Long designVersionId) throws ReportChartDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.design_version_id = ? ORDER BY o.id", this,designVersionId);
		}
		catch (Exception e) {
			throw new ReportChartDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Returns all rows from the report_chart table that match the criteria 'name = :name'.
	 */
	@Transactional(readOnly = true)
	public List<ReportChart> findWhereNameEquals(String name) throws ReportChartDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.name = ? ORDER BY o.id", this, name);
		}
		catch (Exception e) {
			throw new ReportChartDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_chart table that match the criteria 'chart_type = :type'.
	 */
	@Transactional(readOnly = true)
	public List<ReportChart> findWhereTypeEquals(String type) throws ReportChartDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.chart_type = ? ORDER BY o.id", this, type);
		}
		catch (Exception e) {
			throw new ReportChartDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns the rows from the report_chart table that matches the specified primary-key value.
	 */
	@Transactional(readOnly = true)
	public ReportChart findByPrimaryKey(ReportChartPk pk) throws ReportChartDaoException
	{
		return findByPrimaryKey( pk.getId());
	}

}
