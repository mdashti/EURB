package com.sharifpro.eurb.builder.delivery;

import com.sharifpro.eurb.management.mapping.exception.PersistableObjectDaoException;

public class DeliveryException extends PersistableObjectDaoException
{		
    private static final long serialVersionUID = 447839620836583970L;

    public DeliveryException(String message)
	{
		super(message);
	}

	public DeliveryException(Exception exception)
	{
		super("", exception);
    }	
}