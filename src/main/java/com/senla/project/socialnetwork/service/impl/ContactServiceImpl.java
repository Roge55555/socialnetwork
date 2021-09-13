package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.Contact;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.senla.project.socialnetwork.exeptions.TryingRequestToYourselfException;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final RoleListService roleListService;

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingRequestToYourselfException.class})
    @Override
    public Contact add(Long mateId) {
        if (Utils.getLogin().equals(userService.findById(mateId).getLogin())) {
            LOGGER.error("Trying contact to yourself!");
            throw new TryingRequestToYourselfException();
        }

        return contactRepository.save(Contact.builder()
                .creator(userService.findByLogin(Utils.getLogin()))
                .mate(userService.findById(mateId))
                .dateConnected(LocalDate.now())
                .contactLevel(false)
                .creatorRole(null)
                .mateRole(null)
                .build());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public List<Contact> findAll(ContactFilterRequest request) {
        return contactRepository.findAll(ContactSpecification.getSpecification(request));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public Contact findById(Long id) {
        if (!Utils.getLogin().equals(contactRepository.findById(id).get().getCreator().getLogin()) &&
                !Utils.getLogin().equals(contactRepository.findById(id).get().getMate().getLogin())) {
            LOGGER.error("No element with such id - {}.", id);
            throw new TryingModifyNotYourDataException("Not your contact.");
        }
        return contactRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
    @Override
    public void acceptRequest(Long id) {
        if (!findById(id).getMate().getLogin().equals(Utils.getLogin())) {
            LOGGER.error("Trying accept not his request with id - {}.", id);
             throw new TryingModifyNotYourDataException("Only mate can accept request!");
        }

        Contact contact = findById(id);
            contact.setDateConnected(LocalDate.now());
            contact.setContactLevel(true);
            contactRepository.save(contact);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
    @Override
    public void updateRole(Long id, Long roleId) {
        if (!findById(id).getContactLevel()) {
            LOGGER.error("No element with such id - {}.", id);
            throw new TryingModifyNotYourDataException("You can set role only after mate accept request.");
        }

        if (Utils.getLogin().equals(findById(id).getCreator().getLogin())) {
            Contact contact = findById(id);
                contact.setDateConnected(LocalDate.now());
                contact.setMateRole(roleListService.findById(roleId));
                contactRepository.save(contact);

        } else if (Utils.getLogin().equals(findById(id).getMate().getLogin())) {
            Contact contact = findById(id);
                contact.setDateConnected(LocalDate.now());
                contact.setCreatorRole(roleListService.findById(roleId));
                contactRepository.save(contact);
        } else {
            LOGGER.error("Trying update not your contact id - {}.", id);
            throw new TryingModifyNotYourDataException("Trying update not your contact.");
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = {NoSuchElementException.class, TryingModifyNotYourDataException.class})
    @Override
    public void delete(Long id) {
        if (Utils.getLogin().equals(findById(id).getCreator().getLogin())) {
            contactRepository.deleteById(id);
        } else if (Utils.getLogin().equals(findById(id).getMate().getLogin())) {
            Contact contact = findById(id);
                contact.setDateConnected(LocalDate.now());
                contact.setContactLevel(false);
                contact.setCreatorRole(null);
                contact.setMateRole(null);
                contactRepository.save(contact);
        } else {
            LOGGER.error("Trying delete not your contact id - {}.", id);
            throw new TryingModifyNotYourDataException("Trying delete not your contact.");
        }
    }

}
