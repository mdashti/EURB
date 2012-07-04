package com.sharifpro.eurb.management.mapping.dao.impl;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.builder.model.ReportDataset;
import com.sharifpro.eurb.info.RecordStatus;
import com.sharifpro.eurb.management.mapping.dao.ColumnMappingDao;
import com.sharifpro.eurb.management.mapping.exception.ColumnMappingDaoException;
import com.sharifpro.eurb.management.mapping.model.ColumnMapping;
import com.sharifpro.eurb.management.mapping.model.ColumnMappingPk;
import com.sharifpro.eurb.management.mapping.model.TableMapping;
import com.sharifpro.util.PropertyProvider;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ColumnMappingDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ColumnMapping>, ColumnMappingDao
{
	public final static String QUERY_FROM_COLUMNS = "o.db_config_id, o.table_mapping_id, o.column_name, o.mapped_name, o.col_type_name, o.col_data_type, o.col_order, o.format_pattern, o.static_mapping, o.referenced_table, o.referenced_id_col, o.referenced_value_col, o.active_for_manager, o.active_for_user";

	public final static String QUERY_SELECT_PART = "SELECT " + PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + ", " + QUERY_FROM_COLUMNS + " FROM " + getTableName() + " o  INNER JOIN " + DbConfigDaoImpl.getTableName() + " d ON (o.db_config_id=d.id AND d.record_status='" + RecordStatus.ACTIVE.getId() + "') INNER JOIN " + PersistableObjectDaoImpl.TABLE_NAME_AND_INITIAL + " ON (o.id=p.id)";;

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ColumnMappingPk
	 * @throws ColumnMappingDaoException 
	 */
	@Transactional
	public ColumnMappingPk insert(ColumnMapping dto) throws ColumnMappingDaoException
	{
		try{
		ColumnMappingPk pk = new ColumnMappingPk();
		DaoFactory.createPersistableObjectDao().insert(dto, pk);
		getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( id, db_config_id, table_mapping_id, column_name, mapped_name, col_type_name, col_data_type, col_order," +
				" format_pattern, static_mapping, active_for_manager, active_for_user ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )",
				pk.getId(),dto.getDbConfigId(),dto.getTableMappingId(),dto.getColumnName(),dto.getMappedName(),dto.getColTypeName(),dto.getColDataType(),
				dto.getColOrder(),dto.getFormatPattern(),dto.getStaticMapping(),dto.isActiveForManager(),dto.isActiveForUser());
		return pk;
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Updates a single row in the column_mapping table.
	 */
	@Transactional
	public void update(ColumnMappingPk pk, ColumnMapping dto) throws ColumnMappingDaoException
	{
		DaoFactory.createPersistableObjectDao().update(pk);
		getJdbcTemplate().update("UPDATE " + getTableName() + " SET db_config_id = ?, table_mapping_id = ?, column_name = ?, mapped_name = ?, col_type_name = ?, col_data_type = ?, col_order = ?, format_pattern = ?, static_mapping = ?, referenced_table = ?, referenced_id_col = ?, referenced_value_col = ?, active_for_manager = ?, active_for_user = ? WHERE id = ?",dto.getDbConfigId(),dto.getTableMappingId(),dto.getColumnName(),dto.getMappedName(),dto.getColTypeName(),dto.getColDataType(),dto.getColOrder(),dto.getFormatPattern(),dto.getStaticMapping(),dto.getReferencedTable(),dto.getReferencedIdCol(),dto.getReferencedValueCol(),dto.isActiveForManager(),dto.isActiveForUser(),pk.getId());
	}

	/** 
	 * Deletes a single row in the column_mapping table.
	 */
	@Transactional
	public void delete(ColumnMappingPk pk) throws ColumnMappingDaoException
	{
		getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE id = ?",pk.getId());
		DaoFactory.createPersistableObjectDao().delete(pk);
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return ColumnMapping
	 */
	public ColumnMapping mapRow(ResultSet rs, int row) throws SQLException
	{
		ColumnMapping dto = new ColumnMapping();
		PersistableObjectDaoImpl.PERSISTABLE_OBJECT_MAPPER.mapRow(rs, row, dto);
		int i = PersistableObjectDaoImpl.PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT;
		return mapRow(rs, row, dto, i);
	}
	
	public ColumnMapping mapRow(ResultSet rs, int row, ColumnMapping dto, int start) throws SQLException
	{
		int i = start;
		dto.setDbConfigId( new Long( rs.getLong(++i) ) );
		dto.setTableMappingId( new Long( rs.getLong(++i) ) );
		dto.setColumnName( rs.getString( ++i ) );
		dto.setMappedName( rs.getString( ++i ) );
		dto.setColTypeName( rs.getString( ++i ) );
		dto.setColDataType( rs.getInt( ++i ) );
		dto.setColOrder( rs.getInt( ++i ) );
		dto.setFormatPattern( rs.getString( ++i ) );
		dto.setStaticMapping( rs.getString( ++i ) );
		dto.setReferencedTable( new Long( rs.getLong( ++i ) ) );
		if(rs.wasNull()) {
			dto.setReferencedTable(null);
		}
		dto.setReferencedIdCol( new Long( rs.getLong( ++i ) ) );
		if(rs.wasNull()) {
			dto.setReferencedIdCol(null);
		}
		dto.setReferencedValueCol( new Long( rs.getLong( ++i ) ) );
		if(rs.wasNull()) {
			dto.setReferencedValueCol(null);
		}
		dto.setActiveForManager( rs.getBoolean( ++i ) );
		dto.setActiveForUser( rs.getBoolean( ++i ) );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "column_mapping";
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'id = :id'.
	 */
	@Transactional
	public ColumnMapping findByPrimaryKey(Long id) throws ColumnMappingDaoException
	{
		try {
			List<ColumnMapping> list = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ?", this,id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria ''.
	 */
	@Transactional
	public List<ColumnMapping> findAll() throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " ORDER BY o.id", this);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}
	
	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'mapped name is not null'.
	 */
	@Transactional
	public List<ColumnMapping> findAllMapped() throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.mapped_name IS NOT NULL ORDER BY o.id", this);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}
	
	
	@Transactional
	public List<ColumnMapping> findAllMapped(ReportDataset dataset) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.mapped_name IS NOT NULL AND o.table_mapping_id = ?  ORDER BY o.id", this, dataset.getTableMappingId());
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ColumnMapping> findByPersistableObject(Long id) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ?", this,id);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'table_mapping_id = :tableMappingId'.
	 */
	@Transactional
	public List<ColumnMapping> findByTableMapping(Long tableMappingId) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.table_mapping_id = ?", this,tableMappingId);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}
	
	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'table_mapping_id = :tableMappingId and mapped_name is not null'.
	 */
	@Transactional
	public List<ColumnMapping> findMappedByTableMapping(Long tableMappingId) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.table_mapping_id = ? AND o.mapped_name IS NOT NULL ", this,tableMappingId);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'id = :id'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereIdEquals(Long id) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.id = ? ORDER BY o.id", this,id);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'table_mapping_id = :tableMappingId'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereTableMappingIdEquals(Long tableMappingId) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.table_mapping_id = ? ORDER BY o.colOrder", this,tableMappingId);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'column_name = :columnName'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereColumnNameEquals(String columnName) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.column_name = ? ORDER BY o.column_name", this,columnName);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'mapped_name = :mappedName'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereMappedNameEquals(String mappedName) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.mapped_name = ? ORDER BY o.mapped_name", this,mappedName);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'col_type_name = :colTypeName'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereColTypeEquals(String colTypeName) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.col_type_name = ? ORDER BY o.col_type_name", this,colTypeName);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'col_data_type = :colDataType'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereColDataTypeEquals(int colDataType) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.col_data_type = ? ORDER BY o.col_data_type", this,colDataType);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'col_order = :colOrder'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereColOrderEquals(String colOrder) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.col_order = ? ORDER BY o.col_order", this,colOrder);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'format_pattern = :formatPattern'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereFormatPatternEquals(String formatPattern) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.format_pattern = ? ORDER BY o.format_pattern", this,formatPattern);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'static_mapping = :staticMapping'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereStaticMappingEquals(String staticMapping) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.static_mapping = ? ORDER BY o.static_mapping", this,staticMapping);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'referenced_table = :referencedTable'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereReferencedTableEquals(String referencedTable) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.referenced_table = ? ORDER BY o.referenced_table", this,referencedTable);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'referenced_id_col = :referencedIdCol'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereReferencedIdColEquals(String referencedIdCol) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.referenced_id_col = ? ORDER BY o.referenced_id_col", this,referencedIdCol);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the column_mapping table that match the criteria 'referenced_value_col = :referencedValueCol'.
	 */
	@Transactional
	public List<ColumnMapping> findWhereReferencedValueColEquals(String referencedValueCol) throws ColumnMappingDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.referenced_value_col = ? ORDER BY o.referenced_value_col", this,referencedValueCol);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns the rows from the column_mapping table that matches the specified primary-key value.
	 */
	public ColumnMapping findByPrimaryKey(ColumnMappingPk pk) throws ColumnMappingDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	@Transactional
	public List<ColumnMapping> findAll(TableMapping tbl, String query, List<String> onFields) throws ColumnMappingDaoException {
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.table_mapping_id=? AND (" + getMultipleFieldWhereClause(query, onFields) + ") ORDER BY o.col_order", this, tbl.getId());
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}
	
	
	@Transactional
	public List<ColumnMapping> findAllMapped(TableMapping tbl, String query, List<String> onFields) throws ColumnMappingDaoException {
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.table_mapping_id=? AND o.mapped_name IS NOT NULL AND (" + getMultipleFieldWhereClause(query, onFields) + ") ORDER BY o.col_order", this, tbl.getId());
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	
	@Transactional
	public List<ColumnMapping> findAllMapped(String query, List<String> onFields) throws ColumnMappingDaoException {
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.mapped_name IS NOT NULL AND (" + getMultipleFieldWhereClause(query, onFields) + ") ORDER BY o.col_order", this);
		}
		catch (Exception e) {
			throw new ColumnMappingDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	
	private String getMultipleFieldWhereClause(String txt,
			List<String> fields) {
		StringBuilder query = new StringBuilder();
		String likeQuery = " LIKE '%"+txt+"%' OR ";
		if(fields.contains("colTypeName")) {
			query.append("o.col_type_name").append(likeQuery);
		}
		if(fields.contains("columnName")) {
			query.append("o.column_name").append(likeQuery);
		}
		if(fields.contains("formatPattern")) {
			query.append("o.format_pattern").append(likeQuery);
		}
		if(fields.contains("mappedName")) {
			query.append("o.mapped_name").append(likeQuery);
		}
		if(fields.contains("referencedIdCol")) {
			query.append("o.referenced_id_col").append(likeQuery);
		}
		if(fields.contains("referencedTable")) {
			query.append("o.referenced_table").append(likeQuery);
		}
		if(fields.contains("referencedValueCol")) {
			query.append("o.referenced_value_col").append(likeQuery);
		}
		if(fields.contains("colDataType")) {
			query.append("o.col_data_type").append(likeQuery);
		}
		if(fields.contains("colOrder")) {
			query.append("o.col_order").append(likeQuery);
		}
		if(fields.contains("staticMapping")) {
			query.append("o.static_mapping").append(likeQuery);
		}
		if(query.length() > 0) {
			return query.substring(0,query.length()-3);
		} else {
			return "";
		}
	}

	@Transactional
	public void deleteAll(List<ColumnMappingPk> pkList) throws ColumnMappingDaoException {
		for(ColumnMappingPk pk : pkList) {
			delete(pk);
		}
	}

	@Transactional
	public void activateAll(final List<ColumnMappingPk> pkList, String target)
			throws ColumnMappingDaoException {
		final String field = "user".equals(target) ? "active_for_user" : "active_for_manager";
		getJdbcTemplate()
				.batchUpdate(
						"UPDATE " + getTableName() + " SET "+field+" = 1 WHERE id = ?",
						new BatchPreparedStatementSetter() {
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setLong(1, pkList.get(i).getId());
							}

							public int getBatchSize() {
								return pkList.size();
							}
						});
	}

	@Transactional
	public void deactivateAll(final List<ColumnMappingPk> pkList, String target)
			throws ColumnMappingDaoException {
		final String field = "user".equals(target) ? "active_for_user" : "active_for_manager";
		getJdbcTemplate()
				.batchUpdate(
						"UPDATE " + getTableName() + " SET "+field+" = 0 WHERE id = ?",
						new BatchPreparedStatementSetter() {
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setLong(1, pkList.get(i).getId());
							}

							public int getBatchSize() {
								return pkList.size();
							}
						});
	}

	@Transactional
	public void moveUp(ColumnMappingPk pk) throws ColumnMappingDaoException {
		ColumnMapping thiz = findByPrimaryKey(pk);
		ColumnMapping that;
		List<ColumnMapping> columnMappings =  getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.col_order < ? and o.db_config_id = ? and o.table_mapping_id = ? ORDER BY o.col_order DESC LIMIT 1", this,thiz.getColOrder(), thiz.getDbConfigId(), thiz.getTableMappingId());

		if(columnMappings != null && !columnMappings.isEmpty()) {
			that = columnMappings.get(0);
			int tmpColOrder = thiz.getColOrder();
			thiz.setColOrder(that.getColOrder());
			that.setColOrder(tmpColOrder);
			
			update(thiz.createPk(), thiz);
			update(that.createPk(), that);
		}
		
	}

	@Transactional
	public void moveDown(ColumnMappingPk pk) throws ColumnMappingDaoException {
		ColumnMapping thiz = findByPrimaryKey(pk);
		ColumnMapping that;
		List<ColumnMapping> columnMappings =  getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE o.col_order > ? and o.db_config_id = ? and o.table_mapping_id = ? ORDER BY o.col_order ASC LIMIT 1", this,thiz.getColOrder(), thiz.getDbConfigId(), thiz.getTableMappingId());

		if(columnMappings != null && !columnMappings.isEmpty()) {
			that = columnMappings.get(0);
			int tmpColOrder = thiz.getColOrder();
			thiz.setColOrder(that.getColOrder());
			that.setColOrder(tmpColOrder);
			
			update(thiz.createPk(), thiz);
			update(that.createPk(), that);
		}
	}

}
