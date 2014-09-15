package com.sharifpro.db.meta;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sharifpro.db.dialects.DialectType;
import com.sharifpro.db.exception.DataSetException;

public class ResultSetDataSet implements IDataSet {
   // TODO: These 2 should be handled with an Iterator.
   private int _iCurrent = -1;

   private Object[] _currentRow;

   private int _columnCount;

   private List<Object[]> _alData;

   /** If <TT>true</TT> cancel has been requested. */
   private volatile boolean _cancel = false;

   /** the result set reader, which we will notify of cancel requests */
   private ResultSetReader rdr = null;

   /**
    * Default constructor.
    */
   public ResultSetDataSet() {
      super();
   }

   /**
    * Form used by Tabs other than ContentsTab
    * 
    * @param rs
    *           the ResultSet to set.
    * @param dialectType
    *           the type of dialect in use.
    * @throws DataSetException
    */
   public int setResultSet(ResultSet rs, DialectType dialectType)
         throws DataSetException {
      return setResultSet(rs, null, false, dialectType);
   }

   /**
    * Form used by ContentsTab, and for SQL results
    * 
    * @param rs
    *           the ResultSet to set.
    * @param fullTableName
    *           the fully-qualified table name
    * @param dialectType
    *           the type of dialect in use.
    * @throws DataSetException
    */
   public int setContentsTabResultSet(ResultSet rs, String fullTableName,
         DialectType dialectType) throws DataSetException {
      return setResultSet(rs, fullTableName, null, false, true, dialectType);
   }

   /**
    * Sets the ResultSet that contains the data
    * 
    * @param rs
    *           the ResultSet to set.
    * @param columnIndices
    *           columns to read from the specified ResultSet
    * @param dialectType
    *           the type of dialect in use.
    * @throws DataSetException
    */
   public int setResultSet(ResultSet rs, int[] columnIndices,
         DialectType dialectType) throws DataSetException {
      return setResultSet(rs, columnIndices, false, dialectType);
   }

   /**
    * External method to read the contents of a ResultSet that is used by all
    * Tab classes except ContentsTab. This tunrs all the data into strings for
    * simplicity of operation.
    */
   public int setResultSet(ResultSet rs, int[] columnIndices,
         boolean computeWidths, DialectType dialectType) throws DataSetException {
      return setResultSet(rs, null, columnIndices, computeWidths, false, dialectType);
   }

   /**
    * Internal method to read the contents of a ResultSet that is used by all
    * Tab classes
    *
    * @return The number of rows read from the ResultSet
    *
    */
   private int setResultSet(ResultSet rs, String fullTableName,
         int[] columnIndices, boolean computeWidths, boolean useColumnDefs,
         DialectType dialectType) throws DataSetException {
      reset();
      if (columnIndices != null && columnIndices.length == 0) {
         columnIndices = null;
      }
      _iCurrent = -1;
      _alData = new ArrayList<Object[]>();

      if (rs == null)
      {
         return 0;
      }

      try {
         ResultSetMetaData md = rs.getMetaData();
         _columnCount = columnIndices != null ? columnIndices.length
               : md.getColumnCount();

         // Read the entire row, since some drivers complain if columns are
         // read out of sequence
         rdr = new ResultSetReader(rs, dialectType);
         Object[] row = null;

         while (true) {
            row = rdr.readRow();

            if (row == null)
               break;

            if (_cancel) {
               return _alData.size();
            }

            // SS: now select/reorder columns
            if (columnIndices != null) {
               Object[] newRow = new Object[_columnCount];
               for (int i = 0; i < _columnCount; i++) {
                  if (columnIndices[i] - 1 < row.length) {
                     newRow[i] = row[columnIndices[i] - 1];
                  } else {
                     newRow[i] = "Unknown";
                  }
               }
               row = newRow;
            }
            _alData.add(row);
         }

         return _alData.size();

         // ColumnDisplayDefinition[] colDefs = createColumnDefinitions(md,
         // columnIndices, computeWidths);
         // _dataSetDefinition = new DataSetDefinition(colDefs);
      } catch (SQLException ex) {
         // Don't log an error message here. It is possible that the user
         // interrupted the query because it was taking too long. Just
         // throw the exception, and let the caller decide whether or not
         // the exception should be logged.
         throw new DataSetException(ex);
      }
   }

   public final int getColumnCount() {
      return _columnCount;
   }

   public synchronized boolean next()
         throws DataSetException {
      // TODO: This should be handled with an Iterator
      if (++_iCurrent < _alData.size()) {
         _currentRow = _alData.get(_iCurrent);
         return true;
      }
      return false;
   }

   /*
    * (non-Javadoc)
    * 
    * @see net.sourceforge.squirrel_sql.fw.datasetviewer.IDataSet#get(int)
    */
   public Object get(int columnIndex) {
      if (_currentRow != null) {
         return _currentRow[columnIndex];
      } else {
         return null;
      }
   }

   public void cancelProcessing() {
      rdr.setStopExecution(true);
      _cancel = true;
   }
   
   private void reset() {
      _iCurrent = -1;
      _currentRow = null;
      _columnCount = 0;
      _alData = null;
   }

   public void resetCursor() {
      _iCurrent = -1;
      _currentRow = null;
   }

   /**
    * Removes the row at the specified index. 
    * 
    * @param index the row number starting at 0.
    * @return the object at the specified row or null if there is not row at the
    *         specified index.
    */
   public Object removeRow(int index) {
      if (_alData.size() > index) {
         return _alData.remove(index);
      } else {
         return null;
      }
   }

   public List<Object[]> getAllDataForReadOnly()
   {
      return _alData;
   }
}
