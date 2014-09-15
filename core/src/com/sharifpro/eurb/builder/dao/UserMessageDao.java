package com.sharifpro.eurb.builder.dao;

import com.sharifpro.eurb.builder.dao.UserMessageDao;
import com.sharifpro.eurb.builder.exception.UserMessageDaoException;
import com.sharifpro.eurb.builder.model.UserMessage;
import com.sharifpro.eurb.builder.model.UserMessagePk;

import java.util.List;

public interface UserMessageDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return UserMessagePk
	 */
	public UserMessagePk insert(UserMessage dto) throws UserMessageDaoException;

	/** 
	 * Updates a single row in the user_message table.
	 */
	public void update(UserMessagePk pk, UserMessage dto) throws UserMessageDaoException;

	/** 
	 * Deletes a single row in the user_message table.
	 */
	public void delete(String username, UserMessagePk pk) throws UserMessageDaoException;
	
	/** 
	 * Deletes multiple rows in the user_message table.
	 * @param username 
	 */
	public void deleteAll(String username, List<UserMessagePk> pkList) throws UserMessageDaoException;
	
	/** 
	 * Returns all rows from the user_message table that match the criteria 'parent_column_id = :parentColumn.getId()'.
	 */
	public List<UserMessage> findAllForUser(String username) throws UserMessageDaoException;

	

	/** 
	 * Returns all rows from the user_message table that match the criteria 'id = :id'.
	 */
	public UserMessage findByPrimaryKey(Long id) throws UserMessageDaoException;

	
	/** 
	 * Returns all rows from the user_message table that match the criteria 'id = :id'.
	 */
	public List<UserMessage> findByPersistableObject(Long id) throws UserMessageDaoException;

	
	/** 
	 * Returns all rows from the user_message table that match the criteria 'id = :id'.
	 */
	public List<UserMessage> findWhereIdEquals(Long id) throws UserMessageDaoException;

		/** 
	 * Returns the rows from the user_message table that matches the specified primary-key value.
	 */
	public UserMessage findByPrimaryKey(UserMessagePk pk) throws UserMessageDaoException;

}
