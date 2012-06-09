package com.sharifpro.eurb.builder.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.dao.ReportColumnDao;
import com.sharifpro.eurb.builder.exception.ReportColumnDaoException;
import com.sharifpro.eurb.builder.model.ReportColumn;
import com.sharifpro.eurb.builder.model.ReportColumnPk;
import com.sharifpro.eurb.builder.model.ReportDesign;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.dao.impl.ColumnMappingDaoImpl;
import com.sharifpro.eurb.management.mapping.dao.impl.PersistableObjectDaoImpl;
import com.sharifpro.eurb.management.mapping.model.ColumnMapping;
import com.sharifpro.util.PropertyProvider;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Transactional;

public class ReportColumnDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ReportColumn>, ReportColumnDao
{
	private final static String QUERY_FROM_COLUMNS = "r.dataset_id, r.design_id, r.design_version_id, r.col_type, r.column_mapping_id, r.report_column_id, r.col_order, " +
			"r.sort_order, r.sort_type, r.group_level, r.column_width, r.column_align, r.column_dir, r.column_header, r.is_custom, r.formula";
	private final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + ", " + ColumnMappingDaoImpl.QUERY_FROM_COLUMNS + " FROM " + getTableName() + " r " + " LEFT JOIN " + ColumnMappingDaoImpl.getTableName() + " o ON (r.column_mapping_id = o.id) " + " INNER JOIN " + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL + " ON (p.id = r.id)";
	private final static String COUNT_QUERY = "SELECT count(distinct(r.id)) FROM " + getTableName() + " r ";


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
			getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, dataset_id, design_id, design_version_id, col_type, column_mapping_id, report_column_id, col_order, sort_order, sort_type, group_level," +
					" column_width, column_align, column_dir, column_header, is_custom, formula ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",
					pk.getId(),dto.getDatasetId(),dto.getDesignId(),dto.getDesignVersionId(),dto.getColType(),dto.getColumnMappingId(),dto.getReportColumnId(),dto.getColOrder(),dto.getSortOrder(),dto.getSortType(),dto.getGroupLevel(),dto.getColumnWidth(),dto.getColumnAlign(),dto.getColumnDir(),dto.getColumnHeader(),dto.isCustom(),dto.getFormula());
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
			getJdbcTemplate().update("UPDATE " + getTableName() + " SET dataset_id = ?, col_type = ?, column_mapping_id = ?, " +
					"report_column_id = ?, col_order = ?, sort_order = ?, sort_type = ?, group_level = ?, column_width = ?, column_align = ?, column_dir = ?, " +
					"column_header = ?, is_custom = ?, formula = ? WHERE id = ? ",
					dto.getDatasetId(),dto.getColType(),dto.getColumnMappingId(),dto.getReportColumnId(),
					dto.getColOrder(),dto.getSortOrder(),dto.getSortType(),dto.getGroupLevel(),dto.getColumnWidth(),dto.getColumnAlign(),dto.getColumnDir(),
					dto.getColumnHeader(),dto.isCustom(),dto.getFormula(),pk.getId());
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
			getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE id = ? ",pk.getId());
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
		dto.setSortOrder( (Integer) rs.getObject(++i) );
		dto.setSortType( rs.getInt(++i)  );
		dto.setGroupLevel( (Integer) rs.getObject(++i) );
		if (rs.wasNull()) {
			dto.setGroupLevel( null );
		}

		dto.setColumnWidth( new Integer( rs.getInt(++i) ) );
		dto.setColumnAlign( rs.getString( ++i ) );
		dto.setColumnDir( rs.getString( ++i ) );
		dto.setColumnHeader( rs.getString( ++i ) );
		dto.setCustom( rs.getBoolean( ++i ) );
		dto.setFormula( rs.getString( ++i ) );
		
		dto.setColumnMapping(new ColumnMappingDaoImpl().mapRow(rs, row, new ColumnMapping(),i));
		dto.getColumnMapping().setId(dto.getColumnMappingId());
		
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
			List<ReportColumn> list = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.id = ? AND r.dataset_id = ? AND r.design_id = ? AND r.design_version_id = ?", this,id,datasetId,designId,designVersionId);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}
	
	/** 
	 * Returns all rows from the report_column table that match the criteria 'id = :id'.
	 */
	@Transactional
	public ReportColumn findByPrimaryKey(Long id) throws ReportColumnDaoException
	{
		try {
			List<ReportColumn> list = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.id = ? ", this,id);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " ORDER BY r.id, r.dataset_id, r.design_id, r.design_version_id", this);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.design_id = ? ORDER BY r.id, r.dataset_id, r.design_id, r.design_version_id", this, design.getId());
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}
	
	/** 
	 * Returns all rows from the report_column table that match the criteria 'design_id = :design.getId()'.
	 */
	@Transactional
	public List<ReportColumn> findAllSortByColOrder(ReportDesign design) throws ReportColumnDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.design_id = ? ORDER BY r.col_order, r.id", this, design.getId());
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
			return getJdbcTemplate().queryForInt(COUNT_QUERY + " WHERE r.design_id = ?", design.getId());
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.id = ?", this,id);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.column_mapping_id = ?", this,columnMappingId);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.report_column_id = ?", this,reportColumnId);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.dataset_id = ? AND r.design_id = ? AND r.design_version_id = ?", this,datasetId,designId,designVersionId);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.id = ? ORDER BY r.id", this,id);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.dataset_id = ? ORDER BY r.dataset_id", this,datasetId);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.design_id = ? ORDER BY r.design_id", this,designId);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.design_version_id = ? ORDER BY r.design_version_id", this,designVersionId);
		}
		catch (Exception e) {
			throw new ReportColumnDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the report_column table that match the criteria 'col_type = :colType'.
	 */
	@Transactional
	public List<ReportColumn> findWhereColTypeEquals(Integer colType) throws ReportColumnDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.col_type = ? ORDER BY r.col_type", this,colType);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.column_mapping_id = ? ORDER BY r.column_mapping_id", this,columnMappingId);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.report_column_id = ? ORDER BY r.report_column_id", this,reportColumnId);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.col_order = ? ORDER BY r.col_order", this,colOrder);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.sort_order = ? ORDER BY r.sort_order", this,sortOrder);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.sort_type = ? ORDER BY r.sort_type", this,sortType);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.group_level = ? ORDER BY r.group_level", this,groupLevel);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.column_width = ? ORDER BY r.column_width", this,columnWidth);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.column_align = ? ORDER BY r.column_align", this,columnAlign);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.column_dir = ? ORDER BY r.column_dir", this,columnDir);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.column_header = ? ORDER BY r.column_header", this,columnHeader);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.is_custom = ? ORDER BY r.is_custom", this,isCustom);
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
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE r.formula = ? ORDER BY r.formula", this,formula);
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
