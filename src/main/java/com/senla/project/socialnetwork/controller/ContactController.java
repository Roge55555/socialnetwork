package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Contact;
import com.senla.project.socialnetwork.model.filter.ContactFilterRequest;
import com.senla.project.socialnetwork.service.ContactService;
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
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public Contact addContact(@RequestBody Long mateId) {
        return contactService.add(mateId);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<Contact> getAllContacts(@RequestBody ContactFilterRequest request) {
        return contactService.findAll(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public Contact getById(@PathVariable("id") Long id) {
        return contactService.findById(id);
    }

    @PutMapping("/accept/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void acceptContact(@PathVariable("id") Long id) {
        contactService.acceptRequest(id);
    }

    @PutMapping("/changeRole/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void changeContactRole(@PathVariable("id") Long id, @RequestBody Long roleId) {
        contactService.updateRole(id, roleId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteContact(@PathVariable("id") Long id) {
        contactService.delete(id);
    }

}
