package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.UserOfCommunity;
import com.senla.project.socialnetwork.service.UserOfCommunityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserOfCommunityController {
    private final UserOfCommunityService userOfCommunityService;

    public UserOfCommunityController(UserOfCommunityService userOfCommunityService) {
        this.userOfCommunityService = userOfCommunityService;
    }

    @GetMapping("/userOfCommunities")
    public List<UserOfCommunity> getAllEmployees(){
        return userOfCommunityService.findAll();
    }

    @GetMapping("/userOfCommunities/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public UserOfCommunity getById(@PathVariable("id") Long id) {
        return userOfCommunityService.findById(id);
    }

    @PostMapping("/userOfCommunities")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@RequestBody UserOfCommunity userOfCommunity) {
        userOfCommunityService.add(userOfCommunity);
    }

    @PutMapping("/userOfCommunities/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateContact(@PathVariable("id") Long id, @RequestBody UserOfCommunity userOfCommunity) {
        userOfCommunityService.update(id, userOfCommunity);
    }

    @DeleteMapping("/userOfCommunities/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("id") Long id) {
        userOfCommunityService.delete(id);
    }
}
