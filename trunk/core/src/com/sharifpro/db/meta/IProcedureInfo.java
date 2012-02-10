package com.sharifpro.db.meta;

import java.io.Serializable;

public interface IProcedureInfo extends IDatabaseObjectInfo, Serializable
{
	String getRemarks();
	int getProcedureType();
	String getProcedureTypeDescription();
}
