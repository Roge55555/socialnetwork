package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.service.CommunityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CommunityController {
    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/communities")
    public List<Community> getAllEmployees(){
        return communityService.findAll();
    }

    @GetMapping("/communities/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Community getById(@PathVariable("id") Long id) {
        return communityService.findById(id);
    }

    @PostMapping("/communities")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@RequestBody Community community) {
        communityService.add(community);
    }

    @PutMapping("/communities/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateContact(@PathVariable("id") Long id, @RequestBody Community community) {
        communityService.update(id, community);
    }

    @DeleteMapping("/communities/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("id") Long id) {
        communityService.delete(id);
    }
}
