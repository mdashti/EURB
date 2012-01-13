package com.sharifpro.sample.dao;

import java.util.List;

import com.sharifpro.sample.model.Contact;

public interface IContactDAO {

	List<Contact> getContacts();

	void deleteContact(int id);

	Contact saveContact(Contact contact);

}