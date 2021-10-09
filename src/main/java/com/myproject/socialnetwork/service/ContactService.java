package com.myproject.socialnetwork.service;

import com.myproject.socialnetwork.entity.Contact;
import com.myproject.socialnetwork.model.filter.ContactFilterRequest;

import java.util.List;

public interface ContactService {

    Contact add(Long mateId);

    List<Contact> findAll(ContactFilterRequest request);

    Contact findById(Long id);

    void acceptRequest(Long id);

    void updateRole(Long id, Long roleId);

    void delete(Long id);

}
