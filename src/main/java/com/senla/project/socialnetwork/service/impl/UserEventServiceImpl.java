package com.senla.project.socialnetwork.service.impl;

import com.senla.project.socialnetwork.entity.UserEvent;
import com.senla.project.socialnetwork.exeptions.NoSuchElementException;
import com.senla.project.socialnetwork.repository.UserEventRepository;
import com.senla.project.socialnetwork.repository.UserRepository;
import com.senla.project.socialnetwork.service.UserEventService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserEventServiceImpl implements UserEventService {

    private final UserEventRepository userEventRepository;

    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventServiceImpl.class);

    @Override
    public UserEvent add(UserEvent userEvent) {
        LOGGER.info("Trying to add event.");

        if (userRepository.findById(userEvent.getUser().getId()).isEmpty()) {
            LOGGER.error("User doesn`t exist");
            throw new NoSuchElementException(userEvent.getUser().getId());
        }
        userEvent.setId(null);
        UserEvent save = userEventRepository.save(userEvent);
        LOGGER.info("Event added.");
        return save;
    }

    @Override
    public List<UserEvent> findAll() {
        LOGGER.info("Trying to show all events.");
        if (userEventRepository.findAll().isEmpty()) {
            LOGGER.warn("Event`s list is empty!");
        } else {
            LOGGER.info("Event(s) found.");
        }
        return userEventRepository.findAll();
    }

    @Override
    public UserEvent findById(Long id) {
        LOGGER.info("Trying to find event by id");
        final UserEvent userEvent = userEventRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("No element with such id - {}.", id);
            return new NoSuchElementException(id);
        });
        LOGGER.info("Event found using id {}", userEvent.getId());
        return userEvent;
    }

    @Override
    public UserEvent update(Long id, UserEvent userEvent) {
        LOGGER.info("Trying to update event with id - {}.", id);
        if (userRepository.findById(userEvent.getUser().getId()).isEmpty()) {
            LOGGER.error("User doesn`t exist");
            throw new NoSuchElementException(userEvent.getUser().getId());
        }


        return userEventRepository.findById(id).map(ue -> {
            ue.setUser(userEvent.getUser());
            ue.setName(userEvent.getName());
            ue.setDescription(userEvent.getDescription());
            ue.setDate(userEvent.getDate());
            UserEvent save = userEventRepository.save(ue);
            LOGGER.info("Event with id {} updated.", id);
            return save;
        })
                .orElseThrow(() -> {
                    LOGGER.error("No element with such id - {}.", id);
                    return new NoSuchElementException(id);
                });
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Trying to delete event with id - {}.", id);
        if (userEventRepository.findById(id).isEmpty()) {
            LOGGER.error("No event with id - {}.", id);
            throw new NoSuchElementException(id);
        }
        userEventRepository.deleteById(id);
        LOGGER.info("Event with id - {} was deleted.", id);
    }

}
