package com.sharifpro.util;

//import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtil {
	public static String generatePassword(String password, String username) {
		//UserDetailsService userDetailsService = ((UserDetailsService)SharifProApplicationContext.getApplicationContext().getBean("userDetailsService"));
		//UserDetails user =userDetailsService.loadUserByUsername("admin");
		PasswordEncoder encoder = ((PasswordEncoder)SharifProApplicationContext.getApplicationContext().getBean("passwordEncoder"));
		//SaltSource saltSource = ((SaltSource)SharifProApplicationContext.getApplicationContext().getBean("saltSource"));
		return encoder.encodePassword(password, null /*saltSource.getSalt(user)*/);
	}

	public static boolean hasRole(String role) {
		// get security context from thread local
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return false;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return false;

		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (role.equals(auth.getAuthority()))
				return true;
		}

		return false;
	}
}
