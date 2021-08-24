package com.senla.project.socialnetwork.controller;

import com.senla.project.socialnetwork.entity.CommunityMessage;
import com.senla.project.socialnetwork.model.dto.CommunityMessageDTO;
import com.senla.project.socialnetwork.model.dto.CommunityMessageFilterRequest;
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
        return communityMessageService.add(communityMessageDTO.getCommunityName(), communityMessageDTO.getTxt());
    }

//    @GetMapping("/allMessages")
//    @PreAuthorize("hasAuthority('standard:permission')")
//    public List<CommunityMessage> getAllCommunityMessageByCommunity(@RequestBody String communityName) {
//        return communityMessageService.findCommunityMessagesByCommunityName(communityName);
//    }
//
//    @GetMapping("/allMessagesOf")
//    @PreAuthorize("hasAuthority('standard:permission')")
//    public List<CommunityMessage> getAllCommunityMessagesByCommunityAndUser(@RequestBody CommunityMessageDTO communityMessageDTO) {
//        return communityMessageService.findCommunityMessagesByCommunityNameAndCreatorLogin(communityMessageDTO.getCommunityName(), communityMessageDTO.getTxt());
//    }
//
//    @GetMapping("/allMessagesBetween")
//    @PreAuthorize("hasAuthority('standard:permission')")
//    public List<CommunityMessage> getCommunityMessagesByDateBetween(@RequestBody TimeInterval<LocalDateTime> timeInterval) {
//        return communityMessageService.findCommunityMessagesByDateBetween(timeInterval.getFrom(), timeInterval.getTo());
//    }

    @GetMapping("/communityPage")
    @PreAuthorize("hasAuthority('standard:permission')")
    public List<CommunityMessage> getCM(@RequestBody CommunityMessageFilterRequest request) {
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
