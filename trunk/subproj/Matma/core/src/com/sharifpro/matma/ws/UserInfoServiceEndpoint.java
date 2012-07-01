package com.sharifpro.matma.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.sharifpro.eurb.management.security.dao.UserDao;
import com.sharifpro.eurb.management.security.model.User;
import com.sharifpro.matma.wscall.user.UserService;

/**
 * This WebService is our firt webservice in EURB that can be used
 * as a template for adding other services.
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@WebService(serviceName="UserInfoService")
public class UserInfoServiceEndpoint extends SpringBeanAutowiringSupport {

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private SessionRegistry sessionRegistry;
    
    protected static final Log logger = LogFactory.getLog(UserInfoServiceEndpoint.class);

    @WebMethod
    public boolean killUser(@WebParam(name="username")String username) {
    	try {
			User u = userDao.findWhereUsernameEquals(username);
			if(u != null) {
				List<SessionInformation> sessionInfoList = sessionRegistry.getAllSessions(u, false);
				
				if(sessionInfoList != null && !sessionInfoList.isEmpty()) {
					logger.info("killSession for " + u.getUsername() + " was successful!");
					//int i = 1;
					for(SessionInformation si : sessionInfoList) {
						//System.out.println("session info ("+(i++)+") = " + si);
						si.expireNow();
					}
				} else {
					logger.warn("killSession for " + username + " was unsuccessful, session not found!");
				}
			} else {
				logger.error("killSession for " + username + " was unsuccessful, user not found!");
			}
			return true;
		} catch (Exception e) {
			logger.error("killSession for " + username + " was unsuccessful, exception occurred!", e);
			return false;
		}
    }

    @WebMethod
    public boolean profileChanged(@WebParam(name="username") String username) {
    	try {
			User u = userDao.findWhereUsernameEquals(username);
			if(u != null) {
				UserService userService = new UserService();
				com.sharifpro.matma.wscall.user.User user = userService.getUser();
				String p = user.getProfile(u.getUsername());
			} else {
				logger.error("updateProfile for " + username + " was unsuccessful, user not found!");
			}
			return true;
		} catch (Exception e) {
			logger.error("updateProfile for " + username + " was unsuccessful, exception occurred!", e);
			return false;
		}
     }
}