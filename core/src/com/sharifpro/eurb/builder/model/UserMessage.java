package com.sharifpro.eurb.builder.model;

import java.io.Serializable;
import java.util.Date;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.util.DateUtil;

public class UserMessage extends PersistableObject implements Serializable
{
	private static final long serialVersionUID = 7156092611112201177L;

	public final static int MESSAGE_TYPE_INFO = 1;
	
	/** 
	 * This attribute maps to the column username in the user_message table.
	 */
	protected String username;

	/** 
	 * This attribute maps to the column message in the user_message table.
	 */
	protected String message;

	/** 
	 * This attribute maps to the column type in the user_message table.
	 */
	protected int type;

	/** 
	 * This attribute maps to the column show in the user_message table.
	 */
	protected boolean show;

	/** 
	 * This attribute maps to the column available_from in the user_message table.
	 */
	protected Date availableFrom;

	/**
	 * Method 'UserMessage'
	 * 
	 */
	public UserMessage()
	{
		super();
	}
	
	

	public UserMessage(String username, String message, int type, boolean show,
			Date availableFrom) {
		super();
		this.username = username;
		this.message = message;
		this.type = type;
		this.show = show;
		this.availableFrom = availableFrom;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public Date getAvailableFrom() {
		return availableFrom;
	}

	public void setAvailableFrom(Date availableFrom) {
		this.availableFrom = availableFrom;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (!super.equals(_other)) {
			return false;
		}
		
		if (!(_other instanceof UserMessage)) {
			return false;
		}
		
		final UserMessage _cast = (UserMessage) _other;
		
		if (username == null ? _cast.username != username : !username.equals( _cast.username )) {
			return false;
		}
		
		if (message == null ? _cast.message != message : !message.equals( _cast.message )) {
			return false;
		}
		
		if (type != _cast.type) {
			return false;
		}
		
		if (show != _cast.show) {
			return false;
		}
		
		if (availableFrom == null ? _cast.availableFrom != availableFrom : !availableFrom.equals( _cast.availableFrom )) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = super.hashCode();
		
		if (username != null) {
			_hashCode = 29 * _hashCode + username.hashCode();
		}
		
		if (message != null) {
			_hashCode = 29 * _hashCode + message.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return UserMessagePk
	 */
	public UserMessagePk createPk()
	{
		return new UserMessagePk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "model.UserMessage: " );
		ret.append( super.toString() );
		ret.append( ", username=" + username );
		ret.append( ", message=" + message );
		ret.append( ", type=" + type );
		ret.append( ", show=" + show );
		ret.append( ", availableFrom=" + DateUtil.convertGregorianToPersianString(availableFrom) );
		return ret.toString();
	}

}
