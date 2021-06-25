package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.CommunityMessage;
import com.senla.project.socialnetwork.service.CommunityMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CommunityMessageController {
    private final CommunityMessageService communityMessageService;

    public CommunityMessageController(CommunityMessageService communityMessageService) {
        this.communityMessageService = communityMessageService;
    }

    @GetMapping("/communityMessages")
    public List<CommunityMessage> getAllEmployees(){
        return communityMessageService.findAll();
    }

    @GetMapping("/communityMessages/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public CommunityMessage getById(@PathVariable("id") Long id) {
        return communityMessageService.findById(id);
    }

    @PostMapping("/communityMessages")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@RequestBody CommunityMessage communityMessage) {
        communityMessageService.add(communityMessage);
    }

    @PutMapping("/communityMessages/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateContact(@PathVariable("id") Long id, @RequestBody CommunityMessage communityMessage) {
        communityMessageService.update(id, communityMessage);
    }

    @DeleteMapping("/communityMessages/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("id") Long id) {
        communityMessageService.delete(id);
    }
}
