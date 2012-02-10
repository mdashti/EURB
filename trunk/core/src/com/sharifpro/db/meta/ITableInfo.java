package com.sharifpro.db.meta;

import java.io.Serializable;

public interface ITableInfo extends IDatabaseObjectInfo, Serializable
{
	String getType();
	String getRemarks();
	ITableInfo[] getChildTables();
    ForeignKeyInfo[] getImportedKeys();
    ForeignKeyInfo[] getExportedKeys();
    void setExportedKeys(ForeignKeyInfo[] foreignKeys);
    void setImportedKeys(ForeignKeyInfo[] foreignKeys);
}
