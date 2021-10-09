package com.myproject.socialnetwork.controller;

import com.myproject.socialnetwork.entity.ProfileComment;
import com.myproject.socialnetwork.entity.User;
import com.myproject.socialnetwork.model.dto.ProfileCommentDTO;
import com.myproject.socialnetwork.model.filter.ProfileCommentFilterRequest;
import com.myproject.socialnetwork.service.ProfileCommentService;
import lombok.RequiredArgsConstructor;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profileComments")
public class ProfileCommentController {

    private final ProfileCommentService profileCommentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public ProfileComment addProfileComment(@RequestBody ProfileCommentDTO profileCommentDTO) {
        ProfileComment profileComment = ProfileComment.builder()
                .profileOwner(User.builder().id(profileCommentDTO.getUserId()).build())
                .date(LocalDateTime.now().plusSeconds(1).truncatedTo(ChronoUnit.SECONDS))
                .commentTxt(profileCommentDTO.getTxt())
                .build();
        return profileCommentService.add(profileComment);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<ProfileComment> getAllProfileComments(@RequestBody ProfileCommentFilterRequest request) {
        return profileCommentService.findAll(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public ProfileComment getById(@PathVariable("id") Long id) {
        return profileCommentService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateProfileComment(@PathVariable("id") Long id, @RequestBody String profileCommentTxt) {
        profileCommentService.update(id, profileCommentTxt);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteProfileComment(@PathVariable("id") Long id) {
        profileCommentService.delete(id);
    }

}
