package com.sharifpro.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sharifpro.sample.model.Contact;

public class CollectionUtil {

	public static String toString(Collection<? extends Object> collection) {
		return toString(collection, "\r\n");
	}
	public static String toString(Collection<? extends Object> collection, String separator) {
		StringBuilder sb = new StringBuilder();
		for(Object obj : collection) {
			sb.append(obj).append(separator);
		}
		return sb.toString();
	}

	/**
	 * Generates modelMap to return in the modelAndView
	 * @param contacts
	 * @return
	 */
	public static Map<String,Object> getSuccessfulMap(List<? extends Object> objList){

		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		modelMap.put("total", objList.size());
		modelMap.put("data", objList);
		modelMap.put("success", true);

		return modelMap;
	}
	
	/**
	 * Generates modelMap to return in the modelAndView in case
	 * of exception
	 * @param msg message
	 * @return
	 */
	public static Map<String,Object> getModelMapError(String msg){

		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", false);

		return modelMap;
	}
}
