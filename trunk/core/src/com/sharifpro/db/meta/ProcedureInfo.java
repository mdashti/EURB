package com.sharifpro.db.meta;

import java.sql.DatabaseMetaData;

import com.sharifpro.util.PropertyProvider;

public class ProcedureInfo extends DatabaseObjectInfo implements IProcedureInfo
{
	private static final long serialVersionUID = -8748255421871954579L;

	private interface IStrings
	{
		String MAY_RETURN = PropertyProvider.get("ProcedureInfo.mayreturn");
		String DOESNT_RETURN = PropertyProvider.get("ProcedureInfo.doesntreturn");
		String DOES_RETURN = PropertyProvider.get("ProcedureInfo.returns");
		String UNKNOWN = PropertyProvider.get("ProcedureInfo.unknown");
	}

	/** Procedure Type. */
	private final int _procType;

	/** Procedure remarks. */
	private final String _remarks;

	public ProcedureInfo(String catalog, String schema, String simpleName,
							String remarks, int procType,
							SQLDatabaseMetaData md)
	{
		super(catalog, schema, simpleName, DatabaseObjectType.PROCEDURE, md);
		_remarks = remarks;
		_procType = procType;
	}

	public int getProcedureType()
	{
		return _procType;
	}

	public String getRemarks()
	{
		return _remarks;
	}

	public String getProcedureTypeDescription()
	{
		switch (_procType)
		{
			case DatabaseMetaData.procedureNoResult :
				return IStrings.DOESNT_RETURN;
			case DatabaseMetaData.procedureReturnsResult :
				return IStrings.DOES_RETURN;
			case DatabaseMetaData.procedureResultUnknown :
				return IStrings.MAY_RETURN;
			default :
				return IStrings.UNKNOWN;
		}
	}

	public boolean equals(Object obj)
	{
		if (super.equals(obj) && obj instanceof ProcedureInfo)
		{
			ProcedureInfo info = (ProcedureInfo) obj;
			if ((info._remarks == null && _remarks == null)
				|| ((info._remarks != null && _remarks != null)
					&& info._remarks.equals(_remarks)))
			{
				return info._procType == _procType;
			}
		}
		return false;
	}

}
