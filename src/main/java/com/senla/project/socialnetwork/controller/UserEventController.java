package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.UserEvent;
import com.senla.project.socialnetwork.model.dto.UserEventDTO;
import com.senla.project.socialnetwork.service.UserEventService;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userEvents")
public class UserEventController {

    private final UserEventService userEventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public UserEvent addEvent(@RequestBody UserEventDTO userEventDTO) {
        UserEvent userEvent = UserEvent.builder()
                .name(userEventDTO.getName())
                .description(userEventDTO.getDescription())
                .date(userEventDTO.getDate())
                .build();
        return userEventService.add(userEvent);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<UserEvent> getAllEvents(@RequestBody String name) {
        return userEventService.findAllWith(name);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public UserEvent getById(@PathVariable("id") Long id) {
        return userEventService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateEvent(@PathVariable("id") Long id, @RequestBody UserEventDTO userEventDTO) {
        UserEvent userEvent = UserEvent.builder()
                .name(userEventDTO.getName())
                .description(userEventDTO.getDescription())
                .date(userEventDTO.getDate())
                .build();
        userEventService.update(id, userEvent);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteEvent(@PathVariable("id") Long id) {
        userEventService.delete(id);
    }

}
