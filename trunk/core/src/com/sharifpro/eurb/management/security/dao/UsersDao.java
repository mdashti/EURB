package com.sharifpro.eurb.management.security.dao;

import com.sharifpro.eurb.management.security.dao.UsersDao;
import com.sharifpro.eurb.management.security.exception.UsersDaoException;
import com.sharifpro.eurb.management.security.model.Users;
import com.sharifpro.eurb.management.security.model.UsersPk;

import java.util.List;

public interface UsersDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return UsersPk
	 */
	public UsersPk insert(Users dto);

	/** 
	 * Updates a single row in the users table.
	 */
	public void update(UsersPk pk, Users dto) throws UsersDaoException;

	/** 
	 * Deletes a single row in the users table.
	 */
	public void delete(UsersPk pk) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'username = :username'.
	 */
	public Users findByPrimaryKey(String username) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria ''.
	 */
	public List<Users> findAll() throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'id = :id'.
	 */
	public List<Users> findByPersistableObject(Long id) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'id = :id'.
	 */
	public List<Users> findWhereIdEquals(Long id) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'username = :username'.
	 */
	public List<Users> findWhereUsernameEquals(String username) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'password = :password'.
	 */
	public List<Users> findWherePasswordEquals(String password) throws UsersDaoException;

	/** 
	 * Returns all rows from the users table that match the criteria 'enabled = :enabled'.
	 */
	public List<Users> findWhereEnabledEquals(Short enabled) throws UsersDaoException;

	/** 
	 * Returns the rows from the users table that matches the specified primary-key value.
	 */
	public Users findByPrimaryKey(UsersPk pk) throws UsersDaoException;

}
