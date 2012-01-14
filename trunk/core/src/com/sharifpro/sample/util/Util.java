package com.sharifpro.sample.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sharifpro.sample.model.Contact;
import com.sharifpro.util.json.EURBObjectMapper;

/**
 * Util class. Contains some common methods that can be used
 * for any class
 * 
 * @author Mohammad Dashti (m_dashti [at] ce.sharif.edu)
 */
@Component
public class Util {
	/**
	 * Get list of Contacts from request.
	 * @param data - json data from request 
	 * @return list of Contacts
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public List<Contact> getContactsFromRequest(Object data) throws JsonParseException, JsonMappingException, IOException{

		List<Contact> list;

		//it is an array - have to cast to array object
		if (data.toString().indexOf('[') > -1){

			list = getListContactsFromJSON(data);

		} else { //it is only one object - cast to object/bean

			Contact contact = getContactFromJSON(data);

			list = new ArrayList<Contact>();
			list.add(contact);
		}

		return list;
	}

	/**
	 * Transform json data format into Contact object
	 * @param data - json data from request
	 * @return 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	private Contact getContactFromJSON(Object data) throws JsonParseException, JsonMappingException, IOException{
		Contact newContact = (Contact) objectMapper.readValue(data.toString(), Contact.class);
		return newContact;
	}

	/**
	 * Transform json data format into list of Contact objects
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Contact> getListContactsFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<Contact> newContacts = (List<Contact>) JSONArray.toCollection(jsonArray,Contact.class);
		return newContacts;
	}

	/**
	 * Tranform array of numbers in json data format into
	 * list of Integer
	 * @param data - json data from request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getListIdFromJSON(Object data){
		JSONArray jsonArray = JSONArray.fromObject(data);
		List<Integer> idContacts = (List<Integer>) JSONArray.toCollection(jsonArray,Integer.class);
		return idContacts;
	}
	
	@Autowired
	public void setEURBObjectMapper(EURBObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	EURBObjectMapper objectMapper;
}
