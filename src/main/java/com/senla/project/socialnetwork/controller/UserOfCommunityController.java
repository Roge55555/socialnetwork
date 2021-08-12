package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.UserOfCommunity;
import com.senla.project.socialnetwork.service.UserOfCommunityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserOfCommunityController {

    private final UserOfCommunityService userOfCommunityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserOfCommunityController.class);

    @GetMapping("/userOfCommunities")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<UserOfCommunity> getAllUserOfCommunities() {
        LOGGER.debug("Entering findAll subscribers endpoint");
        return userOfCommunityService.findAll();
    }

    @GetMapping("/userOfCommunities/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public UserOfCommunity getById(@PathVariable("id") Long id) {
        LOGGER.debug("Entering getById subscriber endpoint");
        return userOfCommunityService.findById(id);
    }

    @PostMapping("/userOfCommunities")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public UserOfCommunity addUserOfCommunity(@RequestBody UserOfCommunity userOfCommunity) {
        LOGGER.debug("Entering addUserOfCommunity endpoint");
        return userOfCommunityService.add(userOfCommunity);
    }

    @DeleteMapping("/userOfCommunities/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void deleteUserOfCommunity(@PathVariable("id") Long id) {
        LOGGER.debug("Entering deleteUserOfCommunity endpoint");
        userOfCommunityService.delete(id);
    }
}
