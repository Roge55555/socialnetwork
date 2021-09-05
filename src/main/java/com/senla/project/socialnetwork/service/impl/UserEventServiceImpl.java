package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.UserEvent;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.UserEventRepository;
import com.senla.project.socialnetwork.service.UserEventService;
import com.senla.project.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserEventServiceImpl implements UserEventService {

    private final UserEventRepository userEventRepository;

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventServiceImpl.class);

    @Override
    public UserEvent add(UserEvent userEvent) {
        userEvent.setUser(userService.findByLogin(Utils.getLogin()));

        return userEvent;
    }

    @Override
    public List<UserEvent> findAllWith(String name) {
        return userEventRepository.findByNameContainingAndUserIdOrderByDate(name, userService.findByLogin(Utils.getLogin()).getId());
    }

    @Override
    public UserEvent findById(Long id) {
        return userEventRepository.findByIdAndUserId(id, userService.findByLogin(Utils.getLogin()).getId()).orElseThrow(() -> {
            LOGGER.error("No your elements with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
    }

    @Override
    public UserEvent update(Long id, UserEvent userEvent) {
        UserEvent updatedUserEvent = findById(id);
        if(Objects.nonNull(userEvent.getName())) {
            updatedUserEvent.setName(userEvent.getName());
        }
        if(Objects.nonNull(userEvent.getDescription())) {
            updatedUserEvent.setDescription(userEvent.getDescription());
        }
        if(Objects.nonNull(userEvent.getDate())) {
            updatedUserEvent.setDate(userEvent.getDate());
        }
            return userEventRepository.save(updatedUserEvent);
    }

    @Override
    public void delete(Long id) {
        findById(id);

        userEventRepository.deleteById(id);
    }

}
