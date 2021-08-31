package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Message;
import com.senla.project.socialnetwork.model.TimeInterval;
import com.senla.project.socialnetwork.model.dto.MessageDTO;
import com.senla.project.socialnetwork.model.filter.MessageFilterRequest;
import com.senla.project.socialnetwork.repository.specification.MessageSpecification;
import com.senla.project.socialnetwork.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Time;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
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

import java.time.LocalDateTime;
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
        return messageService.add(messageDTO.getUserLogin(), messageDTO.getTxt());
    }

//    @GetMapping("/dialog/{login}")
//    @PreAuthorize("hasAuthority('standard:permission')")
//    public List<Message> getMessagesByLogin(@PathVariable("login") String userLogin) {
//        return messageService.findAllMessagesWith(userLogin);
//    }
//
//    @GetMapping("/dialogTimeInterval/{login}")
//    @PreAuthorize("hasAuthority('standard:permission')")
//    public List<Message> getMessagesByLoginTimeInterval(@PathVariable("login") String userLogin, @RequestBody TimeInterval<LocalDateTime> timeInterval) {
//        return messageService.findAllMessagesWithBetween(userLogin, timeInterval.getFrom(), timeInterval.getTo());
//    }

    @GetMapping("/dialog")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<Message> getM(@RequestBody MessageFilterRequest request) {
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
