package com.sharifpro.eurb;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.exception.DbConfigDaoException;
import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
import com.sharifpro.eurb.management.mapping.model.DbConfigPk;
import com.sharifpro.eurb.management.security.AuthorityType;
import com.sharifpro.eurb.management.security.dao.GroupAuthoritiesDao;
import com.sharifpro.eurb.management.security.dao.GroupMembersDao;
import com.sharifpro.eurb.management.security.dao.GroupsDao;
import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.eurb.management.security.exception.UserDaoException;
import com.sharifpro.eurb.management.security.model.GroupAuthorities;
import com.sharifpro.eurb.management.security.model.GroupMembers;
import com.sharifpro.eurb.management.security.model.Groups;
import com.sharifpro.eurb.management.security.model.User;
import com.sharifpro.util.CollectionUtil;
import com.sharifpro.util.PropertyProvider;
import com.sharifpro.util.SharifProApplicationContext;

public class InsertInitialData {
	private static final String ADMIN_PASSWORD = "mohamad";
	private static final String ADMIN_USERNAME = "admin";
	
	private final String[][] INITIAL_USERS;
	
	public final String[] GROUP_NAME;
	
	public final int GROUP_MAPPING_ADMIN = 0;
	public final int GROUP_USER_ADMIN = 1;
	public final int GROUP_REPORT_BUILDER = 2;
	public final int GROUP_REPORT_VIEWER = 3;
	public final int GROUP_GUEST = 4;
	
	private final String[][] INITIAL_GROUPS_AND_USERS;
	
	private final String[][] INITIAL_GROUPS_AND_AUTHORITIES;
	
	DbConfig dbConf;
	
	public InsertInitialData() {
		INITIAL_USERS = new String[][] {
				new String[]{"dashti", "mohamad"},
				new String[]{"sadeghi", "alireza"},
				new String[]{"keshavarz", "behrouz"}
		};
		
		GROUP_NAME = new String[]{
				PropertyProvider.get("eurb.management.security.group.admin", "Admin"),
				PropertyProvider.get("eurb.management.security.group.useradmin", "User Admin"),
				PropertyProvider.get("eurb.management.security.group.reportbuilder", "ReportBuilder"),
				PropertyProvider.get("eurb.management.security.group.reportviewer", "ReportViewer"),
				PropertyProvider.get("eurb.management.security.group.guest", "Guest")};
		
		INITIAL_GROUPS_AND_USERS = new String[][] {
				new String[] {GROUP_NAME[GROUP_MAPPING_ADMIN], "admin" , "dashti", "keshavarz" },
				new String[] {GROUP_NAME[GROUP_USER_ADMIN], "admin", "keshavarz" },
				new String[] {GROUP_NAME[GROUP_REPORT_BUILDER], "admin" , "sadeghi", "keshavarz" },
				new String[] {GROUP_NAME[GROUP_REPORT_VIEWER], "admin" , "sadeghi", "keshavarz" },
				new String[] {GROUP_NAME[GROUP_GUEST]}
		};
		
		INITIAL_GROUPS_AND_AUTHORITIES = new String[][] {
				new String[] {GROUP_NAME[GROUP_MAPPING_ADMIN], 
						AuthorityType.MAPPING_MANAGEMENT , 
						AuthorityType.MAPPING_MANAGEMENT_COLUMN_MAPPING, 
						AuthorityType.MAPPING_MANAGEMENT_COLUMN_MAPPING_LIST,
						AuthorityType.MAPPING_MANAGEMENT_DB,
						AuthorityType.MAPPING_MANAGEMENT_DB_LIST,
						AuthorityType.MAPPING_MANAGEMENT_TABLE_MAPPING,
						AuthorityType.MAPPING_MANAGEMENT_TABLE_MAPPING_LIST
				},
				new String[] {GROUP_NAME[GROUP_USER_ADMIN], 
						AuthorityType.USER_MANAGEMENT, 
						AuthorityType.USER_MANAGEMENT_GROUP,
						AuthorityType.USER_MANAGEMENT_GROUP_LIST,
						AuthorityType.USER_MANAGEMENT_GROUP_AUTHORITIES_LIST,
						AuthorityType.USER_MANAGEMENT_GROUP_AUTHORITY,
						AuthorityType.USER_MANAGEMENT_GROUP_MEMBER,
						AuthorityType.USER_MANAGEMENT_GROUP_MEMBERS_LIST,
						AuthorityType.USER_MANAGEMENT_USER,
						AuthorityType.USER_MANAGEMENT_USER_LIST,
						AuthorityType.USER_MANAGEMENT_USER_AUTHORITIES_LIST,
						AuthorityType.USER_MANAGEMENT_USER_AUTHORITY
				},
				new String[] {GROUP_NAME[GROUP_REPORT_BUILDER], 
						AuthorityType.REPORT_BUILDER
				},
				new String[] {GROUP_NAME[GROUP_REPORT_VIEWER], 
						AuthorityType.REPORT_VIEWER
				},
				new String[] {GROUP_NAME[GROUP_GUEST],
						AuthorityType.REPORT_VIEWER
				}
		};
	}
	
	public static void main(String[] args) throws Exception {
		InsertInitialData initial = new InsertInitialData();
		System.out.println("################# START ################");
		initial.authenticate();
		
		initial.addUsers();
		
		initial.addGroups();
		
		initial.addCurrentDB();
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
	
	private void addGroups() throws PersistableObjectDaoException {
		GroupsDao dao = DaoFactory.createGroupsDao();
		
		System.out.println("----------Adding initial users----------");
		System.out.println("Current Groups: ");
		System.out.println(CollectionUtil.toString(dao.findAll()));
		System.out.println("----------------------------------------");

		for(int k = 0 ; k < INITIAL_GROUPS_AND_USERS.length; k++) {
			String[] groupUsers = INITIAL_GROUPS_AND_USERS[k];
			String groupName = groupUsers[0];
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
			GroupMembersDao  membersDao = DaoFactory.createGroupMembersDao();
			List<GroupMembers> currentGroupMembers = membersDao.findWhereGroupIdEquals(group.getId());
			GroupMembers m;
			boolean found;
			for(int i = 1; i < groupUsers.length; i++) {
				found = false;
				for(GroupMembers member : currentGroupMembers) {
					if(groupUsers[i].equals(member.getUsername())) {
						System.out.println("\t\t" + groupUsers[i] + " is already a member.");
						found = true;
						break;
					}					
				}
				if(!found) {
					m = new GroupMembers();
					m.setGroupId(group.getId());
					m.setUsername(groupUsers[i]);
					membersDao.insert(m);

					System.out.println("\t\t" + groupUsers[i] + " added as a member.");
				}
			}
			
			GroupAuthoritiesDao groupAuthoritiesDao = DaoFactory.createGroupAuthoritiesDao();
			List<GroupAuthorities> currentGroupAuthorities = groupAuthoritiesDao.findWhereGroupIdEquals(group.getId());
			GroupAuthorities ga;
			String[] groupAuthorities = INITIAL_GROUPS_AND_AUTHORITIES[k];
			for(int i = 1; i < groupAuthorities.length; i++) {
				found = false;
				for(GroupAuthorities groupAuth : currentGroupAuthorities) {
					if(groupAuthorities[i].equals(groupAuth.getAuthority())) {
						System.out.println("\t\tAlready has " + groupAuthorities[i] + " authority.");
						found = true;
						break;
					}					
				}
				if(!found) {
					ga = new GroupAuthorities();
					ga.setGroupId(group.getId());
					ga.setAuthority(groupAuthorities[i]);
					groupAuthoritiesDao.insert(ga);

					System.out.println("\t\t" + groupAuthorities[i] + " authority added.");
				}
			}
		}
	}

	private void addCurrentDB() throws DbConfigDaoException {
		dbConf = new DbConfig();
		dbConf.setName("پایگاه داده داخلی ارب");
		dbConf.setDriverClass("com.mysql.jdbc.Driver");
		dbConf.setDriverUrl("jdbc:mysql://localhost/eurb?useUnicode=yes&amp;characterEncoding=UTF-8");
		dbConf.setUsername("root");
		dbConf.setPassword("mohamad");
		dbConf.setTestQuery("select 1 from dual");
		
		DbConfigDao dao = DaoFactory.createDbConfigDao();

		System.out.println("----------Adding initial databases----------");
		System.out.println("Current Databases: ");
		System.out.println(CollectionUtil.toString(dao.findAll()));
		System.out.println("----------------------------------------");
		
		List<DbConfig> dbConfList = dao.findWhereNameEquals(dbConf.getName());
		
		if(dbConfList.size() > 0) {
			dbConf = dbConfList.get(0);
			System.out.println("DB Exists.");
		} else {
			DbConfigPk pk = dao.insert(dbConf);
			dbConf.setId(pk.getId());
			System.out.println("DB Added.");
		}
	}
}
