package com.sharifpro.eurb.builder.dao;

import com.sharifpro.eurb.builder.exception.ObjectConfigDaoException;
import com.sharifpro.eurb.builder.model.ObjectConfig;
import com.sharifpro.eurb.builder.model.ObjectConfigPk;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;

import java.util.List;

public interface ObjectConfigDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return ObjectConfigPk
	 */
	public ObjectConfigPk insert(ObjectConfig dto) throws ObjectConfigDaoException;

	/** 
	 * Updates a single row in the object_config table.
	 */
	public void update(ObjectConfigPk pk, ObjectConfig dto) throws ObjectConfigDaoException;

	/** 
	 * Deletes a single row in the object_config table.
	 */
	public void delete(ObjectConfigPk pk) throws ObjectConfigDaoException;
	
	/**
	 * Deletes all given rows from the object_config table.
	 */
	public void deleteAll(List<ObjectConfigPk> pkList) throws ObjectConfigDaoException;

	/** 
	 * Returns all rows from the object_config table that match the criteria 'object_id = :objectId and key = :key '.
	 */
	public ObjectConfig findByPrimaryKey(Long objectId, String key) throws ObjectConfigDaoException;

	/** 
	 * Returns all rows from the object_config table that match the criteria ''.
	 */
	public List<ObjectConfig> findAll() throws ObjectConfigDaoException;
	
	/** 
	 * Returns all rows from the object_config table for given object.
	 */
	public List<ObjectConfig> findAll(PersistableObject object) throws ObjectConfigDaoException;
	
	
	/** 
	 * Counts all rows from the object_config table that match the criteria ''.
	 */
	public int countAll() throws ObjectConfigDaoException;
	
	/**
	 *	Counts all rows from the object_config table for given object.
	 */
	public int countAll(PersistableObject object) throws ObjectConfigDaoException;
	

	/** 
	 * Returns all rows from the object_config table that match the criteria 'id = :id'.
	 */
	public List<ObjectConfig> findByPersistableObject(Long objectId) throws ObjectConfigDaoException;

	
	/** 
	 * Returns the rows from the object_config table that matches the specified primary-key value.
	 */
	public ObjectConfig findByPrimaryKey(ObjectConfigPk pk) throws ObjectConfigDaoException;

}
