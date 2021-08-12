package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Contact;
import com.senla.project.socialnetwork.service.ContactService;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class ContactController {

    private final ContactService contactService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    @GetMapping("/contacts")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<Contact> getAllContacts() {
        LOGGER.debug("Entering findAll contacts endpoint");
        return contactService.findAll();
    }

    @GetMapping("/contacts/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public Contact getById(@PathVariable("id") Long id) {
        LOGGER.debug("Entering getById contact endpoint");
        return contactService.findById(id);
    }

    @PostMapping("/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public Contact addContact(@RequestBody Contact contact) {
        LOGGER.debug("Entering addContact endpoint");
        return contactService.add(contact);
    }

    @PutMapping("/contacts/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateContact(@PathVariable("id") Long id, @RequestBody Contact contact) {
        LOGGER.debug("Entering updateContact endpoint");
        contactService.update(id, contact);
    }

    @DeleteMapping("/contacts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteContact(@PathVariable("id") Long id) {
        LOGGER.debug("Entering deleteContact endpoint");
        contactService.delete(id);
    }
}
