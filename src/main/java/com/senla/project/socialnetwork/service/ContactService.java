package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Contact;

import java.util.List;

public interface ContactService {

    Contact add(Contact contact);

    List<Contact> findAll();

    Contact findById(Long id);

    Contact update(Long id, Contact contact);

    void delete(Long id);

}
