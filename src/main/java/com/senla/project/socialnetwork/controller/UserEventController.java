package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.UserEvent;
import com.senla.project.socialnetwork.service.UserEventService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class UserEventController {
    private final UserEventService userEventService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventController.class);

    @GetMapping("/userEvents")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<UserEvent> getAllEvents() {
        LOGGER.debug("Entering findAll events endpoint");
        return userEventService.findAll();
    }

    @GetMapping("/userEvents/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public UserEvent getById(@PathVariable("id") Long id) {
        LOGGER.debug("Entering getById event endpoint");
        return userEventService.findById(id);
    }

    @PostMapping("/userEvents")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public UserEvent addEvent(@Valid @RequestBody UserEvent userEvent) {
        LOGGER.debug("Entering addEvent endpoint");
        return userEventService.add(userEvent);
    }

    @PutMapping("/userEvents/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateEvent(@PathVariable("id") Long id, @RequestBody UserEvent userEvent) {
        LOGGER.debug("Entering updateEvent endpoint");
        userEventService.update(id, userEvent);
    }

    @DeleteMapping("/userEvents/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteEvent(@PathVariable("id") Long id) {
        LOGGER.debug("Entering deleteEvent endpoint");
        userEventService.delete(id);
    }
}
