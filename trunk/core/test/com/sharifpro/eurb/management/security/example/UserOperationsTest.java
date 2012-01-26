package com.sharifpro.eurb.management.security.example;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.InsertInitialData;
import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.eurb.management.security.exception.UserDaoException;
import com.sharifpro.eurb.management.security.model.User;
import com.sharifpro.util.CollectionUtil;

public class UserOperationsTest extends InsertInitialData{
	public static void main(String[] args) throws Exception {
		UserOperationsTest test = new UserOperationsTest();
		test.athenticate();
		test.addUser();
	}

	private void addUser() throws UserDaoException {
		UserDao dao = DaoFactory.createUsersDao();

		System.out.println("current users: ");
		System.out.println(CollectionUtil.toString(dao.findAll()));
		

		User adminUser = dao.findByPrimaryKey("admin");

		if (adminUser == null) {
			System.out.println("Admin user not found! trying to make it...");

			adminUser = new User();
			adminUser.setUsername("admin");
			adminUser.setPassword("mohamad");
			adminUser.setEnabled(true);

			//dao.insert(adminUser);

			System.out.println("current users: ");
			System.out.println(dao.findAll());
		} else {
			System.out.println("Admin user exists.");
		}
	}
}
