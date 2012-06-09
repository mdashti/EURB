package com.sharifpro.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sharifpro.eurb.management.security.model.User;

public class SessionManager {

	public static User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			Object principal = auth.getPrincipal();
		    if (principal instanceof User) {
		    	return ((User) principal);
		    }
		}
	    return null;
	}
	
	public static String getCurrentUserName() {
		User u = getCurrentUser();
	    return u==null ? null : u.getUsername();
	}
	
	public static String getCurrentUserNameNotNull() {
		User u = getCurrentUser();
	    return u==null ? "admin" : u.getUsername();
	}
}
