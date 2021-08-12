package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class CommunityController {

    private final CommunityService communityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityController.class);

    @GetMapping("/communities")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<Community> getAllCommunities() {
        LOGGER.debug("Entering findAll communities endpoint");
        return communityService.findAll();
    }

    @GetMapping("/communitiesPage")
    @PreAuthorize("hasAuthority('standard:permission')")
    public Page<Community> getAllCommunities(Pageable pageable) {
        LOGGER.debug("Entering findAll communities in pages endpoint");
        return communityService.findAll(pageable);
    }

    @GetMapping("/communities/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public Community getById(@PathVariable("id") Long id) {
        LOGGER.debug("Entering getById community endpoint");
        return communityService.findById(id);
    }

    @PostMapping("/communities")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public Community addCommunity(@Valid @RequestBody Community community) {
        LOGGER.debug("Entering addCommunity endpoint");
        return communityService.add(community);
    }

    @PutMapping("/communities/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void updateCommunity(@PathVariable("id") Long id, @RequestBody Community community) {
        LOGGER.debug("Entering updateCommunity endpoint");
        communityService.update(id, community);
    }

    @DeleteMapping("/communities/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void deleteCommunity(@PathVariable("id") Long id) {
        LOGGER.debug("Entering deleteCommunity endpoint");
        communityService.delete(id);
    }
}
