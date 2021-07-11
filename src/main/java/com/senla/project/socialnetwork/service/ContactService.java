package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Contact;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactService {

    private ContactRepository contactRepository;

    public void add(Contact contact) {
        contactRepository.save(contact);
    }

    public List<Contact> findAll() {
        if (contactRepository.findAll().isEmpty()) {
            throw new NoAccountsException();
        }
        return contactRepository.findAll();
    }

    public Contact findById(Long id) {
        return contactRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Contact update(Long id, Contact contact) {

        return contactRepository.findById(id).map(cont -> {
            cont.setCreator(contact.getCreator());
            cont.setMate(contact.getMate());
            cont.setDateConnected(contact.getDateConnected());
            cont.setContactLevel(contact.getContactLevel());
            cont.setContactRole(contact.getContactRole());
            return contactRepository.save(cont);
        })
                .orElseThrow(NoSuchElementException::new);
    }

    public void delete(Long id) {
        if (contactRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        contactRepository.deleteById(id);
    }
}
