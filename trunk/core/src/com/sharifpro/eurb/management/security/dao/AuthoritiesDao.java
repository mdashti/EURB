package com.sharifpro.eurb.management.security.dao;

import com.sharifpro.eurb.management.security.dao.AuthoritiesDao;
import com.sharifpro.eurb.management.security.exception.AuthoritiesDaoException;
import com.sharifpro.eurb.management.security.model.Authorities;
import com.sharifpro.eurb.management.security.model.AuthoritiesPk;

import java.util.List;

public interface AuthoritiesDao
{
	/**
	 * Method 'insert'
	 * 
	 * @param dto
	 * @return AuthoritiesPk
	 */
	public AuthoritiesPk insert(Authorities dto);

	/** 
	 * Updates a single row in the authorities table.
	 */
	public void update(AuthoritiesPk pk, Authorities dto) throws AuthoritiesDaoException;

	/** 
	 * Deletes a single row in the authorities table.
	 */
	public void delete(AuthoritiesPk pk) throws AuthoritiesDaoException;

	/** 
	 * Returns all rows from the authorities table that match the criteria 'username = :username AND authority = :authority'.
	 */
	public Authorities findByPrimaryKey(String username, String authority) throws AuthoritiesDaoException;

	/** 
	 * Returns all rows from the authorities table that match the criteria ''.
	 */
	public List<Authorities> findAll() throws AuthoritiesDaoException;

	/** 
	 * Returns all rows from the authorities table that match the criteria 'username = :username'.
	 */
	public List<Authorities> findByUsers(String username) throws AuthoritiesDaoException;

	/** 
	 * Returns all rows from the authorities table that match the criteria 'username = :username'.
	 */
	public List<Authorities> findWhereUsernameEquals(String username) throws AuthoritiesDaoException;

	/** 
	 * Returns all rows from the authorities table that match the criteria 'authority = :authority'.
	 */
	public List<Authorities> findWhereAuthorityEquals(String authority) throws AuthoritiesDaoException;

	/** 
	 * Returns the rows from the authorities table that matches the specified primary-key value.
	 */
	public Authorities findByPrimaryKey(AuthoritiesPk pk) throws AuthoritiesDaoException;

}
