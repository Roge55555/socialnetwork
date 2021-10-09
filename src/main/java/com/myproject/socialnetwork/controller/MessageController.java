package com.myproject.socialnetwork.controller;

import com.myproject.socialnetwork.service.MessageService;
import com.myproject.socialnetwork.entity.Message;
import com.myproject.socialnetwork.model.dto.MessageDTO;
import com.myproject.socialnetwork.model.filter.MessageFilterRequest;
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
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public Message addMessage(@RequestBody MessageDTO messageDTO) {
        return messageService.add(messageDTO.getUserId(), messageDTO.getTxt());
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<Message> getMessage(@RequestBody MessageFilterRequest request) {
        return messageService.findAll(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateMessage(@PathVariable("id") Long id, @RequestBody String txt) {
        messageService.update(id, txt);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteMessage(@PathVariable("id") Long id) {
        messageService.delete(id);
    }

}
