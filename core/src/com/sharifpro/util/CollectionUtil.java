package com.sharifpro.util;

import java.util.Collection;

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
}
