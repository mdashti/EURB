package com.sharifpro.db.dialects;

import com.sharifpro.db.meta.SQLDatabaseMetaData;

/**
 * An implementation of IDialectFactory that delegates to DialectFactory. Since
 * DialectFactory is mostly static, and performs some static initialization that
 * cannot be undone once initialized, this wrapper allows classes to have a
 * non-static IDialectFactory injected, rather than rely on the static methods
 * in DialectFactory which is essentially a big and complex global variable.
 * This means that alternative (for example, mock) implementations can be
 * substituted for the real DialectFactory, when testing if a real
 * DialectFactory isn't needed for test purposes.
 */
public class DialectFactoryImpl implements IDialectFactory {

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isAxion(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isAxion(SQLDatabaseMetaData md) {
		return DialectFactory.isAxion(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isDaffodil(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isDaffodil(SQLDatabaseMetaData md) {
		return DialectFactory.isDaffodil(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isDB2(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isDB2(SQLDatabaseMetaData md) {
		return DialectFactory.isDB2(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isDerby(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isDerby(SQLDatabaseMetaData md) {
		return DialectFactory.isDerby(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isFirebird(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isFirebird(SQLDatabaseMetaData md) {
		return DialectFactory.isFirebird(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isFrontBase(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isFrontBase(SQLDatabaseMetaData md) {
		return DialectFactory.isFrontBase(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isHADB(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isHADB(SQLDatabaseMetaData md) {
		return DialectFactory.isHADB(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isH2(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isH2(SQLDatabaseMetaData md) {
		return DialectFactory.isH2(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isHSQL(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isHSQL(SQLDatabaseMetaData md) {
		return DialectFactory.isHSQL(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isInformix(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isInformix(SQLDatabaseMetaData md) {
		return DialectFactory.isInformix(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isIngres(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isIngres(SQLDatabaseMetaData md) {
		return DialectFactory.isIngres(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isInterbase(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isInterbase(SQLDatabaseMetaData md) {
		return DialectFactory.isInterbase(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isIntersystemsCacheDialectExt(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isIntersystemsCacheDialectExt(SQLDatabaseMetaData md) {
		return DialectFactory.isIntersystemsCacheDialectExt(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isMaxDB(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isMaxDB(SQLDatabaseMetaData md) {
		return DialectFactory.isMaxDB(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isMcKoi(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isMcKoi(SQLDatabaseMetaData md) {
		return DialectFactory.isMcKoi(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isMSSQLServer(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isMSSQLServer(SQLDatabaseMetaData md) {
		return DialectFactory.isMSSQLServer(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isMySQL(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isMySQL(SQLDatabaseMetaData md) {
		return DialectFactory.isMySQL(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isMySQL5(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isMySQL5(SQLDatabaseMetaData md) {
		return DialectFactory.isMySQL5(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isOracle(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isOracle(SQLDatabaseMetaData md) {
		return DialectFactory.isOracle(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isPointbase(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isPointbase(SQLDatabaseMetaData md) {
		return DialectFactory.isPointbase(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isPostgreSQL(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isPostgreSQL(SQLDatabaseMetaData md) {
		return DialectFactory.isPostgreSQL(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isProgress(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isProgress(SQLDatabaseMetaData md) {
		return DialectFactory.isProgress(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isSyBase(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isSyBase(SQLDatabaseMetaData md) {
		return DialectFactory.isSyBase(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      isTimesTen(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public boolean isTimesTen(SQLDatabaseMetaData md) {
		return DialectFactory.isTimesTen(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      getDialectType(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public DialectType getDialectType(SQLDatabaseMetaData md) {
		return DialectFactory.getDialectType(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#getDialect(java.lang.String)
	 */
	public HibernateDialect getDialect(String dbName) {
		return DialectFactory.getDialect(dbName);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#getDialectIgnoreCase(java.lang.String)
	 */
	public HibernateDialect getDialectIgnoreCase(String dbName) {
		return DialectFactory.getDialectIgnoreCase(dbName);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#
	 *      getDialect(com.sharifpro.db.meta.SQLDatabaseMetaData)
	 */
	public HibernateDialect getDialect(SQLDatabaseMetaData md) {
		return DialectFactory.getDialect(md);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#getDbNames()
	 */
	public Object[] getDbNames() {
		return DialectFactory.getDbNames();
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.IDialectFactory#getSupportedDialects()
	 */
	public Object[] getSupportedDialects() {
		return DialectFactory.getSupportedDialects();
	}
}
