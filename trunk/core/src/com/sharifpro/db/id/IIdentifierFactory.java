package com.sharifpro.db.id;

/**
 * This interface defines the requirements for a factory that generates
 * indentifiers.
 *
 * @author <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 */
public interface IIdentifierFactory
{
	/**
	 * Create a new identifier.
	 *
	 * @return	The new identifier object.
	 */
	IIdentifier createIdentifier();
}
