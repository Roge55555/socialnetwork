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
            cont.setCreator_id(contact.getCreator_id());
            cont.setMate_id(contact.getMate_id());
            cont.setDate_connected(contact.getDate_connected());
            cont.setContact_level(contact.getContact_level());
            cont.setContact_role(contact.getContact_role());
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
