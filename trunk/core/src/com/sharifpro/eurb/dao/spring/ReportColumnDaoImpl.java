package com.sharifpro.eurb.dao.spring;

import com.sharifpro.eurb.dao.ReportColumnDao;
import com.sharifpro.eurb.dto.ReportColumn;
import com.sharifpro.eurb.dto.ReportColumnPk;
import com.sharifpro.eurb.exceptions.ReportColumnDaoException;
import java.util.List;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class ReportColumnDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportColumn>, ReportColumnDao
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
	 * @return ReportColumnPk
	 */
	@Transactional
	public ReportColumnPk insert(ReportColumn dto)
	{
		jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",dto.getId(),dto.getDatasetId(),dto.getDesignId(),dto.getDesignVersionId(),dto.getType(),dto.getColumnMappingId(),dto.getReportColumnId(),dto.getOrder(),dto.getSortOrder(),dto.isSortType(),dto.getGroupLevel(),dto.getColumnWidth(),dto.getColumnAlign(),dto.getColumnDir(),dto.getColumnHeader(),dto.isIsCustom(),dto.getFormula());
		return dto.createPk();
	}

	/** 
	 * Updates a single row in the report_column table.
	 */
	@Transactional
	public void update(ReportColumnPk pk, ReportColumn dto) throws ReportColumnDaoException
	{
		jdbcTemplate.update("UPDATE " + getTableName() + " SET id = ?, dataset_id = ?, design_id = ?, design_version_id = ?, type = ?, column_mapping_id = ?, report_column_id = ?, order = ?, sort_order = ?, sort_type = ?, group_level = ?, column_width = ?, column_align = ?, column_dir = ?, column_header = ?, is_custom = ?, formula = ? WHERE id = ? AND dataset_id = ? AND design_id = ? AND design_version_id = ?",dto.getId(),dto.getDatasetId(),dto.getDesignId(),dto.getDesignVersionId(),dto.getType(),dto.getColumnMappingId(),dto.getReportColumnId(),dto.getOrder(),dto.getSortOrder(),dto.isSortType(),dto.getGroupLevel(),dto.getColumnWidth(),dto.getColumnAlign(),dto.getColumnDir(),dto.getColumnHeader(),dto.isIsCustom(),dto.getFormula(),pk.getId(),pk.getDatasetId(),pk.getDesignId(),pk.getDesignVersionId());
	}

	/** 
	 * Deletes a single row in the report_column table.
	 */
	@Transactional
	public void delete(ReportColumnPk pk) throws ReportColumnDaoException
	{
		jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ? AND dataset_id = ? AND design_id = ? AND design_version_id = ?",pk.getId(),pk.getDatasetId(),pk.getDesignId(),pk.getDesignVersionId());
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return ReportColumn
	 */
	public ReportColumn mapRow(ResultSet rs, int row) throws SQLException
	{
		ReportColumn dto = new ReportColumn();
		dto.setId( new Long( rs.getLong(1) ) );
		dto.setDatasetId( new Long( rs.getLong(2) ) );
		dto.setDesignId( new Long( rs.getLong(3) ) );
		dto.setDesignVersionId( new Long( rs.getLong(4) ) );
		dto.setType( new Integer( rs.getInt(5) ) );
		dto.setColumnMappingId( new Long( rs.getLong(6) ) );
		if (rs.wasNull()) {
			dto.setColumnMappingId( null );
		}
		
		dto.setReportColumnId( new Long( rs.getLong(7) ) );
		if (rs.wasNull()) {
			dto.setReportColumnId( null );
		}
		
		dto.setOrder( new Integer( rs.getInt(8) ) );
		dto.setSortOrder( new Integer( rs.getInt(9) ) );
		dto.setSortType( rs.getBoolean( 10 ) );
		dto.setGroupLevel( new Integer( rs.getInt(11) ) );
		if (rs.wasNull()) {
			dto.setGroupLevel( null );
		}
		
		dto.setColumnWidth( new Integer( rs.getInt(12) ) );
		dto.setColumnAlign( rs.getString( 13 ) );
		dto.setColumnDir( rs.getString( 14 ) );
		dto.setColumnHeader( rs.getString( 15 ) );
		dto.setIsCustom( rs.getBoolean( 16 ) );
		dto.setFormula( rs.getString( 17 ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "report_column";
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'id = :id AND dataset_id = :datasetId AND design_id = :designId AND design_version_id = :designVersionId'.
	 */
	@Transactional
	public ReportColumn findByPrimaryKey(Long id, Long datasetId, Long designId, Long designVersionId) throws ReportColumnDaoException
	{
		try {
			List<ReportColumn> list = jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE id = ? AND dataset_id = ? AND design_id = ? AND design_version_id = ?", this,id,datasetId,designId,designVersionId);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria ''.
	 */
	@Transactional
	public List<ReportColumn> findAll() throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " ORDER BY id, dataset_id, design_id, design_version_id", this);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportColumn> findByPersistableObject(Long id) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE id = ?", this,id);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_mapping_id = :columnMappingId'.
	 */
	@Transactional
	public List<ReportColumn> findByColumnMapping(Long columnMappingId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE column_mapping_id = ?", this,columnMappingId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'report_column_id = :reportColumnId'.
	 */
	@Transactional
	public List<ReportColumn> findByReportColumn(Long reportColumnId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE report_column_id = ?", this,reportColumnId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'dataset_id = :datasetId AND design_id = :designId AND design_version_id = :designVersionId'.
	 */
	@Transactional
	public List<ReportColumn> findByReportDataset(Long datasetId, Long designId, Long designVersionId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE dataset_id = ? AND design_id = ? AND design_version_id = ?", this,datasetId,designId,designVersionId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportColumn> findWhereIdEquals(Long id) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE id = ? ORDER BY id", this,id);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'dataset_id = :datasetId'.
	 */
	@Transactional
	public List<ReportColumn> findWhereDatasetIdEquals(Long datasetId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE dataset_id = ? ORDER BY dataset_id", this,datasetId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'design_id = :designId'.
	 */
	@Transactional
	public List<ReportColumn> findWhereDesignIdEquals(Long designId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE design_id = ? ORDER BY design_id", this,designId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'design_version_id = :designVersionId'.
	 */
	@Transactional
	public List<ReportColumn> findWhereDesignVersionIdEquals(Long designVersionId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE design_version_id = ? ORDER BY design_version_id", this,designVersionId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'type = :type'.
	 */
	@Transactional
	public List<ReportColumn> findWhereTypeEquals(Integer type) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE type = ? ORDER BY type", this,type);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_mapping_id = :columnMappingId'.
	 */
	@Transactional
	public List<ReportColumn> findWhereColumnMappingIdEquals(Long columnMappingId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE column_mapping_id = ? ORDER BY column_mapping_id", this,columnMappingId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'report_column_id = :reportColumnId'.
	 */
	@Transactional
	public List<ReportColumn> findWhereReportColumnIdEquals(Long reportColumnId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE report_column_id = ? ORDER BY report_column_id", this,reportColumnId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'order = :order'.
	 */
	@Transactional
	public List<ReportColumn> findWhereOrderEquals(Integer order) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE order = ? ORDER BY order", this,order);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'sort_order = :sortOrder'.
	 */
	@Transactional
	public List<ReportColumn> findWhereSortOrderEquals(Integer sortOrder) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE sort_order = ? ORDER BY sort_order", this,sortOrder);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'sort_type = :sortType'.
	 */
	@Transactional
	public List<ReportColumn> findWhereSortTypeEquals(Short sortType) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE sort_type = ? ORDER BY sort_type", this,sortType);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'group_level = :groupLevel'.
	 */
	@Transactional
	public List<ReportColumn> findWhereGroupLevelEquals(Integer groupLevel) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE group_level = ? ORDER BY group_level", this,groupLevel);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_width = :columnWidth'.
	 */
	@Transactional
	public List<ReportColumn> findWhereColumnWidthEquals(Integer columnWidth) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE column_width = ? ORDER BY column_width", this,columnWidth);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_align = :columnAlign'.
	 */
	@Transactional
	public List<ReportColumn> findWhereColumnAlignEquals(String columnAlign) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE column_align = ? ORDER BY column_align", this,columnAlign);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_dir = :columnDir'.
	 */
	@Transactional
	public List<ReportColumn> findWhereColumnDirEquals(String columnDir) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE column_dir = ? ORDER BY column_dir", this,columnDir);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_header = :columnHeader'.
	 */
	@Transactional
	public List<ReportColumn> findWhereColumnHeaderEquals(String columnHeader) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE column_header = ? ORDER BY column_header", this,columnHeader);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'is_custom = :isCustom'.
	 */
	@Transactional
	public List<ReportColumn> findWhereIsCustomEquals(Short isCustom) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE is_custom = ? ORDER BY is_custom", this,isCustom);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'formula = :formula'.
	 */
	@Transactional
	public List<ReportColumn> findWhereFormulaEquals(String formula) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query("SELECT id, dataset_id, design_id, design_version_id, type, column_mapping_id, report_column_id, order, sort_order, sort_type, group_level, column_width, column_align, column_dir, column_header, is_custom, formula FROM " + getTableName() + " WHERE formula = ? ORDER BY formula", this,formula);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException("Query failed", e);
		}
		
	}

	/** 
	 * Returns the rows from the report_column table that matches the specified primary-key value.
	 */
	public ReportColumn findByPrimaryKey(ReportColumnPk pk) throws ReportColumnDaoException
	{
		return findByPrimaryKey( pk.getId(), pk.getDatasetId(), pk.getDesignId(), pk.getDesignVersionId() );
	}

}
