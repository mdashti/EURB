package com.sharifpro.db.exception;

/**
 * This exception indicates that a problem has occured in the processing
 * of a <CODE>IDataSet</CODE>.
 */
public class DataSetException extends BaseException
{
	private static final long serialVersionUID = -7858550140735991466L;

	/**
	 * Ctor.
	 *
	 * @param   msg	 Message describing the error.
	 */
	public DataSetException(String msg)
	{
		super(msg);
	}

	/**
	 * Ctor. Wraps this exception around another.
	 *
	 * @param   wrapee  The exception that this one is wrapped around.
	 */
	public DataSetException(Throwable wrapee)
	{
		super(wrapee);
	}
}