package com.sharifpro.db.id;

/**
 * This class is a factory that generates <tt>UidIdentifier</tt>
 * objects.
 *
 * @author <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 */
public class UidIdentifierFactory implements IIdentifierFactory
{
	/**
	 * Default ctor.
	 */
	public UidIdentifierFactory()
	{
		super();
	}

	/**
	 * Create a new identifier.
	 *
	 * @return	The new identifier object.
	 */
	public synchronized IIdentifier createIdentifier()
	{
		return new UidIdentifier();
	}
}
