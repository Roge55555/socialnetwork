package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.UserOfCommunity;
import com.senla.project.socialnetwork.service.UserOfCommunityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class UserOfCommunityController {
    private final UserOfCommunityService userOfCommunityService;

    @GetMapping("/userOfCommunities")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<UserOfCommunity> getAllUserOfCommunities(){
        return userOfCommunityService.findAll();
    }

    @GetMapping("/userOfCommunities/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public UserOfCommunity getById(@PathVariable("id") Long id) {
        return userOfCommunityService.findById(id);
    }

    @PostMapping("/userOfCommunities")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public UserOfCommunity addUserOfCommunity(@RequestBody UserOfCommunity userOfCommunity) {
        return userOfCommunityService.add(userOfCommunity);
    }

//    @PutMapping("/userOfCommunities/{id}")
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public void updateContact(@PathVariable("id") Long id, @RequestBody UserOfCommunity userOfCommunity) {
//        userOfCommunityService.update(id, userOfCommunity);
//    }

    @DeleteMapping("/userOfCommunities/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void deleteUserOfCommunity(@PathVariable("id") Long id) {
        userOfCommunityService.delete(id);
    }
}
