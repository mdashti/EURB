package com.sharifpro.util.xml;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;

import com.sharifpro.db.util.FileWrapper;
import com.sharifpro.db.util.beanwrapper.StringWrapper;

import net.n3.nanoxml.IXMLElement;
import net.n3.nanoxml.XMLElement;
import net.n3.nanoxml.XMLWriter;

public final class XMLBeanWriter
{
	private IXMLElement _rootElement;

	public XMLBeanWriter() throws XMLException
	{
		this(null);
	}

	public XMLBeanWriter(Object bean) throws XMLException
	{
		super();
		_rootElement = new XMLElement(XMLConstants.ROOT_ELEMENT_NAME);
		if (bean != null)
		{
			addToRoot(bean);
		}
	}

	public void addIteratorToRoot(Iterator it) throws XMLException
	{
		while (it.hasNext())
		{
			addToRoot(it.next());
		}
	}

	public void addToRoot(Object bean) throws XMLException
	{
		try
		{
			_rootElement.addChild(createElement(bean, null));
		}
		catch (Exception ex)
		{
			throw new XMLException(ex);
		}
	}

	/**
	 * Saves this xml bean to the specified FileWrapper
	 * 
	 * @param file the FileWrapper that wraps the file to be written to
	 * 
	 * @throws IOException if an error occurs while writing the bean to the specified FileWrapper
	 */
	public void save(FileWrapper file) throws IOException
	{
		save(file.getFileOutputStream());
	}

	public void save(String fileName) throws IOException
	{
		save(new FileOutputStream(fileName));
	}

	public void save(File file) throws IOException
	{
		save(new FileOutputStream(file));
	}

	private void save(FileOutputStream fos) throws IOException
	{
		BufferedOutputStream os = new BufferedOutputStream(fos);
		try
		{
			XMLWriter wtr = new XMLWriter(os);
			wtr.write(_rootElement, true);
		}
		finally
		{
			os.close();
		}
	}

	private IXMLElement createElement(Object bean, String name) throws XMLException
	{
		IXMLElement elem = null;
		BeanInfo info = null;
		try
		{
			if (bean != null)
			{
				info = Introspector.getBeanInfo(bean.getClass(), Object.class);
			}
		}
		catch (IntrospectionException ex)
		{
			throw new XMLException(ex);
		}
		elem = new XMLElement(name != null ? name : XMLConstants.BEAN_ELEMENT_NAME);
		if (info != null)
		{
			if (bean instanceof IXMLAboutToBeWritten)
			{
				((IXMLAboutToBeWritten) bean).aboutToBeWritten();
			}
			PropertyDescriptor[] propDesc = info.getPropertyDescriptors();
			elem = new XMLElement(name != null ? name : XMLConstants.BEAN_ELEMENT_NAME);
			elem.setAttribute(XMLConstants.CLASS_ATTRIBUTE_NAME, bean.getClass().getName());
			for (int i = 0; i < propDesc.length; ++i)
			{
				processProperty(propDesc[i], bean, elem);
			}
		}
		return elem;
	}

	private void processProperty(PropertyDescriptor propDescr, Object bean, IXMLElement beanElem)
		throws XMLException
	{
		final Method getter = propDescr.getReadMethod();
		if (getter != null)
		{
			try
			{
				final String propName = propDescr.getName();
				Class returnType = getter.getReturnType();
				if (returnType.isArray())
				{
					final boolean isStringArray = returnType.getName().equals("[Ljava.lang.String;");
					Object[] props = (Object[]) getter.invoke(bean, (Object[]) null);
					if (props != null)
					{
						IXMLElement indexElem = new XMLElement(propName);
						indexElem.setAttribute(XMLConstants.INDEXED, "true");
						beanElem.addChild(indexElem);
						for (int i = 0; i < props.length; ++i)
						{
							if (isStringArray)
							{
								StringWrapper sw = new StringWrapper((String) props[i]);
								indexElem.addChild(createElement(sw, XMLConstants.BEAN_ELEMENT_NAME));
							}
							else
							{
								indexElem.addChild(createElement(props[i], XMLConstants.BEAN_ELEMENT_NAME));
							}
						}
					}
				}
				else if (returnType == boolean.class || returnType == int.class || returnType == short.class
					|| returnType == long.class || returnType == float.class || returnType == double.class
					|| returnType == char.class)
				{
					IXMLElement propElem = new XMLElement(propName);
					propElem.setContent("" + getter.invoke(bean, (Object[]) null));
					beanElem.addChild(propElem);
				}
				else if (returnType == String.class)
				{
					IXMLElement propElem = new XMLElement(propName);
					propElem.setContent((String) getter.invoke(bean, (Object[]) null));
					beanElem.addChild(propElem);
				}
				else
				{
					beanElem.addChild(createElement(getter.invoke(bean, (Object[]) null), propName));
				}
			}
			catch (Exception ex)
			{
				throw new XMLException(ex);
			}
		}
	}
}
