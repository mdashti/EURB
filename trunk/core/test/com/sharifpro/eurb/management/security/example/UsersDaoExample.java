package com.sharifpro.eurb.management.security.example;

import java.util.List;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.eurb.management.security.model.User;

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
		UserDao dao = DaoFactory.createUsersDao();
		List<User> _result = dao.findAll();
		for (User dto : _result) {
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
		UserDao dao = DaoFactory.createUsersDao();
		List<User> _result = dao.findByPersistableObject(id);
		for (User dto : _result) {
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
		UserDao dao = DaoFactory.createUsersDao();
		List<User> _result = dao.findWhereIdEquals(id);
		for (User dto : _result) {
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
		UserDao dao = DaoFactory.createUsersDao();
		List<User> _result = dao.findWhereUsernameEquals(username);
		for (User dto : _result) {
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
		UserDao dao = DaoFactory.createUsersDao();
		List<User> _result = dao.findWherePasswordEquals(password);
		for (User dto : _result) {
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
		UserDao dao = DaoFactory.createUsersDao();
		List<User> _result = dao.findWhereEnabledEquals(enabled);
		for (User dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(User dto)
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
