package com.sharifpro.util.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.filter.GenericFilterBean;

/**
 * This is a fix for persistent Tomcat sessions for tracking online user sessions.
 * Whenever the server restarts, the users that were working with system will be
 * able to continue their work without having to login again, but SessionManagementFilter
 * will not be aware of these users, so this hack comes to help us!
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
public class SessionRegistryFixFilter extends GenericFilterBean {
	private SessionRegistry sessionRegistry;

	public SessionRegistryFixFilter() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;

		final HttpSession session = request.getSession(false);
		if (session != null) {
			final SessionInformation info = sessionRegistry.getSessionInformation(session.getId());
			if (info == null) {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication != null && authentication.getPrincipal() != null) {
					sessionRegistry.registerNewSession(session.getId(), authentication.getPrincipal());
				}
			}
		}
		chain.doFilter(req, res);
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
}