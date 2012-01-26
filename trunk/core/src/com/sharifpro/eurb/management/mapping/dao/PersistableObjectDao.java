package com.sharifpro.eurb.management.mapping.dao;

import com.sharifpro.eurb.management.mapping.dao.PersistableObjectDao;
import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;
import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.eurb.management.mapping.model.PersistableObjectPk;

import java.util.Date;
import java.util.List;

public interface PersistableObjectDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return PersistableObjectPk
	 */
	public PersistableObjectPk insert(PersistableObject dto);

	/** 
	 * Updates a single row in the persistable_object table.
	 */
	public void update(PersistableObjectPk pk, PersistableObject dto) throws PersistableObjectDaoException;

	/** 
	 * Deletes a single row in the persistable_object table.
	 */
	public void delete(PersistableObjectPk pk) throws PersistableObjectDaoException;

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'id = :id'.
	 */
	public PersistableObject findByPrimaryKey(Long id) throws PersistableObjectDaoException;

	/** 
	 * Returns all rows from the persistable_object table that match the criteria ''.
	 */
	public List<PersistableObject> findAll() throws PersistableObjectDaoException;

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'creator = :creator'.
	 */
	public List<PersistableObject> findByUsers(String creator) throws PersistableObjectDaoException;

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'modifier = :modifier'.
	 */
	public List<PersistableObject> findByUsers2(String modifier) throws PersistableObjectDaoException;

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'id = :id'.
	 */
	public List<PersistableObject> findWhereIdEquals(Long id) throws PersistableObjectDaoException;

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'type = :objectType'.
	 */
	public List<PersistableObject> findWhereObjectTypeEquals(Integer type) throws PersistableObjectDaoException;

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'creator = :creator'.
	 */
	public List<PersistableObject> findWhereCreatorEquals(String creator) throws PersistableObjectDaoException;

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'create_date = :createDate'.
	 */
	public List<PersistableObject> findWhereCreateDateEquals(Date createDate) throws PersistableObjectDaoException;

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'modifier = :modifier'.
	 */
	public List<PersistableObject> findWhereModifierEquals(String modifier) throws PersistableObjectDaoException;

	/** 
	 * Returns all rows from the persistable_object table that match the criteria 'modify_date = :modifyDate'.
	 */
	public List<PersistableObject> findWhereModifyDateEquals(Date modifyDate) throws PersistableObjectDaoException;

	/** 
	 * Returns the rows from the persistable_object table that matches the specified primary-key value.
	 */
	public PersistableObject findByPrimaryKey(PersistableObjectPk pk) throws PersistableObjectDaoException;

}
