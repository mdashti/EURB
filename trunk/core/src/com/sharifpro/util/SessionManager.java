package com.sharifpro.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SessionManager {

	public static UserDetails getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			Object principal = auth.getPrincipal();
		    if (principal instanceof UserDetails) {
		    	return ((UserDetails) principal);
		    }
		}
	    return null;
	}
	
	public static String getCurrentUserName() {
		UserDetails u = getCurrentUser();
	    return u==null ? null : u.getUsername();
	}
	
	public static String getCurrentUserNameNotNull() {
		UserDetails u = getCurrentUser();
	    return u==null ? "admin" : u.getUsername();
	}
}
