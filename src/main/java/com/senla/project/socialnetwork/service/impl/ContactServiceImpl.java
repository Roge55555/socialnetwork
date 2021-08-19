package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.entity.Contact;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingRequestToYourselfException;
import com.senla.project.socialnetwork.repository.ContactRepository;
import com.senla.project.socialnetwork.repository.RoleListRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import com.senla.project.socialnetwork.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final RoleListRepository roleListRepository;

    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Override
    public Contact add(Contact contact) {
        LOGGER.info("Trying to add contact.");

        if (userRepository.findById(contact.getCreator().getId()).isEmpty() ||
                userRepository.findById(contact.getMate().getId()).isEmpty() ||
                roleListRepository.findById(contact.getContactRole().getId()).isEmpty()) {
            LOGGER.error("Creator/Mate/Role do(es)n`t exist");
            throw new NoSuchElementException();
        } else if (contact.getCreator().getId().equals(contact.getMate().getId())) {
            LOGGER.error("User {} trying contact himself", contact.getCreator());
            throw new TryingRequestToYourselfException();
        }
        contact.setId(null);
        final Contact save = contactRepository.save(contact);
        LOGGER.info("Contact added.");
        return save;
    }

    @Override
    public List<Contact> findAll() {
        LOGGER.info("Trying to show all contacts.");
        if (contactRepository.findAll().isEmpty()) {
            LOGGER.warn("Contact`s list is empty!");
        } else {
            LOGGER.info("Contact(s) found.");
        }
        return contactRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        LOGGER.info("Trying to find contact by id");
        final Contact contact = contactRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            return new NoSuchElementException(id);
        });
        LOGGER.info("Blocklist found using id {}", contact.getId());
        return contact;
    }

    @Override
    public Contact update(Long id, Contact contact) {
        LOGGER.info("Trying to update contact with id - {}.", id);
        if (userRepository.findById(contact.getCreator().getId()).isEmpty() ||
                userRepository.findById(contact.getMate().getId()).isEmpty() ||
                roleListRepository.findById(contact.getContactRole().getId()).isEmpty()) {
            LOGGER.error("Creator/Mate/Role do(es)n`t exist");
            throw new NoSuchElementException();
        } else if (contact.getCreator().getId().equals(contact.getMate().getId())) {
            LOGGER.error("User {} trying contact himself", contact.getCreator());
            throw new TryingRequestToYourselfException();
        }

        return contactRepository.findById(id).map(cont -> {
            cont.setCreator(contact.getCreator());
            cont.setMate(contact.getMate());
            cont.setDateConnected(contact.getDateConnected());
            cont.setContactLevel(contact.getContactLevel());
            cont.setContactRole(contact.getContactRole());
            final Contact save = contactRepository.save(cont);
            LOGGER.info("Contact with id {} updated.", id);
            return save;
        })
                .orElseThrow(() -> {
                    LOGGER.error("No element with such id - {}.", id);
                    return new NoSuchElementException(id);
                });
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Trying to delete contact with id - {}.", id);
        if (contactRepository.findById(id).isEmpty()) {
            LOGGER.error("No contact with id - {}.", id);
            throw new NoSuchElementException(id);
        }
        contactRepository.deleteById(id);
        LOGGER.info("Contact with id - {} was deleted.", id);
    }

}
