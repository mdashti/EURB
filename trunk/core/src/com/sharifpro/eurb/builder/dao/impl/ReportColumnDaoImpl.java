package com.sharifpro.eurb.builder.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.ReportColumnDao;
import com.sharifpro.eurb.builder.exception.ReportColumnDaoException;
import com.sharifpro.eurb.builder.model.ReportColumn;
import com.sharifpro.eurb.builder.model.ReportColumnPk;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.dao.impl.PersistableObjectDaoImpl;
import com.sharifpro.util.PropertyProvider;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class ReportColumnDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportColumn>, ReportColumnDao
{
	private final static String QUERY_FROM_COLUMNS = "o.dataset_id, o.design_id, o.design_version_id, o.col_type, o.column_mapping_id, o.report_column_id, o.col_order, " +
			"o.sort_order, o.sort_type, o.group_level, o.column_width, o.column_align, o.column_dir, o.column_header, o.is_custom, o.formula";
	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL_AND_JOIN;
	private final static String COUNT_QUERY = "SELECT count(distinct(o.id)) FROM " + getTableName() + " o ";


	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ReportColumnPk
	 */
	@Transactional
	public ReportColumnPk insert(ReportColumn dto) throws ReportColumnDaoException
	{
		try{
			ReportColumnPk pk = new ReportColumnPk();
			DaoFactory.createPersistableObjectDao().insert(dto, pk);
			jdbcTemplate.update("INSERT INTO " + getTableName() + " ( id, dataset_id, design_id, design_version_id, col_type, column_mapping_id, report_column_id, col_order, sort_order, sort_type, group_level," +
					" column_width, column_align, column_dir, column_header, is_custom, formula ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",
					pk.getId(),dto.getDatasetId(),dto.getDesignId(),dto.getDesignVersionId(),dto.getColType(),dto.getColumnMappingId(),dto.getReportColumnId(),dto.getColOrder(),dto.getSortOrder(),dto.isSortType(),dto.getGroupLevel(),dto.getColumnWidth(),dto.getColumnAlign(),dto.getColumnDir(),dto.getColumnHeader(),dto.isCustom(),dto.getFormula());
			return pk;
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Updates a single row in the report_column table.
	 */
	@Transactional
	public void update(ReportColumnPk pk, ReportColumn dto) throws ReportColumnDaoException
	{
		try{
			DaoFactory.createPersistableObjectDao().update(pk);
			jdbcTemplate.update("UPDATE " + getTableName() + " SET id = ?, dataset_id = ?, design_id = ?, design_version_id = ?, col_type = ?, column_mapping_id = ?, report_column_id = ?, col_order = ?, " +
					"sort_order = ?, sort_type = ?, group_level = ?, column_width = ?, column_align = ?, column_dir = ?, column_header = ?, is_custom = ?, formula = ? WHERE id = ? AND dataset_id = ? AND " +
					"design_id = ? AND design_version_id = ?",dto.getId(),dto.getDatasetId(),dto.getDesignId(),dto.getDesignVersionId(),dto.getColType(),dto.getColumnMappingId(),dto.getReportColumnId(),
					dto.getColOrder(),dto.getSortOrder(),dto.isSortType(),dto.getGroupLevel(),dto.getColumnWidth(),dto.getColumnAlign(),dto.getColumnDir(),dto.getColumnHeader(),dto.isCustom(),dto.getFormula(),
					pk.getId(),pk.getDatasetId(),pk.getDesignId(),pk.getDesignVersionId());
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Deletes a single row in the report_column table.
	 */
	@Transactional
	public void delete(ReportColumnPk pk) throws ReportColumnDaoException
	{
		try{
			jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ? ",pk.getId());
			DaoFactory.createPersistableObjectDao().delete(pk);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/**
	 * Deletes all given records from the report_column table.
	 */
	@Transactional
	public void deleteAll(List<ReportColumnPk> pkList) throws ReportColumnDaoException
	{
		for(ReportColumnPk pk : pkList){
			delete(pk);
		}
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
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		dto.setDatasetId( new Long( rs.getLong(++i) ) );
		dto.setDesignId( new Long( rs.getLong(++i) ) );
		dto.setDesignVersionId( new Long( rs.getLong(++i) ) );
		dto.setColType( new Integer( rs.getInt(++i) ) );
		dto.setColumnMappingId( new Long( rs.getLong(++i) ) );
		if (rs.wasNull()) {
			dto.setColumnMappingId( null );
		}

		dto.setReportColumnId( new Long( rs.getLong(++i) ) );
		if (rs.wasNull()) {
			dto.setReportColumnId( null );
		}

		dto.setColOrder( new Integer( rs.getInt(++i) ) );
		dto.setSortOrder( new Integer( rs.getInt(++i) ) );
		dto.setSortType( rs.getBoolean( ++i ) );
		dto.setGroupLevel( new Integer( rs.getInt(++i) ) );
		if (rs.wasNull()) {
			dto.setGroupLevel( null );
		}

		dto.setColumnWidth( new Integer( rs.getInt(++i) ) );
		dto.setColumnAlign( rs.getString( ++i ) );
		dto.setColumnDir( rs.getString( ++i ) );
		dto.setColumnHeader( rs.getString( ++i ) );
		dto.setCustom( rs.getBoolean( ++i ) );
		dto.setFormula( rs.getString( ++i ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
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
			List<ReportColumn> list = jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.id = ? AND o.dataset_id = ? AND o.design_id = ? AND o.design_version_id = ?", this,id,datasetId,designId,designVersionId);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria ''.
	 */
	@Transactional
	public List<ReportColumn> findAll() throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " ORDER BY o.id, o.dataset_id, o.design_id, o.design_version_id", this);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}


	/** 
	 * Returns all rows from the report_column table that match the criteria 'design_id = :design.getId()'.
	 */
	@Transactional
	public List<ReportColumn> findAll(ReportDesign design) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.design_id = ? ORDER BY o.id, o.dataset_id, o.design_id, o.design_version_id", this, design.getId());
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/**
	 * Counts all rows from the report_column table that match the criteria 'design_id = "design.getId()'.
	 */
	@Transactional
	public int countAll(ReportDesign design) throws ReportColumnDaoException
	{
		try{
			return jdbcTemplate.queryForInt(COUNT_QUERY + " WHERE o.design_id = ?", design.getId());
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}


	/** 
	 * Returns all rows from the report_column table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportColumn> findByPersistableObject(Long id) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.id = ?", this,id);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_mapping_id = :columnMappingId'.
	 */
	@Transactional
	public List<ReportColumn> findByColumnMapping(Long columnMappingId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.column_mapping_id = ?", this,columnMappingId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'report_column_id = :reportColumnId'.
	 */
	@Transactional
	public List<ReportColumn> findByReportColumn(Long reportColumnId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.report_column_id = ?", this,reportColumnId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'dataset_id = :datasetId AND design_id = :designId AND design_version_id = :designVersionId'.
	 */
	@Transactional
	public List<ReportColumn> findByReportDataset(Long datasetId, Long designId, Long designVersionId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.dataset_id = ? AND o.design_id = ? AND o.design_version_id = ?", this,datasetId,designId,designVersionId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ReportColumn> findWhereIdEquals(Long id) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.id = ? ORDER BY o.id", this,id);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'dataset_id = :datasetId'.
	 */
	@Transactional
	public List<ReportColumn> findWhereDatasetIdEquals(Long datasetId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.dataset_id = ? ORDER BY o.dataset_id", this,datasetId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'design_id = :designId'.
	 */
	@Transactional
	public List<ReportColumn> findWhereDesignIdEquals(Long designId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.design_id = ? ORDER BY o.design_id", this,designId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'design_version_id = :designVersionId'.
	 */
	@Transactional
	public List<ReportColumn> findWhereDesignVersionIdEquals(Long designVersionId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.design_version_id = ? ORDER BY o.design_version_id", this,designVersionId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'col_type = :colTypeName'.
	 */
	@Transactional
	public List<ReportColumn> findWhereColTypeEquals(Integer colType) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.col_type = ? ORDER BY o.col_type", this,colType);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_mapping_id = :columnMappingId'.
	 */
	@Transactional
	public List<ReportColumn> findWhereColumnMappingIdEquals(Long columnMappingId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.column_mapping_id = ? ORDER BY o.column_mapping_id", this,columnMappingId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'report_column_id = :reportColumnId'.
	 */
	@Transactional
	public List<ReportColumn> findWhereReportColumnIdEquals(Long reportColumnId) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.report_column_id = ? ORDER BY o.report_column_id", this,reportColumnId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'col_order = :colOrder'.
	 */
	@Transactional
	public List<ReportColumn> findWhereColOrderEquals(Integer colOrder) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.col_order = ? ORDER BY o.col_order", this,colOrder);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'sort_order = :sortOrder'.
	 */
	@Transactional
	public List<ReportColumn> findWhereSortOrderEquals(Integer sortOrder) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.sort_order = ? ORDER BY o.sort_order", this,sortOrder);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'sort_type = :sortType'.
	 */
	@Transactional
	public List<ReportColumn> findWhereSortTypeEquals(Short sortType) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.sort_type = ? ORDER BY o.sort_type", this,sortType);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'group_level = :groupLevel'.
	 */
	@Transactional
	public List<ReportColumn> findWhereGroupLevelEquals(Integer groupLevel) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.group_level = ? ORDER BY o.group_level", this,groupLevel);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_width = :columnWidth'.
	 */
	@Transactional
	public List<ReportColumn> findWhereColumnWidthEquals(Integer columnWidth) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.column_width = ? ORDER BY o.column_width", this,columnWidth);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_align = :columnAlign'.
	 */
	@Transactional
	public List<ReportColumn> findWhereColumnAlignEquals(String columnAlign) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.column_align = ? ORDER BY o.column_align", this,columnAlign);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_dir = :columnDir'.
	 */
	@Transactional
	public List<ReportColumn> findWhereColumnDirEquals(String columnDir) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.column_dir = ? ORDER BY o.column_dir", this,columnDir);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'column_header = :columnHeader'.
	 */
	@Transactional
	public List<ReportColumn> findWhereColumnHeaderEquals(String columnHeader) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.column_header = ? ORDER BY o.column_header", this,columnHeader);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'is_custom = :isCustom'.
	 */
	@Transactional
	public List<ReportColumn> findWhereIsCustomEquals(Short isCustom) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.is_custom = ? ORDER BY o.is_custom", this,isCustom);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'formula = :formula'.
	 */
	@Transactional
	public List<ReportColumn> findWhereFormulaEquals(String formula) throws ReportColumnDaoException
	{
		try {
			return jdbcTemplate.query(QUERY_SELECT_PART + " WHERE o.formula = ? ORDER BY o.formula", this,formula);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
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
