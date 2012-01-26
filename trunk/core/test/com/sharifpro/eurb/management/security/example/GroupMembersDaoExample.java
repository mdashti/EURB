package com.sharifpro.eurb.management.security.example;

import java.util.List;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.security.dao.GroupMembersDao;
import com.sharifpro.eurb.management.security.model.GroupMembers;

public class GroupMembersDaoExample
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
		// findByGroups(null);
		// findWhereIdEquals(null);
		// findWhereUsernameEquals("");
		// findWhereGroupIdEquals(null);
	}

	/**
	 * Method 'findAll'
	 * 
	 * @throws Exception
	 */
	public static void findAll() throws Exception
	{
		GroupMembersDao dao = DaoFactory.createGroupMembersDao();
		List<GroupMembers> _result = dao.findAll();
		for (GroupMembers dto : _result) {
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
		GroupMembersDao dao = DaoFactory.createGroupMembersDao();
		List<GroupMembers> _result = dao.findByGroups(groupId);
		for (GroupMembers dto : _result) {
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
		GroupMembersDao dao = DaoFactory.createGroupMembersDao();
		List<GroupMembers> _result = dao.findWhereIdEquals(id);
		for (GroupMembers dto : _result) {
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
		GroupMembersDao dao = DaoFactory.createGroupMembersDao();
		List<GroupMembers> _result = dao.findWhereUsernameEquals(username);
		for (GroupMembers dto : _result) {
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
		GroupMembersDao dao = DaoFactory.createGroupMembersDao();
		List<GroupMembers> _result = dao.findWhereGroupIdEquals(groupId);
		for (GroupMembers dto : _result) {
			display(dto);
		}
		
	}

	/**
	 * Method 'display'
	 * 
	 * @param dto
	 */
	public static void display(GroupMembers dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.append( dto.getId() );
		buf.append( ", " );
		buf.append( dto.getUsername() );
		buf.append( ", " );
		buf.append( dto.getGroupId() );
		System.out.println( buf.toString() );
	}

}
