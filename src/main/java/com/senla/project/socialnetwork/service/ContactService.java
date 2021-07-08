package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Contact;
import com.senla.project.socialnetwork.exeptions.NoSuchIdException;
import com.senla.project.socialnetwork.repository.ContactRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void add(Contact contact){
        contactRepository.save(contact);
    }

    public List<Contact> findAll(){
        return contactRepository.findAll();
    }

    @SneakyThrows
    public Contact findById(Long id){
        return contactRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @SneakyThrows
    public Contact update(Long id, Contact contact) {

        return contactRepository.findById(id).map(cont -> {
            cont.setCreator(contact.getCreator());
            cont.setMate(contact.getMate());
            cont.setDateConnected(contact.getDateConnected());
            cont.setContactLevel(contact.getContactLevel());
            cont.setContactRole(contact.getContactRole());
            return contactRepository.save(cont);
        })
                .orElseThrow(() ->
                        new NoSuchIdException(id, "contact")
                );
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }
}
