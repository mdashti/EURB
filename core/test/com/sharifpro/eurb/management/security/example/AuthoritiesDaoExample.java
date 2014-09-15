package com.sharifpro.eurb.management.security.example;

import java.util.List;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.security.dao.AuthoritiesDao;
import com.sharifpro.eurb.management.security.model.Authorities;

public class AuthoritiesDaoExample
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
		
		// findAll();
		// findByUsers("");
		// findWhereUsernameEquals("");
		// findWhereAuthorityEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		AuthoritiesDao dao = DaoFactory.createAuthoritiesDao();
		List<Authorities> _result = dao.findAll();
		for (Authorities dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByUsers'
	 * 
	 * @param username
	 * @throws Exception
	 */
	public static void findByUsers(String username) throws Exception
	{
		AuthoritiesDao dao = DaoFactory.createAuthoritiesDao();
		List<Authorities> _result = dao.findByUsers(username);
		for (Authorities dto : _result) {
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
		AuthoritiesDao dao = DaoFactory.createAuthoritiesDao();
		List<Authorities> _result = dao.findWhereUsernameEquals(username);
		for (Authorities dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereAuthorityEquals'
	 * 
	 * @param authority
	 * @throws Exception
	 */
	public static void findWhereAuthorityEquals(String authority) throws Exception
	{
		AuthoritiesDao dao = DaoFactory.createAuthoritiesDao();
		List<Authorities> _result = dao.findWhereAuthorityEquals(authority);
		for (Authorities dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(Authorities dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getUsername() );
		buf.append( ", " );
		buf.append( dto.getAuthority() );
		System.out.println( buf.toString() );
	}

}
