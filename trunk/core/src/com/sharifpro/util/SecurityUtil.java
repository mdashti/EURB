package com.sharifpro.util;

//import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;


public class SecurityUtil {
	public static String generatePassword(String password, String username) {
		//UserDetailsService userDetailsService = ((UserDetailsService)SharifProApplicationContext.getApplicationContext().getBean("userDetailsService"));
		//UserDetails user =userDetailsService.loadUserByUsername("admin");
		PasswordEncoder encoder = ((PasswordEncoder)SharifProApplicationContext.getApplicationContext().getBean("passwordEncoder"));
		//SaltSource saltSource = ((SaltSource)SharifProApplicationContext.getApplicationContext().getBean("saltSource"));
		return encoder.encodePassword(password, username /*saltSource.getSalt(user)*/);
	}
}
