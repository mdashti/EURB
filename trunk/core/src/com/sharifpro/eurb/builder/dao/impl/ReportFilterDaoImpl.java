package com.sharifpro.eurb.builder.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.ReportFilterDao;
import com.sharifpro.eurb.builder.exception.ReportFilterDaoException;
import com.sharifpro.eurb.builder.model.ReportFilter;
import com.sharifpro.eurb.builder.model.ReportFilterPk;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.dao.impl.PersistableObjectDaoImpl;
import com.sharifpro.util.PropertyProvider;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class ReportFilterDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportFilter>, ReportFilterDao
{

	private final static String QUERY_FROM_COLUMNS = "o.column_mapping_id, o.report_dataset_id, o.report_design_id, o.report_design_version_id, o.prefix, o.operator, o.suffix, o.operand1, o.operand2, " +
			"o.filter_type, o.operand1_column_mapping_id, o.operand1_dataset_id, o.operand1_report_column_id ";
	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + " o " + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;
	private final static String COUNT_QUERY = "SELECT count(distinct(o.id)) FROM " + getTableName() + " o ";

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportFilterPk
	 */
	@Transactional
	public ReportFilterPk insert(ReportFilter dto) throws ReportFilterDaoException
	{
		try{
			ReportFilterPk pk = new ReportFilterPk();
			DaoFactory.createPersistableObjectDao().insert(dto, pk);
			getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, column_mapping_id, report_dataset_id, report_design_id, report_design_version_id, prefix, operator, suffix, operand1, " +
					"operand2, filter_type, operand1_column_mapping_id, operand1_dataset_id, operand1_report_column_id ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",
					pk.getId(),dto.getColumnMappingId(),dto.getReportDatasetId(),dto.getReportDesignId(),dto.getReportDesignVersionId(),dto.getPrefix(),dto.getOperator(),dto.getSuffix(),dto.getOperand1(),
					dto.getOperand2(),dto.getFilterType(),dto.getOperand1ColumnMappingId(),dto.getOperand1DatasetId(),dto.getOperand1ReportColumnId());
			return pk;
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Updates a single row in the report_filter table.
	 */
	@Transactional
	public void update(ReportFilterPk pk, ReportFilter dto) throws ReportFilterDaoException
	{
		try{
			DaoFactory.createPersistableObjectDao().update(pk);
			getJdbcTemplate().update("UPDATE " + getTableName() + " SET id = ?, column_mapping_id = ?, report_dataset_id = ?, report_design_id = ?, report_design_version_id = ?, prefix = ?, operator = ?, " +
					"suffix = ?, operand1 = ?, operand2 = ?, filter_type = ?, operand1_column_mapping_id = ?, operand1_dataset_id = ?, operand1_report_column_id = ? WHERE id = ?",
					dto.getId(),dto.getColumnMappingId(),dto.getReportDatasetId(),dto.getReportDesignId(),dto.getReportDesignVersionId(),dto.getPrefix(),dto.getOperator(),dto.getSuffix(),dto.getOperand1(),
					dto.getOperand2(),dto.getFilterType(),dto.getOperand1ColumnMappingId(),dto.getOperand1DatasetId(),dto.getOperand1ReportColumnId(),pk.getId());
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Deletes a single row in the report_filter table.
	 */
	@Transactional
	public void delete(ReportFilterPk pk) throws ReportFilterDaoException
	{
		try{
			getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
			DaoFactory.createPersistableObjectDao().delete(pk);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}
	
	@Transactional
	public void deleteAll(List<ReportFilterPk> pkList) throws ReportFilterDaoException
	{
		for(ReportFilterPk pk : pkList){
			delete(pk);
		}
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
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setColumnMappingId( new Long( rs.getLong(++i) ) );
		dto.setReportDatasetId( new Long( rs.getLong(++i) ) );
		dto.setReportDesignId( new Long( rs.getLong(++i) ) );
		dto.setReportDesignVersionId( new Long( rs.getLong(++i) ) );
		dto.setPrefix( rs.getString( ++i ) );
		dto.setOperator( rs.getString( ++i ) );
		dto.setSuffix( rs.getString( ++i ) );
		dto.setOperand1( rs.getString( ++i ) );
		dto.setOperand2( rs.getString( ++i ) );
		dto.setFilterType( new Integer( rs.getInt(++i) ) );
		if (rs.wasNull()) {
			dto.setFilterType( null );
		}

		dto.setOperand1ColumnMappingId( new Long( rs.getLong(++i) ) );
		if (rs.wasNull()) {
			dto.setOperand1ColumnMappingId( null );
		}

		dto.setOperand1DatasetId( new Long( rs.getLong(++i) ) );
		if (rs.wasNull()) {
			dto.setOperand1DatasetId( null );
		}

		dto.setOperand1ReportColumnId( new Long( rs.getLong(++i) ) );
		if (rs.wasNull()) {
			dto.setOperand1ReportColumnId( null );
		}

		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
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
			List<ReportFilter> list = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria ''.
	 */
	@Transactional
	public List<ReportFilter> findAll() throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " ORDER BY o.id", this);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}
	
	/** 
	 * Returns all rows from the report_filter table that match the criteria 'report_design_id = :reportDesignId'.
	 */
	@Transactional
	public List<ReportFilter> findAll(Long reportDesignId) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.report_design_id = ? ORDER BY o.id", this, reportDesignId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}
	
	
	/** 
	 * Counts all rows from the report_filter table that match the criteria 'report_design_id = :reportDesignId'.
	 */
	@Transactional
	public int countAll(Long reportDesignId) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().queryForInt(COUNT_QUERY + " WHERE o.report_design_id = ? ", reportDesignId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportFilter> findByPersistableObject(Long id) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE p.id = ?", this,id);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand1_column_mapping_id = :operand1ColumnId AND operand1_dataset_id = :operand1ColumnDatasetId AND operand1_report_column_id = :operand1ColumnDesignId'.
	 */
	@Transactional
	public List<ReportFilter> findByReportColumn(Long operand1ColumnId, Long operand1ColumnDatasetId, Long operand1ColumnDesignId) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.operand1_column_mapping_id = ? AND o.operand1_dataset_id = ? AND o.operand1_report_column_id = ?", this,operand1ColumnId,operand1ColumnDatasetId,operand1ColumnDesignId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'column_mapping_id = :reportColumnId AND report_dataset_id = :reportColumnDatasetId AND report_design_id = :reportColumnDesignId AND report_design_version_id = :reportColumnDesignVersionId'.
	 */
	@Transactional
	public List<ReportFilter> findByReportColumn2(Long reportColumnId, Long reportColumnDatasetId, Long reportColumnDesignId, Long reportColumnDesignVersionId) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.column_mapping_id = ? AND o.report_dataset_id = ? AND o.report_design_id = ? AND o.report_design_version_id = ?", this,reportColumnId,reportColumnDatasetId,reportColumnDesignId,reportColumnDesignVersionId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportFilter> findWhereIdEquals(Long id) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ? ORDER BY o.id", this,id);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'column_mapping_id = :reportColumnId'.
	 */
	@Transactional
	public List<ReportFilter> findWhereReportColumnIdEquals(Long reportColumnId) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.column_mapping_id = ? ORDER BY o.column_mapping_id", this,reportColumnId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'report_dataset_id = :reportColumnDatasetId'.
	 */
	@Transactional
	public List<ReportFilter> findWhereReportColumnDatasetIdEquals(Long reportColumnDatasetId) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.report_dataset_id = ? ORDER BY o.report_dataset_id", this,reportColumnDatasetId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'report_design_id = :reportColumnDesignId'.
	 */
	@Transactional
	public List<ReportFilter> findWhereReportColumnDesignIdEquals(Long reportColumnDesignId) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.report_design_id = ? ORDER BY o.report_design_id", this,reportColumnDesignId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'report_design_version_id = :reportColumnDesignVersionId'.
	 */
	@Transactional
	public List<ReportFilter> findWhereReportColumnDesignVersionIdEquals(Long reportColumnDesignVersionId) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.report_design_version_id = ? ORDER BY o.report_design_version_id", this,reportColumnDesignVersionId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'prefix = :prefix'.
	 */
	@Transactional
	public List<ReportFilter> findWherePrefixEquals(String prefix) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.prefix = ? ORDER BY o.prefix", this,prefix);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operator = :operator'.
	 */
	@Transactional
	public List<ReportFilter> findWhereOperatorEquals(String operator) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.operator = ? ORDER BY o.operator", this,operator);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'suffix = :suffix'.
	 */
	@Transactional
	public List<ReportFilter> findWhereSuffixEquals(String suffix) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.suffix = ? ORDER BY o.suffix", this,suffix);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand1 = :operand1'.
	 */
	@Transactional
	public List<ReportFilter> findWhereOperand1Equals(String operand1) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.operand1 = ? ORDER BY o.operand1", this,operand1);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand2 = :operand2'.
	 */
	@Transactional
	public List<ReportFilter> findWhereOperand2Equals(String operand2) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.operand2 = ? ORDER BY o.operand2", this,operand2);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'filter_type = :filterType'.
	 */
	@Transactional
	public List<ReportFilter> findWhereFilterTypeEquals(Integer filterType) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.filter_type = ? ORDER BY o.filter_type", this,filterType);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand1_column_mapping_id = :operand1ColumnId'.
	 */
	@Transactional
	public List<ReportFilter> findWhereOperand1ColumnIdEquals(Long operand1ColumnId) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.operand1_column_mapping_id = ? ORDER BY o.operand1_column_mapping_id", this,operand1ColumnId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand1_dataset_id = :operand1ColumnDatasetId'.
	 */
	@Transactional
	public List<ReportFilter> findWhereOperand1ColumnDatasetIdEquals(Long operand1ColumnDatasetId) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.operand1_dataset_id = ? ORDER BY o.operand1_dataset_id", this,operand1ColumnDatasetId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_filter table that match the criteria 'operand1_report_column_id = :operand1ColumnDesignId'.
	 */
	@Transactional
	public List<ReportFilter> findWhereOperand1ColumnDesignIdEquals(Long operand1ColumnDesignId) throws ReportFilterDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.operand1_report_column_id = ? ORDER BY o.operand1_report_column_id", this,operand1ColumnDesignId);
		}
		catch (Exception e) {
			throw new ReportFilterDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
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
