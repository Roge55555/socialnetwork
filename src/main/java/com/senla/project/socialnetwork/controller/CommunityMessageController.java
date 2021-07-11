package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.CommunityMessage;
import com.senla.project.socialnetwork.service.CommunityMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class CommunityMessageController {
    private final CommunityMessageService communityMessageService;

    @GetMapping("/communityMessages")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<CommunityMessage> getAllEmployees(){
        return communityMessageService.findAll();
    }

    @GetMapping("/communityMessages/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public CommunityMessage getById(@PathVariable("id") Long id) {
        return communityMessageService.findById(id);
    }

    @PostMapping("/communityMessages")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void addContact(@RequestBody CommunityMessage communityMessage) {
        communityMessageService.add(communityMessage);
    }

    @PutMapping("/communityMessages/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateContact(@PathVariable("id") Long id, @RequestBody CommunityMessage communityMessage) {
        communityMessageService.update(id, communityMessage);
    }

    @DeleteMapping("/communityMessages/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteContact(@PathVariable("id") Long id) {
        communityMessageService.delete(id);
    }
}
