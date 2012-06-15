package com.sharifpro.eurb.management.security.dao;

import com.sharifpro.eurb.management.security.exception.UserDaoException;
import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.eurb.management.security.model.User;
import com.sharifpro.eurb.management.security.model.UserPk;

import java.util.List;

public interface UserDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return UsersPk
	 */
	public UserPk insert(User dto);

	/** 
	 * Updates a single row in the users table.
	 */
	public void update(UserPk pk, User dto) throws UserDaoException;

	/** 
	 * Sets enabled flag for a single user in the users table.
	 */
	public void setEnabled(UserPk pk, boolean enabled) throws UserDaoException;
	
	/** 
	 * Sets password for a single user in the users table.
	 */
	public void setPassword(UserPk pk, User dto) throws UserDaoException;
	
	/** 
	 * Sets email for a single user in the users table.
	 */
	public void setEmail(UserPk pk, User dto) throws UserDaoException;
	/** 
	 * Deletes a single row in the users table.
	 */
	public void delete(UserPk pk) throws UserDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'username = :username'.
	 */
	public User findByPrimaryKey(Long id) throws UserDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria ''.
	 */
	public List<User> findAll() throws UserDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'id = :id'.
	 */
	public List<User> findByPersistableObject(Long id) throws UserDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'id = :id'.
	 */
	public List<User> findWhereIdEquals(Long id) throws UserDaoException;

	/** 
	 * Returns the row from the users table that match the criteria 'username = :username'.
	 */
	public User findWhereUsernameEquals(String username) throws UserDaoException;

	/** 
	 * Returns the row from the users table that match the criteria 'email = :email'.
	 */
	public User findByEmail(String email) throws UserDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'password = :password'.
	 */
	public List<User> findWherePasswordEquals(String password) throws UserDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'enabled = :enabled'.
	 */
	public List<User> findWhereEnabledEquals(Short enabled) throws UserDaoException;

	/** 
	 * Returns the rows from the users table that matches the specified primary-key value.
	 */
	public User findByPrimaryKey(UserPk pk) throws UserDaoException;

	/** 
	 * Counts all rows from the db_config table that match the criteria ''.
	 */
	public int countAll() throws UserDaoException;
	
	/** 
	 * Returns all rows from the db_config table that match the criteria '' limited by start and limit.
	 * @param dir 
	 * @param sort 
	 */
	public List<User> findAll(Integer start, Integer limit, String sort, String dir) throws UserDaoException;
	
	/** 
	 * Returns all rows from the db_config table that match the like query in onFields fields limited by start and limit.
	 * @param dir 
	 * @param sort 
	 */
	public List<User> findAll(String query, List<String> onFields, Integer start, Integer limit, String sort, String dir) throws UserDaoException;
	
	/** 
	 * Counts all rows from the db_config table that match the like query in onFields fields.
	 */
	public int countAll(String query, List<String> onFields) throws UserDaoException;

	/**
	 * Deletes multiple rows in the user table.
	 *
	 * @param pkList
	 * @throws UserDaoException 
	 */
	public void deleteAll(List<UserPk> pkList) throws UserDaoException;

	/** 
	 * Activates a single row in the db_config table.
	 */
	public void activate(UserPk pk) throws UserDaoException;
	
	/** 
	 * Activates multiple rows in the db_config table.
	 */
	public void activateAll(List<UserPk> pkList) throws UserDaoException;
	
	/** 
	 * Deactivates a single row in the db_config table.
	 */
	public void deactivate(UserPk pk) throws UserDaoException;
	
	/** 
	 * Deactivates multiple rows in the db_config table.
	 */
	public void deactivateAll(List<UserPk> pkList) throws UserDaoException;

	/**
	 * Finds current groups belonging to this user
	 * 
	 * @param groupId
	 * @return list of users for group
	 * @throws UserDaoException
	 */
	public List<User> findCurrentUsersForGroup(Long groupId) throws UserDaoException;

	/**
	 * Finds current groups not belonging to this user
	 * 
	 * @param groupId
	 * @return list of selectable users for this group
	 * @throws UserDaoException
	 */
	public List<User> findSelectableUsersForGroup(Long groupId) throws UserDaoException;

	public List<User> findAllActive() throws UserDaoException;

	public boolean userWithEmailExists(String exceptUsername, String email) throws UserDaoException;
	
	public boolean userWithEmailExists(String email) throws UserDaoException;

	public boolean userWithUsernameExists(String username) throws UserDaoException;
}
