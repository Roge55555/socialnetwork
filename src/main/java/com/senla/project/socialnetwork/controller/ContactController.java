package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Contact;
import com.senla.project.socialnetwork.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<Contact> getAllEmployees(){
        return contactService.findAll();
    }

    @GetMapping("/contacts/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public Contact getById(@PathVariable("id") Long id) {
        return contactService.findById(id);
    }

    @PostMapping("/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void addContact(@RequestBody Contact contact) {
        contactService.add(contact);
    }

    @PutMapping("/contacts/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateContact(@PathVariable("id") Long id, @RequestBody Contact contact) {
        contactService.update(id, contact);
    }

    @DeleteMapping("/contacts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteContact(@PathVariable("id") Long id) {
        contactService.delete(id);
    }
}
