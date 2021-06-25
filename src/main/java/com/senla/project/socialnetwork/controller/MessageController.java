package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Message;
import com.senla.project.socialnetwork.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public List<Message> getAllEmployees(){
        return messageService.findAll();
    }

    @GetMapping("/messages/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Message getById(@PathVariable("id") Long id) {
        return messageService.findById(id);
    }

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@RequestBody Message message) {
        messageService.add(message);
    }

    @PutMapping("/messages/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateContact(@PathVariable("id") Long id, @RequestBody Message message) {
        messageService.update(id, message);
    }

    @DeleteMapping("/messages/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("id") Long id) {
        messageService.delete(id);
    }
}
