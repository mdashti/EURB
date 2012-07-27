package com.sharifpro.eurb.management.security.dao.impl;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;

/**
 * Adds ACCEPT permission to standard set of Spring Security permissions. Based
 * on BasePermission code.
 */
public class ExtendedPermission extends BasePermission {
	private static final long serialVersionUID = 2280797956479550916L;
	//public static final Permission SHARING = new ExtendedPermission(1 << 5, 'S'); // 32

	private ExtendedPermission(int mask, char code) {
		super(mask, code);
	}
}