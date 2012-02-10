package com.sharifpro.db.meta;

/**
 * This class represents a foreign key relationship.
 */
public class ForeignKeyInfo extends DatabaseObjectInfo
{
	private static final long serialVersionUID = -7975340741481407773L;

	/**
	 * JavaBean property names for this class.
	 */
	public interface IPropertyNames
	{
		/** Primary Key Catalog name. */
		String PK_CATALOG_NAME = "primaryKeyCatalogName";

		/** Primary Key Schema name. */
		String PK_SCHEMA_NAME = "primaryKeySchemaName";
	}
	
    private final String _pkCatalog;
	private final String _pkSchema;
	private final String _pkTableName;
    private final String _pkColumnName;
	private final String _fkTableName;
    private final String _fkColumnName;
	private final int _updateRule;
	private final int _deleteRule;
	private final String _pkName;
	private final int _deferability;
	private ForeignKeyColumnInfo[] _columnInfo;

    /**
     * @deprecated use the version of the constructor accepts fk and pk column
     * names. 
     */
    ForeignKeyInfo(String pkCatalog, String pkSchema, String pkTableName,
                   String fkCatalog, String fkSchema, String fkTableName,
                   int updateRule, int deleteRule, String fkName,
                   String pkName, int deferability,
                   ForeignKeyColumnInfo[] columnInfo, SQLDatabaseMetaData md)
    {
        super(fkCatalog, fkSchema, fkName, DatabaseObjectType.FOREIGN_KEY, md);
        _pkCatalog = pkCatalog;
        _pkSchema = pkSchema;
        _pkTableName = pkTableName;
        _fkTableName = fkTableName;
        _updateRule = updateRule;
        _deleteRule = deleteRule;
        _pkName = pkName;
        _deferability = deferability;
        setForeignKeyColumnInfo(columnInfo);    
        _pkColumnName = null;
        _fkColumnName = null;
    }
    
	ForeignKeyInfo(String pkCatalog, String pkSchema, String pkTableName,
                   String pkColumnName, String fkCatalog, String fkSchema, 
                   String fkTableName, String fkColumnName,
					int updateRule, int deleteRule, String fkName,
					String pkName, int deferability,
					ForeignKeyColumnInfo[] columnInfo, SQLDatabaseMetaData md)
	{
		super(fkCatalog, fkSchema, fkName, DatabaseObjectType.FOREIGN_KEY, md);
		_pkCatalog = pkCatalog;
		_pkSchema = pkSchema;
		_pkTableName = pkTableName;
        _pkColumnName = pkColumnName;
		_fkTableName = fkTableName;
        _fkColumnName = fkColumnName;
		_updateRule = updateRule;
		_deleteRule = deleteRule;
		_pkName = pkName;
		_deferability = deferability;
		setForeignKeyColumnInfo(columnInfo);
	}

	public String getPrimaryKeyCatalogName()
	{
		return _pkCatalog;
	}

	public String getPrimaryKeySchemaName()
	{
		return _pkSchema;
	}

	public String getPrimaryKeyTableName()
	{
		return _pkTableName;
	}

    public String getPrimaryKeyColumnName()
    {
        return _pkColumnName;
    }
    
	public String getPrimaryKeyName()
	{
		return _pkName;
	}

	public String getForeignKeyCatalogName()
	{
		return getCatalogName();
	}

	public String getForeignKeySchemaName()
	{
		return getSchemaName();
	}

	public String getForeignKeyTableName()
	{
		return _fkTableName;
	}

    public String getForeignKeyColumnName() {
        return _fkColumnName;
    }
    
	public String getForeignKeyName()
	{
		return getSimpleName();
	}

	public int getUpdateRule()
	{
		return _updateRule;
	}

	public int getDeleteRule()
	{
		return _deleteRule;
	}

	public int getDeferability()
	{
		return _deferability;
	}

	public ForeignKeyColumnInfo[] getForeignKeyColumnInfo()
	{
		return _columnInfo;
	}

	public void setForeignKeyColumnInfo(ForeignKeyColumnInfo[] value)
	{
		_columnInfo = value != null ? value : new ForeignKeyColumnInfo[0];
	}
}
