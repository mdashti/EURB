package com.sharifpro.db.meta;

import com.sharifpro.db.exception.DataSetException;

public interface IDataSet {
	int getColumnCount() throws DataSetException;
	boolean next() throws DataSetException;
	Object get(int columnIndex) throws DataSetException;
}