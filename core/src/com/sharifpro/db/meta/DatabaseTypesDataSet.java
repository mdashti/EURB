package com.sharifpro.db.meta;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sharifpro.db.exception.DataSetException;
import com.sharifpro.db.util.SQLUtilities;
import com.sharifpro.util.PropertyProvider;

public class DatabaseTypesDataSet implements IDataSet
{


	private static interface i18n
	{

		// i18n[DatabaseMetaData.nullableTypeNoNulls=false]
		String NULLABLE_TYPE_NO_NULLS = PropertyProvider.get("DatabaseMetaData.nullableTypeNoNulls");

		// i18n[DatabaseMetaData.nullableTypeNullable=true]
		String NULLABLE_TYPE_NULLABLE = PropertyProvider.get("DatabaseMetaData.nullableTypeNullable");

		// i18n[DatabaseMetaData.nullableTypeNullableUnknown=unknown]
		String NULLABLE_TYPE_NULLABLE_UNKNOWN =
			PropertyProvider.get("DatabaseMetaData.nullableTypeNullableUnknown");

		// i18n[DatabaseMetaData.searchableTypePredNone=no support]
		String SEARCHABLE_TYPE_PRED_NONE = PropertyProvider.get("DatabaseMetaData.searchableTypePredNone");

		// i18n[DatabaseMetaData.searchableTypePredChar=only supports 'WHERE...LIKE']
		String SEARCHABLE_TYPE_PRED_CHAR = PropertyProvider.get("DatabaseMetaData.searchableTypePredChar");

		// i18n[DatabaseMetaData.searchableTypePredBasic=supports all except 'WHERE...LIKE']
		String SEARCHABLE_TYPE_PRED_BASIC = PropertyProvider.get("DatabaseMetaData.searchableTypePredBasic");

		// i18n[DatabaseMetaData.searchableTypeSearchable=supports all WHERE]
		String SEARCHABLE_TYPE_SEARCHABLE = PropertyProvider.get("DatabaseMetaData.searchableTypeSearchable");

	}

	private int[] _columnIndices;

	private int _columnCount;

	private List<Object[]> _allData = new ArrayList<Object[]>();

	private int _currentRowIdx = -1;

	public DatabaseTypesDataSet(ResultSet rs) throws DataSetException
	{
		this(rs, null);
	}

	public DatabaseTypesDataSet(ResultSet rs, int[] columnIndices) throws DataSetException
	{
		super();

		if (columnIndices != null && columnIndices.length == 0)
		{
			columnIndices = null;
		}
		_columnIndices = columnIndices;

		if (rs != null)
		{
			try
			{
				ResultSetMetaData md = rs.getMetaData();
				_columnCount = columnIndices != null ? columnIndices.length : md.getColumnCount();
			}
			catch (SQLException ex)
			{
				throw new DataSetException(ex);
			}
		}
		setResultSet(rs);
	}

	public final int getColumnCount()
	{
		return _columnCount;
	}

	public synchronized boolean next() throws DataSetException
	{
		if (_currentRowIdx < _allData.size() - 1)
		{
			_currentRowIdx++;
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Helper method to read a row from the specified ResultSet.
	 * 
	 * @return an array of column values in the next row.
	 */
	private Object[] getNextRow(ResultSet rs) throws SQLException
	{
		Object[] _row = new Object[_columnCount];
		for (int i = 0; i < _columnCount; ++i)
		{
			int idx = _columnIndices != null ? _columnIndices[i] : i + 1;
			switch (idx)
			{
			case 2:
				// DATA_TYPE column of result set.
				// int data = _rs.getInt(idx);
				int data = rs.getInt(idx);
				StringBuilder buf = new StringBuilder();
				buf.append(String.valueOf(data))
					.append(" [")
					.append(JDBCTypeMapper.getJdbcTypeName(data))
					.append("]");
				_row[i] = buf.toString();
				break;

			case 3:
			case 14:
			case 15:
			case 18:
				_row[i] = rs.getObject(idx);
				if (_row[i] != null && !(_row[i] instanceof Integer))
				{
					if (_row[i] instanceof Number)
					{
						_row[i] = ((Number) _row[i]).intValue();
					}
					else
					{
						_row[i] = new Integer(_row[i].toString());
					}
				}
				break;

			case 7:
				// NULLABLE column of result set.
				short nullable = rs.getShort(idx);
				switch (nullable)
				{
				case DatabaseMetaData.typeNoNulls:
					_row[i] = i18n.NULLABLE_TYPE_NO_NULLS;
					break;
				case DatabaseMetaData.typeNullable:
					_row[i] = i18n.NULLABLE_TYPE_NULLABLE;
					break;
				case DatabaseMetaData.typeNullableUnknown:
					_row[i] = i18n.NULLABLE_TYPE_NULLABLE_UNKNOWN;
					break;
				default:
					_row[i] = nullable + "[error]";
					break;
				}
				break;

			case 8:
			case 10:
			case 11:
			case 12:
				// boolean columns
				// _row[i] = _rs.getBoolean(idx) ? "true" : "false";
				_row[i] = rs.getObject(idx);
				if (_row[i] != null && !(_row[i] instanceof Boolean))
				{
					if (_row[i] instanceof Number)
					{
						if (((Number) _row[i]).intValue() == 0)
						{
							_row[i] = Boolean.FALSE;
						}
						else
						{
							_row[i] = Boolean.TRUE;
						}
					}
					else
					{
						_row[i] = Boolean.valueOf(_row[i].toString());
					}
				}
				break;

			case 9:
				// SEARCHABLE column of result set.
				short searchable = rs.getShort(idx);
				switch (searchable)
				{
				case DatabaseMetaData.typePredNone:
					_row[i] = i18n.SEARCHABLE_TYPE_PRED_NONE;
					break;
				case DatabaseMetaData.typePredChar:
					_row[i] = i18n.SEARCHABLE_TYPE_PRED_CHAR;
					break;
				case DatabaseMetaData.typePredBasic:
					_row[i] = i18n.SEARCHABLE_TYPE_PRED_BASIC;
					break;
				case DatabaseMetaData.typeSearchable:
					_row[i] = i18n.SEARCHABLE_TYPE_SEARCHABLE;
					break;
				default:
					_row[i] = searchable + "[error]";
					break;
				}
				break;

			case 16:
			case 17:
				// ignore - unused.
				break;

			default:
				_row[i] = rs.getString(idx);
				break;

			}
		}
		return _row;
	}

	/**
     * 
     */
	public Object get(int columnIndex)
	{
		Object[] currentRow = _allData.get(_currentRowIdx);
		return currentRow[columnIndex];
	}

	/**
	 * Reads the specified ResultSet of all of it's rows and closes it.
	 * 
	 * @param rs
	 *           the ResultSet to read from.
	 * @throws DataSetException
	 */
	private void setResultSet(ResultSet rs) throws DataSetException
	{
		if (rs == null) { return; }
		try
		{
			while (rs.next())
			{
				Object[] row = getNextRow(rs);
				_allData.add(row);
			}
		}
		catch (SQLException e)
		{
			throw new DataSetException(e);
		}
		finally
		{
			SQLUtilities.closeResultSet(rs);
		}
	}
}
