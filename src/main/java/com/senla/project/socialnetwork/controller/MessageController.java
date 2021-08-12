package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Message;
import com.senla.project.socialnetwork.service.MessageService;
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
public class MessageController {

    private final MessageService messageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @GetMapping("/messages")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<Message> getAllMessage() {
        LOGGER.debug("Entering findAll messages endpoint");
        return messageService.findAll();
    }

    @GetMapping("/messages/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public Message getById(@PathVariable("id") Long id) {
        LOGGER.debug("Entering getById message endpoint");
        return messageService.findById(id);
    }

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public Message addMessage(@RequestBody Message message) {
        LOGGER.debug("Entering addMessage endpoint");
        return messageService.add(message);
    }

    @PutMapping("/messages/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateMessage(@PathVariable("id") Long id, @RequestBody Message message) {
        LOGGER.debug("Entering updateMessage endpoint");
        messageService.update(id, message);
    }

    @DeleteMapping("/messages/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteMessage(@PathVariable("id") Long id) {
        LOGGER.debug("Entering deleteMessage endpoint");
        messageService.delete(id);
    }
}
