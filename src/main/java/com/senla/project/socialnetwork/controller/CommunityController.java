package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.service.CommunityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class CommunityController {
    private final CommunityService communityService;

    @GetMapping("/communities")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<Community> getAllEmployees(){
        return communityService.findAll();
    }

    @GetMapping("/communities/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public Community getById(@PathVariable("id") Long id) {
        return communityService.findById(id);
    }

    @PostMapping("/communities")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public Community addContact(@Valid @RequestBody Community community) {
        return communityService.add(community);
    }

    @PutMapping("/communities/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void updateContact(@PathVariable("id") Long id, @RequestBody Community community) {
        communityService.update(id, community);
    }

    @DeleteMapping("/communities/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void deleteContact(@PathVariable("id") Long id) {
        communityService.delete(id);
    }
}
