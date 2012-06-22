package com.sharifpro.db.id;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
/**
 * This is the <CODE>BeanInfo</CODE> class for <CODE>UidIdentifier</CODE>.
 *
 * @author <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 */
public class UidIdentifierBeanInfo extends SimpleBeanInfo
{

	/**
	 * See http://tinyurl.com/63no6t for discussion of the proper thread-safe way to implement
	 * getPropertyDescriptors().
	 * 
	 * @see java.beans.SimpleBeanInfo#getPropertyDescriptors()
	 */
	@Override		
	public PropertyDescriptor[] getPropertyDescriptors()
	{
		try
		{
			PropertyDescriptor[] result = new PropertyDescriptor[1];
			result[0] =
				new PropertyDescriptor(
					UidIdentifier.IPropertyNames.STRING,
					UidIdentifier.class,
					"toString",
					"setString");
			
			return result;
		}
		catch (IntrospectionException e)
		{
			throw new Error(e);
		}
	}
}
