package com.senla.project.socialnetwork.service;

import com.senla.project.socialnetwork.entity.Contact;
import com.senla.project.socialnetwork.exeptions.NoAccountsException;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingRequestToYourselfException;
import com.senla.project.socialnetwork.repository.ContactRepository;
import com.senla.project.socialnetwork.repository.RoleListRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    private final RoleListRepository roleListRepository;

    private final UserRepository userRepository;

    public Contact add(Contact contact) {
        if (userRepository.findById(contact.getCreator().getId()).isEmpty() ||
                userRepository.findById(contact.getMate().getId()).isEmpty() ||
                roleListRepository.findById(contact.getContactRole().getId()).isEmpty()) {
            throw new NoSuchElementException();
        } else if (contact.getCreator().getId().equals(contact.getMate().getId())) {
            throw new TryingRequestToYourselfException();
        }
        return contactRepository.save(contact);
    }

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    public Contact findById(Long id) {
        return contactRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Contact update(Long id, Contact contact) {

        if (userRepository.findById(contact.getCreator().getId()).isEmpty() ||
                userRepository.findById(contact.getMate().getId()).isEmpty() ||
                roleListRepository.findById(contact.getContactRole().getId()).isEmpty()) {
            throw new NoSuchElementException();
        } else if (contact.getCreator().getId().equals(contact.getMate().getId())) {
            throw new TryingRequestToYourselfException();
        }

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
