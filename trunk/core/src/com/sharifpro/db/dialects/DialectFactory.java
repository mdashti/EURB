package com.sharifpro.db.dialects;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;

import com.sharifpro.db.meta.SQLDatabaseMetaData;

/**
 * This class maps SQLDatabaseMetaData instances to their corresponding
 * Hibernate dialect.
 */
public class DialectFactory {

	/** this is used to indicate that the sesion is being copied from */
	public static final int SOURCE_TYPE = 0;

	/** this is used to indicate that the sesion is being copied to */
	public static final int DEST_TYPE = 1;

	/** Logger for this class. */
	private final static Logger s_log = Logger.getLogger(DialectFactory.class);

	private static final AxionDialectExt axionDialect = new AxionDialectExt();

	private static final DB2DialectExt db2Dialect = new DB2DialectExt();

	// TODO: subclass these hibernate dialects to provide the "canPasteTo"
	// api method in HibernateDialect interface.
	// private static final DB2390Dialect db2390Dialect = new DB2390Dialect();

	// private static final DB2400Dialect db2400Dialect = new DB2400Dialect();

	private static final DaffodilDialectExt daffodilDialect = new DaffodilDialectExt();

	private static final DerbyDialectExt derbyDialect = new DerbyDialectExt();

	private static final FirebirdDialectExt firebirdDialect = new FirebirdDialectExt();

	private static final FrontBaseDialectExt frontbaseDialect = new FrontBaseDialectExt();

	private static final GenericDialectExt genericDialect = new GenericDialectExt();

	private static final HADBDialectExt hadbDialect = new HADBDialectExt();

	private static final H2DialectExt h2Dialect = new H2DialectExt();

	private static final HSQLDialectExt hsqlDialect = new HSQLDialectExt();

	private static final InformixDialectExt informixDialect = new InformixDialectExt();

	private static final InterbaseDialectExt interbaseDialect = new InterbaseDialectExt();

	private static final IngresDialectExt ingresDialect = new IngresDialectExt();

	private static final MAXDBDialectExt maxDbDialect = new MAXDBDialectExt();

	private static final McKoiDialectExt mckoiDialect = new McKoiDialectExt();

	private static final MySQLDialectExt mysqlDialect = new MySQLDialectExt();

	private static final MySQL5DialectExt mysql5Dialect = new MySQL5DialectExt();

	private static final OracleDialectExt oracle9iDialect = new OracleDialectExt();

	private static final PointbaseDialectExt pointbaseDialect = new PointbaseDialectExt();

	private static final PostgreSQLDialectExt postgreSQLDialect = new PostgreSQLDialectExt();

	private static final ProgressDialectExt progressDialect = new ProgressDialectExt();

	private static final SybaseDialectExt sybaseDialect = new SybaseDialectExt();

	private static final SQLServerDialectExt sqlserverDialect = new SQLServerDialectExt();

	private static final TimesTenDialectExt timestenDialect = new TimesTenDialectExt();

	private static final IntersystemsCacheDialectExt intersystemsCacheDialectExt = new IntersystemsCacheDialectExt();

	private static HashMap<String, HibernateDialect> dbNameDialectMap = new HashMap<String, HibernateDialect>();

	public static boolean isPromptForDialect = false;

	/**
	 * The keys to dbNameDialectMap are displayed to the user in the dialect
	 * chooser widget, so be sure to use something that is intelligable to an
	 * end user
	 */
	static {
		dbNameDialectMap.put(axionDialect.getDisplayName(), axionDialect);
		dbNameDialectMap.put(db2Dialect.getDisplayName(), db2Dialect);
		// dbNameDialectMap.put("DB2/390", db2390Dialect);
		// dbNameDialectMap.put("DB2/400", db2400Dialect);
		dbNameDialectMap.put(daffodilDialect.getDisplayName(), daffodilDialect);
		dbNameDialectMap.put(derbyDialect.getDisplayName(), derbyDialect);
		dbNameDialectMap.put(firebirdDialect.getDisplayName(), firebirdDialect);
		dbNameDialectMap.put(frontbaseDialect.getDisplayName(),
				frontbaseDialect);
		dbNameDialectMap.put(hadbDialect.getDisplayName(), hadbDialect);
		dbNameDialectMap.put(hsqlDialect.getDisplayName(), hsqlDialect);
		dbNameDialectMap.put(h2Dialect.getDisplayName(), h2Dialect);
		dbNameDialectMap.put(informixDialect.getDisplayName(), informixDialect);
		dbNameDialectMap.put(ingresDialect.getDisplayName(), ingresDialect);
		dbNameDialectMap.put(interbaseDialect.getDisplayName(),
				interbaseDialect);
		dbNameDialectMap.put(maxDbDialect.getDisplayName(), maxDbDialect);
		dbNameDialectMap.put(mckoiDialect.getDisplayName(), mckoiDialect);
		dbNameDialectMap.put(mysqlDialect.getDisplayName(), mysqlDialect);
		dbNameDialectMap.put(oracle9iDialect.getDisplayName(), oracle9iDialect);
		dbNameDialectMap.put(pointbaseDialect.getDisplayName(),
				pointbaseDialect);
		dbNameDialectMap.put(postgreSQLDialect.getDisplayName(),
				postgreSQLDialect);
		dbNameDialectMap.put(progressDialect.getDisplayName(), progressDialect);
		dbNameDialectMap.put(sqlserverDialect.getDisplayName(),
				sqlserverDialect);
		dbNameDialectMap.put(sybaseDialect.getDisplayName(), sybaseDialect);
		dbNameDialectMap.put(timestenDialect.getDisplayName(), timestenDialect);
		dbNameDialectMap.put(intersystemsCacheDialectExt.getDisplayName(),
				intersystemsCacheDialectExt);
	}

	public static boolean isAxion(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, axionDialect);
	}

	public static boolean isDaffodil(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, daffodilDialect);
	}

	public static boolean isDB2(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, db2Dialect);
	}

	public static boolean isDerby(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, derbyDialect);
	}

	public static boolean isFirebird(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, firebirdDialect);
	}

	public static boolean isFrontBase(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, frontbaseDialect);
	}

	public static boolean isHADB(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, hadbDialect);
	}

	public static boolean isH2(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, h2Dialect);
	}

	public static boolean isHSQL(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, hsqlDialect);
	}

	public static boolean isInformix(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, informixDialect);
	}

	public static boolean isIngres(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, ingresDialect);
	}

	public static boolean isInterbase(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, interbaseDialect);
	}

	public static boolean isMaxDB(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, maxDbDialect);
	}

	public static boolean isMcKoi(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, mckoiDialect);
	}

	public static boolean isMSSQLServer(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, sqlserverDialect);
	}

	public static boolean isMySQL(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, mysqlDialect);
	}

	public static boolean isMySQL5(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, mysql5Dialect);
	}

	public static boolean isOracle(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, oracle9iDialect);
	}

	public static boolean isPointbase(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, pointbaseDialect);
	}

	public static boolean isPostgreSQL(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, postgreSQLDialect);
	}

	public static boolean isProgress(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, progressDialect);
	}

	public static boolean isSyBase(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, sybaseDialect);
	}

	public static boolean isTimesTen(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, timestenDialect);
	}

	public static boolean isIntersystemsCacheDialectExt(SQLDatabaseMetaData md) {
		return dialectSupportsProduct(md, intersystemsCacheDialectExt);
	}

	/**
	 * Returns a DialectType for the specified SQLDatabaseMetaData
	 * 
	 * @param md
	 *            the metadata to use to determine the dialect type
	 * @return a dialect type
	 */
	public static DialectType getDialectType(SQLDatabaseMetaData md) {
		HibernateDialect dialect = getDialect(md);
		return dialect.getDialectType();
	}

	/**
	 * Examines the driver class name from the specified session to see if it
	 * begins with any of the space-delimited string tokens in the specified
	 * nameToMatch.
	 * 
	 * @param session
	 *            the ISession to check
	 * @param nameToMatch
	 *            a space-delimited string of driver class package prefixes
	 * @return true if there is a match of any string in the nameToMatch and the
	 *         ISession's driver class name; false otherwise.
	 */
	private static boolean dialectSupportsProduct(SQLDatabaseMetaData data,
			HibernateDialect dialect) {
		boolean result = false;
		if (data != null && dialect != null) {
			try {
				String productName = data.getDatabaseProductName();
				String productVersion = data.getDatabaseProductVersion();
				result = dialect.supportsProduct(productName, productVersion);
			} catch (Exception e) {
				s_log.error("Encountered unexpected exception while attempting to "
						+ "determine database product name/version: "
						+ e.getMessage());
				if (s_log.isDebugEnabled()) {
					StringWriter s = new StringWriter();
					PrintWriter p = new PrintWriter(s);
					e.printStackTrace(p);
					s_log.debug(s.getBuffer().toString());
				}
			}
		}
		return result;
	}

	public static HibernateDialect getDialect(String dbName) {
		return dbNameDialectMap.get(dbName);
	}

	public static HibernateDialect getDialectIgnoreCase(String dbName) {
		for (String displayName : dbNameDialectMap.keySet()) {
			if (displayName.toLowerCase().equals(dbName.toLowerCase())) {
				return dbNameDialectMap.get(displayName);
			}
		}
		return null;
	}

	/**
	 * @param md
	 * @return
	 */
	public static HibernateDialect getDialect(SQLDatabaseMetaData md) {
		if (isAxion(md)) {
			return axionDialect;
		}
		if (isDaffodil(md)) {
			return daffodilDialect;
		}
		if (isDB2(md)) {
			return db2Dialect;
		}
		if (isDerby(md)) {
			return derbyDialect;
		}
		if (isFirebird(md)) {
			return firebirdDialect;
		}
		if (isFrontBase(md)) {
			return frontbaseDialect;
		}
		if (isHADB(md)) {
			return hadbDialect;
		}
		if (isH2(md)) {
			return h2Dialect;
		}
		if (isHSQL(md)) {
			return hsqlDialect;
		}
		if (isInformix(md)) {
			return informixDialect;
		}
		if (isIngres(md)) {
			return ingresDialect;
		}
		if (isInterbase(md)) {
			return ingresDialect;
		}
		if (isMaxDB(md)) {
			return maxDbDialect;
		}
		if (isMcKoi(md)) {
			return mckoiDialect;
		}
		if (isMySQL(md)) {
			return mysqlDialect;
		}
		if (isMySQL5(md)) {
			return mysql5Dialect;
		}
		if (isMSSQLServer(md)) {
			return sqlserverDialect;
		}
		if (isOracle(md)) {
			return oracle9iDialect;
		}
		if (isPointbase(md)) {
			return pointbaseDialect;
		}
		if (isPostgreSQL(md)) {
			return postgreSQLDialect;
		}
		if (isProgress(md)) {
			return progressDialect;
		}
		if (isSyBase(md)) {
			return sybaseDialect;
		}
		if (isTimesTen(md)) {
			return timestenDialect;
		}
		if (isIntersystemsCacheDialectExt(md)) {
			return intersystemsCacheDialectExt;
		}
		// GenericDialect must be last, since it will claim that it supports any
		// product/version. That is also
		// why there is no isGenericDialect - it would always return true if
		// there was one, making it useless.
		return genericDialect;
	}

	/**
	 * Returns a list of Database server names that can be preented to the user
	 * whenever we want the user to pick a dialect.
	 * 
	 * @return
	 */
	public static Object[] getDbNames() {
		Set<String> keyset = dbNameDialectMap.keySet();
		Object[] keys = keyset.toArray();
		Arrays.sort(keys);
		return keys;
	}

	/**
	 * Returns an array of HibernateDialect instances, one for each supported
	 * dialect.
	 * 
	 * @return
	 */
	public static Object[] getSupportedDialects() {
		Collection<HibernateDialect> c = dbNameDialectMap.values();
		return c.toArray();
	}

}
