package com.sharifpro.eurb.builder.util;

import java.io.*;

import javax.activation.DataSource;

public class ByteArrayDataSource implements DataSource
{
	private byte[] data;
	private String type;
	private String name;

	public ByteArrayDataSource(byte[] data, String type)
	{
		this.type = type;
		this.data = data;
	}

	public InputStream getInputStream() throws IOException
	{
		if (data == null)
			throw new IOException("No data.");

		return new ByteArrayInputStream(data);
	}

	public OutputStream getOutputStream() throws IOException
	{
		throw new IOException("Not supported.");
	}

	public String getContentType()
	{
		return type;
	}

	public void setContentType(String type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
