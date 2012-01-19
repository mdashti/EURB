package com.sharifpro.eurb.dao.spring;

import com.sharifpro.eurb.dao.ReportFilterDao;
import com.sharifpro.eurb.dto.ReportFilter;
import com.sharifpro.eurb.dto.ReportFilterPk;
import com.sharifpro.eurb.exceptions.ReportFilterDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class ReportFilterDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportFilter>, ReportFilterDao
{
	protected SimpleJdbcTemplate jdbcTemplate;

	protected DataSource dataSource;

	/**
	 * Method 'setDataSource'
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportFilterPk
	 */
	@Transactional
	public ReportFilterPk insert(ReportFilter dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getId(),dto.getReportColumnId(),dto.getReportColumnDatasetId(),dto.getReportColumnDesignId(),dto.getReportColumnDesignVersionId(),dto.getPrefix(),dto.getOperator(),dto.getSuffix(),dto.getOperand1(),dto.getOperand2(),dto.getType(),dto.getOperand1ColumnId(),dto.getOperand1ColumnDatasetId(),dto.getOperand1ColumnDesignId());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the report_filter table.
	 */
	@Transactional
	public void update(ReportFilterPk pk, ReportFilter dto) throws ReportFilterDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET id = ?, report_column_id = ?, report_column_dataset_id = ?, report_column_design_id = ?, report_column_design_version_id = ?, prefix = ?, operator = ?, suffix = ?, operand1 = ?, operand2 = ?, type = ?, operand1_column_id = ?, operand1_column_dataset_id = ?, operand1_column_design_id = ? WHERE id = ?",dto.getId(),dto.getReportColumnId(),dto.getReportColumnDatasetId(),dto.getReportColumnDesignId(),dto.getReportColumnDesignVersionId(),dto.getPrefix(),dto.getOperator(),dto.getSuffix(),dto.getOperand1(),dto.getOperand2(),dto.getType(),dto.getOperand1ColumnId(),dto.getOperand1ColumnDatasetId(),dto.getOperand1ColumnDesignId(),pk.getId());
	}

	/** 
	 * Deletes a single row in the report_filter table.
	 */
	@Transactional
	public void delete(ReportFilterPk pk) throws ReportFilterDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return ReportFilter
	 */
	public ReportFilter mapRow(ResultSet rs, int row) throws SQLException
	{
		ReportFilter dto = new ReportFilter();
		dto.setId( new Long( rs.getLong(1) ) );
		dto.setReportColumnId( new Long( rs.getLong(2) ) );
		dto.setReportColumnDatasetId( new Long( rs.getLong(3) ) );
		dto.setReportColumnDesignId( new Long( rs.getLong(4) ) );
		dto.setReportColumnDesignVersionId( new Long( rs.getLong(5) ) );
		dto.setPrefix( rs.getString( 6 ) );
		dto.setOperator( rs.getString( 7 ) );
		dto.setSuffix( rs.getString( 8 ) );
		dto.setOperand1( rs.getString( 9 ) );
		dto.setOperand2( rs.getString( 10 ) );
		dto.setType( new Integer( rs.getInt(11) ) );
		if (rs.wasNull()) {
			dto.setType( null );
		}
		
		dto.setOperand1ColumnId( new Long( rs.getLong(12) ) );
		if (rs.wasNull()) {
			dto.setOperand1ColumnId( null );
		}
		
		dto.setOperand1ColumnDatasetId( new Long( rs.getLong(13) ) );
		if (rs.wasNull()) {
			dto.setOperand1ColumnDatasetId( null );
		}
		
		dto.setOperand1ColumnDesignId( new Long( rs.getLong(14) ) );
		if (rs.wasNull()) {
			dto.setOperand1ColumnDesignId( null );
		}
		
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "report_filter";
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'id = :id'.
	 */
	@Transactional
	public ReportFilter findByPrimaryKey(Long id) throws ReportFilterDaoException
	{
		try {
			List<ReportFilter> list = jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria ''.
	 */
	@Transactional
	public List<ReportFilter> findAll() throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " ORDER BY id", this);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportFilter> findByPersistableObject(Long id) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand1_column_id = :operand1ColumnId AND operand1_column_dataset_id = :operand1ColumnDatasetId AND operand1_column_design_id = :operand1ColumnDesignId'.
	 */
	@Transactional
	public List<ReportFilter> findByReportColumn(Long operand1ColumnId, Long operand1ColumnDatasetId, Long operand1ColumnDesignId) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE operand1_column_id = ? AND operand1_column_dataset_id = ? AND operand1_column_design_id = ?", this,operand1ColumnId,operand1ColumnDatasetId,operand1ColumnDesignId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'report_column_id = :reportColumnId AND report_column_dataset_id = :reportColumnDatasetId AND report_column_design_id = :reportColumnDesignId AND report_column_design_version_id = :reportColumnDesignVersionId'.
	 */
	@Transactional
	public List<ReportFilter> findByReportColumn2(Long reportColumnId, Long reportColumnDatasetId, Long reportColumnDesignId, Long reportColumnDesignVersionId) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE report_column_id = ? AND report_column_dataset_id = ? AND report_column_design_id = ? AND report_column_design_version_id = ?", this,reportColumnId,reportColumnDatasetId,reportColumnDesignId,reportColumnDesignVersionId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportFilter> findWhereIdEquals(Long id) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'report_column_id = :reportColumnId'.
	 */
	@Transactional
	public List<ReportFilter> findWhereReportColumnIdEquals(Long reportColumnId) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE report_column_id = ? ORDER BY report_column_id", this,reportColumnId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'report_column_dataset_id = :reportColumnDatasetId'.
	 */
	@Transactional
	public List<ReportFilter> findWhereReportColumnDatasetIdEquals(Long reportColumnDatasetId) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE report_column_dataset_id = ? ORDER BY report_column_dataset_id", this,reportColumnDatasetId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'report_column_design_id = :reportColumnDesignId'.
	 */
	@Transactional
	public List<ReportFilter> findWhereReportColumnDesignIdEquals(Long reportColumnDesignId) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE report_column_design_id = ? ORDER BY report_column_design_id", this,reportColumnDesignId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'report_column_design_version_id = :reportColumnDesignVersionId'.
	 */
	@Transactional
	public List<ReportFilter> findWhereReportColumnDesignVersionIdEquals(Long reportColumnDesignVersionId) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE report_column_design_version_id = ? ORDER BY report_column_design_version_id", this,reportColumnDesignVersionId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'prefix = :prefix'.
	 */
	@Transactional
	public List<ReportFilter> findWherePrefixEquals(String prefix) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE prefix = ? ORDER BY prefix", this,prefix);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operator = :operator'.
	 */
	@Transactional
	public List<ReportFilter> findWhereOperatorEquals(String operator) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE operator = ? ORDER BY operator", this,operator);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'suffix = :suffix'.
	 */
	@Transactional
	public List<ReportFilter> findWhereSuffixEquals(String suffix) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE suffix = ? ORDER BY suffix", this,suffix);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand1 = :operand1'.
	 */
	@Transactional
	public List<ReportFilter> findWhereOperand1Equals(String operand1) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE operand1 = ? ORDER BY operand1", this,operand1);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand2 = :operand2'.
	 */
	@Transactional
	public List<ReportFilter> findWhereOperand2Equals(String operand2) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE operand2 = ? ORDER BY operand2", this,operand2);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'type = :type'.
	 */
	@Transactional
	public List<ReportFilter> findWhereTypeEquals(Integer type) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE type = ? ORDER BY type", this,type);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand1_column_id = :operand1ColumnId'.
	 */
	@Transactional
	public List<ReportFilter> findWhereOperand1ColumnIdEquals(Long operand1ColumnId) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE operand1_column_id = ? ORDER BY operand1_column_id", this,operand1ColumnId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand1_column_dataset_id = :operand1ColumnDatasetId'.
	 */
	@Transactional
	public List<ReportFilter> findWhereOperand1ColumnDatasetIdEquals(Long operand1ColumnDatasetId) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE operand1_column_dataset_id = ? ORDER BY operand1_column_dataset_id", this,operand1ColumnDatasetId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand1_column_design_id = :operand1ColumnDesignId'.
	 */
	@Transactional
	public List<ReportFilter> findWhereOperand1ColumnDesignIdEquals(Long operand1ColumnDesignId) throws ReportFilterDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, report_column_id, report_column_dataset_id, report_column_design_id, report_column_design_version_id, prefix, operator, suffix, operand1, operand2, type, operand1_column_id, operand1_column_dataset_id, operand1_column_design_id FROM " + getTableName() + " WHERE operand1_column_design_id = ? ORDER BY operand1_column_design_id", this,operand1ColumnDesignId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the report_filter table that matches the specified primary-key value.
	 */
	public ReportFilter findByPrimaryKey(ReportFilterPk pk) throws ReportFilterDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

}
