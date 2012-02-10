package com.sharifpro.db.dialects;

import com.sharifpro.db.meta.SQLDatabaseMetaData;

/**
 * An interface for a non-static inject-able DialectFactory.
 */
public interface IDialectFactory {

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is Axion
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isAxion(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is Daffodil
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isDaffodil(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is DB2
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isDB2(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is derby
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isDerby(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is firebird
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isFirebird(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is frontbase
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isFrontBase(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is HADB
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isHADB(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is H2
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isH2(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is HSQL
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isHSQL(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is Informix
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isInformix(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is Ingres
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isIngres(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is Interbase
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isInterbase(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is InterSystems Cache
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isIntersystemsCacheDialectExt(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is MaxDB
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isMaxDB(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is McKoi
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isMcKoi(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is MSSQL Server
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isMSSQLServer(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is MySQL 4 or below
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isMySQL(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is MySQL 5
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isMySQL5(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is Oracle
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isOracle(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is Pointbase
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isPointbase(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is PostgreSQL
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isPostgreSQL(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is Progress
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isProgress(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is SyBase
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isSyBase(SQLDatabaseMetaData md);

	/**
	 * Returns a boolean value indicating whether or not the specified metadata
	 * indicates that it is Oracle Times Ten
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return boolean indicating whether or not the specified metadata matches
	 *         the database type
	 */
	boolean isTimesTen(SQLDatabaseMetaData md);

	/**
	 * Returns a DialectType for the specified SQLDatabaseMetaData
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return a dialect type
	 */
	DialectType getDialectType(SQLDatabaseMetaData md);

	/**
	 * Returns the HibernateDialect that corresponds with the specified database
	 * display name.
	 * 
	 * @param dbName
	 *            the database display name
	 * @return a HibernateDialect matching the specified database display name
	 *         exactly, or null.
	 */
	HibernateDialect getDialect(String dbName);

	/**
	 * Returns the HibernateDialect that corresponds with the specified
	 * metadata.
	 * 
	 * @param dbName
	 *            the database display name
	 * @return a HibernateDialect matching the specified database display name
	 *         ignoring case, or null.
	 */
	HibernateDialect getDialectIgnoreCase(String dbName);

	/**
	 * Returns the HibernateDialect that corresponds with the specified
	 * metadata.
	 * 
	 * @param md
	 *            The SQLDatabaseMetaData retrieved from either
	 *            ISQLConnection.getSQLMetaData() or ISession.getMetaData()
	 * @return the HibernateDialect that corresponds with the specified
	 *         metadata. If no specific implementation matches the specified
	 *         metadata, then a generic dialect is returned.
	 */
	HibernateDialect getDialect(SQLDatabaseMetaData md);

	/**
	 * Returns a list of Database display names that can be presented to the
	 * user whenever we want the user to pick a dialect. It is from this list,
	 * that the string parameter in getDialect(String) should be chosen.
	 * 
	 * @return a list of database display names
	 */
	Object[] getDbNames();

	/**
	 * Returns an array of HibernateDialect instances, one for each supported
	 * dialect.
	 * 
	 * @return an array of HibernateDialect instances. This array doesn't
	 *         include the generic dialect.
	 */
	Object[] getSupportedDialects();

}