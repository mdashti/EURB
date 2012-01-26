package com.sharifpro.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SessionManager {

	public static UserDetails getUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    if (principal instanceof UserDetails) {
	    	return ((UserDetails) principal);
	    }
	    return null;
	}
}
