package com.sharifpro.db.meta;

/**
 * Encapsulates metadata about a single index column.
 */
public class IndexInfo extends DatabaseObjectInfo {
	private static final long serialVersionUID = -7333985699141129227L;

	public static enum IndexType {
        STATISTIC,
        CLUSTERED,
        HASHED,
        OTHER
    }
    
    public static enum SortOrder {
        ASC,
        DESC,
        NONE
    }
    
    /** 
     * the name of the column which belongs to a list of columns that form an 
     * index for a table
     */
    private String columnName = null;
    
    private String tableName = null;
    
    private boolean nonUnique = false;
    
    private String indexQualifier = null;
    
    private IndexType indexType = null;
    
    private short ordinalPosition;
    
    private SortOrder sortOrder = null;
    
    private int cardinality;
    
    private int pages;
    
    private String filterCondition = null;
    
    public IndexInfo(String catalog, 
                     String schema, 
                     String indexName,
                     String tableName,
                     String columnName,
                     boolean nonUnique,
                     String indexQualifier,
                     IndexType indexType,
                     short ordinalPosition,
                     SortOrder sortOrder,
                     int cardinality,
                     int pages,
                     String filterCondition,
                     ISQLDatabaseMetaData md) 
    {
        super(catalog, schema, indexName, DatabaseObjectType.INDEX, md);
        this.tableName = tableName;
        this.columnName = columnName;
        this.nonUnique = nonUnique;
        this.indexType = indexType;
        this.ordinalPosition = ordinalPosition;
        this.sortOrder = sortOrder;
        this.cardinality = cardinality;
        this.pages = pages;
        this.filterCondition = filterCondition;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param nonUnique the nonUnique to set
     */
    public void setNonUnique(boolean nonUnique) {
        this.nonUnique = nonUnique;
    }

    /**
     * @return the nonUnique
     */
    public boolean isNonUnique() {
        return nonUnique;
    }

    /**
     * @param indexType the indexType to set
     */
    public void setIndexType(IndexType indexType) {
        this.indexType = indexType;
    }

    /**
     * @return the indexType
     */
    public IndexType getIndexType() {
        return indexType;
    }

    /**
     * @param ordinalPosition the ordinalPosition to set
     */
    public void setOrdinalPosition(short ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    /**
     * @return the ordinalPosition
     */
    public short getOrdinalPosition() {
        return ordinalPosition;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the sortOrder
     */
    public SortOrder getSortOrder() {
        return sortOrder;
    }

    /**
     * @param cardinality the cardinality to set
     */
    public void setCardinality(int cardinality) {
        this.cardinality = cardinality;
    }

    /**
     * @return the cardinality
     */
    public int getCardinality() {
        return cardinality;
    }

    /**
     * @param pages the pages to set
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    /**
     * @return the pages
     */
    public int getPages() {
        return pages;
    }

    /**
     * @param filterCondition the filterCondition to set
     */
    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    /**
     * @return the filterCondition
     */
    public String getFilterCondition() {
        return filterCondition;
    }

    /**
     * @param indexQualifier the indexQualifier to set
     */
    public void setIndexQualifier(String indexQualifier) {
        this.indexQualifier = indexQualifier;
    }

    /**
     * @return the indexQualifier
     */
    public String getIndexQualifier() {
        return indexQualifier;
    }
}
