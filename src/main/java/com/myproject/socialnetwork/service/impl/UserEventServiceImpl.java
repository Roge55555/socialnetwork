package com.myproject.socialnetwork.service.impl;

import com.myproject.socialnetwork.Utils;
import com.myproject.socialnetwork.entity.UserEvent;
import com.myproject.socialnetwork.exeptions.NoSuchElementException;
import com.myproject.socialnetwork.repository.UserEventRepository;
import com.myproject.socialnetwork.service.UserService;
import com.myproject.socialnetwork.service.UserEventService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserEventServiceImpl implements UserEventService {

    private final UserEventRepository userEventRepository;

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventServiceImpl.class);

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = NoSuchElementException.class)
    @Override
    public UserEvent add(UserEvent userEvent) {
        userEvent.setUser(userService.findByLogin(Utils.getLogin()));

        return userEventRepository.save(userEvent);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public List<UserEvent> findAllWith(String name) {
        return userEventRepository.findByNameContainingAndUserIdOrderByDate(name, userService.findByLogin(Utils.getLogin()).getId());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            readOnly = true)
    @Override
    public UserEvent findById(Long id) {
        return userEventRepository.findByIdAndUserId(id, userService.findByLogin(Utils.getLogin()).getId()).orElseThrow(() -> {
            LOGGER.error("No your elements with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = NoSuchElementException.class)
    @Override
    public UserEvent update(Long id, UserEvent userEvent) {
        UserEvent updatedUserEvent = findById(id);
        if (Objects.nonNull(userEvent.getName())) {
            updatedUserEvent.setName(userEvent.getName());
        }
        if (Objects.nonNull(userEvent.getDescription())) {
            updatedUserEvent.setDescription(userEvent.getDescription());
        }
        if (Objects.nonNull(userEvent.getDate())) {
            updatedUserEvent.setDate(userEvent.getDate());
        }
        return userEventRepository.save(updatedUserEvent);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            noRollbackFor = NoSuchElementException.class)
    @Override
    public void delete(Long id) {
        findById(id);

        userEventRepository.deleteById(id);
    }

}
