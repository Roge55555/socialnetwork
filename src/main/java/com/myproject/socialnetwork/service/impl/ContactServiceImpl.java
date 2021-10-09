package com.myproject.socialnetwork.service.impl;

import com.myproject.socialnetwork.Utils;
import com.myproject.socialnetwork.entity.Contact;
import com.myproject.socialnetwork.exeptions.NoSuchElementException;
import com.myproject.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.myproject.socialnetwork.exeptions.TryingRequestToYourselfException;
import com.myproject.socialnetwork.model.filter.ContactFilterRequest;
import com.myproject.socialnetwork.repository.ContactRepository;
import com.myproject.socialnetwork.repository.specification.ContactSpecification;
import com.myproject.socialnetwork.service.UserService;
import com.myproject.socialnetwork.service.ContactService;
import com.myproject.socialnetwork.service.RoleListService;
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
        final Contact contact = contactRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });

        if (!Utils.getLogin().equals(contact.getCreator().getLogin()) &&
                !Utils.getLogin().equals(contact.getMate().getLogin())) {
            LOGGER.error("You can see only your contacts.");
            throw new TryingModifyNotYourDataException("Not your contact.");
        }
        return contact;
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
        findById(id);

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
        }
    }

}
