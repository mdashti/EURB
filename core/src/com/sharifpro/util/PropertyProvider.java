package com.sharifpro.util;

import org.springframework.context.support.AbstractMessageSource;

public class PropertyProvider {

	public static AbstractMessageSource MESSAGES = (AbstractMessageSource) SharifProApplicationContext.getApplicationContext().getBean("messageSource");

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
	public static final String ERROR_DATEANDTIME_REQUIRED = PropertyProvider.get("eurb.error.dateandtime.required", "Error: Date and time is required");
	public static final String ERROR_DATEANDTIME_INVALID = PropertyProvider.get("eurb.error.dateandtime.invalid", "Error: Date and time is invalid");
	public static final String ERROR_NOT_AUTHORIZED_TO_CREATE = PropertyProvider.get("eurb.app.notAuthorizedToCreate", "Error: Not authorized to create record");
	public static final String ERROR_NOT_AUTHORIZED_TO_EDIT = PropertyProvider.get("eurb.app.notAuthorizedToEdit", "Error: Not authorized to edit record");
	public static final String ERROR_NOT_AUTHORIZED_TO_DELETE = PropertyProvider.get("eurb.app.notAuthorizedToDelete", "Error: Not authorized to delete record");
	public static final String ERROR_NOT_AUTHORIZED_TO_ADMINISTER = PropertyProvider.get("eurb.app.notAuthorizedToAdminister", "Error: Not authorized to administer record");
	
}
