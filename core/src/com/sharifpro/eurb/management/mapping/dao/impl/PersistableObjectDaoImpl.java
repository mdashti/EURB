package com.sharifpro.eurb.management.mapping.dao.impl;

import com.sharifpro.eurb.PersistableObjectType;
import com.sharifpro.eurb.management.mapping.dao.PersistableObjectDao;
import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.eurb.management.mapping.model.PersistableObjectPk;
import com.sharifpro.transaction.annotation.TransactionalReadOnly;
import com.sharifpro.transaction.annotation.TransactionalReadWrite;
import com.sharifpro.util.PropertyProvider;
import com.sharifpro.util.SessionManager;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


@Repository
public class PersistableObjectDaoImpl extends AbstractDAO implements /*ParameterizedRowMapper<PersistableObject>,*/ PersistableObjectDao
{	
	
	public static final String TABLE_NAME = "persistable_object";
	public static final String TABLE_NAME_AND_INITIAL = TABLE_NAME + " p";
	public static final String TABLE_NAME_AND_INITIAL_AND_JOIN = " INNER JOIN " + TABLE_NAME_AND_INITIAL + " USING (id)";

	public static final String PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS = "p.id, p.obj_type, p.creator, p.create_date, p.modifier, p.modify_date";
	
	public static final int PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS_COUNT = 6;
	
	public static final PersistableObjectRowMapper PERSISTABLE_OBJECT_MAPPER = (new PersistableObjectDaoImpl()).new PersistableObjectRowMapper();

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return PersistableObjectPk
	 */
	@TransactionalReadWrite
	public PersistableObjectPk insert(final PersistableObject dto, PersistableObjectPk emptyPK)
	{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String INSERT_SQL = "INSERT INTO persistable_object ( obj_type, creator" + /*", create_date, modifier, modify_date" +*/ " ) VALUES ( ?, ?"+/*", ?, ?, ?"+*/" )";
		getJdbcTemplate().update(
			new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] {"id"});
					
					int i=0;
					ps.setInt(++i, dto.getObjectType());
					ps.setString(++i, SessionManager.getCurrentUserNameNotNull());
					/*Long now = new Date().getTime();
					ps.setTimestamp(++i, new java.sql.Timestamp(now));
					ps.setString(++i, dto.getCreator());
					ps.setTimestamp(++i, new java.sql.Timestamp(now));*/
					return ps;
				}
			}, keyHolder);
		emptyPK.setId( keyHolder.getKey().longValue() );
		dto.setId(emptyPK.getId());
		return emptyPK;
	}
	
	/**
	 * Adds a new row as a version object in persistable_object and
	 * @return id to use as a version id
	 */
	@TransactionalReadWrite
	public Long makeVersionId()
	{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String INSERT_SQL = "INSERT INTO persistable_object ( obj_type, creator" + /*", create_date, modifier, modify_date" +*/ " ) VALUES ( ?, ?"+/*", ?, ?, ?"+*/" )";
		getJdbcTemplate().update(
			new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] {"id"});
					
					int i=0;
					ps.setInt(++i, PersistableObjectType.getObjectTypeFor("Version"));
					ps.setString(++i, SessionManager.getCurrentUserNameNotNull());
					/*Long now = new Date().getTime();
					ps.setTimestamp(++i, new java.sql.Timestamp(now));
					ps.setString(++i, dto.getCreator());
					ps.setTimestamp(++i, new java.sql.Timestamp(now));*/
					return ps;
				}
			}, keyHolder);
		return keyHolder.getKey().longValue(); 
	}


	/** 
	 * Updates a single row in the persistable_object table.
	 */
	@TransactionalReadWrite
	public void update(PersistableObjectPk pk)
	{
		getJdbcTemplate().update("UPDATE persistable_object SET modifier = ?, modify_date = ? WHERE id = ?", SessionManager.getCurrentUserNameNotNull(),new Timestamp(new Date().getTime()),pk.getId());
	}

	/** 
	 * Deletes a single row in the persistable_object table.
	 */
	@TransactionalReadWrite
	public void delete(PersistableObjectPk pk)
	{
		getJdbcTemplate().update("DELETE FROM persistable_object WHERE id = ?",pk.getId());
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return TABLE_NAME;
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public PersistableObject findByPrimaryKey(Long id) throws PersistableObjectDaoException
	{
		try {
			List<PersistableObject> list = getJdbcTemplate().query("SELECT "
					+ PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS
					+ " FROM persistable_object p WHERE p.id = ?", new PersistableObjectRowMapper(),id);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria ''.
	 */
	@TransactionalReadOnly
	public List<PersistableObject> findAllObjects() throws PersistableObjectDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT " + PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + " FROM persistable_object ORDER BY id", new PersistableObjectRowMapper());
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'creator = :creator'.
	 */
	@TransactionalReadOnly
	public List<PersistableObject> findByCreator(String creator) throws PersistableObjectDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT " + PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + " FROM persistable_object WHERE creator = ?", new PersistableObjectRowMapper(),creator);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'modifier = :modifier'.
	 */
	@TransactionalReadOnly
	public List<PersistableObject> findByModifier(String modifier) throws PersistableObjectDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT " + PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + " FROM persistable_object WHERE modifier = ?", new PersistableObjectRowMapper(),modifier);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'id = :id'.
	 */
	@TransactionalReadOnly
	public List<PersistableObject> findWhereObjectIdEquals(Long id) throws PersistableObjectDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT " + PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + " FROM persistable_object WHERE id = ? ORDER BY id", new PersistableObjectRowMapper(),id);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'objType = :objectType'.
	 */
	@TransactionalReadOnly
	public List<PersistableObject> findWhereObjectTypeEquals(Integer objectType) throws PersistableObjectDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT " + PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + " FROM persistable_object WHERE obj_type = ? ORDER BY obj_type", new PersistableObjectRowMapper(),objectType);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'creator = :creator'.
	 */
	@TransactionalReadOnly
	public List<PersistableObject> findWhereCreatorEquals(String creator) throws PersistableObjectDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT " + PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + " FROM persistable_object WHERE creator = ? ORDER BY creator", new PersistableObjectRowMapper(),creator);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'create_date = :createDate'.
	 */
	@TransactionalReadOnly
	public List<PersistableObject> findWhereCreateDateEquals(Date createDate) throws PersistableObjectDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT " + PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + " FROM persistable_object WHERE create_date = ? ORDER BY create_date", new PersistableObjectRowMapper(),createDate);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'modifier = :modifier'.
	 */
	@TransactionalReadOnly
	public List<PersistableObject> findWhereModifierEquals(String modifier) throws PersistableObjectDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT " + PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + " FROM persistable_object WHERE modifier = ? ORDER BY modifier", new PersistableObjectRowMapper(),modifier);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'modify_date = :modifyDate'.
	 */
	@TransactionalReadOnly
	public List<PersistableObject> findWhereModifyDateEquals(Date modifyDate) throws PersistableObjectDaoException
	{
		try {
			return getJdbcTemplate().query("SELECT " + PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS + " FROM persistable_object WHERE modify_date = ? ORDER BY modify_date", new PersistableObjectRowMapper(),modifyDate);
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
		
	}

	/** 
	 * Returns the rows from the persistable_object table that matches the specified primary-key value.
	 */
	public PersistableObject findByPrimaryKey(PersistableObjectPk pk) throws PersistableObjectDaoException
	{
		return findByPrimaryKey( pk.getId() );
	}

	public class PersistableObjectRowMapper implements ParameterizedRowMapper<PersistableObject> {

		/**
		 * Method 'mapRow'
		 * 
		 * @param rs
		 * @param row
		 * @throws SQLException
		 * @return PersistableObject
		 */
		public PersistableObject mapRow(ResultSet rs, int row) throws SQLException
		{
			PersistableObject dto = new PersistableObject();
			dto.setId( new Long( rs.getLong(1) ) );
			dto.setObjectType( new Integer( rs.getInt(2) ) );
			dto.setCreator( rs.getString( 3 ) );
			dto.setCreateDate( rs.getTimestamp(4 ) );
			dto.setModifier( rs.getString( 5 ) );
			dto.setModifyDate( rs.getTimestamp(6 ) );
			return dto;
		}
		
		public PersistableObject mapRow(ResultSet rs, int row, PersistableObject emptyObject) throws SQLException
		{
			emptyObject.setId( new Long( rs.getLong(1) ) );
			emptyObject.setObjectType( new Integer( rs.getInt(2) ) );
			emptyObject.setCreator( rs.getString( 3 ) );
			emptyObject.setCreateDate( rs.getTimestamp(4 ) );
			emptyObject.setModifier( rs.getString( 5 ) );
			emptyObject.setModifyDate( rs.getTimestamp(6 ) );
			return emptyObject;
		}
	}

	@Override
	public List<PersistableObject> findByPrimaryKeys(List<Long> targetObjects) throws PersistableObjectDaoException {
		try {
			List<PersistableObject> list = new NamedParameterJdbcTemplate(getJdbcTemplate()).query("SELECT "
					+ PERSISTABLE_OBJECT_QUERY_FROM_COLUMNS
					+ " FROM persistable_object p WHERE p.id IN (:targetObjects)", Collections.singletonMap("targetObjects", targetObjects), new PersistableObjectRowMapper());
			return list;
		}
		catch (Exception e) {
			throw new PersistableObjectDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}
}
