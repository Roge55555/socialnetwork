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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserEventServiceImpl implements UserEventService {

    private final UserEventRepository userEventRepository;

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventServiceImpl.class);

    @Override
    public UserEvent add(String name, String description, LocalDateTime dateTime) {
        LOGGER.info("Trying to add event.");

        UserEvent userEvent = UserEvent.builder()
                .user(userService.findByLogin(Utils.getLogin()))
                .name(name)
                .description(description)
                .date(dateTime)
                .build();
        UserEvent save = userEventRepository.save(userEvent);
        LOGGER.info("Event added.");
        return save;
    }

    @Override
    public List<UserEvent> findAllWith(String name) {
        return userEventRepository.findByNameContainingAndUserIdOrderByDate(name, userService.findByLogin(Utils.getLogin()).getId());
    }

    @Override
    public UserEvent findById(Long id) {
        LOGGER.info("Trying to find event by id");
        final UserEvent userEvent = userEventRepository.findByIdAndUserId(id, userService.findByLogin(Utils.getLogin()).getId()).orElseThrow(() -> {
            LOGGER.error("No your elements with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
        LOGGER.info("Event found using id {}", userEvent.getId());
        return userEvent;
    }

    @Override
    public UserEvent update(Long id, String name, String description, LocalDateTime dateTime) {
        LOGGER.info("Trying to update event with id - {}.", id);

        return userEventRepository.findById(id).map(ue -> {
            ue.setName(name);
            ue.setDescription(description);
            ue.setDate(dateTime);
            UserEvent save = userEventRepository.save(ue);
            LOGGER.info("Event with id {} updated.", id);
            return save;
        })
                .orElseThrow(() -> {
                    LOGGER.error("No element with such id - {}.", id);
                    throw new NoSuchElementException(id);
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
