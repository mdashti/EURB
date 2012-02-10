package com.sharifpro.db.meta;

public interface IUDTInfo extends IDatabaseObjectInfo
{
	String getJavaClassName();
	String getDataType();
	String getRemarks();
}
