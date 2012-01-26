package com.sharifpro.eurb;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.eurb.management.security.exception.UserDaoException;
import com.sharifpro.eurb.management.security.model.User;
import com.sharifpro.util.CollectionUtil;
import com.sharifpro.util.SharifProApplicationContext;

public class InsertInitialData {
	private static final String ADMIN_PASSWORD = "mohamad";
	private static final String ADMIN_USERNAME = "admin";

	public static void main(String[] args) throws Exception {
		InsertInitialData inital = new InsertInitialData();
		System.out.println("################# START ################");
		inital.athenticate();
		
		inital.addUsers();
		System.out.println("################ FINISH ################");
	}

	

	public void athenticate() {
		System.out.println("-------------Authentication-------------");
		String name = ADMIN_USERNAME;
		String password = ADMIN_PASSWORD; 
		try {
			Authentication request = new UsernamePasswordAuthenticationToken(name, password);
			AuthenticationManager authManager = ((AuthenticationManager)SharifProApplicationContext.getApplicationContext().getBean("authManager"));
			
			Authentication result = authManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			System.out.println("Authentication successful for " + ADMIN_USERNAME);
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

		String[][] INITIAL_USERS = new String[][] {
			new String[]{"dashti", "mohamad"},	new String[]{"sadeghi", "alireza"},new String[]{"keshavarz", "behrouz"}
		};
		for(String[] user : INITIAL_USERS) {
			String userName = user[0];
			String password = user[1];
			System.out.println("Looking for user : " + userName + "...");
			User adminUser = dao.findByPrimaryKey(userName);
	
			if (adminUser == null) {
				System.out.println("\tUser not found! trying to make it...");
	
				adminUser = new User();
				adminUser.setUsername(userName);
				adminUser.setPassword(password);
				adminUser.setEnabled(true);
	
				dao.insert(adminUser);
				System.out.println("\tUser added.");
			} else {
				System.out.println("\tUser exists.");
			}
		}
	}
}
