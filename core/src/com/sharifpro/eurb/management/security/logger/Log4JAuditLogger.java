package com.sharifpro.eurb.management.security.logger;

import org.apache.log4j.Logger;
import org.springframework.security.acls.domain.AuditLogger;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.AuditableAccessControlEntry;

import org.springframework.util.Assert;

/**
 * A basic implementation of {@link AuditLogger}.
 * 
 * @author <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 */
public class Log4JAuditLogger implements AuditLogger {
	private static Logger logger = Logger.getLogger(Log4JAuditLogger.class);

	public void logIfNeeded(boolean granted, AccessControlEntry ace) {
		Assert.notNull(ace, "AccessControlEntry required");

		if (ace instanceof AuditableAccessControlEntry) {
			AuditableAccessControlEntry auditableAce = (AuditableAccessControlEntry) ace;

			if (granted && auditableAce.isAuditSuccess()) {
				logger.info("GRANTED due to ACE: " + ace);
			} else if (!granted && auditableAce.isAuditFailure()) {
				logger.warn("DENIED due to ACE: " + ace);
			}
		}
	}
}
