package com.sharifpro.db.dialects;

/**
 * A class that represents what properties of a sequence can be modified (increment, start, cycle, 
 * cache, etc.)  Some databases (such as HSQLDB and Netezza) restrict what properties of a sequence can be 
 * modified.
 *
 */
public class SequencePropertyMutability
{
	private boolean _restart = true;
	private boolean _startWith = true;
	private boolean _minValue = true;
	private boolean _maxValue = true;
	private boolean _cycle = true;
	private boolean _cache = true;
		
	/**
	 * @return the restart
	 */
	public boolean isRestart()
	{
		return _restart;
	}
	/**
	 * @param restart the restart to set
	 */
	public void setRestart(boolean restart)
	{
		this._restart = restart;
	}
	/**
	 * @return the startWith
	 */
	public boolean isStartWith()
	{
		return _startWith;
	}
	/**
	 * @param startWith the startWith to set
	 */
	public void setStartWith(boolean startWith)
	{
		this._startWith = startWith;
	}
	/**
	 * @return the minValue
	 */
	public boolean isMinValue()
	{
		return _minValue;
	}
	/**
	 * @param minValue the minValue to set
	 */
	public void setMinValue(boolean minValue)
	{
		this._minValue = minValue;
	}
	/**
	 * @return the maxValue
	 */
	public boolean isMaxValue()
	{
		return _maxValue;
	}
	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(boolean maxValue)
	{
		this._maxValue = maxValue;
	}
	/**
	 * @return the cycle
	 */
	public boolean isCycle()
	{
		return _cycle;
	}
	/**
	 * @param cycle the cycle to set
	 */
	public void setCycle(boolean cycle)
	{
		this._cycle = cycle;
	}
	/**
	 * @return the cache
	 */
	public boolean isCache()
	{
		return _cache;
	}
	/**
	 * @param cache the cache to set
	 */
	public void setCache(boolean cache)
	{
		this._cache = cache;
	}
	
	
}
