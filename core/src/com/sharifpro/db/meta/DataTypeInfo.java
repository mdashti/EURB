package com.sharifpro.db.meta;

public class DataTypeInfo extends DatabaseObjectInfo
{
	private static final long serialVersionUID = -4813324299931302154L;
	private final int _dataType;
	private final int _precision;
	private final String _literalPrefix;
	private final String _literalSuffix;
	private final String _createParams;
	private final int _nullable;
	private final boolean _caseSensitive;
	private final int _searchable;
	private final boolean _unsigned;
	private final boolean _money;
	private final boolean _autoIncrement;
	private final String _localTypeName;
	private final int _minScale;
	private final int _maxScale;
	private final int _numPrecRadix;

	DataTypeInfo(String typeName, int dataType, int precision,
					String literalPrefix, String literalSuffix,
					String createParams, int nullable, boolean caseSensitive,
					int searchable, boolean unsigned, boolean money,
					boolean autoIncrement, String localTypeName,
					int minScale, int maxScale, int numPrecRadix,
					SQLDatabaseMetaData md)
	{
		super(null, null, typeName, DatabaseObjectType.DATATYPE, md);
		_dataType = dataType;
		_precision = precision;
		_literalPrefix = literalPrefix;
		_literalSuffix = literalSuffix;
		_createParams = createParams;
		_nullable = nullable;
		_caseSensitive = caseSensitive;
		_searchable = searchable;
		_unsigned = unsigned;
		_money = money;
		_autoIncrement = autoIncrement;
		_localTypeName = localTypeName;
		_minScale = minScale;
		_maxScale = maxScale;
		_numPrecRadix = numPrecRadix;
	}

	public int getDataType()
	{
		return _dataType;
	}

	public int getPrecision()
	{
		return _precision;
	}

	public String getLiteralPrefix()
	{
		return _literalPrefix;
	}

	public String getLiteralSuffix()
	{
		return _literalSuffix;
	}

	public String getCreateParams()
	{
		return _createParams;
	}

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (_autoIncrement ? 1231 : 1237);
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        final DataTypeInfo other = (DataTypeInfo) obj;
        if (!getSimpleName().equals(other.getSimpleName()))
            return false;
        return true;
    }

    /**
     * @return the nullable
     */
    public int getNullable() {
        return _nullable;
    }

    /**
     * @return the caseSensitive
     */
    public boolean isCaseSensitive() {
        return _caseSensitive;
    }

    /**
     * @return the searchable
     */
    public int getSearchable() {
        return _searchable;
    }

    /**
     * @return the unsigned
     */
    public boolean isUnsigned() {
        return _unsigned;
    }

    /**
     * @return the money
     */
    public boolean isMoney() {
        return _money;
    }

    /**
     * @return the autoIncrement
     */
    public boolean isAutoIncrement() {
        return _autoIncrement;
    }

    /**
     * @return the localTypeName
     */
    public String getLocalTypeName() {
        return _localTypeName;
    }

    /**
     * @return the minScale
     */
    public int getMinScale() {
        return _minScale;
    }

    /**
     * @return the maxScale
     */
    public int getMaxScale() {
        return _maxScale;
    }

    /**
     * @return the numPrecRadix
     */
    public int getNumPrecRadix() {
        return _numPrecRadix;
    }
}
