package com.sharifpro.util;

import org.springframework.context.support.AbstractMessageSource;

public class PropertyProvider {

	public static final AbstractMessageSource MESSAGES = (AbstractMessageSource) SharifProApplicationContext.getApplicationContext().getBean("messageSource");

	public static String get(String key, Object... args){
		return MESSAGES.getMessage(key, args, key, null);
	}
	
	public static String get(String key, String defaultMessage, Object... args){
		return MESSAGES.getMessage(key, args, defaultMessage, null);
	}
	
	public static String get(String key, String defaultMessage){
		return MESSAGES.getMessage(key, null, defaultMessage, null);
	}
	
	public static String get(String key){
		return MESSAGES.getMessage(key, null, key, null);
	}
	
	public final static String QUERY_FAILED_MESSAGE = PropertyProvider.get("eurb.queryFailed", "Query Failed");
}
