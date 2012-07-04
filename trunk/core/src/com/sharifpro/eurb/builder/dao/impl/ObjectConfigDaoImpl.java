package com.sharifpro.eurb.builder.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sharifpro.eurb.builder.dao.ObjectConfigDao;
import com.sharifpro.eurb.builder.exception.ObjectConfigDaoException;
import com.sharifpro.eurb.builder.model.ObjectConfig;
import com.sharifpro.eurb.builder.model.ObjectConfigPk;
import com.sharifpro.eurb.management.mapping.dao.impl.AbstractDAO;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.util.PropertyProvider;

@Repository
public class ObjectConfigDaoImpl extends AbstractDAO implements ParameterizedRowMapper<ObjectConfig>, ObjectConfigDao
{

	public final static String QUERY_FROM_COLUMNS = " object_id, config_key, value ";
	public final static String QUERY_SELECT_PART = "SELECT " + QUERY_FROM_COLUMNS + " FROM " + getTableName();

	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ObjectConfigPk
	 */
	@Transactional
	public ObjectConfigPk insert(ObjectConfig dto) throws ObjectConfigDaoException
	{
		try{
			ObjectConfigPk pk = new ObjectConfigPk(dto.getObjectId(), dto.getKey()); 
			getJdbcTemplate().update("INSERT INTO " + getTableName() + " ( object_id, config_key, value) VALUES ( ?, ?, ?)",
					pk.getObjectId(),pk.getKey(),dto.getValue());
			return pk;
		}
		catch (Exception e) {
			throw new ObjectConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Updates a single row in the object_config table.
	 */
	@Transactional
	public void update(ObjectConfigPk pk, ObjectConfig dto) throws ObjectConfigDaoException
	{
		try{
			getJdbcTemplate().update("UPDATE " + getTableName() + " SET  value = ? WHERE object_id = ? AND config_key = ?",dto.getValue(), pk.getObjectId(), pk.getKey());
		}
		catch (Exception e) {
			throw new ObjectConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Deletes a single row in the object_config table.
	 */
	@Transactional
	public void delete(ObjectConfigPk pk) throws ObjectConfigDaoException
	{
		try{
			getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE id = ? and config_key = ?",pk.getObjectId(), pk.getKey());
		}
		catch (Exception e) {
			throw new ObjectConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/**
	 * Deletes all given rows from the object_config table.
	 */
	@Transactional
	public void deleteAll(List<ObjectConfigPk> pkList) throws ObjectConfigDaoException
	{
		for(ObjectConfigPk pk : pkList){
			delete(pk);
		}
	}

	/**
	 * Method 'mapRow'
	 * 
	 * @param rs
	 * @param row
	 * @throws SQLException
	 * @return ObjectConfig
	 */
	public ObjectConfig mapRow(ResultSet rs, int row) throws SQLException
	{
		ObjectConfig dto = new ObjectConfig();
		dto.setObjectId(new Long( rs.getLong(1) ) );
		dto.setKey( rs.getString(2) );
		dto.setValue( rs.getString(3)  );
		return dto;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public static String getTableName()
	{
		return "object_config";
	}

	/** 
	 * Returns all rows from the object_config table that match the criteria 'object_id = :objectId and config_key = :key'.
	 */
	@Transactional(readOnly = true)
	public ObjectConfig findByPrimaryKey(Long objectId, String key) throws ObjectConfigDaoException
	{
		try {
			List<ObjectConfig> list = getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE object_id = ? and config_key = ?", this, objectId, key);
			return list.size() == 0 ? null : list.get(0);
		}
		catch (Exception e) {
			throw new ObjectConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	/** 
	 * Returns all rows from the object_config table that match the criteria ''.
	 */
	@Transactional(readOnly = true)
	public List<ObjectConfig> findAll() throws ObjectConfigDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " ORDER BY object_id, config_key", this);
		}
		catch (Exception e) {
			throw new ObjectConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Returns all rows from the object_config table for given object.
	 */

	@Transactional(readOnly = true)
	public List<ObjectConfig> findAll(PersistableObject object) throws ObjectConfigDaoException
	{
		return findByPersistableObject(object.getId());
	}

	/** 
	 * Counts all rows from the object_config table that match the criteria ''.
	 */

	@Transactional(readOnly = true)
	public int countAll() throws ObjectConfigDaoException
	{
		try{
			return getJdbcTemplate().queryForInt("SELECT COUNT(*) FROM " + getTableName());
		}
		catch (Exception e) {
			throw new ObjectConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

	/** 
	 * Counts all rows from the object_config table for given object.
	 */
	@Transactional(readOnly = true)
	public int countAll(PersistableObject object) throws ObjectConfigDaoException
	{
		try{
			return getJdbcTemplate().queryForInt("SELECT COUNT(distinct(config_key)) FROM" + getTableName() + " WHERE object_id = ?",object.getId());
		}
		catch (Exception e) {
			throw new ObjectConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}


	/** 
	 * Returns all rows from the object_config table that match the criteria 'object_id = :id'.
	 */
	@Transactional(readOnly = true)
	public List<ObjectConfig> findByPersistableObject(Long id) throws ObjectConfigDaoException
	{
		try {
			return getJdbcTemplate().query(QUERY_SELECT_PART + " WHERE object_id = ?", this, id);
		}
		catch (Exception e) {
			throw new ObjectConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}

	}

	
	/** 
	 * Returns the rows from the object_config table that matches the specified primary-key value.
	 */
	@Transactional(readOnly = true)
	public ObjectConfig findByPrimaryKey(ObjectConfigPk pk) throws ObjectConfigDaoException
	{
		return findByPrimaryKey( pk.getObjectId(), pk.getKey());
	}

	@Override
	public void insertAll(Long id, List<ObjectConfig> config)
			throws ObjectConfigDaoException {
		try{
			getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE object_id = ? ",id);
			for(ObjectConfig oc : config){
				insert(oc);
			}
		}
		catch (Exception e) {
			throw new ObjectConfigDaoException(PropertyProvider.QUERY_FAILED_MESSAGE, e);
		}
	}

}
