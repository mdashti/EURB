package com.sharifpro.eurb;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Controller - Spring
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Controller
public class SpyController {

	@RequestMapping(value="/**/*.spy*")
	public String handleRequest(ServletWebRequest request) throws Exception {
		HttpServletRequest httpRequest = request.getNativeRequest(HttpServletRequest.class);
		String uri = httpRequest.getRequestURI();
		String context = request.getContextPath();
		
		String uriWithoutContext = uri.substring(context.length());
		String target = uriWithoutContext.substring(0,uriWithoutContext.length()-(".spy".length()));
		return target;
	}
}