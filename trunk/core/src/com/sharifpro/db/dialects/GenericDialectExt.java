package com.sharifpro.db.dialects;

import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;

public class GenericDialectExt extends CommonHibernateDialect {

	private class GenericDialectHelper extends Dialect {
		public GenericDialectHelper() {
			registerColumnType(Types.BIGINT, "integer");
			registerColumnType(Types.CHAR, "char($l)");
			registerColumnType(Types.DATE, "date");
			registerColumnType(Types.INTEGER, "integer");
			registerColumnType(Types.LONGVARCHAR, "varchar($l)");
			registerColumnType(Types.SMALLINT, "integer");
			registerColumnType(Types.TIME, "time");
			registerColumnType(Types.TIMESTAMP, "timestamp");
			registerColumnType(Types.TINYINT, "integer");
			registerColumnType(Types.VARCHAR, "varchar($l)");
		}
	}

	/** extended hibernate dialect used in this wrapper */
	private GenericDialectHelper _dialect = new GenericDialectHelper();

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.CommonHibernateDialect#getTypeName(int,
	 *      int, int, int)
	 */
	@Override
	public String getTypeName(int code, int length, int precision, int scale)
			throws HibernateException {
		return _dialect.getTypeName(code, length, precision, scale);
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.CommonHibernateDialect#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Generic";
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.CommonHibernateDialect#getDialectType()
	 */
	@Override
	public DialectType getDialectType() {
		return DialectType.GENERIC;
	}

	/**
	 * @see net.sourceforge.squirrel_sql.fw.dialects.CommonHibernateDialect#supportsProduct(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean supportsProduct(String databaseProductName,
			String databaseProductVersion) {
		return true;
	}

}
