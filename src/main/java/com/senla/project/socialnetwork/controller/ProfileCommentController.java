package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.ProfileComment;
import com.senla.project.socialnetwork.service.ProfileCommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class ProfileCommentController {
    private final ProfileCommentService profileCommentService;

    @GetMapping("/profileComments")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<ProfileComment> getAllEmployees(){
        return profileCommentService.findAll();
    }

    @GetMapping("/profileComments/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public ProfileComment getById(@PathVariable("id") Long id) {
        return profileCommentService.findById(id);
    }

    @PostMapping("/profileComments")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void addContact(@RequestBody ProfileComment profileComment) {
        profileCommentService.add(profileComment);
    }

    @PutMapping("/profileComments/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateContact(@PathVariable("id") Long id, @RequestBody ProfileComment profileComment) {
        profileCommentService.update(id, profileComment);
    }

    @DeleteMapping("/profileComments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteContact(@PathVariable("id") Long id) {
        profileCommentService.delete(id);
    }
}
