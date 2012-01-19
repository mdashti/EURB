package com.sharifpro.eurb.example;

import java.math.*;
import java.util.Date;
import java.util.List;
import com.sharifpro.eurb.dao.GroupAuthoritiesDao;
import com.sharifpro.eurb.dto.GroupAuthorities;
import com.sharifpro.eurb.exceptions.GroupAuthoritiesDaoException;
import com.sharifpro.eurb.factory.DaoFactory;

public class GroupAuthoritiesDaoExample
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
		// findByGroups(null);
		// findWhereGroupIdEquals(null);
		// findWhereAuthorityEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		GroupAuthoritiesDao dao = DaoFactory.createGroupAuthoritiesDao();
		List<GroupAuthorities> _result = dao.findAll();
		for (GroupAuthorities dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findByGroups'
	 * 
	 * @param groupId
	 * @throws Exception
	 */
	public static void findByGroups(Long groupId) throws Exception
	{
		GroupAuthoritiesDao dao = DaoFactory.createGroupAuthoritiesDao();
		List<GroupAuthorities> _result = dao.findByGroups(groupId);
		for (GroupAuthorities dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereGroupIdEquals'
	 * 
	 * @param groupId
	 * @throws Exception
	 */
	public static void findWhereGroupIdEquals(Long groupId) throws Exception
	{
		GroupAuthoritiesDao dao = DaoFactory.createGroupAuthoritiesDao();
		List<GroupAuthorities> _result = dao.findWhereGroupIdEquals(groupId);
		for (GroupAuthorities dto : _result) {
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
		GroupAuthoritiesDao dao = DaoFactory.createGroupAuthoritiesDao();
		List<GroupAuthorities> _result = dao.findWhereAuthorityEquals(authority);
		for (GroupAuthorities dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(GroupAuthorities dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getGroupId() );
		buf.append( ", " );
		buf.append( dto.getAuthority() );
		System.out.println( buf.toString() );
	}

}
