package com.sharifpro.eurb.example;

import java.util.List;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.security.dao.UsersDao;
import com.sharifpro.eurb.management.security.model.Users;

public class UsersDaoExample
{
	/**
	 * Method 'main'
	 * 
	 * @param arg
	 * @throws Exception
	 */
	public static void main(String[] arg) throws Exception
	{
		// Uncomment one of the lines below to test the generated code
		
		findAll();
		// findByPersistableObject(null);
		// findWhereIdEquals(null);
		// findWhereUsernameEquals("");
		// findWherePasswordEquals("");
		// findWhereEnabledEquals(null);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		UsersDao dao = DaoFactory.createUsersDao();
		List<Users> _result = dao.findAll();
		for (Users dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByPersistableObject'
	 * 
	 * @param id
	 * @throws Exception
	 */
	public static void findByPersistableObject(Long id) throws Exception
	{
		UsersDao dao = DaoFactory.createUsersDao();
		List<Users> _result = dao.findByPersistableObject(id);
		for (Users dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereIdEquals'
	 * 
	 * @param id
	 * @throws Exception
	 */
	public static void findWhereIdEquals(Long id) throws Exception
	{
		UsersDao dao = DaoFactory.createUsersDao();
		List<Users> _result = dao.findWhereIdEquals(id);
		for (Users dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereUsernameEquals'
	 * 
	 * @param username
	 * @throws Exception
	 */
	public static void findWhereUsernameEquals(String username) throws Exception
	{
		UsersDao dao = DaoFactory.createUsersDao();
		List<Users> _result = dao.findWhereUsernameEquals(username);
		for (Users dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWherePasswordEquals'
	 * 
	 * @param password
	 * @throws Exception
	 */
	public static void findWherePasswordEquals(String password) throws Exception
	{
		UsersDao dao = DaoFactory.createUsersDao();
		List<Users> _result = dao.findWherePasswordEquals(password);
		for (Users dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereEnabledEquals'
	 * 
	 * @param enabled
	 * @throws Exception
	 */
	public static void findWhereEnabledEquals(Short enabled) throws Exception
	{
		UsersDao dao = DaoFactory.createUsersDao();
		List<Users> _result = dao.findWhereEnabledEquals(enabled);
		for (Users dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(Users dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getUsername() );
		buf.append( ", " );
		buf.append( dto.getPassword() );
		buf.append( ", " );
		buf.append( dto.isEnabled() );
		System.out.println( buf.toString() );
	}

}
