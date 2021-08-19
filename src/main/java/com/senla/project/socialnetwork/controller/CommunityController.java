package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.model.dto.CommunityAddDTO;
import com.senla.project.socialnetwork.model.dto.CommunityUpdateDTO;
import com.senla.project.socialnetwork.service.CommunityService;
import lombok.RequiredArgsConstructor;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communities")
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('communities:permission')")
    public List<Community> getAllCommunities() {
        return communityService.findAll();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public Community getByName(@RequestBody String name) {
        return communityService.findByName(name);
    }

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

    @PutMapping("/{name}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void updateCommunity(@PathVariable("name") String name, @RequestBody CommunityUpdateDTO communityUpdateDTO) {
        Community community = Community.builder()
                .creator(communityUpdateDTO.getCreator())
                .name(communityUpdateDTO.getName())
                .description(communityUpdateDTO.getDescription())
                .build();
        communityService.update(name, community);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void deleteCommunity(@PathVariable("id") Long id) {
        communityService.delete(id);
    }

}
