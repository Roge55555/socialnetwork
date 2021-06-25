package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Contact;
import com.senla.project.socialnetwork.service.ContactService;
import org.springframework.http.HttpStatus;
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
    public List<Contact> getAllEmployees(){
        return contactService.findAll();
    }

    @GetMapping("/contacts/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Contact getById(@PathVariable("id") Long id) {
        return contactService.findById(id);
    }

    @PostMapping("/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@RequestBody Contact contact) {
        contactService.add(contact);
    }

    @PutMapping("/contacts/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateContact(@PathVariable("id") Long id, @RequestBody Contact contact) {
        contactService.update(id, contact);
    }

    @DeleteMapping("/contacts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("id") Long id) {
        contactService.delete(id);
    }
}
