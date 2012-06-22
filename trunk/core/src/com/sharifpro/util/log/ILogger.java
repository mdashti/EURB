package com.sharifpro.util.log;

import org.apache.log4j.Level;

/**
 * This interface describes a logging object.
 *
 * @author  <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 */
public interface ILogger
{
	public void info(Object message);
	public void info(Object message, Throwable th);
	public void warn(Object message);
	public void warn(Object message, Throwable th);
	public void error(Object message);
	public void error(Object message, Throwable th);
	public void debug(Object message);
	public void debug(Object message, Throwable th);

	boolean isDebugEnabled();
	boolean isInfoEnabled();
    
    /**
     * Sets the log level of the logger.  For instance:
     * 
     * Level.ALL
     * Level.DEBUG
     * Level.ERROR
     * Level.FATAL
     * Level.INFO
     * Level.OFF
     * Level.WARN
     *  
     * @param l the level to set the logger to.
     */
    void setLevel(Level l);
}

