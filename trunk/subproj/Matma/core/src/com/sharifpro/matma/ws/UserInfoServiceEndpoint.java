package com.sharifpro.matma.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.eurb.management.security.exception.UserDaoException;
import com.sharifpro.eurb.management.security.model.User;

@WebService(serviceName="UserInfoService")
public class UserInfoServiceEndpoint extends SpringBeanAutowiringSupport {

    @Autowired
    private UserDao userDao;

    @WebMethod
    public void killSession(@WebParam(name="username")String username) {
    	try {
			User u = userDao.findWhereUsernameEquals(username);
			System.out.println("killSession for user:" + u);
		} catch (UserDaoException e) {
			e.printStackTrace();
		}
    }

    @WebMethod
    public void updateProfile(@WebParam(name="username") String username) {
    	try {
			User u = userDao.findWhereUsernameEquals(username);
			System.out.println("updateProfile for user:" + u);
		} catch (UserDaoException e) {
			e.printStackTrace();
		}
     }
}