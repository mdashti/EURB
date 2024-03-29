package com.sharifpro.eurb.builder.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.ReportDatasetDao;
import com.sharifpro.eurb.builder.exception.ReportDatasetDaoException;
import com.sharifpro.eurb.builder.model.ReportChart;
import com.sharifpro.eurb.builder.model.ReportDataset;
import com.sharifpro.eurb.builder.model.ReportDatasetPk;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.dao.impl.PersistableObjectDaoImpl;
import com.sharifpro.transaction.annotation.TransactionalReadOnly;
import com.sharifpro.transaction.annotation.TransactionalReadWrite;
import com.sharifpro.util.PropertyProvider;

@Repository
public class ReportDatasetDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportDataset>, ReportDatasetDao
{

	private final static String QUERY_FROM_COLUMNS = " o.name, o.design_id, o.design_version_id, o.table_mapping_id, o.base_report_id, o.base_report_version_id, o.ds_order, o.operator ";
	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + " o " + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;
	private final static String COUNT_QUERY = "SELECT count(distinct(o.id)) FROM " + getTableName() + " o ";
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportDatasetPk
	 */
	@TransactionalReadWrite
	public ReportDatasetPk insert(ReportDataset dto) throws ReportDatasetDaoException
	{
		try{
			int lastOrder = getJdbcTemplate().queryForInt("SELECT MAX(ds_order) FROM " + getTableName() + " WHERE design_id = ? AND design_version_id = ?", dto.getDesignId(), dto.getDesignVersionId());
			dto.setDsOrder(++lastOrder);
			ReportDatasetPk pk = new ReportDatasetPk(); 
			DaoFactory.createPersistableObjectDao().insert(dto, pk);
			getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, name, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, ds_order, operator) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )",
					pk.getId(),dto.getName(), dto.getDesignId(),dto.getDesignVersionId(),dto.getTableMappingId(),dto.getBaseReportId(),dto.getBaseReportVersionId(),dto.getDsOrder(),dto.getOperator());
			return pk;
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Updates a single row in the report_dataset table.
	 */
	@TransactionalReadWrite
	public void update(ReportDatasetPk pk, ReportDataset dto) throws ReportDatasetDaoException
	{
		try{
			DaoFactory.createPersistableObjectDao().update(pk);
			getJdbcTemplate().update("UPDATE " + getTableName() + " SET id = ?, name = ?, design_id = ?, design_version_id = ?, table_mapping_id = ?, base_report_id = ?, base_report_version_id = ?, ds_order = ?, operator = ?" +
					" WHERE id = ? AND design_id = ? AND design_version_id = ?",pk.getId(),dto.getName(), dto.getDesignId(),dto.getDesignVersionId(),dto.getTableMappingId(),dto.getBaseReportId(),dto.getBaseReportVersionId(),
					dto.getDsOrder(),dto.getOperator(),pk.getId(),pk.getDesignId(),pk.getDesignVersionId());
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Deletes a single row in the report_dataset table.
	 */
	@TransactionalReadWrite
	public void delete(ReportDatasetPk pk) throws ReportDatasetDaoException
	{
		try{
			getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
			DaoFactory.createPersistableObjectDao().delete(pk);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/**
	 * Deletes all given rows from the report_dataset table.
	 */
	@TransactionalReadWrite
	public void deleteAll(List<ReportDatasetPk> pkList) throws ReportDatasetDaoException
	{
		for(ReportDatasetPk pk : pkList){
			delete(pk);
		}
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return ReportDataset
	 */
	public ReportDataset mapRow(ResultSet rs, int row) throws SQLException
	{
		ReportDataset dto = new ReportDataset();
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setName(rs.getString(++i));
		dto.setDesignId( new Long( rs.getLong(++i) ) );
		dto.setDesignVersionId( new Long( rs.getLong(++i) ) );
		dto.setTableMappingId( new Long( rs.getLong(++i) ) );
		if (rs.wasNull()) {
			dto.setTableMappingId( null );
		}

		dto.setBaseReportId( new Long( rs.getLong(++i) ) );
		if (rs.wasNull()) {
			dto.setBaseReportId( null );
		}

		dto.setBaseReportVersionId( new Long( rs.getLong(++i) ) );
		if (rs.wasNull()) {
			dto.setBaseReportVersionId( null );
		}

		dto.setDsOrder( new Integer( rs.getInt(++i) ) );
		dto.setOperator( new Integer( rs.getInt(++i) ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "report_dataset";
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'id = :id AND design_id = :designId AND design_version_id = :designVersionId'.
	 */
	@TransactionalReadOnly
	public ReportDataset findByPrimaryKey(Long id, Long designId, Long designVersionId) throws ReportDatasetDaoException
	{
		try {
			List<ReportDataset> list = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ? AND o.design_id = ? AND o.design_version_id = ?", this,id,designId,designVersionId);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria ''.
	 */
	@TransactionalReadOnly
	public List<ReportDataset> findAll() throws ReportDatasetDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " ORDER BY o.id, o.design_id, o.design_version_id", this);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Returns all rows from the report_dataset table for given report design.
	 */

	@TransactionalReadOnly
	public List<ReportDataset> findAll(ReportDesign reportDesign) throws ReportDatasetDaoException
	{
		return findByReportDesign(reportDesign.getId(), reportDesign.getVersionId());
	}

	/**
	 * Returns all report datasets in the given reportDesign associated with the given report chart
	 * @param reportDesign
	 * @param chart
	 * @return List of ReportDataset
	 */
	@TransactionalReadOnly
	public List<ReportDataset> findAll(ReportDesign reportDesign, ReportChart chart) throws ReportDatasetDaoException
	{
		try{
			String query = QUERY_SELECT_PART + " JOIN report_chart_axis axis ON axis.report_dataset_id = o.id JOIN report_chart chart ON chart.id = axis.chart_id ";
			return getJdbcTemplate().query(query + " WHERE o.design_id = ? AND o.design_version_id = ? ORDER BY o.ds_order", this, reportDesign.getId(), reportDesign.getVersionId());
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Counts all rows from the report_dataset table that match the criteria ''.
	 */

	@TransactionalReadOnly
	public int countAll() throws ReportDatasetDaoException
	{
		try{
			return getJdbcTemplate().queryForInt(COUNT_QUERY);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Counts all rows from the report_dataset table for given report design.
	 */
	@TransactionalReadOnly
	public int countAll(ReportDesign reportDesign) throws ReportDatasetDaoException
	{
		try{
			return getJdbcTemplate().queryForInt(COUNT_QUERY + " WHERE o.design_id = ? AND o.design_version_id = ?",reportDesign.getId(), reportDesign.getVersionId());
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}


	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public List<ReportDataset> findByPersistableObject(Long id) throws ReportDatasetDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ?", this,id);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'base_report_id = :baseReportId AND base_report_version_id = :baseReportVersionId'.
	 */
	@TransactionalReadOnly
	public List<ReportDataset> findByBaseReportDesign(Long baseReportId, Long baseReportVersionId) throws ReportDatasetDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.base_report_id = ? AND o.base_report_version_id = ?", this,baseReportId,baseReportVersionId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'design_id = :designId AND design_version_id = :designVersionId'.
	 */
	@TransactionalReadOnly
	public List<ReportDataset> findByReportDesign(Long designId, Long designVersionId) throws ReportDatasetDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.design_id = ? AND o.design_version_id = ? ORDER BY o.ds_order", this,designId,designVersionId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'design_id = :designId AND design_version_id = :designVersionId AND table_mapping_id = :tableMappingId'.
	 */
	@TransactionalReadOnly
	public List<ReportDataset> findByReportDesignAndTableMapping(Long designId, Long designVersionId, Long tableMappingId) throws ReportDatasetDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.design_id = ? AND o.design_version_id = ? AND o.table_mapping_id = ?", this,designId,designVersionId, tableMappingId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'table_mapping_id = :tableMappingId'.
	 */
	@TransactionalReadOnly
	public List<ReportDataset> findByTableMapping(Long tableMappingId) throws ReportDatasetDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.table_mapping_id = ?", this,tableMappingId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public List<ReportDataset> findWhereIdEquals(Long id) throws ReportDatasetDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ? ORDER BY o.id", this,id);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'design_id = :designId'.
	 */
	@TransactionalReadOnly
	public List<ReportDataset> findWhereDesignIdEquals(Long designId) throws ReportDatasetDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.design_id = ? ORDER BY o.design_id", this,designId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'design_version_id = :designVersionId'.
	 */
	@TransactionalReadOnly
	public List<ReportDataset> findWhereDesignVersionIdEquals(Long designVersionId) throws ReportDatasetDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.design_version_id = ? ORDER BY o.design_version_id", this,designVersionId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'table_mapping_id = :tableMappingId'.
	 */
	@TransactionalReadOnly
	public List<ReportDataset> findWhereTableMappingIdEquals(Long tableMappingId) throws ReportDatasetDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.table_mapping_id = ? ORDER BY o.table_mapping_id", this,tableMappingId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'base_report_id = :baseReportId'.
	 */
	@TransactionalReadOnly
	public List<ReportDataset> findWhereBaseReportIdEquals(Long baseReportId) throws ReportDatasetDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.base_report_id = ? ORDER BY o.base_report_id", this,baseReportId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'base_report_version_id = :baseReportVersionId'.
	 */
	@TransactionalReadOnly
	public List<ReportDataset> findWhereBaseReportVersionIdEquals(Long baseReportVersionId) throws ReportDatasetDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.base_report_version_id = ? ORDER BY o.base_report_version_id", this,baseReportVersionId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'ds_order = :dsOrder'.
	 */
	@TransactionalReadOnly
	public List<ReportDataset> findWhereDsOrderEquals(Integer dsOrder) throws ReportDatasetDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.ds_order = ? ORDER BY o.ds_order", this,dsOrder);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'operator = :operator'.
	 */
	@TransactionalReadOnly
	public List<ReportDataset> findWhereOperatorEquals(Integer operator) throws ReportDatasetDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.operator = ? ORDER BY o.operator", this,operator);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns the rows from the report_dataset table that matches the specified primary-key value.
	 */
	@TransactionalReadOnly
	public ReportDataset findByPrimaryKey(ReportDatasetPk pk) throws ReportDatasetDaoException
	{
		return findByPrimaryKey( pk.getId(), pk.getDesignId(), pk.getDesignVersionId() );
	}

}
