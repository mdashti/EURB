package com.sharifpro.util.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.sharifpro.db.util.Utilities;

/**
 * This is a logger that logs using the Apache log4j package.
 * 
 * @author <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 */
public class Log4jLogger implements ILogger
{
	/** Log4j logger to log to. */
	private Logger _log;

	private ILoggerListener _listener;

	private Class<?> _clazz;

	/**
	 * Ctor specifying the object requesting the logger. A Log4J <TT>Logger</TT> is created using as a name
	 * the fully qualified class of <TT>requester</TT>.
	 * 
	 * @param clazz
	 *           The class requesting a logger.
	 * @param listener
	 *           the ILoggerListener implementation
	 * @throws IllegalArgumentException
	 *            Thrown if <TT>clazz</TT> or <TT>listener<TT> are <TT>null</TT>.
	 */
	Log4jLogger(Class<?> clazz, ILoggerListener listener)
	{
		Utilities.checkNull("Log4jLogger.init","clazz", clazz, "listener", listener);
		_listener = listener;
		_clazz = clazz;
		_log = Logger.getLogger(clazz);
      _log.setLevel(Level.INFO);
	}

	/**
	 * @see ILogger#debug(Object)
	 */
	public void debug(Object message)
	{
		_log.debug(message);
	}

	/**
	 * @see ILogger#debug(Object, Throwable)
	 */
	public void debug(Object message, Throwable th)
	{
		_log.debug(message, th);
	}

	/**
	 * @see ILogger#info(Object)
	 */
	public void info(Object message)
	{
		_log.info(message);
		_listener.info(_clazz, message);
	}

	/**
	 * @see ILogger#info(Object, Throwable)
	 */
	public void info(Object message, Throwable th)
	{
		_log.info(message, th);
		_listener.info(_clazz, message, th);
	}

	/**
	 * @see ILogger#warn(Object)
	 */
	public void warn(Object message)
	{
		_log.warn(message);
		_listener.warn(_clazz, message);
	}

	/**
	 * @see ILogger#warn(Object, Throwable)
	 */
	public void warn(Object message, Throwable th)
	{
		_log.warn(message, th);
		_listener.warn(_clazz, message, th);
	}

	/**
	 * @see ILogger#error(Object)
	 */
	public void error(Object message)
	{
		_log.error(message);
		_listener.error(_clazz, message);
	}

	/**
	 * @see ILogger#error(Object, Throwable)
	 */
	public void error(Object message, Throwable th)
	{
		_log.error(message, th);
		_listener.error(_clazz, message, th);
	}

	/**
	 * @see ILogger#isDebugEnabled()
	 */
	public boolean isDebugEnabled()
	{
		return _log.isDebugEnabled();
	}

	/**
	 * @see ILogger#isInfoEnabled()
	 */
	public boolean isInfoEnabled()
	{
		return _log.isInfoEnabled();
	}

	/**
	 * Sets the log level of the logger. For instance:
	 * <ul>
	 * <li>Level.ALL</li>
	 * <li>Level.DEBUG</li>
	 * <li>Level.ERROR</li>
	 * <li>Level.FATAL</li>
	 * <li>Level.INFO</li>
	 * <li>Level.OFF</li>
	 * <li>Level.WARN</li>
	 * </ul>
	 * 
	 * @param l
	 *           the level to set the logger to.
	 */
	public void setLevel(Level l)
	{
		_log.setLevel(l);
	}
}
