package com.sharifpro.eurb;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.info.AuthorityType;
import com.sharifpro.eurb.info.RecordStatus;
import com.sharifpro.eurb.management.mapping.dao.DbConfigDao;
import com.sharifpro.eurb.management.mapping.exception.DbConfigDaoException;
import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;
import com.sharifpro.eurb.management.mapping.model.DbConfig;
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
				new String[]{"admin", "mohamad", "me@mdashti.com"},
				new String[]{"dashti", "mohamad", "mdashti@gmail.com"},
				new String[]{"sadeghi", "alireza", "alireza.sadeghipour.1988@gmail.com"},
				new String[]{"keshavarz", "behrouz", "keshavarz81@yahoo.com"}
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
						AuthorityType.ROLE_BASE_MANAGEMENT_MENU_VIEW,
						AuthorityType.ROLE_BASE_DB_CONFIG_VIEW_LIST,
						AuthorityType.ROLE_BASE_DB_CONFIG_VIEW,
						AuthorityType.ROLE_BASE_DB_CONFIG_EDIT,
						AuthorityType.ROLE_BASE_DB_CONFIG_CREATE,
						AuthorityType.ROLE_BASE_DB_CONFIG_DELETE,
						AuthorityType.ROLE_BASE_DB_CONFIG_SHARING,
						AuthorityType.ROLE_BASE_TABLE_MAPPING_VIEW_LIST,
						AuthorityType.ROLE_BASE_TABLE_MAPPING_VIEW,
						AuthorityType.ROLE_BASE_TABLE_MAPPING_EDIT,
						AuthorityType.ROLE_BASE_TABLE_MAPPING_CREATE,
						//AuthorityType.ROLE_BASE_TABLE_MAPPING_DELETE,
						AuthorityType.ROLE_BASE_TABLE_MAPPING_SHARING,
						AuthorityType.ROLE_BASE_COL_MAPPING_VIEW_LIST,
						AuthorityType.ROLE_BASE_COL_MAPPING_VIEW,
						AuthorityType.ROLE_BASE_COL_MAPPING_EDIT,
						AuthorityType.ROLE_BASE_COL_MAPPING_CREATE,
						//AuthorityType.ROLE_BASE_COL_MAPPING_DELETE,
						AuthorityType.ROLE_BASE_COL_MAPPING_SHARING,
						AuthorityType.ROLE_BASE_CATEGORY_MANAGEMENT_VIEW_LIST,
						AuthorityType.ROLE_BASE_CATEGORY_MANAGEMENT_VIEW,
						AuthorityType.ROLE_BASE_CATEGORY_MANAGEMENT_EDIT,
						AuthorityType.ROLE_BASE_CATEGORY_MANAGEMENT_CREATE,
						AuthorityType.ROLE_BASE_CATEGORY_MANAGEMENT_DELETE,
						AuthorityType.ROLE_BASE_CATEGORY_MANAGEMENT_SHARING,
						AuthorityType.ROLE_BASE_CATEGORY_MANAGEMENT_EXECUTE,
				},
				new String[] {GROUP_NAME[GROUP_USER_ADMIN],
						AuthorityType.ROLE_SECURITY_MANAGEMENT_MENU_VIEW,
						AuthorityType.ROLE_SEC_USER_MANAGEMENT_VIEW_LIST,
						AuthorityType.ROLE_SEC_USER_MANAGEMENT_VIEW,
						AuthorityType.ROLE_SEC_USER_MANAGEMENT_EDIT,
						AuthorityType.ROLE_SEC_USER_MANAGEMENT_CREATE,
						AuthorityType.ROLE_SEC_USER_MANAGEMENT_DELETE,
						AuthorityType.ROLE_SEC_GROUP_MANAGEMENT_VIEW_LIST,
						AuthorityType.ROLE_SEC_GROUP_MANAGEMENT_VIEW,
						AuthorityType.ROLE_SEC_GROUP_MANAGEMENT_EDIT,
						AuthorityType.ROLE_SEC_GROUP_MANAGEMENT_CREATE,
						AuthorityType.ROLE_SEC_GROUP_MANAGEMENT_DELETE,
						AuthorityType.ROLE_SEC_ROLE_MANAGEMENT_VIEW_LIST,
						AuthorityType.ROLE_SEC_ROLE_MANAGEMENT_VIEW,
						AuthorityType.ROLE_SEC_ROLE_MANAGEMENT_EDIT
				},
				new String[] {GROUP_NAME[GROUP_REPORT_BUILDER],
						AuthorityType.ROLE_REPORT_GENERATOR_MENU_VIEW,
						AuthorityType.ROLE_RPG_REPORT_BUILDER_VIEW_LIST,
						AuthorityType.ROLE_RPG_REPORT_BUILDER_VIEW,
						AuthorityType.ROLE_RPG_REPORT_BUILDER_EDIT,
						AuthorityType.ROLE_RPG_REPORT_BUILDER_CREATE,
						AuthorityType.ROLE_RPG_REPORT_BUILDER_DELETE,
						AuthorityType.ROLE_RPG_REPORT_BUILDER_EXECUTE,
						AuthorityType.ROLE_RPG_REPORT_BUILDER_SHARING
				},
				new String[] {GROUP_NAME[GROUP_REPORT_VIEWER],
						AuthorityType.ROLE_REPORT_GENERATOR_MENU_VIEW,
						AuthorityType.ROLE_RPG_REPORT_BUILDER_VIEW_LIST,
						AuthorityType.ROLE_RPG_REPORT_BUILDER_EXECUTE,
						AuthorityType.ROLE_RPG_REPORT_SCHEDULER_VIEW_LIST,
						AuthorityType.ROLE_RPG_REPORT_SCHEDULER_VIEW,
						AuthorityType.ROLE_RPG_REPORT_SCHEDULER_EDIT,
						AuthorityType.ROLE_RPG_REPORT_SCHEDULER_CREATE,
						AuthorityType.ROLE_RPG_REPORT_SCHEDULER_DELETE,
				},
				new String[] {GROUP_NAME[GROUP_GUEST],
						AuthorityType.USER
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
		UserDao dao = DaoFactory.createUserDao();

		System.out.println("----------Adding initial users----------");
		System.out.println("Current Users: ");
		System.out.println(CollectionUtil.toString(dao.findAll()));
		System.out.println("----------------------------------------");

		for(String[] theUser : INITIAL_USERS) {
			String userName = theUser[0];
			String password = theUser[1];
			String email = theUser[2];
			System.out.println("Looking for user : " + userName + "...");
			User user = dao.findWhereUsernameEquals(userName);
			if (user == null) {
				System.out.println("\tUser not found! trying to make it...");
	
				user = new User(null, userName, password, AuthorityUtils.NO_AUTHORITIES);
				user.setEmail(email);
				dao.insert(user);
				System.out.println("\tUser added.");
			} else {
				System.out.println("\tUser exists.");
				user.setPassword(password);
				user.setEmail(email);
				dao.setPassword(user.createPk(), user);
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
		dbConf.setDriverUrl("jdbc:mysql://localhost/eurb?useUnicode=yes&characterEncoding=UTF-8");
		dbConf.setUsername("root");
		dbConf.setPassword("root");
		dbConf.setTestQuery("select 1 from dual");
		dbConf.setRecordStatus(RecordStatus.ACTIVE);
		
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
			dao.insert(dbConf);
			System.out.println("DB Added.");
		}
		
		
		
		DbConfig dbConf = new DbConfig();
		dbConf.setName("پایگاه داده پژوهشی");
		dbConf.setDriverClass("com.mysql.jdbc.Driver");
		dbConf.setDriverUrl("jdbc:mysql://localhost/crud?useUnicode=yes&characterEncoding=UTF-8");
		dbConf.setUsername("root");
		dbConf.setPassword("ROOT");
		dbConf.setTestQuery("select 1 from dual");
		dbConf.setRecordStatus(RecordStatus.ACTIVE);
		
		dbConfList = dao.findWhereNameEquals(dbConf.getName());
		
		if(dbConfList.size() > 0) {
			dbConf = dbConfList.get(0);
			System.out.println("DB Exists.");
		} else {
			dao.insert(dbConf);
			System.out.println("DB Added.");
		}
	}
}
