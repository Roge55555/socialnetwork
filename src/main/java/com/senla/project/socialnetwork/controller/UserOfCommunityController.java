package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.UserOfCommunity;
import com.senla.project.socialnetwork.model.dto.UserOfCommunityDTO;
import com.senla.project.socialnetwork.model.dto.UserOfCommunitySearchDTO;
import com.senla.project.socialnetwork.service.UserOfCommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userOfCommunities")
public class UserOfCommunityController {

    private final UserOfCommunityService userOfCommunityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('communities:permission')")
    public UserOfCommunity addUserOfCommunity(@RequestBody UserOfCommunityDTO userOfCommunityDTO) {
        return userOfCommunityService.add(userOfCommunityDTO.getCommunityId(), userOfCommunityDTO.getUserId());
    }

    @GetMapping("/communities/{login}")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<UserOfCommunity> getAllCommunitiesOfUsers(@PathVariable("login") String login) {
        return userOfCommunityService.findAllCommunitiesOfUser(login);
    }

    @GetMapping("/users/{name}")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<UserOfCommunity> getAllUsersOfCommunity(@PathVariable("name") String name) {
        return userOfCommunityService.findAllUsersOfCommunity(name);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('standard:permission')")
    public UserOfCommunity getByIdCommunityNameAndUserLogin(@RequestBody UserOfCommunitySearchDTO userOfCommunitySearchDTO) {
        return userOfCommunityService.findByCommunityNameAndUserLogin(userOfCommunitySearchDTO.getCommunityName(), userOfCommunitySearchDTO.getUserLogin());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void deleteUserOfCommunity(@RequestBody UserOfCommunityDTO userOfCommunityDTO) {
        userOfCommunityService.delete(userOfCommunityDTO.getCommunityId(), userOfCommunityDTO.getUserId());
    }

}
