package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.entity.User;
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
        UserOfCommunity userOfCommunity = UserOfCommunity.builder()
                .community(Community.builder().id(userOfCommunityDTO.getCommunityId()).build())
                .user(User.builder().id(userOfCommunityDTO.getUserId()).build())
                .build();
        return userOfCommunityService.add(userOfCommunity);
    }

    @GetMapping("/communities")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<UserOfCommunity> getAllCommunitiesOfUsers() {
        return userOfCommunityService.findAllCommunitiesOfUser();
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<UserOfCommunity> getAllUsersOfCommunity(@PathVariable("id") Long communityId) {
        return userOfCommunityService.findAllUsersOfCommunity(communityId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('communities:permission')")
    public UserOfCommunity getByIdCommunityNameAndUserLogin(@RequestBody UserOfCommunitySearchDTO userOfCommunitySearchDTO) {
        return userOfCommunityService.findByCommunityIdAndUserId(userOfCommunitySearchDTO.getCommunityId(), userOfCommunitySearchDTO.getUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('communities:permission')")
    public void deleteUserOfCommunity(@RequestBody UserOfCommunityDTO userOfCommunityDTO) {
        userOfCommunityService.delete(userOfCommunityDTO.getCommunityId(), userOfCommunityDTO.getUserId());
    }

}
