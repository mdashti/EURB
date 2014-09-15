package com.sharifpro.db.meta;

import java.io.Serializable;

public class DatabaseObjectInfo implements IDatabaseObjectInfo, Serializable
{
	private static final long serialVersionUID = 7037035868636032327L;
	/** Property names for this bean. */
   public interface IPropertyNames
   {
      /** Catalog name. */
      String CATALOG_NAME = "catalogName";

      /** Schema name. */
      String SCHEMA_NAME = "schemaName";

      /** Simple name. */
      String SIMPLE_NAME = "simpleName";

      /** Qualified name. */
      String QUALIFIED_NAME = "qualifiedName";
   }


/** Catalog name. Can be <CODE>null</CODE> */
   private final String _catalog;

   /** Schema name. Can be <CODE>null</CODE> */
   private final String _schema;

   /** Simple object name. */
   private final String _simpleName;

   /** Object type. @see DatabaseObjectType.*/
   private DatabaseObjectType _dboType = DatabaseObjectType.OTHER;

   public DatabaseObjectInfo(String catalog, String schema, String simpleName,
                             DatabaseObjectType dboType, ISQLDatabaseMetaData md)
   {
      super();
      if (dboType == null)
      {
         throw new IllegalArgumentException("DatabaseObjectType == null");
      }
      if (md == null)
      {
         throw new IllegalArgumentException("SQLDatabaseMetaData == null");
      }

      _catalog = catalog;
      _schema = schema;
      _simpleName = simpleName;
      _dboType = dboType;
   }

   /**
    * Default constructor for using instances of this class to contain 
    * information about new objects that will be created soon.
    */
   public DatabaseObjectInfo(String catalog, String schema, String simpleName) {
       _catalog = catalog;
       _schema = schema;
       _simpleName = simpleName;
   }
   
   public String toString()
   {
      return getSimpleName();
   }

   public String getCatalogName()
   {
      return _catalog;
   }


   public String getSchemaName()
   {
      return _schema;
   }

   public String getSimpleName()
   {
      return _simpleName;
   }

   public DatabaseObjectType getDatabaseObjectType()
   {
      return _dboType;
   }
   
   public boolean equals(Object obj)
   {
      if (obj == null) {
          return false;
      }
      if (obj.getClass() == this.getClass())
      {
         DatabaseObjectInfo info = (DatabaseObjectInfo) obj;
         if ((info._catalog == null && _catalog == null)
            || ((info._catalog != null && _catalog != null)
               && info._catalog.equals(_catalog)))
         {
           if ((info._schema == null && _schema == null)
              || ((info._schema != null && _schema != null)
                 && info._schema.equals(_schema)))
           {
              return (
                 (info._simpleName == null && _simpleName == null)
                    || ((info._simpleName != null
                       && _simpleName != null)
                       && info._simpleName.equals(_simpleName)));
           }
         }
      }
      return false;
   }

   public int hashCode()
   {
      return _simpleName.hashCode();
   }

   public int compareTo(IDatabaseObjectInfo o)
   {
      return _simpleName.compareTo(o.getSimpleName());
   }

   public void replaceDatabaseObjectTypeConstantObjectsByConstantObjectsOfThisVM(DatabaseObjectType type)
   {
      _dboType = type;
   }
}
