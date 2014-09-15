package com.sharifpro.util.xml;

/**
 * This interface is used to declare that a javabean requires to run some code
 * prior to being written out as XML.
 */
public interface IXMLAboutToBeWritten
{
	/**
	 * This JavaBean is about to be written out as XML.
	 */
	void aboutToBeWritten();
}
