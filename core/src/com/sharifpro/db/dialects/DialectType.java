package com.sharifpro.db.dialects;

/**
 * An enumeration class that provides an entry for each database that we have a
 * HibernateDialect (SQuirreL's version) implemented.
 */
public enum DialectType {
   AXION,
   DAFFODIL,
   DB2,
   DERBY,
   FIREBIRD,
   FRONTBASE,
   HADB,
   HSQLDB,
   H2,
   INFORMIX,
   INGRES,
   INTERBASE,
   MAXDB,
   MCKOI,
   MSSQL,
   MYSQL,     // MySQL 4.x and below
   MYSQL5,    // MySQL 5.x and above
   NETEZZA,
   ORACLE,
   POINTBASE,
   POSTGRES,
   PROGRESS,
   SYBASEASE, // Sybase Adaptive Server Enterprise 
   TIMESTEN,
   GENERIC
}
