package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.Community;
import com.senla.project.socialnetwork.entity.CommunityMessage;
import com.senla.project.socialnetwork.model.dto.CommunityMessageDTO;
import com.senla.project.socialnetwork.model.filter.CommunityMessageFilterRequest;
import com.senla.project.socialnetwork.service.CommunityMessageService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communityMessages")
public class CommunityMessageController {

    private final CommunityMessageService communityMessageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public CommunityMessage addCommunityMessage(@RequestBody CommunityMessageDTO communityMessageDTO) {
        CommunityMessage communityMessage = CommunityMessage.builder()
                .community(Community.builder().id(communityMessageDTO.getCommunityId()).build())
                .txt(communityMessageDTO.getTxt())
                .build();
        return communityMessageService.add(communityMessage);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<CommunityMessage> getCommunityMessages(@RequestBody CommunityMessageFilterRequest request) {
        return communityMessageService.findAll(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void updateCommunityMessage(@PathVariable("id") Long id, @RequestBody String txt) {
        communityMessageService.update(id, txt);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('standard:permission')")
    public void deleteCommunityMessage(@PathVariable("id") Long id) {
        communityMessageService.delete(id);
    }

}
