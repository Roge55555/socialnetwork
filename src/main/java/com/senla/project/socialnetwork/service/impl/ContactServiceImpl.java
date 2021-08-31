package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.Contact;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.senla.project.socialnetwork.model.filter.ContactFilterRequest;
import com.senla.project.socialnetwork.repository.ContactRepository;
import com.senla.project.socialnetwork.repository.specification.ContactSpecification;
import com.senla.project.socialnetwork.service.ContactService;
import com.senla.project.socialnetwork.service.RoleListService;
import com.senla.project.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final RoleListService roleListService;

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Override
    public Contact add(Long mateId) {
        LOGGER.info("Trying to add contact.");

        Contact contact = Contact.builder()
                .creator(userService.findByLogin(Utils.getLogin()))
                .mate(userService.findById(mateId))
                .dateConnected(LocalDate.now())
                .contactLevel(false)
                .creatorRole(null)
                .mateRole(null)
                .build();

        final Contact save = contactRepository.save(contact);
        LOGGER.info("Contact added.");
        return save;
    }

    @Override
    public List<Contact> findAll(ContactFilterRequest request) {
        return contactRepository.findAll(ContactSpecification.getSpecification(request));
    }

    @Override
    public Contact findById(Long id) {
        if (!contactRepository.findById(id).get().getCreator().getLogin().equals(Utils.getLogin()) ||
                !contactRepository.findById(id).get().getMate().getLogin().equals(Utils.getLogin())) {
            throw new TryingModifyNotYourDataException("Not your contact.");
        }
        LOGGER.info("Trying to find contact by id");
        final Contact contact = contactRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
        LOGGER.info("Contact found using id {}", contact.getId());
        return contact;
    }

    @Override
    public void acceptRequest(Long id) {
        LOGGER.info("Trying to update contact with id - {}.", id);

        if (!findById(id).getMate().getLogin().equals(Utils.getLogin())) {
            throw new TryingModifyNotYourDataException("Only mate can accept request!");
        }

        contactRepository.findById(id).map(cont -> {
            cont.setDateConnected(LocalDate.now());
            cont.setContactLevel(true);
            final Contact save = contactRepository.save(cont);
            LOGGER.info("Contact with id {} updated.", id);
            return save;
        })
                .orElseThrow(() -> {
                    LOGGER.error("No element with such id - {}.", id);
                    throw new NoSuchElementException(id);
                });
    }

    @Override
    public void updateRole(Long id, Long roleId) {
        LOGGER.info("Trying to update contact with id - {}.", id);

        if (!findById(id).getContactLevel()) {
            throw new TryingModifyNotYourDataException("You can set role only after mate accept request.");
        }

        if (findById(id).getCreator().getLogin().equals(Utils.getLogin())) {
            contactRepository.findById(id).map(cont -> {
                cont.setDateConnected(LocalDate.now());
                cont.setMateRole(roleListService.findById(roleId));
                final Contact save = contactRepository.save(cont);
                LOGGER.info("Contact with id {} updated.", id);
                return save;
            })
                    .orElseThrow(() -> {
                        LOGGER.error("No element with such id - {}.", id);
                        throw new NoSuchElementException(id);
                    });
        } else {
            contactRepository.findById(id).map(cont -> {
                cont.setDateConnected(LocalDate.now());
                cont.setCreatorRole(roleListService.findById(roleId));
                final Contact save = contactRepository.save(cont);
                LOGGER.info("Contact with id {} updated.", id);
                return save;
            })
                    .orElseThrow(() -> {
                        LOGGER.error("No element with such id - {}.", id);
                        throw new NoSuchElementException(id);
                    });
        }
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Trying to delete contact with id - {}.", id);
        if (findById(id).getCreator().getLogin().equals(Utils.getLogin())) {
            contactRepository.deleteById(id);
            LOGGER.info("Contact with id - {} was deleted.", id);
        } else {
            contactRepository.findById(id).map(cont -> {
                cont.setDateConnected(LocalDate.now());
                cont.setContactLevel(false);
                cont.setCreatorRole(null);
                cont.setMateRole(null);
                return contactRepository.save(cont);
            });
            LOGGER.info("Contact with id - {} was level downed.", id);
        }

    }

}
