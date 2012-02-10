package com.sharifpro.db.meta;

import java.lang.reflect.Method;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sharifpro.db.util.SQLUtilities;
import com.sharifpro.util.PropertyProvider;


public class MetaDataDataSet implements IDataSet
{

	private final static Map<String, Object> s_ignoreMethods = 
        new HashMap<String, Object>();
	static
	{
		s_ignoreMethods.put("getCatalogs", null);
		s_ignoreMethods.put("getConnection", null);
		s_ignoreMethods.put("getSchemas", null);
		s_ignoreMethods.put("getTableTypes", null);
		s_ignoreMethods.put("getTypeInfo", null);
		s_ignoreMethods.put("fail", null);
		s_ignoreMethods.put("hashCode", null);
		s_ignoreMethods.put("toString", null);
		s_ignoreMethods.put("getNumericFunctions", null);
		s_ignoreMethods.put("getStringFunctions", null);
		s_ignoreMethods.put("getSystemFunctions", null);
		s_ignoreMethods.put("getTimeDateFunctions", null);
		s_ignoreMethods.put("getSQLKeywords", null);
	}

	private static interface IStrings
	{
		String UNSUPPORTED = PropertyProvider.get("MetaDataDataSet.unsupported");
		String NAME_COLUMN = PropertyProvider.get("MetaDataDataSet.propname");
		String VALUE_COLUMN = PropertyProvider.get("MetaDataDataSet.value");
	}

	private final static String[] s_hdgs =
		new String[] { IStrings.NAME_COLUMN, IStrings.VALUE_COLUMN };

	private Iterator<Object[]> _rowsIter;
	private Object[] _row;

	/**
	 * Data. Each element represents a row of the table and is made up of
	 * an array of strings. Each string is an element in the row.
	 */
	private List<Object[]> _data = new ArrayList<Object[]>();

	public MetaDataDataSet(DatabaseMetaData md)
	{
		super();
		load(md);
	}

	public final int getColumnCount()
	{
		return s_hdgs.length;
	}

	public synchronized boolean next()
	{
		if (_rowsIter.hasNext())
		{
			_row = _rowsIter.next();
		}
		else
		{
			_row = null;
		}
		return _row != null;
	}

	public synchronized Object get(int columnIndex)
	{
		return _row[columnIndex];
	}

	private void load(DatabaseMetaData md)
	{
		Method[] methods = DatabaseMetaData.class.getMethods();
		for (int i = 0; i < methods.length; ++i)
		{
			final Method method = methods[i];
			if (method.getParameterTypes().length == 0
				&& method.getReturnType() != Void.TYPE
				&& !s_ignoreMethods.containsKey(method.getName()))
			{
				_data.add(generateLine(md, method));
			}
		}

		// Sort the rows by the property name.
//		Collections.sort(_data, new DataSorter());

		_rowsIter = _data.iterator();
	}

	/**
	 * Generate a line for the result of calling the passed method.
	 *
	 * @param   getter	  The "getter" function to retrieve the
	 *					  properties value.
	 *
	 * @return  An <TT>Object[]</CODE> containing the cells for the line in
	 *		  the table. Element zero the first cell etc. Return
	 *		  <CODE>null</CODE> if this property is <B>not</B> to be added
	 *		  to the table.
	 */
	private Object[] generateLine(DatabaseMetaData md, Method getter)
	{
		final Object[] line = new Object[2];
		line[0] = getter.getName();
		if (line[0].equals("getDefaultTransactionIsolation"))
		{
			try
			{
				line[1] = IStrings.UNSUPPORTED;
				final int isol = md.getDefaultTransactionIsolation();
				switch (isol)
				{
					case java.sql.Connection.TRANSACTION_NONE :
						{
							line[1] = "TRANSACTION_NONE";
							break;
						}
					case java.sql.Connection.TRANSACTION_READ_COMMITTED :
						{
							line[1] = "TRANSACTION_READ_COMMITTED";
							break;
						}
					case java.sql.Connection.TRANSACTION_READ_UNCOMMITTED :
						{
							line[1] = "TRANSACTION_READ_UNCOMMITTED";
							break;
						}
					case java.sql.Connection.TRANSACTION_REPEATABLE_READ :
						{
							line[1] = "TRANSACTION_REPEATABLE_READ";
							break;
						}
					case java.sql.Connection.TRANSACTION_SERIALIZABLE :
						{
							line[1] = "TRANSACTION_SERIALIZABLE";
							break;
						}
					default :
						{
							line[1] = "" + isol + "?";
							break;
						}
				}
			}
			catch (SQLException ex)
			{
				//_msgHandler.showMessage(ex, null);
			}

		}
		else if (line[0].equals("getClientInfoProperties")) 
		{
			Object obj = executeGetter(md, getter);
			if (obj instanceof ResultSet) {
				ResultSet rs = (ResultSet)obj;
				try {
					StringBuilder tmp = new StringBuilder();
					while (rs.next()) {
						tmp.append(rs.getString(1)).append("\t");
						tmp.append(rs.getInt(2)).append("\t");
						tmp.append(rs.getString(3)).append("\t");
						tmp.append(rs.getString(4)).append("\n");
					}
					line[1] = tmp.toString();
				} catch (SQLException ex) {
					//_msgHandler.showMessage(ex, null);
				} finally {
					SQLUtilities.closeResultSet(rs);
				}
			} else {
				line[1] = obj;
			}
		} 
		else
		{
			Object obj = executeGetter(md, getter);
			line[1] = obj;
		}
		return line;
	}

	protected Object executeGetter(Object bean, Method getter)
	{
		try
		{
			return getter.invoke(bean, (Object[])null);
		}
		catch (Throwable th)
		{
			return IStrings.UNSUPPORTED;
		}
	}
}
