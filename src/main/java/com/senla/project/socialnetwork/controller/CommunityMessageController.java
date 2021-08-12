package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.CommunityMessage;
import com.senla.project.socialnetwork.service.CommunityMessageService;
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
public class CommunityMessageController {

    private final CommunityMessageService communityMessageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityMessageController.class);

    @GetMapping("/communityMessages")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<CommunityMessage> getAllCommunityMessages() {
        LOGGER.debug("Entering findAll community messages endpoint");
        return communityMessageService.findAll();
    }

    @GetMapping("/communityMessages/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public CommunityMessage getById(@PathVariable("id") Long id) {
        LOGGER.debug("Entering getById community message endpoint");
        return communityMessageService.findById(id);
    }

    @PostMapping("/communityMessages")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public CommunityMessage addCommunityMessage(@RequestBody CommunityMessage communityMessage) {
        LOGGER.debug("Entering addCommunityMessage endpoint");
        return communityMessageService.add(communityMessage);
    }

    @PutMapping("/communityMessages/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateCommunityMessage(@PathVariable("id") Long id, @RequestBody CommunityMessage communityMessage) {
        LOGGER.debug("Entering updateCommunityMessage endpoint");
        communityMessageService.update(id, communityMessage);
    }

    @DeleteMapping("/communityMessages/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteCommunityMessage(@PathVariable("id") Long id) {
        LOGGER.debug("Entering deleteCommunityMessage endpoint");
        communityMessageService.delete(id);
    }
}
