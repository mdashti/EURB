package com.sharifpro.db.meta;

public interface IDatabaseObjectInfo extends Comparable<IDatabaseObjectInfo>
{
	String getCatalogName();
	String getSchemaName();
	String getSimpleName();

	/**
	 * Return the type for this object. @see DatabaseObjectType.
	 */
	DatabaseObjectType getDatabaseObjectType();
}
