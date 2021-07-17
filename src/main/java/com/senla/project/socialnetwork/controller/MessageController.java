package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Message;
import com.senla.project.socialnetwork.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/messages")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<Message> getAllMessage(){
        return messageService.findAll();
    }

    @GetMapping("/messages/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public Message getById(@PathVariable("id") Long id) {
        return messageService.findById(id);
    }

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public Message addMessage(@RequestBody Message message) {
        return messageService.add(message);
    }

    @PutMapping("/messages/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateMessage(@PathVariable("id") Long id, @RequestBody Message message) {
        messageService.update(id, message);
    }

    @DeleteMapping("/messages/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteMessage(@PathVariable("id") Long id) {
        messageService.delete(id);
    }
}
