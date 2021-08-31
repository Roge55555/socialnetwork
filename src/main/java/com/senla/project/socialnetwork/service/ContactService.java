package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Contact;
import com.senla.project.socialnetwork.model.filter.ContactFilterRequest;

import java.util.List;

public interface ContactService {

    Contact add(Long mateId);

    List<Contact> findAll(ContactFilterRequest request);

    Contact findById(Long id);

    void acceptRequest(Long id);

    void updateRole(Long id, Long roleId);

    void delete(Long id);

}
