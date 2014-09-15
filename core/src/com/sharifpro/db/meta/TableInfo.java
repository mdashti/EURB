package com.sharifpro.db.meta;

import java.util.SortedSet;
import java.util.TreeSet;

import com.sharifpro.eurb.management.mapping.model.TableMapping;

public class TableInfo extends DatabaseObjectInfo implements ITableInfo
{
	private static final long serialVersionUID = -6076125642707698698L;

	/** Table Type. */
	private final String _tableType;

	/** Table remarks. */
	private final String _remarks;

	private SortedSet<ITableInfo> _childList; // build up datastructure.
	private ITableInfo[] _childs; // final cache.

    ForeignKeyInfo[] exportedKeys = null;
    ForeignKeyInfo[] importedKeys = null;
    
	public TableInfo(String catalog, String schema, String simpleName,
					 String tableType, String remarks,
					 SQLDatabaseMetaData md)
	{
		super(catalog, schema, simpleName, getTableType(tableType), md);
		_remarks = remarks;
		_tableType = tableType;
	}

   private static DatabaseObjectType getTableType(String tableType)
   {
      if(null == tableType)
      {
         return DatabaseObjectType.TABLE;
      }
      else if(false == tableType.equalsIgnoreCase("TABLE") && false == tableType.equalsIgnoreCase("VIEW"))
      {
         return DatabaseObjectType.TABLE;
      }
      else
      {
         return tableType.equalsIgnoreCase("VIEW") ? DatabaseObjectType.VIEW : DatabaseObjectType.TABLE;
      }
   }

   public void replaceDatabaseObjectTypeConstantObjectsByConstantObjectsOfThisVM()
   {
      if(DatabaseObjectType.TABLE.getKeyForSerializationReplace().equals(super.getDatabaseObjectType().getKeyForSerializationReplace()))
      {
         super.replaceDatabaseObjectTypeConstantObjectsByConstantObjectsOfThisVM(DatabaseObjectType.TABLE);
      }
      else if (DatabaseObjectType.TABLE.getKeyForSerializationReplace().equals(super.getDatabaseObjectType().getKeyForSerializationReplace()))
      {
         super.replaceDatabaseObjectTypeConstantObjectsByConstantObjectsOfThisVM(DatabaseObjectType.VIEW);
      }
   }


   // TODO: Rename this to getTableType.
   public String getType()
   {
      return _tableType;
   }

	public String getRemarks()
	{
		return _remarks;
	}

	public boolean equals(Object _other)
	{
		if(_other instanceof TableMapping) {
			TableMapping _cast = (TableMapping)_other;
			
			if (_cast.getCatalog() == null ? this.getCatalogName() != _cast.getCatalog() : !_cast.getCatalog().equals( this.getCatalogName() )) {
				return false;
			}
			
			if (_cast.getSchema() == null ? this.getSchemaName() != _cast.getSchema() : !_cast.getSchema().equals( this.getSchemaName() )) {
				return false;
			}
			
			if (!_cast.getTableName().equals( this.getSimpleName() )) {
				return false;
			}
			
			if (TableMapping.MAPPED_TYPE_TABLE == _cast.getMappedType() ? !"TABLE".equals(this.getType()) : !"VIEW".equals(this.getType())) {
				return false;
			}
			return true;
		}
		if (super.equals(_other) && _other instanceof TableInfo)
		{
			TableInfo info = (TableInfo) _other;
			if ((info._tableType == null && _tableType == null)
				|| ((info._tableType != null && _tableType != null)
					&& info._tableType.equals(_tableType)))
			{
				return (
					(info._remarks == null && _remarks == null)
						|| ((info._remarks != null && _remarks != null)
							&& info._remarks.equals(_remarks)));
			}
		}
		return false;
	}

	void addChild(ITableInfo tab)
	{
		if (_childList == null)
		{
			_childList = new TreeSet<ITableInfo>();
		}
		_childList.add(tab);
	}

	public ITableInfo[] getChildTables()
	{
		if (_childs == null && _childList != null)
		{
			_childs = _childList.toArray(new ITableInfo[_childList.size()]);
			_childList = null;
		}
		return _childs;
	}


    /* (non-Javadoc)
     * @see net.sourceforge.squirrel_sql.fw.sql.ITableInfo#getExportedKeys()
     */
    public ForeignKeyInfo[] getExportedKeys() {
        return exportedKeys;
    }

    public void setExportedKeys(ForeignKeyInfo[] foreignKeys) {
        exportedKeys = foreignKeys;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.squirrel_sql.fw.sql.ITableInfo#getImportedKeys()
     */
    public ForeignKeyInfo[] getImportedKeys() {
        return importedKeys;
    }

    public void setImportedKeys(ForeignKeyInfo[] foreignKeys) {
        importedKeys = foreignKeys;
    }



}
