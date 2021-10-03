package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.entity.User;
import com.senla.project.socialnetwork.model.dto.CommunityAddDTO;
import com.senla.project.socialnetwork.model.dto.CommunityUpdateDTO;
import com.senla.project.socialnetwork.service.CommunityService;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communities")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public Community addCommunity(@Valid @RequestBody CommunityAddDTO communityAddDTO) {
        Community community = Community.builder()
                .name(communityAddDTO.getName())
                .description(communityAddDTO.getDescription())
                .build();
        return communityService.add(community);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('communities:permission')")
    public List<Community> getAllCommunitiesByCreator() {
        return communityService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public Community getById(@PathVariable("id") Long id) {
        return communityService.findById(id);
    }

    @GetMapping("/search/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<Community> getByName(@PathVariable("name") String name) {
        return communityService.searchBySubstringOfName(name);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void updateCommunity(@PathVariable("id") Long id, @RequestBody CommunityUpdateDTO communityUpdateDTO) {
        Community community = Community.builder()
                .creator(User.builder().id(communityUpdateDTO.getCreatorId()).build())
                .name(communityUpdateDTO.getName())
                .description(communityUpdateDTO.getDescription())
                .build();
        communityService.update(id, community);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void deleteCommunity(@PathVariable("id") Long id) {
        communityService.delete(id);
    }

}
