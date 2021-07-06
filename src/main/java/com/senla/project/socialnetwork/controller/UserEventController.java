package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.UserEvent;
import com.senla.project.socialnetwork.service.UserEventService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserEventController {
    private final UserEventService userEventService;

    public UserEventController(UserEventService userEventService) {
        this.userEventService = userEventService;
    }

    @GetMapping("/userEvents")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<UserEvent> getAllEmployees(){
        return userEventService.findAll();
    }

    @GetMapping("/userEvents/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public UserEvent getById(@PathVariable("id") Long id) {
        return userEventService.findById(id);
    }

    @PostMapping("/userEvents")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void addContact(@Valid @RequestBody UserEvent userEvent) {
        userEventService.add(userEvent);
    }

    @PutMapping("/userEvents/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateContact(@PathVariable("id") Long id, @RequestBody UserEvent userEvent) {
        userEventService.update(id, userEvent);
    }

    @DeleteMapping("/userEvents/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteContact(@PathVariable("id") Long id) {
        userEventService.delete(id);
    }
}
