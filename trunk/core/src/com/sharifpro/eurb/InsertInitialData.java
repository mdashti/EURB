package com.sharifpro.eurb;

import java.util.List;

import org.springframework.context.support.AbstractMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.security.dao.GroupMembersDao;
import com.sharifpro.eurb.management.security.dao.GroupsDao;
import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.eurb.management.security.exception.GroupMembersDaoException;
import com.sharifpro.eurb.management.security.exception.GroupsDaoException;
import com.sharifpro.eurb.management.security.exception.UserDaoException;
import com.sharifpro.eurb.management.security.model.GroupMembers;
import com.sharifpro.eurb.management.security.model.Groups;
import com.sharifpro.eurb.management.security.model.User;
import com.sharifpro.util.CollectionUtil;
import com.sharifpro.util.SharifProApplicationContext;

public class InsertInitialData {
	private static final String ADMIN_PASSWORD = "mohamad";
	private static final String ADMIN_USERNAME = "admin";

	private final AbstractMessageSource messages = (AbstractMessageSource) SharifProApplicationContext.getApplicationContext().getBean("messageSource");
	
	private final String[][] INITIAL_USERS = new String[][] {
			new String[]{"dashti", "mohamad"},	new String[]{"sadeghi", "alireza"},new String[]{"keshavarz", "behrouz"}
		};
	private final String[][] INITIAL_GROUPS_AND_USERS = new String[][] {
			new String[] {messages.getMessage("eurb.management.security.group.admin", null, "Admin",null), "admin" , "dashti", "keshavarz" },
			new String[] {messages.getMessage("eurb.management.security.group.reportbuilder", null, "ReportBuilder",null), "admin" , "sadeghi", "keshavarz" },
			new String[] {messages.getMessage("eurb.management.security.group.reportviewer", null, "ReportViewer",null), "admin" , "sadeghi", "keshavarz" },
			new String[] {messages.getMessage("eurb.management.security.group.guest", null, "Guest",null)}
	};
	
	public static void main(String[] args) throws Exception {
		InsertInitialData initial = new InsertInitialData();
		System.out.println("################# START ################");
		initial.authenticate();
		
		initial.addUsers();
		
		initial.addGroups();
		System.out.println("################ FINISH ################");
	}

	public void authenticate() {
		System.out.println("-------------Authentication-------------");
		authenticate(ADMIN_USERNAME, ADMIN_PASSWORD);
	}
	
	public void authenticate(String userName, String password) {
		try {
			Authentication request = new UsernamePasswordAuthenticationToken(userName, password);
			AuthenticationManager authManager = ((AuthenticationManager)SharifProApplicationContext.getApplicationContext().getBean("authManager"));
			
			Authentication result = authManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			System.out.println("Authentication successful for " + userName);
		} catch(AuthenticationException e) {
			System.out.println("Exception in authentication!");
			e.printStackTrace();
		}
	}

	private void addUsers() throws UserDaoException {
		UserDao dao = DaoFactory.createUsersDao();

		System.out.println("----------Adding initial users----------");
		System.out.println("Current Users: ");
		System.out.println(CollectionUtil.toString(dao.findAll()));
		System.out.println("----------------------------------------");

		for(String[] theUser : INITIAL_USERS) {
			String userName = theUser[0];
			String password = theUser[1];
			System.out.println("Looking for user : " + userName + "...");
			User user = dao.findByPrimaryKey(userName);
	
			if (user == null) {
				System.out.println("\tUser not found! trying to make it...");
	
				user = new User();
				user.setUsername(userName);
				user.setPassword(password);
				user.setEnabled(true);
	
				dao.insert(user);
				System.out.println("\tUser added.");
			} else {
				System.out.println("\tUser exists.");
			}
		}
	}
	
	private void addGroups() throws GroupsDaoException, GroupMembersDaoException {
		GroupsDao dao = DaoFactory.createGroupsDao();
		GroupMembersDao  membersDao = DaoFactory.createGroupMembersDao();
		
		System.out.println("----------Adding initial users----------");
		System.out.println("Current Groups: ");
		System.out.println(CollectionUtil.toString(dao.findAll()));
		System.out.println("----------------------------------------");

		for(String[] groupBundle : INITIAL_GROUPS_AND_USERS) {
			String groupName = groupBundle[0];
			System.out.println("Looking for group : " + groupName + "...");
			List<Groups> groups = dao.findWhereGroupNameEquals(groupName);
			Groups group = null;
			if(groups.size() > 0) {
				group = groups.get(0);
				if(groups.size() > 1) {
					System.out.println("\tThere are multiple groups with this name:");
					System.out.println("\t\t"+CollectionUtil.toString(groups, "\n\t\t"));
				}
			}
			if (group == null) {
				System.out.println("\tGroup not found! trying to make it...");
	
				group = new Groups();
				group.setGroupName(groupName);
	
				group.setId(dao.insert(group).getId());
				System.out.println("\tGroup added.");
			} else {
				System.out.println("\tGroup exists.");
			}
			
			System.out.println("\tAdding members to group...");
			List<GroupMembers> currentGroupMembers = membersDao.findWhereGroupIdEquals(group.getId());
			GroupMembers m;
			boolean found;
			for(int i = 1; i < groupBundle.length; i++) {
				found = false;
				for(GroupMembers member : currentGroupMembers) {
					if(groupBundle[i].equals(member.getUsername())) {
						System.out.println("\t\t" + groupBundle[i] + " is already a member.");
						found = true;
						break;
					}					
				}
				if(!found) {
					m = new GroupMembers();
					m.setGroupId(group.getId());
					m.setUsername(groupBundle[i]);
					membersDao.insert(m);

					System.out.println("\t\t" + groupBundle[i] + " added as a member.");
				}
			}
		}
	}
}
