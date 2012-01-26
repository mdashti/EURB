package com.sharifpro.eurb.example;

import java.util.List;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.security.dao.GroupsDao;
import com.sharifpro.eurb.management.security.model.Groups;

public class GroupsDaoExample
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
		// findByPersistableObject(null);
		// findWhereIdEquals(null);
		// findWhereGroupNameEquals("");
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		GroupsDao dao = DaoFactory.createGroupsDao();
		List<Groups> _result = dao.findAll();
		for (Groups dto : _result) {
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
		GroupsDao dao = DaoFactory.createGroupsDao();
		List<Groups> _result = dao.findByPersistableObject(id);
		for (Groups dto : _result) {
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
		GroupsDao dao = DaoFactory.createGroupsDao();
		List<Groups> _result = dao.findWhereIdEquals(id);
		for (Groups dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'findWhereGroupNameEquals'
	 * 
	 * @param groupName
	 * @throws Exception
	 */
	public static void findWhereGroupNameEquals(String groupName) throws Exception
	{
		GroupsDao dao = DaoFactory.createGroupsDao();
		List<Groups> _result = dao.findWhereGroupNameEquals(groupName);
		for (Groups dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(Groups dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getGroupName() );
		System.out.println( buf.toString() );
	}

}
