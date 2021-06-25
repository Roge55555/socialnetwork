package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.ProfileComment;
import com.senla.project.socialnetwork.service.ProfileCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ProfileCommentController {
    private final ProfileCommentService profileCommentService;

    public ProfileCommentController(ProfileCommentService profileCommentService) {
        this.profileCommentService = profileCommentService;
    }

    @GetMapping("/profileComments")
    public List<ProfileComment> getAllEmployees(){
        return profileCommentService.findAll();
    }

    @GetMapping("/profileComments/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ProfileComment getById(@PathVariable("id") Long id) {
        return profileCommentService.findById(id);
    }

    @PostMapping("/profileComments")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@RequestBody ProfileComment profileComment) {
        profileCommentService.add(profileComment);
    }

    @PutMapping("/profileComments/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateContact(@PathVariable("id") Long id, @RequestBody ProfileComment profileComment) {
        profileCommentService.update(id, profileComment);
    }

    @DeleteMapping("/profileComments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("id") Long id) {
        profileCommentService.delete(id);
    }
}
