package com.sharifpro.eurb;

import java.util.HashMap;
import java.util.Map;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.eurb.management.security.model.Groups;
import com.sharifpro.eurb.management.security.model.User;

public class PersistableObjectType {

	public static Map<String, Integer> OBJECT_TYPE_MAP = new HashMap<String, Integer>();
	static {
		OBJECT_TYPE_MAP.put(User.class.getName(), 100);
		OBJECT_TYPE_MAP.put(Groups.class.getName(), 200);
	}
	
	public static Integer getObjectTypeFor(String className) {
		Integer result = OBJECT_TYPE_MAP.get(className);
		if(result == null) {
			return -1;
		}
		return result;
	}

	public static Integer getObjectTypeFor(Class<? extends PersistableObject> clazz) {
		return getObjectTypeFor(clazz.getName());
	}
}
