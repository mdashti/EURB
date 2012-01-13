package com.sharifpro.sample.model;


import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sharifpro.util.json.JsonDateDeserializer;
import com.sharifpro.util.json.JsonDateSerializer;

/**
 * Contact POJO
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@JsonAutoDetect
public class Contact {

	private int id;
	private String name;
	private String phone;
	private String email;
	private Date birthday;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getBirthday() {
		return birthday;
	}

	@JsonDeserialize(using=JsonDateDeserializer.class)
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}