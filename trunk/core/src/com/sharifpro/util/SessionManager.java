package com.sharifpro.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.sharifpro.eurb.management.security.model.User;

public class SessionManager {

	public static UserDetails getCurrentUserDetails() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			Object principal = auth.getPrincipal();
		    if (principal instanceof UserDetails) {
		    	return ((UserDetails) principal);
		    }
		}
	    return null;
	}
	
	public static User getCurrentUser() {
		UserDetails ud = getCurrentUserDetails();
		return new User(ud);
	}
	
	public static String getCurrentUserName() {
		UserDetails u = getCurrentUserDetails();
	    return u==null ? null : u.getUsername();
	}
	
	public static String getCurrentUserNameNotNull() {
		UserDetails u = getCurrentUserDetails();
	    return u==null ? "admin" : u.getUsername();
	}
}
