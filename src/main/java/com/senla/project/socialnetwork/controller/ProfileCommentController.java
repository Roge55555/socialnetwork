package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.ProfileComment;
import com.senla.project.socialnetwork.service.ProfileCommentService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/")
public class ProfileCommentController {
    private final ProfileCommentService profileCommentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileCommentController.class);

    @GetMapping("/profileComments")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<ProfileComment> getAllProfileComments() {
        LOGGER.debug("Entering findAll profile comments endpoint");
        return profileCommentService.findAll();
    }

    @GetMapping("/profileComments/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public ProfileComment getById(@PathVariable("id") Long id) {
        LOGGER.debug("Entering getById profile comment endpoint");
        return profileCommentService.findById(id);
    }

    @PostMapping("/profileComments")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public ProfileComment addProfileComment(@RequestBody ProfileComment profileComment) {
        LOGGER.debug("Entering addProfileComment endpoint");
        return profileCommentService.add(profileComment);
    }

    @PutMapping("/profileComments/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateProfileComment(@PathVariable("id") Long id, @RequestBody ProfileComment profileComment) {
        LOGGER.debug("Entering updateProfileComment endpoint");
        profileCommentService.update(id, profileComment);
    }

    @DeleteMapping("/profileComments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteProfileComment(@PathVariable("id") Long id) {
        LOGGER.debug("Entering deleteProfileComment endpoint");
        profileCommentService.delete(id);
    }
}
