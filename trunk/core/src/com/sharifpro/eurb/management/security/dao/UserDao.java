package com.sharifpro.eurb.management.security.dao;

import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.eurb.management.security.exception.UserDaoException;
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
	public void setEnabled(UserPk pk, User dto) throws UserDaoException;
	
	/** 
	 * Sets password for a single user in the users table.
	 */
	public void setPassword(UserPk pk, User dto) throws UserDaoException;
	/** 
	 * Deletes a single row in the users table.
	 */
	public void delete(UserPk pk) throws UserDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'username = :username'.
	 */
	public User findByPrimaryKey(String username) throws UserDaoException;

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
	 * Returns all rows from the users table that match the criteria 'username = :username'.
	 */
	public List<User> findWhereUsernameEquals(String username) throws UserDaoException;

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

}
