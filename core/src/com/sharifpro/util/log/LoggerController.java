package com.sharifpro.util.log;

import java.util.Vector;

import org.apache.log4j.Level;

public class LoggerController
{
	private static Vector<ILoggerFactory> s_oldfactories = new Vector<ILoggerFactory>();
	private static ILoggerFactory s_factory = new Log4jLoggerFactory();
	
	/** Whether or not to override the level configured in the properties file */ 
	private static boolean forceDebug = false;

	public static void registerLoggerFactory(ILoggerFactory factory)
	{
		s_oldfactories.add(s_factory);
		s_factory = factory != null ? factory : new Log4jLoggerFactory();
	}

	public static ILogger createLogger(Class<?> clazz)
	{
		ILogger result = s_factory.createLogger(clazz);
		if (forceDebug) {
			result.setLevel(Level.DEBUG);
		}
		return result;
	}

	public static void shutdown()
	{
		s_factory.shutdown();
	}

	public static void addLoggerListener(ILoggerListener l)
	{
		s_factory.addLoggerListener(l);

		for (int i = 0; i < s_oldfactories.size(); i++)
		{
			ILoggerFactory iLoggerFactory = s_oldfactories.get(i);
			iLoggerFactory.addLoggerListener(l);
		}
	}

	public static void removeLoggerListener(ILoggerListener l)
	{
		s_factory.removeLoggerListener(l);

		for (int i = 0; i < s_oldfactories.size(); i++)
		{
			ILoggerFactory iLoggerFactory = s_oldfactories.get(i);
			iLoggerFactory.removeLoggerListener(l);
		}

	}

	/**
	 * @param forceDebug the forceDebug to set
	 */
	public static void setForceDebug(boolean forceDebug)
	{
		LoggerController.forceDebug = forceDebug;
	}

	/**
	 * @return the forceDebug
	 */
	public static boolean isForceDebug()
	{
		return forceDebug;
	}

}