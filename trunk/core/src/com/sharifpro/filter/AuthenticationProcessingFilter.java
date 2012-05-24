package com.sharifpro.filter;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sharifpro.util.PropertyProvider;

public class AuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain, Authentication authResult)
			throws IOException, ServletException {
		//we do not want the super class to send output, so we send a mock response to it!
		super.successfulAuthentication(request, new MockHttpServletResponse(), filterChain, authResult);

		HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(
				response);

		Writer out = responseWrapper.getWriter();

		String targetUrl = "index.spy";
		out.write("{success:true, targetUrl : \'" + targetUrl + "\'}");
		out.close();

	}

	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException {

		HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(
				response);

		Writer out = responseWrapper.getWriter();

		out.write("{success:false,errors:{title:'"+PropertyProvider.get("eurb.app.loginFailedTitle")+"',errormsg:'"+PropertyProvider.get("eurb.app.loginFailedMessage")+"'}}");
		out.close();

	}

}
