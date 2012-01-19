package com.sharifpro.eurb.dao.spring;

import com.sharifpro.eurb.dao.ReportDatasetDao;
import com.sharifpro.eurb.dto.ReportDataset;
import com.sharifpro.eurb.dto.ReportDatasetPk;
import com.sharifpro.eurb.exceptions.ReportDatasetDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class ReportDatasetDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportDataset>, ReportDatasetDao
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
	 * @return ReportDatasetPk
	 */
	@Transactional
	public ReportDatasetPk insert(ReportDataset dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, order, operator ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )",dto.getId(),dto.getDesignId(),dto.getDesignVersionId(),dto.getTableMappingId(),dto.getBaseReportId(),dto.getBaseReportVersionId(),dto.getOrder(),dto.getOperator());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the report_dataset table.
	 */
	@Transactional
	public void update(ReportDatasetPk pk, ReportDataset dto) throws ReportDatasetDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET id = ?, design_id = ?, design_version_id = ?, table_mapping_id = ?, base_report_id = ?, base_report_version_id = ?, order = ?, operator = ? WHERE id = ? AND design_id = ? AND design_version_id = ?",dto.getId(),dto.getDesignId(),dto.getDesignVersionId(),dto.getTableMappingId(),dto.getBaseReportId(),dto.getBaseReportVersionId(),dto.getOrder(),dto.getOperator(),pk.getId(),pk.getDesignId(),pk.getDesignVersionId());
	}

	/** 
	 * Deletes a single row in the report_dataset table.
	 */
	@Transactional
	public void delete(ReportDatasetPk pk) throws ReportDatasetDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ? AND design_id = ? AND design_version_id = ?",pk.getId(),pk.getDesignId(),pk.getDesignVersionId());
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
		dto.setId( new Long( rs.getLong(1) ) );
		dto.setDesignId( new Long( rs.getLong(2) ) );
		dto.setDesignVersionId( new Long( rs.getLong(3) ) );
		dto.setTableMappingId( new Long( rs.getLong(4) ) );
		if (rs.wasNull()) {
			dto.setTableMappingId( null );
		}
		
		dto.setBaseReportId( new Long( rs.getLong(5) ) );
		if (rs.wasNull()) {
			dto.setBaseReportId( null );
		}
		
		dto.setBaseReportVersionId( new Long( rs.getLong(6) ) );
		if (rs.wasNull()) {
			dto.setBaseReportVersionId( null );
		}
		
		dto.setOrder( new Integer( rs.getInt(7) ) );
		dto.setOperator( new Integer( rs.getInt(8) ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "report_dataset";
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'id = :id AND design_id = :designId AND design_version_id = :designVersionId'.
	 */
	@Transactional
	public ReportDataset findByPrimaryKey(Long id, Long designId, Long designVersionId) throws ReportDatasetDaoException
	{
		try {
			List<ReportDataset> list = jdbcTemplate.query("SELECT id, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, order, operator FROM " + getTableName() + " WHERE id = ? AND design_id = ? AND design_version_id = ?", this,id,designId,designVersionId);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria ''.
	 */
	@Transactional
	public List<ReportDataset> findAll() throws ReportDatasetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, order, operator FROM " + getTableName() + " ORDER BY id, design_id, design_version_id", this);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportDataset> findByPersistableObject(Long id) throws ReportDatasetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, order, operator FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'base_report_id = :baseReportId AND base_report_version_id = :baseReportVersionId'.
	 */
	@Transactional
	public List<ReportDataset> findByReportDesign(Long baseReportId, Long baseReportVersionId) throws ReportDatasetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, order, operator FROM " + getTableName() + " WHERE base_report_id = ? AND base_report_version_id = ?", this,baseReportId,baseReportVersionId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'design_id = :designId AND design_version_id = :designVersionId'.
	 */
	@Transactional
	public List<ReportDataset> findByReportDesign2(Long designId, Long designVersionId) throws ReportDatasetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, order, operator FROM " + getTableName() + " WHERE design_id = ? AND design_version_id = ?", this,designId,designVersionId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'table_mapping_id = :tableMappingId'.
	 */
	@Transactional
	public List<ReportDataset> findByTableMapping(Long tableMappingId) throws ReportDatasetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, order, operator FROM " + getTableName() + " WHERE table_mapping_id = ?", this,tableMappingId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportDataset> findWhereIdEquals(Long id) throws ReportDatasetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, order, operator FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'design_id = :designId'.
	 */
	@Transactional
	public List<ReportDataset> findWhereDesignIdEquals(Long designId) throws ReportDatasetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, order, operator FROM " + getTableName() + " WHERE design_id = ? ORDER BY design_id", this,designId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'design_version_id = :designVersionId'.
	 */
	@Transactional
	public List<ReportDataset> findWhereDesignVersionIdEquals(Long designVersionId) throws ReportDatasetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, order, operator FROM " + getTableName() + " WHERE design_version_id = ? ORDER BY design_version_id", this,designVersionId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'table_mapping_id = :tableMappingId'.
	 */
	@Transactional
	public List<ReportDataset> findWhereTableMappingIdEquals(Long tableMappingId) throws ReportDatasetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, order, operator FROM " + getTableName() + " WHERE table_mapping_id = ? ORDER BY table_mapping_id", this,tableMappingId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'base_report_id = :baseReportId'.
	 */
	@Transactional
	public List<ReportDataset> findWhereBaseReportIdEquals(Long baseReportId) throws ReportDatasetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, order, operator FROM " + getTableName() + " WHERE base_report_id = ? ORDER BY base_report_id", this,baseReportId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'base_report_version_id = :baseReportVersionId'.
	 */
	@Transactional
	public List<ReportDataset> findWhereBaseReportVersionIdEquals(Long baseReportVersionId) throws ReportDatasetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, order, operator FROM " + getTableName() + " WHERE base_report_version_id = ? ORDER BY base_report_version_id", this,baseReportVersionId);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'order = :order'.
	 */
	@Transactional
	public List<ReportDataset> findWhereOrderEquals(Integer order) throws ReportDatasetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, order, operator FROM " + getTableName() + " WHERE order = ? ORDER BY order", this,order);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_dataset table that match the criteria 'operator = :operator'.
	 */
	@Transactional
	public List<ReportDataset> findWhereOperatorEquals(Integer operator) throws ReportDatasetDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, design_id, design_version_id, table_mapping_id, base_report_id, base_report_version_id, order, operator FROM " + getTableName() + " WHERE operator = ? ORDER BY operator", this,operator);
		}
		catch (Exception e) {
			throw new ReportDatasetDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the report_dataset table that matches the specified primary-key value.
	 */
	public ReportDataset findByPrimaryKey(ReportDatasetPk pk) throws ReportDatasetDaoException
	{
		return findByPrimaryKey( pk.getId(), pk.getDesignId(), pk.getDesignVersionId() );
	}

}
